package com.group12.degreeaudit;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class menuGUI extends Application {

    /**
    * Description: start - Main entry point for Menu GUI
    * @param primaryStage    Which JavaFX Scene we want to run
    * @exception Exception    if the administration scene cannot be loaded
    */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuScene.fxml"));
        primaryStage.setTitle("Degree Planning & Audit Tool");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/images/favicon.png"));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}