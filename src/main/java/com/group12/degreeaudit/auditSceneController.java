package com.group12.degreeaudit;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.DegreeList;
import com.group12.degreeaudit.Administration.FileActions;
import com.group12.degreeaudit.Audit.DegreeAudit;

import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Description: auditSceneController - Controller for the front end for audits
 */
public class auditSceneController implements Initializable {

private Student student = null;

@FXML 
private Button import_student_btn, export_pdf_btn, return_to_menu_btn;

@FXML
private TextArea audit_textarea;

/**
* Description: returnToMenu - Brings user to main menu GUI when "return to main menu" button is clicked
* @param event    JavaFX ActionEvent when a user clicks
* @exception IOException    if the menu scene cannot be loaded
*/
@FXML
public void returnToMenu(ActionEvent event) throws IOException 
{
    Stage stage;
    Parent root;
    stage = (Stage) return_to_menu_btn.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("/fxml/menuScene.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}

/**
* Description: importStudent - Lets the user open up their file directory when "import student" button is clicked
* @param event    JavaFX ActionEvent when a user clicks
*/
@FXML
public void importStudent(ActionEvent event) {
    // grab student file
    CourseList courseList = new CourseList("resources/CourseList.json");
    DegreeList degreeList = new DegreeList("resources/DegreeList.json");
    FileActions importStudentFromFile = new FileActions(courseList, degreeList);
    student = importStudentFromFile.importStudent();
    
    // perform audit
    DegreeAudit audit = new DegreeAudit(student, courseList);
    audit_textarea.setText(audit.doAudit());
}

/**
* Description: exportPDF - Lets the user open up their file directory when "export PDF" button is clicked
* @param event    JavaFX ActionEvent when a user clicks
*/
@FXML
public void exportPDF(ActionEvent event) {
    if(student == null){
        errorAlert("Please import student before exporting pdf.");
    }
    else{
        CourseList courseList = new CourseList("resources/CourseList.json");
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        FileActions exportAudit = new FileActions(courseList, degreeList);
        DegreeAudit audit = new DegreeAudit(student, courseList);
        exportAudit.exportAuditPDF(student, audit);
    }
}

/**
* Description: errorAlert - Displays error popup
* @param error    The specific error that occured as a String
*/
public void errorAlert(String error) {

    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(null);
    alert.setContentText(error);
    alert.showAndWait();

}


@Override 
public void initialize(URL url, ResourceBundle rb) {

    }
}