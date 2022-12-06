package com.group12.degreeaudit;

import java.util.Collections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class CourseWrapper
{
    public Course course;
    public Button button;
    public String courseNumber = "";
    public String courseTitle = "";
    public String semester = "";
    public char classType;
    public boolean transfer = false;
    public String grade = "";

    /**
    * Description: CourseWrapper Constructor
    */
    public CourseWrapper() 
    {
        course = new Course();
    }

    /**
    * Description: CourseWrapper Constructor
    * @param course as Course that we are wrapping
    */
    public CourseWrapper(Course course)
    {
        this.course = course;
        this.courseNumber = course.getCourseNumber();
        this.courseTitle = course.getCourseTitle();
        this.semester = course.getSemester();
        this.transfer = course.getTransfer();
        this.classType = course.getClassType();
        this.grade = course.getGrade();
        // create a remove button for the instance
        this.button = new Button("X");
        button.setStyle("-fx-text-fill: #C00000; -fx-background-color: transparent; -fx-font-weight: bold;");
    }

    /**
    * Description: removeCourse - called whenever the "X" button on a course in a table is pressed
    * @param tblView current table the course is in
    * @param course course to be removed
    * @param dropdown dropdown that the course will be added to after removal from table
    * @param studentRemove student who needs a course to be removed from
    */
    public void removeCourse(final TableView tblView, final CourseWrapper course, final ComboBox dropdown, final String prereq, final Student studentRemove){
        button.setFocusTraversable(false);
        button.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                //tblView.getItems().remove(tblView.getSelectionModel().getSelectedItem());
                tblView.getItems().remove(course);   
                
                // Put item back into dropdown
                dropdown.getItems().add(prereq);
                
                // sort the dropdown again after putting back an item
                ObservableList<String> dropdownItems = dropdown.getItems();
                Collections.sort(dropdownItems);
                studentRemove.removeCourse(course.getCourse());

            }
        });
    }

    /**
    * Description: getCourse - getter for a course
    */
    public Course getCourse() {
        return course;
    }
    
    /**
    * Description: setCourse - setter for a course
    */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
    * Description: getCourseNumber - getter for a course number
    */
    public String getCourseNumber() {
        return courseNumber;
    }

    /**
    * Description: getCourseTitle - getter for a course title
    */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
    * Description: getSemester - getter for the semester a course was taken in
    */
    public String getSemester() {
        return semester;
    }

    /**
    * Description: getTransfer - getter for a course's transfer info
    */
    public boolean getTransfer() {
        return transfer;
    }

    /**
    * Description: getGrade - getter for a grade in course
    */
    public String getGrade() {
        return grade;
    }

    /**
    * Description: setButton - setter for "X" button
    * @param button for "X"
    */
    public void setButton(Button button){
        this.button = button;
    }

    /**
    * Description: getButton - getter for "X" button
    */
    public Button getButton(){
        return button;
    }

    /**
    * Description: setClassType - setter for class type 
    * @param classType can be 'A' for admissions, 'C' for core, or 'E' for electives
    */
    public void setClassType(char classType){
        this.classType = classType;
    }

    /**
    * Description: getClassType - getter for class type 
    */
    public char getClassType(){
        return classType;
    }
}