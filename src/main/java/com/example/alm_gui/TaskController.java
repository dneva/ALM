package com.example.alm_gui;

import com.example.alm_gui.Classes.Item;
import com.example.alm_gui.Classes.Requirement;
import com.example.alm_gui.Classes.Task;
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

public class TaskController {
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
    TextField fieldOriginalEffort;
    @FXML
    TextField fieldRemainingEffort;
    @FXML
    TextField fieldExpectedResolve;
    @FXML
    TextField fieldResolve;
    private Task task;
    private PostgreConnection postgreConnection;
    private User user;
    public void initialization(PostgreConnection post,Task t,User u){
        task=t;
        postgreConnection=post;
        user=u;

        labelId_requirement.setText("ID: "+String.valueOf(task.getId_item()));
        textDescription.setText(task.getDescription());
        fieldTime.setText("Изменено "+ task.getTime());
        fieldChanged.setText(postgreConnection.findUser(task.getChanged_by()).getLogin());
        fieldTitle.setText(task.getTitle());
        fieldVersion.setText(task.getVersion());
        fieldState.setText(task.getState());
        fieldAssign.setText(postgreConnection.findUser(task.getAssign()).getLogin());
        fieldOriginalEffort.setText(String.valueOf(task.getOriginal_effort()));
        fieldRemainingEffort.setText(String.valueOf(task.getRemaining_effort()));
        fieldExpectedResolve.setText(task.getExpected_resolve());
        fieldResolve.setText(task.getResolve());
    }
    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException {
        Date date = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);

        task.setDescription(textDescription.getText());
        task.setTime(df.format(date));
        task.setChanged_by(user.getId());
        task.setTitle(fieldTitle.getText());
        task.setVersion(fieldVersion.getText());
        task.setState(fieldState.getText());
        task.setAssign(postgreConnection.findUser(fieldAssign.getText()).getId());
        task.setOriginal_effort(Double.parseDouble(fieldOriginalEffort.getText()));
        task.setRemaining_effort(Double.parseDouble(fieldRemainingEffort.getText()));
        task.setExpected_resolve(fieldExpectedResolve.getText());
        task.setResolve(fieldResolve.getText());
        if (task.getId_item()==0)
        {
            Item item = new Item(df.format(date),4,df.format(date));
            task.setId_item((int)postgreConnection.insertItem(item));
        }
        postgreConnection.updateModifyItem(task.getId_item(),df.format(date));
        postgreConnection.insertTask(task);
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

        postgreConnection.deleteItem(task.getId_item());
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
