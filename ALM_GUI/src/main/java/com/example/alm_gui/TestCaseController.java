package com.example.alm_gui;

import com.example.alm_gui.Classes.*;
import javafx.collections.ObservableList;
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

public class TestCaseController {
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
    TextField fieldPriority;
    @FXML
    TextField fieldAutoStatus;
    private TestCase testCase;
    private PostgreConnection postgreConnection;
    private User user;
    private String parentFxml;
    private TestPlan testPlan;
    public void initialization(PostgreConnection post,TestCase tc,User u, String fxml){
        testCase=tc;
        postgreConnection=post;
        user=u;
        parentFxml =fxml;

        labelId_requirement.setText("ID: "+String.valueOf(testCase.getId_item()));
        textSteps.setText(testCase.getSteps());
        fieldTime.setText("Изменено "+ testCase.getTime());
        fieldChanged.setText(postgreConnection.findUser(testCase.getChanged_by()).getLogin());
        fieldTitle.setText(testCase.getTitle());
        fieldVersion.setText(testCase.getVersion());
        fieldState.setText(testCase.getState());
        fieldAssign.setText(postgreConnection.findUser(testCase.getAssign()).getLogin());
        fieldPriority.setText(String.valueOf(testCase.getPriority()));
        fieldAutoStatus.setText(testCase.getAuto_status());
    }
    public void initialization(PostgreConnection post,TestCase tc,User u, String fxml, TestPlan tp){
        testCase=tc;
        postgreConnection=post;
        user=u;
        parentFxml =fxml;
        testPlan = tp;

        labelId_requirement.setText("ID: "+String.valueOf(testCase.getId_item()));
        textSteps.setText(testCase.getSteps());
        fieldTime.setText("Изменено "+ testCase.getTime());
        fieldChanged.setText(postgreConnection.findUser(testCase.getChanged_by()).getLogin());
        fieldTitle.setText(testCase.getTitle());
        fieldVersion.setText(testCase.getVersion());
        fieldState.setText(testCase.getState());
        fieldAssign.setText(postgreConnection.findUser(testCase.getAssign()).getLogin());
        fieldPriority.setText(String.valueOf(testCase.getPriority()));
        fieldAutoStatus.setText(testCase.getAuto_status());
    }
    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException {
        Date date = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);

        testCase.setSteps(textSteps.getText());
        testCase.setTime(df.format(date));
        testCase.setChanged_by(user.getId());
        testCase.setTitle(fieldTitle.getText());
        testCase.setVersion(fieldVersion.getText());
        testCase.setState(fieldState.getText());
        testCase.setAssign(postgreConnection.findUser(fieldAssign.getText()).getId());
        testCase.setPriority(Integer.parseInt(fieldPriority.getText()));
        testCase.setAuto_status(fieldAutoStatus.getText());
        if (testCase.getId_item()==0)
        {
            Item item = new Item(df.format(date),5,df.format(date));
            testCase.setId_item((int)postgreConnection.insertItem(item));
        }
        postgreConnection.updateModifyItem(testCase.getId_item(),df.format(date));
        postgreConnection.insertTestCase(testCase);
    }
    @FXML
    protected void onBackButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(parentFxml));
        root = loader.load();
        if (parentFxml == "TestPlanScene.fxml")
        {
            TestPlanController testPlanController = loader.getController();
            testPlanController.initialization(postgreConnection, testPlan, user);

        } else
        {
            MainController mainController = loader.getController();
            mainController.helloTitle(user);
            mainController.tableFill(postgreConnection);
        }


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    protected void onDeleteButtonClick(ActionEvent event) throws IOException {

        postgreConnection.deleteItem(testCase.getId_item());
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
