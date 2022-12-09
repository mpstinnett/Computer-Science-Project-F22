package com.group12.degreeaudit.Audit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Planner.Course;
import com.group12.degreeaudit.Planner.Student;

/**
 * Description: DegreeAudit - Houses degree audit operations for running audits on students
 */
public class DegreeAudit 
{
    private Student student;
    private CourseList courseList;
    private List<Course> pastTop5Core;
    private List<Course> top5Core;
    private List<Course> finalElectiveCourses;
    private List<Course> levelingCourses;
    private List<Course> sortedCourseListByGrade;
    private List<Course> duplicatesRemovedCourses;
    private List<Course> allTakenCourses;
    private double electiveGPA = 0.0;
    private double coreGPA = 0.0;
    private double combinedGPA = 0.0;
    private String outstandingRequirements = "\nOutstanding Requirements:\n";
    final DecimalFormat decfor = new DecimalFormat("0.00"); 
    
    /**
    * Description: Degree Audit Constructor
    * @param student    Student object who will have their audit performed on
    * @param courseList    list of all courses that a student can take
    */
    public DegreeAudit(Student student, CourseList courseList)
    {
        this.student = student;
        this.courseList = courseList;
    }

    /**
    * Description: performs degree audit on the student
    * @return String    formatted String containing the student's completed degree audit
    */
    public String doAudit()
    {
        coreComplete();
        electiveComplete();
        calculateOverallGPA();
        String auditString = "Audit Report\n";
        auditString += "Name: " + student.getName()
            + "\nPlan: " + student.getProgram() 
            + "\nID: " + student.getID()
            + "\nMajor: Computer Science"
            + "\nTrack: " + student.getDegreeTrack().getDegreeName()
            + "\n\n\nCore GPA: " + getCoreGPA()
            + "\nElective GPA: " + getElectiveGPA()
            + "\nCombined GPA: " + getCombinedGPA()
            + "\n\nCore Courses: ";
        for(int i = 0; i < getCoreCoursesTaken().size(); i++)
        {
            if(i !=  getCoreCoursesTaken().size()-1)
            {
                auditString += getCoreCoursesTaken().get(i).getCourseNumber() + ", ";
            }
            else
            {
                auditString += getCoreCoursesTaken().get(i).getCourseNumber();
            }
        }
        auditString += "\nElective Courses: ";
        for(int i = 0; i < getElectiveCoursesTaken().size(); i++)
        {
            if(i !=  getElectiveCoursesTaken().size()-1)
            {
                auditString += getElectiveCoursesTaken().get(i).getCourseNumber() + ", ";
            }
            else
            {
                auditString += getElectiveCoursesTaken().get(i).getCourseNumber();
            }
        }
        if(getLevelingCoursesTaken() != null)
        {
            auditString += "\n\nLeveling Courses and Pre-Requisites from Admission Letter\n";
            for(int i = 0; i < getLevelingCoursesTaken().size(); i++)
            {
                auditString += getLevelingCoursesTaken().get(i).getCourseNumber() + " - " + getLevelingCoursesTaken().get(i).getSemester() + "\n";
            }
        }
        auditString += "\n" + getOutstandingRequirements();
        

        return auditString;
    }

    /**
    * Description: getter for elective GPA of the student
    * @return double    elective GPA of the student  
    */
    public double getElectiveGPA()
    {
        return electiveGPA;
    }

    /**
    * Description: getter for core GPA of the student
    * @return double    core GPA of the student  
    */
    public double getCoreGPA()
    {
        return coreGPA;
    }

    /**
    * Description: getter for combined GPA (overall GPA of the student)
    * @return double    overall GPA of the student
    */
    public double getCombinedGPA()
    {
        return combinedGPA;
    }

    /**
    * Description: getter for outstanding requirements a student has
    * @return String    list of the requirements that will be displayed when the student is not meeting the core, elective, or overall GPA
    */
    public String getOutstandingRequirements()
    {
        return outstandingRequirements;
    }

    /**
    * Description: Retrieves all the leveling courses and pre-requisites from Admission Letter that are found in the course list
    */
    public void findLevelingCourses()
    {
        List<Course> levelingCoursesFound = new ArrayList<Course>();
        for(Course course : duplicatesRemovedCourses)
        {
            if(course.getClassType() == 'A')
            {
                levelingCoursesFound.add(course);
            }
        }

        levelingCourses = levelingCoursesFound;
    }

