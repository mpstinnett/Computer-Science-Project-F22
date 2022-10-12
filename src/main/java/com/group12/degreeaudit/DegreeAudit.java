package com.group12.degreeaudit;

import com.group12.degreeaudit.Administration.CourseList;

public class DegreeAudit
{
    public static void main(String args[])
    {
        System.out.println("Hello Overworld");
        CourseList temp = new CourseList("resources/CourseList.json");
    }
}
