package com.group12.degreeaudit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
    public TableView<Course> admission_prereq_table, req_core_table, core_options_table, electives_table, addl_electives_table;

    @FXML
    public TableColumn<Course, String> admission_prereq_course_col, admission_prereq_semester_col, admission_prereq_grade_col, admission_prereq_remove_col;
    public TableColumn<Course, Boolean> admission_prereq_transfer_col;

    @FXML
    public TableColumn<Course, String> req_core_course_col, req_core_semester_col, req_core_grade_col, req_core_remove_col;
    public TableColumn<Course, Boolean> req_core_transfer_col;

    @FXML
    public TableColumn<Course, String> core_options_course_col, core_options_semester_col, core_options_grade_col, core_options_remove_col;
    public TableColumn<Course, Boolean> core_options_transfer_col;

    @FXML
    public TableColumn<Course, String> electives_course_col, electives_semester_col,  electives_grade_col, electives_remove_col;
    public TableColumn<Course, Boolean> electives_transfer_col;

    @FXML
    public TableColumn<Course, String> addl_electives_course_col, addl_electives_semester_col, addl_electives_grade_col, addl_electives_remove_col;
    public TableColumn<Course, Boolean> addl_electives_transfer_col;

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
            

            // ADMISSION PART MISSING
            for (String course : degreeTrack.getCoreClassListRequirement()) {
                reqCoreList.add(course);
            }
            for (String course : degreeTrack.getOptionsCoreClassListRequirement()) {
                optionalCoreList.add(course);
            }
            for (String course : degreeTrack.getElectiveClassListRequirement()) {
                electiveList.add(course);
            }
            for (String course : degreeTrack.getElectivesAcceptedLowerCourses()) {
                addlElectiveList.add(course);
            }
            Collections.sort(reqCoreList);
            Collections.sort(optionalCoreList);
            Collections.sort(electiveList);
            Collections.sort(addlElectiveList);
            req_core_add_dropdown.setItems(reqCoreList);
            core_options_add_dropdown.setItems(optionalCoreList);
            electives_add_dropdown.setItems(electiveList);
            addl_electives_add_dropdown.setItems(addlElectiveList);

           
            // Populate already taken core courses
            ObservableList<Course> coreCourses = req_core_table.getItems();
            for(int i = 0; i < student.matchCoreCourses(degreeTrack).size(); i++){
                coreCourses.add(student.matchCoreCourses(degreeTrack).get(i));
                req_core_add_dropdown.getItems().remove(student.matchCoreCourses(degreeTrack).get(i).getCourseNumber());
                student.matchCoreCourses(degreeTrack).get(i).removeCourse(req_core_table, student.matchCoreCourses(degreeTrack).get(i), req_core_add_dropdown, student.matchCoreCourses(degreeTrack).get(i).getCourseNumber());
            }

            // Populate already taken optional courses
            ObservableList<Course> optionalCoreCourses = core_options_table.getItems();
            for(int i = 0; i < student.matchCoreOptionCourses(degreeTrack).size(); i++){
                optionalCoreCourses.add(student.matchCoreOptionCourses(degreeTrack).get(i));
                core_options_add_dropdown.getItems().remove(student.matchCoreOptionCourses(degreeTrack).get(i).getCourseNumber());
                student.matchCoreOptionCourses(degreeTrack).get(i).removeCourse(core_options_table, student.matchCoreOptionCourses(degreeTrack).get(i), core_options_add_dropdown, student.matchCoreOptionCourses(degreeTrack).get(i).getCourseNumber());
            }
            
            // Populate already taken elective courses
            ObservableList<Course> electiveCourses = electives_table.getItems();
            System.out.println(student.matchElectiveCourses(degreeTrack));
            for(int i = 0; i < student.matchElectiveCourses(degreeTrack).size(); i++){
                electiveCourses.add(student.matchElectiveCourses(degreeTrack).get(i));
                electives_add_dropdown.getItems().remove(student.matchElectiveCourses(degreeTrack).get(i).getCourseNumber());
                student.matchElectiveCourses(degreeTrack).get(i).removeCourse(electives_table, student.matchElectiveCourses(degreeTrack).get(i), electives_add_dropdown, student.matchElectiveCourses(degreeTrack).get(i).getCourseNumber());
            }

            // Populate already taken lower level elective courses
            ObservableList<Course> lowerElectiveCourses = addl_electives_table.getItems();
            System.out.println(student.matchAddlElectiveCourses(degreeTrack));
            for(int i = 0; i < student.matchAddlElectiveCourses(degreeTrack).size(); i++){
                lowerElectiveCourses.add(student.matchAddlElectiveCourses(degreeTrack).get(i));
                addl_electives_add_dropdown.getItems().remove(student.matchAddlElectiveCourses(degreeTrack).get(i).getCourseNumber());
                student.matchAddlElectiveCourses(degreeTrack).get(i).removeCourse(addl_electives_table, student.matchAddlElectiveCourses(degreeTrack).get(i), addl_electives_add_dropdown, student.matchAddlElectiveCourses(degreeTrack).get(i).getCourseNumber());
            }

        }
    }

    // Handles return button
    @FXML
    public void addReqCore(ActionEvent event){
        System.out.println("adding smadding");
        // Grab the selected class from dropdown
        String courseNum = req_core_add_dropdown.getValue();

        // create an instance of Course to add to the table
        // public Course(String courseNumber, String semester, String grade, String courseTitle, boolean transfer) {
        // Course course = new Course(courseNum);
    }



    @FXML
    private void importTranscript(ActionEvent event){
        

        FileChooser fc = new FileChooser();
		// if we want to open fixed location
		//fc.setInitialDirectory(new File("D:\\\\Books"));
		

		// Only allowing .txt files
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = fc.showOpenDialog(null);
        
        //System.out.println("BEFORE: " + coursesTaken);


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
            // coursesTaken = student.getCoursesTaken();
            // System.out.println(student.getCoursesTaken());
            // System.out.println("get program: " + student.getProgram());

            // System.out.println("AFTER: " + coursesTaken.get(0).getClassType());

            // for (Course course : coursesTaken) {
            //     System.out.println(course.getClassType());
            // }
            
            // String degreeTrackName = degree_plan_dropdown.getValue();
            // System.out.println(degreeTrackName);
            // if (degreeTrackName != null) {
            //     // Get Degree track name
            //     DegreeList degreeList = new DegreeList("resources/DegreeList.json");
            //     JSONDegree degreeTrack = degreeList.GetDegreeFromList(degreeTrackName);
            //     System.out.println(student.matchCoreCourses(degreeTrack));
            // }

  

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

        // Set core course table
        req_core_course_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseNumber"));
        req_core_semester_col.setCellValueFactory(new PropertyValueFactory<Course, String>("semester"));
        req_core_transfer_col.setCellValueFactory(new PropertyValueFactory<Course, Boolean>("transfer"));
        req_core_grade_col.setCellValueFactory(new PropertyValueFactory<Course, String>("grade"));
        req_core_remove_col.setCellValueFactory(new PropertyValueFactory<Course, String>("button"));

        // Set optional core course table
        core_options_course_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseNumber"));
        core_options_semester_col.setCellValueFactory(new PropertyValueFactory<Course, String>("semester"));
        core_options_transfer_col.setCellValueFactory(new PropertyValueFactory<Course, Boolean>("transfer"));
        core_options_grade_col.setCellValueFactory(new PropertyValueFactory<Course, String>("grade"));
        core_options_remove_col.setCellValueFactory(new PropertyValueFactory<Course, String>("button"));

        // Set elective course table
        electives_course_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseNumber"));
        electives_semester_col.setCellValueFactory(new PropertyValueFactory<Course, String>("semester"));
        electives_transfer_col.setCellValueFactory(new PropertyValueFactory<Course, Boolean>("transfer"));
        electives_grade_col.setCellValueFactory(new PropertyValueFactory<Course, String>("grade"));
        electives_remove_col.setCellValueFactory(new PropertyValueFactory<Course, String>("button"));

        // Set lower level elective course table
        addl_electives_course_col.setCellValueFactory(new PropertyValueFactory<Course, String>("courseNumber"));
        addl_electives_semester_col.setCellValueFactory(new PropertyValueFactory<Course, String>("semester"));
        addl_electives_transfer_col.setCellValueFactory(new PropertyValueFactory<Course, Boolean>("transfer"));
        addl_electives_grade_col.setCellValueFactory(new PropertyValueFactory<Course, String>("grade"));
        addl_electives_remove_col.setCellValueFactory(new PropertyValueFactory<Course, String>("button"));
    }
}
