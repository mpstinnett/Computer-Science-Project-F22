package com.group12.degreeaudit.Administration;

import java.util.ArrayList;

/**
 * Description: JSONDegree - Class to house the fields and methods associated with degree plans
 * Used when Exporting and Importing degree plans in JSON
 */
public class JSONDegree 
{
    private String degreeName;              //Degree Name Ex: Systems
    private String coreRequirementAmount;   //Required amount of core classes
    private String coreGPARequirement;      //Required GPA core requirement
    private boolean coreAllowSeventhElective;   //Allow a 7th elective to bypass the core GPA (>3.0 but < requirement)
    private String electiveRequirementAmount;   //Required amount of elective classes
    private String electiveGPARequirement;      //Required GPA of electives
    private boolean electiveAllowOneLowerCourse;    //Allow a lower level course as an elective (Only if listed)
    private String[] electivesAcceptedLowerCourses; //List of acceptable lower level courses to fill an elective
    private String overallGPARequirement;       //Overall GPA (All 5xxx and 6xxx courses)
    private String[] coreClassListRequirement;  //List of required core classes
    private ArrayList<String> optionsCoreClassListRequirement;    //List of optional core classes (to satisfy the total required beyond the listed required)
    private String[] electiveClassListRequirement;      //Required electives to satisfy the degree track
    private boolean activeStatus;   //If the degree is able to be selected in dropdowns (Active to enroll into)

    /** Description: JSONDegree default constructor - Sets all values to nulls or empty values
    */
    public JSONDegree()
    {
        this.degreeName = "No Degree Track Chosen";
        this.coreRequirementAmount = "0";
        this.coreGPARequirement = "0";
        this.coreAllowSeventhElective = false;
        this.electiveRequirementAmount = "0";
        this.electiveGPARequirement = "0";
        this.electiveAllowOneLowerCourse = false;
        this.electivesAcceptedLowerCourses = null;
        this.overallGPARequirement = "0";
        this.coreClassListRequirement = null;
        this.optionsCoreClassListRequirement = new ArrayList<String>();
        this.electiveClassListRequirement = null;
        this.activeStatus = false;
    }

    /** Description: JSONDegree constructor - Sets all values to parameter values, creating the degree
      * @param  degreeName  String for the name of the degree
      * @param  coreRequirementAmount   String for the required amount of core courses
      * @param  coreGPARequirement  String for the core gpa requirement
      * @param  coreAllowSeventhElective    Boolean for if there can be a 7th elective to bypass a core gpa greater than 3.0 but less than 3.19
      * @param  electiveRequirementAmount   String for the total number of electives required
      * @param  electiveGPARequirement  String for the elective gpa requirement
      * @param  electiveAllowOneLowerCourse Boolean for if lower courses can be used as electives
      * @param  electivesAcceptedLowerCourses   String array for the accepted 5XXX courses to count as electives
      * @param  overallGPARequirement   String for the overall gpa requirement
      * @param  coreClassListRequirement    String array for the core class requirements
      * @param  optionsCoreClassListRequirement Arraylist of Strings for the optional core classes
      * @param  electiveClassListRequirement    String array for the required electives
      * @param  activeStatus    Boolean for if the degree can be taken or not (dropdowns)
    */
    public JSONDegree(String degreeName, String coreRequirementAmount, String coreGPARequirement, 
             boolean coreAllowSeventhElective, String electiveRequirementAmount,
            String electiveGPARequirement, boolean electiveAllowOneLowerCourse,
            String[] electivesAcceptedLowerCourses, String overallGPARequirement, String[] coreClassListRequirement, 
            ArrayList<String> optionsCoreClassListRequirement, String[] electiveClassListRequirement, boolean activeStatus)
    {
       this.degreeName = degreeName;
       this.coreRequirementAmount = coreRequirementAmount;
       this.coreGPARequirement = coreGPARequirement;
       this.coreAllowSeventhElective = coreAllowSeventhElective;
       this.electiveRequirementAmount = electiveRequirementAmount;
       this.electiveGPARequirement = electiveGPARequirement;
       this.electiveAllowOneLowerCourse = electiveAllowOneLowerCourse;
       this.electivesAcceptedLowerCourses = electivesAcceptedLowerCourses;
       this.overallGPARequirement = overallGPARequirement;
       this.coreClassListRequirement = coreClassListRequirement;
       this.optionsCoreClassListRequirement = optionsCoreClassListRequirement;
       this.electiveClassListRequirement = electiveClassListRequirement;
       this.activeStatus = activeStatus;
    }

