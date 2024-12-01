package project_system.org;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create TextAreas
        TextArea textArea1 = new TextArea("This is the first text area with some initial content.");
        TextArea textArea2 = new TextArea("This is the second text area.");

        // Create Button
        Button cutAndPasteButton = new Button("Cut and Paste");

        // Set action on the button
        cutAndPasteButton.setOnAction(event -> {
            // Cut all content from textArea1
            String allText = textArea1.getText();
            if (!allText.isEmpty()) {
                ClipboardContent content = new ClipboardContent();
                content.putString(allText);
                Clipboard.getSystemClipboard().setContent(content);
                textArea1.clear(); // Remove all the text

                // Paste text to textArea2
                if (Clipboard.getSystemClipboard().hasString()) {
                    String clipboardText = Clipboard.getSystemClipboard().getString();
                    textArea2.setText(clipboardText);
                }
            }
        });

        // Create a layout and add the TextAreas and Button
        VBox layout = new VBox(10);
        layout.getChildren().addAll(textArea1, cutAndPasteButton, textArea2);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cut and Paste Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
