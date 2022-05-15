package com.example.alm_gui;

import com.example.alm_gui.Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private PostgreConnection postgreConnection;
    private User user;
    @FXML
    Label nameLabel;
    @FXML
    TableView<MainItem> mainTable;
    @FXML
    private TableColumn<MainItem, Integer> itemId;
    @FXML
    private TableColumn<MainItem, String> title;
    @FXML
    private TableColumn<MainItem, String> time_create;
    @FXML
    private TableColumn<MainItem, String> modify_item;
    @FXML
    private TableColumn<MainItem, String> type;
    @FXML
    private TableColumn<MainItem, String> status;
    @FXML
    private TableColumn<MainItem, String> assign;
    @FXML
    private ChoiceBox<String> itemChoiceBox;
    @FXML
    private Button createButton;
    @FXML
    private TextField filterInput;
    @FXML
    private ChoiceBox<Integer> choiceYear;
    @FXML
    private BarChart<String, Integer> barReqs;
    @FXML
    private PieChart pieTest;
    public void helloTitle(User u) {
        user = u;
        nameLabel.setText("Добро пожаловать, " + user.getLogin());
    }

    public void tableFill(PostgreConnection post) {
        postgreConnection = post;
        ObservableList<MainItem> mainItems = postgreConnection.getMainScreenItems();
        itemId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        time_create.setCellValueFactory(new PropertyValueFactory<>("Time_create"));
        modify_item.setCellValueFactory(new PropertyValueFactory<>("Modify_item"));
        type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        status.setCellValueFactory(new PropertyValueFactory<>("State"));
        assign.setCellValueFactory(new PropertyValueFactory<>("Assign"));
        FilteredList<MainItem> filteredItems = new FilteredList<>(mainItems, s -> true);
        filterInput.textProperty().addListener(obs->{
            String filter = filterInput.getText();
            if(filter == null || filter.length() == 0) {
                filteredItems.setPredicate(s -> true);
            }
            else {
                filteredItems.setPredicate(s -> s.contains(filter));
            }
        });
        mainTable.setItems(filteredItems);
        onTableDoubleClick();

    }

    public void onTableDoubleClick() {
        mainTable.setRowFactory(tv -> {
            TableRow<MainItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    MainItem rowData = row.getItem();
                    String type = rowData.getType();
                    FXMLLoader loader;
                    int id = rowData.getId();
                    switch (type) {
                        case "Requirement":
                            loader = new FXMLLoader(getClass().getResource("RequirementScene.fxml"));
                            try {
                                root = loader.load();
                                RequirementController requirementController = loader.getController();
                                requirementController.initialization(postgreConnection, postgreConnection.findRequirement(rowData.getId(), rowData.getModify_item()), user);

                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Bug":
                            loader = new FXMLLoader(getClass().getResource("BugScene.fxml"));
                            try {
                                root = loader.load();
                                BugController bugController = loader.getController();
                                bugController.initialization(postgreConnection, postgreConnection.findBug(rowData.getId(), rowData.getModify_item()), user);

                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Issue":
                            loader = new FXMLLoader(getClass().getResource("IssueScene.fxml"));
                            try {
                                root = loader.load();
                                IssueController issueController = loader.getController();
                                issueController.initialization(postgreConnection, postgreConnection.findIssue(rowData.getId(), rowData.getModify_item()), user);

                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Task":
                            loader = new FXMLLoader(getClass().getResource("TaskScene.fxml"));
                            try {
                                root = loader.load();
                                TaskController taskController = loader.getController();
                                taskController.initialization(postgreConnection, postgreConnection.findTask(rowData.getId(), rowData.getModify_item()), user);

                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Test Case":
                            loader = new FXMLLoader(getClass().getResource("TestCaseScene.fxml"));
                            try {
                                root = loader.load();
                                TestCaseController testCaseController = loader.getController();
                                testCaseController.initialization(postgreConnection, postgreConnection.findTestCase(rowData.getId(), rowData.getModify_item()), user, "MainScene.fxml");

                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Test Plan":
                            loader = new FXMLLoader(getClass().getResource("TestPlanScene.fxml"));
                            try {
                                root = loader.load();
                                TestPlanController testPlanController = loader.getController();
                                testPlanController.initialization(postgreConnection, postgreConnection.findTestPlan(rowData.getId(), rowData.getModify_item()), user);

                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            });
            return row;
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] items = {"Requirement", "Bug", "Issue", "Task", "Test Case", "Test Plan"};
        itemChoiceBox.getItems().addAll(items);
        itemChoiceBox.setValue("Requirement");

        Integer[] years = {2022};
        choiceYear.getItems().addAll(years);
        choiceYear.setValue(2022);


    }

    @FXML
    public void chartsInit(PostgreConnection post){
        postgreConnection = post;
        barReqs.setTitle("Выполнено требований за год");
        CategoryAxis xaxis= new CategoryAxis();
        NumberAxis yaxis = new NumberAxis();
        xaxis.setLabel("Месяц");
        yaxis.setLabel("Выполнено требований");

        XYChart.Series<String,Integer> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data("Январь",postgreConnection.countReqs(1,choiceYear.getValue())));
        System.out.println(postgreConnection.countReqs(1,choiceYear.getValue()));
        series.getData().add(new XYChart.Data("Февраль",postgreConnection.countReqs(2,choiceYear.getValue())));
        series.getData().add(new XYChart.Data("Март",postgreConnection.countReqs(3,choiceYear.getValue())));
        series.getData().add(new XYChart.Data("Апрель",postgreConnection.countReqs(4,choiceYear.getValue())));

        series.getData().add(new XYChart.Data("Май",postgreConnection.countReqs(5,choiceYear.getValue())));
        series.getData().add(new XYChart.Data("Июнь",postgreConnection.countReqs(6,choiceYear.getValue())));
        series.getData().add(new XYChart.Data("Июль",postgreConnection.countReqs(7,choiceYear.getValue())));
        series.getData().add(new XYChart.Data("Август",postgreConnection.countReqs(8,choiceYear.getValue())));

        series.getData().add(new XYChart.Data("Сентябрь",postgreConnection.countReqs(9,choiceYear.getValue())));
        series.getData().add(new XYChart.Data("Октябрь",postgreConnection.countReqs(10,choiceYear.getValue())));
        series.getData().add(new XYChart.Data("Ноябрь",postgreConnection.countReqs(11,choiceYear.getValue())));
        series.getData().add(new XYChart.Data("Декабрь",postgreConnection.countReqs(12,choiceYear.getValue())));

        barReqs.getData().add(series);
        barReqs.setLegendVisible(false);

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Провален", postgreConnection.getTestResultsFail()),
                new PieChart.Data("Не пройден", postgreConnection.getTestResultsNotPass()),
                new PieChart.Data("Пройден", postgreConnection.getTestResultsPass())


        );
        pieTest.setData(data);

    }

    public void onCreateButtonClick(ActionEvent event) {
        FXMLLoader loader;
        String type=itemChoiceBox.getValue();
        switch (type) {
            case "Requirement":
                loader = new FXMLLoader(getClass().getResource("RequirementScene.fxml"));
                try {
                    root = loader.load();
                    RequirementController requirementController = loader.getController();
                    requirementController.initialization(postgreConnection, new Requirement(), user);
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Bug":
                loader = new FXMLLoader(getClass().getResource("BugScene.fxml"));
                try {
                    root = loader.load();
                    BugController bugController = loader.getController();
                    bugController.initialization(postgreConnection, new Bug(), user);
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Issue":
                loader = new FXMLLoader(getClass().getResource("IssueScene.fxml"));
                try {
                    root = loader.load();
                    IssueController issueController = loader.getController();
                    issueController.initialization(postgreConnection,new Issue(), user);
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Task":
                loader = new FXMLLoader(getClass().getResource("TaskScene.fxml"));
                try {
                    root = loader.load();
                    TaskController taskController = loader.getController();
                    taskController.initialization(postgreConnection, new Task(), user);
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Test Case":
                loader = new FXMLLoader(getClass().getResource("TestCaseScene.fxml"));
                try {
                    root = loader.load();
                    TestCaseController testCaseController = loader.getController();
                    testCaseController.initialization(postgreConnection, new TestCase(), user, "MainScene.fxml");
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Test Plan":
                loader = new FXMLLoader(getClass().getResource("TestPlanScene.fxml"));
                try {
                    root = loader.load();
                    TestPlanController testPlanController = loader.getController();
                    testPlanController.initialization(postgreConnection, new TestPlan(), user);
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}

