package project_system.org;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.SwingUtilities;

<<<<<<< HEAD
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML; //calendar import -- - --- - -- -
=======
import javafx.fxml.FXML;
>>>>>>> 7e3302fc3c36e072d611c9a83bd01ac4883004e5
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class EmployeeController {

    
    private Tab TAB_FINISHED;

    @FXML
    private Tab TAB_MISSING;

    @FXML
    private Tab TAB_PENDINGS;

    @FXML
    private Button btn_Calendar;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_performance;

    @FXML
    private Button btn_settings;

    @FXML
    private Button btn_logout;

    @FXML
    private ImageView profile;

    @FXML
    private TabPane tabALL;

    @FXML
    private Tab tab_task;

    @FXML
    private AnchorPane tabtask;

    @FXML
    private AnchorPane tabfinished;
    
    @FXML
    private Label text_date;

    @FXML
    private Label text_date1;

    @FXML
    private Label text_department;

    @FXML
    private Label text_greet1;

    @FXML
    private Label text_name;

    @FXML
    private Label text_time;
    private File selectedFiles;
    private File selectedFile;


    private final String DB_URL = "jdbc:mysql://localhost:3306/pomsdb";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "AMANTE12345";


    @FXML  //java fx button to display the calendar
    void Clicked_Calendar(MouseEvent event) 
    { SwingUtilities.invokeLater(() -> { 
        Calendar calendarFrame = new Calendar();
        calendarFrame.setVisible(true); });
    }// calendar click button ------

    @FXML
    void Clicked_Home(MouseEvent event) {

    }

    @FXML
    void Clicked_Performance(MouseEvent event) {

    }

    @FXML
    void Clicked_Settings(MouseEvent event) {

    }

    @FXML
    void Clicked_Logout(MouseEvent event) {
        logout();
    }


private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

<<<<<<< HEAD
    public void initialize() { // Initialize the Employee Dashboard
        setTask();
        fetchTasks();
    }
















=======
public void initialize() {
    setTask();
    setFinishedTasks(); // Initialize finished tasks
}

private void setFinishedTasks() {
    VBox vbox = new VBox();
    vbox.setSpacing(10);
    vbox.setPadding(new Insets(20, 20, 20, 20));
    vbox.setPrefWidth(500);
    vbox.setPrefHeight(800);
    vbox.setLayoutX(20);
    vbox.setLayoutY(20);

    List<Task> finishedTaskList = fetchFinishedTasks(); // Fetch tasks from finished database

    for (Task task : finishedTaskList) {
        VBox taskBox = new VBox();
        taskBox.setSpacing(15);
        taskBox.setPadding(new Insets(10, 10, 10, 10));
        taskBox.setStyle("-fx-background-color: white");
        taskBox.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Label titleLabel = new Label(task.getTitle());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label dateLabel = new Label(task.getDatePosted() + (task.getDatePosted() != null ? "- (" + task.getAssigndTo() + ")" : ""));
        dateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        dateLabel.setTextFill(Color.GRAY);

        taskBox.getChildren().addAll(titleLabel, dateLabel);

        // Make the title clickable to display task details
        titleLabel.setOnMouseClicked(e -> {
            List<Map<String, Object>> taskInfo = fetchtaskInfoTask();
            for (Map<String, Object> taskData : taskInfo) {
                if (taskData.get("Title").equals(task.getTitle())) {
                    tabfinished.getChildren().removeIf(node -> node.getId() != null && node.getId().startsWith("taskDetail"));

                    VBox taskDetailsBox = new VBox(20);
                    taskDetailsBox.setLayoutX(560);
                    taskDetailsBox.setLayoutY(40);
                    taskDetailsBox.setId("taskDetailBox");

                    Label title = new Label("" + taskData.get("Title"));
                    title.setFont(Font.font("Garet", FontWeight.BOLD, 20));
                    title.setId("taskDetailTitle");

                    Label due = new Label("Due: " + taskData.get("Due"));
                    due.setFont(Font.font("Garet", FontWeight.BOLD, 14));
                    due.setId("taskDetailDue");

                    Label instruction = new Label("" + taskData.get("Instruction"));
                    instruction.setFont(Font.font("Garet", FontWeight.NORMAL, 15));
                    instruction.setId("taskDetailInstruction");

                    Label fileType = new Label("File Type: " + taskData.get("FileType"));
                    fileType.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
                    fileType.setId("taskDetailFileType");


                    taskDetailsBox.getChildren().addAll(title, due, instruction, fileType);
                    tabfinished.getChildren().add(taskDetailsBox);
                }
            }
        });

        vbox.getChildren().add(taskBox);
    }

    ScrollPane scrollPane = new ScrollPane(vbox);
    tabfinished.getChildren().add(scrollPane);
}

private List<Task> fetchFinishedTasks() { // method to fetch tasks from the finished database
    String query = "SELECT Title, Due, AssignedTo FROM finisheddb";
    List<Task> finishedTaskList = new ArrayList<>();

    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String title = rs.getString("Title");
            String datePosted = rs.getString("Due");
            String assigned = rs.getString("AssignedTo");
            finishedTaskList.add(new Task(title, datePosted, assigned));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return finishedTaskList;
}



        
    
>>>>>>> 7e3302fc3c36e072d611c9a83bd01ac4883004e5

    public void setTask() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.setPrefWidth(500);
        vbox.setPrefHeight(800);
        vbox.setLayoutX(20);
        vbox.setLayoutY(20);

        List<Task> taskList = fetchTasks(); // Fetch tasks from database

        for (Task task : taskList) {
            VBox taskBox = new VBox();
            taskBox.setSpacing(15);
            taskBox.setPadding(new Insets(10, 10, 10, 10));
            taskBox.setStyle("-fx-background-color: white");
            taskBox.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


            Label titleLabel = new Label(task.getTitle());
            titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            Label dateLabel = new Label(task.getDatePosted() + (task.getDatePosted() != null ? "- (" + task.getAssigndTo() + ")" : ""));
            dateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            dateLabel.setTextFill(Color.GRAY);

            taskBox.getChildren().addAll(titleLabel, dateLabel);


    titleLabel.setOnMouseClicked(e -> {  // Display task details when title is clicked
    List<Map<String, Object>> taskInfo = fetchtaskInfoTask();
    for (Map<String, Object> taskData : taskInfo) {
        if (taskData.get("Title").equals(task.getTitle())) {
            // Remove existing task detail nodes if they exist
            tabtask.getChildren().removeIf(node -> node.getId() != null && node.getId().startsWith("taskDetail"));
            

            VBox taskDetailsBox = new VBox(20); // 10 is the spacing between elements
            taskDetailsBox.setLayoutX(560);
            taskDetailsBox.setLayoutY(40);
            taskDetailsBox.setId("taskDetailBox");

            Label title = new Label("" + taskData.get("Title"));
            title.setFont(Font.font("Garet", FontWeight.BOLD, 20));
            title.setId("taskDetailTitle");
        

            Label due = new Label("Due: " + taskData.get("Due"));
            due.setFont(Font.font("Garet", FontWeight.BOLD, 14));
            due.setId("taskDetailDue");

            Label instruction = new Label("" + taskData.get("Instruction"));
            instruction.setFont(Font.font("Garet", FontWeight.NORMAL, 15));
            instruction.setId("taskDetailInstruction");
            

            Label fileType = new Label("File Type: " + taskData.get("File"));
            fileType.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
            fileType.setId("taskDetailFileType");


            Button Submit = new Button("Add or Create");
            Submit.setFont(Font.font("Garet", FontWeight.BOLD, 14));
            Submit.setId("taskDetailSubmit");
            Submit.setLayoutX(600);


            Button MarkAsDone = new Button("Mark as Done");
            MarkAsDone.setFont(Font.font("Garet", FontWeight.BOLD, 14));
            MarkAsDone.setId("taskDetailMarkAsDone");
            MarkAsDone.setLayoutX(600);

        
            Submit.setOnAction(event -> {
                String insertquery = "INSERT INTO taskdb (Title, Instruction, `For`, AssignedTo, Due, File, FileType, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    PreparedStatement stmt = conn.prepareStatement(insertquery)) {
                    stmt.setString(1, title.getText());
                    stmt.setString(2, instruction.getText());
                    stmt.setString(3, "For");
                    stmt.setString(4, "AssignedTo");
                    stmt.setString(5, due.getText());
                    stmt.setBytes(6, (byte[]) taskData.get("File"));
                    stmt.setString(7, fileType.getText());
                    stmt.setString(8, "Pending");
                    stmt.executeUpdate();
                } catch (SQLException ex) {
                }
            });

            MarkAsDone.setOnAction(event -> {
                System.out.println("Mark as Done button clicked");
<<<<<<< HEAD
            
                String insertQuery = "INSERT INTO finisheddb (id, Title, Instruction, `For`, AssignedTo, Due, File, FileData, FileType, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                String deleteQuery = "DELETE FROM taskdb WHERE Title = ?";
            
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                     PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
            
                    int id = generateId();
                    insertStmt.setInt(1, id);
                    insertStmt.setString(2, taskData.get("Title").toString());
                    insertStmt.setString(3, taskData.get("Instruction").toString());
                    insertStmt.setString(4, taskData.get("For").toString());
                    insertStmt.setString(5, taskData.get("AssignedTo").toString());
                    
                    String dueDate = taskData.get("Due").toString();
                    insertStmt.setString(6, dueDate);
                    
                    insertStmt.setBytes(7, (byte[]) taskData.get("File"));
                    insertStmt.setString(8, taskData.get("FileData") != null ? taskData.get("FileData").toString() : "");
                    insertStmt.setString(9, taskData.get("FileType").toString());
                    insertStmt.setString(10, "Completed");
            
                    insertStmt.executeUpdate();
            
                    // Delete the task from taskdb
                    deleteStmt.setString(1, taskData.get("Title").toString());
                    deleteStmt.executeUpdate();
            
                    // Remove task details from the tab and task from the list
                    tabtask.getChildren().remove(taskDetailsBox);
                    vbox.getChildren().remove(taskBox);
                    tabfinished.getChildren().add(taskBox); // Add task to finished VBox
            
                    showAlert("Task marked as done and moved to finished tasks.");
                } catch (SQLException ex) {
                    System.err.println("Error updating task: " + ex.getMessage());
                    showAlert("Error updating task: " + ex.getMessage());
                }
            });
            
            // Add task details to the task details box and add it to the tab
            taskDetailsBox.getChildren().addAll(title, due, instruction, fileType, Submit, MarkAsDone);
            tabtask.getChildren().add(taskDetailsBox);
=======
            
                // Checking if taskData is not null and contains 'Title'
                if (taskData == null || !taskData.containsKey("Title")) {
                    System.out.println("Task data is missing or does not contain 'Title'");
                    return;
                }
            
                Object titleObj = taskData.get("Title");
                if (titleObj == null || !(titleObj instanceof String)) {
                    System.out.println("'Title' is null or not a string");
                    return;
                }
            
                String taskTitle = titleObj.toString();
                String taskInstruction = taskData.getOrDefault("Instruction", "No instructions provided").toString();
                String updateQuery = "UPDATE taskdb SET Status = 'Done' WHERE Title = ?";
                String insertQuery = "INSERT INTO finisheddb (Title, Instruction) VALUES (?, ?)";
                String deleteQuery = "DELETE FROM taskdb WHERE Title = ?";
            
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                     PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                     PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
            
                    // Checking if the connection is valid
                    if (conn == null || conn.isClosed()) {
                        System.out.println("Failed to connect to the database");
                        return;
                    }
            
                    // Update the task status
                    updateStmt.setString(1, taskTitle);
                    int rowsAffected = updateStmt.executeUpdate();
            
                    if (rowsAffected > 0) {
                        System.out.println("Task '" + taskTitle + "' status updated to Done in database");
            
                        // Insert the completed task into finisheddb with an Instruction value
                        insertStmt.setString(1, taskTitle);
                        insertStmt.setString(2, taskInstruction);
                        insertStmt.executeUpdate();
                        System.out.println("Task '" + taskTitle + "' transferred to finisheddb with instruction: " + taskInstruction);
            
                        // Delete the task from taskdb
                        deleteStmt.setString(1, taskTitle);
                        deleteStmt.executeUpdate();
                        System.out.println("Task '" + taskTitle + "' removed from taskdb");
            
                        // Move task between VBoxes
                        tabtask.getChildren().remove(taskBox); // Remove task from current VBox
                        tabfinished.getChildren().add(taskBox); // Add task to finished VBox
                    } else {
                        System.out.println("No rows were updated for task '" + taskTitle + "'");
                    }
                } catch (SQLException ex) {
                    System.err.println("SQL error when updating task '" + taskTitle + "': " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
            
            

                            taskDetailsBox.getChildren().addAll(title, due, instruction, fileType, Submit, MarkAsDone);
                            tabtask.getChildren().add(taskDetailsBox);

                        }
                    }
                }); 
                
                        vbox.getChildren().add(taskBox);
            }
                
                        ScrollPane scrollPane = new ScrollPane(vbox);
                        tabtask.getChildren().add(scrollPane);
