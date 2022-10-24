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
    
    public DegreeAudit(Student student, CourseList courseList)
    {
        this.student = student;
        this.courseList = courseList;
    }

    public String coreComplete()
    {
        int completedCoreAmount = 0;
        List<Course> coreCourses = new ArrayList<Course>();
        for(Course course : student.getCoursesTaken())
        {
            if(course.getClassType() == 'C')
            {
                completedCoreAmount++;
                coreCourses.add(course);
            }
        }
        if(Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount()) <= completedCoreAmount)
        {
            sortByGrade(coreCourses);
            for(int i = 4; i < coreCourses.size(); i++)
            {
                coreCourses.remove(4);
            }
            if(getGPA('C', coreCourses) >= Double.parseDouble(student.getDegreeTrack().getCoreGPARequirement()))
            {
                return "Complete";
            }
            else
            {
                return "Core GPA of " + student.getDegreeTrack().getCoreGPARequirement() + " is not met";
            }
        }
        else
        {
            String returnString = "";
            String[] coursesToTake = new String[Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount())];
            int i = 0;
            for(String courseRequirement : student.getDegreeTrack().getCoreClassListRequirement())
            {
                coursesToTake[i] = courseRequirement;
                for(Course coreCourse : coreCourses)
                {
                    if(coreCourse.getCourseNumber().equals(courseRequirement))
                    {
                        coursesToTake[i] = null;
                        break;
                    }
                }
                i++;
            }
            
            for(int j = 0; j < Integer.parseInt(student.getDegreeTrack().getCoreRequirementAmount()); j++)
            {
                if(coursesToTake[j] != null)
                {
                    returnString += "\nNeed to complete course " + coursesToTake[j];
                }
            }

            return returnString;
        }
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
