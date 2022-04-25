package com.example.alm_gui;

import com.example.alm_gui.Classes.Bug;
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

public class BugController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    Label labelId_requirement;
    @FXML
    TextArea textSteps;
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
    TextField fieldEnviroment;
    @FXML
    TextField fieldFindBuild;
    @FXML
    TextField fieldFixBuild;
    @FXML
    TextField fieldOS;
    @FXML
    TextField fieldVerified;
    @FXML
    TextField fieldHowFound;
    @FXML
    TextField fieldLocalization;
    @FXML
    TextField fieldPriority;
    @FXML
    TextField fieldSeverity;
    private Bug bug;
    private PostgreConnection postgreConnection;
    private User user;
    public void initialization(PostgreConnection post,Bug b,User u){
        bug=b;
        postgreConnection=post;
        user=u;

        labelId_requirement.setText("ID: "+String.valueOf(bug.getId_item()));
        textSteps.setText(bug.getSteps());
        fieldTime.setText("Изменено "+ bug.getTime());
        fieldChanged.setText(postgreConnection.findUser(bug.getChanged_by()).getLogin());
        fieldTitle.setText(bug.getTitle());
        fieldVersion.setText(bug.getVersion());
        fieldState.setText(bug.getState());
        fieldAssign.setText(postgreConnection.findUser(bug.getAssign()).getLogin());
        fieldEnviroment.setText(bug.getEnvironment());
        fieldFindBuild.setText(bug.getFound_build());
        fieldFixBuild.setText(bug.getIntegreted_build());
        fieldOS.setText(bug.getOs_ver());
        fieldVerified.setText(bug.getVerified());
        fieldHowFound.setText(bug.getHow_found());
        fieldLocalization.setText(bug.getLocalization());
        fieldPriority.setText(String.valueOf(bug.getPriority()));
        fieldSeverity.setText(String.valueOf(bug.getSeverity()));

    }
    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException {
        Date date = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);

        bug.setSteps(textSteps.getText());
        bug.setTime(df.format(date));
        bug.setChanged_by(user.getId());
        bug.setTitle(fieldTitle.getText());
        bug.setVersion(fieldVersion.getText());
        bug.setState(fieldState.getText());
        bug.setAssign(postgreConnection.findUser(fieldAssign.getText()).getId());
        bug.setEnvironment(fieldEnviroment.getText());
        bug.setFound_build(fieldFindBuild.getText());
        bug.setIntegreted_build(fieldFixBuild.getText());
        bug.setOs_ver(fieldOS.getText());
        bug.setVerified(fieldVerified.getText());
        bug.setHow_found(fieldHowFound.getText());
        bug.setLocalization(fieldLocalization.getText());
        bug.setPriority(Integer.parseInt(fieldPriority.getText()));
        bug.setSeverity(Integer.parseInt(fieldSeverity.getText()));

        if (bug.getId_item()==0)
        {
            Item item = new Item(df.format(date),2,df.format(date));
            bug.setId_item((int)postgreConnection.insertItem(item));
        }
        postgreConnection.updateModifyItem(bug.getId_item(),df.format(date));
        postgreConnection.insertBug(bug);
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

        postgreConnection.deleteItem(bug.getId_item());
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
