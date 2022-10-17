package com.group12.degreeaudit;

import java.util.List;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.JSONCourse;

public class DegreeAudit
{
    public static void main(String args[])
    {
        String transcriptFilePath = "resources\\TSRPT_Sample2.txt";
        TranscriptScanner transcriptScanner = new TranscriptScanner(transcriptFilePath);

        Student student = transcriptScanner.scanTranscript();
        CourseList temp = new CourseList("resources/CourseList.json");
        List<JSONCourse> possibleCourses = student.getPossibleCourses(temp.GetCourseList());
        for(JSONCourse jsonCourse : possibleCourses)
            System.out.println(jsonCourse.getCourseNumber());
    }
}
