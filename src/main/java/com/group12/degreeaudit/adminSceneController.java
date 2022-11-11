package com.group12.degreeaudit;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.JSONCourse;
import com.group12.degreeaudit.Administration.JSONCourseWrapper;

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

    /* 
     * RETURN BUTTON
    */
    @FXML
    private Button return_to_menu_btn;
    
    // Handles return button
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

    /*
     * EXPORT SETTINGS 
    */
    @FXML
    private Button export_settings_btn;
    
    // Handles export button
    @FXML 
    public void exportSettings(ActionEvent event){
        System.out.println("Export settings");
    }

    /* 
     * TABS
    */
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
    
    // Add to Prerequisites Courses Table
    @FXML
    public TableView<JSONCourseWrapper> addc_prerequisites_table;
    
    //Columns
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
        
        // populate the prerequisite table
        addc_JSONCourseWrapper.add(addc_prereq_course);
        addc_prerequisites_table.setItems(addc_JSONCourseWrapper);
        addc_prereq_course.removePrerequisite(addc_prerequisites_table, addc_prereq_course, addc_prerequisites_dropdown, addc_prereq_course_num);
    
    }

    @FXML
    public void testMeBestie(ActionEvent event){
        initialize(null, null);
    }


    // "ADD" button is pressed for adding a course
    @FXML
    private Button addc_add_btn;

    @FXML
    public void addCourseToCourseList(ActionEvent event)
    {
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
        
        CourseList courseList = new CourseList("resources/CourseList.json");
        courseList.AddCourseToList(courseNumber, courseName, courseDescription, prerequisites, classType, activeStatus);
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

    // Add to Prerequisites Courses Table
    @FXML
    public TableView<JSONCourseWrapper> updatec_prerequisites_table;
    
    //Columns
    @FXML
    public TableColumn<JSONCourseWrapper, String> updatec_course_num_col;
    
    @FXML
    public TableColumn<JSONCourseWrapper, String> updatec_course_remove_col;

    // Add to table button
    @FXML
    void updatecPrerequisites(ActionEvent event) {
        // String courseNumber = updatec_class_number.getText().toString();
        // String courseName = updatec_class_name.getText().toString();
        // String courseDescription = updatec_class_description.getText().toString();
        // char classType = updatec_type_dropdown.getValue().charAt(0);
        // boolean activeStatus = updatec_active_status.isSelected();

        // // Getting prereqs from table
        // String prerequisites[] = new String[updatec_prerequisites_table.getItems().size()];
        // for (int i = 0; i < prerequisites.length; i++) {

        //     prerequisites[i] = updatec_prerequisites_table.getItems().get(i).getJsonCourse().getCourseNumber();
        // }


        // System.out.println("Course Number is: " + courseNumber);
        
        // CourseList courseList = new CourseList("resources/CourseList.json");
        // courseList.AddCourseToList(courseNumber, courseName, courseDescription, prerequisites, classType, activeStatus);
        // initialize(null, null);
    }

    @FXML
    private Button updatec_update_btn;

    @FXML
    public void updateCourseInCourseList(ActionEvent event)
    {
        JSONCourse courseToUpdate = new JSONCourse();
        CourseList courseList = new CourseList("resources/CourseList.json");
        courseList.UpdateCourseInList(null);
        System.out.println("update course in course list");
    }
 

    /* 
     * REMOVE COURSE TAB
    */
    @FXML
    private ComboBox<String> removec_dropdown;

    @FXML
    private Button removec_remove_btn;

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
    public TextField addt_track_name, addt_num_core_courses, addt_gpa_requirements, 
                     addt_num_electives, addt_overall_gpa, addt_5k_course_num_input, 
                     addt_5k_course_name_input, addt_core_course_num_input, addt_core_course_name_input,
                     addt_elective_num_input, addt_elective_name_input;

    @FXML
    private CheckBox addt_active_status, addt_replace_2nd, addt_allow_7th;

    // Add to 5XXX Courses Table
    @FXML
    public TableView<CourseSample> addt_5k_table;

    //Columns
    @FXML
    public TableColumn<CourseSample, String> addt_5k_course_num_col;

    @FXML
    public TableColumn<CourseSample, String> addt_5k_course_name_col;

    @FXML
    public TableColumn<CourseSample, String> addt_5k_remove_course_col;

    // Add to table button
    @FXML
    void addtAdd5kCourse(ActionEvent event) {
        CourseSample course = new CourseSample(addt_5k_course_name_input.getText(), addt_5k_course_num_input.getText());
        ObservableList<CourseSample> courseSample = addt_5k_table.getItems();
        courseSample.add(course);
        addt_5k_table.setItems(courseSample);
        course.ButtonCell(addt_5k_table, course);
        System.out.println(addt_5k_table.getItems());
    }

    
    // Add to Core Courses Table
    @FXML
    public TableView<CourseSample> addt_core_table;

    //Columns
    @FXML
    public TableColumn<CourseSample, String> addt_core_course_num_col;

    @FXML
    public TableColumn<CourseSample, String> addt_core_course_name_col;

    @FXML
    public TableColumn<CourseSample, String> addt_core_remove_course_col;

    // Add to table button
    @FXML
    void addtAddCoreCourse(ActionEvent event) {
        System.out.println("Add core course");
    }

    // Add to Elective Courses Table
    @FXML
    public TableView<CourseSample> addt_elective_table;

    //Columns
    @FXML
    public TableColumn<CourseSample, String> addt_elective_course_num_col;

    @FXML
    public TableColumn<CourseSample, String> addt_elective_course_name_col;

    @FXML
    public TableColumn<CourseSample, String> addt_elective_remove_course_col;
  
    // Add to table button
    @FXML
    void addtAddElectiveCourse(ActionEvent event) {
        System.out.println("Add elective course");
        
    }

    // Submit button
    @FXML
    void addtSubmit(ActionEvent event) {
        System.out.println("Submit Degree Track");
        
    }


    /* 
     * UPDATE DEGREE TRACK TAB
    */
    @FXML
    private ComboBox<String> updatet_dropdown; 

    @FXML
    public TextField updatet_track_name, updatet_num_core_courses, updatet_gpa_requirements, 
                     updatet_num_electives, updatet_overall_gpa, updatet_5k_course_num_input, 
                     updatet_5k_course_name_input, updatet_core_course_num_input, updatet_core_course_name_input,
                     updatet_elective_num_input, updatet_elective_name_input;

    @FXML
    private CheckBox updatet_active_status, updatet_replace_2nd, updatet_allow_7th;

    // Add to 5XXX Courses Table
    @FXML
    public TableView<CourseSample> updatet_5k_table;

    //Columns
    @FXML
    public TableColumn<CourseSample, String> updatet_5k_course_num_col;

    @FXML
    public TableColumn<CourseSample, String> updatet_5k_course_name_col;

    @FXML
    public TableColumn<CourseSample, String> updatet_5k_remove_course_col;

    // Add to table button
    @FXML
    void updatetAdd5kCourse(ActionEvent event) {
        System.out.println("Add 5k course");
        
    }

    
    // Add to Core Courses Table
    @FXML
    public TableView<CourseSample> updatet_core_table;

    //Columns
    @FXML
    public TableColumn<CourseSample, String> updatet_core_course_num_col;

    @FXML
    public TableColumn<CourseSample, String> updatet_core_course_name_col;

    @FXML
    public TableColumn<CourseSample, String> updatet_core_remove_course_col;

    // Add to table button
    @FXML
    void updatetAddCoreCourse(ActionEvent event) {
        System.out.println("Add core course");
        
    }

    // Add to Elective Courses Table
    @FXML
    public TableView<CourseSample> updatet_elective_table;

    //Columns
    @FXML
    public TableColumn<CourseSample, String> updatet_elective_course_num_col;

    @FXML
    public TableColumn<CourseSample, String> updatet_elective_course_name_col;

    @FXML
    public TableColumn<CourseSample, String> updatet_elective_remove_course_col;
  
    // Add to table button
    @FXML
    void updatetAddElectiveCourse(ActionEvent event) {
        System.out.println("Add elective course");
        
    }

    // Submit button
    @FXML
    void updatetSubmit(ActionEvent event) {
        System.out.println("Update Degree Track");
        
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
        updatec_prerequisites_table.getItems().clear();

        // ADD COURSE TAB
        // Drop down for prerequisites
        CourseList courseList = new CourseList("resources/CourseList.json");
        List<JSONCourse> allCourses = courseList.GetCourseList();
        ObservableList<String> AddcPreqDropList = FXCollections.observableArrayList();
        ObservableList<String> RemoveDropList = FXCollections.observableArrayList();
        ObservableList<String> UpdateDropList = FXCollections.observableArrayList();
        ObservableList<String> UpdatePreqDropList = FXCollections.observableArrayList();

        for (JSONCourse course : allCourses) {
            AddcPreqDropList.add(course.getCourseNumber());
            RemoveDropList.add(course.getCourseNumber());
            UpdateDropList.add(course.getCourseNumber());
            UpdatePreqDropList.add(course.getCourseNumber());
        }
        addc_prerequisites_dropdown.setItems(AddcPreqDropList);
        removec_dropdown.setItems(RemoveDropList);
        updatec_class_dropdown.setItems(UpdateDropList);
        updatec_prerequisites_dropdown.setItems(UpdatePreqDropList);

        // ADD COURSE TAB
        // PREREQUISITES TABLE - setting columns using getters and setters from JSONCourseWrapper
        addc_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        addc_course_remove_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));


        // UPDATE COURSE TAB
        // PREREQUISITES TABLE - setting columns using getters and setters from JSONCourseWrapper
        updatec_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        updatec_course_remove_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));


        // Class type dropdown
        ObservableList<String> classTypeList = FXCollections.observableArrayList("Admission", "Elective", "Core");
        addc_type_dropdown.setItems(classTypeList);
        updatec_type_dropdown.setItems(classTypeList);
        // REMOVE COURSE TAB
        

        
        // ADD DEGREE TRACK TAB
        addt_5k_course_name_col.setCellValueFactory(new PropertyValueFactory<CourseSample, String>("name"));
        addt_5k_course_num_col.setCellValueFactory(new PropertyValueFactory<CourseSample, String>("number"));
        addt_5k_remove_course_col.setCellValueFactory(new PropertyValueFactory<CourseSample, String>("button"));


    }

}
