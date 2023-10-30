public class TestStudent {
    public static void main(String[] args) {
        //Create Array
        Student[] students = {
            new Student("Miles", 3.7),
            new Student("Keegan", 2.2),
            new Student("Myla", 1.1),
            new Student("Amy", 3.8)
        };

        //List Prior
        System.out.println("Before sorting : ");
        for (Student student : students) {
            System.out.println(student);
        }
        //Sorting Students
        MySelectionSort.sort(students);

        System.out.println("\nAfter sorting : ");
        for (Student student : students) {
            System.out.println(student);
        }
    }
}