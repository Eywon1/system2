package project_system.org;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Ensure the FXML file name is correct and the file is in the correct location
        Parent root = loadFXML("EmployeeDashboard");
        scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml, double width, double height) throws IOException {
        // Ensure the FXML file name is correct and the file is in the correct location
        Parent root = loadFXML(fxml);
        scene.setRoot(root);
        scene.getWindow().setWidth(width);
        scene.getWindow().setHeight(height);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        // Ensure the path to the FXML file is correct
        String fxmlPath = fxml + ".fxml";
    System.out.println("Loading FXML file: " + fxmlPath);
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath));
    return fxmlLoader.load();
    }
    
    public static void main(String[] args) {
        launch();
    }
}      //kupal