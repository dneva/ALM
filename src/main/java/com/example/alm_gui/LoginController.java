package com.example.alm_gui;

import com.example.alm_gui.Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    TextField fieldLogin;
    @FXML
    TextField fieldPassword;
    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        PostgreConnection postgreConnection = new PostgreConnection();
        User user = postgreConnection.findUser(fieldLogin.getText(),fieldPassword.getText());
        if (user.getId()!=0)
        {
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
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Пользователь не найден");
            alert.setContentText("Проверьте правильность пароля и логина");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }
}