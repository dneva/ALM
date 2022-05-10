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

public class BugController extends Application {
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
    @FXML
    TextField fieldGitUser;
    @FXML
    TextField fieldGitPassword;
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
        linkCommit.setText(bug.getDev());
        linkCommit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                getHostServices().showDocument(linkCommit.getText());
            }
        });
        tableFill();

    }
    public void tableFill() {
        ObservableList<LinkItem> linkItems = postgreConnection.getLinkItems(bug.getId_item());
        columnIDLink1.setCellValueFactory(new PropertyValueFactory<>("Id1"));
        columnIDLink2.setCellValueFactory(new PropertyValueFactory<>("Id2"));
        columnTypeLink.setCellValueFactory(new PropertyValueFactory<>("Type"));
        tableLinks.setItems(linkItems);
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
        bug.setDev(linkCommit.getText());

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
        Link link = new Link(bug.getId_item(),Integer.parseInt(fieldIDLink.getText()),fieldTypeLink.getText());
        postgreConnection.insertLink(link);
        tableFill();
    }
    @FXML
    protected void onButtonDeleteLinkClick(ActionEvent event) throws IOException{
        postgreConnection.deleteLink(postgreConnection.findLink(bug.getId_item(),Integer.parseInt(fieldIDLink.getText())));
        tableFill();
    }
    @Override
    public void start(Stage stage) throws Exception {

    }
}