    /** Description: JSONDegree copy constructor - Copies all values from a JSONDegree
      * @param copyDegree JSONDegree to copy
    */
    public JSONDegree(JSONDegree copyDegree)
    {
        this.degreeName = copyDegree.getDegreeName();
        this.coreRequirementAmount = copyDegree.getCoreRequirementAmount();
        this.coreGPARequirement = copyDegree.getCoreGPARequirement();
        this.coreAllowSeventhElective = copyDegree.getCoreAllowSeventhElective();
        this.electiveRequirementAmount = copyDegree.getElectiveRequirementAmount();
        this.electiveGPARequirement = copyDegree.getElectiveGPARequirement();
        this.electiveAllowOneLowerCourse = copyDegree.getElectiveAllowOneLowerCourse();
        this.electivesAcceptedLowerCourses = copyDegree.getElectivesAcceptedLowerCourses();
        this.overallGPARequirement = copyDegree.getOverallGPARequirement();
        this.coreClassListRequirement = copyDegree.getCoreClassListRequirement();
        this.optionsCoreClassListRequirement = copyDegree.getOptionsCoreClassListRequirement();
        this.electiveClassListRequirement = copyDegree.getElectiveClassListRequirement();
        this.activeStatus = copyDegree.getActiveStatus();
    }

    /** Description: getDegreeName - Returns the degree name
      * @return String with the degree name
    */
    public String getDegreeName()
    {
        return degreeName;
    }

    /** Description: setDegreeName - Sets the degree name.
      * @param degreeName Sets the degree name
    */
    public void setDegreeName(String degreeName)
    {
        this.degreeName = degreeName;
    }

    /** Description: getCoreRequirementAmount - Returns the required amount of core classes to take.
      * @return String with the required core amount of core classes
    */
    public String getCoreRequirementAmount()
    {
        return coreRequirementAmount;
    }

    /** Description: setCoreRequirementAmount - Sets the required amount of core classes to take.
      * @param coreRequirementAmount Sets the degree core requirement amount
    */
    public void setCoreRequirementAmount(String coreRequirementAmount)
    {
        this.coreRequirementAmount = coreRequirementAmount;
    }

    /** Description: getCoreGPARequirement - Returns the required gpa requirement of core classes.
      * @return String with the required core GPA
    */
    public String getCoreGPARequirement()
    {
        return coreGPARequirement;
    }
    
    /** Description: setCoreGPARequirement - Sets the required gpa requirement of core classes.
      * @param coreGPARequirement Sets the degree core gpa requirement
    */
    public void setCoreGPARequirement(String coreGPARequirement)
    {
        this.coreGPARequirement = coreGPARequirement;
    }

    /** Description: getCoreAllowSeventhElective - Returns if the core gpa requirement can be bypassed with a 7th elective.
      * @return Boolean with the allowance of a 7th elective to bypass core gpa requirements
    */
    public boolean getCoreAllowSeventhElective()
    {
        return coreAllowSeventhElective;
    }
    
    /** Description: setCoreAllowSeventhElective - Sets if the core gpa requirement can be bypassed with a 7th elective.
      * @param coreAllowSeventhElective Sets the degree allowing of a 7th elective to bypass core gpa requirements
    */
    public void setCoreAllowSeventhElective(boolean coreAllowSeventhElective)
    {
        this.coreAllowSeventhElective = coreAllowSeventhElective;
    }
    
