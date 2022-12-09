package com.group12.degreeaudit.FileActions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.DegreeList;
import com.group12.degreeaudit.Administration.JSONCourse;
import com.group12.degreeaudit.Administration.JSONDegree;
import com.group12.degreeaudit.Audit.DegreeAudit;
import com.group12.degreeaudit.Planner.Student;

/**
 * Description: FileActions - Houses importing and exporting methods for PDFs, students, and settings
 */
public class FileActions 
{
    File dir = new File("./Resources");
    final FileChooser fileChooser = new FileChooser();
    CourseList courseList;
    DegreeList degreeList;
    Gson gson = new Gson();

    /** Description: FileActions constructor - Creates the file settings before use
     * @param   courseList  Course list to use with file actions
     * @param   degreeList  Degree list to use with file actions
    */
    public FileActions(CourseList courseList, DegreeList degreeList)
    {
        this.courseList = courseList;
        this.degreeList = degreeList;
        if(!dir.exists())
        {
            dir.mkdir();
        }

        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.json"));
        fileChooser.setInitialDirectory(dir);
    }
    
    /** Description: exportSettings - Exports the courselist and degree list to a settings file
    */
    public void exportSettings()
    {
        try
        {
            List<JSONCourse> courseListExport = courseList.GetCourseListFromFile();
            List<JSONDegree> degreeListExport = degreeList.GetDegreeListFromFile();
            List<Object> objectExport = new ArrayList<Object>();

            objectExport.add(courseListExport);
            objectExport.add(degreeListExport);

            fileChooser.setInitialFileName("settings.json");
            File selectedFile = fileChooser.showSaveDialog(null);

            File createFile = new File(selectedFile.toString());
            createFile.createNewFile();

            FileWriter writeFile = new FileWriter(createFile.getPath());
            writeFile.write(gson.toJson(objectExport));
            writeFile.close();
        }
        catch(Exception e)
        {
            System.out.println("Unable to export the settings " + e);
        }
    }

    /** Description: importSettings - Imports the course list and degree list from a settings file
    */
    public void importSettings()
    {
        try
        {
            List<Object> objectImport;
            fileChooser.setInitialFileName("");
            File selectedFile = fileChooser.showOpenDialog(null);

            FileReader readFile = new FileReader(selectedFile.toString());
        
            objectImport = gson.fromJson(readFile, new TypeToken<List<Object>>(){}.getType());
            readFile.close();
            System.out.print(objectImport.toString());

            //Clean up the files
            File courseListFile = new File("./resources/CourseList.json");
            File degreeListFile = new File("./resources/DegreeList.json");
            if(courseListFile.exists())
            {
                courseListFile.delete();
            }
            if(degreeListFile.exists())
            {
                degreeListFile.delete();
            }
            courseListFile.createNewFile();
            degreeListFile.createNewFile();

            FileWriter writeFile = new FileWriter(courseListFile.getPath());
            writeFile.write(gson.toJson(objectImport.toArray()[0]));
            writeFile.close();

            writeFile = new FileWriter(degreeListFile.getPath());
            writeFile.write(gson.toJson(objectImport.toArray()[1]));
            writeFile.close();

        }
        catch(Exception e)
        {
            System.out.println("Unable to import the settings " + e);
        }
    }

    /** Description: exportStudent - Exports the student object into a json file
      * @param student Student object to export
      * @exception Exception throws if there is a problem exporting
    */
    public void exportStudent(Student student) throws Exception
    {
        try
        {
            fileChooser.setInitialFileName(student.getID() + ".json");
            File selectedFile = fileChooser.showSaveDialog(null);

            File createFile = new File(selectedFile.toString());
            createFile.createNewFile();

            FileWriter writeFile = new FileWriter(createFile.getPath());
            writeFile.write(gson.toJson(student));
            writeFile.close();
        }
        catch(Exception e)
        {
            System.out.println("Unable to export the student " + e);
            throw e;
        }
    }

    /** Description: importStudent - Imports the student from a student file
      * @return Student object that was imported from the file
    */
    public Student importStudent()
    {
        try
        {
            fileChooser.setInitialFileName("");
            File selectedFile = fileChooser.showOpenDialog(null);

            FileReader readFile = new FileReader(selectedFile.toString());
            Student jsonStudent = gson.fromJson(readFile, new TypeToken<Student>(){}.getType());
            readFile.close();

            return jsonStudent;
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Unable to import the student " + e);
        }
        catch(JsonSyntaxException e)
        {
            System.out.println("Unable to import the student " + e);
        }
        catch(IOException e)
        {
            System.out.println("Unable to import the student " + e);
        }
        return null;
    }

    /** Description: exportDegreePlanPDF - Exports the degree plan in PDF form based on a student
      * @param student Student object to export into a PDF for a degree plan
      * @exception Exception Thrown when unable to save a degree plan
    */
    public void exportDegreePlanPDF(Student student) throws Exception
    {
        try
        {
            fileChooser.setInitialFileName(student.getID() + "_Degree_Plan.pdf");
            File selectedFile = fileChooser.showSaveDialog(null);

            File createFile = new File(selectedFile.toString());
            if(createFile.exists())
            {
                createFile.delete();
            }
            Report.createDegreePlan(student, createFile);
        }
        catch(Exception e)
        {
            System.out.println("Unable to save the degree plan " + e);
            throw e;
        }
    }

    /** Description: exportAuditPDF - Exports the audit in PDF form based on a student
      * @param student Student object to export into a PDF for an audit
      * @param audit DegreeAudit from the audit report
    */
    public void exportAuditPDF(Student student, DegreeAudit audit)
    {
        try
        {
            fileChooser.setInitialFileName(student.getID() + "_Audit.pdf");
            File selectedFile = fileChooser.showSaveDialog(null);

            File createFile = new File(selectedFile.toString());
            Report.createAuditReport(audit.doAudit(), student.getID(), createFile);
        }
        catch(Exception e)
        {
            System.out.println("Unable to save the audit " + e);
        }
    }
}
