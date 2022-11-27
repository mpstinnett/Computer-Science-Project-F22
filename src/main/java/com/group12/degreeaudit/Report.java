package com.group12.degreeaudit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.group12.degreeaudit.Administration.CourseList;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

public class Report {
    public static void createAuditReport(String audit, String studentID) {
        try {
            Scanner scan = new Scanner(audit);
            //Create initial variables for document
            String dest = "resources/" + studentID + "_Audit.pdf";
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
    public static void createDegreePlan(Student student) {
        String degreeTrack = student.getDegreeTrack().getDegreeName();
        final String FILE_NAME;
        String[] studentInformationFieldKeys; 
        String[] coreClassNumbers;
        String[][] coreFieldKeys;
        String[][] electiveFieldKeys;
        String[] admissionPrereqClassNumbers;
        String[][] admissionPrereqFieldKeys;

        switch(degreeTrack) {
            case "Traditional Computer Science":  
                FILE_NAME = "Traditional_Computer_Science";
                studentInformationFieldKeys = new String[]{"", "", ""};
                coreClassNumbers = new String[]{};
                coreFieldKeys = new String[][]{{}};
                electiveFieldKeys = new String[][]{{}};
                admissionPrereqClassNumbers = new String[]{};
                admissionPrereqFieldKeys = new String[][]{{}};
                break;
            case "Networks and Telecommunications":
                FILE_NAME = "resources\\degreePlanBlueprints\\Networks_Telecommunications.pdf";
                studentInformationFieldKeys = new String[]{"", "", ""};
                coreClassNumbers = new String[]{};
                coreFieldKeys = new String[][]{{}};
                electiveFieldKeys = new String[][]{{}};
                admissionPrereqClassNumbers = new String[]{};
                admissionPrereqFieldKeys = new String[][]{{}};
                break;
            case "Intelligent Systems":
                FILE_NAME = "resources\\degreePlanBlueprints\\Intelligent_Systems.pdf";
                studentInformationFieldKeys = new String[]{"", "", ""};
                coreClassNumbers = new String[]{};
                coreFieldKeys = new String[][]{{}};
                electiveFieldKeys = new String[][]{{}};
                admissionPrereqClassNumbers = new String[]{};
                admissionPrereqFieldKeys = new String[][]{{}};
                break;
            case "Cyber Security":
                FILE_NAME = "resources\\degreePlanBlueprints\\Cyber_Security.pdf";
                studentInformationFieldKeys = new String[]{"", "", ""};
                coreClassNumbers = new String[]{};
                coreFieldKeys = new String[][]{{}};
                electiveFieldKeys = new String[][]{{}};
                admissionPrereqClassNumbers = new String[]{};
                admissionPrereqFieldKeys = new String[][]{{}};
                break;
            case "Interactive Computing":
                FILE_NAME = "resources\\degreePlanBlueprints\\Interactive_Computing.pdf";
                studentInformationFieldKeys = new String[]{"", "", ""};
                coreClassNumbers = new String[]{};
                coreFieldKeys = new String[][]{{}};
                electiveFieldKeys = new String[][]{{}};
                admissionPrereqClassNumbers = new String[]{};
                admissionPrereqFieldKeys = new String[][]{{}};
                break;
            case "Systems":
                FILE_NAME = "resources\\degreePlanBlueprints\\Systems.pdf";
                studentInformationFieldKeys = new String[]{"Name of Student", "Student ID Number", "Semester Admitted to Program"};
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
                FILE_NAME = "resources\\degreePlanBlueprints\\Data_Science.pdf";
                studentInformationFieldKeys = new String[]{"", "", ""};
                coreClassNumbers = new String[]{};
                coreFieldKeys = new String[][]{{}};
                electiveFieldKeys = new String[][]{{}};
                admissionPrereqClassNumbers = new String[]{};
                admissionPrereqFieldKeys = new String[][]{{}};
                break;
            case "Software Engineering":
                FILE_NAME = "resources\\degreePlanBlueprints\\Software_Engineering.pdf";
                studentInformationFieldKeys = new String[]{"", "", ""};
                coreClassNumbers = new String[]{};
                coreFieldKeys = new String[][]{{}};
                electiveFieldKeys = new String[][]{{}};
                admissionPrereqClassNumbers = new String[]{};
                admissionPrereqFieldKeys = new String[][]{{}};
                break;
            default: System.out.println("Degree Track Not Found");
                FILE_NAME = "";
                studentInformationFieldKeys = new String[]{"", "", ""};
                coreClassNumbers = new String[]{};
                coreFieldKeys = new String[][]{{}};
                electiveFieldKeys = new String[][]{{}};
                admissionPrereqClassNumbers = new String[]{};
                admissionPrereqFieldKeys = new String[][]{{}};
        }
        getDegreePlan(student, FILE_NAME, studentInformationFieldKeys, coreClassNumbers, coreFieldKeys, electiveFieldKeys, admissionPrereqClassNumbers, admissionPrereqFieldKeys);
    }
    private static void getDegreePlan(Student student, String FILE_NAME, 
            String[] studentInformationFieldKeys, 
            String[] coreClassNumbers, 
            String[][] coreFieldKeys,
            String[][] electiveFieldKeys,
            String[] admissionPrereqClassNumbers,
            String[][] admissionPrereqFieldKeys) {

        String dest = "resources/" + student.getID() + "_Degree_Plan.pdf";
        try {
            PdfReader pdfReader = new PdfReader(new FileInputStream(FILE_NAME));
            PdfDocument pdfDoc = new PdfDocument(
                pdfReader, new PdfWriter(new FileOutputStream(dest)));
                
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            Map<String, PdfFormField> fields = form.getFormFields();

            //Courses taken by student
            List<Course> coursesTaken = student.getCoursesTaken();
            List<String> coursesTakenAsCourseNumber = new ArrayList<>();
            for(Course course : coursesTaken) {
                coursesTakenAsCourseNumber.add(course.getCourseNumber());
            }

            //Student Information
            fields.get(studentInformationFieldKeys[0]).setValue(student.getName());
            fields.get(studentInformationFieldKeys[1]).setValue(student.getID());
            fields.get(studentInformationFieldKeys[2]).setValue(student.getSemesterAdmitted());

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
            for(Course course : coursesTaken) {
                if(course.getCourseNumber().charAt(3) == '6' && course.getClassType() == 'E') {
                    fields.get(electiveFieldKeys[electiveCount][0]).setValue(new CourseList("resources/CourseList.json").GetCourseFromList(course.getCourseNumber()).getCourseName());
                    fields.get(electiveFieldKeys[electiveCount][1]).setValue(course.getCourseNumber());
                    fields.get(electiveFieldKeys[electiveCount][2]).setValue(course.getSemester()).setJustification(PdfFormField.ALIGN_CENTER);
                    fields.get(electiveFieldKeys[electiveCount][3]).setValue(course.getTransfer()? "T":"").setJustification(PdfFormField.ALIGN_CENTER);
                    fields.get(electiveFieldKeys[electiveCount][4]).setValue(course.getGrade()).setJustification(PdfFormField.ALIGN_CENTER);
                    electiveCount++;
                }
            }
            
            //Admission Prerequisites
            for(int i = 0; i < admissionPrereqClassNumbers.length; i++) {
                if(coursesTakenAsCourseNumber.contains(admissionPrereqClassNumbers[i])) {
                    fields.get(admissionPrereqFieldKeys[i][0]).setValue(student.getCourseGivenCourseNumber(admissionPrereqClassNumbers[i]).getSemester()).setJustification(PdfFormField.ALIGN_CENTER);
                    fields.get(admissionPrereqFieldKeys[i][2]).setValue(student.getCourseGivenCourseNumber(admissionPrereqClassNumbers[i]).getGrade()).setJustification(PdfFormField.ALIGN_CENTER);
                }
            }
            Document document = new Document(pdfDoc);
            document.close();
            pdfReader.close();
        } catch (IOException e) {
            System.out.println("IOException thrown.");
        }
    }
}
