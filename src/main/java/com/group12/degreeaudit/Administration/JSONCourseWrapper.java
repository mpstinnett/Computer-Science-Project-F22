package com.group12.degreeaudit.Administration;

import com.group12.degreeaudit.CourseSample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class JSONCourseWrapper 
{
    private JSONCourse jsonCourse;
    private Button button;


    public JSONCourseWrapper(String courseNumber, String courseName, String courseDescription, String[] prereqs, char classType, boolean activeStatus)
    {
        jsonCourse = new JSONCourse(courseNumber, courseName, courseDescription, prereqs, classType, activeStatus);
    }

    public JSONCourseWrapper(String courseNumber)
    {
        jsonCourse = new JSONCourse(courseNumber);

        // create a remove button for the instance
        this.button = new Button("X");
        button.setStyle("-fx-text-fill: #C00000; -fx-background-color: transparent; -fx-font-weight: bold;");
        
    }

    public void removePrerequisite(final TableView tblView, final JSONCourseWrapper course, final ComboBox dropdown, final String prereq){
        button.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                //tblView.getItems().remove(tblView.getSelectionModel().getSelectedItem());
                tblView.getItems().remove(course);   
                System.out.println("after removed: " + tblView.getItems());
                
                // Put item back into dropdown
                dropdown.getItems().add(prereq);
            }
        });
    }

    public JSONCourse getJsonCourse() {
        return jsonCourse;
    }
    public void setJsonCourse(JSONCourse jsonCourse) {
        this.jsonCourse = jsonCourse;
    }

    public void setButton(Button button){
        this.button = button;
    }

    public Button getButton(){
        return button;
    }
}