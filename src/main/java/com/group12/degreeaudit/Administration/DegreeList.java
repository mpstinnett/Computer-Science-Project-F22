package com.group12.degreeaudit.Administration;

//File Read/Write Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//List Imports
import java.util.ArrayList;
import java.util.List;

//GSON Imports
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

//DegreeList Class - Houses the active lists of degrees read in from the DegreeList.json
//      Uses JSONDegree class to house the individual degree tracks
public class DegreeList 
{
    private List<JSONDegree> degreeList = new ArrayList<JSONDegree>();  //Degree List - Holds all the degree tracks
    private Gson gson = new Gson();     //Google JSON - Used for Importing/Exporting JSON files
    private File degreeListFile;        //Path to the degree list (normally resources/DegreeList.json)

    //DegreeList constructor - Takes in a file path to open and read the degree list
    public DegreeList(String fileName)
    {
        degreeListFile = new File(fileName);    //Sets the file path for future use
        if(!degreeListFile.exists())    //If the file does not exist, create it
        {
            try
            {
                degreeListFile.createNewFile(); //Creates the file specified
            }
            catch(IOException ex)
            {
                System.out.println("Unable to open or create file: " + fileName);
            }
        }

        degreeList = GetDegreeListFromFile();   //Reads the degree list into the object

        if(degreeList == null)  //If the degree list read is null (empty file likely) then create a empty list for now
        {
            degreeList = new ArrayList<JSONDegree>();   //Create an empty list since the read file was empty
        }

        // Commented out code for creating a degree and adding it to the list - Will delete by submission
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
    
    //AddDegreeToList - Takes all values to create a JSONDegree and adds it to the degree list both locally and in the file
    //Returns True - Degree Track successfully added
    //Returns False - Degree Track already in the list, try updating instead
    public boolean AddDegreeToList(String degreeName, String coreRequirementAmount, String coreGPARequirement, 
    boolean coreReplaceHighestAttempt, boolean coreAllowSeventhElective, String electiveRequirementAmount,
    String electiveGPARequirement, boolean electiveReplaceHighestAttempt, boolean electiveAllowOneLowerCourse,
    String[] electivesAcceptedLowerCourses, String overallGPARequirement, String[] coreClassListRequirement,
    String[] optionsCoreClassListRequirement, String[] electiveClassListRequirement, boolean activeStatus)
    {
        //First, create the JSONDegree to add with the parameter values
        JSONDegree createdDegree = new JSONDegree(degreeName, coreRequirementAmount, coreGPARequirement, 
        coreReplaceHighestAttempt, coreAllowSeventhElective, electiveRequirementAmount,
        electiveGPARequirement, electiveReplaceHighestAttempt, electiveAllowOneLowerCourse,
        electivesAcceptedLowerCourses, overallGPARequirement, coreClassListRequirement,
        optionsCoreClassListRequirement, electiveClassListRequirement, activeStatus);

        if(!CheckIfInDegreeList(createdDegree)) //Check if the newly created degree is not already in the degree list
        {
            AppendDegreeList(createdDegree);    //If not, apend to the list with the new degree
            return true;    //Return true - Successfully added
        }

        return false;   //Return false - Already in the list
    }

    //CheckIfInDegreeList - Checks if the Degree Track is already in the degree list
    //Returns True - Degree Track already exists in the degree list
    //Returns False - Degree Track does not exist in the degree list
    public boolean CheckIfInDegreeList(JSONDegree checkDegree)
    {
        if(degreeList != null)  //First check if the list is null, if it is null then the degree track is not in the list
        {
            for(int i = 0; i < degreeList.size(); i++)  //Loop all Degree Tracks in the list
            {
                //Check if the degree track adding equals the one checking by degree name
                if(degreeList.get(i).getDegreeName().equals(checkDegree.getDegreeName())) 
                {
                    return true;    //Return True - If the degree track name matches, it is in the list
                }
            }
        }
        return false;   //Return False - If the degree track name does not match any in the list
    }

    //AppendDegreeList - Appends the degree track locally and in the file
    private void AppendDegreeList(JSONDegree degree)
    {
        degreeList.add(degree);         //Appends the list locally
        WriteDegreeList(degreeList);    //Appends the file
    }

    //WriteDegreeList - Writes the degree list into the file
    //Returns True - 
    //Returns False - Failed to write
    private boolean WriteDegreeList(List<JSONDegree> degreeList)
    {
        //Try to append - Catches an IOException
        try 
        {
            FileWriter writeFile = new FileWriter(degreeListFile.getPath());    //First set up the filewriter to the saved path - Opens as new
            writeFile.append(gson.toJson(degreeList));  //Append the degree list to the file, this works since the file is opened as blank
            writeFile.close();  //Close the filewriter - No longer needed
        }
        catch(IOException e)    //Catch an IO exception and print it
        {
            System.out.println("Unable to write to JSON degree list: " + e);
            return false;       //Return False - Failed to write
        }

        return true;    //Returns True - Wrote Successfully
    }

    //RemoveDegree - Removes a specified degree track from the list and file
    //Returns True - Successfully Removed
    //Returns False - Degree is not in the list
    public boolean RemoveDegree(String degreeName)
    {
        for(int i = 0; i < degreeList.size(); i++)  //Loop all the degree tracks in the list
        {
            if(degreeList.get(i).getDegreeName().equalsIgnoreCase(degreeName))  //If the name matches
            {
                degreeList.remove(i);           //Remove the degree locally
                WriteDegreeList(degreeList);    //Write the changed degree list to the file
                return true; //Return True - Successfully Removed
            }
        }
        return false; //Return False - Degree is not in the list
    }

    //GetDegreeList - Returns the entire degree list
    public List<JSONDegree> GetDegreeList()
    {
        return degreeList;
    }

    //GetDegreeList - Returns a degree track from the list if it exists
    //Parameter - String degreeName - Name of degree to search for
    //Returns null - If the degree track is not in the list
    public JSONDegree GetDegreeFromList(String degreeName)
    {
        int degreeLocationInList = FindDegreeInList(degreeName);    //Search for the degree track in the list

        if(degreeLocationInList != -1)  //If the found int is not -1 (meaning that is is found)
        {
            return degreeList.get(degreeLocationInList);    //Returns the degree track from the list
        }

        return null;    //Returns null - If the degree track is not in the list
    }

    //GetDegreeList - Returns a degree track from the list if it exists
    //Parameter - String degreeName - Name of degree to search for
    //Returns True - If the degree track was successfully updated
    //Returns False - If the degree track is not in the list
    public boolean UpdateDegreeInList(JSONDegree degreeToUpdate)
    {
        int degreeLocationInList = FindDegreeInList(degreeToUpdate);    //Search for the degree track in the list

        if(degreeLocationInList != -1)  //If the found int is not -1 (meaning that is is found)
        {
            degreeList.set(degreeLocationInList, degreeToUpdate);   //Set (Replace) the degree locally track in the list with the parameter
            WriteDegreeList(degreeList);    //Write the changed degree to the file
            return true;    //Return True - The degree track was successfully updated
        }

        return false;   //Return False - The degree track is not in the list
    }

    //UpdateDegreeName - Updating a degree requires the name to stay the same as its the key we use to search
    //      So this method is called after an update to set the name to the correct one
    public void UpdateDegreeName(String oldDegreeName, String newDegreeName) 
    {
        int degreeLocationInList = FindDegreeInList(oldDegreeName);    //Search for the degree track in the list

        if(degreeLocationInList != -1)  //If the found int is not -1 (meaning that is is found)
        {
            degreeList.get(degreeLocationInList).setDegreeName(newDegreeName);   //Set (Replace) the degree locally track in the list with the parameter
            WriteDegreeList(degreeList);    //Write the changed degree to the file
        }
    }

    //FindDegreeInList(JSONDegree) - Finds a degree in the degree list based on the entire degree track object
    //Returns an integer location in the list for the degree track if found
    //Returns -1 if the degree does not exist in the list
    public int FindDegreeInList(JSONDegree degreeToFind)
    {
        return FindDegreeInList(degreeToFind.getDegreeName()); //Pulls the name from the JSONDegree and searches by the name
    }

    //FindDegreeInList(String) - Finds a degree in the degree list based on the name of the degree track
    //Returns an integer location in the list for the degree track if found
    //Returns -1 if the degree does not exist in the list
    public int FindDegreeInList(String degreeNameToFind)
    {
        for(int i = 0; i < degreeList.size(); i++)  //Loop all degree tracks in the list
        {
            if(degreeList.get(i).getDegreeName().equals(degreeNameToFind))  //If the degree name matches the one checking in the list
            {
                return i;   //Return the integer location in the list for the degree track
            }
        }
        return -1;  //Return -1 - The degree does not exist in the list
    }

    //GetDegreeListFromFile - Reads the degree list from the specified file path (variable)
    //Returns List of JSONDegrees from the file read
    //Returns null - Unable to read the file
    public List<JSONDegree> GetDegreeListFromFile()
    {
        try
        {
            FileReader readFile = new FileReader(degreeListFile);   //Open the file
            List<JSONDegree> listArray = gson.fromJson(readFile, new TypeToken<List<JSONDegree>>(){}.getType());    //Read the file using GSON
            readFile.close();   //Close the file
            return listArray;   //Return the list
        }  
        catch(FileNotFoundException e)  //Catch if the file isnt found
        {
            System.out.println("Unable to read from JSON course list file " + e);
        }
        catch(JsonSyntaxException e)    //Catch if the file has a json syntax error
        {
            System.out.println("Unable to read from JSON course list file " + e);
        }
        catch(IOException e)    //Catch if there is an IO Exception
        {
            System.out.println("Unable to read from JSON course list file " + e);
        }
        return null;    //Return null - Unable to read the file
    }

    //PrintDegreeList - Returns a string with all degree tracks in the list
    public String PrintDegreeList()
    {
        String degreeL = new String();  //Create an empty string
            
        degreeL += "Degree Tracks:\n\n";    //State this is the degree tracks

        for(int i = 0; i < degreeList.size(); i++)  //Loop all degree tracks in the list
        {
            //Append the string with the degree track (Calls its toString())
            degreeL += degreeList.get(i)   
                + "\n";
        }

        return degreeL; //Returns the finished string with all degree tracks information
    }
}