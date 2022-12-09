package com.group12.degreeaudit.MainMenu;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Description: menuGUI -  Front end for Main Menu
 */
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

    /**
    * Description: main - loads and initializes application window
    * @param args    command line arguments (null)
    */
    public static void main(String[] args) {
        launch(args);
    }
}