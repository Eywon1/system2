package project_system.org;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

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
    private Label text_date;

    @FXML
    private Label text_department;

    @FXML
    private Label text_greet1;

    @FXML
    private Label text_name;

    @FXML
    private Label text_time;

    @FXML
    private TableView<?> tableTask;


     public void initialize() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        LocalDateTime now = LocalDateTime.now();
        text_date.setText(dtf.format(now));

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, (javafx.event.ActionEvent e) -> {
            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("h:mm:ss");
            LocalDateTime now1 = LocalDateTime.now();
            text_time.setText(dtf1.format(now1));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        if(Time.valueOf(LocalDateTime.now().toLocalTime()).after(Time.valueOf(LocalTime.parse("00:00:00")))&&Time.valueOf(LocalDateTime.now().toLocalTime()).before(Time.valueOf(LocalTime.parse("12:00:00")))){
            text_greet1.setText("Good Morning" + text_name);
    }
    }



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
    void Clicked_Settings(MouseEvent event) throws IOException {
     App.setRoot("login", 500, 600);
    }

}
