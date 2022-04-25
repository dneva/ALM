package com.example.alm_gui;

import com.example.alm_gui.Classes.Item;
import com.example.alm_gui.Classes.Requirement;
import com.example.alm_gui.Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequirementController {
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
    TextField fieldAnalysis;
    @FXML
    TextField fieldDevelopment;
    @FXML
    TextField fieldTest;
    @FXML
    TextField fieldRelease;
    private Requirement requirement;
    private PostgreConnection postgreConnection;
    private User user;
    public void initialization(PostgreConnection post,Requirement req,User u){
        requirement=req;
        postgreConnection=post;
        user=u;

        labelId_requirement.setText("ID: "+String.valueOf(requirement.getId_item()));
        textDescription.setText(requirement.getDescription());
        fieldTime.setText("Изменено "+ requirement.getTime());
        fieldChanged.setText(postgreConnection.findUser(requirement.getChanged_by()).getLogin());
        fieldTitle.setText(requirement.getTitle());
        fieldVersion.setText(requirement.getVersion());
        fieldState.setText(requirement.getState());
        fieldAssign.setText(postgreConnection.findUser(requirement.getAssign()).getLogin());
        fieldAnalysis.setText(requirement.getAnalysis_estimate());
        fieldDevelopment.setText(requirement.getDevelopment_estimate());
        fieldTest.setText(requirement.getTesting_estimate());
        fieldRelease.setText(requirement.getRelease_date());
    }
    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException {
        Date date = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);

        requirement.setDescription(textDescription.getText());
        requirement.setTime(df.format(date));
        requirement.setChanged_by(user.getId());
        requirement.setTitle(fieldTitle.getText());
        requirement.setVersion(fieldVersion.getText());
        requirement.setState(fieldState.getText());
        requirement.setAssign(postgreConnection.findUser(fieldAssign.getText()).getId());
        requirement.setAnalysis_estimate(fieldAnalysis.getText());
        requirement.setDevelopment_estimate(fieldDevelopment.getText());
        requirement.setTesting_estimate(fieldTest.getText());
        requirement.setRelease_date(fieldRelease.getText());
        if (requirement.getId_item()==0)
        {
            Item item = new Item(df.format(date),1,df.format(date));
            requirement.setId_item((int)postgreConnection.insertItem(item));
        }
        postgreConnection.updateModifyItem(requirement.getId_item(),df.format(date));
        postgreConnection.insertRequirement(requirement);
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

        postgreConnection.deleteItem(requirement.getId_item());
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
