package com.group12.degreeaudit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TranscriptScanner {
    private File transcriptFile;
    private Scanner scan;

    public TranscriptScanner() {}

    public TranscriptScanner(String transcriptFilePath) {
        transcriptFile = new File(transcriptFilePath);
    }

    /*
     * Goes through whole transcript and assigns relevant data to Student and Course objects.
     */
    public Student scanTranscript() {
        try {
            scan = new Scanner(transcriptFile);

            //Fetch student information
            String studentName = grabStudentName();
            String studentID = grabStudentID();
            String program = grabProgram();
            String semesterAdmitted = grabSemesterAdmitted();
            List<Course> studentCourses = grabStudentCourses();
            
            return new Student(studentName, studentID, program, semesterAdmitted, studentCourses);
        } catch (Exception e) {
            System.out.println("EXCEPTION ERROR!");
        }
        return null;
    }

    /*
     * Grabs student name.
     * 
     * Assume file has only one line with "Name: " followed by the student name.
     */
    private String grabStudentName() throws IOException {
        while(scan.hasNext()) {
            String line = scan.nextLine();
            if(line.startsWith("Name:"))
                return line.substring(line.indexOf(":")+2);
        }
        return "";
    }

    /*
     * Grabs student ID number.
     * 
     * Assume file has only one line with "Student ID: " followed by the student ID number.
     */
    private String grabStudentID() throws IOException {
        while(scan.hasNext()) {
            String line = scan.nextLine();
            if(line.startsWith("Student ID:"))
                return line.substring(line.indexOf(":")+2);
        }
        return "";
    }

    /*
     * Grabs the program the student is enrolled in.
     * 
     * Assume file has only one line with "Program: " followed by the program.
     */
    private String grabProgram() throws IOException {
        while(scan.hasNext()) {
            String line = scan.nextLine();
            if(line.startsWith("Program:"))
                return line.substring(line.indexOf(":")+2);
        }
        return "";
    }

    /*
     * Grabs the semester when the student was admitted.
     * 
     * The method will grab the data when the student was admitted and figure out the year and semester after.
     * 
     * Assume the method is ran right after TranscriptScanner.grabProgram
     * Assume the line after running TranscriptScanner.grabProgram starts with the admission date
     */
    private String grabSemesterAdmitted() throws IOException {
        String line = scan.nextLine();
        String year = line.substring(0, line.indexOf("-"));
        String month = line.substring(line.indexOf("-")+1, line.indexOf("-")+3);
        if(month.equals("01") || month.equals("02") || month.equals("03") || month.equals("04") || month.equals("05"))
            return year + " Spring";
        else if(month.equals("06") || month.equals("07"))
            return year + " Summer";
        else if(month.equals("08") || month.equals("09") || month.equals("10") || month.equals("11") || month.equals("12"))
            return year + " Fall";
        return "";
    }

    /*
     * Grabs the list of courses the studend has taken or is currently taking.
     * 
     * The method will run through the rest of the transcript and grab all the courses in the transcript, 
     *      creating a Course object for each one and adding it to the list.
     * 
     * Assume all course information will start after lie "Beginning of Graduate Record"
     * Assume any line that starts with "CS" indicates a course the student is taking or has taken.
     * Assume all courses will be in a line that starts with "CS"
     * Assume any line after "Beginning of Graduate Record" that starts with "20" indicates a new semester.
     * Assume all new semesters will be in a line that starts with "20"
     */
    private List<Course> grabStudentCourses() {
        List<Course> studentCourses = new ArrayList<>();

        while(scan.nextLine().equals("Beginning of Graduate Record")) {}

        String semester = "";
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            if(line.startsWith("20")) {
                semester = line;
            }
            if(line.startsWith("CS ") || line.startsWith("SE ")) {
                String[] lineAsSplit = line.split(" ");
                String courseNumber = lineAsSplit[0] + " " + lineAsSplit[1];
                String grade = "";
                if(lineAsSplit[lineAsSplit.length-2].charAt(0) != '0')
                    grade = lineAsSplit[lineAsSplit.length-2];
                String courseTitle = "";
                for(int i = 2; i < lineAsSplit.length-4; i++) {
                    courseTitle += lineAsSplit[i] + " ";
                }
                courseTitle = courseTitle.trim();
                
                Course course = new Course(courseNumber, semester, grade, courseTitle, false);
                studentCourses.add(course);
            }
        }
        return studentCourses;
    }
}
