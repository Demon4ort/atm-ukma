package WindowControllers;

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
import java.util.ResourceBundle;

public class ControllerRegistration {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea login_field;

    @FXML
    private PasswordField pass_field;

    @FXML
    private PasswordField pass_field_2;

    @FXML
    private Button reg;


    @FXML
    private Text status;

    @FXML
    private Button sign;


    @FXML
    void initialize() {
        AuthService authService = new AuthService();

        sign.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) sign.getScene().getWindow();
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


        reg.setOnMouseClicked(Event -> {
            String pass = pass_field.getText();
            String login = login_field.getText();
            if (login.equals("") || pass.equals("")) {
                status.setText("input login and password");
            } else {
                if (!pass.equals(pass_field_2.getText())) {
                    status.setText("passwords don't match");
                } else {
                    try {
                        authService.register(login_field.getText(), pass_field.getText());
                        status.setText("account created");
                    } catch (AuthService.UserAlreadyExistsException e) {
                        status.setText(e.getMessage());
                    }
                }
            }
        });
    }
}


