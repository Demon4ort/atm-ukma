package WindowControllers;


import Storage.CardInfo;
import example.service.BalanceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPutMoney {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button put;

    @FXML
    private Button quit;

    @FXML
    private TextField sum;

    @FXML
    private Text status;

    @FXML
    void initialize() {

        BalanceManager bm = new BalanceManager();

        put.setOnMouseClicked(mouseEvent -> {
            try {
                double value = Double.parseDouble(sum.getText());
                if (value > 0) {
                    bm.updateBalance(CardInfo.getCard().value() + value);
                    status.setText("Money added!");
                } else {
                    status.setText("Please, print correct value");
                }
            } catch (NumberFormatException ex) {
                status.setText("Please, print numeric value");
            }

        });


        quit.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) quit.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Frontend/MainMenu.fxml"));
            Parent root1 = null;
            try {
                root1 = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Menu card");
            stage.setScene(new Scene(root1));
        });


    }

}