    /**
    * Description: getter for leveling courses a student has taken
    * @return List of Course    list of leveling and prerequisite courses a student has taken
    */
    public List<Course> getLevelingCoursesTaken()
    {
        return levelingCourses;
    }

    /**
    * Description: getter for elective courses a student has taken
    * @return List of Course    list of elective courses a student has taken
    */
    public List<Course> getElectiveCoursesTaken()
    {
        return finalElectiveCourses;
    }

    /**
    * Description: getter for the 5 highest-grade core courses a student has taken 
    * @return List of Course    list of the 5 highest-grade core courses a student has taken
    */
    public List<Course> getCoreCoursesTaken()
    {
        return top5Core;
    }

    /**
    * Description: calculates the outstanding requirements for a student's combined GPA if it is lower than the overall GPA requirement
    */
    public void calculateOverallGPA()
    {
        //List<Course> nonACourses = new ArrayList<Course>();
        // for(Course course : duplicatesRemovedCourses)
        // {
        //     if(course.getClassType() != 'C')
        //     {
        //         nonACourses.add(course);
        //     }
        // }

        combinedGPA = getGPA(duplicatesRemovedCourses);
        //combinedGPA = getGPA(nonACourses);

        if(combinedGPA < Double.parseDouble(student.getDegreeTrack().getOverallGPARequirement()))
        {
            outstandingRequirements += "\nThe Student needs a Combined GPA >= " + student.getDegreeTrack().getOverallGPARequirement();
        }
    }

    /**
    * Description: Retrieves all the electives a student has completed, specifies if the elective requirement amount is not met, and adds to list of outstanding requirements if elective GPA requirements are not met
    * @return String    String that lists out all the electives a student has taken
    */
    public String electiveComplete()
    {
        String electiveInformationString = "";

        int completedElectiveAmount = 0;
        List<Course> electiveCourses = new ArrayList<Course>();
        String[] coursesToTake = new String[Integer.parseInt(student.getDegreeTrack().getElectiveRequirementAmount())];
        int courseToTakeIterator = 0;

        //Get all elective courses from the completed courses list
        for(Course course : duplicatesRemovedCourses)
        {
            if(course.getClassType() == 'E')
            {
                if(isPastTop5Core(course))
                {
                    completedElectiveAmount++;
                    electiveCourses.add(course);
                }
            }
            else if(course.getClassType() == 'C')
            {
                if(isPastTop5Core(course))
                {
                    completedElectiveAmount++;
                    electiveCourses.add(course);
                }
            }
            else if(student.getDegreeTrack().getElectiveAllowOneLowerCourse())
            {
                for(String courseApprove5X : student.getDegreeTrack().getElectivesAcceptedLowerCourses())
                {
                    if(courseApprove5X.equals(course.getCourseNumber()))
                    {
                        electiveCourses.add(course);
                        completedElectiveAmount++;
                    }
                }
            }
        }

        finalElectiveCourses = electiveCourses;
        
        //Check if required amount is not met
        if(completedElectiveAmount < Integer.parseInt(student.getDegreeTrack().getElectiveRequirementAmount()))
        {
            electiveInformationString += "\nRequired amount of Elective courses is not met. Need "
            + (Integer.parseInt(student.getDegreeTrack().getElectiveRequirementAmount()) - completedElectiveAmount)
            + " more.";
        }

        //Check if all courses are satisfied
        for(String courseRequirement : student.getDegreeTrack().getElectiveClassListRequirement())
        {
            coursesToTake[courseToTakeIterator] = courseRequirement;
            for(Course electiveCourse : electiveCourses)
            {
                if(electiveCourse.getCourseNumber().equals(courseRequirement))
                {
                    coursesToTake[courseToTakeIterator] = null;
                    break;
                }
            }
            courseToTakeIterator++;
        }

        int aboveCourses = 0;
        int creditsNeeded = 0;
        for(int i = 0; i < courseToTakeIterator; i++)
        {
            if(coursesToTake[i] != null)
            {
                electiveInformationString += "\nNeed to complete course " + coursesToTake[i];
                outstandingRequirements += "\nThe student must pass " + coursesToTake[i];
                aboveCourses ++;
                creditsNeeded += Integer.parseInt(coursesToTake[i].substring(coursesToTake[i].length()-3, coursesToTake[i].length()-2));
            }
        }

        electiveGPA = getGPA(electiveCourses);

        //Check if GPA is satisfied
        if(electiveCourses.size() == 0
        || getGPA(electiveCourses) < Double.parseDouble(student.getDegreeTrack().getElectiveGPARequirement()))
        {
            electiveInformationString += "\nElective GPA of " + student.getDegreeTrack().getElectiveGPARequirement() + " is not met";
            if(aboveCourses > 0)
            {
                outstandingRequirements += "\nThe student needs a Elective GPA >= " + decfor.format(getNeededGPA(electiveCourses, creditsNeeded, 'E')) + " in the above " + aboveCourses + " courses.";
            }
            else
            {
                outstandingRequirements += "\nThe student needs a Elective GPA >= " + student.getDegreeTrack().getElectiveGPARequirement();
            }
        }

        return electiveInformationString;
    }

