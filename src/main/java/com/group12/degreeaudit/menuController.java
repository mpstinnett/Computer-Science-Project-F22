package com.group12.degreeaudit;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
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
            System.out.println("Lightning");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource() == degree_planner_btn){
            System.out.println("Thunder");
        }
    }

}
