package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class that starts the Application
 */
public class Main extends Application {

    /**
     * This method sets and displays the primary stage
     * @param primaryStage The initial stage when the application starts up
     * @throws Exception indicates conditions that a reasonable application might want to catch
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
        primaryStage.setTitle("Inventory System");
        primaryStage.setScene(new Scene(root, 1300,900));
        primaryStage.show();
    }

    /**
     * The main method for the application.
     * @param args The initial arguments for the main method
     */
    public static void main(String[] args) {
        launch(args);
    }
}
