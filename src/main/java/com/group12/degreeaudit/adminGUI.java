package com.group12.degreeaudit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class adminGUI extends Application {

    /**
    * Description: start - Main entry point for Administration GUI
    * @param primaryStage Which JavaFX Scene we want to run
    * @exception Exception if the administration scene cannot be loaded
    */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminScene.fxml"));
        primaryStage.setTitle("Administration Settings");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}