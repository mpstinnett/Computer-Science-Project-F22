package com.group12.degreeaudit.Administration;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;

/*
 * Description: CourseList - holds all methods relating to obtaining, creating, adding, removing, updating, etc of 
 * courses in a list. These courses are different from JSON course as they are designed for courses that have grades
 * and semesters.
*/
public class CourseList
{
    List<JSONCourse> courseList = new ArrayList<JSONCourse>();
    Gson gson = new Gson();
    File courseListFile;

    /* Description: CourseList - Constructor that takes a filename for the location of the courselist json file.
     * Sets all needed values and makes sure the file exists or creates a new one.
     * @param fileName String for the name of the file
    */
    public CourseList(String fileName)
    {
        courseListFile = new File(fileName);
        if(!courseListFile.exists())
        {
            try
            {
                courseListFile.createNewFile();
            }
            catch(IOException ex)
            {
                System.out.println("Unable to open or create file: " + fileName);
            }
        }

        courseList = GetCourseListFromFile();
        if(courseList == null)
        {
            courseList = new ArrayList<JSONCourse>();
        }
    }

    public boolean AddCourseToList(String courseNumber, String courseName, String courseDescription, String[] prereqs, char classType, boolean activeStatus)
    {
        JSONCourse createdCourse = new JSONCourse(courseNumber, courseName, courseDescription, prereqs, classType, activeStatus);
        if(!CheckIfInCourseList(createdCourse))
        {
            AppendCourseList(createdCourse);
            return true;
        }
        return false;
    }

    public boolean CheckIfInCourseList(JSONCourse checkCourse)
    {
        if(courseList != null)
        {
            for(int i = 0; i < courseList.size(); i++)
            {
                if(courseList.get(i).getCourseNumber().equals(checkCourse.getCourseNumber()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private void AppendCourseList(JSONCourse course)
    {
        courseList.add(course);
        WriteCourseList(courseList);
    }

    private boolean WriteCourseList(List<JSONCourse> courseList)
    {
        try
        {
            FileWriter writeFile = new FileWriter(courseListFile.getPath());
            writeFile.append(gson.toJson(courseList));
            writeFile.close();
        }
        catch(IOException e)
        {
            System.out.println("Unable to write to JSON course list");
            return false;
        }
        return true;
    }

    public boolean RemoveCourse(String courseNumber)
    {
        for(int i = 0; i < courseList.size(); i++)
        {
            if(courseList.get(i).getCourseNumber().equalsIgnoreCase(courseNumber))
            {
                courseList.remove(i);
                WriteCourseList(courseList);
                return true;
            }
        }
        return false;
    }

    public List<JSONCourse> GetCourseList()
    {
        return courseList;
    }

    public JSONCourse GetCourseFromList(String courseNumber)
    {
        int courseLocationInList = FindCourseInList(courseNumber);
        if(courseLocationInList != -1)
        {
            return courseList.get(courseLocationInList);
        }
        return null;
    }

    public boolean UpdateCourseInList(JSONCourse courseToUpdate)
    {
        int courseLocationInList = FindCourseInList(courseToUpdate);
        if(courseLocationInList != -1)
        {
            courseList.set(courseLocationInList, courseToUpdate);
            WriteCourseList(courseList);
            return true;
        }
        return false;
    }
    
    public void UpdateCourseNumber(String oldCourseNumber, String newCourseNumber) {
        int courseLocationInList = FindCourseInList(oldCourseNumber);
        courseList.get(courseLocationInList).setCourseNumber(newCourseNumber);
        WriteCourseList(courseList);
    }

    public int FindCourseInList(JSONCourse courseToFind)
    {
        for(int i = 0; i < courseList.size(); i++)
        {
            if(courseList.get(i).getCourseNumber().equals(courseToFind.getCourseNumber()))
            {
                return i;
            }
        }
        return -1;
    }

    public int FindCourseInList(String courseNumberToFind)
    {
        for(int i = 0; i < courseList.size(); i++)
        {
            if(courseList.get(i).getCourseNumber().equals(courseNumberToFind))
            {
                return i;
            }
        }
        return -1;
    }

    public List<JSONCourse> GetCourseListFromFile()
    {
        try
        {
            FileReader readFile = new FileReader(courseListFile);
            List<JSONCourse> listArray = gson.fromJson(readFile, new TypeToken<List<JSONCourse>>(){}.getType());
            readFile.close();
            return listArray;
        }  
        catch(FileNotFoundException e)
        {
            System.out.println("Unable to read from JSON course list file " + e);
        }
        catch(JsonSyntaxException e)
        {
            System.out.println("Unable to read from JSON course list file " + e);
        }
        catch(IOException e)
        {
            System.out.println("Unable to read from JSON course list file " + e);
        }
        return null;
    }

    public String PrintCourseList()
    {
        String courseL = new String();
            
        courseL += "Course List:\n\n";
        for(int i = 0; i < courseList.size(); i++)
        {
            courseL += courseList.get(i) 
                + "\n";
        }
        return courseL;
    }
}