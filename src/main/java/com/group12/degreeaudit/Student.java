package com.group12.degreeaudit;
import java.util.ArrayList;
import java.util.List;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.DegreePlanner;
import com.group12.degreeaudit.Administration.JSONCourse;
import com.group12.degreeaudit.Administration.JSONDegree;

public class Student {
    private String name;
    private String ID;
    private String program;
    private String semesterAdmitted;
    private JSONDegree degreeTrack;
    private String anticipatedGraduation;
    private boolean fastTrack;
    private List<Course> coursesTaken = new ArrayList<Course>();
    private boolean hasThesis = false;

    public Student(String name, String ID, String program, String semesterAdmitted, List<Course> coursesTaken) {
        this.name = name;
        this.ID = ID;
        this.program = program;
        this.semesterAdmitted = semesterAdmitted;
        this.coursesTaken = coursesTaken;
        setHasThesis();
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

    public void removeCourse(Course course) {
        coursesTaken.remove(course);
    }

    public void setDegreeTrack(JSONDegree degreeTrack)
    {
        this.degreeTrack = degreeTrack;
    }

    public void setAnticipatedGraduation(String anticipatedGraduation)
    {
        this.anticipatedGraduation = anticipatedGraduation;
    }

    public String getAnticipatedGraduation()
    {
        return anticipatedGraduation;
    }

    public Course getCourseGivenCourseNumber(String courseNumber) {
        for(Course course : coursesTaken) {
            if(course.getCourseNumber().equals(courseNumber)) {
                return course;
            }
        }
        return null;
    }

    public JSONDegree getDegreeTrack()
    {
        if(degreeTrack != null)
            return degreeTrack;
        else
            return new JSONDegree();
    }

    public String[] getAllElectivesNotTaken(CourseList courseList, JSONDegree degreeTrack)
    {
        List<JSONCourse> allCoursesNotTaken = new ArrayList<JSONCourse>();
        for(JSONCourse jsonCourse : courseList.GetCourseList())
        {
            allCoursesNotTaken.add(jsonCourse);
            for(Course course : getCoursesTaken())
            {   
                if(course.getCourseNumber().equals(jsonCourse.getCourseNumber()))
                {
                    allCoursesNotTaken.remove(jsonCourse);
                    break;
                }
            }
        }
        
        for(int i = 0; i < allCoursesNotTaken.size(); i++)
        {
            if(i < allCoursesNotTaken.size())
            {
                JSONCourse jsonCourse = allCoursesNotTaken.get(i);
                if(jsonCourse.getCourseNumber().split(" ")[1].charAt(0) != '6')
                {
                    boolean found = false;
                    for(String approved5XCourse : degreeTrack.getElectivesAcceptedLowerCourses())
                    {
                        if(jsonCourse.getCourseNumber().equals(approved5XCourse))
                        {
                            found = true;
                            break;
                        }
                    }
                    if(!found)
                    {
                        allCoursesNotTaken.remove(jsonCourse);
                        i--;
                    }
                }
            }
            else
            {
                break;
            }
        }

        String[] returnAllElectivesNotTaken = new String[allCoursesNotTaken.size()];

        for(int i = 0; i < allCoursesNotTaken.size(); i++)
        {
            returnAllElectivesNotTaken[i] = allCoursesNotTaken.get(i).getCourseNumber();
        }

        return returnAllElectivesNotTaken;
    }

    public List<Course> matchCoreCourses(JSONDegree degreeTrack){ 
        List<Course> matchedCoreCourses = new ArrayList<Course>();
        for(Course course: coursesTaken)
        {
            for(String JSONcourse : degreeTrack.getCoreClassListRequirement())
            {
                if(course.getCourseNumber().equals(JSONcourse))
                    matchedCoreCourses.add(course);
            }            
        }
        return matchedCoreCourses;
    }

    public List<Course> matchCoreOptionCourses(JSONDegree degreeTrack){ 
        List<Course> matchedCoreOptionCourses = new ArrayList<Course>();
        for(Course course: coursesTaken)
        {
            for(String JSONcourse : degreeTrack.getOptionsCoreClassListRequirement())
            {
                if(course.getCourseNumber().equals(JSONcourse))
                    matchedCoreOptionCourses.add(course);
            }            
        }
        return matchedCoreOptionCourses;
    }

    private List<Course> matchRequiredElectiveCourses(JSONDegree degreeTrack)
    { 
        List<Course> matchedRequiredElectiveCourses = new ArrayList<Course>();
        for(Course course: coursesTaken)
        {
            for(String JSONcourse : degreeTrack.getElectiveClassListRequirement())
            {
                if(course.getCourseNumber().equals(JSONcourse))
                {
                    matchedRequiredElectiveCourses.add(course);
                }
            }
        }
        return matchedRequiredElectiveCourses;
    }

    public List<Course> matchElectiveCourses(JSONDegree degreeTrack, String type)
    { 
        List<Course> matchedRequired = matchRequiredElectiveCourses(degreeTrack);
        List<Course> matchedAdditional = matchAdditionalElectiveCourses(degreeTrack);

        List<Course> matchedRequiredElectiveCourses = matchedRequired;
        matchedRequiredElectiveCourses.addAll(matchedAdditional);

        List<Course> matchedElectiveCourses = new ArrayList<Course>();

        if(matchedRequiredElectiveCourses.size() >= Integer.parseInt(degreeTrack.getElectiveRequirementAmount()))
        {
            for(int i = matchedRequiredElectiveCourses.size() - 1; i >= Integer.parseInt(degreeTrack.getElectiveRequirementAmount()); i--)
            {
                matchedElectiveCourses.add(matchedRequiredElectiveCourses.get(i));
                matchedRequiredElectiveCourses.remove(i);
            }
        }

        if(type.equals("top5"))
        {
            return matchedRequiredElectiveCourses;
        }
        else if (type.equals("past5"))
        {
            return matchedElectiveCourses;
        }
        else
        {
            return null;
        }
        
    }

    private List<Course> matchAdditionalElectiveCourses(JSONDegree degreeTrack){ 
        List<Course> matchedElectiveCourses = new ArrayList<Course>();
        List<String> electiveCourses = new ArrayList<String>();

        CourseList courseList = new CourseList("resources/CourseList.json");
        for(JSONCourse JSONcourse : courseList.GetCourseList())
        {
            if(JSONcourse.getClassType() == 'E' || JSONcourse.getClassType() == 'C' ){
                electiveCourses.add(JSONcourse.getCourseNumber());
            }
        }

        for(Course course: coursesTaken)
        {

            if(electiveCourses.contains(course.getCourseNumber())){
                matchedElectiveCourses.add(course);
            }

            for(String JSONcourse : degreeTrack.getCoreClassListRequirement())
            {
                if(course.getCourseNumber().equals(JSONcourse))
                    matchedElectiveCourses.remove(course);
            }  
            for(String JSONcourse : degreeTrack.getOptionsCoreClassListRequirement())
            {
                if(course.getCourseNumber().equals(JSONcourse))
                    matchedElectiveCourses.remove(course);
            }   
            for(String JSONcourse : degreeTrack.getElectivesAcceptedLowerCourses())
            {
                if(course.getCourseNumber().equals(JSONcourse))
                    matchedElectiveCourses.remove(course);
            }             
        }
        List<Course> matchedRequired = matchRequiredElectiveCourses(degreeTrack);
        for(Course courseAll : matchedElectiveCourses)
        {
            if(matchedRequired.contains(courseAll))
            {
                matchedElectiveCourses.remove(courseAll);
            }
        }
        matchedElectiveCourses.addAll(matchApproved5XElectiveCourses(degreeTrack));
        return matchedElectiveCourses;
    }

    public List<Course> matchAdmissionCourses(JSONDegree degreeTrack)
    {
        List<Course> matchAdmissionCourses = new ArrayList<Course>();

        for(Course course : coursesTaken)
        {
            if(course.getClassType() == 'A')
                matchAdmissionCourses.add(course);
        }

        return matchAdmissionCourses;
    }

    public List<Course> matchApproved5XElectiveCourses(JSONDegree degreeTrack){ 
        List<Course> matchApproved5XElectiveCourses = new ArrayList<Course>();
        for(Course course: coursesTaken)
        {
            for(String JSONcourse : degreeTrack.getElectivesAcceptedLowerCourses())
            {
                if(course.getCourseNumber().equals(JSONcourse))
                    matchApproved5XElectiveCourses.add(course);
            }            
        }
        return matchApproved5XElectiveCourses;
	}
	
    public void setHasThesis() {
        for (Course course : coursesTaken) {
            if (course.getCourseNumber().equals("CS 6V98")) {
                hasThesis = true;
            } 
        }
    }

    public boolean getFastTrack()
    {
        return fastTrack;
    }

    public void setFastTrack(boolean fastTrack)
    {
        this.fastTrack = fastTrack;
    }

    public void setThesis(boolean thesis) {
        hasThesis = thesis;
    }

    public boolean getThesis() {
        return hasThesis;
    }

    public boolean getHasThesis() {
        return hasThesis;
    }

    public String toString() {
        return "Name: " + name
                + "\nID: " + ID
                + "\nProgram: " + program
                + "\nSemester Admitted: " + semesterAdmitted
                + "\nHas Thesis: " + hasThesis;
    }
}