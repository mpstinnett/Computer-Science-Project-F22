package com.group12.degreeaudit.Administration;

import java.util.ArrayList;

public class JSONCourse 
{
    String courseNum;
    String courseName;
    String courseDescription;
    JSONCourse[] coursePreReqs;

    public JSONCourse(String courseNumber, String courseName, String courseDescription, ArrayList<JSONCourse> prereqs)
    {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.coursePreReqs = coursePreReqs;
    }
}
