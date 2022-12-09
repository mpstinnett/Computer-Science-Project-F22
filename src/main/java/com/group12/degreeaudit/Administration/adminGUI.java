package com.group12.degreeaudit.Administration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Description: adminGUI - Front end for administration
 */
public class adminGUI extends Application {

    /**
    * Description: start - Main entry point for Administration GUI
    * @param primaryStage    JavaFX Stage for Which JavaFX Scene we want to run
    * @exception Exception    if the administration scene cannot be loaded
    */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminScene.fxml"));
        primaryStage.setTitle("Administration Settings");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
    * Description: main - loads and initializes application window
    * @param args    command line arguments (null)
    */
    public static void main(String[] args) {
        launch(args);
    }
}