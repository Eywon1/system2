package project_system.org;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class RegisterController {

    @FXML
    private Button btnSignup;

    @FXML
    private TextField txtR3;

    @FXML
    private PasswordField txtR4;

    @FXML
    private PasswordField txtR5;

    @FXML
    private ImageView btnBACK;

    @FXML
    void btnSignUp(MouseEvent event) {

    }

    
    @FXML
    void btnBACKCLICKED(MouseEvent event) throws IOException {
     App.setRoot("Login", 500, 600);
    }
}