package com.group12.degreeaudit.Audit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.group12.degreeaudit.Course;
import com.group12.degreeaudit.Student;
import com.group12.degreeaudit.Administration.CourseList;

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
    
    public DegreeAudit(Student student, CourseList courseList)
    {
        this.student = student;
        this.courseList = courseList;
    }

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
        for(int i = 0; i < courseToTakeIterator; i++)
        {
            if(coursesToTake[i] != null)
            {
                electiveInformationString += "\nNeed to complete course " + coursesToTake[i];
            }
        }

        //Check if GPA is satisfied
        if(electiveCourses.size() == 0
        || getGPA(electiveCourses) < Double.parseDouble(student.getDegreeTrack().getElectiveGPARequirement()))
        {
            electiveInformationString += "\nElective GPA of " + student.getDegreeTrack().getElectiveGPARequirement() + " is not met";
        }

        return electiveInformationString;
    }

    public boolean isPastTop5Core(Course course)
    {
        if(pastTop5Core != null)
        {
            for(Course courseRequirement : pastTop5Core)
            {
                if(course.getCourseNumber().equals(courseRequirement.getCourseNumber()))
                {
                    return true;
                }
            }
        }   
        return false;
    }

    public void removeDuplicates()
    {
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

    public String coreComplete()
    {
        String coreInformationString = "";

        int completedCoreAmount = 0;
        List<Course> coreCourses = new ArrayList<Course>();
        List<Course> allTakenCourses = new ArrayList<Course>();
        String[] coursesToTake = new String[Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount())];
        int courseToTakeIterator = 0;

        //Get all core courses from the completed courses list
        for(Course course : student.getCoursesTaken())
        {
            if(course.getClassType() == 'C')
            {
                completedCoreAmount++;
                coreCourses.add(course);
            }
            allTakenCourses.add(course);
        }

        //Sort Core Courses by Grade
        sortByGrade(coreCourses);

        //Sort All Courses by Grade
        sortByGrade(allTakenCourses);
        sortedCourseListByGrade = allTakenCourses;

        //Make sure all duplicates are removed. This is important to make sure we dont use retaken classes in a gpa calculation, or total number completed.
        removeDuplicates(); //Removes duplicates, keeps highest grade

        //Check if required amount is not met
        if(completedCoreAmount < Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount()))
        {
            coreInformationString += "\nRequired amount of Core courses is not met. Need "
            + (Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount()) - completedCoreAmount)
            + " more.";
        }

        //Check if all courses are satisfied
        for(String courseRequirement : student.getDegreeTrack().getCoreClassListRequirement())
        {
            coursesToTake[courseToTakeIterator] = courseRequirement;
            for(Course coreCourse : coreCourses)
            {
                if(coreCourse.getCourseNumber().equals(courseRequirement))
                {
                    coursesToTake[courseToTakeIterator] = null;
                    break;
                }
            }
            courseToTakeIterator++;
        }
        for(int i = 0; i < courseToTakeIterator; i++)
        {
            if(coursesToTake[i] != null)
            {
                coreInformationString += "\nNeed to complete course " + coursesToTake[i];
            }
        }

        //Check if GPA is satisfied
        //If more classes completed, trim the top 5
        if(completedCoreAmount >= Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount()))
        {
            //Remove anything past the top 5 courses
            for(int i = 4; i < coreCourses.size(); i++)
            {
                pastTop5Core.add(coreCourses.get(4));
                coreCourses.remove(4);
            }

            top5Core = coreCourses;
        }
        //Check the top 5 gpa
        if(coreCourses.size() == 0
            || getGPA(coreCourses) < Double.parseDouble(student.getDegreeTrack().getCoreGPARequirement()))
        {
            coreInformationString += "\nCore GPA of " + student.getDegreeTrack().getCoreGPARequirement() + " is not met";
        }

        return coreInformationString;
    }

    private void sortByGrade(List<Course> sortList)
    {
        Collections.sort(sortList);
    }

    public double getGPA(List<Course> courses)
    {
        return calculateGPA(courses);
    }

    public double getGPA(char classType, List<Course> courses) {
        List<Course> GPAlist = new ArrayList<Course>();
        
        for(Course course: courses) {
            if (course.getClassType() == classType) {
                GPAlist.add(course);
            }
        }

        return calculateGPA(GPAlist);
    }

    private double calculateGPA(List<Course> courses) {
        double gpa = 0.0;
        int totalCreditHoursAttempted = 0;
        double totalGradePointsEarned = 0;
        for (Course course : courses) {
            if (course.getGrade() == "") {
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
        return Double.parseDouble(df_obj.format(gpa));
    }
}
