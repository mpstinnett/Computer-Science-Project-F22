package com.group12.degreeaudit.Administration;

import java.util.ArrayList;
import java.util.List;

import com.group12.degreeaudit.Course;
import com.group12.degreeaudit.Student;

/**
 * Description: DegreePlanner - Houses degree planner methods
 */
public class DegreePlanner 
{
    private Student student;
    private CourseList courseList;

    /** Description: DegreePlanner constructor - Creates a degree planning session from a student, a course list, and a degree lists
     * @param   student    Student object containing the student to degree plan with (normally an empty student)
     * @param   courseList  CourseList of courses to use in the degree planning session
     * @param   degreeList  DegreeList of degrees to use in the degree planning session
    */
    public DegreePlanner(Student student, CourseList courseList, DegreeList degreeList)
    {
        this.student = student;
        this.courseList = courseList;
    }

     /** Description:
     * Gets a list of all possible courses the student is able to take based on
     *      courses the student has taken.
     * There are 3 conditions that must be met for a course to be listed:
     *      1. The course has to be an active course.
     *      2. The course's prerequisites must be met.
     *      3. The course hasn't already been taken.
     * @param   courseType    Character of the course type (A, E, or C)
     * @return  List of JSONCourses that match the coursetype and above description
    */
    public List<JSONCourse> getPossibleCourses(char courseType) {
        List<JSONCourse> possibleCourses = new ArrayList<>();
        for(JSONCourse jsonCourse : courseList.GetCourseList()) {
            if(jsonCourse.getActiveStatus() && checkPreReqCondition(jsonCourse) && !hasTakenCourse(jsonCourse))
            {
                switch(courseType)
                {
                    case 'c':
                    case 'C':
                        if(jsonCourse.getClassType() == 'C')
                        {
                            possibleCourses.add(jsonCourse);
                        }
                        break;
                    case 'e':
                    case 'E':
                        if(jsonCourse.getClassType() == 'C' || jsonCourse.getClassType() == 'E')
                        {
                            possibleCourses.add(jsonCourse);
                        }
                        break;
                    case 'a':
                    case 'A':
                        if(jsonCourse.getClassType() == 'A')
                        {
                            possibleCourses.add(jsonCourse);
                        }
                        break;
                }
            }
        }
        return possibleCourses;
    }

    /** Description: getAllCourses - Gets all courses in the courselist
     * @return List of JSONCourses (All)
    */
    public List<JSONCourse> getAllCourses() {
        return courseList.GetCourseList();
    }

    /** Description: getDegreeTrack - Gets the degree track of the student
     * @return JSONDegree degree track from the student
    */
    public JSONDegree getDegreeTrack() {
        return student.getDegreeTrack();
    }

    /** Description: checkPreReqCondition - Checks if the given JSONCourse is able to be taken based on its Pre-Reqs
      * @param  jsonCourse   JSONCourse to check if pre-reqs are met
      * @return True - Pre-reqs met. False - Pre-reqs not met
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

    /** Description: hasTakenCourse - Checks if the given JSONCourse has been taken by the student.
      * @param  jsonCourse   JSONCourse to check if the student has taken
      * @return True - Student has taken the course. False - Stuent has not taken the course.
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