    /**
    * Description - given a core course, determine if the course can be one of the top five highest-grade core courses
    * @param course    course to compare with all the current top five courses
    * @return boolean    if the course can be a top five course, return true; otherwise, false
    */
    public boolean isPastTop5Core(Course course)
    {
        if(top5Core != null)
        {
            for(Course courseRequirement : top5Core)
            {
                if(course.getCourseNumber().equals(courseRequirement.getCourseNumber()))
                {
                    return false;
                }
            }
        }   
        return true;
    }

    /**
    * Description - remove duplicate courses from the list of all the courses the student has taken
    */
    public void removeDuplicates()
    {
        for(int i = 0; i < sortedCourseListByGrade.size(); i++)
        {
            if(sortedCourseListByGrade.get(i).getCourseNumber().split(" ")[1].charAt(0) != '5' && 
                sortedCourseListByGrade.get(i).getCourseNumber().split(" ")[1].charAt(0) != '6' )
                {
                    sortedCourseListByGrade.remove(i);
                }
        }

        duplicatesRemovedCourses = sortedCourseListByGrade;

        for(int i = 0; i < sortedCourseListByGrade.size(); i++)
        {
            for(int j = 0; j < duplicatesRemovedCourses.size(); j++)
            {
                if(i != j)
                {
                    if(sortedCourseListByGrade.get(i).getCourseNumber().equals(duplicatesRemovedCourses.get(j).getCourseNumber()))
                    {
                        duplicatesRemovedCourses.remove(j);
                    }
                }
            }
        }
    }

    /**
    * Description: Retrieves all the core courses a student has completed, specifies if the core requirement amount is not met, and adds to list of outstanding requirements if core GPA requirements are not met
    * @return String    String that lists out all the core courses a student has taken (the top 5 highest-grade core courses)
    */
    public String coreComplete()
    {
        //Check required + optional - Optional only up to the required amount
        String coreInformationString = "";

        int completedCoreAmount = 0;
        List<Course> coreCourses = new ArrayList<Course>();
        List<Course> allTakenCourses = new ArrayList<Course>();
        String[] coursesToTake = new String[Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount())];
        int courseToTakeIterator = 0;

        //Get all core courses from the completed courses list
        for(Course course : student.getCoursesTaken())
        {
            allTakenCourses.add(course);
        }

        //Sort Core Courses by Grade
        //sortByGrade(coreCourses);

        //Sort All Courses by Grade
        sortByGrade(allTakenCourses);
        sortedCourseListByGrade = allTakenCourses;

        //Make sure all duplicates are removed. This is important to make sure we dont use retaken classes in a gpa calculation, or total number completed.
        removeDuplicates(); //Removes duplicates, keeps highest grade

