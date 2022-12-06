package com.group12.degreeaudit.Administration;

import java.util.Collections;

import com.group12.degreeaudit.CourseSample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class JSONCourseWrapper 
{
    private JSONCourse jsonCourse;
    private Button button;

    /**
    * Description: JSONCourseWrapper Constructor
    * @return None
    * @throws Nothing is implemented
    */
    public JSONCourseWrapper() 
    {
        jsonCourse = new JSONCourse();
    }

    /**
    * Description: JSONCourseWrapper Constructor
    * @param courseNumber as String
    * @param courseName as String
    * @param courseDescription as String
    * @param prereqs as String array of course numbers
    * @param classType as char 'A' for admissions, 'E' for elective, or 'C' for core
    * @param activeStatus as boolean
    * @return None
    * @throws Nothing is implemented
    */
    public JSONCourseWrapper(String courseNumber, String courseName, String courseDescription, String[] prereqs, char classType, boolean activeStatus)
    {
        jsonCourse = new JSONCourse(courseNumber, courseName, courseDescription, prereqs, classType, activeStatus);
    }

    /**
    * Description: JSONCourseWrapper Constructor - Created when adding a course to a table in administration GUI
    * @param courseNumber as String
    * @return None
    * @throws Nothing is implemented
    */
    public JSONCourseWrapper(String courseNumber)
    {
        jsonCourse = new JSONCourse(courseNumber);

        // create a remove button for the instance
        this.button = new Button("X");
        button.setStyle("-fx-text-fill: #C00000; -fx-background-color: transparent; -fx-font-weight: bold;");
        
    }

    /**
    * Description: removeTableCourse - called whenever the "X" button on a course in a table is pressed
    * @param tblView current table the course is in
    * @param course course to be removed
    * @param dropdown dropdown that the course will be added to after removal from table
    * @param prereq String for the class number (used for dropdown)
    * @return None
    * @throws Nothing is implemented
    */
    public void removeTableCourse(final TableView tblView, final JSONCourseWrapper course, final ComboBox dropdown, final String prereq){
        button.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                //tblView.getItems().remove(tblView.getSelectionModel().getSelectedItem());
                tblView.getItems().remove(course);   
                System.out.println("after removed: " + tblView.getItems());
                
                // Put item back into dropdown
                dropdown.getItems().add(prereq);

                // sort the dropdown again after putting back an item
                ObservableList<String> dropdownItems = dropdown.getItems();
                Collections.sort(dropdownItems);
            }
        });
    }

    /**
    * Description: getJsonCourse - getter for a course
    * @return None
    * @throws Nothing is implemented
    */
    public JSONCourse getJsonCourse() {
        return jsonCourse;
    }

    /**
    * Description: setJsonCourse - setter for course
    * @params jsonCourse course that is being wrapped
    * @return None
    * @throws Nothing is implemented
    */
    public void setJsonCourse(JSONCourse jsonCourse) {
        this.jsonCourse = jsonCourse;
    }

    /**
    * Description: setButton - setter for "X" button
    * @params button for "X"
    * @return None
    * @throws Nothing is implemented
    */
    public void setButton(Button button){
        this.button = button;
    }

    /**
    * Description: getButton - getter for "X" button
    * @return None
    * @throws Nothing is implemented
    */
    public Button getButton(){
        return button;
    }
}