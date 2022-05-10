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
    @FXML
    private TableColumn<LinkItem, Integer> columnIDLink1;
    @FXML
    private TableColumn<LinkItem, Integer> columnIDLink2;
    @FXML
    private TableColumn<LinkItem, String> columnTypeLink;
    @FXML
    TableView<LinkItem> tableLinks;
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
        tableFill();
        tableHistoryFill();
    }
    public void tableFill() {
        ObservableList<LinkItem> linkItems = postgreConnection.getLinkItems(issue.getId_item());
        columnIDLink1.setCellValueFactory(new PropertyValueFactory<>("Id1"));
        columnIDLink2.setCellValueFactory(new PropertyValueFactory<>("Id2"));
        columnTypeLink.setCellValueFactory(new PropertyValueFactory<>("Type"));
        tableLinks.setItems(linkItems);
    }
    public void tableHistoryFill(){
        ObservableList<HistoryItem> historyItems = postgreConnection.getIssueHistoryItems(issue.getId_item());
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
        tableHistoryFill();
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
    @FXML
    protected void onButtonAddLinkClick(ActionEvent event) throws IOException{
        Link link = new Link(issue.getId_item(),Integer.parseInt(fieldIDLink.getText()),fieldTypeLink.getText());
        postgreConnection.insertLink(link);
        tableFill();
    }
    @FXML
    protected void onButtonDeleteLinkClick(ActionEvent event) throws IOException{
        postgreConnection.deleteLink(postgreConnection.findLink(issue.getId_item(),Integer.parseInt(fieldIDLink.getText())));
        tableFill();
    }
}
