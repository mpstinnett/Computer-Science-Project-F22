package com.group12.degreeaudit;

// Imports
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.DegreeList;
import com.group12.degreeaudit.Administration.JSONCourse;
import com.group12.degreeaudit.Administration.JSONCourseWrapper;
import com.group12.degreeaudit.Administration.JSONDegree;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class adminSceneController implements Initializable{

    // Return to main menu button
    @FXML
    private Button return_to_menu_btn;
    
    // Handles return to main menu button
    @FXML
    public void returnToMenu(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) return_to_menu_btn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/menuScene.fxml"));
        System.out.println("Lightning");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Export Settings button
    @FXML
    private Button export_settings_btn;
    
    // Handles export button
    @FXML 
    public void exportSettings(ActionEvent event){
        System.out.println("Export settings");
    }

    // Tabs
    @FXML
    private TabPane tab_pane;


    /* 
     * ADD COURSE TAB
    */
    @FXML
    private TextField addc_class_number, addc_class_name;
    
    @FXML
    private TextArea addc_class_description;
    
    @FXML
    private CheckBox addc_active_status;
    
    @FXML
    private ComboBox<String> addc_type_dropdown, addc_prerequisites_dropdown;
    
    @FXML
    public TableView<JSONCourseWrapper> addc_prerequisites_table;
    
    @FXML
    public TableColumn<JSONCourseWrapper, String> addc_course_num_col;
    
    @FXML
    public TableColumn<JSONCourseWrapper, String> addc_course_remove_col;

    // "ADD" button is pressed for prerequisite table
    @FXML
    void addcPrerequisites(ActionEvent event) {

        // Grab the selected class from prerequisite dropdown
        String addc_prereq_course_num = addc_prerequisites_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the prerequisite table
        JSONCourseWrapper addc_prereq_course = new JSONCourseWrapper(addc_prereq_course_num);

        // remove the selected class from the dropdown 
        addc_prerequisites_dropdown.getItems().remove(addc_prereq_course_num);  

        // create an observable list for the prerequisite table
        ObservableList<JSONCourseWrapper> addc_JSONCourseWrapper = addc_prerequisites_table.getItems();
        
        // populate the prerequisite table & remove the selected item from dropdown
        addc_JSONCourseWrapper.add(addc_prereq_course);
        addc_prerequisites_table.setItems(addc_JSONCourseWrapper);
        addc_prereq_course.removeTableCourse(addc_prerequisites_table, addc_prereq_course, addc_prerequisites_dropdown, addc_prereq_course_num);
    
    }

    // "TEST" button is pressed
    @FXML
    public void testMeBestie(ActionEvent event){
        initialize(null, null);
    }


    // "ADD" button 
    @FXML
    private Button addc_add_btn;

    // "ADD" button is pressed for adding a course
    @FXML
    public void addCourseToCourseList(ActionEvent event)
    {
        // Get filled in fields
        String courseNumber = addc_class_number.getText().toString();
        String courseName = addc_class_name.getText().toString();
        String courseDescription = addc_class_description.getText().toString();
        char classType = addc_type_dropdown.getValue().charAt(0);
        boolean activeStatus = addc_active_status.isSelected();

        // Getting prereqs from table
        String prerequisites[] = new String[addc_prerequisites_table.getItems().size()];
        for (int i = 0; i < prerequisites.length; i++) {
            prerequisites[i] = addc_prerequisites_table.getItems().get(i).getJsonCourse().getCourseNumber();
        }

        System.out.println("Course Number is: " + courseNumber);
        
        // Adding course to course list
        CourseList courseList = new CourseList("resources/CourseList.json");
        courseList.AddCourseToList(courseNumber, courseName, courseDescription, prerequisites, classType, activeStatus);

        // Refresh the tab
        initialize(null, null);
    }
 

    /* 
     * UPDATE COURSE TAB
    */
    @FXML
    private ComboBox<String> updatec_class_dropdown, updatec_type_dropdown, updatec_prerequisites_dropdown;

    @FXML
    private TextField updatec_class_number, updatec_class_name;
    
    @FXML
    private TextArea updatec_class_description;
    
    @FXML
    private CheckBox updatec_active_status;

    @FXML
    public TableView<JSONCourseWrapper> updatec_prerequisites_table;
    
    @FXML
    public TableColumn<JSONCourseWrapper, String> updatec_course_num_col;
    
    @FXML
    public TableColumn<JSONCourseWrapper, String> updatec_course_remove_col;


    // Prefill the tab when a class is chosen
    @FXML
    void updatecGetClassInfo(ActionEvent event){

        String courseNumber = updatec_class_dropdown.getValue();

        // Get all the courses possible
        CourseList courseList = new CourseList("resources/CourseList.json");
        JSONCourse course = courseList.GetCourseFromList(courseNumber);
        List<JSONCourse> allCourses = courseList.GetCourseList();

        // Populate prerequisite dropdown with all courses possible
        ObservableList<String> updatecPreqDropList = FXCollections.observableArrayList();
        

        for (JSONCourse preReqCourse : allCourses) {
            updatecPreqDropList.add(preReqCourse.getCourseNumber());
        }
        
        Collections.sort(updatecPreqDropList);

        updatec_prerequisites_dropdown.setItems(updatecPreqDropList);

        // Remove chosen class from prerequisite dropdown
        updatec_prerequisites_dropdown.getItems().remove(courseNumber);  

        // Populate details of the chosen class
        updatec_class_number.setText(course.getCourseNumber());
        updatec_class_name.setText(course.getCourseName());
        updatec_class_description.setText(course.getCourseDescription());
        updatec_active_status.setSelected(course.getActiveStatus());

        switch(course.getClassType()){
            case 'A':
                updatec_type_dropdown.getSelectionModel().select("Admission");
                break;
            case 'E':
                updatec_type_dropdown.getSelectionModel().select("Elective");
                break;
            case 'C':
                updatec_type_dropdown.getSelectionModel().select("Core");
                break;

        }
        
        // Clear the prerequisites table whenever a new class is chosen from the class dropdown
        updatec_prerequisites_table.getItems().clear();
        
        // Populate the table with the course prerequisites
        String prerequisites[] = course.getCoursePreReqs();

        ObservableList<JSONCourseWrapper> updatec_JSONCourseWrapper = updatec_prerequisites_table.getItems();

        // Make a JSONCourseWrapper for every prerequisite in the course
        for(int i = 0; i < prerequisites.length; i++){
            JSONCourseWrapper updatec_prereq_course = new JSONCourseWrapper(prerequisites[i]);
            updatec_JSONCourseWrapper.add(updatec_prereq_course);
            updatec_prerequisites_table.setItems(updatec_JSONCourseWrapper);

            // Remove the selected class from the dropdown 
            updatec_prerequisites_dropdown.getItems().remove(prerequisites[i]);
            
            updatec_prereq_course.removeTableCourse(updatec_prerequisites_table, updatec_prereq_course, updatec_prerequisites_dropdown, prerequisites[i]);
            
        }


    }

    // "ADD" button is pressed for prerequisite table
    @FXML
    void updatecPrerequisites(ActionEvent event) {
        // Grab the selected class from prerequisite dropdown
        String updatec_prereq_course_num = updatec_prerequisites_dropdown.getValue();

        // Create an instance of JSONCourseWrapper to add to the prerequisite table
        JSONCourseWrapper updatec_prereq_course = new JSONCourseWrapper(updatec_prereq_course_num);

        // Remove the selected class from the dropdown 
        updatec_prerequisites_dropdown.getItems().remove(updatec_prereq_course_num);  

        // Create an observable list for the prerequisite table
        ObservableList<JSONCourseWrapper> updatec_JSONCourseWrapper = updatec_prerequisites_table.getItems();
        
        // Populate the prerequisite table
        updatec_JSONCourseWrapper.add(updatec_prereq_course);
        updatec_prerequisites_table.setItems(updatec_JSONCourseWrapper);
        updatec_prereq_course.removeTableCourse(updatec_prerequisites_table, updatec_prereq_course, updatec_prerequisites_dropdown, updatec_prereq_course_num);

    }

    @FXML
    private Button updatec_update_btn;

    // "UPDATE" button is pressed for updating a course
    @FXML
    public void updateCourseInCourseList(ActionEvent event)
    {
        // Get filled in fields
        CourseList courseList = new CourseList("resources/CourseList.json");
        JSONCourse courseToUpdate = new JSONCourse();
        courseToUpdate.setCourseName(updatec_class_name.getText().toString());
        courseToUpdate.setCourseNumber(updatec_class_number.getText().toString());
        courseToUpdate.setCourseDescription(updatec_class_description.getText().toString());
        courseToUpdate.setActiveStatus(updatec_active_status.isSelected());

        // Getting prereqs from table
        String prerequisites[] = new String[updatec_prerequisites_table.getItems().size()];
        for (int i = 0; i < prerequisites.length; i++) {
            prerequisites[i] = updatec_prerequisites_table.getItems().get(i).getJsonCourse().getCourseNumber();
        }

        // Updating the course
        courseToUpdate.setCoursePreReqs(prerequisites);
        courseToUpdate.setClassType(updatec_type_dropdown.getValue().charAt(0));
        if (courseList.UpdateCourseInList(courseToUpdate)) {
            System.out.println("Course updated.");
        } else {
            System.out.println("Error: Course not Updated.");
        }
        initialize(null, null);
    }
 

    /* 
     * REMOVE COURSE TAB
    */ 
    @FXML
    private ComboBox<String> removec_dropdown;

    @FXML
    private Button removec_remove_btn;

    // "REMOVE" button is pressed for removing a course
    @FXML
    public void removeCourseInCourseList(ActionEvent event)
    {
        CourseList courseList = new CourseList("resources/CourseList.json");
        courseList.RemoveCourse(removec_dropdown.getValue());
        initialize(null, null);
    }


    /* 
     * ADD DEGREE TRACK TAB
    */
    @FXML
    public TextField addt_track_name, addt_num_core_courses, addt_core_gpa_requirements, 
                     addt_num_electives, addt_overall_gpa, addt_elective_gpa_requirements;

    @FXML
    private CheckBox addt_active_status, addt_core_replace_2nd, addt_core_allow_7th, addt_elective_replace_2nd, addt_elective_allow_5k;

    @FXML
    private ComboBox<String> addt_5k_dropdown, addt_core_dropdown, addt_elective_dropdown;

    // Add to 5XXX Courses Table
    @FXML
    public TableView<JSONCourseWrapper> addt_5k_table;

    @FXML
    public TableColumn<JSONCourseWrapper, String> addt_5k_course_num_col;

    @FXML
    public TableColumn<JSONCourseWrapper, String> addt_5k_remove_course_col;

    // "ADD" button is pressed for adding a 5XXX course
    @FXML
    void addtAdd5kCourse(ActionEvent event) {

        // Grab the selected class from prerequisite dropdown
        String addt_5k_course_num = addt_5k_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the prerequisite table
        JSONCourseWrapper addt_5k_course = new JSONCourseWrapper(addt_5k_course_num);

        // create an observable list for the prerequisite table
        ObservableList<JSONCourseWrapper> addt_5k_JSONCourseWrapper = addt_5k_table.getItems();
        
        // populate the prerequisite table
        addt_5k_dropdown.getItems().remove(addt_5k_course_num);  

        addt_5k_JSONCourseWrapper.add(addt_5k_course);
        addt_5k_table.setItems(addt_5k_JSONCourseWrapper);
        addt_5k_course.removeTableCourse(addt_5k_table, addt_5k_course, addt_5k_dropdown, addt_5k_course_num);
    }

    
    // Add to Core Courses Table
    @FXML
    public TableView<JSONCourseWrapper> addt_core_table;

    @FXML
    public TableColumn<JSONCourseWrapper, String> addt_core_course_num_col;

    @FXML
    public TableColumn<JSONCourseWrapper, String> addt_core_remove_course_col;

    // "ADD" button is pressed for adding a core course
    @FXML
    void addtAddCoreCourse(ActionEvent event) {
         // Grab the selected class from prerequisite dropdown
        String addt_core_course_num = addt_core_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the prerequisite table
        JSONCourseWrapper addt_core_course = new JSONCourseWrapper(addt_core_course_num);

        // create an observable list for the prerequisite table
        ObservableList<JSONCourseWrapper> addt_core_JSONCourseWrapper = addt_core_table.getItems();
        
        // populate the prerequisite table
        addt_core_dropdown.getItems().remove(addt_core_course_num);  

        addt_core_JSONCourseWrapper.add(addt_core_course);
        addt_core_table.setItems(addt_core_JSONCourseWrapper);
        addt_core_course.removeTableCourse(addt_core_table, addt_core_course, addt_core_dropdown, addt_core_course_num);
    }

    // Add to Elective Courses Table
    @FXML
    public TableView<JSONCourseWrapper> addt_elective_table;

    @FXML
    public TableColumn<JSONCourseWrapper, String> addt_elective_course_num_col;

    @FXML
    public TableColumn<JSONCourseWrapper, String> addt_elective_remove_course_col;
  
    // "ADD" button is pressed for adding a elective course
    @FXML
    void addtAddElectiveCourse(ActionEvent event) {
        // Grab the selected class from prerequisite dropdown
        String addt_elective_course_num = addt_elective_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the prerequisite table
        JSONCourseWrapper addt_elective_course = new JSONCourseWrapper(addt_elective_course_num);

        // create an observable list for the prerequisite table
        ObservableList<JSONCourseWrapper> addt_elective_JSONCourseWrapper = addt_elective_table.getItems();
        
        // populate the prerequisite table
        addt_elective_dropdown.getItems().remove(addt_elective_course_num);  

        addt_elective_JSONCourseWrapper.add(addt_elective_course);
        addt_elective_table.setItems(addt_elective_JSONCourseWrapper);
        addt_elective_course.removeTableCourse(addt_elective_table, addt_elective_course, addt_elective_dropdown, addt_elective_course_num);
        
    }

    // "SUBMIT" button is pressed for adding a degree track
    @FXML
    void addtSubmit(ActionEvent event) {

        // Grab filled in fields
        String degreeName = addt_track_name.getText().toString();
        String coreRequirementAmount = addt_num_core_courses.getText().toString();
        String coreGPARequirement = addt_core_gpa_requirements.getText().toString();
        boolean coreReplaceHighestAttempt = addt_core_replace_2nd.isSelected();
        boolean coreAllowSeventhElective = addt_core_allow_7th.isSelected();
        String electiveRequirementAmount = addt_num_electives.getText().toString();
        String electiveGPARequirement = addt_elective_gpa_requirements.getText().toString();
        boolean electiveReplaceHighestAttempt = addt_elective_replace_2nd.isSelected();
        boolean electiveAllowOneLowerCourse = addt_elective_allow_5k.isSelected();
        String overallGPARequirement = addt_overall_gpa.getText().toString();
        boolean activeStatus = addt_active_status.isSelected();

        // Grab all table values
        String electivesAcceptedLowerCourses[] = new String[addt_5k_table.getItems().size()];
        for (int i = 0; i < electivesAcceptedLowerCourses.length; i++) {
            electivesAcceptedLowerCourses[i] = addt_5k_table.getItems().get(i).getJsonCourse().getCourseNumber();
        }

        String coreClassListRequirement[] = new String[addt_core_table.getItems().size()];
        for (int i = 0; i < coreClassListRequirement.length; i++) {
            coreClassListRequirement[i] = addt_core_table.getItems().get(i).getJsonCourse().getCourseNumber();
        }

        String electiveClassListRequirement[] = new String[addt_elective_table.getItems().size()];
        for (int i = 0; i < electiveClassListRequirement.length; i++) {
            electiveClassListRequirement[i] = addt_elective_table.getItems().get(i).getJsonCourse().getCourseNumber();
        }

        // Add to degree list
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        degreeList.AddDegreeToList(degreeName, coreRequirementAmount, coreGPARequirement, coreReplaceHighestAttempt,
            coreAllowSeventhElective, electiveRequirementAmount, electiveGPARequirement, 
            electiveReplaceHighestAttempt, electiveAllowOneLowerCourse, electivesAcceptedLowerCourses, overallGPARequirement, 
            coreClassListRequirement, electiveClassListRequirement, activeStatus);
    }


    /* 
     * UPDATE DEGREE TRACK TAB
    */
    @FXML
    private ComboBox<String> updatet_dropdown; 

    @FXML
    public TextField updatet_track_name, updatet_num_core_courses, updatet_core_gpa_requirements, 
                     updatet_num_electives, updatet_overall_gpa, updatet_elective_gpa_requirements;

    @FXML
    private CheckBox updatet_active_status, updatet_core_replace_2nd, updatet_core_allow_7th, updatet_elective_replace_2nd, updatet_elective_allow_5k;

    // Add to 5XXX Courses Table
    @FXML
    public TableView<JSONCourseWrapper> updatet_5k_table;

    @FXML
    private ComboBox<String> updatet_5k_dropdown, updatet_core_dropdown, updatet_elective_dropdown;

    // Prefill the tab when a class is chosen
    @FXML
    void updatetGetClassInfo(ActionEvent event) {
        String degreeTrackName = updatet_dropdown.getValue();

        // Get all the courses possible
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        JSONDegree degreeTrack = degreeList.GetDegreeFromList(degreeTrackName);

        CourseList courseList = new CourseList("resources/CourseList.json");
        List<JSONCourse> allCourses = courseList.GetCourseList();
        // Populate table dropdowns with all courses possible
        ObservableList<String> updatet5kDropList = FXCollections.observableArrayList();
        ObservableList<String> updatetCoreDropList = FXCollections.observableArrayList();
        ObservableList<String> updatetElectiveDropList = FXCollections.observableArrayList();
        


        for (JSONCourse course : allCourses) {
            updatet5kDropList.add(course.getCourseNumber());
            updatetCoreDropList.add(course.getCourseNumber());
            updatetElectiveDropList.add(course.getCourseNumber());
        }

        Collections.sort(updatet5kDropList);
        Collections.sort(updatetCoreDropList);
        Collections.sort(updatetElectiveDropList);

        updatet_5k_dropdown.setItems(updatet5kDropList);
        updatet_core_dropdown.setItems(updatetCoreDropList);
        updatet_elective_dropdown.setItems(updatetElectiveDropList);

        // Populate details of the chosen class
        updatet_track_name.setText(degreeTrack.getDegreeName());
        updatet_active_status.setSelected(degreeTrack.getActiveStatus());
        updatet_num_core_courses.setText(degreeTrack.getCoreRequirementAmount());
        updatet_core_gpa_requirements.setText(degreeTrack.getCoreGPARequirement());
        updatet_core_replace_2nd.setSelected(degreeTrack.getElectiveReplaceHighestAttempt());
        updatet_core_allow_7th.setSelected(degreeTrack.getCoreAllowSeventhElective());
        updatet_num_electives.setText(degreeTrack.getElectiveRequirementAmount());
        updatet_overall_gpa.setText(degreeTrack.getOverallGPARequirement());
        updatet_elective_gpa_requirements.setText(degreeTrack.getElectiveGPARequirement());
        updatet_elective_replace_2nd.setSelected(degreeTrack.getElectiveReplaceHighestAttempt());
        updatet_elective_allow_5k.setSelected(degreeTrack.getElectiveAllowOneLowerCourse());


        // Clear the tables whenever a new track is chosen from the dropdown
        updatet_5k_table.getItems().clear();
        updatet_core_table.getItems().clear();
        updatet_elective_table.getItems().clear();

        // POPULATE 5XXX COURSE TABLE
        String lowerCourses[] = degreeTrack.getElectivesAcceptedLowerCourses();

        // Populate the table with the 5k courses
        ObservableList<JSONCourseWrapper> lowerCourseJSONCourseWrapper = updatet_5k_table.getItems();

        // Make a JSONCourseWrapper for every 5k course
        for(int i = 0; i < lowerCourses.length; i++){
            JSONCourseWrapper lowerCourse = new JSONCourseWrapper(lowerCourses[i]);
            lowerCourseJSONCourseWrapper.add(lowerCourse);
            updatet_5k_table.setItems(lowerCourseJSONCourseWrapper);

            // Remove the selected class from the dropdown 
            updatet_5k_dropdown.getItems().remove(lowerCourses[i]);
            
            lowerCourse.removeTableCourse(updatet_5k_table, lowerCourse, updatet_5k_dropdown, lowerCourses[i]);
            
        }

        // POPULATE CORE COURSE TABLE
        String coreCourses[] = degreeTrack.getCoreClassListRequirement();

        // Populate the table with the 5k courses
        ObservableList<JSONCourseWrapper> coreCourseJSONCourseWrapper = updatet_core_table.getItems();

        // Make a JSONCourseWrapper for every 5k course
        for(int i = 0; i < coreCourses.length; i++){
            JSONCourseWrapper coreCourse = new JSONCourseWrapper(coreCourses[i]);
            coreCourseJSONCourseWrapper.add(coreCourse);
            updatet_core_table.setItems(coreCourseJSONCourseWrapper);

            // Remove the selected class from the dropdown 
            updatet_core_dropdown.getItems().remove(coreCourses[i]);
            
            coreCourse.removeTableCourse(updatet_core_table, coreCourse, updatet_core_dropdown, coreCourses[i]);
            
        }

        // POPULATE ELECTIVE COURSE TABLE
        String electiveCourses[] = degreeTrack.getElectiveClassListRequirement();

        // Populate the table with the 5k courses
        ObservableList<JSONCourseWrapper> electiveCourseJSONCourseWrapper = updatet_elective_table.getItems();

        // Make a JSONCourseWrapper for every 5k course
        for(int i = 0; i < electiveCourses.length; i++){
            JSONCourseWrapper electiveCourse = new JSONCourseWrapper(electiveCourses[i]);
            electiveCourseJSONCourseWrapper.add(electiveCourse);
            updatet_elective_table.setItems(electiveCourseJSONCourseWrapper);

            // Remove the selected class from the dropdown 
            updatet_elective_dropdown.getItems().remove(electiveCourses[i]);
            
            electiveCourse.removeTableCourse(updatet_elective_table, electiveCourse, updatet_elective_dropdown, electiveCourses[i]);
            
        }

    }

    @FXML
    public TableColumn<JSONCourseWrapper, String> updatet_5k_course_num_col;

    @FXML
    public TableColumn<JSONCourseWrapper, String> updatet_5k_remove_course_col;

    // "ADD" button is pressed for adding a 5XXX course
    @FXML
    void updatetAdd5kCourse(ActionEvent event) {
        // Grab the selected class from prerequisite dropdown
        String updatet_5k_course_num = updatet_5k_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the prerequisite table
        JSONCourseWrapper updatet_5k_course = new JSONCourseWrapper(updatet_5k_course_num);

        // create an observable list for the prerequisite table
        ObservableList<JSONCourseWrapper> updatet_5k_JSONCourseWrapper = updatet_5k_table.getItems();
        
        // populate the prerequisite table
        updatet_5k_dropdown.getItems().remove(updatet_5k_course_num);  

        updatet_5k_JSONCourseWrapper.add(updatet_5k_course);
        updatet_5k_table.setItems(updatet_5k_JSONCourseWrapper);
        updatet_5k_course.removeTableCourse(updatet_5k_table, updatet_5k_course, updatet_5k_dropdown, updatet_5k_course_num);
        
    }

    
    // Add to Core Courses Table
    @FXML
    public TableView<JSONCourseWrapper> updatet_core_table;

    @FXML
    public TableColumn<JSONCourseWrapper, String> updatet_core_course_num_col;

    @FXML
    public TableColumn<JSONCourseWrapper, String> updatet_core_remove_course_col;

    // "ADD" button is pressed for adding a core course
    @FXML
    void updatetAddCoreCourse(ActionEvent event) {
         // Grab the selected class from prerequisite dropdown
         String updatet_core_course_num = updatet_core_dropdown.getValue();

         // create an instance of JSONCourseWrapper to add to the prerequisite table
         JSONCourseWrapper updatet_core_course = new JSONCourseWrapper(updatet_core_course_num);
 
         // create an observable list for the prerequisite table
         ObservableList<JSONCourseWrapper> updatet_core_JSONCourseWrapper = updatet_core_table.getItems();
         
         // populate the prerequisite table
         updatet_core_dropdown.getItems().remove(updatet_core_course_num);  
 
         updatet_core_JSONCourseWrapper.add(updatet_core_course);
         updatet_core_table.setItems(updatet_core_JSONCourseWrapper);
         updatet_core_course.removeTableCourse(updatet_core_table, updatet_core_course, updatet_core_dropdown, updatet_core_course_num);
        
    }

    // Add to Elective Courses Table
    @FXML
    public TableView<JSONCourseWrapper> updatet_elective_table;

    @FXML
    public TableColumn<JSONCourseWrapper, String> updatet_elective_course_num_col;

    @FXML
    public TableColumn<JSONCourseWrapper, String> updatet_elective_remove_course_col;
  
    // "ADD" button is pressed for adding a elective course
    @FXML
    void updatetAddElectiveCourse(ActionEvent event) {
        // Grab the selected class from prerequisite dropdown
        String updatet_elective_course_num = updatet_elective_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the prerequisite table
        JSONCourseWrapper updatet_elective_course = new JSONCourseWrapper(updatet_elective_course_num);

        // create an observable list for the prerequisite table
        ObservableList<JSONCourseWrapper> updatet_elective_JSONCourseWrapper = updatet_elective_table.getItems();
        
        // populate the prerequisite table
        updatet_elective_dropdown.getItems().remove(updatet_elective_course_num);  

        updatet_elective_JSONCourseWrapper.add(updatet_elective_course);
        updatet_elective_table.setItems(updatet_elective_JSONCourseWrapper);
        updatet_elective_course.removeTableCourse(updatet_elective_table, updatet_elective_course, updatet_elective_dropdown, updatet_elective_course_num);
        
        
    }

    // "SUBMIT" button is pressed for updating a degree track
    @FXML
    void updatetSubmit(ActionEvent event) {
        String degreeName = updatet_track_name.getText().toString();
        String coreRequirementAmount = updatet_num_core_courses.getText().toString();
        String coreGPARequirement = updatet_core_gpa_requirements.getText().toString();
        boolean coreReplaceHighestAttempt = updatet_core_replace_2nd.isSelected();
        boolean coreAllowSeventhElective = updatet_core_allow_7th.isSelected();
        String electiveRequirementAmount = updatet_num_electives.getText().toString();
        String electiveGPARequirement = updatet_elective_gpa_requirements.getText().toString();
        boolean electiveReplaceHighestAttempt = updatet_elective_replace_2nd.isSelected();
        boolean electiveAllowOneLowerCourse = updatet_elective_allow_5k.isSelected();
        String overallGPARequirement = updatet_overall_gpa.getText().toString();
        boolean activeStatus = addt_active_status.isSelected();
        
    }


    /* 
     * REMOVE DEGREE TRACK
    */
    @FXML
    private ComboBox<String> removet_dropdown;

    @FXML
    private Button removet_remove_btn;

    @FXML
    public void removeDegreeTrack(ActionEvent event)
    {
        System.out.println("Remove degree track");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        // Clear Tables
        addc_prerequisites_table.getItems().clear();

        // Class type dropdown
        ObservableList<String> classTypeList = FXCollections.observableArrayList("Admission", "Elective", "Core");
        addc_type_dropdown.setItems(classTypeList);
        updatec_type_dropdown.setItems(classTypeList);
        
        // Drop down for all courses
        CourseList courseList = new CourseList("resources/CourseList.json");
        List<JSONCourse> allCourses = courseList.GetCourseList();
        ObservableList<String> addcPreqDropList = FXCollections.observableArrayList();
        ObservableList<String> removecDropList = FXCollections.observableArrayList();
        ObservableList<String> updatecDropList = FXCollections.observableArrayList();
        ObservableList<String> addt5kDropList = FXCollections.observableArrayList();
        ObservableList<String> addtCoreDropList = FXCollections.observableArrayList();
        ObservableList<String> addtElectiveDropList = FXCollections.observableArrayList();
        

        for (JSONCourse course : allCourses) {
            addcPreqDropList.add(course.getCourseNumber());
            removecDropList.add(course.getCourseNumber());
            updatecDropList.add(course.getCourseNumber());
            addt5kDropList.add(course.getCourseNumber());
            addtCoreDropList.add(course.getCourseNumber());
            addtElectiveDropList.add(course.getCourseNumber());
        }

        Collections.sort(addcPreqDropList);
        Collections.sort(removecDropList);
        Collections.sort(updatecDropList);
        Collections.sort(addt5kDropList);
        Collections.sort(addtCoreDropList);
        Collections.sort(addtElectiveDropList);

        addc_prerequisites_dropdown.setItems(addcPreqDropList);
        removec_dropdown.setItems(removecDropList);
        updatec_class_dropdown.setItems(updatecDropList);
        addt_5k_dropdown.setItems(addt5kDropList);
        addt_core_dropdown.setItems(addtCoreDropList);
        addt_elective_dropdown.setItems(addtElectiveDropList);

        // down for all degree tracks
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        List<JSONDegree> allDegreeTracks = degreeList.GetDegreeList();
        ObservableList<String> updatetDropList = FXCollections.observableArrayList();
        Collections.sort(updatetDropList);

        for(JSONDegree degreeTrack: allDegreeTracks){
            updatetDropList.add(degreeTrack.getDegreeName());
        }
        updatet_dropdown.setItems(updatetDropList);

        // ADD COURSE TAB - PREREQUISITES TABLE - setting columns using getters and setters from JSONCourseWrapper
        addc_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        addc_course_remove_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));


        // UPDATE COURSE TAB - PREREQUISITES TABLE - setting columns using getters and setters from JSONCourseWrapper
        updatec_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        updatec_course_remove_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));
        
        

        // ADD TRACK TAB - ADD 5k COURSES TABLE - setting columns using getters and setters from JSONCourseWrapper
        addt_5k_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        addt_5k_remove_course_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

        // ADD TRACK TAB - ADD CORE COURSES TABLE - setting columns using getters and setters from JSONCourseWrapper
        addt_core_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        addt_core_remove_course_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));


        // ADD TRACK TAB - ADD ELECTIVE COURSES TABLE - setting columns using getters and setters from JSONCourseWrapper
        addt_elective_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        addt_elective_remove_course_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

        // UPDATE TRACK TAB - ADD 5k COURSES TABLE - setting columns using getters and setters from JSONCourseWrapper
        updatet_5k_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        updatet_5k_remove_course_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

        // UPDATE TRACK TAB - ADD CORE COURSES TABLE - setting columns using getters and setters from JSONCourseWrapper
        updatet_core_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        updatet_core_remove_course_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));


        // UPDATE TRACK TAB - ADD ELECTIVE COURSES TABLE - setting columns using getters and setters from JSONCourseWrapper
        updatet_elective_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        updatet_elective_remove_course_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));
        
        

    }

}
