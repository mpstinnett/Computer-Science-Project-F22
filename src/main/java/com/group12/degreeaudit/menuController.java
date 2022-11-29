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

import com.group12.degreeaudit.Administration.CourseList;

import javafx.fxml.Initializable;

public class menuController {

    @FXML
    private Button admin_btn,degree_planner_btn, audit_btn;
    private CourseList courseList;


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
        else if(event.getSource() == audit_btn){
            stage = (Stage) audit_btn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/auditScene.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


}
