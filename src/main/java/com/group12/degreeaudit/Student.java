package com.group12.degreeaudit;
import java.util.ArrayList;
import java.util.List;

import com.group12.degreeaudit.Administration.JSONCourse;

public class Student {
    private String name;
    private String ID;
    private String program;
    private String semesterAdmitted;
    private List<Course> coursesTaken = new ArrayList<Course>();

    public Student() {}
    public Student(String name, String ID, String program, String semesterAdmitted, List<Course> coursesTaken) {
        this.name = name;
        this.ID = ID;
        this.program = program;
        this.semesterAdmitted = semesterAdmitted;
        this.coursesTaken = coursesTaken;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getProgram() {
        return program;
    }
    public void setProgram(String program) {
        this.program = program;
    }
    public String getSemesterAdmitted() {
        return semesterAdmitted;
    }
    public void setSemesterAdmitted(String semesterAdmitted) {
        this.semesterAdmitted = semesterAdmitted;
    }
    public List<Course> getCourses() {
        return coursesTaken;
    }
    public void addCourse(Course course) {
        coursesTaken.add(course);
    }

    /*
     * Gets a list of all possible courses the student is able to take based on
     *      courses the student has taken.
     * There are 3 conditions that must be met for a course to be listed:
     *      1. The course has to be an active course.
     *      2. The course's prerequisites must be met.
     *      3. The course hasn't already been taken.
     */
    public List<JSONCourse> getPossibleCourses(List<JSONCourse> allCourses) {
        List<JSONCourse> possibleCourses = new ArrayList<>();
        for(JSONCourse jsonCourse : allCourses) {
            if(jsonCourse.getActiveStatus() && checkPreReqCondition(jsonCourse) && !hasTakenCourse(jsonCourse))
                possibleCourses.add(jsonCourse);
        }
        return possibleCourses;
    }

    /*
     * Checks if the given JSONCourse is able to be taken based on its Pre-Reqs
     */
    public boolean checkPreReqCondition(JSONCourse jsonCourse) {
        List<Course> takenCourses = getCourses();
        String[] preReqs = jsonCourse.getCoursePreReqs();
        if(preReqs == null)
            return true;
        for(String preReq : preReqs) {
            boolean taken = false;
            for(Course course : takenCourses) {
                String courseNum = course.getCourseNumber();
                if(preReq.equals(courseNum)) {
                    taken = true;
                    break;
                }
            }
            if(taken == false)
                return false;
        }
        return true;
    }

    /*
     * Checks if the given JSONCourse has been taken by the student.
     */
    public boolean hasTakenCourse(JSONCourse jsonCourse) {
        List<Course> takenCourses = getCourses();
        for(Course course : takenCourses) {
            if(jsonCourse.getCourseNumber().equals(course.getCourseNumber()))
                return true;
        }
        return false;
    }

    public String toString() {
        return "Name: " + name
                + "\nID: " + ID
                + "\nProgram: " + program
                + "\nSemester Admitted: " + semesterAdmitted;
    }
}