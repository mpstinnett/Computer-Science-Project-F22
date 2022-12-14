package com.group12.degreeaudit.MainMenu;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Description: menuController - Controller for the Main Menu
 */
public class menuController {

    @FXML
    private Button admin_btn,degree_planner_btn, audit_btn;

    /**
    * Description: handleButtonAction - Directs user to desired window (administration, degree planner, or audit)
    * @param event    JavaFX ActionEvent when a user clicks
    * @exception IOException    if the window cannot be loaded
    */
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
