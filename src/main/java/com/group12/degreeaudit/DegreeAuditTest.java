package com.group12.degreeaudit;

import java.util.List;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.DegreeList;
import com.group12.degreeaudit.Administration.JSONCourse;
import com.group12.degreeaudit.Audit.DegreeAudit;
import com.group12.degreeaudit.Administration.DegreePlanner;

public class DegreeAuditTest
{
    public static void main(String args[])
    {
        String transcriptFilePath = "resources/TSRPT_Sample2.txt";
        CourseList courseList = new CourseList("resources/CourseList.json");
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        TranscriptScanner transcriptScanner = new TranscriptScanner(transcriptFilePath, courseList);
        Student student = transcriptScanner.scanTranscript();

        student.setDegreeTrack(degreeList.GetDegreeList().get(degreeList.FindDegreeInList("Systems")));
        DegreePlanner degreePlanner = new DegreePlanner(student, courseList, degreeList);

        List<JSONCourse> possibleCourses = degreePlanner.getPossibleCourses('C');
        
        
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

        DegreeAudit audit = new DegreeAudit(student, courseList);
        System.out.println(audit.getGPA('C', student.getCoursesTaken()));
        System.out.println(audit.coreComplete());
    }

    
}
