package com.group12.degreeaudit;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.JSONCourse;

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
    public void returnToMenu(ActionEvent event) throws IOException 
    {
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
    public TableView<CourseSample> addc_prerequisites_table;
    
    //Columns
    @FXML
    public TableColumn<CourseSample, String> addc_course_num_col;
    
    @FXML
    public TableColumn<CourseSample, String> addc_course_remove_col;

    // Add to table button
    @FXML
    void addcPrerequisites(ActionEvent event) {
        System.out.println("add to prerequisites table");
        System.out.println(addc_prerequisites_dropdown.getValue());
        /*
        String courseNum = addc_prerequisites_dropdown.getValue();
        JSONCourse course = new JSONCourse(courseNum);
        ObservableList<JSONCourse> courseSample = addc_prerequisites_table.getItems();
        courseSample.add(course);
        addc_prerequisites_table.setItems(courseSample);
        course.ButtonCell(addc_prerequisites_table, course);
        //addc_prerequisites_table.setItems(addc_prerequisites_dropdown.getValue());
 */
    
    }



    // add course
    @FXML
    private Button addc_add_btn;

    @FXML
    public void addCourseToCourseList(ActionEvent event)
    {
        String courseNumber = addc_class_number.getText().toString();
        String courseName = addc_class_name.getText().toString();
        String courseDescription = addc_class_description.getText().toString();
        String[] prereqs = new String[addc_prerequisites_table.getItems().size()];  
        char classType = addc_type_dropdown.getValue().charAt(0);
        boolean activeStatus = addc_active_status.isSelected();

        int i = 0;
        for (CourseSample course : addc_prerequisites_table.getItems()) {
            //prereqs[i] = addc_prerequisites_table.getValueAt(1, i);
            prereqs [i] = course.getNumber();
            System.out.println(prereqs[i]);
            i++;
        }

        System.out.println("Course Number is: " + courseNumber);
        
        CourseList courseList = new CourseList("resources/CourseList.json");
        courseList.AddCourseToList(courseNumber, courseName, courseDescription, prereqs, classType, activeStatus);
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
    public TableView<CourseSample> updatec_prerequisites_table;
    
    //Columns
    @FXML
    public TableColumn<CourseSample, String> updatec_course_num_col;
    
    @FXML
    public TableColumn<CourseSample, String> updatec_course_remove_col;

    // Add to table button
    @FXML
    void updatecPrerequisites(ActionEvent event) {
        System.out.println("update prerequisites table");
    }

    @FXML
    private Button updatec_update_btn;

    @FXML
    public void updateCourseInCourseList(ActionEvent event)
    {
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
        System.out.println("Remove course in course list");
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
    public TableView<CourseSample> addt_5k_table_courses;

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
        ObservableList<CourseSample> courseSample = addt_5k_table_courses.getItems();
        courseSample.add(course);
        addt_5k_table_courses.setItems(courseSample);
        course.ButtonCell(addt_5k_table_courses, course);
    }

    
    // Add to Core Courses Table
    @FXML
    public TableView<CourseSample> addt_core_table_courses;

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
    public TableView<CourseSample> addt_elective_table_courses;

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
    public TableView<CourseSample> updatet_5k_table_courses;

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
    public TableView<CourseSample> updatet_core_table_courses;

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
    public TableView<CourseSample> updatet_elective_table_courses;

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

        // UPDATE COURSE TAB
        ObservableList<String> list = FXCollections.observableArrayList("CS123", "CS456", "CS789");
        updatec_class_dropdown.setItems(list);

        // UPDATE COURSE TAB
        ObservableList<String> classTypeList = FXCollections.observableArrayList("Admission", "Elective", "Core");
        addc_type_dropdown.setItems(classTypeList);

        CourseList courseList = new CourseList("resources/CourseList.json");
        List<JSONCourse> allCourses = courseList.GetCourseList();
        ObservableList<String> prereqList = FXCollections.observableArrayList();
        for (JSONCourse course : allCourses) {
            prereqList.add(course.getCourseNumber());
        }
        addc_prerequisites_dropdown.setItems(prereqList);

        // ADD DEGREE TRACK TAB
        addt_5k_course_name_col.setCellValueFactory(new PropertyValueFactory<CourseSample, String>("name"));
        addt_5k_course_num_col.setCellValueFactory(new PropertyValueFactory<CourseSample, String>("number"));
        addt_5k_remove_course_col.setCellValueFactory(new PropertyValueFactory<CourseSample, String>("button"));


        // ADD DEGREE TRACK TAB
        // addc_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourse, String>("name"));
        // addc_course_remove_col.setCellValueFactory(new PropertyValueFactory<JSONCourse, String>("button"));

    }

}
