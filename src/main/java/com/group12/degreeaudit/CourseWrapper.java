package com.group12.degreeaudit;

import java.util.Collections;

import com.group12.degreeaudit.CourseSample;

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
    public boolean transfer = false;
    public String grade = "";


    public CourseWrapper() 
    {
        course = new Course();
    }

    public CourseWrapper(Course course)
    {
        this.course = course;
        this.courseNumber = course.getCourseNumber();
        this.courseTitle = course.getCourseTitle();
        this.semester = course.getSemester();
        this.transfer = course.getTransfer();
        this.grade = course.getGrade();
        // create a remove button for the instance
        this.button = new Button("X");
        button.setStyle("-fx-text-fill: #C00000; -fx-background-color: transparent; -fx-font-weight: bold;");
    }

    public void removeCourse(final TableView tblView, final CourseWrapper course, final ComboBox dropdown, final String prereq, final boolean overriddenCourse, final Student studentRemove){
        button.setFocusTraversable(false);
        button.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                //tblView.getItems().remove(tblView.getSelectionModel().getSelectedItem());
                tblView.getItems().remove(course);   
                

                // Put item back into dropdown (ONLY IF IT WAS ORIGINALLY IN DROPDOWN)
                if(!overriddenCourse){
                    dropdown.getItems().add(prereq);
                }
                
                // sort the dropdown again after putting back an item
                ObservableList<String> dropdownItems = dropdown.getItems();
                Collections.sort(dropdownItems);
                studentRemove.removeCourse(course.getCourse());

            }
        });
    }

    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getSemester() {
        return semester;
    }

    public boolean getTransfer() {
        return transfer;
    }

    public String getGrade() {
        return grade;
    }

    public void setButton(Button button){
        this.button = button;
    }

    public Button getButton(){
        return button;
    }
}