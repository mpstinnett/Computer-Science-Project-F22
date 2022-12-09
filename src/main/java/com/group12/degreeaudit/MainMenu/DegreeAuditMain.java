package com.group12.degreeaudit.MainMenu;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.DegreeList;
import com.group12.degreeaudit.FileActions.FileActions;
import com.group12.degreeaudit.Planner.degreePlanningSceneController;

/**
 * Description: DegreeAuditMain - Main Class, runs when launched
 */
public class DegreeAuditMain
{
    /**
    * Description: main - Runs when the application is first run. loads and initializes application window starting with the main menu
    * @param args    command line arguments (null)
    */
    public static void main(String args[])
    {
        CourseList courseList = new CourseList("resources/CourseList.json");
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        FileActions fa = new FileActions(courseList, degreeList);
        menuGUI.main(args);
    }
    
}

