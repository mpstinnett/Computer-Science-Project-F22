public class Course {
    public String courseNumber;
    public String semester;
    public String grade;
    public String courseTitle;
    public boolean transfer;

    public Course() {};
    public Course(String courseNumber, String semester, String grade, String courseTitle, boolean transfer) {
        this.courseNumber = courseNumber;
        this.semester = semester;
        this.grade = grade;
        this.courseTitle = courseTitle;
        this.transfer = transfer;
    }
}