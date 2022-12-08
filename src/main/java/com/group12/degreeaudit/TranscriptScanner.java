package com.group12.degreeaudit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.JSONCourse;

/**
 * Description: TranscriptScanner - Scans a student transcript and produces a student object
 */
public class TranscriptScanner {
    private File transcriptFile;
    private Scanner scan;
    private CourseList courseList;

    public TranscriptScanner(String transcriptFilePath, CourseList courseList) {
        transcriptFile = new File(transcriptFilePath);
        this.courseList = courseList;
    }

    /**
     * Goes through whole transcript and assigns relevant data to Student and Course objects.
     * 
     * @return Student - returns student with all the information from the transcript
     */
    public Student scanTranscript() {
        try {
            scan = new Scanner(transcriptFile);

            //Fetch student information
            String studentName = grabStudentName();
            String studentID = grabStudentID();
            String program = grabProgram();
            String semesterAdmitted = grabSemesterAdmitted();
            //Resetting the scanner here to account for transfer students
            scan.close();
            scan = new Scanner(transcriptFile);
            List<Course> studentCourses = grabStudentCourses();

            return new Student(studentName, studentID, program, semesterAdmitted, studentCourses);
        } catch (Exception e) {
            System.out.println("EXCEPTION ERROR! " + e);
        }
        return null;
    }

    /**
     * Grabs student name.
     * 
     * Assume file has only one line with "Name: " followed by the student name.
     * 
     * @return String - returns student name
     * @throws IOException - if scanner reads null
     */
    private String grabStudentName() throws IOException {
        while(scan.hasNext()) {
            String line = scan.nextLine();
            if(line.startsWith("Name:"))
                return line.substring(line.indexOf(":")+2);
        }
        return "";
    }

    /**
     * Grabs student ID number.
     * 
     * Assume file has only one line with "Student ID: " followed by the student ID number.
     * 
     * @return String - returns student ID number
     * @throws IOException - if scanner reads null
     */
    private String grabStudentID() throws IOException {
        while(scan.hasNext()) {
            String line = scan.nextLine();
            if(line.startsWith("Student ID:"))
                return line.substring(line.indexOf(":")+2);
        }
        return "";
    }

    /**
     * Grabs the program the student is enrolled in.
     * 
     * Assume file has only one line with "Program: " followed by the program.
     * 
     * @return String - returns student program
     * @throws IOException - if scanner reads null
     */
    private String grabProgram() throws IOException {
        while(scan.hasNext()) {
            String line = scan.nextLine();
            if(line.startsWith("Program: Master"))
                return line.substring(line.indexOf(":")+2);
        }
        return "";
    }

