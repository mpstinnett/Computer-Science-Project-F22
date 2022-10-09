package com.group12.degreeaudit.Administration;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
                //ReadJsonCourseList();
            }
            catch(IOException ex)
            {
                System.out.println("Unable to open or create file: " + fileName);
            }
        }
        else
        {
            System.out.println("Adding1");
            AddCourse("CS 5555", "Test Class", "Best Class EVER", null);
            AddCourse("CS 5555", "Test Class", "Best Class NEVER", null);
            AddCourse("CS 5555", "Test Class", "Best Class FOREVER", null);
            ReadJsonCourseList();
        }
    }

    public boolean AddCourse(String courseNumber, String courseName, String courseDescription, ArrayList<JSONCourse> prereqs)
    {
        System.out.println("Adding2");
        JSONCourse createdCourse = new JSONCourse(courseNumber, courseName, courseDescription, prereqs);
        AppendCourseList(createdCourse);
        return false;
    }

    public void AppendCourseList(JSONCourse course)
    {
        System.out.println("Adding3");
        courseList.add(course);
        WriteToFile(courseList);
    }

    public boolean WriteToFile(ArrayList<JSONCourse> courseList)
    {
        System.out.println("Adding4");
        try
        {
            System.out.println("Adding5");
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

    public boolean RemoveCourse()
    {
        return false;
    }

    public void ReadJsonCourseList()
    {
        try
        {
            System.out.println("READING:");
            Scanner myReader = new Scanner(courseListFile);
            try
            {
                Type listType = new TypeToken<ArrayList<JSONCourse>>(){}.getType();
                ArrayList<JSONCourse> temp = gson.fromJson(myReader.nextLine(), listType);
                System.out.println(temp.size());
            }
            catch(JsonSyntaxException e)
            {

            }
        }  
        catch(FileNotFoundException e)
        {

        }
    }
}