package project_system.org;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AssignedTaskController {

    @FXML
    private ImageView ADD_EMPLOYEE;

    @FXML
    private Label Goodmorninggreet;

    @FXML
    private Text adminPosition;

    @FXML
    private Button btnAssigned;

    @FXML
    private Button btnCREATETASK;

    @FXML
    private Button btnHOME;

    @FXML
    private Button btnPERFORAMNCE;

    @FXML
    private Button btnPRINT;

    @FXML
    private Text dategreet;

    @FXML
    private ComboBox<String> department_list;

    @FXML
    private Label label_PDF1;

    @FXML
    private Label label_PDF2;

    @FXML
    private Label label_PDF3;

    @FXML
    private Label label_PDF4;

    @FXML
    private Label label_PDF5;

    @FXML
    private DatePicker myCalendar;

    @FXML
    private TextArea text_instruction;

    @FXML
    private TextField text_tittle;

    @FXML
    private ComboBox<String> AssignedEmployee;

    @FXML
    private Text timegreet;

    @FXML
    private Label txtadminName;
    
    @FXML
    private Label Adminname;

    private File selectedFile;

    private final String DB_URL = "jdbc:mysql://localhost:3306/pomsdb";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "luese_192003";

    public void initialize() {

        

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        LocalDateTime now = LocalDateTime.now();
        dategreet.setText(dtf.format(now));

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, (javafx.event.ActionEvent e) -> {
            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("h:mm:ss");
            LocalDateTime now1 = LocalDateTime.now();
            timegreet.setText(dtf1.format(now1));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    if (Time.valueOf(LocalDateTime.now().toLocalTime()).after(Time.valueOf(LocalTime.parse("00:00:00"))) && 
        Time.valueOf(LocalDateTime.now().toLocalTime()).before(Time.valueOf(LocalTime.parse("12:00:00")))) {
        Goodmorninggreet.setText("Good Morning, " + txtadminName.getText());
    } else if (Time.valueOf(LocalDateTime.now().toLocalTime()).before(Time.valueOf(LocalTime.parse("18:00:00")))) {
        Goodmorninggreet.setText("Good Afternoon, " + txtadminName.getText());
    } else {
        Goodmorninggreet.setText("Good Evening, " + txtadminName.getText());
    }
    
        populateDepartment();
        populateEmployee();
    }




       @FXML
        void btnCREATETASK(MouseEvent event) throws SQLException {
      
    }
        
    @FXML
    void btnClickedAssigned(MouseEvent event) {
        String title = text_tittle.getText();
        String instruction = text_instruction.getText();
        String Assignedto = AssignedEmployee.getValue(); 
        String date = myCalendar.getValue().toString();
        String status = "Pending";
        String forEmployee = department_list.getValue();



        if (Assignedto.isEmpty() || date.isEmpty()) {
            showAlert("Error", "Please fill in all the fields.");
        } else {
            String insertquery = "INSERT INTO taskdb (Title, Instruction, AssignedTo, Due, `For`, File, FileData, FileType, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement insertStatement = connection.prepareStatement(insertquery);
                 FileInputStream fis = new FileInputStream(selectedFile)) {
    
                insertStatement.setString(1, title);
                insertStatement.setString(2, instruction);
                insertStatement.setString(3, Assignedto);
                insertStatement.setString(4, date);
                insertStatement.setString(5, forEmployee);
                insertStatement.setString(6, selectedFile.getName());
                insertStatement.setBinaryStream(7, fis, (int) selectedFile.length());
                insertStatement.setString(8, getFileType(selectedFile));
                insertStatement.setString(9, status);
    
                int rowsAffected = insertStatement.executeUpdate();
    
                if (rowsAffected > 0) {
                    showAlert("Success", "Task assigned successfully.");
                } else {
                    showAlert("Error", "An error occurred. Please try again.");
                }
            }catch (SQLIntegrityConstraintViolationException e) {
               showAlert("Error", "Duplicate entry. The task already exists.");
          
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred. Please try again.");
            }
        }
    }
     
    @FXML
    void btnHOME(MouseEvent event) throws IOException {
        App.setRoot("AdminDashboard", 1280, 800);
    }

    @FXML
    void btnLink(MouseEvent event) {
        // Implement your logic here
    }

    @FXML
    void btnPERFORAMANCE(MouseEvent event) {
        // Implement your logic here
    }

    @FXML
    void btnPRINT(MouseEvent event) {
        // Implement your logic here
    }


    @FXML
    void btnUpload(MouseEvent event) {                    //uploading file button
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            label_PDF1.setText(selectedFile.getAbsolutePath());
        }
    }

    private String getFileType(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return "application/" + fileName.substring(dotIndex + 1);
        }
        return "application/octet-stream";
    }
    @FXML
    public void populateDepartment() {
        String query = "SELECT ProjectTittle FROM projectname";                                      //populate department list
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ObservableList<String> departmentNames = FXCollections.observableArrayList();
            while (resultSet.next()) {
                departmentNames.add(resultSet.getString("ProjectTittle"));
            }
            department_list.setItems(departmentNames);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
             
    @FXML                                                           //populate employee list
    public void populateEmployee() {
        String query = "SELECT FullName FROM employeedb";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ObservableList<String> employeeNames = FXCollections.observableArrayList();
            while (resultSet.next()) {
                employeeNames.add(resultSet.getString("FullName"));
            }
            AssignedEmployee.setItems(employeeNames);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);      //alert message
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}









/*import java.io.File;


public class FileUpload {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/yourdatabase";
    private static final String DB_USER = "yourusername";
    private static final String DB_PASSWORD = "yourpassword";

    public static void main(String[] args) {
        File file = new File("path/to/your/file.pdf");
        String fileType = "application/pdf"; // Change this according to the file type
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             FileInputStream fis = new FileInputStream(file)) {
            
            String sql = "INSERT INTO documents (filename, filedata, filetype) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, file.getName());
            pstmt.setBinaryStream(2, fis, (int) file.length());
            pstmt.setString(3, fileType);

            int row = pstmt.executeUpdate();
            if (row > 0) {
                System.out.println("File uploaded successfully.");
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
 */