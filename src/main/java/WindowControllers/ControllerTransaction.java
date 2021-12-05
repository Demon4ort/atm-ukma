package WindowControllers;


import Storage.CardInfo;
import example.service.TransactionManager;
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
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class ControllerTransaction {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField number;

    @FXML
    private Button quit;

    @FXML
    private TextField sum;

    @FXML
    private Button transfer;

    @FXML
    private Text status;


    @FXML
    void initialize() {
        TransactionManager tm = new TransactionManager();
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

        transfer.setOnMouseClicked(mouseEvent -> {
            if (sum.getText().equals("") || number.getText().equals("")) {
                status.setText("input sum and card id");
            } else {
                double sum_sum = Double.parseDouble(sum.getText());
                int card_num = Integer.parseInt(number.getText());
                System.out.println(number.getText());
                if (sum_sum > CardInfo.getCard().value()) {
                    status.setText("not enough money");
                } else if (sum_sum <= 0) {
                    status.setText("cant do - or 0  transaction ");
                } else if (card_num == (int) CardInfo.getCard().id().get()) {
                    status.setText("input another card");
                } else {
                    try {
                        tm.transaction(card_num, sum_sum);
                        status.setText("You sent money!");
                    } catch (NoSuchElementException e) {
                        status.setText(e.getMessage());
                    }
                }
            }

        });
    }

}
