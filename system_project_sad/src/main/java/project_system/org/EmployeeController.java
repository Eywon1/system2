package project_system.org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Media;
import javax.swing.JFrame;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class EmployeeController {

    @FXML
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
    private ImageView profile;

    @FXML
    private TabPane tabALL;

    @FXML
    private Tab tab_task;

    @FXML
    private AnchorPane tabtask;

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
    private final String DB_PASSWORD = "luese_192003";


    @FXML  //java fx button to display the calendar
    void Clicked_Calendar(MouseEvent event) {

    }

    @FXML
    void Clicked_Home(MouseEvent event) {

    }

    @FXML
    void Clicked_Performance(MouseEvent event) {

    }

    @FXML
    void Clicked_Settings(MouseEvent event) {

    }

    public void initialize() { // Initialize the Employee Dashboard
        setTask();
    }

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
                
                            taskDetailsBox.getChildren().addAll(title, due, instruction, fileType, Submit, MarkAsDone);
                            tabtask.getChildren().add(taskDetailsBox);

                        }
                    }
                }); 
                
                        vbox.getChildren().add(taskBox);
            }
                
                        ScrollPane scrollPane = new ScrollPane(vbox);
                        tabtask.getChildren().add(scrollPane);
        }

        /*private void AttachFile(byte[] bs, String string) {
            File file = new FileChooser().showOpenDialog(new JFrame());   
            if (file != null) {
                // Read file content
                byte[] fileContent = new byte[(int) file.length()];
                try (FileInputStream fis = new FileInputStream(file)) {
                    fis.read(fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Display file
                displayFile(fileContent, string);
            }         

                        
        }*/
                
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
                taskData.put("Title", rs.getString("Title"));
                taskData.put("Instruction", rs.getString("Instruction"));
                taskData.put("For", rs.getString("For"));
                taskData.put("AssignedTo", rs.getString("AssignedTo"));
                taskData.put("Due", rs.getString("Due"));
                taskData.put("File", rs.getBytes("File")); // Retrieve file as byte array
                taskData.put("FileType", rs.getString("FileType"));
                taskData.put("Status", rs.getString("Status"));
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
                    
 }
            
                













































    

    /*public void displayFile(byte[] file, String fileType) {
       
    tabtask.getChildren().removeIf(node -> node.getId() != null && node.getId().startsWith("fileDisplay"));

    Node fileDisplayNode = null;

    switch (fileType.toLowerCase()) {
        case "image/png":
        case "image/jpeg":
        case "image/gif":
            // Display image
            Image image = new Image(new ByteArrayInputStream(file));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(400); // Set desired width
            imageView.setPreserveRatio(true);
            imageView.setId("fileDisplayImage");
            fileDisplayNode = imageView;
            break;
        case "application/pdf":
            // Display PDF
            WebView webView = new WebView();
            webView.getEngine().loadContent(new String(file), "application/pdf");
            webView.setPrefSize(600, 800); // Set desired size
            webView.setId("fileDisplayPDF");
            fileDisplayNode = webView;
            break; 
              case "text/plain":
            // Display text
            TextArea textArea = new TextArea(new String(file));
            textArea.setEditable(false);
            textArea.setPrefSize(600, 400); // Set desired size
            textArea.setId("fileDisplayText");
            fileDisplayNode = textArea;
            break;
        default:
            // Handle unsupported file types
            TextArea unsupportedText = new TextArea("Unsupported file type: " + fileType);
            unsupportedText.setEditable(false);
            unsupportedText.setPrefSize(600, 100); // Set desired size
            unsupportedText.setId("fileDisplayUnsupported");
            fileDisplayNode = unsupportedText;
            break;    
    }

      if (fileDisplayNode != null) {
        VBox fileDisplayBox = new VBox(10);
        fileDisplayBox.setLayoutX(560);
        fileDisplayBox.setLayoutY(300); // Adjust as needed
        fileDisplayBox.setId("fileDisplayBox");
        fileDisplayBox.getChildren().add(fileDisplayNode);
        tabtask.getChildren().add(fileDisplayBox);
    }    
}/*


<dependency>

    <groupId>org.openjfx</groupId>

    <artifactId>javafx-controls</artifactId>

    <version>17.0.2</version>

</dependency>

<dependency>

    <groupId>org.openjfx</groupId>

    <artifactId>javafx-web</artifactId>

    <version>17.0.2</version>

</dependency>
 */
 

















    /*public void setTask(){

        String query = "SELECT Title FROM taskdb";
        List<String> taskList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                taskList.add(rs.getString("Title"));
            }

            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(20, 20, 20, 20));
            vbox.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            vbox.setPrefWidth(500);
            vbox.setPrefHeight(800);
            vbox.setLayoutX(20);
            vbox.setLayoutY(20);
            for (String task : taskList) {
                Label label = new Label(task);
                label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                vbox.getChildren().add(label);
            }
            ScrollPane scrollPane = new ScrollPane(vbox);
            tabtask.getChildren().add(scrollPane);
            
        } catch (SQLException e) {
            e.printStackTrace();

    }
   
    
    }
    
}*/ 










