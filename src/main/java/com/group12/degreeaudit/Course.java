package com.group12.degreeaudit;


public class Course {
    private String courseNumber;
    private String semester;
    private String grade;
    private String courseTitle;
    private boolean transfer;

    public Course() {}
    public Course(String courseNumber, String semester, String grade, String courseTitle, boolean transfer) {
        this.courseNumber = courseNumber;
        this.semester = semester;
        this.grade = grade;
        this.courseTitle = courseTitle;
        this.transfer = transfer;
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

    public String toString() {
        return "\tNumber: " + courseNumber
               + "\n\tTitle: " + courseTitle
               + "\n\tGrade: " + grade
               + "\n\tSemester: " + semester
               + "\n\tTransfer: " + transfer;
    }
}