package com.group12.degreeaudit;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class menuController {

    @FXML
    private Button admin_btn,degree_planner_btn;


    public void initialize() {
        // TODO
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if(event.getSource() == admin_btn){
            stage = (Stage) admin_btn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/adminScene.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource() == degree_planner_btn){
            stage = (Stage) degree_planner_btn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/degreePlanningScene.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void importTranscript(ActionEvent event){
        System.out.println("I'm so done with you");
        FileChooser fc = new FileChooser();
		// if we want to open fixed location
		//fc.setInitialDirectory(new File("D:\\\\Books"));
		

		// if we want to fixed file extension
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = fc.showOpenDialog(null);
        
        
		
		if(selectedFile != null) {
			//listview.getItems().add(selectedFile.getAbsolutePath());
            TranscriptScanner transcriptScanner = new TranscriptScanner(selectedFile.toPath().toString());
            Student student = transcriptScanner.scanTranscript();

            System.out.println("File is valid");
            System.out.println(selectedFile.toPath().toString());
		}else {
			System.out.println("File is not valid!");
		}
    }

}