        //Check if all required core courses are satisfied
        for(String courseRequirement : student.getDegreeTrack().getCoreClassListRequirement())
        {
            if(courseToTakeIterator < Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount()))
            {
                if(completedCoreAmount < Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount()))
                {
                    coursesToTake[courseToTakeIterator] = courseRequirement;
                    for(Course course : student.getCoursesTaken())
                    {
                        if(course.getCourseNumber().equals(courseRequirement))
                        {
                            coursesToTake[courseToTakeIterator] = null;
                            completedCoreAmount++;
                            coreCourses.add(course);
                            break;
                        }
                    } 
                }
                courseToTakeIterator++;
            }
        }

        for(String optionalRequirement : student.getDegreeTrack().getOptionsCoreClassListRequirement())
        {
            if(courseToTakeIterator < Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount()))
            {
                if(completedCoreAmount < Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount()))
                {
                    coursesToTake[courseToTakeIterator] = optionalRequirement;
                    for(Course course : student.getCoursesTaken())
                    {
                        if(course.getCourseNumber().equals(optionalRequirement))
                        {
                            coursesToTake[courseToTakeIterator] = null;
                            completedCoreAmount++;
                            coreCourses.add(course);
                            break;
                        }
                    }
                    courseToTakeIterator++;
                }
                else
                {
                    break;
                }
            }
        }

        //Check if required amount is not met
        if(completedCoreAmount < Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount()))
        {
            coreInformationString += "\nRequired amount of Core courses is not met. Need "
            + (Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount()) - completedCoreAmount)
            + " more.";
        }

        int aboveCourses = 0;
        int creditsNeeded = 0;
        for(int i = 0; i < courseToTakeIterator; i++)
        {
            if(coursesToTake[i] != null)
            {
                coreInformationString += "\nNeed to complete course " + coursesToTake[i];
                outstandingRequirements += "\nThe student must pass " + coursesToTake[i];
                aboveCourses++;
                creditsNeeded += Integer.parseInt(coursesToTake[i].substring(coursesToTake[i].length()-3, coursesToTake[i].length()-2));
            }
        }

        //Check if Core GPA is satisfied
        //If more classes completed, trim the top 5
        // if(completedCoreAmount >= Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount()))
        // {
        //     //Remove anything past the top 5 courses
        //     for(int i = 4; i < coreCourses.size(); i++)
        //     {
        //         pastTop5Core.add(coreCourses.get(4));
        //         coreCourses.remove(4);
        //     }
        // }

        top5Core = coreCourses;
        coreGPA = getGPA(coreCourses);
        //Check the top 5 gpa
        if(coreCourses.size() == 0
            || getGPA(coreCourses) < Double.parseDouble(student.getDegreeTrack().getCoreGPARequirement()))
        {
            if(!student.getDegreeTrack().getCoreAllowSeventhElective() || ((getGPA(coreCourses) > 3.0) && !extraElectiveCheck())
                || (getGPA(coreCourses) < 3.0))
            {
                coreInformationString += "\nCore GPA of " + student.getDegreeTrack().getCoreGPARequirement() + " is not met";
                if(aboveCourses > 0)
                {
                    outstandingRequirements += "\nThe student needs a core GPA >= " + decfor.format(getNeededGPA(coreCourses, creditsNeeded, 'C')) + " in the above " + aboveCourses + " courses.";
                }
                else
                {
                    outstandingRequirements += "\nThe student needs a core GPA >= " + student.getDegreeTrack().getCoreGPARequirement();
                }
            }
        }

        return coreInformationString;
    }

    /**
    * Description - calculates the needed GPA of a student when they have outstanding requirements
    * @param courses    The list of all the courses that are being checked (can be core courses or electives)
    * @param creditsLeft    The amount of remaining credits that a student needs to take for the course category (ex: amount of core courses not taken yet)
    * @param gpaType    Core course 'C' or elective course 'E'
    * @return double    The GPA that a student needs to have in order to fulfill the GPA requirement for a particular course category
    */
    private double getNeededGPA(List<Course> courses, int creditsLeft, char gpaType)
    {
        int currentCredits = 0;
        double currentGPA;
        currentGPA = getGPA(courses);
        for(Course course : courses)
        {
            currentCredits += course.getCredits();
        }

        try
        {
            switch(gpaType)
            {
                case 'C':
                    return ((((currentCredits+creditsLeft) * Double.parseDouble(student.getDegreeTrack().getCoreGPARequirement())) - (currentCredits*currentGPA)) / (creditsLeft));
                case 'E':
                return ((((currentCredits+creditsLeft) * Double.parseDouble(student.getDegreeTrack().getElectiveGPARequirement())) - (currentCredits*currentGPA)) / (creditsLeft));
                default:
                    return 0;
            }
        }
        catch(Exception e)
        {
            return 0;
        }
    }
    
    /**
    * Description: sortByGrade - sorts the student's list of courses by grade
    * @param sortList    List of all the courses a student has taken
    */
    private void sortByGrade(List<Course> sortList)
    {
        Collections.sort(sortList);
    }

    /**
    * Description: getGPA - calls the calculateGPA method on a given list of courses
    * @param courses    List of courses to check the gpa from
    * @return Double for the GPA calculated
    */
    public double getGPA(List<Course> courses)
    {
        return calculateGPA(courses);
    }

    /**
      * Description: getGPA - Gets the gpa based on class type and courses
      * @param classType    Character for the class type (A, C, E) - Used to trim
      * @param courses  List of courses to check the gpa from
      * @return Double for the GPA calculated
    */
    public double getGPA(char classType, List<Course> courses) {
        List<Course> GPAlist = new ArrayList<Course>();
        
        for(Course course: courses) {
            if (course.getClassType() == classType) {
                GPAlist.add(course);
            }
        }

        return calculateGPA(GPAlist);
    }


    /**
    * Description: Takes as input a list of course objects and calculates the gpa of that list. 
    * @param List of course objects - courses
    * @return double GPA
    */
    private double calculateGPA(List<Course> courses) {
        double gpa = 0.0;
        int totalCreditHoursAttempted = 0;
        double totalGradePointsEarned = 0;
        for (Course course : courses) {
            if (course.getGrade().equals("") || course.getGrade().equals("P")) {
                continue;
            }

            int courseCreditHours = Character.getNumericValue(course.getCourseNumber().split(" ")[1].charAt(1));
            totalCreditHoursAttempted += courseCreditHours;
            double grade = 0;
            switch (course.getGrade()) {
                case "A+" : grade = 4.00;
                            break;
                case "A"  : grade = 4.00;
                            break;
                case "A-" : grade = 3.67;
                            break;
                case "B+" : grade = 3.33;
                            break;
                case "B"  : grade = 3.00;
                            break;
                case "B-" : grade = 2.67;
                            break;
                case "C+" : grade = 2.33;
                            break;
                case "C"  : grade = 2.00;
                            break;
                case "C-" : grade = 1.67;
                            break;
                case "D+" : grade = 1.33;
                            break;
                case "D"  : grade = 1.00;
                            break;
                case "D-" : grade = 0.67;
                            break;
                case "F"  : grade = 0.00;
                            break;
                default : grade = 0.00;
                            break;
            } 
            totalGradePointsEarned += (grade * courseCreditHours);
        }
        gpa = totalGradePointsEarned / totalCreditHoursAttempted;
        DecimalFormat df_obj = new DecimalFormat("#.###");
        if(df_obj.format(gpa).toString().equals("NaN"))
        {
            return 0.0;
        }
        return Double.parseDouble(df_obj.format(gpa));
    }

    /**
    * Description: extraElectiveCheck - If 7th elective is allowed, it will check if the elective GPA is satisfied
    * @return boolean    true if a 7th elective is allowed and that elective can bypass the elective GPA
    */
    public boolean extraElectiveCheck()
    {
        int completedElectiveAmount = 0;
        List<Course> electiveCourses = new ArrayList<Course>();

        //Get all elective courses from the completed courses list
        for(Course course : duplicatesRemovedCourses)
        {
            if(course.getClassType() == 'E')
            {
                completedElectiveAmount++;
                electiveCourses.add(course);
            }
            else if(course.getClassType() == 'C')
            {
                if(isPastTop5Core(course))
                {
                    completedElectiveAmount++;
                    electiveCourses.add(course);
                }
            }
        }
        finalElectiveCourses = electiveCourses;
        
        //Check if required amount is not met
        if(completedElectiveAmount < (Integer.parseInt(student.getDegreeTrack().getElectiveRequirementAmount()) + 1))
        {
            return false;
        }

        electiveGPA = getGPA(electiveCourses);

        //Check if GPA is satisfied
        if(electiveCourses.size() == 0
        || getGPA(electiveCourses) < Double.parseDouble(student.getDegreeTrack().getElectiveGPARequirement()))
        {
            return false;
        }

        return true;
    }
}