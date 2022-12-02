package com.group12.degreeaudit.Administration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import javax.swing.JFileChooser;
//import javax.swing.filechooser.FileFilter;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.group12.degreeaudit.Report;
import com.group12.degreeaudit.Student;
import com.group12.degreeaudit.Audit.DegreeAudit;

public class FileActions 
{
    File dir = new File("./Resources");
    final FileChooser fileChooser = new FileChooser();
    CourseList courseList;
    DegreeList degreeList;
    Gson gson = new Gson();

    public FileActions(CourseList courseList, DegreeList degreeList)
    {
        this.courseList = courseList;
        this.degreeList = degreeList;
        if(!dir.exists())
        {
            dir.mkdir();
        }

        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.json"));
        // fileChooser.setFileFilter(new FileFilter() {
        //     public String getDescription(){
        //         return "JSON File (*.json)";
        //     }

        //     public boolean accept (File f)
        //     {
        //         if(f.isDirectory())
        //         {
        //             return true;
        //         }
        //         else
        //         {
        //             return f.getName().toLowerCase().endsWith(".json");
        //         }
        //     }
        // });
        fileChooser.setInitialDirectory(dir);
        //fileChooser.setCurrentDirectory(dir);

        //File selectedFile = fileChooser.showOpenDialog(null);
        //fileChooser.setSelectedFile(new File("settings.json"));
    }
    

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
            //fileChooser.showSaveDialog(null);
            //fileChooser.getSelectedFile();

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

    public void importSettings()
    {
        try
        {
            List<Object> objectImport;
            fileChooser.setInitialFileName("");
            File selectedFile = fileChooser.showOpenDialog(null);
            //fileChooser.showOpenDialog(null);
            //fileChooser.getSelectedFile();

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

    public void exportStudent(Student student) throws Exception
    {
        try
        {
            fileChooser.setInitialFileName(student.getID() + ".json");
            File selectedFile = fileChooser.showSaveDialog(null);

            //fileChooser.setSelectedFile(new File(student.getID() + ".json"));
            //fileChooser.showSaveDialog(null);
            //fileChooser.getSelectedFile();

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

    public Student importStudent()
    {
        try
        {
            fileChooser.setInitialFileName("");
            File selectedFile = fileChooser.showOpenDialog(null);

            //fileChooser.showOpenDialog(null);
            //fileChooser.getSelectedFile();

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

    public void exportDegreePlanPDF(Student student) throws Exception
    {
        try
        {
            fileChooser.setInitialFileName(student.getID() + "_Degree_Plan.pdf");
            File selectedFile = fileChooser.showSaveDialog(null);

            //fileChooser.setSelectedFile(new File(student.getID() + "_Degree_Plan.pdf"));
            //fileChooser.showSaveDialog(null);
            //fileChooser.getSelectedFile();

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

    public void exportAuditPDF(Student student, DegreeAudit audit)
    {
        try
        {
            fileChooser.setInitialFileName(student.getID() + "_Audit.pdf");
            File selectedFile = fileChooser.showSaveDialog(null);

            //fileChooser.setSelectedFile(new File(student.getID() + "_Audit.pdf"));
            //fileChooser.showSaveDialog(null);
            //fileChooser.getSelectedFile();

            File createFile = new File(selectedFile.toString());
            Report.createAuditReport(audit.doAudit(), student.getID(), createFile);
        }
        catch(Exception e)
        {
            System.out.println("Unable to save the audit " + e);
        }
    }
}
