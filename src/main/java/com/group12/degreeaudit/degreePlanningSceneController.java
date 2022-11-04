package com.group12.degreeaudit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class degreePlanningSceneController {

    @FXML
    private TextField tfTitle;

    @FXML
    private ImageView infoIcon;

    @FXML
    void btnOkClicked(ActionEvent event) {
        Stage mainWindow = (Stage) tfTitle.getScene().getWindow();
        String title = tfTitle.getText();
        mainWindow.setTitle(title);
    }

    

}
