package com.group12.degreeaudit;

import java.util.List;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.DegreeList;
import com.group12.degreeaudit.Administration.JSONCourse;
import com.group12.degreeaudit.Administration.DegreePlanner;

public class DegreeAudit
{
    public static void main(String args[])
    {
        String transcriptFilePath = "resources\\TSRPT_Sample2.txt";
        TranscriptScanner transcriptScanner = new TranscriptScanner(transcriptFilePath);
        Student student = transcriptScanner.scanTranscript();
        CourseList courseList = new CourseList("resources/CourseList.json");
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");

        student.setDegreeTrack(degreeList.GetDegreeList().get(degreeList.FindDegreeInList("Systems")));
        DegreePlanner degreePlanner = new DegreePlanner(student, courseList, degreeList);

        List<JSONCourse> possibleCourses = degreePlanner.getPossibleCourses('A');
        
        
        //Testing
        System.out.println("Possible Classes to Take: ");
        for(JSONCourse jsonCourse : possibleCourses)
            System.out.println(jsonCourse.getCourseNumber() + " - " + jsonCourse.getCourseName() + " - " + jsonCourse.getClassType());

        System.out.println("\nAll Classes: ");
        for(JSONCourse courses : degreePlanner.getAllCourses())
        {
            System.out.println(courses.getCourseNumber() + " - " + courses.getCourseName() + " - " + courses.getClassType());
        }

        System.out.println("\nDegree Track: ");
        System.out.println(degreePlanner.getDegreeTrack().getDegreeName());


    }
}
