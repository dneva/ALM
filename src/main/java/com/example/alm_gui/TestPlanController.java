package com.example.alm_gui;

import com.example.alm_gui.Classes.Item;
import com.example.alm_gui.Classes.Requirement;
import com.example.alm_gui.Classes.TestPlan;
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

public class TestPlanController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    Label labelId_requirement;
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

    private TestPlan testPlan;
    private PostgreConnection postgreConnection;
    private User user;
    public void initialization(PostgreConnection post,TestPlan tp,User u){
        testPlan=tp;
        postgreConnection=post;
        user=u;

        labelId_requirement.setText("ID: "+String.valueOf(testPlan.getId_item()));
        fieldTime.setText("Изменено "+ testPlan.getTime());
        fieldChanged.setText(postgreConnection.findUser(testPlan.getChanged_by()).getLogin());
        fieldTitle.setText(testPlan.getTitle());
        fieldVersion.setText(testPlan.getVersion());
        fieldState.setText(testPlan.getState());
        fieldAssign.setText(postgreConnection.findUser(testPlan.getAssign()).getLogin());
    }
    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException {
        Date date = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);

        testPlan.setTime(df.format(date));
        testPlan.setChanged_by(user.getId());
        testPlan.setTitle(fieldTitle.getText());
        testPlan.setVersion(fieldVersion.getText());
        testPlan.setState(fieldState.getText());
        testPlan.setAssign(postgreConnection.findUser(fieldAssign.getText()).getId());
        if (testPlan.getId_item()==0)
        {
            Item item = new Item(df.format(date),6,df.format(date));
            testPlan.setId_item((int)postgreConnection.insertItem(item));
        }
        postgreConnection.updateModifyItem(testPlan.getId_item(),df.format(date));
        postgreConnection.insertTestPlan(testPlan);
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

        postgreConnection.deleteItem(testPlan.getId_item());
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
