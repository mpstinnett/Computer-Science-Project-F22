package com.group12.degreeaudit.Audit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Description: auditGUI -  Front end for audits
 */
public class auditGUI extends Application {

    /**
    * Description: start - Main entry point for audit GUI
    * @param primaryStage    Which JavaFX Scene we want to run
    * @exception Exception    if the audit scene cannot be loaded
    */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/auditSceneController.fxml"));
        primaryStage.setTitle("Degree Audit");
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