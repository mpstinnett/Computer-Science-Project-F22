package com.group12.degreeaudit;

import com.group12.degreeaudit.Administration.CourseList;

import java.util.Collections;

public class Course implements Comparable<Course> {
    private String courseNumber;
    private String semester;
    private String grade;
    private String courseTitle;
    private boolean transfer;
    private char classType;
    private double gradePoints;
    private double creditHours;

    public Course() {}

    public Course(String courseNumber, String semester, String grade, String courseTitle, boolean transfer) 
    {
        this.courseNumber = courseNumber;
        this.semester = semester;
        this.grade = grade;
        this.courseTitle = courseTitle;
        this.transfer = transfer;
        this.creditHours = 0;
        updateGradePoints();
    }

    public Course(String courseNumber, String semester, String grade, String courseTitle, boolean transfer, double creditHours) {
        this.courseNumber = courseNumber;
        this.semester = semester;
        this.grade = grade;
        this.courseTitle = courseTitle;
        this.transfer = transfer;
        this.creditHours = creditHours;
        updateGradePoints();
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
        double credits = getCredits();

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
    
    public void setCreditHours(double creditHours) {
        this.creditHours = creditHours;
    }

    public double getCredits() 
    {
        if(creditHours == 0)
        {
            return Double.parseDouble(Character.toString(getCourseNumber().split(" ")[1].charAt(1)));
            //return Integer.parseInt(courseNumber.substring(courseNumber.length()-3, courseNumber.length()-2));
        }
        return creditHours;
    }

    public String toString() {
        return "\tNumber: " + courseNumber
               + "\n\tTitle: " + courseTitle
               + "\n\tGrade: " + grade
               + "\n\tSemester: " + semester
               + "\n\tTransfer: " + transfer
               + "\n\tCredit Hours: " + getCredits();
    }
    

}