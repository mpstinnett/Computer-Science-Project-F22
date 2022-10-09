import java.util.ArrayList;
import java.util.List;

public class Student {
    public String name;
    public String ID;
    public String program;
    public String semesterAdmitted;
    public List<Course> courses = new ArrayList<Course>();

    public Student() {

    }
    public Student(String name, String ID, String program, String semesterAdmitted, List<Course> courses) {
        this.name = name;
        this.ID = ID;
        this.program = program;
        this.semesterAdmitted = semesterAdmitted;
        this.courses = courses;
    }
}