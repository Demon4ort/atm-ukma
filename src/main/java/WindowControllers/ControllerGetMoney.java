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

public class ControllerGetMoney {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button get;

    @FXML
    private Button quit;

    @FXML
    private Text status;

    @FXML
    private TextField sum;

    @FXML
    void initialize() {

        BalanceManager bm = new BalanceManager();

        get.setOnMouseClicked(mouseEvent -> {
            try {
                double value = Double.parseDouble(sum.getText());
                double cardValue = CardInfo.getCard().value();
                if (value > cardValue) {
                    status.setText("Not enough money");
                } else {
                    bm.updateBalance(cardValue - value);
                    status.setText("Money get!");
                }
            } catch (NumberFormatException ex) {
                status.setText("please, input numeric value");
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
