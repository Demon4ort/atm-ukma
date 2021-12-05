package WindowControllers;

import Storage.CardInfo;
import example.entity.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BalanceController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button quit;

    @FXML
    private Text balance;

    @FXML
    void initialize() {
        Model.Card card = CardInfo.getCard();
        balance.setText(String.valueOf(card.value()));

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
