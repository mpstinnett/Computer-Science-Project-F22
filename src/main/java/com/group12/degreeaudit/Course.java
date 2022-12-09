package com.group12.degreeaudit;

/**
 * Description: Course - Houses all methods for course operations for student courses taken or taking
 */
public class Course implements Comparable<Course> {
    private String courseNumber;
    private String semester;
    private String grade;
    private String courseTitle;
    private boolean transfer;
    private char classType;
    private double gradePoints;
    private double creditHours;
    private boolean waiver = false;

    /** Description: Course default constructor - Does nothing
    */
    public Course() {}

    /**
     * Description: Course Constructor - generates a new course a student takes
     * @param   courseNumber    String for the course number
     * @param   semester    String for the semester
     * @param   grade    String for the grade
     * @param   courseTitle    String array for the course prerequisites
     * @param   transfer    Boolean for if the course was a transfer course
    */
    public Course(String courseNumber, String semester, String grade, String courseTitle, boolean transfer) 
    {
        this.courseNumber = courseNumber;
        this.semester = semester;
        this.grade = grade;
        this.courseTitle = courseTitle;
        this.transfer = transfer;
        this.creditHours = 0;
        updateGradePoints();
    }

    /**
     * Description: Course Constructor - generates a new course a student takes with information about credit hours for transfer credit
     * @param   courseNumber    String for the course number
     * @param   semester    String for the semester
     * @param   grade    String for the grade
     * @param   courseTitle    String array for the course prerequisites
     * @param   transfer    Boolean for if the course was a transfer course
     * @param   creditHours    Double for credit hours for a course
    */
    public Course(String courseNumber, String semester, String grade, String courseTitle, boolean transfer, double creditHours) {
        this.courseNumber = courseNumber;
        this.semester = semester;
        this.grade = grade;
        this.courseTitle = courseTitle;
        this.transfer = transfer;
        this.creditHours = creditHours;
        updateGradePoints();
    }

    /**
     * Description: Course Constructor - generates a new course a student takes with information about credit hours for transfer credit
     * @param   courseNumber    String for the course number
     * @param   semester    String for the semester
     * @param   grade    String for the grade
     * @param   courseTitle    String array for the course prerequisites
     * @param   transfer    Boolean for if the course was a transfer course
     * @param   creditHours    Double for credit hours for a course
     * @param   waiver  If the coruse is waived (Admissions)
    */
    public Course(String courseNumber, String semester, String grade, String courseTitle, boolean transfer, double creditHours, boolean waiver) {
        this.courseNumber = courseNumber;
        this.semester = semester;
        this.grade = grade;
        this.courseTitle = courseTitle;
        this.transfer = transfer;
        this.creditHours = creditHours;
        this.waiver = waiver;
        updateGradePoints();
    }

    /**
     * Description: Course Constructor - generates a new course a student takes with information about credit hours for transfer credit and class type information
     * @param   courseNumber    String for the course number
     * @param   semester    String for the semester
     * @param   grade    String for the grade
     * @param   courseTitle    String array for the course prerequisites
     * @param   transfer    Boolean for if the course was a transfer course
     * @param   creditHours    Double for credit hours for a course
     * @param   classType     Character for the class type (A, C, E)
    */
    public Course(String courseNumber, String semester, String grade, String courseTitle, boolean transfer, double creditHours, char classType) {
        this.courseNumber = courseNumber;
        this.semester = semester;
        this.grade = grade;
        this.courseTitle = courseTitle;
        this.transfer = transfer;
        this.creditHours = creditHours;
        this.classType = classType;
        updateGradePoints();
    }

    /**
     * Description: getCourseNumber - getter for course number
     * @return  String    courseNumber that is from the course
    */
    public String getCourseNumber() {
        return courseNumber;
    }

    /**
     * Description: setCourseNumber - setter for course number
     * @param   courseNumber     String for the course number
    */
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    /**
     * Description: getSemester - getter for course semester
     * @return  String    semester that is from the course
    */
    public String getSemester() {
        return semester;
    }

    /**
     * Description: setSemester - setter for course semester
     * @param   semester     String for course semester
    */
    public void setSemester(String semester) {
        this.semester = semester;
    }

    /**
     * Description: getGrade - getter for course grade
     * @return  String    grade that is from the course
    */
    public String getGrade() {
        return grade;
    }

    /**
     * Description: setGrade - setter for course grade
     * @param   grade     String for course grade
    */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Description: getGrade - getter for course title
     * @return  String    course title that is from the course
    */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * Description: setCourseTitle - setter for course title
     * @param   courseTitle     String for course title
    */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * Description: getTransfer - getter for if a course was transfered or not
     * @return  Boolean   transfer status that is from the course
    */
    public boolean getTransfer() {
        return transfer;
    }

