package com.group12.degreeaudit.Administration;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.Year;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;

import java.io.FileWriter;

public class CourseList
{
    ArrayList<JSONCourse> courseList = new ArrayList<JSONCourse>();
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
        AddCourseToList("CS 5556", "Test Class", "Best Class EVER", null);
        AddCourseToList("CS 5557", "Test Class", "Best Class NEVER", null);

        String[] temp = new String[]{"CS 555755", "CS 5556"};
        AddCourseToList("CS 5558", "Test L Class", "Best Class FOREVER", temp);
        ReadJsonCourseList();
        System.out.println(PrintCourseList(courseList));

        if(RemoveCourse("CS 5556"))
            System.out.println("Removed");
        ReadJsonCourseList();
        System.out.println(PrintCourseList(courseList));
    }

    public boolean AddCourseToList(String courseNumber, String courseName, String courseDescription, String[] prereqs)
    {
        JSONCourse createdCourse = new JSONCourse(courseNumber, courseName, courseDescription, prereqs);
        AppendCourseList(createdCourse);
        return false;
    }

    public void AppendCourseList(JSONCourse course)
    {
        courseList.add(course);
        WriteCourseList(courseList);
    }

    public boolean WriteCourseList(ArrayList<JSONCourse> courseList)
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
            if(courseList.get(i).courseNumber.equalsIgnoreCase(courseNumber))
            {
                courseList.remove(i);
                return true;
            }
        }
        return false;
    }

    public void ReadJsonCourseList()
    {
        try
        {
            Scanner myReader = new Scanner(courseListFile);
            Type listType = new TypeToken<ArrayList<JSONCourse>>(){}.getType();
            ArrayList<JSONCourse> temp = gson.fromJson(myReader.nextLine(), listType);
            System.out.println(temp.size());
            myReader.close();
        }  
        catch(FileNotFoundException e)
        {
            System.out.println("Unable to read from JSON course list file");
        }
        catch(JsonSyntaxException e)
        {
            System.out.println("Unable to read from JSON course list file");
        }
    }

    public String PrintCourseList(ArrayList<JSONCourse> c)
    {
        String courseL = new String();
            
        courseL += "Course List:\n\n";
        for(int i = 0; i < c.size(); i++)
        {
            courseL += "" + i + ":"
                + "\nCourse: " + c.get(i).courseNumber
                + "\nName: " + c.get(i).courseName
                + "\nDescription: " + c.get(i).courseDescription
                + "\nPrerequisites: ";
            if(c.get(i).coursePreReqs != null)
            {
                for(int j = 0; j < c.get(i).coursePreReqs.length; j++)
                {
                    courseL += "\n  " + c.get(i).coursePreReqs[j];
                }
            }
            else
            {
                courseL += "\n  None";
            }
            courseL += "\n\n";
        }
        return courseL;
    }
}