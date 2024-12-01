package project_system.org;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminController {

    @FXML
    private TableView<Task> TB_Table;

    @FXML
    private Label Adminname;

    @FXML
    private Label Goodmorninggreet;

    @FXML
    private Label adminPosition;

    @FXML
    private Button btnCREATETASK;

    @FXML
    private Button btnHOME;

    @FXML
    private Button btnPERFORAMNCE;

    @FXML
    private Button btnPRINT;

    @FXML
    private Label dategreet;

    @FXML
    private Button BTN_CREATEPROJECT;

    @FXML
    private AnchorPane TabPROJECT;

    @FXML
    private Label timegreet;

    @FXML
    private ComboBox<String> C_BOX;

    @FXML
    private ProgressIndicator TF_FinishedTask;

    @FXML
    private ProgressIndicator TM_Missing;

    @FXML
    private ProgressIndicator TP_Pendings;

    @FXML
    private VBox vb_Table;

    @FXML
    private ScrollPane scrollPane; // Add this line

    private final String DB_URL = "jdbc:mysql://localhost:3306/pomsdb";
    private final String DB_USER ="root";
    private final String DB_PASSWORD = "eywon_1";

    public void initialize() {
        String css = this.getClass().getResource("/project_system/org/projectStyle.css").toExternalForm();

        // Apply the CSS file to the TabPane
        TabPROJECT.getStylesheets().add(css);


        populateProjects();
        loadTasksForSelectedProject();
    
    
    

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
        Goodmorninggreet.setText("Good Morning, " + Adminname.getText());
    } else if (Time.valueOf(LocalDateTime.now().toLocalTime()).before(Time.valueOf(LocalTime.parse("18:00:00")))) {
        Goodmorninggreet.setText("Good Afternoon, " + Adminname.getText());
    } else {
        Goodmorninggreet.setText("Good Evening, " + Adminname.getText());
    }

       BTN_CREATEPROJECT.setOnMouseClicked(this::clickedCreateProject);
       updateProjectTab();

         C_BOX.setOnAction(e -> {
          loadTasksForSelectedProject();
        }); 

        scrollPane.setContent(vb_Table); // Add this line to set vb_Table as the content of scrollPane
        scrollPane.setFitToWidth(true); // Ensure the scroll pane fits the width of the content
        scrollPane.setPrefHeight(563); // Set preferred height for the scroll pane
        scrollPane.setPrefWidth(519); // Set preferred width for the scroll pane

    }

    @FXML
    void btnCREATETASK(MouseEvent event) throws IOException {
        App.setRoot("AssignedTask", 1280, 800);

    }

    @FXML
    void btnHOME(MouseEvent event) {
    
    }

    @FXML
    void btnPERFORAMANCE(MouseEvent event) {

    }

    @FXML
    void btnPRINT(MouseEvent event) {

    }

    @FXML
    void clickedCreateProject(MouseEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Create Project");
    
        Pane pane = new Pane();
        pane.setPrefSize(500, 200);
    
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

        Label label = new Label("Project Name");
        label.setLayoutX(100);
        label.setLayoutY(20);
        label.setStyle("-fx-font-weight: bold");
        pane.getChildren().add(label);
    
        TextField t1 = new TextField("");
        t1.setLayoutX(100);
        t1.setLayoutY(50);
        t1.setPrefWidth(300);
    
        pane.getChildren().add(t1);
        
        Button saveButton = new Button("Save");
        saveButton.getStyleClass().add("cave-Button");  
        saveButton.setStyle("-fx-background-color: Blue; -fx-text-fill: White;"); 
        saveButton.setLayoutX(100);
        saveButton.setLayoutY(100);
    
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancel-Button");
        cancelButton.setLayoutX(150);
        cancelButton.setLayoutY(100);
    
        saveButton.setOnAction(e -> {
            String projectName = t1.getText();

            stage.close();
    
            if (projectName.isEmpty()) {
                System.out.println("Please fill in all fields");
            } else {
                String query = "INSERT INTO projectname (ProjectTittle) VALUES (?)";
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement insertStatement = connection.prepareStatement(query)) {
                    insertStatement.setString(1, projectName);
                    insertStatement.executeUpdate();
                    updateProjectTab();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    
        cancelButton.setOnAction(e1 -> {
            stage.close();
        });
    
        pane.getChildren().addAll(saveButton, cancelButton);

        
    }


    public void updateProjectTab() {
    TabPROJECT.getChildren().clear(); // Clear existing project panes

    String query = "SELECT ProjectTittle FROM projectname";
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {

        int column = 0;
        int row = 0;
        double columnMargin = 20;
        double rowMargin = 20;
        double topMargin = 70;
        double leftMargin = 30; // Added left margin

        while (resultSet.next()) {
            String projectName = resultSet.getString("ProjectTittle");

            Pane projectPane = new Pane();
            projectPane.setPrefSize(200, 150);
            projectPane.setStyle("-fx-border-color: black; -fx-background-color: #f0f0f0; -fx-border-radius: 10;");

            VBox contentBox = new VBox();
            contentBox.setSpacing(10);
            contentBox.setLayoutX(10);
            contentBox.setLayoutY(10);

            Text projectNameText = new Text(projectName);
            projectNameText.setFont(Font.font("Arial", 20));
            projectNameText.setStyle("-fx-font-weight: bold");
            projectNameText.setWrappingWidth(180);

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> {
                deleteProject(projectName);
                updateProjectTab(); // Re-arrange projects after deletion
            });

            contentBox.getChildren().addAll(projectNameText, deleteButton);
            projectPane.getChildren().add(contentBox);

            double layoutX = column * (200 + columnMargin) + leftMargin;
            double layoutY = row * (150 + rowMargin) + topMargin;

            projectPane.setLayoutX(layoutX);
            projectPane.setLayoutY(layoutY);

            TabPROJECT.getChildren().add(projectPane);

            column++;
            if (column >= 4) {
                column = 0;
                row++;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    Text text = new Text("PROJECTS");
    text.setFont(Font.font("Arial", 20));
    text.setLayoutX(10);
    text.setLayoutY(40);
    text.setStyle("-fx-font-weight: bold; -fx-font-color: green");
    Separator separator = new Separator();
    separator.setPrefWidth(1280);
    separator.setLayoutX(10);
    separator.setLayoutY(50); 

    TabPROJECT.getChildren().add(text);
    TabPROJECT.getChildren().addAll(separator);
}
 
private void deleteProject(String projectName) {           //delete project
    String query = "DELETE FROM projectname WHERE ProjectTittle = ?";
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, projectName);
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void populateProjects() {                                      //populate the combobox with project names
    String query = "SELECT ProjectTittle FROM projectname";
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            String projectName = resultSet.getString("ProjectTittle");
            C_BOX.getItems().add(projectName);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void loadTasksForSelectedProject() {
    String selectedProject = C_BOX.getSelectionModel().getSelectedItem();
    System.out.println("Loading tasks for project: ");

    if (selectedProject != null) {
        String query = "SELECT Title, Instruction, Due, Status, AssignedTo FROM taskdb WHERE `For` = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, selectedProject);
            ResultSet resultSet = statement.executeQuery();

            ObservableList<Task> tasks = FXCollections.observableArrayList();
            vb_Table.getChildren().clear(); // Clear previous tasks

            while (resultSet.next()) {
                String title = resultSet.getString("Title");
                String instruction = resultSet.getString("Instruction");
                String due = resultSet.getString("Due");
                String status = resultSet.getString("Status");
                String assignedTo = resultSet.getString("AssignedTo");
                Task task = new Task(title, instruction, due, status, assignedTo);
                tasks.add(task);

                Button deleteButton = createDeleteButton(title);
                Button editButton = createEditButton(title, instruction, due, assignedTo);

                VBox taskDetails = createTaskDetailsVBox(task);

                HBox buttonBox = new HBox(10); // 10 is the spacing between buttons
                buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
                buttonBox.getChildren().addAll(deleteButton, editButton);

                VBox taskBox = new VBox();
                taskBox.getChildren().addAll(taskDetails, buttonBox);
                taskBox.setSpacing(10);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(taskBox);
                StackPane.setAlignment(buttonBox, Pos.BOTTOM_RIGHT);

                vb_Table.getChildren().add(stackPane);
            }
            System.out.println("Tasks loaded successfully.");
        } catch (SQLException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
    } else {
        System.out.println("No project selected.");
    }
}

private Button createDeleteButton(String title) {
    Button deleteButton = new Button("Delete");
    deleteButton.setLayoutX(200);
    deleteButton.setLayoutY(60);
    deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
    deleteButton.setOnAction(e -> {
        String deleteQuery = "DELETE FROM taskdb WHERE Title = ?";
        try (Connection deleteConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement deleteStatement = deleteConnection.prepareStatement(deleteQuery)) {
            deleteStatement.setString(1, title);
            deleteStatement.executeUpdate();
            loadTasksForSelectedProject();
            System.out.println("Task deleted successfully.");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    });
    return deleteButton;
}

private Button createEditButton(String title, String instruction, String due, String assignedTo) {
    Button editButton = new Button("Edit");
    editButton.setLayoutX(250);
    editButton.setLayoutY(60);
    editButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
    editButton.setOnAction(e -> {
        Stage stage = new Stage();
        stage.setTitle("Edit Task");

        Pane pane = new Pane();
        pane.setPrefSize(500, 500);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

        TextField t1 = createTextField("Task Title", title, 100, 50, pane);
        TextArea t2 = createTextArea("Task Instruction", instruction, 100, 110, pane);
        TextField t3 = createTextField("Due Date", due, 100, 325, pane);
        TextField t4 = createTextField("Assigned To", assignedTo, 100, 380, pane);

        Button saveButton = createSaveButton(stage, title, t1, t2, t3, t4);
        Button cancelButton = createCancelButton(stage);

        pane.getChildren().addAll(saveButton, cancelButton);
    });
    return editButton;
}

private TextField createTextField(String labelText, String text, int x, int y, Pane pane) {
    Label label = new Label(labelText);
    label.setLayoutX(x);
    label.setLayoutY(y - 30);
    label.setStyle("-fx-font-weight: bold");
    pane.getChildren().add(label);

    TextField textField = new TextField(text);
    textField.setLayoutX(x);
    textField.setLayoutY(y);
    textField.setPrefWidth(300);
    pane.getChildren().add(textField);

    return textField;
}

private TextArea createTextArea(String labelText, String text, int x, int y, Pane pane) {
    Label label = new Label(labelText);
    label.setLayoutX(x);
    label.setLayoutY(y - 30);
    label.setStyle("-fx-font-weight: bold");
    pane.getChildren().add(label);

    TextArea textArea = new TextArea(text);
    textArea.setLayoutX(x);
    textArea.setLayoutY(y);
    textArea.setPrefWidth(300);
    pane.getChildren().add(textArea);

    return textArea;
}

private Button createSaveButton(Stage stage, String title, TextField t1, TextArea t2, TextField t3, TextField t4) {
    Button saveButton = new Button("Save");
    saveButton.getStyleClass().add("save-Button");
    saveButton.setStyle("-fx-background-color: Blue; -fx-text-fill: White;");
    saveButton.setLayoutX(100);
    saveButton.setLayoutY(450);
    saveButton.setOnAction(e2 -> {
        String newTitle = t1.getText();
        stage.close();

        String newInstruction = t2.getText();
        String newDue = t3.getText();
        String newAssignedTo = t4.getText();

        if (newTitle.isEmpty() || newInstruction.isEmpty() || newDue.isEmpty() || newAssignedTo.isEmpty()) {
            System.out.println("Please fill in all fields");
        } else {
            String updateQuery = "UPDATE taskdb SET Title = ?, Instruction = ?, Due = ?, AssignedTo = ? WHERE Title = ?";
            try (Connection updateConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement updateStatement = updateConnection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, newTitle);
                updateStatement.setString(2, newInstruction);
                updateStatement.setString(3, newDue);
                updateStatement.setString(4, newAssignedTo);
                updateStatement.setString(5, title); // Assuming 'title' is the original title of the task
                updateStatement.executeUpdate();
                loadTasksForSelectedProject();
                showAlert("Task updated successfully.");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    });
    return saveButton;
}

private Button createCancelButton(Stage stage) {
    Button cancelButton = new Button("Cancel");
    cancelButton.getStyleClass().add("cancel-Button");
    cancelButton.setLayoutX(150);
    cancelButton.setLayoutY(450);
    cancelButton.setOnAction(e1 -> stage.close());
    return cancelButton;
}

private VBox createTaskDetailsVBox(Task task) {
    VBox taskDetails = new VBox();
    taskDetails.setSpacing(10);
    taskDetails.setPadding(new Insets(10)); // Add padding to position text inside the rectangle
    taskDetails.setAlignment(Pos.CENTER_LEFT); // Align text to the left
    taskDetails.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #d3d3d3; -fx-border-radius: 5;");
    taskDetails.setLayoutX(10);
    taskDetails.setPrefWidth(495);

    Text titleText = new Text(" " + task.getName());
    Text instructionText = new Text("" + task.getInstruction());
    Text dueDateText = new Text("Due Date: " + task.getDueDate());
    Text assignedToText = new Text("Assigned To: " + task.getAssignedTo());
    Text statusText = new Text("Status: " + task.getStatus());

    Separator separator1 = new Separator();
    separator1.setPrefWidth(280);

    Separator separator2 = new Separator();
    separator2.setPrefWidth(280);

    taskDetails.getChildren().addAll(
        titleText,
        separator1,
        instructionText,
        separator2,
        dueDateText,
        assignedToText,
        statusText
    );

    return taskDetails; 
}

private void showAlert(String title) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(title);
    alert.showAndWait();
}

                                        
    class Task {
        private String title;
        private String instruction;
        private String dueDate;
        private String status;
        private String assignedTo;

        public Task(String title, String instruction, String dueDate, String status, String assignedTo) {
            this.title = title;
            this.instruction = instruction;
            this.dueDate = dueDate;
            this.status = status;
            this.assignedTo = assignedTo;
        }

        public String getName() {
            return title;
        }

        public String getInstruction() {
            return instruction;
        }

        public String getDueDate() {
            return dueDate;
        }

        public String getStatus() {
            return status;
        }

        public String getAssignedTo() {
            return assignedTo;
        }
    }



}
























































    /*public void updateProjectTab(String projectName) {
        // Create a new pane for the project
        Pane projectPane = new Pane();
        projectPane.setPrefSize(200, 100);
        projectPane.setStyle("-fx-border-color: black; -fx-background-color: #f0f0f0;");
        
        Text projectNameText = new Text(projectName);
        projectNameText.setFont(Font.font("Arial", 20));
        projectNameText.setStyle("-fx-font-weight: bold");
        projectNameText.setLayoutX(50);
        projectNameText.setLayoutY(10);
        // Set wrapping width
        projectNameText.setWrappingWidth(150);
        
        
        projectPane.getChildren().addAll(projectNameText);

        // Arrange the new project pane in the TabPROJECT by columns
        int numProjects = TabPROJECT.getChildren().size();
        int column = numProjects % 5; // Adjust the number of columns as needed
        int row = numProjects / 5;
        
        double columnMargin = 20; // Set the margin between columns
       // double rowMargin = 20; // Set the margin between rows
        
        double layoutX = column * (220 + columnMargin); // Adjust the spacing as needed
        double layoutY = row * (120); // Adjust the spacing as needed
        
        projectPane.setLayoutX(layoutX);
        projectPane.setLayoutY(layoutY);
         // Adjust the width as needed

         Button deleteButton = new Button("Delete");
         deleteButton.setLayoutX(50);
         deleteButton.setLayoutY(50);
         deleteButton.setOnAction(e -> {
             TabPROJECT.getChildren().remove(projectPane);
         
             String query = "DELETE FROM projectname WHERE ProjectTittle = ?";
             try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                  PreparedStatement deleteStatement = connection.prepareStatement(query)) {
                 deleteStatement.setString(1, projectName);
                 deleteStatement.executeUpdate();
                 System.out.println("Project deleted successfully");
             } catch (Exception e1) {
                 e1.printStackTrace();
             }
         });

        projectPane.getChildren().add(deleteButton);
        TabPROJECT.getChildren().add(projectPane);*/
        //}
