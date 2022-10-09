package com.group12.degreeaudit.Administration;

import java.util.ArrayList;

public class JSONCourse 
{
    String courseNumber;
    String courseName;
    String courseDescription;
    String[] coursePreReqs;

    public JSONCourse(String courseNumber, String courseName, String courseDescription, String[] prereqs)
    {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.coursePreReqs = prereqs;
    }
}
