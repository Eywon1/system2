package project_system.org;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javafx.scene.control.Separator;
import javax.swing.JTextField;



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
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminController {

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

    private final String DB_URL = "jdbc:mysql://localhost:3306/pomsdb";
    private final String DB_USER ="root";
    private final String DB_PASSWORD = "luese_192003";

    public void initialize() {
        String css = this.getClass().getResource("/project_system/org/projectStyle.css").toExternalForm();

        // Apply the CSS file to the TabPane
        TabPROJECT.getStylesheets().add(css);
      
             

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

            Text projectNameText = new Text(projectName);
            projectNameText.setFont(Font.font("Arial", 20));
            projectNameText.setStyle("-fx-font-weight: bold");
            projectNameText.setLayoutX(10);
            projectNameText.setLayoutY(30);
            projectNameText.setWrappingWidth(180);

            Button deleteButton = new Button("Delete");
            deleteButton.setLayoutX(10);
            deleteButton.setLayoutY(100);
            deleteButton.setOnAction(e -> {
                deleteProject(projectName);
                updateProjectTab(); // Re-arrange projects after deletion
            });

            projectPane.getChildren().addAll(projectNameText, deleteButton);

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
    } catch (Exception e) {
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

private void deleteProject(String projectName) {
    String query = "DELETE FROM projectname WHERE ProjectTittle = ?";
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, projectName);
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
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
