package com.group12.degreeaudit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.DegreeList;
import com.group12.degreeaudit.Administration.DegreePlanner;
import com.group12.degreeaudit.Administration.JSONCourse;
import com.group12.degreeaudit.Administration.JSONDegree;
import com.group12.degreeaudit.Administration.FileActions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private ComboBox<String> degree_plan_dropdown, admission_prereq_add_dropdown, req_core_add_dropdown, core_options_add_dropdown, electives_add_dropdown, addl_electives_add_dropdown; 

    @FXML
    private CheckBox fast_track_checkbox, thesis_checkbox;

    @FXML
    public TableView<CourseWrapper> admission_prereq_table, req_core_table, core_options_table, electives_table, addl_electives_table;

    @FXML
    public TableColumn<CourseWrapper, String> admission_prereq_course_col, admission_prereq_name_col, admission_prereq_semester_col, admission_prereq_grade_col, admission_prereq_remove_col;
    public TableColumn<CourseWrapper, Boolean> admission_prereq_waiver_col;

    @FXML
    public TableColumn<CourseWrapper, String> req_core_course_col, req_core_course_name_col, req_core_semester_col, req_core_grade_col, req_core_remove_col;
    public TableColumn<CourseWrapper, Boolean> req_core_transfer_col;

    @FXML
    public TableColumn<CourseWrapper, String> core_options_course_col, core_options_name_col, core_options_semester_col, core_options_grade_col, core_options_remove_col;
    public TableColumn<CourseWrapper, Boolean> core_options_transfer_col;

    @FXML
    public TableColumn<CourseWrapper, String> electives_course_col, electives_name_col, electives_semester_col,  electives_grade_col, electives_remove_col;
    public TableColumn<CourseWrapper, Boolean> electives_transfer_col;

    @FXML
    public TableColumn<CourseWrapper, String> addl_electives_course_col, addl_electives_name_col, addl_electives_semester_col, addl_electives_grade_col, addl_electives_remove_col;
    public TableColumn<CourseWrapper, Boolean> addl_electives_transfer_col;

    @FXML
    private TextField admission_add_semester, req_core_add_semester, core_options_add_semester, electives_add_semester, addl_electives_add_semester;
    
    @FXML
    private ComboBox<String> admission_add_grade, req_core_add_grade, core_options_add_grade, electives_add_grade, addl_electives_add_grade;

    @FXML
    private CheckBox admission_add_all, req_core_add_all, core_options_add_all, electives_add_all, addl_electives_add_all;

    @FXML
    private CheckBox admission_add_waiver, req_core_add_transfer, core_options_add_transfer, electives_add_transfer, addl_electives_add_transfer;

    @FXML
    private Tooltip coreCourseTooltip, electiveTooltip;

    @FXML
    public void importStudent(ActionEvent event){
        clearAllFields();
        CourseList courseList = new CourseList("resources/CourseList.json");
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        FileActions importStudentFromFile = new FileActions(courseList, degreeList);
        student = importStudentFromFile.importStudent();

        student_name.setText(student.getName());
        student_id.setText(student.getID());
        semester_admitted.setText(student.getSemesterAdmitted());
        anticipated_graduation.setText(student.getAnticipatedGraduation());
        degree_plan_dropdown.setValue(student.getDegreeTrack().getDegreeName());
        fast_track_checkbox.setSelected(student.getFastTrack());
        thesis_checkbox.setSelected(student.getThesis());
        getDegreePlanInfo(event);
    }

    @FXML
    void btnOkClicked(ActionEvent event) {
        Stage mainWindow = (Stage) tfTitle.getScene().getWindow();
        String title = tfTitle.getText();
        mainWindow.setTitle(title);
    }


    @FXML
    void getDegreePlanInfo(ActionEvent event) 
    {
        clearAllFields();
        String degreeTrackName = degree_plan_dropdown.getValue();
        if(student == null)
        {
            student = new Student();
        }
        if (degreeTrackName != null && student != null) {
            // Get Degree track name
            DegreeList degreeList = new DegreeList("resources/DegreeList.json");
            JSONDegree degreeTrack = degreeList.GetDegreeFromList(degreeTrackName);
            student.setDegreeTrack(degreeTrack);

            // Based on degree track name, populate tooltip
            coreCourseTooltip.setText(degreeTrack.getCoreGPARequirement() + " Grade Point Average Required.");
            electiveTooltip.setText(degreeTrack.getElectiveGPARequirement() + " Grade Point Average Required.");

            // Populate course dropdowns
            ObservableList<String> admissionsList = FXCollections.observableArrayList();
            ObservableList<String> reqCoreList = FXCollections.observableArrayList();
            ObservableList<String> optionalCoreList = FXCollections.observableArrayList();
            ObservableList<String> electiveList = FXCollections.observableArrayList();
            ObservableList<String> addlElectiveList = FXCollections.observableArrayList();
            
            // Used to get course title based on course number
            // Used because transcript course title format differs from JSONCourse
            CourseList courseList = new CourseList("resources/CourseList.json");


            DegreePlanner degreePlan = new DegreePlanner(student, courseList, degreeList);
            admissionsList.clear();
            reqCoreList.clear();
            optionalCoreList.clear();
            electiveList.clear();
            addlElectiveList.clear();
            

            for (JSONCourse course : degreePlan.getPossibleCourses('A')) {
                if(course != null)
                    admissionsList.add(course.getCourseNumber() + " - " + course.getCourseName());
            }
            for (String course : degreeTrack.getCoreClassListRequirement()) {
                if(course != null && courseList.GetCourseFromList(course) != null)
                    reqCoreList.add(course + " - " + courseList.GetCourseFromList(course).getCourseName());
            }
            for (String course : degreeTrack.getOptionsCoreClassListRequirement()) {
                if(course != null && courseList.GetCourseFromList(course) != null)
                    optionalCoreList.add(course + " - " + courseList.GetCourseFromList(course).getCourseName());
            }
            for (String course : degreeTrack.getElectiveClassListRequirement()) {
                if(course != null && courseList.GetCourseFromList(course) != null)
                    electiveList.add(course + " - " + courseList.GetCourseFromList(course).getCourseName());
            }
            for (String course : student.getAllElectivesNotTaken(courseList, degreeTrack)) {
                if(course != null && courseList.GetCourseFromList(course) != null)
                    addlElectiveList.add(course + " - " + courseList.GetCourseFromList(course).getCourseName());
            }
            Collections.sort(admissionsList);
            Collections.sort(reqCoreList);
            Collections.sort(optionalCoreList);
            Collections.sort(electiveList);
            Collections.sort(addlElectiveList);
            admission_prereq_add_dropdown.setItems(admissionsList);
            req_core_add_dropdown.setItems(reqCoreList);
            core_options_add_dropdown.setItems(optionalCoreList);
            electives_add_dropdown.setItems(electiveList);
            addl_electives_add_dropdown.setItems(addlElectiveList);

           
            String courseNumAndName = "";

            // Populate already taken admission courses
            ObservableList<CourseWrapper> admissionCourses = admission_prereq_table.getItems();
            for(int i = 0; i < student.matchAdmissionCourses(degreeTrack).size(); i++){
                CourseWrapper cw = new CourseWrapper(student.matchAdmissionCourses(degreeTrack).get(i));
                admissionCourses.add(cw);
                courseNumAndName = student.matchAdmissionCourses(degreeTrack).get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchAdmissionCourses(degreeTrack).get(i).getCourseNumber()).getCourseName();
                admission_prereq_add_dropdown.getItems().remove(courseNumAndName);
                cw.removeCourse(admission_prereq_table, cw, admission_prereq_add_dropdown, courseNumAndName, student);
            }

            // Populate already taken core courses
            ObservableList<CourseWrapper> coreCourses = req_core_table.getItems();
            for(int i = 0; i < student.matchCoreCourses(degreeTrack).size(); i++){
                CourseWrapper cw = new CourseWrapper(student.matchCoreCourses(degreeTrack).get(i));
                coreCourses.add(cw);
                courseNumAndName = student.matchCoreCourses(degreeTrack).get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchCoreCourses(degreeTrack).get(i).getCourseNumber()).getCourseName();
                req_core_add_dropdown.getItems().remove(courseNumAndName);
                cw.removeCourse(req_core_table, cw, req_core_add_dropdown, courseNumAndName, student);
            }

            // Populate already taken optional courses
            ObservableList<CourseWrapper> optionalCoreCourses = core_options_table.getItems();
            for(int i = 0; i < student.matchCoreOptionCourses(degreeTrack).size(); i++){
                CourseWrapper cw = new CourseWrapper(student.matchCoreOptionCourses(degreeTrack).get(i));
                optionalCoreCourses.add(cw);
                courseNumAndName = student.matchCoreOptionCourses(degreeTrack).get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchCoreOptionCourses(degreeTrack).get(i).getCourseNumber()).getCourseName();
                core_options_add_dropdown.getItems().remove(courseNumAndName);
                cw.removeCourse(core_options_table, cw, core_options_add_dropdown, courseNumAndName, student);
            }

            // Populate already taken elective courses
            ObservableList<CourseWrapper> electiveCourses = electives_table.getItems();
            // for(int i = 0; i < student.matchElectiveCourses(degreeTrack).size(); i++){
            //     CourseWrapper cw = new CourseWrapper(student.matchElectiveCourses(degreeTrack).get(i));
            //     electiveCourses.add(cw);
            //     courseNumAndName = student.matchElectiveCourses(degreeTrack).get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchElectiveCourses(degreeTrack).get(i).getCourseNumber()).getCourseName();
            //     electives_add_dropdown.getItems().remove(courseNumAndName);
            //     cw.removeCourse(electives_table, cw, electives_add_dropdown, courseNumAndName, false, student);
            // }
            for(int i = 0; i < student.matchElectiveCourses(degreeTrack,"top5").size(); i++){
                CourseWrapper cw = new CourseWrapper(student.matchElectiveCourses(degreeTrack,"top5").get(i));
                electiveCourses.add(cw);
                courseNumAndName = student.matchElectiveCourses(degreeTrack,"top5").get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchElectiveCourses(degreeTrack,"top5").get(i).getCourseNumber()).getCourseName();

                if(electiveList.contains(courseNumAndName)){
                    electives_add_dropdown.getItems().remove(courseNumAndName);
                }
                
                cw.removeCourse(electives_table, cw, electives_add_dropdown, courseNumAndName, student);
            }

            // Populate already taken lower level elective courses
            ObservableList<CourseWrapper> lowerElectiveCourses = addl_electives_table.getItems();
            for(int i = 0; i < student.matchElectiveCourses(degreeTrack,"past5").size(); i++){
                CourseWrapper cw = new CourseWrapper(student.matchElectiveCourses(degreeTrack,"past5").get(i));
                lowerElectiveCourses.add(cw);
                courseNumAndName = student.matchElectiveCourses(degreeTrack,"past5").get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchElectiveCourses(degreeTrack,"past5").get(i).getCourseNumber()).getCourseName();
                addl_electives_add_dropdown.getItems().remove(courseNumAndName);
                cw.removeCourse(addl_electives_table, cw, addl_electives_add_dropdown, courseNumAndName, student);
            }

        }
    }

    // Handles add button
    @FXML
    public void addAdmission(ActionEvent event){
        CourseList courseList = new CourseList("resources/CourseList.json");
        String courseNum, courseTitle;

        // course drop down has course number and course name delimited by dash
        courseNum = admission_prereq_add_dropdown.getValue().split(" - ")[0];
        courseTitle = admission_prereq_add_dropdown.getValue().split(" - ")[1];
        
        // Grab semester and transfer and grade
        String semester = admission_add_semester.getText().toString();
        boolean transfer = admission_add_waiver.isSelected();
        String grade = admission_add_grade.getValue();

        if(grade == null){
            grade = "";
        }
        if(semester == null){
            semester = "";
        }
        // create an instance of Course to add to the table
        // Constructor: Course(String courseNum, String semester, String grade, String courseTitle, boolean transfer) 
        CourseWrapper course = new CourseWrapper(new Course(courseNum, semester, grade, courseTitle, transfer, 0, 'A'));

        // create an observable list for the table
        ObservableList<CourseWrapper> tableList = admission_prereq_table.getItems();

        // populate the table & remove the selected item from dropdown
        admission_prereq_add_dropdown.getItems().remove(courseNum + " - " + courseTitle);

        // Add course to the table
        tableList.add(course);
        student.addCourse(course.getCourse());
        admission_prereq_table.setItems(tableList);

        course.removeCourse(admission_prereq_table, course, admission_prereq_add_dropdown, courseNum + " - " + courseTitle, student);

        // Clear all fields for this section
        clearFields(admission_prereq_add_dropdown, admission_add_all, admission_add_semester, admission_add_waiver, admission_add_grade);

    }


    // Handles add button
    @FXML
    public void addReqCore(ActionEvent event){
        // Adding course not included in dropdown, otherwise use dropdown
        CourseList courseList = new CourseList("resources/CourseList.json");
        String courseNum, courseTitle;

        // course drop down has course number and course name delimited by dash
        courseNum = req_core_add_dropdown.getValue().split(" - ")[0];
        courseTitle = req_core_add_dropdown.getValue().split(" - ")[1];
        
        // Grab semester and transfer
        String semester = req_core_add_semester.getText().toString();
        boolean transfer = req_core_add_transfer.isSelected();
        String grade = req_core_add_grade.getValue();

        if(grade == null){
            grade = "";
        }
        if(semester == null){
            semester = "";
        }
        // create an instance of Course to add to the table
        // Constructor: Course(String courseNum, String semester, String grade, String courseTitle, boolean transfer) 
        CourseWrapper course = new CourseWrapper(new Course(courseNum, semester, grade, courseTitle, transfer));

        // create an observable list for the table
        ObservableList<CourseWrapper> tableList = req_core_table.getItems();

        // populate the table & remove the selected item from dropdown
        req_core_add_dropdown.getItems().remove(courseNum + " - " + courseTitle);

        // Add course to the table
        tableList.add(course);
        student.addCourse(course.getCourse());
        req_core_table.setItems(tableList);

        course.removeCourse(req_core_table, course, req_core_add_dropdown, courseNum + " - " + courseTitle, student);

        // Clear all fields for this section
        clearFields(req_core_add_dropdown, req_core_add_all, req_core_add_semester, req_core_add_transfer, req_core_add_grade);

    }


    
    // Handles add button
    @FXML
    public void addOptionalCore(ActionEvent event){
        // Adding course not included in dropdown, otherwise use dropdown
        CourseList courseList = new CourseList("resources/CourseList.json");
        String courseNum, courseTitle;

        // course drop down has course number and course name delimited by dash
        courseNum = core_options_add_dropdown.getValue().split(" - ")[0];
        courseTitle = core_options_add_dropdown.getValue().split(" - ")[1];
        
        // Grab semester and transfer
        String semester = core_options_add_semester.getText().toString();
        boolean transfer = core_options_add_transfer.isSelected();
        String grade = core_options_add_grade.getValue();

        if(grade == null){
            grade = "";
        }
        if(semester == null){
            semester = "";
        }

        // create an instance of Course to add to the table
        // Constructor: Course(String courseNum, String semester, String grade, String courseTitle, boolean transfer) 
        CourseWrapper course = new CourseWrapper(new Course(courseNum, semester, grade, courseTitle, transfer));

        // create an observable list for the table
        ObservableList<CourseWrapper> tableList = core_options_table.getItems();

        // populate the table & remove the selected item from dropdown
        core_options_add_dropdown.getItems().remove(courseNum + " - " + courseTitle);

        // Add course to the table
        tableList.add(course);
        student.addCourse(course.getCourse());
        core_options_table.setItems(tableList);

        course.removeCourse(core_options_table, course, core_options_add_dropdown, courseNum + " - " + courseTitle, student);

        // Clear all fields for this section
        clearFields(core_options_add_dropdown, core_options_add_all, core_options_add_semester, core_options_add_transfer, core_options_add_grade);

    }

    // Handles add button
    @FXML
    public void addElective(ActionEvent event){
        // Adding course not included in dropdown, otherwise use dropdown
        CourseList courseList = new CourseList("resources/CourseList.json");
        String courseNum, courseTitle;

        // course drop down has course number and course name delimited by dash
        courseNum = electives_add_dropdown.getValue().split(" - ")[0];
        courseTitle = electives_add_dropdown.getValue().split(" - ")[1];
        
        
        // Grab semester and transfer
        String semester = electives_add_semester.getText().toString();
        boolean transfer = electives_add_transfer.isSelected();
        String grade = electives_add_grade.getValue();

        if(grade == null){
            grade = "";
        }
        if(semester == null){
            semester = "";
        }

        // create an instance of Course to add to the table
        // Constructor: Course(String courseNum, String semester, String grade, String courseTitle, boolean transfer) 
        CourseWrapper course = new CourseWrapper(new Course(courseNum, semester, grade, courseTitle, transfer));

        // create an observable list for the table
        ObservableList<CourseWrapper> tableList = electives_table.getItems();

        // populate the table & remove the selected item from dropdown
        electives_add_dropdown.getItems().remove(courseNum + " - " + courseTitle);

        // Add course to the table
        tableList.add(course);
        student.addCourse(course.getCourse());
        electives_table.setItems(tableList);

        course.removeCourse(electives_table, course, electives_add_dropdown, courseNum + " - " + courseTitle, student);

        // Clear all fields for this section
        clearFields(electives_add_dropdown, electives_add_all, electives_add_semester, electives_add_transfer, electives_add_grade);

    }

    // Handles add button
    @FXML
    public void addAddlElective(ActionEvent event){
        // Adding course not included in dropdown, otherwise use dropdown
        CourseList courseList = new CourseList("resources/CourseList.json");
        String courseNum, courseTitle;


        // course drop down has course number and course name delimited by dash
        courseNum = addl_electives_add_dropdown.getValue().split(" - ")[0];
        courseTitle = addl_electives_add_dropdown.getValue().split(" - ")[1];
        
        
        // Grab semester and transfer
        String semester = addl_electives_add_semester.getText().toString();
        boolean transfer = addl_electives_add_transfer.isSelected();
        String grade = addl_electives_add_grade.getValue();

        if(grade == null){
            grade = "";
        }
        if(semester == null){
            semester = "";
        }
        
        // create an instance of Course to add to the table
        // Constructor: Course(String courseNum, String semester, String grade, String courseTitle, boolean transfer) 
        CourseWrapper course = new CourseWrapper(new Course(courseNum, semester, grade, courseTitle, transfer));

        // create an observable list for the table
        ObservableList<CourseWrapper> tableList = addl_electives_table.getItems();

        // populate the table & remove the selected item from dropdown
        addl_electives_add_dropdown.getItems().remove(courseNum + " - " + courseTitle);

        // Add course to the table
        tableList.add(course);
        student.addCourse(course.getCourse());
        addl_electives_table.setItems(tableList);

        course.removeCourse(addl_electives_table, course, addl_electives_add_dropdown, courseNum + " - " + courseTitle, student);

        // Clear all fields for this section
        clearFields(addl_electives_add_dropdown, addl_electives_add_all, addl_electives_add_semester, addl_electives_add_transfer, addl_electives_add_grade);

    }


    private void clearFields(ComboBox<String> dropdown, CheckBox getAllCourses, TextField semester, CheckBox transfer, ComboBox<String> grade) {        
        dropdown.getSelectionModel().clearSelection();
        getAllCourses.setSelected(false);
        getAllCourses.fireEvent(new ActionEvent());
        semester.clear();
        transfer.setSelected(false);
        grade.getSelectionModel().clearSelection();
    }

    public void clearAllFields()
    {
        admission_prereq_table.getItems().clear();
        req_core_table.getItems().clear();
        core_options_table.getItems().clear();
        electives_table.getItems().clear();
        addl_electives_table.getItems().clear();
    }

    @FXML
    public void getAllCoursesAdmission(ActionEvent event){
        admission_prereq_add_dropdown.getSelectionModel().clearSelection();
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        CourseList courseList = new CourseList("resources/CourseList.json");
        DegreePlanner degreePlan = new DegreePlanner(student, courseList, degreeList);
        
        boolean getAll = admission_add_all.isSelected();
        ObservableList<String> admissionsList = FXCollections.observableArrayList();

        if(getAll){
            for (JSONCourse course : degreePlan.getAllCourses()) {
                admissionsList.add(course.getCourseNumber() + " - " + course.getCourseName());
            }
            // System.out.println()
        }
        else{
            for (JSONCourse course : degreePlan.getPossibleCourses('A')) {
                admissionsList.add(course.getCourseNumber() + " - " + course.getCourseName());
            }
        }

        Collections.sort(admissionsList);
        admission_prereq_add_dropdown.setItems(admissionsList);

    }

    @FXML
    public void getAllCoursesReqCore(ActionEvent event){
        req_core_add_dropdown.getSelectionModel().clearSelection();
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        CourseList courseList = new CourseList("resources/CourseList.json");
        DegreePlanner degreePlan = new DegreePlanner(student, courseList, degreeList);
        
        boolean getAll = req_core_add_all.isSelected();
        ObservableList<String> reqCoreList = FXCollections.observableArrayList();

        if(getAll){
            for (JSONCourse course : degreePlan.getAllCourses()) {
                reqCoreList.add(course.getCourseNumber() + " - " + course.getCourseName());
            }
            Collections.sort(reqCoreList);
            req_core_add_dropdown.setItems(reqCoreList);
            // System.out.println()
        }
        else{
            String degreeTrackName = degree_plan_dropdown.getValue();

            if (degreeTrackName != null && student != null) {
                JSONDegree degreeTrack = degreeList.GetDegreeFromList(degreeTrackName);

                for (String course : degreeTrack.getCoreClassListRequirement()) {
                    reqCoreList.add(course + " - " + courseList.GetCourseFromList(course).getCourseName());
                }

                Collections.sort(reqCoreList);
                req_core_add_dropdown.setItems(reqCoreList);

                String courseNumAndName = "";
                // Remove already taken courses
                for(int i = 0; i < student.matchCoreCourses(degreeTrack).size(); i++){
                    courseNumAndName = student.matchCoreCourses(degreeTrack).get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchCoreCourses(degreeTrack).get(i).getCourseNumber()).getCourseName();
                    req_core_add_dropdown.getItems().remove(courseNumAndName);
                }
                Collections.sort(reqCoreList);
                req_core_add_dropdown.setItems(reqCoreList);
            }
        }


    }

    @FXML
    public void getAllCoursesOptional(ActionEvent event){
        core_options_add_dropdown.getSelectionModel().clearSelection();
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        CourseList courseList = new CourseList("resources/CourseList.json");
        DegreePlanner degreePlan = new DegreePlanner(student, courseList, degreeList);
        
        boolean getAll = core_options_add_all.isSelected();
        ObservableList<String> optionsList = FXCollections.observableArrayList();

        if(getAll){
            for (JSONCourse course : degreePlan.getAllCourses()) {
                optionsList.add(course.getCourseNumber() + " - " + course.getCourseName());
            }
            Collections.sort(optionsList);
            core_options_add_dropdown.setItems(optionsList);
            // System.out.println()
        }
        else{
            String degreeTrackName = degree_plan_dropdown.getValue();
            if (degreeTrackName != null && student != null) {
                JSONDegree degreeTrack = degreeList.GetDegreeFromList(degreeTrackName);

                for (String course : degreeTrack.getOptionsCoreClassListRequirement()) {
                    optionsList.add(course + " - " + courseList.GetCourseFromList(course).getCourseName());
                }

                Collections.sort(optionsList);
                core_options_add_dropdown.setItems(optionsList);


                String courseNumAndName = "";

                for(int i = 0; i < student.matchCoreOptionCourses(degreeTrack).size(); i++){
                    courseNumAndName = student.matchCoreOptionCourses(degreeTrack).get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchCoreOptionCourses(degreeTrack).get(i).getCourseNumber()).getCourseName();
                    core_options_add_dropdown.getItems().remove(courseNumAndName);
                }                  
                Collections.sort(optionsList);
                core_options_add_dropdown.setItems(optionsList);
            }
        }


    }

    @FXML
    public void getAllElectives(ActionEvent event){
        electives_add_dropdown.getSelectionModel().clearSelection();
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        CourseList courseList = new CourseList("resources/CourseList.json");
        DegreePlanner degreePlan = new DegreePlanner(student, courseList, degreeList);
        
        boolean getAll = electives_add_all.isSelected();
        ObservableList<String> electivesList = FXCollections.observableArrayList();

        if(getAll){
            for (JSONCourse course : degreePlan.getAllCourses()) {
                electivesList.add(course.getCourseNumber() + " - " + course.getCourseName());
            }
            Collections.sort(electivesList);
            electives_add_dropdown.setItems(electivesList);
            // System.out.println()
        }
        else{
            String degreeTrackName = degree_plan_dropdown.getValue();
            if (degreeTrackName != null && student != null) {
                JSONDegree degreeTrack = degreeList.GetDegreeFromList(degreeTrackName);

                for (String course : degreeTrack.getElectiveClassListRequirement()) {
                    electivesList.add(course + " - " + courseList.GetCourseFromList(course).getCourseName());
                }

                Collections.sort(electivesList);
                electives_add_dropdown.setItems(electivesList);


                String courseNumAndName = "";
                for(int i = 0; i < student.matchElectiveCourses(degreeTrack,"top5").size(); i++){
                    courseNumAndName = student.matchElectiveCourses(degreeTrack,"top5").get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchElectiveCourses(degreeTrack,"top5").get(i).getCourseNumber()).getCourseName();
                    electives_add_dropdown.getItems().remove(courseNumAndName);
                }                  

                Collections.sort(electivesList);
                electives_add_dropdown.setItems(electivesList);
            }
        }

        
    }

    @FXML
    public void getAllLowerElectives(ActionEvent event){
        addl_electives_add_dropdown.getSelectionModel().clearSelection();
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        CourseList courseList = new CourseList("resources/CourseList.json");
        DegreePlanner degreePlan = new DegreePlanner(student, courseList, degreeList);
        
        boolean getAll = addl_electives_add_all.isSelected();
        ObservableList<String> addlList = FXCollections.observableArrayList();

        if(getAll){
            for (JSONCourse course : degreePlan.getAllCourses()) {
                addlList.add(course.getCourseNumber() + " - " + course.getCourseName());
            }
            Collections.sort(addlList);
            addl_electives_add_dropdown.setItems(addlList);
            // System.out.println()
        }
        else{
            String degreeTrackName = degree_plan_dropdown.getValue();
            if (degreeTrackName != null && student != null) {
                JSONDegree degreeTrack = degreeList.GetDegreeFromList(degreeTrackName);

                for (String course : student.getAllElectivesNotTaken(courseList, degreeTrack)) {
                    addlList.add(course + " - " + courseList.GetCourseFromList(course).getCourseName());
                }
                Collections.sort(addlList);
                addl_electives_add_dropdown.setItems(addlList);

                String courseNumAndName = "";
                for(int i = 0; i < student.matchElectiveCourses(degreeTrack,"past5").size(); i++){
                    courseNumAndName = student.matchElectiveCourses(degreeTrack,"past5").get(i).getCourseNumber() + " - " + courseList.GetCourseFromList(student.matchElectiveCourses(degreeTrack,"past5").get(i).getCourseNumber()).getCourseName();
                    addl_electives_add_dropdown.getItems().remove(courseNumAndName);
                }     

                Collections.sort(addlList);
                addl_electives_add_dropdown.setItems(addlList);
            }
        }

    }

    @FXML
    private void importTranscript(ActionEvent event){
        clearAllFields();
        degree_plan_dropdown.setValue(null);

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
            fast_track_checkbox.setSelected(student.getFastTrack());
            thesis_checkbox.setSelected(student.getThesis());

		}

        
    }

    @FXML
    private void exportStudentAndPDF(ActionEvent event) throws IOException{
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        CourseList courseList = new CourseList("resources/CourseList.json");
        if (student == null)
        {
            errorAlert("Error: Submitted without input ");
        }
        else
        {
            student.setName(student_name.getText());
            student.setID(student_id.getText());
            student.setSemesterAdmitted(semester_admitted.getText());
            student.setAnticipatedGraduation(anticipated_graduation.getText());
            student.setFastTrack(fast_track_checkbox.isSelected());
            student.setThesis(thesis_checkbox.isSelected());
            FileActions export = new FileActions(courseList, degreeList);

            try{
                export.exportStudent(student);
                export.exportDegreePlanPDF(student);

                // Go back to main menu
                Stage stage;
                Parent root;
                stage = (Stage) return_to_menu_btn.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/fxml/menuScene.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            catch(Exception e) {
                errorAlert("Error: Canceled while saving... Try Again");
            }
        }
    }

    /*
     * ERROR ALERT
     */
    public void errorAlert(String error) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText(error);
        alert.showAndWait();

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
            if(degreeTrack.getActiveStatus()){
                degreeTrackList.add(degreeTrack.getDegreeName());
            }
        }
        degree_plan_dropdown.setItems(degreeTrackList);

        // Grades dropdown
        ObservableList<String> gradeList = FXCollections.observableArrayList("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F", "P");
        admission_add_grade.setItems(gradeList);
        req_core_add_grade.setItems(gradeList);
        core_options_add_grade.setItems(gradeList);
        electives_add_grade.setItems(gradeList);
        addl_electives_add_grade.setItems(gradeList);

        // Set admission table
        admission_prereq_course_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("courseNumber"));
        admission_prereq_name_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("courseTitle"));
        admission_prereq_semester_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("semester"));
        admission_prereq_waiver_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, Boolean>("transfer"));
        admission_prereq_grade_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("grade"));
        admission_prereq_remove_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("button"));

        // Set core course table
        req_core_course_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("courseNumber"));
        req_core_course_name_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("courseTitle"));
        req_core_semester_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("semester"));
        req_core_transfer_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, Boolean>("transfer"));
        req_core_grade_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("grade"));
        req_core_remove_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("button"));

        // Set optional core course table
        core_options_course_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("courseNumber"));
        core_options_name_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("courseTitle"));
        core_options_semester_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("semester"));
        core_options_transfer_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, Boolean>("transfer"));
        core_options_grade_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("grade"));
        core_options_remove_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("button"));

        // Set elective course table
        electives_course_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("courseNumber"));
        electives_name_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("courseTitle"));
        electives_semester_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("semester"));
        electives_transfer_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, Boolean>("transfer"));
        electives_grade_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("grade"));
        electives_remove_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("button"));

        // Set lower level elective course table
        addl_electives_course_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("courseNumber"));
        addl_electives_name_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("courseTitle"));
        addl_electives_semester_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("semester"));
        addl_electives_transfer_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, Boolean>("transfer"));
        addl_electives_grade_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("grade"));
        addl_electives_remove_col.setCellValueFactory(new PropertyValueFactory<CourseWrapper, String>("button"));

    }
}
