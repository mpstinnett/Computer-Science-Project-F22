package com.group12.degreeaudit;
import java.util.ArrayList;
import java.util.List;

import com.group12.degreeaudit.Administration.JSONDegree;

public class Student {
    private String name;
    private String ID;
    private String program;
    private String semesterAdmitted;
    private JSONDegree degreeTrack;
    private List<Course> coursesTaken = new ArrayList<Course>();

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
    public List<Course> getCoursesTaken() {
        return coursesTaken;
    }
    public void addCourse(Course course) {
        coursesTaken.add(course);
    }

    public void setDegreeTrack(JSONDegree degreeTrack)
    {
        this.degreeTrack = degreeTrack;
    }

    public JSONDegree getDegreeTrack()
    {
        if(degreeTrack != null)
            return degreeTrack;
        else
            return new JSONDegree();
    }

    public String toString() {
        return "Name: " + name
                + "\nID: " + ID
                + "\nProgram: " + program
                + "\nSemester Admitted: " + semesterAdmitted;
    }
}