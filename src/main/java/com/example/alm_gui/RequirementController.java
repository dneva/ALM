package com.example.alm_gui;

import com.example.alm_gui.Classes.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TableColumn<LinkItem, Integer> columnIDLink1;
    @FXML
    private TableColumn<LinkItem, Integer> columnIDLink2;
    @FXML
    private TableColumn<LinkItem, String> columnTypeLink;
    @FXML
    TableView <LinkItem> tableLinks;
    @FXML
    Button buttonDeleteLink;
    @FXML
    Button buttonAddLink;
    @FXML
    TextField fieldIDLink;
    @FXML
    TextField fieldTypeLink;
    @FXML
    TableView HistoryTable;
    @FXML
    private TableColumn<HistoryItem, Integer> columnIDHistory;
    @FXML
    private TableColumn<HistoryItem, String> columnTimeHistory;
    @FXML
    private TableColumn<HistoryItem, String> columnChangedByHistory;
    @FXML
    private TableColumn<HistoryItem, String> columnStateHistory;
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
        tableFill();
        tableHistoryFill();
    }
    public void tableFill() {
        ObservableList<LinkItem> linkItems = postgreConnection.getLinkItems(requirement.getId_item());
        columnIDLink1.setCellValueFactory(new PropertyValueFactory<>("Id1"));
        columnIDLink2.setCellValueFactory(new PropertyValueFactory<>("Id2"));
        columnTypeLink.setCellValueFactory(new PropertyValueFactory<>("Type"));
        tableLinks.setItems(linkItems);
    }
    public void tableHistoryFill(){
        ObservableList<HistoryItem> historyItems = postgreConnection.getRequirementHistoryItems(requirement.getId_item());
        columnIDHistory.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnTimeHistory.setCellValueFactory(new PropertyValueFactory<>("Time"));
        columnChangedByHistory.setCellValueFactory(new PropertyValueFactory<>("Login"));
        columnStateHistory.setCellValueFactory(new PropertyValueFactory<>("State"));
        HistoryTable.setItems(historyItems);
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
            labelId_requirement.setText("ID: "+String.valueOf(requirement.getId_item()));
        }
        postgreConnection.updateModifyItem(requirement.getId_item(),df.format(date));
        postgreConnection.insertRequirement(requirement);
        fieldTime.setText("Изменено "+ requirement.getTime());
        fieldChanged.setText(postgreConnection.findUser(requirement.getChanged_by()).getLogin());
        tableHistoryFill();
    }
    @FXML
    protected void onBackButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        root = loader.load();
        MainController mainController = loader.getController();
        mainController.helloTitle(user);
        mainController.tableFill(postgreConnection);
        mainController.chartsInit(postgreConnection);

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
        mainController.chartsInit(postgreConnection);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    protected void onButtonAddLinkClick(ActionEvent event) throws IOException{
        Link link = new Link(requirement.getId_item(),Integer.parseInt(fieldIDLink.getText()),fieldTypeLink.getText());
        postgreConnection.insertLink(link);
        tableFill();
    }
    @FXML
    protected void onButtonDeleteLinkClick(ActionEvent event) throws IOException{
        postgreConnection.deleteLink(postgreConnection.findLink(requirement.getId_item(),Integer.parseInt(fieldIDLink.getText())));
        tableFill();
    }
}
