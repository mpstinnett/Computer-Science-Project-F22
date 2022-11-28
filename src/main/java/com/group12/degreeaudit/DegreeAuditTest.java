package com.group12.degreeaudit;

import java.util.List;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.DegreeList;
import com.group12.degreeaudit.Administration.JSONCourse;
import com.group12.degreeaudit.Audit.DegreeAudit;
import com.group12.degreeaudit.Administration.DegreePlanner;
import com.group12.degreeaudit.Administration.FileActions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import java.util.Collections;

public class DegreeAuditTest
{
    public static void main(String args[])
    {
        String transcriptFilePath = "resources/Transfer.txt";
        CourseList courseList = new CourseList("resources/CourseList.json");
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        TranscriptScanner transcriptScanner = new TranscriptScanner(transcriptFilePath, courseList);
        Student student = transcriptScanner.scanTranscript();
        System.out.println(student.getCoursesTaken());
        // student.setDegreeTrack(degreeList.GetDegreeList().get(degreeList.FindDegreeInList("Systems")));
        // DegreePlanner degreePlanner = new DegreePlanner(student, courseList, degreeList);

        // List<JSONCourse> possibleCourses = degreePlanner.getPossibleCourses('C');
        
        
        // //Testing
        // //System.out.println("Possible Classes to Take: ");
        // for(JSONCourse jsonCourse : possibleCourses)
        //     //System.out.println(jsonCourse.getCourseNumber() + " - " + jsonCourse.getCourseName() + " - " + jsonCourse.getClassType());

        // //System.out.println("\nAll Classes: ");
        // for(JSONCourse courses : degreePlanner.getAllCourses())
        // {
        //     //System.out.println(courses.getCourseNumber() + " - " + courses.getCourseName() + " - " + courses.getClassType());
        // }

        // //System.out.println("\nDegree Track: ");
        // //System.out.println(degreePlanner.getDegreeTrack().getDegreeName());

        // DegreeAudit audit = new DegreeAudit(student, courseList);
        // Report.createAuditReport(audit.doAudit(), student.getID());
        // Report.createDegreePlan(student);
        // FileActions fA = new FileActions(courseList, degreeList);
        // fA.exportStudent(student);
    }

    
}

