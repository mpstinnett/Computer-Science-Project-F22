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

/**
 * Description: DegreeList - Houses the active lists of degrees read in from the DegreeList.json
 * Uses JSONDegree class to house the individual degree tracks
 */
public class DegreeList 
{
    private List<JSONDegree> degreeList = new ArrayList<JSONDegree>();  //Degree List - Holds all the degree tracks
    private Gson gson = new Gson();     //Google JSON - Used for Importing/Exporting JSON files
    private File degreeListFile;        //Path to the degree list (normally resources/DegreeList.json)

    /** Description: DegreeList constructor - Takes in a file path to open and read the degree list
     * Sets all needed values and makes sure the file exists or creates a new one.
     * @param   fileName    String for the name of the file
    */
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
    }
    
    /** Description: AddDegreeToList - Takes all values to create a JSONDegree and adds it to the degree list both locally and in the file
     * course locally
     * @param   degreeName    String for the degree name
     * @param   coreRequirementAmount  String for core requirement amount
     * @param   coreGPARequirement   String core gpa requirement
     * @param   coreAllowSeventhElective Boolean for the allowing of a 7th elective
     * @param   electiveRequirementAmount   String for core requirement amount
     * @param   electiveGPARequirement    String for the elective gpa requirement
     * @param   electiveAllowOneLowerCourse Boolean for the allowing of lower level courses for the electives
     * @param   electivesAcceptedLowerCourses   String array for accepted lower level courses
     * @param   overallGPARequirement   String for the overall gpa requirement
     * @param   coreClassListRequirement    String array for the required core classes
     * @param   optionsCoreClassListRequirement ArrayList of Strings for the optional core classes
     * @param   electiveClassListRequirement    String array for the required elective classes
     * @param   activeStatus    Boolean for the active status of the degree track
     * @return True - Degree Track successfully added. False - Degree Track already in the list, try updating instead.
     */
    public boolean AddDegreeToList(String degreeName, String coreRequirementAmount, String coreGPARequirement, boolean coreAllowSeventhElective, String electiveRequirementAmount,
    String electiveGPARequirement, boolean electiveAllowOneLowerCourse,
    String[] electivesAcceptedLowerCourses, String overallGPARequirement, String[] coreClassListRequirement,
    ArrayList<String> optionsCoreClassListRequirement, String[] electiveClassListRequirement, boolean activeStatus)
    {
        //First, create the JSONDegree to add with the parameter values
        JSONDegree createdDegree = new JSONDegree(degreeName, coreRequirementAmount, coreGPARequirement, 
         coreAllowSeventhElective, electiveRequirementAmount,
        electiveGPARequirement, electiveAllowOneLowerCourse,
        electivesAcceptedLowerCourses, overallGPARequirement, coreClassListRequirement,
        optionsCoreClassListRequirement, electiveClassListRequirement, activeStatus);

        if(!CheckIfInDegreeList(createdDegree)) //Check if the newly created degree is not already in the degree list
        {
            AppendDegreeList(createdDegree);    //If not, apend to the list with the new degree
            return true;    //Return true - Successfully added
        }

        return false;   //Return false - Already in the list
    }

    /** Description: CheckIfInDegreeList - Checks if the Degree Track is already in the degree list
     * @param   checkDegree    JSONDegree to check if it is in the degree list
     * @return  True - Degree Track already exists in the degree list. False - Degree Track does not exist in the degree list.
    */
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

    /** Description: AppendDegreeList - Appends the degree track locally and in the file
     * @param   degree    JSONDegree to append to the list
     */
    private void AppendDegreeList(JSONDegree degree)
    {
        degreeList.add(degree);         //Appends the list locally
        WriteDegreeList(degreeList);    //Appends the file
    }

    /** Description: WriteDegreeList - Writes the degree list into the file
     * @param   degreeList    List of JSONDegrees to write to the degree list (DegreeList to overwrite the file with)
     * @return  Returns True - Wrote Successfully. Returns False - Failed to write (IOException)
     */
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

    /** Description: RemoveDegree - Removes a specified degree track from the list and file
     * @param   degreeName    String of the degree name to remove
     * @return  Returns True - Successfully Removed. Returns False - Degree is not in the list.
     */
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

    /** Description: GetDegreeList - Returns the entire degree list
     * @return  List of JSONDegrees (The Degree List from the variable)
     */
    public List<JSONDegree> GetDegreeList()
    {
        return degreeList;
    }

    /** Description: GetDegreeFromList - Gets a degree from the degree list based on the name of a degree
     * @param   degreeName    String for the degree name to get from the list
     * @return  JSONDegree from the degree list. Null - If the degree track is not in the list.
     */
    public JSONDegree GetDegreeFromList(String degreeName)
    {
        int degreeLocationInList = FindDegreeInList(degreeName);    //Search for the degree track in the list

        if(degreeLocationInList != -1)  //If the found int is not -1 (meaning that is is found)
        {
            return degreeList.get(degreeLocationInList);    //Returns the degree track from the list
        }

        return null;    //Returns null - If the degree track is not in the list
    }

    /** Description: UpdateDegreeInList - Updates a degree in the list based on a JSONDegree (Uses the name from the degree object)
     * @param   degreeToUpdate    JSONDegree for the degree to update (will use name from this object)
     * @return  True - If the degree track was successfully updated. False - If the degree track is not in the list.
     */
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
   
    /** Description: UpdateDegreeName - Updating a degree requires the name to stay the same as its the key we use to search
     * So this method is called after an update to set the name to the correct one
     * @param   oldDegreeName    String for the old degree name to find and update
     * @param   newDegreeName    String for the new degree name to update to
     */
    public void UpdateDegreeName(String oldDegreeName, String newDegreeName) 
    {
        int degreeLocationInList = FindDegreeInList(oldDegreeName);    //Search for the degree track in the list

        if(degreeLocationInList != -1)  //If the found int is not -1 (meaning that is is found)
        {
            degreeList.get(degreeLocationInList).setDegreeName(newDegreeName);   //Set (Replace) the degree locally track in the list with the parameter
            WriteDegreeList(degreeList);    //Write the changed degree to the file
        }
    }

    /** Description: FindDegreeInList(JSONDegree) - Finds a degree in the degree list based on a JSONDegree (Uses name of this object)
     * @param   degreeToFind    JSONDegree degree to find in the list
     * @return Integer of the location of the degree in the list. -1 if degree is not found in the list.
     */
    public int FindDegreeInList(JSONDegree degreeToFind)
    {
        return FindDegreeInList(degreeToFind.getDegreeName()); //Pulls the name from the JSONDegree and searches by the name
    }

    /** Description: FindDegreeInList(String) - Finds a degree in the degree list based on the name of the degree track
     * @param   degreeNameToFind    String of degree name to find in the list
     * @return Integer of the location of the degree in the list. -1 if degree is not found in the list.
     */
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

    /** Description: GetDegreeListFromFile - Gets the degree list from the specified file path (variable based not parameter)
     * @return List of JSONDegrees from the file read. null - Unable to read the file (FileNotFoundException, JsonSyntaxException, or IOException).
     */
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

    /** Description: PrintDegreeList - Returns a string with all degree tracks in the list
     * @return String with all degree tracks
     */
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