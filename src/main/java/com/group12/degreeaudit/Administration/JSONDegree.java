package com.group12.degreeaudit.Administration;

import java.util.ArrayList;

//JSONDegree - Class to house the fields and methods associated with degree plans
//      Used when Exporting and Importing degree plans in JSON
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
    private String overallGPARequirement;       //Overall GPA (All 5xxx & 6xxx courses)
    private String[] coreClassListRequirement;  //List of required core classes
    private ArrayList<String> optionsCoreClassListRequirement;    //List of optional core classes (to satisfy the total required beyond the listed required)
    private String[] electiveClassListRequirement;      //Required electives to satisfy the degree track
    private boolean activeStatus;   //If the degree is able to be selected in dropdowns (Active to enroll into)

    //Empty constructor, sets all values to nulls or empty values
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

    //Constructor with all values to input.
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

    //Copy Constructor, copies all values of a degree track.
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

    //getDegreeName - Returns the degree name.
    public String getDegreeName()
    {
        return degreeName;
    }
    //setDegreeName - Sets the degree name.
    public void setDegreeName(String degreeName)
    {
        this.degreeName = degreeName;
    }

    //getCoreRequirementAmount - Returns the required amount of core classes to take.
    public String getCoreRequirementAmount()
    {
        return coreRequirementAmount;
    }
    //setCoreRequirementAmount - Sets the required amount of core classes to take.
    public void setCoreRequirementAmount(String coreRequirementAmount)
    {
        this.coreRequirementAmount = coreRequirementAmount;
    }

    //getCoreGPARequirement - Returns the required gpa requirement of core classes.
    public String getCoreGPARequirement()
    {
        return coreGPARequirement;
    }
    //setCoreGPARequirement - Sets the required gpa requirement of core classes.
    public void setCoreGPARequirement(String coreGPARequirement)
    {
        this.coreGPARequirement = coreGPARequirement;
    }

    //getCoreAllowSeventhElective - Returns if the core gpa requirement can be bypassed with a 7th elective.
    public boolean getCoreAllowSeventhElective()
    {
        return coreAllowSeventhElective;
    }
    //setCoreAllowSeventhElective - Sets if the core gpa requirement can be bypassed with a 7th elective.
    public void setCoreAllowSeventhElective(boolean coreAllowSeventhElective)
    {
        this.coreAllowSeventhElective = coreAllowSeventhElective;
    }
    
    //getElectiveRequirementAmount - Returns the required amount of electives classes to take.
    public String getElectiveRequirementAmount()
    {
        return electiveRequirementAmount;
    }
    //setElectiveRequirementAmount - Sets the required amount of electives classes to take.
    public void setElectiveRequirementAmount(String electiveRequirementAmount)
    {
        this.electiveRequirementAmount = electiveRequirementAmount;
    }

    //getElectiveGPARequirement - Returns the required gpa requirement of elective classes.
    public String getElectiveGPARequirement()
    {
        return electiveGPARequirement;
    }
    //setElectiveGPARequirement - Sets the required gpa requirement of elective classes.
    public void setElectiveGPARequirement(String electiveGPARequirement)
    {
        this.electiveGPARequirement = electiveGPARequirement;
    }
    
    //getElectiveAllowOneLowerCourse - Returns if it is allowed to have a lower level elective count towards a completed course and gpa.
    public boolean getElectiveAllowOneLowerCourse()
    {
        return electiveAllowOneLowerCourse;
    }
    //setElectiveAllowOneLowerCourse - Sets if it is allowed to have a lower level elective count towards a completed course and gpa.
    public void setElectiveAllowOneLowerCourse(boolean electiveAllowOneLowerCourse)
    {
        this.electiveAllowOneLowerCourse = electiveAllowOneLowerCourse;
    }
    
    //getElectivesAcceptedLowerCourses - Returns a list of approved lower level coursees for electives.
    public String[] getElectivesAcceptedLowerCourses()
    {
        if (electivesAcceptedLowerCourses.length == 0) {
            return new String[0];
        }
        return electivesAcceptedLowerCourses;
    }
    //setElectivesAcceptedLowerCourses - Sets a list of approved lower level coursees for electives.
    public void setElectivesAcceptedLowerCourses(String[] electivesAcceptedLowerCourses)
    {
        this.electivesAcceptedLowerCourses = electivesAcceptedLowerCourses;
    }
    
    //getOverallGPARequirement - Returns the overall gpa (all 5xxx & 6xxx) requirement.
    public String getOverallGPARequirement()
    {
        return overallGPARequirement;
    }
    //setOverallGPARequirement - Sets the overall gpa (all 5xxx & 6xxx) requirement.
    public void setOverallGPARequirement(String overallGPARequirement)
    {
        this.overallGPARequirement = overallGPARequirement;
    }
    
    //getCoreClassListRequirement - Returns the class list of required core classes.
    public String[] getCoreClassListRequirement()
    {
        if (coreClassListRequirement.length == 0) {
            return new String[0];
        }
        return coreClassListRequirement;
    }
    //setCoreClassListRequirement - Sets the class list of required core classes.
    public void setCoreClassListRequirement(String[] coreClassListRequirement)
    {
        this.coreClassListRequirement = coreClassListRequirement;
    }

    //getOptionsCoreClassListRequirement - Returns the class list of core classes that can be used to satisfy the required amount
    //      past the cores required to take.
    public ArrayList<String> getOptionsCoreClassListRequirement()
    {
        return optionsCoreClassListRequirement;
    }
    //setOptionsCoreClassListRequirement - Sets the class list of core classes that can be used to satisfy the required amount
    //      past the cores required to take.
    public void setOptionsCoreClassListRequirement(ArrayList<String> optionsCoreClassListRequirement)
    {
        this.optionsCoreClassListRequirement = optionsCoreClassListRequirement;
    }
    
    //getElectiveClassListRequirement - Returns the class list of required elective classes.
    public String[] getElectiveClassListRequirement()
    {
        if (electiveClassListRequirement.length == 0) {
            return new String[0];
        }
        return electiveClassListRequirement;
    }
    //setElectiveClassListRequirement - Sets the class list of required elective classes.
    public void setElectiveClassListRequirement(String[] electiveClassListRequirement)
    {
        this.electiveClassListRequirement = electiveClassListRequirement;
    }
    
    //getActiveStatus - Returns if the degree track is active - Meaning a student may enroll into it
    public boolean getActiveStatus()
    {
        return activeStatus;
    }
    //setActiveStatus - Sets if the degree track is active - Meaning a student may enroll into it
    public void setActiveStatus(boolean activeStatus)
    {
        this.activeStatus = activeStatus;
    }
    
    //toString method - Prints the information of the degree and all its fields.
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
