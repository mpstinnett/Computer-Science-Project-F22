package com.group12.degreeaudit.Administration;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.FileWriter;

public class CourseList
{
    List<JSONCourse> courseList = new ArrayList<JSONCourse>();
    Gson gson = new Gson();
    File courseListFile;

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

        AddCourseToList("CS 5556", "Test Class", "Best Class EVER", null, 'C', true);
        AddCourseToList("CS 5557", "Test Class", "Best Class NEVER", null, 'E', false);

        String[] temp = new String[]{"CS 555755", "CS 5556"};
        AddCourseToList("CS 5558", "Test L Class", "Best Class FOREVER", temp, 'A', true);
        System.out.println(PrintCourseList(courseList));

        if(RemoveCourse("CS 5556"))
            System.out.println("Removed");
        System.out.println(PrintCourseList(courseList));
    }

    public void AddCourseToList(String courseNumber, String courseName, String courseDescription, String[] prereqs, char classType, boolean activeStatus)
    {
        JSONCourse createdCourse = new JSONCourse(courseNumber, courseName, courseDescription, prereqs, classType, activeStatus);
        if(!CheckIfInCourseList(createdCourse))
        {
            AppendCourseList(createdCourse);
        }
        else
        {
            System.out.println("Cant Append as course is already in the course list");
        }
        
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

    public String PrintCourseList(List<JSONCourse> c)
    {
        String courseL = new String();
            
        courseL += "Course List:\n\n";
        for(int i = 0; i < c.size(); i++)
        {
            courseL += courseList.get(i) 
                + "\n";
        }
        return courseL;
    }
}