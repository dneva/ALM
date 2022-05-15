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

public class TaskController extends Application {
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
        linkCommit.setText(task.getDev());
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
        ObservableList<LinkItem> linkItems = postgreConnection.getLinkItems(task.getId_item());
        columnIDLink1.setCellValueFactory(new PropertyValueFactory<>("Id1"));
        columnIDLink2.setCellValueFactory(new PropertyValueFactory<>("Id2"));
        columnTypeLink.setCellValueFactory(new PropertyValueFactory<>("Type"));
        tableLinks.setItems(linkItems);
    }
    public void tableHistoryFill(){
        ObservableList<HistoryItem> historyItems = postgreConnection.getTaskHistoryItems(task.getId_item());
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
        task.setDev(linkCommit.getText());
        if (task.getId_item()==0)
        {
            Item item = new Item(df.format(date),4,df.format(date));
            task.setId_item((int)postgreConnection.insertItem(item));
        }
        postgreConnection.updateModifyItem(task.getId_item(),df.format(date));
        postgreConnection.insertTask(task);
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

        postgreConnection.deleteItem(task.getId_item());
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
        Link link = new Link(task.getId_item(),Integer.parseInt(fieldIDLink.getText()),fieldTypeLink.getText());
        postgreConnection.insertLink(link);
        tableFill();
    }
    @FXML
    protected void onButtonDeleteLinkClick(ActionEvent event) throws IOException{
        postgreConnection.deleteLink(postgreConnection.findLink(task.getId_item(),Integer.parseInt(fieldIDLink.getText())));
        tableFill();
    }
    @Override
    public void start(Stage stage) throws Exception {

    }
}
