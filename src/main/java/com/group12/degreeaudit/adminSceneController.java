package com.group12.degreeaudit;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class adminSceneController implements Initializable{

    // TABS
    @FXML
    private TabPane tab_pane;

    @FXML
    private Tab panel_add_course, panel_update_course, panel_remove_course, panel_add_degree_track, panel_update_degree_track, panel_remove_degree_track;

    @FXML
    private Button btn_add_course, btn_update_course, btn_remove_course, btn_add_degree_track, btn_update_degree_track, btn_remove_degree_track;

    @FXML
    private void handleButtonAction(ActionEvent event){
        if(event.getSource() == btn_add_course){
            tab_pane.getSelectionModel().select(panel_add_course);
        }
        else if(event.getSource() == btn_update_course){
            tab_pane.getSelectionModel().select(panel_update_course);
        }
        else if(event.getSource() == btn_remove_course){
            tab_pane.getSelectionModel().select(panel_remove_course);
        }
        else if(event.getSource() == btn_add_degree_track){
            tab_pane.getSelectionModel().select(panel_add_degree_track);
        }
        else if(event.getSource() == btn_update_degree_track){
            tab_pane.getSelectionModel().select(panel_update_degree_track);
        }
        else if(event.getSource() == btn_remove_degree_track){
            tab_pane.getSelectionModel().select(panel_remove_degree_track);
        }
    }
    
    // Update Course Tab:
    @FXML
    private ComboBox<String> choose_class_dropdown;
    
   
    @FXML
    private Label label;
 /* 
    @FXML
    void Select(ActionEvent event){
        String s = class_number_dropdown.getSelectionModel().getSelectedItem().toString();
        label.setText(s);
    }*/



    // Add Degree Track Tab
    // Add Course Table
    @FXML
    public TableView<CourseSample> table_view;

    //Columns
    @FXML
    public TableColumn<CourseSample, String> course_num_col;

    @FXML
    public TableColumn<CourseSample, String> course_name_col;

    @FXML
    public TableColumn<CourseSample, String> remove_course_col;

    //Text input
    @FXML
    private TextField course_num_input;

    @FXML
    private TextField course_name_input;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        ObservableList<String> list = FXCollections.observableArrayList("CS123", "CS456", "CS789");
        choose_class_dropdown.setItems(list);


        course_name_col.setCellValueFactory(new PropertyValueFactory<CourseSample, String>("name"));
        course_num_col.setCellValueFactory(new PropertyValueFactory<CourseSample, String>("number"));
        remove_course_col.setCellValueFactory(new PropertyValueFactory<CourseSample, String>("button"));


    }

    //Submit button
    @FXML
    void addCourse(ActionEvent event) {
        CourseSample course = new CourseSample(course_name_input.getText(), course_num_input.getText());
        ObservableList<CourseSample> courseSample = table_view.getItems();
        courseSample.add(course);
        table_view.setItems(courseSample);
        course.ButtonCell(table_view, course);
        
    }

    @FXML
    public void clickItem(MouseEvent event)
    {
        if (event.getClickCount() == 2) //Checking double click
        {

            CourseSample row_selected = (table_view.getSelectionModel().getSelectedItem());
            //table_view.getItems().remove(row_selected);
        }
    }

}
