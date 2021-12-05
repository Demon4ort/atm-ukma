package WindowControllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerMain {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Balance;

    @FXML
    private Button charity;

    @FXML
    private Button credit;

    @FXML
    private Button get;

    @FXML
    private Button put;

    @FXML
    private Button quit;

    @FXML
    private Button transaction;

    @FXML
    void initialize() {

        quit.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) quit.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Frontend/auth.fxml"));
            Parent root1 = null;
            try {
                root1 = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Menu card");
            stage.setScene(new Scene(root1));
        });
        Balance.setOnMouseClicked(mouseEvent -> {

            Stage stage = (Stage) Balance.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Frontend/balance.fxml"));
            Parent root1 = null;
            try {
                root1 = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Menu balance");
            stage.setScene(new Scene(root1));
        });

        transaction.setOnMouseClicked(mouseEvent -> {

            Stage stage = (Stage) transaction.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Frontend/transaction.fxml"));
            Parent root1 = null;
            try {
                root1 = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Menu balance");
            stage.setScene(new Scene(root1));


        });

        put.setOnMouseClicked(mouseEvent -> {

            Stage stage = (Stage) Balance.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Frontend/putMoney.fxml"));
            Parent root1 = null;
            try {
                root1 = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Menu balance");
            stage.setScene(new Scene(root1));

        });

        get.setOnMouseClicked(mouseEvent -> {

            Stage stage = (Stage) Balance.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Frontend/getMoney.fxml"));
            Parent root1 = null;
            try {
                root1 = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Menu balance");
            stage.setScene(new Scene(root1));

        });

        charity.setOnMouseClicked(mouseEvent -> {

            Stage stage = (Stage) Balance.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Frontend/charity.fxml"));
            Parent root1 = null;
            try {
                root1 = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Menu balance");
            stage.setScene(new Scene(root1));

        });
    }

}



