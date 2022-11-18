package com.group12.degreeaudit.Administration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class DegreeList 
{
    List<JSONDegree> degreeList = new ArrayList<JSONDegree>();
    Gson gson = new Gson();
    File degreeListFile;

    public DegreeList(String fileName)
    {
        degreeListFile = new File(fileName);
        if(!degreeListFile.exists())
        {
            try
            {
                degreeListFile.createNewFile();
            }
            catch(IOException ex)
            {
                System.out.println("Unable to open or create file: " + fileName);
            }
        }


        degreeList = GetDegreeListFromFile();
        if(degreeList == null)
        {
            degreeList = new ArrayList<JSONDegree>();
        }

        // String[] temp = new String[]{"CS 555755", "CS 5556"};
        
        // AddDegreeToList("Systems", "5", "3.2", 
        // true, true, "6",
        // "3.0", true, true,
        // temp, "3.2", temp,
        // temp, true);
        // AddDegreeToList("Data Science", "5", "3.2", 
        // true, true, "6",
        // "3.0", true, true,
        // temp, "3.2", temp,
        // temp, true);

        // AddDegreeToList("Systems", "5", "3.2", 
        // true, true, "6",
        // "3.0", true, true,
        // temp, "3.2", temp,
        // temp, true);
        // AddDegreeToList("Interactive Computing", "5", "3.2", 
        // true, true, "6",
        // "3.0", true, true,
        // temp, "3.2", temp,
        // temp, true);
        // System.out.println(PrintDegreeList());

        // if(RemoveDegree("Interactive Computing"))
        //     System.out.println("Removed");
        // System.out.println(PrintDegreeList());
    }
    
    public boolean AddDegreeToList(String degreeName, String coreRequirementAmount, String coreGPARequirement, 
    boolean coreReplaceHighestAttempt, boolean coreAllowSeventhElective, String electiveRequirementAmount,
    String electiveGPARequirement, boolean electiveReplaceHighestAttempt, boolean electiveAllowOneLowerCourse,
    String[] electivesAcceptedLowerCourses, String overallGPARequirement, String[] coreClassListRequirement,
    String[] electiveClassListRequirement, boolean activeStatus)
    {
        JSONDegree createdDegree = new JSONDegree(degreeName, coreRequirementAmount, coreGPARequirement, 
        coreReplaceHighestAttempt, coreAllowSeventhElective, electiveRequirementAmount,
        electiveGPARequirement, electiveReplaceHighestAttempt, electiveAllowOneLowerCourse,
        electivesAcceptedLowerCourses, overallGPARequirement, coreClassListRequirement,
        electiveClassListRequirement, activeStatus);

        if(!CheckIfInDegreeList(createdDegree))
        {
            AppendDegreeList(createdDegree);
            return true;
        }

        return false;
    }

    public boolean CheckIfInDegreeList(JSONDegree checkDegree)
    {
        if(degreeList != null)
        {
            for(int i = 0; i < degreeList.size(); i++)
            {
                if(degreeList.get(i).getDegreeName().equals(checkDegree.getDegreeName()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private void AppendDegreeList(JSONDegree degree)
    {
        degreeList.add(degree);
        WriteDegreeList(degreeList);
    }

    private boolean WriteDegreeList(List<JSONDegree> degreeList)
    {
        try
        {
            FileWriter writeFile = new FileWriter(degreeListFile.getPath());
            writeFile.append(gson.toJson(degreeList));
            writeFile.close();
        }
        catch(IOException e)
        {
            System.out.println("Unable to write to JSON degree list");
            return false;
        }
        return true;
    }

    public boolean RemoveDegree(String degreeName)
    {
        for(int i = 0; i < degreeList.size(); i++)
        {
            if(degreeList.get(i).getDegreeName().equalsIgnoreCase(degreeName))
            {
                degreeList.remove(i);
                WriteDegreeList(degreeList);
                return true;
            }
        }
        return false;
    }

    public List<JSONDegree> GetDegreeList()
    {
        return degreeList;
    }

    public JSONDegree GetDegreeFromList(String degreeName)
    {
        int degreeLocationInList = FindDegreeInList(degreeName);
        if(degreeLocationInList != -1)
        {
            return degreeList.get(degreeLocationInList);
        }
        return null;
    }

    public boolean UpdateDegreeInList(JSONDegree degreeToUpdate)
    {
        int degreeLocationInList = FindDegreeInList(degreeToUpdate);
        if(degreeLocationInList != -1)
        {
            degreeList.set(degreeLocationInList, degreeToUpdate);
            WriteDegreeList(degreeList);
            return true;
        }
        return false;
    }

    public void UpdateDegreeName(String oldDegreeName,String newDegreeName) {
        int degreeLocationInList = FindDegreeInList(oldDegreeName);
        degreeList.get(degreeLocationInList).setDegreeName(newDegreeName);
        WriteDegreeList(degreeList);
    }


    public int FindDegreeInList(JSONDegree degreeToFind)
    {
        for(int i = 0; i < degreeList.size(); i++)
        {
            if(degreeList.get(i).getDegreeName().equals(degreeToFind.getDegreeName()))
            {
                return i;
            }
        }
        return -1;
    }

    public int FindDegreeInList(String degreeNameToFind)
    {
        for(int i = 0; i < degreeList.size(); i++)
        {
            if(degreeList.get(i).getDegreeName().equals(degreeNameToFind))
            {
                return i;
            }
        }
        return -1;
    }

    public List<JSONDegree> GetDegreeListFromFile()
    {
        try
        {
            FileReader readFile = new FileReader(degreeListFile);
            List<JSONDegree> listArray = gson.fromJson(readFile, new TypeToken<List<JSONDegree>>(){}.getType());
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

    public String PrintDegreeList()
    {
        String degreeL = new String();
            
        degreeL += "Degree Tracks:\n\n";
        for(int i = 0; i < degreeList.size(); i++)
        {
            degreeL += degreeList.get(i) 
                + "\n";
        }
        return degreeL;
    }
}
