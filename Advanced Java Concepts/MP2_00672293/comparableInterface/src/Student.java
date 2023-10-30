import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Student implements Comparable<Student> {

    //Pulls Random number
    private static final Random RANDOM = new Random();
    //Checks for Dupes
    private static final Set<Integer> assignedIDs = new HashSet<>();


    // Stores the students id, name, and gpa
    private int studentID;
    private String name;
    private double gpa;

    // Constructor to create a new student with the given name and GPA.
    public Student(String name, double gpa) {
        do {
            this.studentID = RANDOM.nextInt(10) + 1; // random number between 1 and 10
        } while (!assignedIDs.add(this.studentID)); // Continue generating until a unique ID 
        this.name = name;
        this.gpa = gpa;
    }
    //Getter ID
    public int getStudentID() {
        return studentID;
    }
    //Getter Name
    public String getName() {
        return name;
    }
    //Getter gpa
    public double getGpa() {
        return gpa;
    }
    //Setter Name
    public void setName(String name) {
        this.name = name;
    }
    //Setter gpa
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

     @Override
    public int compareTo(Student other) {
        return Integer.compare(this.studentID, other.studentID);
    }

    @Override
    public String toString() {
        return "ID: " + studentID + ", Name: " + name + ", GPA: " + gpa;
    }
}