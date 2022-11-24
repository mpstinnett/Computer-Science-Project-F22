package com.group12.degreeaudit;

import com.group12.degreeaudit.Administration.CourseList;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import java.util.Collections;

public class Course implements Comparable<Course> {
    private String courseNumber;
    private String semester;
    private String grade;
    private String courseTitle;
    private Button button;
    private boolean transfer;
    private char classType;
    private double gradePoints;

    public Course() {}
    public Course(String courseNumber, String semester, String grade, String courseTitle, boolean transfer) {
        this.courseNumber = courseNumber;
        this.semester = semester;
        this.grade = grade;
        this.courseTitle = courseTitle;
        this.transfer = transfer;
        this.button = new Button("X");
        button.setStyle("-fx-text-fill: #C00000; -fx-background-color: transparent; -fx-font-weight: bold;");
        
        updateGradePoints();
    }

    public void removeCourse(final TableView tblView, final Course course, final ComboBox dropdown, final String prereq){
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
            }
        });
    }


    public String getCourseNumber() {
        return courseNumber;
    }
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }
    public String getSemester() {
        return semester;
    }
    public void setSemester(String semester) {
        this.semester = semester;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getCourseTitle() {
        return courseTitle;
    }
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
    public boolean getTransfer() {
        return transfer;
    }
    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }
    public char getClassType() {
        return classType;
    }
    public void setClassType(char classType) {
        this.classType = classType;
    }

    @Override
    public int compareTo(Course c)
    {
        return Integer.compare(getGradeHeirarchy(), c.getGradeHeirarchy());
    }

    public double getGradePoints()
    {
        return gradePoints;
    }

    private void updateGradePoints()
    {
        int credits = getCredits();
        switch (getGrade()) {
            case "A+" : gradePoints = 4.00 * credits;
                        break;
            case "A"  : gradePoints = 4.00 * credits;
                        break;
            case "A-" : gradePoints = 3.67 * credits;
                        break;
            case "B+" : gradePoints = 3.33 * credits;
                        break;
            case "B"  : gradePoints = 3.00 * credits;
                        break;
            case "B-" : gradePoints = 2.67 * credits;
                        break;
            case "C+" : gradePoints = 2.33 * credits;
                        break;
            case "C"  : gradePoints = 2.00 * credits;
                        break;
            case "C-" : gradePoints = 1.67 * credits;
                        break;
            case "D+" : gradePoints = 1.33 * credits;
                        break;
            case "D"  : gradePoints = 1.00 * credits;
                        break;
            case "D-" : gradePoints = 0.67 * credits;
                        break;
            case "F"  : gradePoints = 0.00 * credits;
                        break;
            default : gradePoints = 0.00 * credits;
                        break;
        } 
    }

    public int getCredits()
    {
        return Integer.parseInt(courseNumber.substring(courseNumber.length()-3, courseNumber.length()-2));
    }

    private int getGradeHeirarchy()
    {
        switch (getGrade()) {
            case "A+" : return 1;
            case "A"  : return 2;
            case "A-" : return 3;
            case "B+" : return 4;
            case "B"  : return 5;
            case "B-" : return 6;
            case "C+" : return 7;
            case "C"  : return 8;
            case "C-" : return 9;
            case "D+" : return 10;
            case "D"  : return 11;
            case "D-" : return 12;
            case "F"  : return 13;
            default : return 14;
        } 
    }

    public String toString() {
        return "\tNumber: " + courseNumber
               + "\n\tTitle: " + courseTitle
               + "\n\tGrade: " + grade
               + "\n\tSemester: " + semester
               + "\n\tTransfer: " + transfer;
    }
    
    public void setButton(Button button){
        this.button = button;
    }

    public Button getButton(){
        return button;
    }
}