package com.example.alm_gui;

import com.example.alm_gui.Classes.Issue;
import com.example.alm_gui.Classes.Item;
import com.example.alm_gui.Classes.Requirement;
import com.example.alm_gui.Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IssueController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    Label labelId_requirement;
    @FXML
    TextArea textDescription;
    @FXML
    Label fieldTime;
    @FXML
    Label fieldChanged;
    @FXML
    TextField fieldTitle;
    @FXML
    TextField fieldVersion;
    @FXML
    TextField fieldState;
    @FXML
    TextField fieldAssign;
    @FXML
    TextField fieldType;
    @FXML
    TextField fieldFoundBuild;
    @FXML
    TextField fieldPriority;
    @FXML
    TextArea textSteps;
    private Issue issue;
    private PostgreConnection postgreConnection;
    private User user;
    public void initialization(PostgreConnection post, Issue is, User u){
        issue=is;
        postgreConnection=post;
        user=u;

        labelId_requirement.setText("ID: "+String.valueOf(issue.getId_item()));
        textDescription.setText(issue.getDescription());
        fieldTime.setText("Изменено "+ issue.getTime());
        fieldChanged.setText(postgreConnection.findUser(issue.getChanged_by()).getLogin());
        fieldTitle.setText(issue.getTitle());
        fieldVersion.setText(issue.getVersion());
        fieldState.setText(issue.getState());
        fieldAssign.setText(postgreConnection.findUser(issue.getAssign()).getLogin());
        fieldType.setText(issue.getIssue_type());
        fieldFoundBuild.setText(issue.getFound_build());
        fieldPriority.setText(String.valueOf(issue.getPriority()));
        textSteps.setText(issue.getSteps());
    }
    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException {
        Date date = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);

        issue.setDescription(textDescription.getText());
        issue.setTime(df.format(date));
        issue.setChanged_by(user.getId());
        issue.setTitle(fieldTitle.getText());
        issue.setVersion(fieldVersion.getText());
        issue.setState(fieldState.getText());
        issue.setAssign(postgreConnection.findUser(fieldAssign.getText()).getId());
        issue.setIssue_type(fieldType.getText());
        issue.setFound_build(fieldFoundBuild.getText());
        issue.setPriority(Integer.parseInt(fieldPriority.getText()));
        issue.setSteps(textSteps.getText());
        if (issue.getId_item()==0)
        {
            Item item = new Item(df.format(date),3,df.format(date));
            issue.setId_item((int)postgreConnection.insertItem(item));
        }
        postgreConnection.updateModifyItem(issue.getId_item(),df.format(date));
        postgreConnection.insertIssue(issue);
    }
    @FXML
    protected void onBackButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        root = loader.load();
        MainController mainController = loader.getController();
        mainController.helloTitle(user);
        mainController.tableFill(postgreConnection);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    protected void onDeleteButtonClick(ActionEvent event) throws IOException {

        postgreConnection.deleteItem(issue.getId_item());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        root = loader.load();
        MainController mainController = loader.getController();
        mainController.helloTitle(user);
        mainController.tableFill(postgreConnection);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
