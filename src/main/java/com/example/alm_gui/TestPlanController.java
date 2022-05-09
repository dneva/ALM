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
import javafx.scene.control.cell.TextFieldTableCell;
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
    @FXML
    private TableColumn <TestCaseItem, Integer> columnID;
    @FXML
    private TableColumn <TestCaseItem, String> columnTitle;
    @FXML
    private TableColumn <TestCaseItem, String> columnExecution;
    @FXML
    TableView tableCases;
    @FXML
    TextField fieldIDTest;
    @FXML
    Button addTestButton;
    @FXML
    Button deleteTestButton;
    private TestPlan testPlan;
    private PostgreConnection postgreConnection;
    private User user;
    private ObservableList<TestCaseItem> testCaseItems;

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

        testCaseItems = postgreConnection.getTestCaseItems(testPlan.getId_item());
        columnID.setCellValueFactory(new PropertyValueFactory<>("Id_item"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        columnExecution.setCellValueFactory(new PropertyValueFactory<>("Execution"));
        tableCases.setItems(testCaseItems);

        columnExecution.setCellFactory(TextFieldTableCell.forTableColumn());
        columnExecution.setOnEditCommit(event-> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setExecution(event.getNewValue());
        });
        tableCases.setRowFactory(tv -> {
            TableRow<TestCaseItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2 && (!row.isEmpty())) {
                            TestCaseItem rowData = row.getItem();
                            FXMLLoader loader;
                            int id = rowData.getId();
                            loader = new FXMLLoader(getClass().getResource("TestCaseScene.fxml"));
                            try {
                                root = loader.load();
                                TestCaseController testCaseController = loader.getController();
                                testCaseController.initialization(postgreConnection, postgreConnection.findTestCase(rowData.getId_item()), user, "TestPlanScene.fxml", testPlan);

                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                });
            return row;
        });

    }
    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException {
        tableCases.refresh();
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

        testCaseItems = tableCases.getItems();

        for (TestCaseItem tci : testCaseItems)
        {
            postgreConnection.updateExecutionTest(postgreConnection.findPlanCase(testPlan.getId_item(),tci.getId_item()).getId(),tci.getExecution());
        }



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
    @FXML
    protected void onAddTestButtonClick(ActionEvent event) throws IOException
    {

        PlanCase planCase = new PlanCase(testPlan.getId_item(),Integer.parseInt(fieldIDTest.getText()),"Не пройдено");
        postgreConnection.insertPlanCase(planCase);
        testCaseItems = postgreConnection.getTestCaseItems(testPlan.getId_item());
        tableCases.setItems(testCaseItems);
    }
    @FXML
    protected void  onDeleteTestButtonClick(ActionEvent event) throws IOException
    {
        postgreConnection.deletePlanCase(postgreConnection.findPlanCase(testPlan.getId_item(),Integer.parseInt(fieldIDTest.getText())).getId());
        testCaseItems = postgreConnection.getTestCaseItems(testPlan.getId_item());
        tableCases.setItems(testCaseItems);
    }
}
