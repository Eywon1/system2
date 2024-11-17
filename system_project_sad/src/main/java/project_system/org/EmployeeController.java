package project_system.org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ScrollPane;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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


    private final String DB_URL = "jdbc:mysql://localhost:3306/pomsdb";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "luese_192003";


    @FXML
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

    public void initialize() {
        setTask();
    }

        public void setTask() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        vbox.setPrefWidth(500);
        vbox.setPrefHeight(800);
        vbox.setLayoutX(20);
        vbox.setLayoutY(20);

        List<Task> taskList = fetchTasks();

        for (Task task : taskList) {
            VBox taskBox = new VBox();
            taskBox.setSpacing(15);
            taskBox.setPadding(new Insets(10, 10, 10, 10));
            taskBox.setStyle("-fx-background-color: white");
            taskBox.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


            Label titleLabel = new Label(task.getTitle());
            titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            Label dateLabel = new Label(task.getDatePosted() + (task.getDatePosted() != null ? "-(" + task.getAssigndTo() + ")" : ""));
            dateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            dateLabel.setTextFill(Color.GRAY);

            taskBox.getChildren().addAll(titleLabel, dateLabel);

            taskBox.setOnMouseClicked(e -> {            //Action button for task
                System.out.println("Task clicked");
            });

            vbox.getChildren().add(taskBox);
        }

        ScrollPane scrollPane = new ScrollPane(vbox);
        tabtask.getChildren().add(scrollPane);

    }

    private List<Task> fetchTasks() {
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

    public void fetchtaskInfoTask(){
        String query = "SELECT Title, Instruction, For,  AssignedTo, Due, File, FileType, Status FROM taskdb";
        List<String> TaskList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TaskList.add(rs.getString("Title"));
                TaskList.add(rs.getString("Instruction"));
                TaskList.add(rs.getString("For"));
                TaskList.add(rs.getString("AssignedTo"));
                TaskList.add(rs.getString("Due"));
                TaskList.add(rs.getString("File"));
                TaskList.add(rs.getString("FileType"));
                TaskList.add(rs.getString("Status"));

                
                
            }
        } catch (SQLException e) {
            e.printStackTrace();

    }
    }
}
 
































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



    




    
 
    