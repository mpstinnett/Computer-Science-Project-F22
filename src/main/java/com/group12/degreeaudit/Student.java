package com.group12.degreeaudit;
import java.util.ArrayList;
import java.util.List;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.JSONCourse;
import com.group12.degreeaudit.Administration.JSONDegree;

/**
* Description: Student - Holds methods and fields to store and process student information 
*/
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

    /**
    * Description: Student - Constructor that has not parameters and sets fields as empty 
    */
    public Student()
    {
        name = "";
        ID = "";
        program = "";
        semesterAdmitted = "";
    }

    /**
    * Description: Student - Constructor that takes in basic student information like a students name, id, program, their semester admitted, and their courses taken  
    * @param name 
    * @param ID
    * @param program
    * @param semesterAdmitted
    * @param coursesTaken
    */
    public Student(String name, String ID, String program, String semesterAdmitted, List<Course> coursesTaken) {
        this.name = name;
        this.ID = ID;
        this.program = program;
        this.semesterAdmitted = semesterAdmitted;
        this.coursesTaken = coursesTaken;
        setHasThesis();
    }

    /**
    * Description: Gets the name of the Student
    * @return A String containing the name of the student
    */
    public String getName() {
        return name;
    }
    
    /**
    * Description: Sets the name of the Student
    * @param name   String for the name of the student to set
    */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
    * Description: Gets the id of the Student
    * @return A String containing the ID of the student
    */
    public String getID() {
        return ID;
    }
    
    /**
    * Description: Sets the id of the Student
    * @param ID Id of the student to set
    */
    public void setID(String ID) {
        this.ID = ID;
    }
    
    /**
     * Description: gets the program of the student
     * @return A String containing the program of the student
     */
    public String getProgram() {
        return program;
    }
    
    /**
    * Description: Sets the program of the Student
    * @param program    String containing the program of the student to set
    */
    public void setProgram(String program) {
        this.program = program;
    }
    
    /**
     * Description: gets the semester the student was admitted
     * @return A String containing the program of the student
     */
    public String getSemesterAdmitted() {
        return semesterAdmitted;
    }
    
    /**
    * Description: Sets the semester the Student was admitted
    * @param semesterAdmitted   String containing the semester the student was admitted
    */
    public void setSemesterAdmitted(String semesterAdmitted) {
        this.semesterAdmitted = semesterAdmitted;
    }
    
    /**
     * Description: gets the courses a student has taken
     * @return  A list containing the Courses objects for the courses a student has taken
     */
    public List<Course> getCoursesTaken() {
        return coursesTaken;
    }
    
    /**
     * Description: adds a course to a student object
     * @param  course   The Course object to be added to the student
     */
    public void addCourse(Course course) {
        coursesTaken.add(course);
    }
    
    /**
     * Description: remove a course from a student object 
     * @param  course   The Course object to be removed from the student
     */
    public void removeCourse(Course course) {
        coursesTaken.remove(course);
    }
    
    /**
     * Description: set student's degreeTrack
     * @param  degreeTrack The degree track a student is in 
     */
    public void setDegreeTrack(JSONDegree degreeTrack)
    {
        this.degreeTrack = degreeTrack;
    }
    
    /**
     * Description: set student's anticipated graduation date
     * @param  anticipatedGraduation A student's anticipated graduation
     */
    public void setAnticipatedGraduation(String anticipatedGraduation)
    {
        this.anticipatedGraduation = anticipatedGraduation;
    }
    
    /**
     * Description: gets student's anticipated graduation date
     * @return  A String containing student's anticipated graduation
     */
    public String getAnticipatedGraduation()
    {
        return anticipatedGraduation;
    }
    
    /**
     * Description: gets a specific course that a student has taken
     * @param courseNumber A string containing the courseNumber to be searched for
     * @return  A course object if the student has taken the course being searched for or null if they have not
     */
    public Course getCourseGivenCourseNumber(String courseNumber) {
        for(Course course : coursesTaken) {
            if(course.getCourseNumber().equals(courseNumber)) {
                return course;
            }
        }
        return null;
    }
     
    /**
     * Description: gets a student's degree track 
     * @return  A JSONDegree object containing the student's degree track or a new JSONDegree if the student't degree track is null
     */
    public JSONDegree getDegreeTrack()
    {
        if(degreeTrack != null)
            return degreeTrack;
        else
            return new JSONDegree();
    }

    /**
     * Description: takes the list of electives a student has taken, compares it to a degree track, and returns the electives a student has left to take 
     * @param courseList A list of courses a student has taken
     * @param degreeTrack A JSON degree track to be compared to the courseList
     * @return  A String array containing the course numbers of the courses a student has left to take 
     */
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

    /**
     * Description: compares the list of core courses a student has taken to a degree track and looks for overlapping courses
     * @param degreeTrack A JSON degree track to be compared to the courseList
     * @return  A List of Course Objects containing the core courses that are both in courseList and degreeTrack
     */
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
    
    /**
     * Description: compares the list of core option courses a student has taken to a degree track and looks for overlapping courses
     * @param degreeTrack A JSON degree track to be compared to the courseList
     * @return  A List of Course Objects containing the core option courses that are both in courseList and degreeTrack
     */
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
    
    /**
     * Description: compares the list of required elective courses a student has taken to a degree track and looks for overlapping courses
     * @param degreeTrack A JSON degree track to be compared to the courseList
     * @return  A List of Course Objects containing the required elective courses that are both in courseList and degreeTrack
     */
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
    
    /**
     * Description: compares the list of elective courses a student has taken to a degree track and looks for overlapping courses
     * @param degreeTrack A JSON degree track to be compared to the courseList
     * @param type 
     * @return  A List of Course Objects containing the elective courses that are both in courseList and degreeTrack
     */
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
    
    /**
     * Description: compares the list of additional elective courses a student has taken to a degree track and looks for overlapping courses
     * @param degreeTrack A JSON degree track to be compared to the courseList
     * @return  A List of Course Objects containing the additional elective courses that are both in courseList and degreeTrack
     */
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
    
    /**
     * Description: compares the list of admission courses a student has taken to a degree track and looks for overlapping courses
     * @param degreeTrack A JSON degree track to be compared to the courseList
     * @return  A List of Course Objects containing the admission courses that are both in courseList and degreeTrack
     */
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
    
    /**
     * Description: compares the list of approved 5000 Level elective courses a student has taken to a degree track and looks for overlapping courses
     * @param degreeTrack A JSON degree track to be compared to the courseList
     * @return  A List of Course Objects containing the approved 5000 Level elective courses that are both in courseList and degreeTrack
     */
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
	
    /**
     * Description: Goes through the list of courses a student has taken and sets thesis to true if it is a thesis class
     */
    public void setHasThesis() {
        for (Course course : coursesTaken) {
            if (course.getCourseNumber().equals("CS 6V98")) {
                hasThesis = true;
            } 
        }
    }
    
    /**
     * Description: gets whether or not a student is part of the fast track progra
     * @return  A boolean value that is true if they are a fast track student or false if they are not
     */
    public boolean getFastTrack()
    {
        return fastTrack;
    }

    /**
     * Description: set a student's fast track status
     * @param  fastTrack A boolean value containing a student's fast track status
     */
    public void setFastTrack(boolean fastTrack)
    {
        this.fastTrack = fastTrack;
    }

    /**
     * Description: set a student's hasThesis status
     * @param  thesis A boolean value containing a student's thesis status, it is true if they have a thesis and false if they do not
     */
    public void setThesis(boolean thesis) {
        hasThesis = thesis;
    }

    /**
     * Description: gets a student's hasThesis value
     * @return  hasThesis A boolean value containing a student's thesis status, it is true if they have a thesis and false if they do not
     */
    public boolean getThesis() {
        return hasThesis;
    }

    /**
     * Description: gets a student's hasThesis value
     * @return  hasThesis A boolean value containing a student's thesis status, it is true if they have a thesis and false if they do not
     */
    public boolean getHasThesis() {
        return hasThesis;
    }

    /**
     * Description: toString method for the Student class
     * @return  A string containing a student's name, ID, program, semester admitted, and whether or not they have a thesis
     */
    public String toString() {
        return "Name: " + name
                + "\nID: " + ID
                + "\nProgram: " + program
                + "\nSemester Admitted: " + semesterAdmitted
                + "\nHas Thesis: " + hasThesis;
    }
}