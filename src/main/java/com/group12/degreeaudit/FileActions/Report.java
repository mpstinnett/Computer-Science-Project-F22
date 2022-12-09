package com.group12.degreeaudit.FileActions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Planner.Course;
import com.group12.degreeaudit.Planner.Student;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

/**
 * Description: Report - Class to generate reports (audit report and degree plan)
 */
public class Report {
    /**
     * Description: Generates an audit report pdf given some student information.
     * @param audit as String - contains student information such as name, ID, GPA, etc.
     * @param studentID as String - the student's ID number
     * @param auditFile as File - the file to write to
     */
    public static void createAuditReport(String audit, String studentID, File auditFile) {
        try {
            Scanner scan = new Scanner(audit);
            //Create initial variables for document
            String dest = auditFile.getPath().toString();
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            PdfFont regular = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);

            //String: Audit Report
            Text text = new Text(scan.nextLine()).setFont(bold);
            Paragraph paragraph = new Paragraph(text);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            paragraph.setFontSize(14);
            document.add(paragraph);

            paragraph = new Paragraph();

            //For each line, if there is a colon, bold the text before it. Otherwise, use regular font.
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                if(!line.contains(":")) {
                    text = new Text(line + "\n").setFont(regular);
                    paragraph.add(text);
                    continue;
                }
                String title = line.substring(0, line.indexOf(":")+1);
                String description = line.substring(line.indexOf(":")+1);
                //Puts necessary information on right side
                //Name will be on y=756, Plan on y=739, Track on y= 721
                if(title.equals("ID:") || title.equals("Major:") || title.equals("Track:")) {
                    text = new Text(title).setFont(bold);
                    Paragraph p = new Paragraph(text);
                    text = new Text(description).setFont(regular);
                    p.add(text);
                    if(title.equals("ID:"))
                        p.setFixedPosition(350, 756, 300);
                    else if(title.equals("Major:"))
                        p.setFixedPosition(350, 739, 300);
                    else
                        p.setFixedPosition(350, 721, 300);
                    document.add(p);
                    continue;
                }
                text = new Text(title).setFont(bold);
                paragraph.add(text);
                text = new Text(description + "\n").setFont(regular);
                paragraph.add(text);
            }
            document.add(paragraph);

