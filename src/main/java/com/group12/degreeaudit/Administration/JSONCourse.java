package com.group12.degreeaudit.Administration;

// import javafx.event.ActionEvent;
// import javafx.event.EventHandler;
// import javafx.scene.control.Button;
// import javafx.scene.control.TableView;

public class JSONCourse 
{
    private String courseNumber;
    private String courseName;
    private String courseDescription;
    private String[] coursePreReqs;
    private char classType;
    private boolean activeStatus;
    //private Button button;

    public JSONCourse(String courseNumber, String courseName, String courseDescription, String[] prereqs, char classType, boolean activeStatus)
    {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.coursePreReqs = prereqs;
        this.classType = classType;
        this.activeStatus = activeStatus;
    }

    public JSONCourse(JSONCourse copyCourse)
    {
        this.courseNumber = copyCourse.getCourseNumber();
        this.courseName = copyCourse.getCourseName();
        this.courseDescription = copyCourse.getCourseDescription();
        this.coursePreReqs = copyCourse.getCoursePreReqs();
        this.classType = copyCourse.getClassType();
        this.activeStatus = copyCourse.getActiveStatus();
    }

    // // CONSTRUCTOR FOR PREREQUISITES TABLE IN ADD COURSE TAB 
    // public JSONCourse(String prereqCourseNum)
    // {
    //     this.courseNumber = prereqCourseNum;
    //     this.button = new Button("X");
    //     button.setStyle("-fx-text-fill: #C00000; -fx-background-color: transparent; -fx-font-weight: bold;");
        
    // }

    // public void ButtonCell(final TableView tblView, final JSONCourse course){
            
    //     button.setOnAction(new EventHandler<ActionEvent>(){

    //         @Override
    //         public void handle(ActionEvent t) {
    //             //tblView.getItems().remove(tblView.getSelectionModel().getSelectedItem());
    //             tblView.getItems().remove(course);              
    //         }
    //     });
    // }

    // public void setButton(Button button){
    //     this.button = button;
    // }

    // public Button getButton(){
    //     return button;
    // }


    public String getCourseNumber()
    {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber)
    {
        this.courseNumber = courseNumber;
    }

    public String getCourseName()
    {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public String getCourseDescription()
    {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription)
    {
        this.courseDescription = courseDescription;
    }

    public String[] getCoursePreReqs()
    {
        return coursePreReqs;
    }

    public void setCoursePreReqs(String[] coursePreReqs)
    {
        this.coursePreReqs = coursePreReqs;
    }

    public char getClassType()
    {
        return classType;
    }

    public void setClassType(char classType)
    {
        this.classType = classType;
    }

    public boolean getActiveStatus()
    {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus)
    {
        this.activeStatus = activeStatus;
    }

    @Override
    public String toString()
    {
        String returnString = "";
        returnString += "\nCourse: " + getCourseNumber()
            + "\nName: " + getCourseName()
            + "\nDescription: " + getCourseDescription()
            + "\nType: " + getClassType()
            + "\nActive: " + getActiveStatus()
            + "\nPrerequisites: ";
        if(getCoursePreReqs() != null)
        {
            for(int j = 0; j < getCoursePreReqs().length; j++)
            {
                returnString += "\n  " + getCoursePreReqs()[j];
            }
        }
        else
        {
            returnString += "\n  None";
        }
        return returnString;
    }
}