>>>>>>> 7e3302fc3c36e072d611c9a83bd01ac4883004e5
        }
    }
});

// Add task box to the VBox
vbox.getChildren().add(taskBox);
}

// Create a scroll pane and add the VBox to it, then add the scroll pane to the tab
ScrollPane scrollPane = new ScrollPane(vbox);
tabtask.getChildren().add(scrollPane);
}

    
                
        private List<Task> fetchTasks() {  //method to fetch task from database
        String query = "SELECT Title, Due, AssignedTo FROM taskdb";
        List<Task> taskList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("Title");
                String datePosted = rs.getString("Due");
            String Assigned = rs.getString("AssignedTo");
                taskList.add(new Task(title, datePosted, Assigned ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taskList;
    }

    class Task {
        private String title;
        private String datePosted;
        private String Assigned;

        public Task(String title, String datePosted, String AssignedTo) {
            this.title = title;
            this.datePosted = datePosted;
            this.Assigned = AssignedTo;
        }

        public String getTitle() {
            return title;
        }

        public String getDatePosted() {
            return datePosted;
        }

        public String getAssigndTo() {
            return Assigned;
        }
    }

    public List<Map<String, Object>> fetchtaskInfoTask() {
        String query = "SELECT Title, Instruction, `For`, AssignedTo, Due, File, FileType, Status FROM taskdb";
        List<Map<String, Object>> taskList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> taskData = new HashMap<>();
                


                taskList.add(taskData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    public void selectDoneFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            //label_PDF1.setText(selectedFile.getAbsolutePath());
        }
    }

    public void logout() {
        // Show confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Logout");
        confirmationAlert.setHeaderText("Are you sure you want to log out?");
        confirmationAlert.setContentText("Click Yes to log out or No to stay on this screen.");
    
        // Show the dialog and wait for the user's response
        Optional<ButtonType> result = confirmationAlert.showAndWait();
    
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Navigate back to the login screen
                App.setRoot("Login", 470, 520);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to navigate to the login screen.");
            }
        } else {
            // User selected No or closed the dialog
            System.out.println("Logout canceled.");
        }
    }

     private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Task Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private int generateId() {
        String query = "SELECT MAX(id) AS max_id FROM finisheddb";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Return 1 if there are no entries in the table
    }

      public void initialize() throws SQLException {
        // Initialize the table columns
        txt5TaskName.setCellValueFactory(new PropertyValueFactory<>("TaskName"));
        txt5Discreption.setCellValueFactory(new PropertyValueFactory<>("Discreption"));
        txt5AssDate.setCellValueFactory(new PropertyValueFactory<>("AssDate"));
        txt5DueDate.setCellValueFactory(new PropertyValueFactory<>("DueDate"));
        txt5Department.setCellValueFactory(new PropertyValueFactory<>("Department"));
        txt5FinishedDate.setCellValueFactory(new PropertyValueFactory<>("FinishedDate"));
        txt5Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
    
        // Set up the animation
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> refreshTable()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void refreshTable() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM finishedtask");
            ResultSet resultSet = preparedStatement.executeQuery();
    
            ObservableList<FinishedTask> finishedTasks = FXCollections.observableArrayList();
    
            while (resultSet.next()) {
                finishedTasks.add(new FinishedTask(
                        resultSet.getString("Taskname"),
                        resultSet.getString("Discreption"),
                        resultSet.getString("AssignedDate"),
                        resultSet.getString("DueDate"),
                        resultSet.getString("Department"),
                        resultSet.getString("FinishedDate"),
                        resultSet.getString("Status")
                        
                ));
            }
    
            txtbotbots.setItems(finishedTasks);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void db() {
        
    }
}
            
 









