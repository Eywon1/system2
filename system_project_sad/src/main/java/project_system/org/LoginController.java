package project_system.org;

import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class LoginController {

    @FXML
    private Button btnR;

    @FXML
    private ImageView btnr;

    @FXML
    private Button button1;

    @FXML
    private TextField txtemail;

    @FXML
    private PasswordField txtpass;

    // DriverManager.registerDriver(new com.mysql.cj.jdbc.Drver());

    private final String DB_URL = "jdbc:mysql://localhost:3306/pomsdb";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "luese_192003";

    @FXML
    void btn1(MouseEvent event) throws Exception {
        String email = txtemail.getText();
        String password = txtpass.getText();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (performLogin(connection, email, password, "admindb")) {
                showAlert("Login Successful, Welcome, Admin " + email + "!");
                App.setRoot("AdminDashboard",1280, 700);
                Node source = (Node) event.getSource();
                if (source.getScene() != null) {
                    source.getScene().getWindow().hide();
                }
            } else if (performLogin(connection, email, password, "employeedb")) {
                showAlert("Login Successful, Welcome, " + email + "!");
                App.setRoot("EmployeeDashboard", 1280, 700);
            } else {
                showAlert("Login Failed");
            }
        } catch (SQLException e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean performLogin(Connection connection, String email, String password, String tableName)
            throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE Email = ? AND Password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @FXML
    void btnR(MouseEvent event) throws IOException {
        App.setRoot("Register", 470, 520);
    }

    @FXML
    void btnr(MouseEvent event) throws IOException {
        App.setRoot("Register", 470, 520);
    }

    /*
     * try{
     * Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
     * 
     * System.out.println("Connected to mysql");
     * } catch (SQLException e) {
     * System.out.println("tangina mo error:" + e.getMessage());
     * }
     */
}
