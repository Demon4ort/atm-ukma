package WindowControllers;


import Storage.Charity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CharityController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button animals;

    @FXML
    private Button ato;

    @FXML
    private Button kids;

    @FXML
    private Button oncology;

    @FXML
    private Button quit;

    @FXML
    void initialize() {

        List<Button> buttons = new ArrayList<>() {{
            add(animals);
            add(ato);
            add(kids);
            add(oncology);
        }};
        for (Button button : buttons) {
            Charity.setStatus(button.getText());
            button.setOnMouseClicked(mouseEvent -> {
                Stage stage = (Stage) quit.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Frontend/charityTransaction.fxml"));
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
