package com.group12.degreeaudit.Administration;

public class JSONDegree 
{
    private String degreeName;
    private String coreRequirementAmount;
    private String coreGPARequirement;
    private boolean coreReplaceHighestAttempt;
    private boolean coreAllowSeventhElective;
    private String electiveRequirementAmount;
    private String electiveGPARequirement;
    private boolean electiveReplaceHighestAttempt;
    private boolean electiveAllowOneLowerCourse;
    private String[] electivesAcceptedLowerCourses;
    private String overallGPARequirement;
    private String[] coreClassListRequirement;
    private String[] electiveClassListRequirement;
    private boolean activeStatus;

    public JSONDegree()
    {
        this.degreeName = "No Degree Track Chosen";
       this.coreRequirementAmount = "0";
       this.coreGPARequirement = "0";
       this.coreReplaceHighestAttempt = false;
       this.coreAllowSeventhElective = false;
       this.electiveRequirementAmount = "0";
       this.electiveGPARequirement = "0";
       this.electiveReplaceHighestAttempt = false;
       this.electiveAllowOneLowerCourse = false;
       this.electivesAcceptedLowerCourses = null;
       this.overallGPARequirement = "0";
       this.coreClassListRequirement = null;
       this.electiveClassListRequirement = null;
       this.activeStatus = false;
    }

    public JSONDegree(String degreeName, String coreRequirementAmount, String coreGPARequirement, 
            boolean coreReplaceHighestAttempt, boolean coreAllowSeventhElective, String electiveRequirementAmount,
            String electiveGPARequirement, boolean electiveReplaceHighestAttempt, boolean electiveAllowOneLowerCourse,
            String[] electivesAcceptedLowerCourses, String overallGPARequirement, String[] coreClassListRequirement,
            String[] electiveClassListRequirement, boolean activeStatus)
    {
       this.degreeName = degreeName;
       this.coreRequirementAmount = coreRequirementAmount;
       this.coreGPARequirement = coreGPARequirement;
       this.coreReplaceHighestAttempt = coreReplaceHighestAttempt;
       this.coreAllowSeventhElective = coreAllowSeventhElective;
       this.electiveRequirementAmount = electiveRequirementAmount;
       this.electiveGPARequirement = electiveGPARequirement;
       this.electiveReplaceHighestAttempt = electiveReplaceHighestAttempt;
       this.electiveAllowOneLowerCourse = electiveAllowOneLowerCourse;
       this.electivesAcceptedLowerCourses = electivesAcceptedLowerCourses;
       this.overallGPARequirement = overallGPARequirement;
       this.coreClassListRequirement = coreClassListRequirement;
       this.electiveClassListRequirement = electiveClassListRequirement;
       this.activeStatus = activeStatus;
    }

    public JSONDegree(JSONDegree copyDegree)
    {
        this.degreeName = copyDegree.getDegreeName();
        this.coreRequirementAmount = copyDegree.getCoreRequirementAmount();
        this.coreGPARequirement = copyDegree.getCoreGPARequirement();
        this.coreReplaceHighestAttempt = copyDegree.getCoreReplaceHighestAttempt();
        this.coreAllowSeventhElective = copyDegree.getCoreAllowSeventhElective();
        this.electiveRequirementAmount = copyDegree.getElectiveRequirementAmount();
        this.electiveGPARequirement = copyDegree.getElectiveGPARequirement();
        this.electiveReplaceHighestAttempt = copyDegree.getElectiveReplaceHighestAttempt();
        this.electiveAllowOneLowerCourse = copyDegree.getElectiveAllowOneLowerCourse();
        this.electivesAcceptedLowerCourses = copyDegree.getElectivesAcceptedLowerCourses();
        this.overallGPARequirement = copyDegree.getOverallGPARequirement();
        this.coreClassListRequirement = copyDegree.getCoreClassListRequirement();
        this.electiveClassListRequirement = copyDegree.getElectiveClassListRequirement();
        this.activeStatus = copyDegree.getActiveStatus();
    }

    public String getDegreeName()
    {
        return degreeName;
    }

    public void setDegreeName(String degreeName)
    {
        this.degreeName = degreeName;
    }