    /**
     * Description: setTransfer - setter for if a course was transfered or not
     * @param   transfer     Boolean to indicate if a course was transfered or not
    */
    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    /**
     * Description: getTransfer - getter for class type
     * @return  char    Character for the class type (A, C, E) of the course
    */
    public char getClassType() {
        return classType;
    }

    /**
     * Description: setClassType - setter for class type
     * @param   classType     Character for the class type (A, C, E)
    */
    public void setClassType(char classType) {
        this.classType = classType;
    }

    /**
     * Description: compareTo - used to check if one course has a higher grade than another
     * @param   c    course that is being checked
     * @return  int  0 if grade is equal, negative if grade is less than, positive if grade is greater than
    */
    @Override
    public int compareTo(Course c)
    {
        return Integer.compare(getGradeHeirarchy(), c.getGradeHeirarchy());
    }

    /**
     * Description: getGradePoints - getter for grade points
     * @return  double   grade points for the course
    */
    public double getGradePoints()
    {
        return gradePoints;
    }

    /**
     * Description: updateGradePoints - dynamically returns the amount of points based on the grade recieved
    */
    private void updateGradePoints()
    {
        double credits = getCredits();

        switch (getGrade()) {
            case "A+" : gradePoints = 4.00 * credits;
                        break;
            case "A"  : gradePoints = 4.00 * credits;
                        break;
            case "A-" : gradePoints = 3.67 * credits;
                        break;
            case "B+" : gradePoints = 3.33 * credits;
                        break;
            case "B"  : gradePoints = 3.00 * credits;
                        break;
            case "B-" : gradePoints = 2.67 * credits;
                        break;
            case "C+" : gradePoints = 2.33 * credits;
                        break;
            case "C"  : gradePoints = 2.00 * credits;
                        break;
            case "C-" : gradePoints = 1.67 * credits;
                        break;
            case "D+" : gradePoints = 1.33 * credits;
                        break;
            case "D"  : gradePoints = 1.00 * credits;
                        break;
            case "D-" : gradePoints = 0.67 * credits;
                        break;
            case "F"  : gradePoints = 0.00 * credits;
                        break;
            default : gradePoints = 0.00 * credits;
                        break;
        } 
    }

    /**
     * Description: getGradeHeirarchy - dynamically returns the grade hierarchy based on the letter grade
     * @return   int    grade hierarchy for a course based on letter grade
    */
    private int getGradeHeirarchy()
    {
        switch (getGrade()) {
            case "A+" : return 1;
            case "A"  : return 2;
            case "A-" : return 3;
            case "B+" : return 4;
            case "B"  : return 5;
            case "B-" : return 6;
            case "C+" : return 7;
            case "C"  : return 8;
            case "C-" : return 9;
            case "D+" : return 10;
            case "D"  : return 11;
            case "D-" : return 12;
            case "F"  : return 13;
            default : return 14;
        } 
    }
    
    /**
     * Description: setCreditHours - setter for credit hours
     * @param   creditHours     Double value for credit hours for transfer credit
    */
    public void setCreditHours(double creditHours) {
        this.creditHours = creditHours;
    }

    /**
     * Description: getCredits - getter for credits that are taken
     * @return   Double    creditHours total that are for the course bassed on the course number
    */
    public double getCredits() 
    {
        if(creditHours == 0)
        {
            return Double.parseDouble(Character.toString(getCourseNumber().split(" ")[1].charAt(1)));
            //return Integer.parseInt(courseNumber.substring(courseNumber.length()-3, courseNumber.length()-2));
        }
        return creditHours;
    }

    /**
    * Description: setWaiver - setter for waiver type 
    * @param waiver    boolean for if the class is waived
    */
    public void setWaiver(boolean waiver){
        this.waiver = waiver;
    }

    /**
    * Description: getWaiver - getter for waiver type
    * @return    boolean for if the class is waived
    */
    public boolean getWaiver(){
        return waiver;
    }

    /**
     * Description: toString - puts all information about a course into a string
     * @return   String    all information about a course in string format
    */
    public String toString() {
        return "\tNumber: " + courseNumber
               + "\n\tTitle: " + courseTitle
               + "\n\tGrade: " + grade
               + "\n\tSemester: " + semester
               + "\n\tTransfer: " + transfer
               + "\n\tCredit Hours: " + getCredits();
    }
    

}