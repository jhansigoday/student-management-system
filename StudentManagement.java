import java.util.Scanner;

public class StudentManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentData sd = new StudentData();

        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Calculate GPA");
            System.out.println("6. Average GPA by Branch");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Roll No: ");
                    int rollNo = sc.nextInt();
                    sc.nextLine(); // Clear buffer
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine(); // Clear buffer
                    System.out.print("Enter Branch: ");
                    String branch = sc.nextLine();
                    sd.addStudent(rollNo, name, age, branch);
                    break;

                case 2:
                    sd.viewAllStudents();
                    break;

                case 3:
                    System.out.print("Enter Roll No to update: ");
                    rollNo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new Name: ");
                    name = sc.nextLine();
                    System.out.print("Enter new Age: ");
                    age = sc.nextInt();
                    sd.updateStudent(rollNo, name, age);
                    break;

                case 4:
                    System.out.print("Enter Roll No to delete: ");
                    rollNo = sc.nextInt();
                    sd.deleteStudent(rollNo);
                    break;

                case 5:
                    System.out.print("Enter Roll No to calculate GPA: ");
                    rollNo = sc.nextInt();
                    System.out.print("Enter number of subjects: ");
                    int n = sc.nextInt();
                    int[] marks = new int[n];
                    for (int i = 0; i < n; i++) {
                        System.out.print("Enter marks for subject " + (i + 1) + ": ");
                        marks[i] = sc.nextInt();
                    }
                    sd.calculateGPA(rollNo, marks);
                    break;

                case 6:
                    sd.averageGPAByBranch();
                    break;

                case 7:
                    System.out.println("Exiting Student Management System. Goodbye!");
                    return;

                default:
                    System.out.println("[ERROR] Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }
}