    public String getCoreRequirementAmount()
    {
        return coreRequirementAmount;
    }

    public void setCoreRequirementAmount(String coreRequirementAmount)
    {
        this.coreRequirementAmount = coreRequirementAmount;
    }

    public String getCoreGPARequirement()
    {
        return coreGPARequirement;
    }

    public void setCoreGPARequirement(String coreGPARequirement)
    {
        this.coreGPARequirement = coreGPARequirement;
    }

    public boolean getCoreReplaceHighestAttempt()
    {
        return coreReplaceHighestAttempt;
    }

    public void setCoreReplaceHighestAttempt(boolean coreReplaceHighestAttempt)
    {
        this.coreReplaceHighestAttempt = coreReplaceHighestAttempt;
    }

    public boolean getCoreAllowSeventhElective()
    {
        return coreAllowSeventhElective;
    }

    public void setCoreAllowSeventhElective(boolean coreAllowSeventhElective)
    {
        this.coreAllowSeventhElective = coreAllowSeventhElective;
    }
    
    public String getElectiveRequirementAmount()
    {
        return electiveRequirementAmount;
    }

    public void setElectiveRequirementAmount(String electiveRequirementAmount)
    {
        this.electiveRequirementAmount = electiveRequirementAmount;
    }

    public String getElectiveGPARequirement()
    {
        return electiveGPARequirement;
    }

    public void setElectiveGPARequirement(String electiveGPARequirement)
    {
        this.electiveGPARequirement = electiveGPARequirement;
    }
    
    public boolean getElectiveReplaceHighestAttempt()
    {
        return electiveReplaceHighestAttempt;
    }

    public void setElectiveReplaceHighestAttempt(boolean electiveReplaceHighestAttempt)
    {
        this.electiveReplaceHighestAttempt = electiveReplaceHighestAttempt;
    }
    
    public boolean getElectiveAllowOneLowerCourse()
    {
        return electiveAllowOneLowerCourse;
    }

    public void setElectiveAllowOneLowerCourse(boolean electiveAllowOneLowerCourse)
    {
        this.electiveAllowOneLowerCourse = electiveAllowOneLowerCourse;
    }
    
    public String[] getElectivesAcceptedLowerCourses()
    {
        return electivesAcceptedLowerCourses;
    }

    public void setElectivesAcceptedLowerCourses(String[] electivesAcceptedLowerCourses)
    {
        this.electivesAcceptedLowerCourses = electivesAcceptedLowerCourses;
    }
    
    public String getOverallGPARequirement()
    {
        return overallGPARequirement;
    }

    public void setOverallGPARequirement(String overallGPARequirement)
    {
        this.overallGPARequirement = overallGPARequirement;
    }
    
    public String[] getCoreClassListRequirement()
    {
        return coreClassListRequirement;
    }

    public void setCoreClassListRequirement(String[] coreClassListRequirement)
    {
        this.coreClassListRequirement = coreClassListRequirement;
    }
    
    public String[] getElectiveClassListRequirement()
    {
        return electiveClassListRequirement;
    }

    public void setElectiveClassListRequirement(String[] electiveClassListRequirement)
    {
        this.electiveClassListRequirement = electiveClassListRequirement;
    }
    
    public boolean getActiveStatus()
    {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus)
    {
        this.activeStatus = activeStatus;
    }
    
    @Override
    public String toString()
    {
        String returnString = "";
        returnString += "\nDegree Track: " + getDegreeName()
            + "\nRequired Core Classes: " + getCoreRequirementAmount()
            + "\nRequired Core GPA: " + getCoreGPARequirement()
            + "\nHighest Core Retake Replaced: " + getCoreReplaceHighestAttempt()
            + "\nAllow 7th Elective if Core GPA >=3.0 but <3.19: " + getCoreAllowSeventhElective()
            + "\nRequired Elective Classes: " + getElectiveRequirementAmount()
            + "\nRequired Elective GPA:  " + getElectiveGPARequirement()
            + "\nHighest Elective Retake Replaced:  " + getElectiveReplaceHighestAttempt()
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


        return returnString;
    }
}