    /**
     * Grabs the semester when the student was admitted.
     * 
     * The method will grab the data when the student was admitted and figure out the year and semester after.
     * 
     * Assume the method is ran right after TranscriptScanner.grabProgram
     * Assume the line after running TranscriptScanner.grabProgram starts with the admission date
     * 
     * @return String - returns student's semester admitted into program
     * @throws IOException - if scanner reads null
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

    /**
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
     * 
     * @return List of Courses - returns the list of courses that the student has taken from the transcript
     */
    private List<Course> grabStudentCourses() {
        List<Course> studentCourses = new ArrayList<>();
        boolean isTransfered = true;
        boolean locatedCourses = false;
        String semester = "";
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            if (locatedCourses) {
                if(line.startsWith("20")) {
                    semester = line;
                }
                if(line.startsWith("CS ") || 
                    line.startsWith("SE ") ||
                    line.startsWith("ACCT ") ||
                    line.startsWith("ACN ") ||
                    line.startsWith("ACTS ") ||
                    line.startsWith("AHST ") ||
                    line.startsWith("ARHM ") ||
                    line.startsWith("ATCM ") ||
                    line.startsWith("AUD ") ||
                    line.startsWith("BIOL ") ||
                    line.startsWith("BMEN ") ||
                    line.startsWith("BPS ") ||
                    line.startsWith("BUAN ") ||
                    line.startsWith("CE ") ||
                    line.startsWith("CHEM ") ||
                    line.startsWith("COMD ") ||
                    line.startsWith("CRIM ") ||
                    line.startsWith("ECON ") ||
                    line.startsWith("ECSC ") ||
                    line.startsWith("ED ") ||
                    line.startsWith("EEBM ") ||
                    line.startsWith("EECS ") ||
                    line.startsWith("EECT ") ||
                    line.startsWith("EEDG ") ||
                    line.startsWith("EEGR ") ||
                    line.startsWith("EEMF ") ||
                    line.startsWith("EEOP ") ||
                    line.startsWith("EEPE ") ||
                    line.startsWith("EERF ") ||
                    line.startsWith("EESC ") ||
                    line.startsWith("ENGY ") ||
                    line.startsWith("ENTP ") ||
                    line.startsWith("EPPS ") ||
                    line.startsWith("FIN ") ||
                    line.startsWith("FTEC ") ||
                    line.startsWith("GEOS ") ||
                    line.startsWith("GISC ") ||
                    line.startsWith("HCS ") ||
                    line.startsWith("HDCD ") ||
                    line.startsWith("HIST ") ||
                    line.startsWith("HMGT ") ||
                    line.startsWith("HUAS ") ||
                    line.startsWith("HUHI ") ||
                    line.startsWith("HUMA ") ||
                    line.startsWith("IDEA ") ||
                    line.startsWith("IMS ") ||
                    line.startsWith("IPEC ") ||
                    line.startsWith("LATS ") ||
                    line.startsWith("LIT ") ||
                    line.startsWith("MAIS ") ||
                    line.startsWith("MAS ") ||
                    line.startsWith("MATH ") ||
                    line.startsWith("MECH ") ||
                    line.startsWith("MECO ") ||
                    line.startsWith("MIS ") ||
                    line.startsWith("MKT ") ||
                    line.startsWith("MSEN ") ||
                    line.startsWith("MTHE ") ||
                    line.startsWith("OB ") ||
                    line.startsWith("OPRE ") ||
                    line.startsWith("PA ") ||
                    line.startsWith("PHIL ") ||
                    line.startsWith("PHYS ") ||
                    line.startsWith("PPPE ") ||
                    line.startsWith("PSCI ") ||
                    line.startsWith("PSYC ") ||
                    line.startsWith("SCI ") ||
                    line.startsWith("SE ") ||
                    line.startsWith("SMED ") ||
                    line.startsWith("SOC ") ||
                    line.startsWith("STAT ") ||
                    line.startsWith("SYSE ") ||
                    line.startsWith("SYSM ") ||
                    line.startsWith("TE ") ||
                    line.startsWith("VPAS ")) 
                {
                    String[] lineAsSplit = line.split(" ");
                    String courseNumber = lineAsSplit[0] + " " + lineAsSplit[1];
                    String grade = "";
                    String creditHours = "";
                    if(lineAsSplit[1].charAt(1) == 'V' || lineAsSplit[1].charAt(1) == 'v')
                    {
                        creditHours = lineAsSplit[lineAsSplit.length-3];
                    }

                    if(lineAsSplit[lineAsSplit.length-2].charAt(0) != '0') {
                        grade = lineAsSplit[lineAsSplit.length-2];

                        if(lineAsSplit[1].charAt(1) == 'V' || lineAsSplit[1].charAt(1) == 'v')
                        {
                            creditHours = lineAsSplit[lineAsSplit.length-4];
                        }

                    }

                    String courseTitle = "";

                    for(int i = 2; i < lineAsSplit.length-4; i++) 
                    {
                        courseTitle += lineAsSplit[i] + " ";
                    }

                    courseTitle = courseTitle.trim();

                    Course course;
                    if(creditHours.equals(""))
                    {
                        course = new Course(courseNumber, semester, grade, courseTitle, isTransfered);
                    }
                    else
                    {
                        course = new Course(courseNumber, semester, grade, courseTitle, isTransfered, Double.parseDouble(creditHours));
                    }
                    course.setClassType(getCourseType(course));
                    studentCourses.add(course);
                }
            } else {
                locatedCourses = line.equals("Beginning of Graduate Record") || line.equals("Applied Toward Master Program");
            }
            if (line.equals("Beginning of Graduate Record")) {
                isTransfered = false;
            }
        }
        return studentCourses;
    }

    /**
     * Given a course, return the course type as a letter.
     * 
     * 'C' means core course. 'E' means elective course. 'A' means admission prerequisite course.
     * If the course meets no type, return 'U'.
     * 
     * @param course as course - a course
     * @return char - returns the course type as a letter
     */
    private char getCourseType(Course course)
    {
        for(JSONCourse courseL : courseList.GetCourseList())
        {
            if(courseL.getCourseNumber().equals(course.getCourseNumber()))
            {
                return courseL.getClassType();
            }
        }
        return 'U';
    }
}