    /** Description: getElectiveRequirementAmount - Returns the required amount of electives classes to take.
      * @return String with the required amount of elective courses
    */
    public String getElectiveRequirementAmount()
    {
        return electiveRequirementAmount;
    }
    
    /** Description: setElectiveRequirementAmount - Sets the required amount of electives classes to take.
      * @param electiveRequirementAmount Sets the requirement amount of elective courses
    */
    public void setElectiveRequirementAmount(String electiveRequirementAmount)
    {
        this.electiveRequirementAmount = electiveRequirementAmount;
    }

    /** Description: getElectiveGPARequirement - Returns the required gpa requirement of elective classes.
      * @return String with the required elective GPA
    */
    public String getElectiveGPARequirement()
    {
        return electiveGPARequirement;
    }
    
    /** Description: setElectiveGPARequirement - Sets the required gpa requirement of elective classes.
      * @param electiveGPARequirement Sets the required elective gpa
    */
    public void setElectiveGPARequirement(String electiveGPARequirement)
    {
        this.electiveGPARequirement = electiveGPARequirement;
    }
    
    /** Description: getElectiveAllowOneLowerCourse - Returns if it is allowed to have a lower level elective count towards a completed course and gpa.
      * @return Boolean with if there can be a single lower level (5XXX) course in the electives
    */
    public boolean getElectiveAllowOneLowerCourse()
    {
        return electiveAllowOneLowerCourse;
    }

    /** Description: setElectiveAllowOneLowerCourse - Sets if it is allowed to have a lower level elective count towards a completed course and gpa.
      * @param electiveAllowOneLowerCourse Sets the allowance of a 5XXX course to be counted as an elective
    */
    public void setElectiveAllowOneLowerCourse(boolean electiveAllowOneLowerCourse)
    {
        this.electiveAllowOneLowerCourse = electiveAllowOneLowerCourse;
    }
    
    /** Description: getElectivesAcceptedLowerCourses - Returns a list of approved lower level coursees for electives.
      * @return String array with the approved lower level (5XXX) courses that can be an elective
    */
    public String[] getElectivesAcceptedLowerCourses()
    {
        if (electivesAcceptedLowerCourses.length == 0) {
            return new String[0];
        }
        return electivesAcceptedLowerCourses;
    }
    
    /** Description: setElectivesAcceptedLowerCourses - Sets a list of approved lower level coursees for electives.
      * @param electivesAcceptedLowerCourses Sets the accepted 5XXX courses to be counted as an elective
    */
    public void setElectivesAcceptedLowerCourses(String[] electivesAcceptedLowerCourses)
    {
        this.electivesAcceptedLowerCourses = electivesAcceptedLowerCourses;
    }
    
    /** Description: getOverallGPARequirement - Returns the overall gpa (all 5xxx and 6xxx) requirement.
      * @return String with the overall GPA requirement
    */
    public String getOverallGPARequirement()
    {
        return overallGPARequirement;
    }
    
    /** Description: setOverallGPARequirement - Sets the overall gpa (all 5xxx and 6xxx) requirement.
      * @param overallGPARequirement Sets the overall GPA requirement
    */
    public void setOverallGPARequirement(String overallGPARequirement)
    {
        this.overallGPARequirement = overallGPARequirement;
    }
    
    /** Description: getCoreClassListRequirement - Returns the class list of required core classes.
      * @return String array with the required core classes
    */
    public String[] getCoreClassListRequirement()
    {
        if (coreClassListRequirement.length == 0) {
            return new String[0];
        }
        return coreClassListRequirement;
    }
    
    /** Description: setCoreClassListRequirement - Sets the class list of required core classes.
      * @param coreClassListRequirement Sets the list of required core classes
    */
    public void setCoreClassListRequirement(String[] coreClassListRequirement)
    {
        this.coreClassListRequirement = coreClassListRequirement;
    }

    /** Description: getOptionsCoreClassListRequirement - Returns the class list of core classes that can be used to satisfy the required amount
      * past the cores required to take.
      * @return Arraylist of Strings with the optional core classes
    */
    public ArrayList<String> getOptionsCoreClassListRequirement()
    {
        return optionsCoreClassListRequirement;
    }
     
