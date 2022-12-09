package com.group12.degreeaudit.Planner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Description: degreePlanningGUI -  Front end for degree planning
 */
public class degreePlanningGUI extends Application {

    /**
    * Description: start - Main entry point for degree planning GUI
    * @param primaryStage    Which JavaFX Scene we want to run
    * @exception Exception    if the degree planning scene cannot be loaded
    */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/degreePlanningScene.fxml"));
        primaryStage.setTitle("Degree Planning");
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