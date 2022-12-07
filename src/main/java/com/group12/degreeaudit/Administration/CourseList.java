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

/**
 * Description: CourseList - holds all methods relating to obtaining, creating, adding, removing, updating, etc of 
 * courses in a list. These courses are different from JSON course as they are designed for courses that have grades
 * and semesters.
 */
public class CourseList
{
    List<JSONCourse> courseList = new ArrayList<JSONCourse>();
    Gson gson = new Gson();
    File courseListFile;

    /** Description: CourseList - Constructor that takes a filename for the location of the courselist json file.
     * Sets all needed values and makes sure the file exists or creates a new one.
     * @param   fileName    String for the name of the file
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

    /** Description: AddCourseToList - Adds a course to the courselist, takes a course by the course values and creates the
     * course locally
     * @param   courseNumber    String for the course number
     * @param   courseName  String for the course name
     * @param   courseDescription   String for the course description
     * @param   prereqs String array for the course prerequisites
     * @param   classType   Character for the class type (A, C, E)
     * @param   activeStatus    Boolean for the active status (if the course can be taken)
     * @return True - If the course successfully added to the list. False - If the course is already in the list
     */
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

    /** Description: CheckIfInCourseList - Checks if a JSONCourse is in the courselist
     * Sets all needed values and makes sure the file exists or creates a new one.
     * @param   checkCourse    JSONCourse to check if in the course list
     * @return True - Course is in the courselist. False - Course is not in the courselist.
    */
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

    /** Description: AppendCourseList - Appends a course to the end of the courselist and writes it to the
     * courselist file
     * @param   course    JSONCourse for the course to append to the courselist
    */
    private void AppendCourseList(JSONCourse course)
    {
        courseList.add(course);
        WriteCourseList(courseList);
    }

    /** Description: WriteCourseList - Writes the courselist to the courselist file
     * @param   courseList    List of JSONCourses to write to the file
     * @return  True - Courselist wrote successfully. False - IOException occured
    */
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

    /** Description: RemoveCourse - Removes a course from the list (locally and in file)
     * @param   courseNumber    String containing the course number
     * @return  True - Course Removed. False - Course not found in the list
    */
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

    /** Description: GetCourseList - Gets the entire course list
     * @return   List of JSONCourses holding all courses in the course list
    */
    public List<JSONCourse> GetCourseList()
    {
        return courseList;
    }

    /** Description: GetCourseFromList - Returns a course from the courselist (JSONCourse)
     * @param   courseNumber    String of the coursenumber to find
     * @return  JSONCourse of the course from the course list
    */
    public JSONCourse GetCourseFromList(String courseNumber)
    {
        int courseLocationInList = FindCourseInList(courseNumber);
        if(courseLocationInList != -1)
        {
            return courseList.get(courseLocationInList);
        }
        return null;
    }

    /** Description: UpdateCourseInList - Updates a course in the list using a JSONCourse (Later only uses the number from this param)
     * @param   courseToUpdate    JSONCourse to use - Uses name from this later
     * @return  True - Update successfully. False - Course not found in the list
    */
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
    
    /** Description: UpdateCourseNumber - Updates a course in the list with a new course number (Used after UpdateCourseInList)
     * @param   oldCourseNumber    String for the old course number
     * @param   newCourseNumber    String for the new course number
    */
    public void UpdateCourseNumber(String oldCourseNumber, String newCourseNumber) {
        int courseLocationInList = FindCourseInList(oldCourseNumber);
        courseList.get(courseLocationInList).setCourseNumber(newCourseNumber);
        WriteCourseList(courseList);
    }

    /** Description: FindCourseInList - Finds a course location in a list based on the JSONCourse (Uses course number of JSONCourse)
     * @param   courseToFind    JSONCourse to find
     * @return   Integer location of the course in the list. -1 if not found.
    */
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

    /** Description: FindCourseInList - Finds a course location in a list based on the course number
     * @param   courseNumberToFind    String of the course number to look for in the list
     * @return   Integer location of the course in the list. -1 if not found.
    */
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

    /** Description: GetCourseListFromFile - Returns the course list from the file, not from local variable
     * @return   List of JSONCourses of all the courses in the list
    */
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

    /** Description: PrintCourseList - Printable string all the courses in the list
     * @return   String containing all courses in the list
    */
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