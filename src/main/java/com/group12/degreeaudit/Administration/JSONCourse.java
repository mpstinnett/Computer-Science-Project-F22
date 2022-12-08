package com.group12.degreeaudit.Administration;

/**
 * Description: JSONCourse - Course class that holds information for a course in the administration
 */
public class JSONCourse 
{
    private String courseNumber;
    private String courseName;
    private String courseDescription;
    private String[] coursePreReqs;
    private char classType;
    private boolean activeStatus;

    /** Description: JSONCourse default constructor - Does nothing
    */
    public JSONCourse() {};

    /** Description: JSONCourse constructor - sets all the values for a course in administration
     * @param   courseNumber  String for the course number
     * @param   courseName  String for the course name
     * @param   courseDescription   String for the course description
     * @param   prereqs String array for all the pre-requisite courses
     * @param   classType   Character for the class type (A, C, E)
     * @param   activeStatus    Boolean for if the class can be taken or not (dropdowns show or not)
    */
    public JSONCourse(String courseNumber, String courseName, String courseDescription, String[] prereqs, char classType, boolean activeStatus)
    {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.coursePreReqs = prereqs;
        this.classType = classType;
        this.activeStatus = activeStatus;
    }

    /** Description: JSONCourse constructor - Creates a JSONCourse with only a string for the course number 
     * @param   courseNumber  String for the course number
    */
    public JSONCourse(String courseNumber)
    {
        this.courseNumber = courseNumber;
    }

    /** Description: JSONCourse copy constructor - Copies a JSONCourse
     * @param   copyCourse  JSONCourse to copy from
    */
    public JSONCourse(JSONCourse copyCourse)
    {
        this.courseNumber = copyCourse.getCourseNumber();
        this.courseName = copyCourse.getCourseName();
        this.courseDescription = copyCourse.getCourseDescription();
        this.coursePreReqs = copyCourse.getCoursePreReqs();
        this.classType = copyCourse.getClassType();
        this.activeStatus = copyCourse.getActiveStatus();
    }
    
    /** Description: getCourseNumber - Returns the course number
     * @return String for the course number
    */
    public String getCourseNumber()
    {
        return courseNumber;
    }

    /** Description: setCourseNumber - Sets the course number
     * @param courseNumber String to set the course number to
    */
    public void setCourseNumber(String courseNumber)
    {
        this.courseNumber = courseNumber;
    }

    /** Description: getCourseName - Returns the course name
     * @return String for the course name
    */
    public String getCourseName()
    {
        return courseName;
    }

    /** Description: setCourseName - Sets the course name
     * @param courseName String to set the course name to
    */
    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    /** Description: getCourseDescription - Returns the course description
     * @return String for the course description
    */
    public String getCourseDescription()
    {
        return courseDescription;
    }

    /** Description: setCourseDescription - Sets the course description
     * @param courseDescription String to set the course description to
    */
    public void setCourseDescription(String courseDescription)
    {
        this.courseDescription = courseDescription;
    }

    /** Description: getCoursePreReqs - Returns the course pre-requisites
     * @return String array for the course pre-requsites
    */
    public String[] getCoursePreReqs()
    {
        return coursePreReqs;
    }

    /** Description: coursePreReqs - Sets the course pre-requisites
     * @param coursePreReqs String array to set the course pre-requisites to
    */
    public void setCoursePreReqs(String[] coursePreReqs)
    {
        this.coursePreReqs = coursePreReqs;
    }

    /** Description: getClassType - Returns the course class type (A, C, E)
     * @return Character for the class type (A, C, E)
    */
    public char getClassType()
    {
        return classType;
    }

    /** Description: setClassType - Sets the class type
     * @param classType Character to set the class type to (A, C, E)
    */
    public void setClassType(char classType)
    {
        this.classType = classType;
    }

    /** Description: getActiveStatus - Returns the course active status
     * @return Boolean for the active status
    */
    public boolean getActiveStatus()
    {
        return activeStatus;
    }

    /** Description: setActiveStatus - Sets the course active status
     * @param activeStatus Boolean to set the active status
    */
    public void setActiveStatus(boolean activeStatus)
    {
        this.activeStatus = activeStatus;
    }

    /** Description: toString Override - Returns a string with the course information
     * @return String with the course information
    */
    @Override
    public String toString()
    {
        if (getCourseName() == null) {
            return getCourseNumber();
        }
        String returnString = "";
        returnString += "\nCourse: " + getCourseNumber()
            + "\nName: " + getCourseName()
            + "\nDescription: " + getCourseDescription()
            + "\nType: " + getClassType()
            + "\nActive: " + getActiveStatus()
            + "\nPrerequisites: ";
        if(getCoursePreReqs() != null)
        {
            for(int j = 0; j < getCoursePreReqs().length; j++)
            {
                returnString += "\n  " + getCoursePreReqs()[j];
            }
        }
        else
        {
            returnString += "\n  None";
        }
        return returnString;
    }
}
