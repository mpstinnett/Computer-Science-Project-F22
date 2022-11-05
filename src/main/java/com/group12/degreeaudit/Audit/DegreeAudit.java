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
    private String coreInformationString;
    
    public DegreeAudit(Student student, CourseList courseList)
    {
        this.student = student;
        this.courseList = courseList;
    }

    public String coreComplete()
    {
        coreInformationString = "";

        int completedCoreAmount = 0;
        List<Course> coreCourses = new ArrayList<Course>();
        String[] coursesToTake = new String[Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount())];
        int courseToTakeIterator = 0;

        //Get all courses from the completed courses list
        for(Course course : student.getCoursesTaken())
        {
            if(course.getClassType() == 'C')
            {
                completedCoreAmount++;
                coreCourses.add(course);
            }
        }

        //Sort Courses by Grade
        sortByGrade(coreCourses);

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
                coreCourses.remove(4);
            }
        }
        //Check the top 5 gpa
        if(getGPA('C', coreCourses) < Double.parseDouble(student.getDegreeTrack().getCoreGPARequirement()))
        {
            coreInformationString += "\nCore GPA of " + student.getDegreeTrack().getCoreGPARequirement() + " is not met";
        }

        return coreInformationString;
    }

    private void sortByGrade(List<Course> sortList)
    {
        Collections.sort(sortList);
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