    /** Description: setOptionsCoreClassListRequirement - Sets the class list of core classes that can be used to satisfy the required amount
      * past the cores required to take.
      * @param optionsCoreClassListRequirement Sets the optional core class list
    */
    public void setOptionsCoreClassListRequirement(ArrayList<String> optionsCoreClassListRequirement)
    {
        this.optionsCoreClassListRequirement = optionsCoreClassListRequirement;
    }
    
    /** Description: getElectiveClassListRequirement - Returns the class list of required elective classes.
      * @return String array with the required electives
    */
    public String[] getElectiveClassListRequirement()
    {
        if (electiveClassListRequirement.length == 0) {
            return new String[0];
        }
        return electiveClassListRequirement;
    }
    
    /** Description: setElectiveClassListRequirement - Sets the class list of required elective classes.
      * @param electiveClassListRequirement Sets the elective required courses
    */
    public void setElectiveClassListRequirement(String[] electiveClassListRequirement)
    {
        this.electiveClassListRequirement = electiveClassListRequirement;
    }
    
    /** Description: getActiveStatus - Returns if the degree track is active - Meaning a student may enroll into it
      * @return Boolean with the active status of the degree
    */
    public boolean getActiveStatus()
    {
        return activeStatus;
    }
    
    /** Description: setActiveStatus - Sets if the degree track is active - Meaning a student may enroll into it
      * @param activeStatus Sets the active status of the degree track
    */
    public void setActiveStatus(boolean activeStatus)
    {
        this.activeStatus = activeStatus;
    }
    
    /** Description: toString Override - Returns a string with the degree information
     * @return String with the degree information
    */
    @Override
    public String toString()
    {
        String returnString = "";   //Create an empty string

        //Append all the information of the degree track to the string
        returnString += "\nDegree Track: " + getDegreeName()
            + "\nRequired Core Classes: " + getCoreRequirementAmount()
            + "\nRequired Core GPA: " + getCoreGPARequirement()
            + "\nAllow 7th Elective if Core GPA >=3.0 but <3.19: " + getCoreAllowSeventhElective()
            + "\nRequired Elective Classes: " + getElectiveRequirementAmount()
            + "\nRequired Elective GPA:  " + getElectiveGPARequirement()
            + "\nAllow One 5xxx Elective Course: " + getElectiveAllowOneLowerCourse()
            + "\nApproved Lower Level Courses: ";

        if(getElectivesAcceptedLowerCourses() != null)
        {
            for(int j = 0; j < getElectivesAcceptedLowerCourses().length; j++)
            {
                returnString += "\n  " + getElectivesAcceptedLowerCourses()[j];
            }
        }
        else
        {
            returnString += "\n  None";
        }
        returnString += "\nRequired Overall GPA (5xxx+): " + getOverallGPARequirement()
            + "\nRequired Core Class List: ";

        if(getCoreClassListRequirement() != null)
        {
            for(int j = 0; j < getCoreClassListRequirement().length; j++)
            {
                returnString += "\n  " + getCoreClassListRequirement()[j];
            }
        }
        else
        {
            returnString += "\n  None";
        }

        returnString += "\nOptional Core List: ";
        if(getOptionsCoreClassListRequirement() != null)
        {
            for(int j = 0; j < getOptionsCoreClassListRequirement().size(); j++)
            {
                returnString += "\n  " + getOptionsCoreClassListRequirement().get(j);
            }
        }
        else
        {
            returnString += "\n  None";
        }

        returnString += "\nRequired Elective Class List: ";
        if(getElectiveClassListRequirement() != null)
        {
            for(int j = 0; j < getElectiveClassListRequirement().length; j++)
            {
                returnString += "\n  " + getElectiveClassListRequirement()[j];
            }
        }
        else
        {
            returnString += "\n  None";
        }

        returnString += "\nActive Course (Able to be taken): " + activeStatus;


        return returnString;    //Return the string with all the degree tracks information
    }
}
