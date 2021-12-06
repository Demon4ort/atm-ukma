package WindowControllers;

import example.entity.Model;
import example.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class ControllerAuth {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button log;

    @FXML
    private TextArea login_field;

    @FXML
    private PasswordField pass_field;

    @FXML
    private Button reg;
    @FXML
    private Text status;


    @FXML
    void initialize() {
        AuthService authService = new AuthService();
        log.setOnMouseClicked(mouseEvent -> {
            Model.User user = null;
            try {
                user = authService.login(login_field.getText(), pass_field.getText());
                System.out.println(user);
            } catch (NoSuchElementException exception) {
                status.setText(exception.getMessage());
            } catch (AuthService.WrongPasswordException exception) {
                status.setText("Wrong password");
            }
            if (user != null) {
                Stage stage = (Stage) log.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Frontend/MainMenu.fxml"));
                Parent root1 = null;
                try {
                    root1 = (Parent) fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setTitle("Menu card");
                stage.setScene(new Scene(root1));
            }
        });


        reg.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) log.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Frontend/registration.fxml"));
            Parent root1 = null;
            try {
                root1 = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Menu card");
            stage.setScene(new Scene(root1));
        });

    }
}