            document.close();
            pdfDoc.close();
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException found.");
        } catch (IOException e) {
            System.out.println("IOException found.");
        }

    }

    /**
     * Generates a pdf of a given degree plan with the corresponding key values filled in for each field.
     * @param degreeName as string - name of the degree track
     * @param degreePlanFilePath as string - path containing the location to export to
     */
    public static void getKeysPDF(String degreeName, String degreePlanFilePath) {
        String outputFilePath = "resources\\degreePlanBlueprints\\" + degreeName + "_Keys.pdf";
        try {
            PdfReader pdfReader = new PdfReader(new FileInputStream(degreePlanFilePath));
            PdfDocument pdfDoc = new PdfDocument(
                pdfReader, new PdfWriter(new FileOutputStream(outputFilePath)));

            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            Map<String, PdfFormField> fields = form.getFormFields();
            for(String key : fields.keySet()) {
                fields.get(key).setValue(key);
            }
            Document document = new Document(pdfDoc);
            document.close();
            pdfDoc.close();
            pdfReader.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found.");
        } catch(IOException e) {
            System.out.println("IOException found.");
        }
    }

    /**
     * Description: Creates the degree plan pdf given a student. Has a lot of cases for each degree plan.
     * @param student as String - the student
     * @param degreePlanFile as File - File containing the location to export to
     */
    public static void createDegreePlan(Student student, File degreePlanFile) {
        String degreeTrack = student.getDegreeTrack().getDegreeName();
        final String DEGREE_PLAN_BLUEPRINT_FILE_NAME;
        String[] studentInformationFieldKeys; 
        String[] coreClassNumbers;
        String[][] coreFieldKeys;
        String[][] electiveFieldKeys;
        String[] admissionPrereqClassNumbers;
        String[][] admissionPrereqFieldKeys;

        switch(degreeTrack) {
            case "Traditional Computer Science":  
                DEGREE_PLAN_BLUEPRINT_FILE_NAME = "resources\\degreePlanBlueprints\\Traditional_Computer_Science.pdf";
                studentInformationFieldKeys = new String[]{"Name of Student", "Student ID Number", "Semester Admitted to Program 1", "Graduation"};
                coreClassNumbers = new String[]{"CS 6363", "CS 6378", "CS 6390", "CS 6353", "CS 6360", "CS 6371"};
                coreFieldKeys = new String[][]{
                    {"Text118.0.0.0", "Text118.0.1.0", "Text118.0.2.0"},
                    {"Text118.1.0.0", "Text118.1.1.0", "Text118.1.2.0"},
                    {"Text118.2.0.0", "Text118.2.1.0", "Text118.2.2.0"},
                    {"Text118.0.0.1", "Text118.0.1.1", "Text118.0.2.1"},
                    {"Text118.1.0.1", "Text118.1.1.1", "Text118.1.2.1"},
                    {"Text118.2.0.1", "Text118.2.1.1", "Text118.2.2.1"}};
                electiveFieldKeys = new String[][]{
                    {"Text120.0", "Text121.0", "Text118.0.0.2", "Text118.0.1.2", "Text118.0.2.2"},
                    {"Text120.1", "Text121.1", "Text118.1.0.2", "Text118.1.1.2", "Text118.1.2.2"},
                    {"Text120.2", "Text121.2", "Text118.2.0.2", "Text118.2.1.2", "Text118.2.2.2"},
                    {"Text120.3", "Text121.3", "Text118.3.0.2", "Text118.3.1.2", "Text118.3.2.2"},
                    {"Text120.4.0", "Text121.4.0", "Text118.0.0.3", "Text118.0.1.3", "Text118.0.2.3"},
                    {"Text120.4.1", "Text121.4.1", "Text118.1.0.3", "Text118.1.1.3", "Text118.1.2.3"},
                    {"Text120.4.2", "Text121.4.2", "Text118.2.0.3", "Text118.2.1.3", "Text118.2.2.3"},
                    {"Text120.4.3.0", "Text121.4.3", "Text118.3.0.3", "Text118.3.1.3", "Text118.3.2.3"}};
                admissionPrereqClassNumbers = new String[]{"CS 5303", "CS 5330", "CS 5333", "CS 5343", "CS 5348", "CS 5349", "CS 5390"};
                admissionPrereqFieldKeys = new String[][]{
                    {"Text124.0", "Text124.1", "Text124.2"},
                    {"Text118.0.0.5", "Text118.0.1.5", "Text118.0.2.5"},
                    {"Text118.1.0.5", "Text118.1.1.5", "Text118.1.2.5"},
                    {"Text118.2.0.5", "Text118.2.1.5", "Text118.2.2.5"},
                    {"Text118.3.0.5", "Text118.3.1.5", "Text118.3.2.5"},
                    {"Text118.0.0.6", "Text118.0.1.6", "Text118.0.2.6"},
                    {"Text118.1.0.6", "Text118.1.1.6", "Text118.1.2.6"}};
                break;
            case "Networks Telecommunication":
                DEGREE_PLAN_BLUEPRINT_FILE_NAME = "resources\\degreePlanBlueprints\\Networks_Telecommunication.pdf";
                studentInformationFieldKeys = new String[]{"Name of Student", "Student ID Number", "Semester Admitted to Program 1", "Graduation"};
                coreClassNumbers = new String[]{"CS 6352", "CS 6363", "CS 6378", "CS 6385", "CS 6390"};
                coreFieldKeys = new String[][]{
                    {"Text141.0.0", "Text141.0.1", "Text141.0.2"},
                    {"Text141.1.0", "Text141.1.1", "Text141.1.2"},
                    {"Text141.2.0", "Text141.2.1", "Text141.2.2"},
                    {"Text141.3.0", "Text141.3.1", "Text141.3.2"},
                    {"Text141.4.0", "Text141.4.1", "Text141.4.2"}};
                electiveFieldKeys = new String[][]{
                    {"Text146.0", "Text147.0", "Text148.0", "Text149.0", "Text150.0"}, 
                    {"Text146.1", "Text147.1", "Text148.1", "Text149.1", "Text150.1"}, 
                    {"Text146.2", "Text147.2", "Text148.2", "Text149.2", "Text150.2"}, 
                    {"Text146.3", "Text147.3", "Text148.3", "Text149.3", "Text150.3"}, 
                    {"Text146.4", "Text147.4", "Text148.4", "Text149.4", "Text150.4"}, 
                    {"Text146.5", "Text147.5", "Text148.5", "Text149.5", "Text150.5"}, 
                    {"Text146.6", "Text147.6", "Text148.6", "Text149.6", "Text150.6"}, 
                    {"Text146.7", "Text147.7", "Text148.7", "Text149.7", "Text150.7"}};
                admissionPrereqClassNumbers = new String[]{"CS 5303", "CS 5330", "CS 5333", "CS 5343", "CS 5348", "CS 5390", "CS 3341"};
                admissionPrereqFieldKeys = new String[][]{
                    {"Text148.8.1", "Text149.8.1", "Text150.8.1"}, 
                    {"Text148.9.1", "Text149.9.1", "Text150.9.1"},
                    {"Text148.8.2", "Text149.8.2", "Text150.8.2"}, 
                    {"Text148.9.2", "Text149.9.2", "Text150.9.2"}, 
                    {"Text148.8.3", "Text149.8.3", "Text150.8.3"}, 
                    {"Text148.9.3", "Text149.9.3", "Text150.9.3"}, 
                    {"Text148.8.4", "Text149.8.4", "Text150.8.4"}};
                break;
            case "Intelligent Systems":
                DEGREE_PLAN_BLUEPRINT_FILE_NAME = "resources\\degreePlanBlueprints\\Intelligent_Systems.pdf";
                studentInformationFieldKeys = new String[]{"Name of Student", "Student ID Number", "Semester Admitted to Program 1", "Graduation"};
                coreClassNumbers = new String[]{"CS 6320", "CS 6363", "CS 6364", "CS 6375", "CS 6360", "CS 6378"};
                coreFieldKeys = new String[][]{
                    {"Text182.0.0", "Text182.0.1", "Text182.0.2"},
                    {"Text182.1.0", "Text182.1.1", "Text182.1.2"},
                    {"Text182.2.0.0", "Text182.2.1.0", "Text182.2.2.0"},
                    {"Text182.3.0.0", "Text182.3.1.0", "Text182.3.2.0"},
                    {"Text182.2.0.1.0", "Text182.2.1.1.0", "Text182.2.2.1.0"},
                    {"Text182.3.0.1.0", "Text182.3.1.1.0", "Text182.3.2.1.0"}};
                electiveFieldKeys = new String[][]{
                    {"Text186.0.1.0", "Text186.1.1", "Text182.2.0.1.1", "Text182.2.1.1.1", "Text182.2.2.1.1"}, 
                    {"Text186.0.1.1", "Text186.1.2", "Text182.3.0.1.1", "Text182.3.1.1.1", "Text182.3.2.1.1"}, 
                    {"Text186.0.1.2", "Text186.1.3", "Text182.4.0.1.1", "Text182.4.1.1.1", "Text182.4.2.1.1"}, 
                    {"Text186.0.1.3.0", "Text186.1.4", "Text182.2.0.1.2", "Text182.2.1.1.2", "Text182.2.2.1.2"}, 
                    {"Text186.0.1.3.1", "Text186.1.5", "Text182.3.0.1.2.0", "Text182.3.1.1.2.0", "Text182.3.2.1.2.0"}, 
                    {"Text186.0.1.4", "Text186.1.6", "Text182.4.0.1.2.0.0", "Text182.4.1.1.2.0.0", "Text182.4.2.1.2.0.0"}, 
                    {"Text186.0.1.5", "Text186.1.7", "Text182.3.0.1.2.1.0", "Text182.3.1.1.2.1.0", "Text182.3.2.1.2.1.0"}, 
                    {"Text186.0.1.6", "Text186.1.8", "Text182.4.0.1.2.1.0", "Text182.4.1.1.2.1.0", "Text182.4.2.1.2.1.0"}};
                admissionPrereqClassNumbers = new String[]{"CS 5303", "CS 5330", "CS 5333", "CS 5343", "CS 5348"};
                admissionPrereqFieldKeys = new String[][]{
                    {"Text182.4.0.1.2.1.1", "Text182.4.1.1.2.1.1", "Text182.4.2.1.2.1.1"}, 
                    {"Text182.4.0.1.2.0.2", "Text182.4.1.1.2.0.2", "Text182.4.2.1.2.0.2"},
                    {"Text182.3.0.1.2.1.2", "Text182.3.1.1.2.1.2", "Text182.3.2.1.2.1.2"}, 
                    {"Text182.4.0.1.2.1.2", "Text182.4.1.1.2.1.2", "Text182.4.2.1.2.1.2"}, 
                    {"Text182.4.0.1.2.0.3", "Text182.4.1.1.2.0.3", "Text182.4.2.1.2.0.3"}};
                break;
            case "Cyber Security":
                DEGREE_PLAN_BLUEPRINT_FILE_NAME = "resources\\degreePlanBlueprints\\Cyber_Security.pdf";
                studentInformationFieldKeys = new String[]{"Name of Student", "Student ID Number", "Semester Admitted to Program", "Text199"};
                coreClassNumbers = new String[]{"CS 6324", "CS 6363", "CS 6378", "CS 6332", "CS 6348", "CS 6349", "CS 6377"};
                coreFieldKeys = new String[][]{
                    {"Text200.0.0", "Text200.0.1", "Text200.0.2"}, 
                    {"Text200.1.0", "Text200.1.1", "Text200.1.2"},
                    {"Text200.2.0", "Text200.2.1", "Text200.2.2"}, 
                    {"Text200.3.0.2.0.0", "Text200.3.0.2.1.0", "Text200.3.0.2.2.0"}, 
                    {"Text200.3.0.2.0.1", "Text200.3.0.2.1.1", "Text200.3.0.2.2.1"}, 
                    {"Text200.3.0.2.0.2", "Text200.3.0.2.1.2", "Text200.3.0.2.2.2"}, 
                    {"Text200.3.0.2.0.3", "Text200.3.0.2.1.3", "Text200.3.0.2.2.3"}};
                electiveFieldKeys = new String[][]{
                    {"Text203.3", "Text204.3", "Text205.3", "Text206.3", "Text207.3"}, 
                    {"Text203.4", "Text204.4", "Text205.4", "Text206.4", "Text207.4"}, 
                    {"Text203.5", "Text204.5", "Text205.5", "Text206.5", "Text207.5"}, 
                    {"Text203.6", "Text204.6", "Text205.6", "Text206.6", "Text207.6"}};
                admissionPrereqClassNumbers = new String[]{"CS 5303", "CS 5330", "CS 5333", "CS 5343", "CS 5348", "CS 5390"};
                admissionPrereqFieldKeys = new String[][]{
                    {"Text213.0.0", "Text213.0.1", "Text213.0.2"}, 
                    {"Text213.1.0", "Text213.1.1", "Text213.1.2"},
                    {"Text213.2.0", "Text213.2.1", "Text213.2.2"}, 
                    {"Text213.3.0", "Text213.3.1", "Text213.3.2"}, 
                    {"Text213.4.0", "Text213.4.1", "Text213.4.2"}, 
                    {"Text213.5.0", "Text213.5.1", "Text213.5.2"}};
                break;
            case "Interactive Computing":
                DEGREE_PLAN_BLUEPRINT_FILE_NAME = "resources\\degreePlanBlueprints\\Interactive_Computing.pdf";
                studentInformationFieldKeys = new String[]{"Name of Student", "Student ID Number", "Semester Admitted to Program 1", "Graduation"};
                coreClassNumbers = new String[]{"CS 6326", "CS 6363", "CS 6323", "CS 6328", "CS 6331", "CS 6334", "CS 6366"};
                coreFieldKeys = new String[][]{
                    {"Text1.0.0", "Text1.0.1", "Text1.0.2"}, 
                    {"Text1.1.0", "Text1.1.1", "Text1.1.2"},
                    {"Text2.0.0", "Text2.0.1", "Text2.0.2"}, 
                    {"Text2.1.0", "Text2.1.1", "Text2.1.2"}, 
                    {"Text2.2.0", "Text2.2.1", "Text2.2.2"}, 
                    {"Text2.3.0", "Text2.3.1", "Text2.3.2"}, 
                    {"Text2.4.0", "Text2.4.1", "Text2.4.2"}};
                electiveFieldKeys = new String[][]{
                    {"Text3.0", "Text4.0.0", "Text4.0.1.0", "Text4.0.2.0", "Text4.0.3.0"}, 
                    {"Text3.1", "Text4.1.0", "Text4.1.1.0", "Text4.1.2.0", "Text4.1.3.0"}, 
                    {"Text3.2", "Text4.2.0", "Text4.2.1.0", "Text4.2.2.0", "Text4.2.3.0"}, 
                    {"Text3.3", "Text4.3.0", "Text4.3.1.0", "Text4.3.2.0", "Text4.3.3.0"}, 
                    {"Text3.4", "Text4.4.0", "Text4.4.1.0", "Text4.4.2.0", "Text4.4.3.0"}, 
                    {"Text3.5", "Text4.5", "Text4.0.1.1", "Text4.0.2.1", "Text4.0.3.1"}, 
                    {"Text3.6", "Text4.6", "Text4.1.1.1", "Text4.1.2.1", "Text4.1.3.1"}, 
                    {"Text3.7", "Text4.7", "Text4.2.1.1", "Text4.2.2.1", "Text4.2.3.1"}};
                admissionPrereqClassNumbers = new String[]{"CS 5303", "CS 5330", "CS 5333", "CS 5343", "CS 5348"};
                admissionPrereqFieldKeys = new String[][]{
                    {"Text4.4.1.1.2", "Text4.4.1.1.3", "Text4.4.1.1.4"}, 
                    {"Text4.4.1.1.5", "Text4.4.1.1.7", "Text4.4.1.1.6"},
                    {"Text4.4.1.1.8", "Text4.4.1.1.10", "Text4.4.1.1.11"}, 
                    {"Text4.4.1.1.12", "Text4.4.1.1.13", "Text4.4.1.1.9"}, 
                    {"Text4.4.1.1.14", "Text4.4.1.1.16", "Text4.4.1.1.17"}};
                break;
            case "Systems":
                DEGREE_PLAN_BLUEPRINT_FILE_NAME = "resources\\degreePlanBlueprints\\Systems.pdf";
                studentInformationFieldKeys = new String[]{"Name of Student", "Student ID Number", "Semester Admitted to Program", "Graduation"};
                coreClassNumbers = new String[]{"CS 6304", "CS 6363", "CS 6378", "CS 6396", "CS 6349", "CS 6376", "CS 6380", "CS 6397", "CS 6399"};
                coreFieldKeys = new String[][]{
                    {"Text132.0.0.0", "Text132.0.1.0", "Text132.0.2.0"},
                    {"Text132.1.0.0", "Text132.1.1.0", "Text132.1.2.0"},
                    {"Text132.2.0.0", "Text132.2.1.0", "Text132.2.2.0"},
                    {"Text132.3.0.0", "Text132.3.1.0", "Text132.3.2.0"},
                    {"Text132.0.0.1", "Text132.0.1.1", "Text132.0.2.1"},
                    {"Text132.1.0.1", "Text132.1.1.1", "Text132.1.2.1"},
                    {"Text132.2.0.1", "Text132.2.1.1", "Text132.2.2.1"},
                    {"Text132.3.0.1.0", "Text132.3.1.1.0", "Text132.3.2.1.0"},
                    {"Text132.3.0.1.1", "Text132.3.1.1.1", "Text132.3.2.1.1"}};
                electiveFieldKeys = new String[][]{
                    {"Text133.0", "Text134.0.0", "Text134.0.1", "Text134.0.2", "Text134.0.3"},
                    {"Text133.1.0", "Text134.1.0", "Text134.1.1", "Text134.1.2", "Text134.1.3"},
                    {"Text133.2.0", "Text134.2.0.0", "Text134.2.1.0", "Text134.2.2.0", "Text134.2.3.0"},
                    {"Text133.3.0", "Text134.3.0.0", "Text134.3.1.0", "Text134.3.2.0", "Text134.3.3.0"},
                    {"Text133.4.0", "Text134.4.0.0", "Text134.4.1.0", "Text134.4.2.0", "Text134.4.3.0"},
                    {"Text133.2.1", "Text134.2.0.1", "Text134.2.1.1", "Text134.2.2.1", "Text134.2.3.1"},
                    {"Text133.3.1.0", "Text134.3.0.1.0", "Text134.3.1.1.0", "Text134.3.2.1.0", "Text134.3.3.1.0"},
                    {"Text133.4.1.0", "Text134.4.0.1.0", "Text134.4.1.1.0", "Text134.4.2.1.0", "Text134.4.3.1.0"}};
                admissionPrereqClassNumbers = new String[]{"CS 5303", "CS 5330", "CS 5333", "CS 5343", "CS 5348", "CS 5390"};
                admissionPrereqFieldKeys = new String[][]{
                    {"Text134.4.1.1.1.1.0", "Text134.4.1.1.1.1.1", "Text134.4.1.1.1.1.2"},
                    {"Text134.4.1.1.1.2.0", "Text134.4.1.1.1.2.1", "Text134.4.1.1.1.2.2"},
                    {"Text134.4.1.1.1.3.0", "Text134.4.1.1.1.3.1", "Text134.4.1.1.1.3.2"},
                    {"Text134.4.1.1.1.4.0", "Text134.4.1.1.1.4.1", "Text134.4.1.1.1.4.2"},
                    {"Text134.4.1.1.1.5.0", "Text134.4.1.1.1.5.1", "Text134.4.1.1.1.5.2"},
                    {"Text134.4.1.1.1.6.0", "Text134.4.1.1.1.6.1", "Text134.4.1.1.1.6.2"}};
                break;
            case "Data Science":
                DEGREE_PLAN_BLUEPRINT_FILE_NAME = "resources\\degreePlanBlueprints\\Data_Science.pdf";
                studentInformationFieldKeys = new String[]{"Name of Student", "Student ID Number", "Semester Admitted to Program", "Graduation"};
                coreClassNumbers = new String[]{"CS 6313", "CS 6350", "CS 6363", "CS 6375", "CS 6301", "CS 6320", "CS 6327", "CS 6347", "CS 6360"};
                coreFieldKeys = new String[][]{
                    {"CS 6313.0", "CS 6313.1", "CS 6313.2"}, 
                    {"CS 6350.0", "CS 6350.1", "CS 6350.2"},
                    {"CS 6363.0", "CS 6363.1", "CS 6363.2"}, 
                    {"CS 6375.0", "CS 6375.1", "CS 6375.2"}, 
                    {"CS 6301.0.0", "CS 6301.0.1", "CS 6301.0.2"}, 
                    {"CS 6320.0.0", "CS 6320.0.1", "CS 6320.0.2"}, 
                    {"CS 6327.0.0", "CS 6327.0.1", "CS 6327.0.2"}, 
                    {"CS 6347.0.0", "CS 6347.0.1", "CS 6347.0.2"}, 
                    {"CS 6360.0.0", "CS 6360.0.1", "CS 6360.0.2"}};
                electiveFieldKeys = new String[][]{
                    {"1", "CS 6327.1.0.0.0", "CS 6327.1.0.1", "CS 6327.1.1", "CS 6327.1.2"}, 
                    {"2", "CS 6327.1.0.0.1", "CS 6347.1.0", "CS 6347.1.1", "CS 6347.1.2"}, 
                    {"3", "CS 6327.1.0.0.2", "CS 6360.1.0", "CS 6360.1.1", "CS 6360.1.2"}, 
                    {"4", "CS 6327.1.0.0.3", "CS 6301.2.0", "CS 6301.2.1", "CS 6301.2.2"}, 
                    {"5", "CS 6327.1.0.0.4", "CS 6320.2.0", "CS 6320.2.1", "CS 6320.2.2"}, 
                    {"6", "CS 6327.1.0.0.5", "CS 6347.2.0", "CS 6347.2.1", "CS 6347.2.2"}, 
                    {"7", "CS 6327.1.0.0.6", "CS 6360.2.0.0", "CS 6360.2.1.0", "CS 6360.2.2.0"}, 
                    {"8", "CS 6327.1.0.0.7", "CS 6360.2.0.1", "CS 6360.2.1.1", "CS 6360.2.2.1"}};
                admissionPrereqClassNumbers = new String[]{"CS 5303", "CS 5330", "CS 5333", "CS 5343", "CS 5348", "CS 3341"};
                admissionPrereqFieldKeys = new String[][]{
                    {"CS 5303", "UTD Admission Prerequisites Course Semester Waiver GradeRow7_2.0", "UTD Admission Prerequisites Course Semester Waiver GradeRow7_3.0"}, 
                    {"CS 5330", "UTD Admission Prerequisites Course Semester Waiver GradeRow7_2.1", "UTD Admission Prerequisites Course Semester Waiver GradeRow7_3.1"},
                    {"CS 5333", "UTD Admission Prerequisites Course Semester Waiver GradeRow7_2.2", "UTD Admission Prerequisites Course Semester Waiver GradeRow7_3.2"}, 
                    {"CS 5343", "UTD Admission Prerequisites Course Semester Waiver GradeRow7_2.3", "UTD Admission Prerequisites Course Semester Waiver GradeRow7_3.3"}, 
                    {"CS 5348", "UTD Admission Prerequisites Course Semester Waiver GradeRow7_2.4", "UTD Admission Prerequisites Course Semester Waiver GradeRow7_3.4"}, 
                    {"CS 3341", "UTD Admission Prerequisites Course Semester Waiver GradeRow7_2.5", "UTD Admission Prerequisites Course Semester Waiver GradeRow7_3.5"}};
                break;
            case "Software Engineering":
                DEGREE_PLAN_BLUEPRINT_FILE_NAME = "resources\\degreePlanBlueprints\\Software_Engineering.pdf";
                studentInformationFieldKeys = new String[]{"Name of Student", "Student ID Number", "Semester Admitted to Program 1", "Graduation"};
                coreClassNumbers = new String[]{"SE 6329", "SE 6361", "SE 6362", "SE 6367", "SE 6387"};
                coreFieldKeys = new String[][]{
                    {"Text65.0", "Text65.1", "Text65.2"}, 
                    {"Text66.0", "Text66.1", "Text66.2"},
                    {"Text67.0", "Text67.1", "Text67.2"}, 
                    {"Text68.0", "Text68.1", "Text68.2"}, 
                    {"Text69.0", "Text69.1", "Text69.2"}};
                electiveFieldKeys = new String[][]{
                    {"Text89", "Text70.0.0", "Text99.1", "Text70.1", "Text70.2"}, 
                    {"Text90", "Text71.0.0", "Text71.0.1", "Text71.1", "Text71.2"}, 
                    {"Text91", "Text72.0.0", "Text72.0.1", "Text72.1", "Text72.2"}, 
                    {"Text92", "Text73.0.0", "Text73.0.1", "Text73.1", "Text73.2"}, 
                    {"Text93", "Text74.0.0", "Text74.0.1", "Text74.1", "Text74.2"}, 
                    {"Text94", "Text75.0.0", "Text75.0.1", "Text75.1", "Text75.2"}, 
                    {"Text95", "Text76.0.0", "Text76.0.1", "Text76.1", "Text76.2"}, 
                    {"Text96", "Text77.0.0", "Text77.0.1", "Text77.1", "Text77.2"}};
                admissionPrereqClassNumbers = new String[]{"CS 5303", "CS 5330", "CS 5333", "CS 5343", "CS 5348", "CS 5354"};
                admissionPrereqFieldKeys = new String[][]{
                    {"Text80.0", "Text80.1", "Text80.2"}, 
                    {"Text81.0", "Text81.1", "Text81.2"},
                    {"Text82.0", "Text82.1", "Text82.2"}, 
                    {"Text83.0", "Text83.1", "Text83.2"}, 
                    {"Text84.0", "Text84.1", "Text84.2"}, 
                    {"Text85.0", "Text85.1", "Text85.2"}};
                break;
            default: System.out.println("Degree Track Not Found");
                DEGREE_PLAN_BLUEPRINT_FILE_NAME = "";
                studentInformationFieldKeys = new String[]{"", "", ""};
                coreClassNumbers = new String[]{};
                coreFieldKeys = new String[][]{{}};
                electiveFieldKeys = new String[][]{
                    {"", "", "", "", ""}, 
                    {"", "", "", "", ""}, 
                    {"", "", "", "", ""}, 
                    {"", "", "", "", ""}, 
                    {"", "", "", "", ""}, 
                    {"", "", "", "", ""}, 
                    {"", "", "", "", ""}, 
                    {"", "", "", "", ""}};
                admissionPrereqClassNumbers = new String[]{};
                admissionPrereqFieldKeys = new String[][]{
                    {"", "", ""}, 
                    {"", "", ""},
                    {"", "", ""}, 
                    {"", "", ""}, 
                    {"", "", ""}, 
                    {"", "", ""}, 
                    {"", "", ""}};
        }
        getDegreePlan(student, DEGREE_PLAN_BLUEPRINT_FILE_NAME, studentInformationFieldKeys, coreClassNumbers, coreFieldKeys, electiveFieldKeys, admissionPrereqClassNumbers, admissionPrereqFieldKeys, degreePlanFile);
    }

    /**
     * Description: Generates the degree plan pdf after getting values from Report.createDegreePlan
     * @param student as student - the student
     * @param DEGREE_PLAN_BLUEPRINT_FILE_NAME as String - the file path to the degree plan
     * @param studentInformationFieldKeys as String array - the field keys for student information
     * @param coreClassNumbers as String array - the core class numbers in the degree plan
     * @param coreFieldKeys as String 2D array - the field keys for core classes
     * @param electiveFieldKeys as String 2D array - the field keys for elective courses
     * @param admissionPrereqClassNumbers as String array - the admission prerequisite class numbers in the degree plan
     * @param admissionPrereqFieldKeys as String 2D array - the field keys for admission prerequisite courses
     * @param degreePlanFile as File - the file to write to
     */
    private static void getDegreePlan(Student student, String DEGREE_PLAN_BLUEPRINT_FILE_NAME, 
            String[] studentInformationFieldKeys, 
            String[] coreClassNumbers, 
            String[][] coreFieldKeys,
            String[][] electiveFieldKeys,
            String[] admissionPrereqClassNumbers,
            String[][] admissionPrereqFieldKeys,
            File degreePlanFile) {

        String dest = degreePlanFile.getPath();

        try {
            PdfReader pdfReader = new PdfReader(new FileInputStream(DEGREE_PLAN_BLUEPRINT_FILE_NAME));
            PdfDocument pdfDoc = new PdfDocument(
                pdfReader, new PdfWriter(new FileOutputStream(dest)));
                
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            Map<String, PdfFormField> fields = form.getFormFields();

            //Tests all fields if they exist
            for(String key : fields.keySet()) {
                if(fields.get(key) == null)
                    System.out.println("Field " + key + " does not exist.");
            }

            //Courses taken by student
            List<Course> coursesTaken = student.getUniqueCoursesTaken(student.getCoursesTaken());
            List<String> coursesTakenAsCourseNumber = new ArrayList<>();
            for(Course course : coursesTaken) {
                coursesTakenAsCourseNumber.add(course.getCourseNumber());
            }

            //Student Information
            fields.get(studentInformationFieldKeys[0]).setValue(student.getName());
            fields.get(studentInformationFieldKeys[1]).setValue(student.getID());
            fields.get(studentInformationFieldKeys[2]).setValue(student.getSemesterAdmitted());
            fields.get(studentInformationFieldKeys[3]).setValue(student.getAnticipatedGraduation());

            //Core Courses
            for(int i = 0; i < coreClassNumbers.length; i++) {
                if(coursesTakenAsCourseNumber.contains(coreClassNumbers[i])) {
                    fields.get(coreFieldKeys[i][0]).setValue(student.getCourseGivenCourseNumber(coreClassNumbers[i]).getSemester()).setJustification(PdfFormField.ALIGN_CENTER);
                    fields.get(coreFieldKeys[i][1]).setValue(student.getCourseGivenCourseNumber(coreClassNumbers[i]).getTransfer()? "T":"").setJustification(PdfFormField.ALIGN_CENTER);
                    fields.get(coreFieldKeys[i][2]).setValue(student.getCourseGivenCourseNumber(coreClassNumbers[i]).getGrade()).setJustification(PdfFormField.ALIGN_CENTER);
                }
            }
            //Approved 6000 Level Electives
            int electiveCount = 0;
            boolean needSecondPage = true;
            Table table = null;
            List<Course> top5 = student.matchElectiveCourses(student.getDegreeTrack(), "top5");
            List<Course> past5 = student.matchElectiveCourses(student.getDegreeTrack(), "past5");
            
            if(electiveFieldKeys.length == 4)
            {
                if(top5.size() == 5 && top5.get(4) != null)
                {
                    past5.add(top5.get(4));
                    System.out.println("GET: " + top5.get(4));
                    top5.remove(4);
                }
            }

            for(int i = 0; i < top5.size(); i++) 
            {
                if(electiveCount < electiveFieldKeys.length) 
                {
                    fields.get(electiveFieldKeys[electiveCount][0]).setValue(new CourseList("resources/CourseList.json").GetCourseFromList(top5.get(i).getCourseNumber()).getCourseName());
                    fields.get(electiveFieldKeys[electiveCount][1]).setValue(top5.get(i).getCourseNumber());
                    fields.get(electiveFieldKeys[electiveCount][2]).setValue(top5.get(i).getSemester()).setJustification(PdfFormField.ALIGN_CENTER);
                    fields.get(electiveFieldKeys[electiveCount][3]).setValue(top5.get(i).getTransfer()? "T":"").setJustification(PdfFormField.ALIGN_CENTER);
                    fields.get(electiveFieldKeys[electiveCount][4]).setValue(top5.get(i).getGrade()).setJustification(PdfFormField.ALIGN_CENTER);
                    electiveCount++;
                }
            }
            for(int i = 0; i < past5.size(); i++) 
            {
                if(electiveCount < electiveFieldKeys.length) 
                {
                    fields.get(electiveFieldKeys[electiveCount][0]).setValue(new CourseList("resources/CourseList.json").GetCourseFromList(past5.get(i).getCourseNumber()).getCourseName());
                    fields.get(electiveFieldKeys[electiveCount][1]).setValue(past5.get(i).getCourseNumber());
                    fields.get(electiveFieldKeys[electiveCount][2]).setValue(past5.get(i).getSemester()).setJustification(PdfFormField.ALIGN_CENTER);
                    fields.get(electiveFieldKeys[electiveCount][3]).setValue(past5.get(i).getTransfer()? "T":"").setJustification(PdfFormField.ALIGN_CENTER);
                    fields.get(electiveFieldKeys[electiveCount][4]).setValue(past5.get(i).getGrade()).setJustification(PdfFormField.ALIGN_CENTER);
                    electiveCount++;
                }
            }
            if((past5.size() + top5.size()) > electiveCount)
            {
                //Adding a second page for extra electives
                if(needSecondPage) {
                    PageSize ps = new PageSize(pdfDoc.getFirstPage().getPageSize());
                    pdfDoc.addNewPage(ps);
                    float[] columnWidths = {50F, 300F, 125F, 125F, 125F, 125F};
                    table = new Table(columnWidths);
                    needSecondPage = false;
                }

                int removeFromElective = 5;
                if(electiveFieldKeys.length == 4)
                {
                    removeFromElective = 4;
                }
                for(int i = electiveCount - removeFromElective; i < past5.size(); i++)
                {
                    //Cells for the table
                    Cell courseCountCell = new Cell();
                    courseCountCell.add("" + (electiveCount+1));
                    table.addCell(courseCountCell);

                    Cell courseNameCell = new Cell();
                    courseNameCell.add(new CourseList("resources/CourseList.json").GetCourseFromList(past5.get(i).getCourseNumber()).getCourseName());
                    table.addCell(courseNameCell);

                    Cell courseNumberCell = new Cell();
                    courseNumberCell.add(past5.get(i).getCourseNumber());
                    table.addCell(courseNumberCell);

                    Cell courseSemesterCell = new Cell();
                    courseSemesterCell.add(past5.get(i).getSemester());
                    table.addCell(courseSemesterCell);

                    Cell courseTransferCell = new Cell();
                    courseTransferCell.add(past5.get(i).getTransfer()? "T":"");
                    table.addCell(courseTransferCell);

                    Cell courseGradeCell = new Cell();
                    courseGradeCell.add(past5.get(i).getGrade());
                    table.addCell(courseGradeCell);

                    electiveCount++;
                }
            }
            
            //Admission Prerequisites
            for(int i = 0; i < admissionPrereqClassNumbers.length; i++) {
                if(coursesTakenAsCourseNumber.contains(admissionPrereqClassNumbers[i])) {
                    fields.get(admissionPrereqFieldKeys[i][0]).setValue(student.getCourseGivenCourseNumber(admissionPrereqClassNumbers[i]).getSemester()).setJustification(PdfFormField.ALIGN_CENTER);
                    fields.get(admissionPrereqFieldKeys[i][1]).setValue(student.getCourseGivenCourseNumber(admissionPrereqClassNumbers[i]).getWaiver()? "T":"").setJustification(PdfFormField.ALIGN_CENTER);
                    fields.get(admissionPrereqFieldKeys[i][2]).setValue(student.getCourseGivenCourseNumber(admissionPrereqClassNumbers[i]).getGrade()).setJustification(PdfFormField.ALIGN_CENTER);
                }
            }
            Document document = new Document(pdfDoc);
            if(!needSecondPage) {
                document.add(new AreaBreak());
                Paragraph para = new Paragraph("Additional Electives (3 Credit Hours Minimum)").setTextAlignment(TextAlignment.CENTER);
                document.add(para);
                document.add(table);
            }
            document.close();
            pdfReader.close();
        } catch (IOException e) {
            System.out.println("IOException thrown.");
        }
    }
}
