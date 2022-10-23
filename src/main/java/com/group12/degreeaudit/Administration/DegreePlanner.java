package com.group12.degreeaudit.Administration;

import java.util.ArrayList;
import java.util.List;

import com.group12.degreeaudit.Course;
import com.group12.degreeaudit.Student;

public class DegreePlanner 
{
    private Student student;
    private CourseList courseList;

    public DegreePlanner(Student student, CourseList courseList, DegreeList degreeList)
    {
        this.student = student;
        this.courseList = courseList;
    }
    /*
     * Gets a list of all possible courses the student is able to take based on
     *      courses the student has taken.
     * There are 3 conditions that must be met for a course to be listed:
     *      1. The course has to be an active course.
     *      2. The course's prerequisites must be met.
     *      3. The course hasn't already been taken.
     */
    public List<JSONCourse> getPossibleCourses() {
        List<JSONCourse> possibleCourses = new ArrayList<>();
        for(JSONCourse jsonCourse : courseList.GetCourseList()) {
            if(jsonCourse.getActiveStatus() && checkPreReqCondition(jsonCourse) && !hasTakenCourse(jsonCourse))
                possibleCourses.add(jsonCourse);
        }
        return possibleCourses;
    }

    public List<JSONCourse> getAllCourses() {
        return courseList.GetCourseList();
    }

    public JSONDegree getDegreeTrack() {
        return student.getDegreeTrack();
    }

    /*
     * Checks if the given JSONCourse is able to be taken based on its Pre-Reqs
     */
    public boolean checkPreReqCondition(JSONCourse jsonCourse) {
        List<Course> takenCourses = student.getCoursesTaken();
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
        List<Course> takenCourses = student.getCoursesTaken();
        for(Course course : takenCourses) {
            if(jsonCourse.getCourseNumber().equals(course.getCourseNumber()))
                return true;
        }
        return false;
    }

}
