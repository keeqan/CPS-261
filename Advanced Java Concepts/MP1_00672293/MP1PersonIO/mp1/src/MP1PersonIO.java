
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Person implements Serializable{
private String name;
private int age;

public Person(String name, int age) {
	this.name = name;
	this.age = age;
} 


public String getName() {
	return name;
}

public int getAge() {
	return age; 
}

public String toString() {
	return "Name: " + name + ", Age: " + age; 
}


}

public class MP1PersonIO {
	String fileName;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	static Scanner kbInput = new Scanner(System.in);
	List<Person> personList = new ArrayList<>();

	public MP1PersonIO(String fileName) {
		this.fileName = fileName;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fileName, true));
			ois = new ObjectInputStream(new FileInputStream(fileName));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void add() {
		System.out.println("Enter The Name of the Person: ");
		String name = kbInput.nextLine();
		System.out.println("Enter the Age of the Person: ");
		int age = kbInput.nextInt();
		kbInput.nextLine();

		Person person = new Person(name, age);
		personList.add(person);

		try {
			
				oos.writeObject(person);
		}
		catch (IOException e) {
			e.printStackTrace();
		}



	}

	public void display() {
		System.out.println("People in the list:");
        for (Person person : personList) {
            System.out.println(person);
		}

	}

	public static void main(String[] args)  {
		MP1PersonIO mp1 = new MP1PersonIO("person.ser");
		try {
			int option = -1;
			while (option != 0) {
				System.out.println("Please choose an option:");
				System.out.println("0: quit");
				System.out.println("1: add");
				System.out.println("2: display");
				option = kbInput.nextInt();
				kbInput.nextLine();
				switch (option) {
				case 0:
					System.out.println("Bye");
					break;					
				case 1:
					mp1.add();
					break;
				case 2:
					mp1.display();
					break;
				}

			}
		} finally {
			try {
                if (mp1.oos != null) {
                    mp1.oos.close();
                }
                if (mp1.ois != null) {
                    mp1.ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
		}

	}

}