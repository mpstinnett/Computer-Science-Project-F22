package com.group12.degreeaudit;

// Imports
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class adminSceneController implements Initializable {

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
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Export Settings button
    @FXML
    private Button export_settings_btn;

    // Handles export button
    @FXML
    public void exportSettings(ActionEvent event) {
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
        String courseNum = addc_prerequisites_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the prerequisite table
        JSONCourseWrapper course = new JSONCourseWrapper(courseNum);

        // create an observable list for the prerequisite table
        ObservableList<JSONCourseWrapper> courseWrapper = addc_prerequisites_table.getItems();

        // populate the prerequisite table & remove the selected item from dropdown
        addc_prerequisites_dropdown.getItems().remove(courseNum);
        courseWrapper.add(course);
        addc_prerequisites_table.setItems(courseWrapper);
        course.removeTableCourse(addc_prerequisites_table, course, addc_prerequisites_dropdown,
                courseNum);

        // clear selection from dropdown
        addc_prerequisites_dropdown.getSelectionModel().clearSelection();

    }

    // "TEST" button is pressed
    @FXML
    public void testMeBestie(ActionEvent event) {

    }

    /*
     * SUCCESS ALERT
     */
    public void tattooOnMyChestie() {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Successfully Submitted!");
        alert.showAndWait();

    }

    /*
     * ERROR ALERT
     */
    public void cowboyFromTheWestie(String error) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText(error);
        alert.showAndWait();

    }

    // "ADD" button
    @FXML
    private Button addc_add_btn;

    // "ADD" button is pressed for adding a course
    @FXML
    public void addCourseToCourseList(ActionEvent event) {
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

        // Adding course to course list
        CourseList courseList = new CourseList("resources/CourseList.json");
        if (courseList.AddCourseToList(courseNumber, courseName, courseDescription, prerequisites, classType,
                activeStatus)) {
            // display success
            tattooOnMyChestie();
        } else {
            cowboyFromTheWestie("Error:  Course Number is already found in the list.");
        }

        // Refresh tab
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

    // Prefill the tab when a class is chosen from the dropdown
    @FXML
    void updatecGetClassInfo(ActionEvent event) {

        // Chosen class from dropdown
        String courseNumber = updatec_class_dropdown.getValue();

        // Only prefill when the course chosen is not null
        if (courseNumber != null) {

            // Get all the courses possible
            CourseList courseList = new CourseList("resources/CourseList.json");
            JSONCourse course = courseList.GetCourseFromList(courseNumber);
            List<JSONCourse> allCourses = courseList.GetCourseList();

            // Populate prerequisite dropdown with all courses possible
            ObservableList<String> updatecPreqDropList = FXCollections.observableArrayList();

            for (JSONCourse preReqCourse : allCourses) {
                updatecPreqDropList.add(preReqCourse.getCourseNumber());
            }

            // Sort the prerequisite list
            Collections.sort(updatecPreqDropList);

            // Populate the prerequisites dropdown
            updatec_prerequisites_dropdown.setItems(updatecPreqDropList);

            // Remove chosen class from prerequisite dropdown
            updatec_prerequisites_dropdown.getItems().remove(courseNumber);

            // Populate details of the chosen class
            updatec_class_number.setText(course.getCourseNumber());
            updatec_class_name.setText(course.getCourseName());
            updatec_class_description.setText(course.getCourseDescription());
            updatec_active_status.setSelected(course.getActiveStatus());

            switch (course.getClassType()) {
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

            // Clear the prerequisites table whenever a new class is chosen from the class
            // dropdown
            updatec_prerequisites_table.getItems().clear();

            // Populate the table with the course prerequisites
            String prerequisites[] = course.getCoursePreReqs();

            ObservableList<JSONCourseWrapper> updatec_JSONCourseWrapper = updatec_prerequisites_table.getItems();

            // Make a JSONCourseWrapper for every prerequisite in the course
            for (int i = 0; i < prerequisites.length; i++) {
                JSONCourseWrapper updatec_prereq_course = new JSONCourseWrapper(prerequisites[i]);
                updatec_JSONCourseWrapper.add(updatec_prereq_course);

                // Populate the prerequisite table & remove the selected item from dropdown
                updatec_prerequisites_table.setItems(updatec_JSONCourseWrapper);
                updatec_prerequisites_dropdown.getItems().remove(prerequisites[i]);
                updatec_prereq_course.removeTableCourse(updatec_prerequisites_table, updatec_prereq_course,
                        updatec_prerequisites_dropdown, prerequisites[i]);

            }

        }

    }

    // "ADD" button is pressed for prerequisite table
    @FXML
    void updatecPrerequisites(ActionEvent event) {
        // Grab the selected class from prerequisite dropdown
        String courseNum = updatec_prerequisites_dropdown.getValue();

        // Create an instance of JSONCourseWrapper to add to the prerequisite table
        JSONCourseWrapper course = new JSONCourseWrapper(courseNum);

        // Remove the selected class from the dropdown
        updatec_prerequisites_dropdown.getItems().remove(courseNum);

        // Create an observable list for the prerequisite table
        ObservableList<JSONCourseWrapper> courseWrapper = updatec_prerequisites_table.getItems();

        // Populate the prerequisite table & remove the selected item from dropdown
        courseWrapper.add(course);
        updatec_prerequisites_table.setItems(courseWrapper);
        course.removeTableCourse(updatec_prerequisites_table, course,
                updatec_prerequisites_dropdown, courseNum);

        // Clear selection from dropdown
        updatec_prerequisites_dropdown.getSelectionModel().clearSelection();
    }

    @FXML
    private Button updatec_update_btn;

    // "UPDATE" button is pressed for updating a course
    @FXML
    public void updateCourseInCourseList(ActionEvent event) {
        // Get filled in fields
        CourseList courseList = new CourseList("resources/CourseList.json");

        // To hold new course numbers
        String oldCourseNum = updatec_class_dropdown.getValue();
        String newCourseNum = updatec_class_number.getText().toString();

        // Grab all fields and update them
        JSONCourse courseToUpdate = new JSONCourse();
        courseToUpdate.setCourseName(updatec_class_name.getText().toString());
        courseToUpdate.setCourseNumber(oldCourseNum);
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

        // If course number was renamed, update it
        if (courseList.UpdateCourseInList(courseToUpdate)) {
            if (!oldCourseNum.equals(newCourseNum)) {
                courseList.UpdateCourseNumber(oldCourseNum, newCourseNum);
            }
            tattooOnMyChestie();
        } else {
            cowboyFromTheWestie("Error: Degree List not Updated.");
        }

        // Refresh Tab
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
    public void removeCourseInCourseList(ActionEvent event) {
        CourseList courseList = new CourseList("resources/CourseList.json");
        courseList.RemoveCourse(removec_dropdown.getValue());

        // Refresh Tab
        initialize(null, null);
        tattooOnMyChestie();
    }

    /*
     * ADD DEGREE TRACK TAB
     */
    @FXML
    public TextField addt_track_name, addt_num_core_courses, addt_core_gpa_requirements,
            addt_num_electives, addt_overall_gpa, addt_elective_gpa_requirements;

    @FXML
    private CheckBox addt_active_status, addt_core_replace_2nd, addt_core_allow_7th, addt_elective_replace_2nd,
            addt_elective_allow_5k;

    @FXML
    private ComboBox<String> addt_5k_dropdown, addt_core_dropdown, addt_optional_core_dropdown, addt_elective_dropdown;

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
        // Grab the selected class from core course dropdown
        String courseNum = addt_core_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the core course table
        JSONCourseWrapper course = new JSONCourseWrapper(courseNum);

        // create an observable list for the core course table
        ObservableList<JSONCourseWrapper> courseWrapper = addt_core_table.getItems();

        // populate the core course table & remove the selected item from dropdown
        addt_core_dropdown.getItems().remove(courseNum);
        courseWrapper.add(course);
        addt_core_table.setItems(courseWrapper);
        course.removeTableCourse(addt_core_table, course, addt_core_dropdown, courseNum);

        // clear selection from dropdown
        addt_core_dropdown.getSelectionModel().clearSelection();
    }

    // Add to Optional Core Courses Table
    @FXML
    public TableView<JSONCourseWrapper> addt_optional_core_table;

    @FXML
    public TableColumn<JSONCourseWrapper, String> addt_optional_core_course_num_col;

    @FXML
    public TableColumn<JSONCourseWrapper, String> addt_optional_core_remove_course_col;

    // "ADD" button is pressed for adding an optional core course
    @FXML
    void addtAddOptionalCoreCourse(ActionEvent event) {
        // Grab the selected class from optional core dropdown
        String courseNum = addt_optional_core_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the optional core table
        JSONCourseWrapper course = new JSONCourseWrapper(courseNum);

        // create an observable list for the optional core table
        ObservableList<JSONCourseWrapper> courseWrapper = addt_optional_core_table.getItems();

        // populate the optional core table & remove the selected item from dropdown
        addt_optional_core_dropdown.getItems().remove(courseNum);
        courseWrapper.add(course);
        addt_optional_core_table.setItems(courseWrapper);
        course.removeTableCourse(addt_optional_core_table, course, addt_optional_core_dropdown, courseNum);

        // clear selection from dropdown
        addt_optional_core_dropdown.getSelectionModel().clearSelection();
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
        // Grab the selected class from elective dropdown
        String courseNum = addt_elective_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the elective table
        JSONCourseWrapper course = new JSONCourseWrapper(courseNum);

        // create an observable list for the elective table
        ObservableList<JSONCourseWrapper> courseWrapper = addt_elective_table.getItems();

        // populate the prerequisite table & remove the selected item from dropdown
        addt_elective_dropdown.getItems().remove(courseNum);
        courseWrapper.add(course);
        addt_elective_table.setItems(courseWrapper);
        course.removeTableCourse(addt_elective_table, course, addt_elective_dropdown,
                courseNum);

        // clear selection from dropdown
        addt_elective_dropdown.getSelectionModel().clearSelection();

    }

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

        // Grab the selected class from 5XXX dropdown
        String courseNum = addt_5k_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the 5XXX table
        JSONCourseWrapper course = new JSONCourseWrapper(courseNum);

        // create an observable list for the 5XXX table
        ObservableList<JSONCourseWrapper> courseWrapper = addt_5k_table.getItems();

        // populate the 5XXX table
        addt_5k_dropdown.getItems().remove(courseNum);
        courseWrapper.add(course);
        addt_5k_table.setItems(courseWrapper);
        course.removeTableCourse(addt_5k_table, course, addt_5k_dropdown, courseNum);

        // clear selection
        addt_5k_dropdown.getSelectionModel().clearSelection();
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

        String optionsCoreClassListRequirement[] = new String[addt_optional_core_table.getItems().size()];
        for (int i = 0; i < optionsCoreClassListRequirement.length; i++) {
            optionsCoreClassListRequirement[i] = addt_optional_core_table.getItems().get(i).getJsonCourse()
                    .getCourseNumber();
        }

        String electiveClassListRequirement[] = new String[addt_elective_table.getItems().size()];
        for (int i = 0; i < electiveClassListRequirement.length; i++) {
            electiveClassListRequirement[i] = addt_elective_table.getItems().get(i).getJsonCourse().getCourseNumber();
        }

        // Add to degree list
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");

        if (degreeList.AddDegreeToList(degreeName, coreRequirementAmount, coreGPARequirement, coreReplaceHighestAttempt,
                coreAllowSeventhElective, electiveRequirementAmount, electiveGPARequirement,
                electiveReplaceHighestAttempt, electiveAllowOneLowerCourse, electivesAcceptedLowerCourses,
                overallGPARequirement, coreClassListRequirement,optionsCoreClassListRequirement, electiveClassListRequirement, activeStatus)) {
            tattooOnMyChestie();
        } else {
            cowboyFromTheWestie("Error: Degree Name is already found in the list");
        }

        // Refresh Tab
        initialize(null, null);
    }

    /*
     * UPDATE DEGREE TRACK TAB
     */
    @FXML
    private ComboBox<String> updatet_dropdown;

    @FXML
    public TextField updatet_track_name, updatet_num_core_courses,
            updatet_core_gpa_requirements,
            updatet_num_electives, updatet_overall_gpa, updatet_elective_gpa_requirements;

    @FXML
    private CheckBox updatet_active_status, updatet_core_replace_2nd, updatet_core_allow_7th,
            updatet_elective_replace_2nd, updatet_elective_allow_5k;

    @FXML
    private ComboBox<String> updatet_5k_dropdown, updatet_core_dropdown, updatet_optional_core_dropdown,
            updatet_elective_dropdown;

    // Prefill the tab when a class is chosen
    @FXML
    void updatetGetClassInfo(ActionEvent event) {
        String degreeTrackName = updatet_dropdown.getValue();

        if (degreeTrackName != null) {
            // Get all the courses possible
            DegreeList degreeList = new DegreeList("resources/DegreeList.json");
            JSONDegree degreeTrack = degreeList.GetDegreeFromList(degreeTrackName);

            CourseList courseList = new CourseList("resources/CourseList.json");
            List<JSONCourse> allCourses = courseList.GetCourseList();

            // Populate table dropdowns with all courses possible
            ObservableList<String> updatet5kDropList = FXCollections.observableArrayList();
            ObservableList<String> updatetCoreDropList = FXCollections.observableArrayList();
            ObservableList<String> updatetOptionalCoreDropList = FXCollections.observableArrayList();
            ObservableList<String> updatetElectiveDropList = FXCollections.observableArrayList();

            for (JSONCourse course : allCourses) {
                updatet5kDropList.add(course.getCourseNumber());
                updatetCoreDropList.add(course.getCourseNumber());
                updatetOptionalCoreDropList.add(course.getCourseNumber());
                updatetElectiveDropList.add(course.getCourseNumber());
            }

            // Sort dropdowns
            Collections.sort(updatet5kDropList);
            Collections.sort(updatetCoreDropList);
            Collections.sort(updatetOptionalCoreDropList);
            Collections.sort(updatetElectiveDropList);

            // Populate dropdowns
            updatet_5k_dropdown.setItems(updatet5kDropList);
            updatet_core_dropdown.setItems(updatetCoreDropList);
            updatet_optional_core_dropdown.setItems(updatetOptionalCoreDropList);
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
            updatet_optional_core_table.getItems().clear();
            updatet_elective_table.getItems().clear();

            // POPULATE 5XXX COURSE TABLE
            String lowerCourses[] = degreeTrack.getElectivesAcceptedLowerCourses();

            // Populate the table with the 5k courses
            ObservableList<JSONCourseWrapper> lowerCourseJSONCourseWrapper = updatet_5k_table.getItems();

            // Make a JSONCourseWrapper for every 5k course
            for (int i = 0; i < lowerCourses.length; i++) {
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
            for (int i = 0; i < coreCourses.length; i++) {
                JSONCourseWrapper coreCourse = new JSONCourseWrapper(coreCourses[i]);
                coreCourseJSONCourseWrapper.add(coreCourse);
                updatet_core_table.setItems(coreCourseJSONCourseWrapper);

                // Remove the selected class from the dropdown
                updatet_core_dropdown.getItems().remove(coreCourses[i]);

                coreCourse.removeTableCourse(updatet_core_table, coreCourse, updatet_core_dropdown, coreCourses[i]);

            }

            // POPULATE OPTIONAL COURE COURSE TABLE
            String optionalCoreCourses[] = degreeTrack.getOptionsCoreClassListRequirement();

            // Populate the table with the 5k courses
            ObservableList<JSONCourseWrapper> optionalCoreCourseJSONCourseWrapper = updatet_optional_core_table
                    .getItems();

            // Make a JSONCourseWrapper for every 5k course
            for (int i = 0; i < optionalCoreCourses.length; i++) {
                JSONCourseWrapper optionalCourse = new JSONCourseWrapper(optionalCoreCourses[i]);
                optionalCoreCourseJSONCourseWrapper.add(optionalCourse);
                updatet_optional_core_table.setItems(optionalCoreCourseJSONCourseWrapper);

                // Remove the selected class from the dropdown
                updatet_optional_core_dropdown.getItems().remove(optionalCoreCourses[i]);

                optionalCourse.removeTableCourse(updatet_optional_core_table, optionalCourse,
                        updatet_optional_core_dropdown, optionalCoreCourses[i]);

            }

            // POPULATE ELECTIVE COURSE TABLE
            String electiveCourses[] = degreeTrack.getElectiveClassListRequirement();

            // Populate the table with the 5k courses
            ObservableList<JSONCourseWrapper> electiveCourseJSONCourseWrapper = updatet_elective_table.getItems();

            // Make a JSONCourseWrapper for every 5k course
            for (int i = 0; i < electiveCourses.length; i++) {
                JSONCourseWrapper electiveCourse = new JSONCourseWrapper(electiveCourses[i]);
                electiveCourseJSONCourseWrapper.add(electiveCourse);
                updatet_elective_table.setItems(electiveCourseJSONCourseWrapper);

                // Remove the selected class from the dropdown
                updatet_elective_dropdown.getItems().remove(electiveCourses[i]);

                electiveCourse.removeTableCourse(updatet_elective_table, electiveCourse, updatet_elective_dropdown,
                        electiveCourses[i]);

            }
        }
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
        // Grab the selected class from core course dropdown
        String courseNum = updatet_core_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the core course table
        JSONCourseWrapper course = new JSONCourseWrapper(courseNum);

        // create an observable list for the core course table
        ObservableList<JSONCourseWrapper> courseWrapper = updatet_core_table.getItems();

        // populate the core course table & remove the selected item from dropdown
        updatet_core_dropdown.getItems().remove(courseNum);
        courseWrapper.add(course);
        updatet_core_table.setItems(courseWrapper);
        course.removeTableCourse(updatet_core_table, course, updatet_core_dropdown,
                courseNum);

        // clear selection
        updatet_core_dropdown.getSelectionModel().clearSelection();
    }

    // Add to Optional Core Courses Table
    @FXML
    public TableView<JSONCourseWrapper> updatet_optional_core_table;

    @FXML
    public TableColumn<JSONCourseWrapper, String> updatet_optional_core_course_num_col;

    @FXML
    public TableColumn<JSONCourseWrapper, String> updatet_optional_core_remove_course_col;

    // "ADD" button is pressed for adding a optional core course
    @FXML
    void updatetAddOptionalCoreCourse(ActionEvent event) {
        // Grab the selected class from optional core dropdown
        String courseNum = updatet_optional_core_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the optional core table
        JSONCourseWrapper course = new JSONCourseWrapper(courseNum);

        // create an observable list for the optional core table
        ObservableList<JSONCourseWrapper> courseWrapper = updatet_optional_core_table.getItems();

        // populate the optional core table & remove the selected item from dropdown
        updatet_optional_core_dropdown.getItems().remove(courseNum);
        courseWrapper.add(course);
        updatet_optional_core_table.setItems(courseWrapper);
        course.removeTableCourse(updatet_optional_core_table, course, updatet_optional_core_dropdown,
                courseNum);

        // clear selection
        updatet_optional_core_dropdown.getSelectionModel().clearSelection();
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
        // Grab the selected class from elective dropdown
        String courseNum = updatet_elective_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the elective table
        JSONCourseWrapper course = new JSONCourseWrapper(courseNum);

        // create an observable list for the elective table
        ObservableList<JSONCourseWrapper> courseWrapper = updatet_elective_table.getItems();

        // populate the elective table & remove the selected item from dropdown
        updatet_elective_dropdown.getItems().remove(courseNum);
        courseWrapper.add(course);
        updatet_elective_table.setItems(courseWrapper);
        course.removeTableCourse(updatet_elective_table, course,
                updatet_elective_dropdown, courseNum);

        // clear selection
        updatet_elective_dropdown.getSelectionModel().clearSelection();
    }

    // Add to 5XXX Courses Table
    @FXML
    public TableView<JSONCourseWrapper> updatet_5k_table;

    @FXML
    public TableColumn<JSONCourseWrapper, String> updatet_5k_course_num_col;

    @FXML
    public TableColumn<JSONCourseWrapper, String> updatet_5k_remove_course_col;

    // "ADD" button is pressed for adding a 5XXX course
    @FXML
    void updatetAdd5kCourse(ActionEvent event) {
        // Grab the selected class from 5XXX dropdown
        String courseNum = updatet_5k_dropdown.getValue();

        // create an instance of JSONCourseWrapper to add to the 5XXX table
        JSONCourseWrapper course = new JSONCourseWrapper(courseNum);

        // create an observable list for the 5XXX table
        ObservableList<JSONCourseWrapper> courseWrapper = updatet_5k_table.getItems();

        // populate the 5XXX table & remove the selected item from dropdown
        updatet_5k_dropdown.getItems().remove(courseNum);
        courseWrapper.add(course);
        updatet_5k_table.setItems(courseWrapper);
        course.removeTableCourse(updatet_5k_table, course, updatet_5k_dropdown,
                courseNum);

        // clear selection
        updatet_5k_dropdown.getSelectionModel().clearSelection();
    }

    // "SUBMIT" button is pressed for updating a degree track
    @FXML
    void updatetSubmit(ActionEvent event) {

        DegreeList degreeList = new DegreeList("resources/DegreeList.json");

        // To hold new names for degree track
        String oldDegreeName = updatet_dropdown.getValue();
        String newDegreeName = updatet_track_name.getText().toString();

        // Grab all fields and update them
        JSONDegree degreeTrackToUpdate = new JSONDegree();
        degreeTrackToUpdate.setDegreeName(oldDegreeName);
        degreeTrackToUpdate.setActiveStatus(updatet_active_status.isSelected());
        degreeTrackToUpdate.setCoreRequirementAmount(updatet_num_core_courses.getText().toString());
        degreeTrackToUpdate.setCoreGPARequirement(updatet_core_gpa_requirements.getText().toString());
        degreeTrackToUpdate.setCoreReplaceHighestAttempt(updatet_core_replace_2nd.isSelected());
        degreeTrackToUpdate.setCoreAllowSeventhElective(updatet_core_allow_7th.isSelected());
        degreeTrackToUpdate.setElectiveRequirementAmount(updatet_num_electives.getText().toString());
        degreeTrackToUpdate.setElectiveGPARequirement(updatet_elective_gpa_requirements.getText().toString());
        degreeTrackToUpdate.setElectiveReplaceHighestAttempt(updatet_elective_replace_2nd.isSelected());
        degreeTrackToUpdate.setElectiveAllowOneLowerCourse(updatet_elective_allow_5k.isSelected());
        degreeTrackToUpdate.setOverallGPARequirement(updatet_overall_gpa.getText().toString());

        // Grab all table values
        String electivesAcceptedLowerCourses[] = new String[updatet_5k_table.getItems().size()];
        for (int i = 0; i < electivesAcceptedLowerCourses.length; i++) {
            electivesAcceptedLowerCourses[i] = updatet_5k_table.getItems().get(i).getJsonCourse().getCourseNumber();
        }

        String coreClassListRequirement[] = new String[updatet_core_table.getItems().size()];
        for (int i = 0; i < coreClassListRequirement.length; i++) {
            coreClassListRequirement[i] = updatet_core_table.getItems().get(i).getJsonCourse().getCourseNumber();
        }

        String optionalCoreClassListRequirement[] = new String[updatet_optional_core_table.getItems().size()];
        for (int i = 0; i < optionalCoreClassListRequirement.length; i++) {
            optionalCoreClassListRequirement[i] = updatet_optional_core_table.getItems().get(i).getJsonCourse()
                    .getCourseNumber();
        }

        String electiveClassListRequirement[] = new String[updatet_elective_table.getItems().size()];
        for (int i = 0; i < electiveClassListRequirement.length; i++) {
            electiveClassListRequirement[i] = updatet_elective_table.getItems().get(i).getJsonCourse()
                    .getCourseNumber();
        }

        // Update table values
        degreeTrackToUpdate.setElectivesAcceptedLowerCourses(electivesAcceptedLowerCourses);
        degreeTrackToUpdate.setCoreClassListRequirement(coreClassListRequirement);
        degreeTrackToUpdate.setOptionsCoreClassListRequirement(optionalCoreClassListRequirement);
        degreeTrackToUpdate.setElectiveClassListRequirement(electiveClassListRequirement);

        // If degree track was renamed, update it
        if (degreeList.UpdateDegreeInList(degreeTrackToUpdate)) {
            if (!oldDegreeName.equals(newDegreeName)) {
                degreeList.UpdateDegreeName(oldDegreeName, newDegreeName);
            }
            tattooOnMyChestie();
        } else {
            cowboyFromTheWestie("Error: Degree List not Updated.");
        }

        // Refresh Tab
        initialize(null, null);
    }

    /*
     * REMOVE DEGREE TRACK
     */
    @FXML
    private ComboBox<String> removet_dropdown;

    @FXML
    private Button removet_remove_btn;

    // "REMOVE" button is pressed
    @FXML
    public void removeDegreeTrack(ActionEvent event) {
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        degreeList.RemoveDegree(removet_dropdown.getValue());

        // Refresh Tab
        initialize(null, null);
        tattooOnMyChestie();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Clear ADD COURSES tab
        addc_class_number.clear();
        addc_class_name.clear();
        addc_class_description.clear();
        addc_type_dropdown.getSelectionModel().clearSelection();
        addc_active_status.setSelected(false);
        addc_prerequisites_dropdown.getSelectionModel().clearSelection();
        addc_prerequisites_table.getItems().clear();

        // clear UPDATE COURSES tab
        updatec_class_dropdown.getSelectionModel().clearSelection();
        updatec_class_number.clear();
        updatec_class_name.clear();
        updatec_class_description.clear();
        updatec_type_dropdown.getSelectionModel().clearSelection();
        updatec_active_status.setSelected(false);
        updatec_prerequisites_dropdown.getSelectionModel().clearSelection();
        updatec_prerequisites_table.getItems().clear();

        // clear ADD DEGREE TRACK tab
        addt_track_name.clear();
        addt_active_status.setSelected(false);
        addt_num_core_courses.clear();
        addt_core_gpa_requirements.clear();
        addt_core_replace_2nd.setSelected(false);
        addt_core_allow_7th.setSelected(false);
        addt_num_electives.clear();
        addt_overall_gpa.clear();
        addt_elective_gpa_requirements.clear();
        addt_elective_replace_2nd.setSelected(false);
        addt_elective_allow_5k.setSelected(false);
        addt_5k_dropdown.getSelectionModel().clearSelection();
        addt_5k_table.getItems().clear();
        addt_core_dropdown.getSelectionModel().clearSelection();
        addt_core_table.getItems().clear();
        addt_optional_core_dropdown.getSelectionModel().clearSelection();
        addt_optional_core_table.getItems().clear();
        addt_elective_dropdown.getSelectionModel().clearSelection();
        addt_elective_table.getItems().clear();

        // clear UPDATE DEGREE TRACK tab
        updatet_dropdown.getSelectionModel().clearSelection();
        updatet_track_name.clear();
        updatet_active_status.setSelected(false);
        updatet_num_core_courses.clear();
        updatet_core_gpa_requirements.clear();
        updatet_core_replace_2nd.setSelected(false);
        updatet_core_allow_7th.setSelected(false);
        updatet_num_electives.clear();
        updatet_overall_gpa.clear();
        updatet_elective_gpa_requirements.clear();
        updatet_elective_replace_2nd.setSelected(false);
        updatet_elective_allow_5k.setSelected(false);
        updatet_5k_dropdown.getSelectionModel().clearSelection();
        updatet_5k_table.getItems().clear();
        updatet_core_dropdown.getSelectionModel().clearSelection();
        updatet_core_table.getItems().clear();
        updatet_optional_core_dropdown.getSelectionModel().clearSelection();
        updatet_optional_core_table.getItems().clear();
        updatet_elective_dropdown.getSelectionModel().clearSelection();
        updatet_elective_table.getItems().clear();

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
        ObservableList<String> addtOptionalCoreDropList = FXCollections.observableArrayList();
        ObservableList<String> addtElectiveDropList = FXCollections.observableArrayList();

        for (JSONCourse course : allCourses) {
            addcPreqDropList.add(course.getCourseNumber());
            removecDropList.add(course.getCourseNumber());
            updatecDropList.add(course.getCourseNumber());
            addt5kDropList.add(course.getCourseNumber());
            addtCoreDropList.add(course.getCourseNumber());
            addtOptionalCoreDropList.add(course.getCourseNumber());
            addtElectiveDropList.add(course.getCourseNumber());
        }

        // Sort all course dropdowns
        Collections.sort(addcPreqDropList);
        Collections.sort(removecDropList);
        Collections.sort(updatecDropList);
        Collections.sort(addt5kDropList);
        Collections.sort(addtCoreDropList);
        Collections.sort(addtOptionalCoreDropList);
        Collections.sort(addtElectiveDropList);

        // Populate all course dropdowns
        addc_prerequisites_dropdown.setItems(addcPreqDropList);
        removec_dropdown.setItems(removecDropList);
        updatec_class_dropdown.setItems(updatecDropList);
        addt_5k_dropdown.setItems(addt5kDropList);
        addt_core_dropdown.setItems(addtCoreDropList);
        addt_optional_core_dropdown.setItems(addtOptionalCoreDropList);
        addt_elective_dropdown.setItems(addtElectiveDropList);

        // dropdown for all degree tracks
        DegreeList degreeList = new DegreeList("resources/DegreeList.json");
        List<JSONDegree> allDegreeTracks = degreeList.GetDegreeList();
        ObservableList<String> updatetDropList = FXCollections.observableArrayList();
        ObservableList<String> removetDropList = FXCollections.observableArrayList();

        // Sort degree tracks
        Collections.sort(updatetDropList);

        // Populate degree tracks
        for (JSONDegree degreeTrack : allDegreeTracks) {
            updatetDropList.add(degreeTrack.getDegreeName());
            removetDropList.add(degreeTrack.getDegreeName());
        }
        updatet_dropdown.setItems(updatetDropList);
        removet_dropdown.setItems(removetDropList);

        // ADD COURSE TAB - PREREQUISITES TABLE - setting columns using getters and
        // setters from JSONCourseWrapper
        addc_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        addc_course_remove_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

        // UPDATE COURSE TAB - PREREQUISITES TABLE - setting columns using getters and
        // setters from JSONCourseWrapper
        updatec_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        updatec_course_remove_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

        // ADD TRACK TAB - ADD 5k COURSES TABLE - setting columns using getters and
        // setters from JSONCourseWrapper
        addt_5k_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        addt_5k_remove_course_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

        // ADD TRACK TAB - ADD CORE COURSES TABLE - setting columns using getters and
        // setters from JSONCourseWrapper
        addt_core_course_num_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        addt_core_remove_course_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

        // ADD TRACK TAB - ADD OPTIONAL CORE COURSES TABLE - setting columns using
        // getters and
        // setters from JSONCourseWrapper
        addt_optional_core_course_num_col
                .setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        addt_optional_core_remove_course_col
                .setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

        // ADD TRACK TAB - ADD ELECTIVE COURSES TABLE - setting columns using getters
        // and setters from JSONCourseWrapper
        addt_elective_course_num_col
                .setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        addt_elective_remove_course_col
                .setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

        // UPDATE TRACK TAB - ADD 5k COURSES TABLE - setting columns using getters and
        // setters from JSONCourseWrapper
        updatet_5k_course_num_col
                .setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        updatet_5k_remove_course_col.setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

        // UPDATE TRACK TAB - ADD CORE COURSES TABLE - setting columns using getters and
        // setters from JSONCourseWrapper
        updatet_core_course_num_col
                .setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        updatet_core_remove_course_col
                .setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

        // UPDATE TRACK TAB - ADD OPTIONAL CORE COURSES TABLE - setting columns using
        // getters and
        // setters from JSONCourseWrapper
        updatet_optional_core_course_num_col
                .setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        updatet_optional_core_remove_course_col
                .setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

        // UPDATE TRACK TAB - ADD ELECTIVE COURSES TABLE - setting columns using getters
        // and setters from JSONCourseWrapper
        updatet_elective_course_num_col
                .setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("jsonCourse"));
        updatet_elective_remove_course_col
                .setCellValueFactory(new PropertyValueFactory<JSONCourseWrapper, String>("button"));

    }

}
