package com.group12.degreeaudit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.DegreeList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class degreePlanningSceneController implements Initializable{
    private Student student = null;
    

    /* 
     * RETURN BUTTON
    */
    @FXML
    private Button return_to_menu_btn, admission_prereq_add_button, req_core_add_button, core_options_add_buttons, electives_add_button, addl_electives_add_button;
    
    // Handles return button
    @FXML
    public void returnToMenu(ActionEvent event) throws IOException 
    {
        Stage stage;
        Parent root;
        stage = (Stage) return_to_menu_btn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/menuScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private TextField tfTitle, student_name, student_id, semester_admitted, anticipated_graduation;

    @FXML
    private ComboBox<String> degree_plan_dropdown, req_core_add_dropdown, core_options_add_dropdown, electives_add_dropdown, addl_electives_add_dropdown; 

    @FXML
    private CheckBox fast_track_checkbox, thesis_checkbox;

    @FXML
    public TableView<Course> admission_prereq_table, req_core_table, core_options_table, electives_table, addl_electives_table;

    @FXML
    public TableColumn<Course, String> admission_prereq_course_col, admission_prereq_name_col, admission_prereq_semester_col, admission_prereq_grade_col, admission_prereq_remove_col;
    public TableColumn<Course, Boolean> admission_prereq_waiver_col;

    @FXML
    public TableColumn<Course, String> req_core_course_col, req_core_course_name_col, req_core_semester_col, req_core_grade_col, req_core_remove_col;
    public TableColumn<Course, Boolean> req_core_transfer_col;

    @FXML
    public TableColumn<Course, String> core_options_course_col, core_options_name_col, core_options_semester_col, core_options_grade_col, core_options_remove_col;
    public TableColumn<Course, Boolean> core_options_transfer_col;

    @FXML
    public TableColumn<Course, String> electives_course_col, electives_name_col, electives_semester_col,  electives_grade_col, electives_remove_col;
    public TableColumn<Course, Boolean> electives_transfer_col;

    @FXML
    public TableColumn<Course, String> addl_electives_course_col, addl_electives_name_col, addl_electives_semester_col, addl_electives_grade_col, addl_electives_remove_col;
    public TableColumn<Course, Boolean> addl_electives_transfer_col;

    @FXML
    private TextField admission_add_semester, req_core_add_semester, core_options_add_semester, electives_add_semester, addl_electives_add_semester;
    
    @FXML
    private ComboBox<String> admission_add_grade;

    @FXML
    private TextField admission_add_num, req_core_add_num, core_options_add_num, electives_add_num, addl_electives_add_num;

    @FXML
    private TextField admission_add_name, req_core_add_name, core_options_add_name, electives_add_name, addl_electives_add_name;

    @FXML
    private CheckBox req_core_add_new, core_options_add_new, electives_add_new, addl_electives_add_new;

    @FXML
    private CheckBox admission_add_waiver, req_core_add_transfer, core_options_add_transfer, electives_add_transfer, addl_electives_add_transfer;

    @FXML
    private Tooltip coreCourseTooltip, electiveTooltip;



    @FXML
    void btnOkClicked(ActionEvent event) {
        Stage mainWindow = (Stage) tfTitle.getScene().getWindow();
        String title = tfTitle.getText();
        mainWindow.setTitle(title);
    }


    @FXML
    void getDegreePlanInfo(ActionEvent event) {

        String degreeTrackName = degree_plan_dropdown.getValue();
        if (degreeTrackName != null && student != null) {
            // Get Degree track name
            DegreeList degreeList = new DegreeList("resources/DegreeList.json");
            JSONDegree degreeTrack = degreeList.GetDegreeFromList(degreeTrackName);

            // Based on degree track name, populate tooltip
            coreCourseTooltip.setText(degreeTrack.getCoreGPARequirement() + " Grade Point Average Required.");
            electiveTooltip.setText(degreeTrack.getElectiveGPARequirement() + " Grade Point Average Required.");

            // Populate course dropdowns
            ObservableList<String> reqCoreList = FXCollections.observableArrayList();
            ObservableList<String> optionalCoreList = FXCollections.observableArrayList();
            ObservableList<String> electiveList = FXCollections.observableArrayList();
            ObservableList<String> addlElectiveList = FXCollections.observableArrayList();
            
            // Used to get course title based on course number
            // Used because transcript course title format differs from JSONCourse
            CourseList courseList = new CourseList("resources/CourseList.json");

            // ADMISSION PART MISSING
            for (String course : degreeTrack.getCoreClassListRequirement()) {
                reqCoreList.add(course + " - " + courseList.GetCourseFromList(course).getCourseName());
            }
            for (String course : degreeTrack.getOptionsCoreClassListRequirement()) {
                optionalCoreList.add(course + " - " + courseList.GetCourseFromList(course).getCourseName());
            }
            for (String course : degreeTrack.getElectiveClassListRequirement()) {
                electiveList.add(course + " - " + courseList.GetCourseFromList(course).getCourseName());
            }
            for (String course : degreeTrack.getElectivesAcceptedLowerCourses()) {
                addlElectiveList.add(course + " - " + courseList.GetCourseFromList(course).getCourseName());
            }
            Collections.sort(reqCoreList);
            Collections.sort(optionalCoreList);
            Collections.sort(electiveList);
            Collections.sort(addlElectiveList);
            req_core_add_dropdown.setItems(reqCoreList);
            core_options_add_dropdown.setItems(optionalCoreList);
            electives_add_dropdown.setItems(electiveList);
            addl_electives_add_dropdown.setItems(addlElectiveList);

           
            String courseNumAndName = "";
            // Populate already taken core courses
            ObservableList<Course> coreCourses = req_core_table.getItems();
            for(int i = 0; i < student.matchCoreCourses(degreeTrack).size(); i++){
                coreCourses.add(student.matchCoreCourses(degreeTrack).get(i));
                // WHAT THE HECK WHY DOES TANSCRIPT CLASSES NAME NOT MATCH THE COURSE BOOOK ALFDKJAS;LDFKJAF I HAT EIT HERE
                //TRANSCRIPT: System.out.println(student.matchCoreCourses(degreeTrack).get(i).getCourseNumber() + " - " + student.matchCoreCourses(degreeTrack).get(i).getCourseTitle());
                //JSONSCourse: System.out.println(student.matchCoreCourses(degreeTrack).get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchCoreCourses(degreeTrack).get(i).getCourseNumber()).getCourseName());
                courseNumAndName = student.matchCoreCourses(degreeTrack).get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchCoreCourses(degreeTrack).get(i).getCourseNumber()).getCourseName();
                req_core_add_dropdown.getItems().remove(courseNumAndName);
                student.matchCoreCourses(degreeTrack).get(i).removeCourse(req_core_table, student.matchCoreCourses(degreeTrack).get(i), req_core_add_dropdown, courseNumAndName, false);
            }

            // Populate already taken optional courses
            ObservableList<Course> optionalCoreCourses = core_options_table.getItems();
            for(int i = 0; i < student.matchCoreOptionCourses(degreeTrack).size(); i++){
                optionalCoreCourses.add(student.matchCoreOptionCourses(degreeTrack).get(i));
                courseNumAndName = student.matchCoreOptionCourses(degreeTrack).get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchCoreOptionCourses(degreeTrack).get(i).getCourseNumber()).getCourseName();
                core_options_add_dropdown.getItems().remove(courseNumAndName);
                student.matchCoreOptionCourses(degreeTrack).get(i).removeCourse(core_options_table, student.matchCoreOptionCourses(degreeTrack).get(i), core_options_add_dropdown, courseNumAndName, false);
            }
            
            // Populate already taken elective courses
            ObservableList<Course> electiveCourses = electives_table.getItems();
            for(int i = 0; i < student.matchElectiveCourses(degreeTrack).size(); i++){
                electiveCourses.add(student.matchElectiveCourses(degreeTrack).get(i));
                courseNumAndName = student.matchElectiveCourses(degreeTrack).get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchElectiveCourses(degreeTrack).get(i).getCourseNumber()).getCourseName();
                electives_add_dropdown.getItems().remove(courseNumAndName);
                student.matchElectiveCourses(degreeTrack).get(i).removeCourse(electives_table, student.matchElectiveCourses(degreeTrack).get(i), electives_add_dropdown, courseNumAndName, false);
            }

            // Populate already taken lower level elective courses
            ObservableList<Course> lowerElectiveCourses = addl_electives_table.getItems();
            for(int i = 0; i < student.matchAddlElectiveCourses(degreeTrack).size(); i++){
                lowerElectiveCourses.add(student.matchAddlElectiveCourses(degreeTrack).get(i));
                courseNumAndName = student.matchAddlElectiveCourses(degreeTrack).get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchAddlElectiveCourses(degreeTrack).get(i).getCourseNumber()).getCourseName();
                addl_electives_add_dropdown.getItems().remove(courseNumAndName);
                student.matchAddlElectiveCourses(degreeTrack).get(i).removeCourse(addl_electives_table, student.matchAddlElectiveCourses(degreeTrack).get(i), addl_electives_add_dropdown, courseNumAndName, false);
            }

        }
    }

    // Handles add button
    @FXML
    public void addAdmission(ActionEvent event){

        // Grab name and number
        String courseNum = admission_add_num.getText().toString();
        String courseTitle = admission_add_name.getText().toString();
        
        // Grab semester and transfer
        String semester = admission_add_semester.getText().toString();
        boolean transfer = admission_add_waiver.isSelected();
        String grade = admission_add_grade.getValue();

        // create an instance of Course to add to the table
        // Constructor: Course(String courseNum, String semester, String grade, String courseTitle, boolean transfer) 
        Course course = new Course(courseNum, semester, grade, courseTitle, transfer);

        // create an observable list for the table
        ObservableList<Course> tableList = admission_prereq_table.getItems();

        // Add course to the table
        tableList.add(course);
        admission_prereq_table.setItems(tableList);
        course.removeCourse(admission_prereq_table, course, null, courseNum + " - " + courseTitle, false);

        // Clear all fields for this section
        cleaur(null, admission_add_num, admission_add_name, null, admission_add_semester, admission_add_waiver);
        admission_add_grade.getSelectionModel().clearSelection();
    }


    // Handles add button
    @FXML
    public void addReqCore(ActionEvent event){
        // Adding course not included in dropdown, otherwise use dropdown
        CourseList courseList = new CourseList("resources/CourseList.json");
        boolean addNew = req_core_add_new.isSelected();
        String courseNum, courseTitle;

        if(addNew){
            courseNum = req_core_add_num.getText().toString();
            courseTitle = req_core_add_name.getText().toString();
        }
        else{
            // course drop down has course number and course name delimited by dash
            courseNum = req_core_add_dropdown.getValue().split(" - ")[0];
            courseTitle = req_core_add_dropdown.getValue().split(" - ")[1];
        }
        
        // Grab semester and transfer
        String semester = req_core_add_semester.getText().toString();
        boolean transfer = req_core_add_transfer.isSelected();

        // create an instance of Course to add to the table
        // Constructor: Course(String courseNum, String semester, String grade, String courseTitle, boolean transfer) 
        Course course = new Course(courseNum, semester, "", courseTitle, transfer);

        // create an observable list for the table
        ObservableList<Course> tableList = req_core_table.getItems();

        // populate the table & remove the selected item from dropdown
        req_core_add_dropdown.getItems().remove(courseNum + " - " + courseTitle);

        // Add course to the table
        tableList.add(course);
        req_core_table.setItems(tableList);
        course.removeCourse(req_core_table, course, req_core_add_dropdown, courseNum + " - " + courseTitle, addNew);

        // Clear all fields for this section
        cleaur(req_core_add_new, req_core_add_num, req_core_add_name, req_core_add_dropdown, req_core_add_semester, req_core_add_transfer);

    }


    
    // Handles add button
    @FXML
    public void addOptionalCore(ActionEvent event){
        // Adding course not included in dropdown, otherwise use dropdown
        CourseList courseList = new CourseList("resources/CourseList.json");
        boolean addNew = core_options_add_new.isSelected();
        String courseNum, courseTitle;

        if(addNew){
            courseNum = core_options_add_num.getText().toString();
            courseTitle = core_options_add_name.getText().toString();
        }
        else{
            // course drop down has course number and course name delimited by dash
            courseNum = core_options_add_dropdown.getValue().split(" - ")[0];
            courseTitle = core_options_add_dropdown.getValue().split(" - ")[1];
        }
        
        // Grab semester and transfer
        String semester = core_options_add_semester.getText().toString();
        boolean transfer = core_options_add_transfer.isSelected();

        // create an instance of Course to add to the table
        // Constructor: Course(String courseNum, String semester, String grade, String courseTitle, boolean transfer) 
        Course course = new Course(courseNum, semester, "", courseTitle, transfer);

        // create an observable list for the table
        ObservableList<Course> tableList = core_options_table.getItems();

        // populate the table & remove the selected item from dropdown
        core_options_add_dropdown.getItems().remove(courseNum + " - " + courseTitle);

        // Add course to the table
        tableList.add(course);
        core_options_table.setItems(tableList);
        course.removeCourse(core_options_table, course, core_options_add_dropdown, courseNum + " - " + courseTitle, addNew);

        // Clear all fields for this section
        cleaur(core_options_add_new, core_options_add_num, core_options_add_name, core_options_add_dropdown, core_options_add_semester, core_options_add_transfer);

    }

    // Handles add button
    @FXML
    public void addElective(ActionEvent event){
        // Adding course not included in dropdown, otherwise use dropdown
        CourseList courseList = new CourseList("resources/CourseList.json");
        boolean addNew = electives_add_new.isSelected();
        String courseNum, courseTitle;

        if(addNew){
            courseNum = electives_add_num.getText().toString();
            courseTitle = electives_add_name.getText().toString();
        }
        else{
            // course drop down has course number and course name delimited by dash
            courseNum = electives_add_dropdown.getValue().split(" - ")[0];
            courseTitle = electives_add_dropdown.getValue().split(" - ")[1];
        }
        
        // Grab semester and transfer
        String semester = electives_add_semester.getText().toString();
        boolean transfer = electives_add_transfer.isSelected();

        // create an instance of Course to add to the table
        // Constructor: Course(String courseNum, String semester, String grade, String courseTitle, boolean transfer) 
        Course course = new Course(courseNum, semester, "", courseTitle, transfer);

        // create an observable list for the table
        ObservableList<Course> tableList = electives_table.getItems();

        // populate the table & remove the selected item from dropdown
        electives_add_dropdown.getItems().remove(courseNum + " - " + courseTitle);

        // Add course to the table
        tableList.add(course);
        electives_table.setItems(tableList);
        course.removeCourse(electives_table, course, electives_add_dropdown, courseNum + " - " + courseTitle, addNew);

        // Clear all fields for this section
        cleaur(electives_add_new, electives_add_num, electives_add_name, electives_add_dropdown, electives_add_semester, electives_add_transfer);

    }

    // Handles add button
    @FXML
    public void addAddlElective(ActionEvent event){
        // Adding course not included in dropdown, otherwise use dropdown
        CourseList courseList = new CourseList("resources/CourseList.json");
        boolean addNew = addl_electives_add_new.isSelected();
        String courseNum, courseTitle;

        if(addNew){
            courseNum = addl_electives_add_num.getText().toString();
            courseTitle = addl_electives_add_name.getText().toString();
        }
        else{
            // course drop down has course number and course name delimited by dash
            courseNum = addl_electives_add_dropdown.getValue().split(" - ")[0];
            courseTitle = addl_electives_add_dropdown.getValue().split(" - ")[1];
        }
        
        // Grab semester and transfer
        String semester = addl_electives_add_semester.getText().toString();
        boolean transfer = addl_electives_add_transfer.isSelected();

        // create an instance of Course to add to the table
        // Constructor: Course(String courseNum, String semester, String grade, String courseTitle, boolean transfer) 
        Course course = new Course(courseNum, semester, "", courseTitle, transfer);

        // create an observable list for the table
        ObservableList<Course> tableList = addl_electives_table.getItems();

        // populate the table & remove the selected item from dropdown
        addl_electives_add_dropdown.getItems().remove(courseNum + " - " + courseTitle);

        // Add course to the table
        tableList.add(course);
        addl_electives_table.setItems(tableList);
        course.removeCourse(addl_electives_table, course, addl_electives_add_dropdown, courseNum + " - " + courseTitle, addNew);

        // Clear all fields for this section
        cleaur(addl_electives_add_new, addl_electives_add_num, addl_electives_add_name, addl_electives_add_dropdown, addl_electives_add_semester, addl_electives_add_transfer);

    }


    private void cleaur(CheckBox courseNotInDropdown, TextField courseNum, TextField courseName, ComboBox<String> dropdown, TextField semester, CheckBox transfer) {
        // admissions table does not have disabled fields
        if(courseNotInDropdown != null){
            courseNotInDropdown.setSelected(false);
            courseNum.setDisable(true);
            courseName.setDisable(true);
        }
        
        courseNum.clear();
        courseName.clear();

        // admissions table does not have a dropdown
        if(dropdown != null){
            dropdown.getSelectionModel().clearSelection();
        }
        
        semester.clear();
        transfer.setSelected(false);
    }


    @FXML
    public void enableAddingNewReqClass(ActionEvent event){

        boolean addNew = req_core_add_new.isSelected();

        if(addNew){
            req_core_add_num.setDisable(false);
            req_core_add_name.setDisable(false);
        } else{
            req_core_add_num.setDisable(true);
            req_core_add_name.setDisable(true);
        }


    }

    @FXML
    public void enableAddingNewOptionalClass(ActionEvent event){

        boolean addNew = core_options_add_new.isSelected();

        if(addNew){
            core_options_add_num.setDisable(false);
            core_options_add_name.setDisable(false);
        } else{
            core_options_add_num.setDisable(true);
            core_options_add_name.setDisable(true);
        }


    }

    @FXML
    public void enableAddingNewElectiveClass(ActionEvent event){

        boolean addNew = electives_add_new.isSelected();

        if(addNew){
            electives_add_num.setDisable(false);
            electives_add_name.setDisable(false);
        } else{
            electives_add_num.setDisable(true);
            electives_add_name.setDisable(true);
        }


    }

    @FXML
    public void enableAddingNewAddlElectiveClass(ActionEvent event){

        boolean addNew = addl_electives_add_new.isSelected();

        if(addNew){
            addl_electives_add_num.setDisable(false);
            addl_electives_add_name.setDisable(false);
        } else{
            addl_electives_add_num.setDisable(true);
            addl_electives_add_name.setDisable(true);
        }


    }

    @FXML
    private void importTranscript(ActionEvent event){
        

        FileChooser fc = new FileChooser();
		// if we want to open fixed location
		//fc.setInitialDirectory(new File("D:\\\\Books"));
		

		// Only allowing .txt files
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = fc.showOpenDialog(null);
        

		if(selectedFile != null) {
			//listview.getItems().add(selectedFile.getAbsolutePath());
            CourseList courseList = new CourseList("resources/CourseList.json");
            TranscriptScanner transcriptScanner = new TranscriptScanner(selectedFile.toPath().toString(), courseList);
            student = transcriptScanner.scanTranscript();

            System.out.println("File is valid");
            System.out.println(selectedFile.toPath().toString());

            student_name.setText(student.getName());
            student_id.setText(student.getID());
            semester_admitted.setText(student.getSemesterAdmitted());
  

		}else {
			System.out.println("File is not valid!");
		}

        
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // dropdown for all degree tracks
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        List<JSONDegree> allDegreeTracks = degreeList.GetDegreeList();
        ObservableList<String> degreeTrackList = FXCollections.observableArrayList();
        Collections.sort(degreeTrackList);

        // Populate degree tracks
        for (JSONDegree degreeTrack : allDegreeTracks) {
            degreeTrackList.add(degreeTrack.getDegreeName());
        }
        degree_plan_dropdown.setItems(degreeTrackList);

        // Grades dropdown
        ObservableList<String> gradeList = FXCollections.observableArrayList("", "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F");
        admission_add_grade.setItems(gradeList);

        // Set admission table
        admission_prereq_course_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseNumber"));
        admission_prereq_name_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseTitle"));
        admission_prereq_semester_col.setCellValueFactory(new PropertyValueFactory<Course, String>("semester"));
        admission_prereq_waiver_col.setCellValueFactory(new PropertyValueFactory<Course, Boolean>("transfer"));
        admission_prereq_grade_col.setCellValueFactory(new PropertyValueFactory<Course, String>("grade"));
        admission_prereq_remove_col.setCellValueFactory(new PropertyValueFactory<Course, String>("button"));

        // Set core course table
        req_core_course_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseNumber"));
        req_core_course_name_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseTitle"));
        req_core_semester_col.setCellValueFactory(new PropertyValueFactory<Course, String>("semester"));
        req_core_transfer_col.setCellValueFactory(new PropertyValueFactory<Course, Boolean>("transfer"));
        req_core_grade_col.setCellValueFactory(new PropertyValueFactory<Course, String>("grade"));
        req_core_remove_col.setCellValueFactory(new PropertyValueFactory<Course, String>("button"));

        // Set optional core course table
        core_options_course_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseNumber"));
        core_options_name_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseTitle"));
        core_options_semester_col.setCellValueFactory(new PropertyValueFactory<Course, String>("semester"));
        core_options_transfer_col.setCellValueFactory(new PropertyValueFactory<Course, Boolean>("transfer"));
        core_options_grade_col.setCellValueFactory(new PropertyValueFactory<Course, String>("grade"));
        core_options_remove_col.setCellValueFactory(new PropertyValueFactory<Course, String>("button"));

        // Set elective course table
        electives_course_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseNumber"));
        electives_name_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseTitle"));
        electives_semester_col.setCellValueFactory(new PropertyValueFactory<Course, String>("semester"));
        electives_transfer_col.setCellValueFactory(new PropertyValueFactory<Course, Boolean>("transfer"));
        electives_grade_col.setCellValueFactory(new PropertyValueFactory<Course, String>("grade"));
        electives_remove_col.setCellValueFactory(new PropertyValueFactory<Course, String>("button"));

        // Set lower level elective course table
        addl_electives_course_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseNumber"));
        addl_electives_name_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseTitle"));
        addl_electives_semester_col.setCellValueFactory(new PropertyValueFactory<Course, String>("semester"));
        addl_electives_transfer_col.setCellValueFactory(new PropertyValueFactory<Course, Boolean>("transfer"));
        addl_electives_grade_col.setCellValueFactory(new PropertyValueFactory<Course, String>("grade"));
        addl_electives_remove_col.setCellValueFactory(new PropertyValueFactory<Course, String>("button"));

    }
}
