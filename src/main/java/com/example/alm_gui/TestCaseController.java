package com.example.alm_gui;

import com.example.alm_gui.Classes.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.kohsuke.github.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TestCaseController extends Application {
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
    @FXML
    TextField fieldGitUser;
    @FXML
    PasswordField fieldGitPassword;
    @FXML
    ChoiceBox<String> choiceBoxRepository;
    @FXML
    ListView<Hyperlink> listCommit;
    @FXML
    Hyperlink linkCommit;
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
        linkCommit.setText(testCase.getDev());
        linkCommit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                getHostServices().showDocument(linkCommit.getText());
            }
        });
        tableFill();
        tableHistoryFill();

    }
    public void tableFill() {
        ObservableList<LinkItem> linkItems = postgreConnection.getLinkItems(testCase.getId_item());
        columnIDLink1.setCellValueFactory(new PropertyValueFactory<>("Id1"));
        columnIDLink2.setCellValueFactory(new PropertyValueFactory<>("Id2"));
        columnTypeLink.setCellValueFactory(new PropertyValueFactory<>("Type"));
        tableLinks.setItems(linkItems);
    }
    public void tableHistoryFill(){
        ObservableList<HistoryItem> historyItems = postgreConnection.getTestCaseHistoryItems(testCase.getId_item());
        columnIDHistory.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnTimeHistory.setCellValueFactory(new PropertyValueFactory<>("Time"));
        columnChangedByHistory.setCellValueFactory(new PropertyValueFactory<>("Login"));
        columnStateHistory.setCellValueFactory(new PropertyValueFactory<>("State"));
        HistoryTable.setItems(historyItems);
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
        linkCommit.setText(testCase.getDev());
        linkCommit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                getHostServices().showDocument(linkCommit.getText());
            }
        });

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
        testCase.setDev(linkCommit.getText());
        if (testCase.getId_item()==0)
        {
            Item item = new Item(df.format(date),5,df.format(date));
            testCase.setId_item((int)postgreConnection.insertItem(item));
        }
        postgreConnection.updateModifyItem(testCase.getId_item(),df.format(date));
        postgreConnection.insertTestCase(testCase);
        tableHistoryFill();
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
            mainController.chartsInit(postgreConnection);
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
        mainController.chartsInit(postgreConnection);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    protected void onButtonGitLoginClick(ActionEvent event) throws IOException {

        String gitLogin = fieldGitUser.getText();
        String gitPass = fieldGitPassword.getText();
        GitHub github = new GitHubBuilder().withPassword(gitLogin,gitPass).build();
        //System.out.println("Connection Success!!");
        GHUser gitUser = github.getUser(gitLogin);
        Iterator<GHRepository> itr = gitUser.listRepositories().iterator();
        while (itr.hasNext()) {
            GHRepository r = itr.next();
            choiceBoxRepository.getItems().add(r.getName());
        }
        choiceBoxRepository.setOnAction((e) -> {
            String selectedItem = choiceBoxRepository.getSelectionModel().getSelectedItem();
            try {
                GHRepository rep=gitUser.getRepository(selectedItem);
                //System.out.println(rep.getUrl().toString());
                List<Hyperlink> comm=new ArrayList<>();
                for(GHCommit c : rep.listCommits().toList()){
                    Hyperlink h = new Hyperlink(c.getHtmlUrl().toString());
                    h.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent t) {
                            getHostServices().showDocument(h.getText());
                        }
                    });
                    comm.add(h);
                }
                ObservableList<Hyperlink> commits = FXCollections.observableArrayList(comm);
                listCommit.setItems(commits);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


    }
    @FXML
    protected void onButtonCommitClick(ActionEvent event) throws IOException{
        linkCommit.setText(listCommit.getSelectionModel().getSelectedItem().getText());
        linkCommit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                getHostServices().showDocument(linkCommit.getText());
            }
        });

    }
    @FXML
    protected void onButtonAddLinkClick(ActionEvent event) throws IOException{
        Link link = new Link(testCase.getId_item(),Integer.parseInt(fieldIDLink.getText()),fieldTypeLink.getText());
        postgreConnection.insertLink(link);
        tableFill();
    }
    @FXML
    protected void onButtonDeleteLinkClick(ActionEvent event) throws IOException{
        postgreConnection.deleteLink(postgreConnection.findLink(testCase.getId_item(),Integer.parseInt(fieldIDLink.getText())));
        tableFill();
    }
    @Override
    public void start(Stage stage) throws Exception {

    }
}
