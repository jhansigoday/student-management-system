import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {

    static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Change if needed
    static final String DB_USER = "your_username"; // Your Oracle username
    static final String DB_PASS = "your_password"; // Your Oracle password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            Scanner sc = new Scanner(System.in);
            StudentManagementSystem sms = new StudentManagementSystem();

            while (true) {
                System.out.println("\n--- Student Management Menu ---");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Calculate GPA of a Student");
                System.out.println("6. Average GPA by Branch");
                System.out.println("7. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> sms.addStudent(conn);
                    case 2 -> sms.viewStudents(conn);
                    case 3 -> sms.updateStudent(conn);
                    case 4 -> sms.deleteStudent(conn);
                    case 5 -> {
                        System.out.print("Enter Roll No: ");
                        int roll = sc.nextInt();
                        sms.calculateGPA(conn, roll);
                    }
                    case 6 -> sms.calculateAverageGPAByBranch(conn);
                    case 7 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addStudent(Connection conn) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Roll No: ");
        int rollNo = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Department: ");
        String department = sc.nextLine();
        System.out.print("Enter Branch: ");
        String branch = sc.nextLine();
        System.out.print("Enter Number of Subjects: ");
        int numSubjects = sc.nextInt();

        double totalMarks = 0;
        for (int i = 1; i <= numSubjects; i++) {
            System.out.print("Enter marks for subject " + i + ": ");
            totalMarks += sc.nextDouble();
        }

        String sql = "INSERT INTO studentt (roll_no, name, age, department, branch, num_subjects, total_marks) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, rollNo);
        ps.setString(2, name);
        ps.setInt(3, age);
        ps.setString(4, department);
        ps.setString(5, branch);
        ps.setInt(6, numSubjects);
        ps.setDouble(7, totalMarks);

        int rowsInserted = ps.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Student added successfully.");
        }
    }

    public void viewStudents(Connection conn) throws SQLException {
        String sql = "SELECT * FROM studentt";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("Student Records:");
        while (rs.next()) {
            System.out.println("Roll No: " + rs.getInt("roll_no"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Age: " + rs.getInt("age"));
            System.out.println("Department: " + rs.getString("department"));
            System.out.println("Branch: " + rs.getString("branch"));
            System.out.println("Subjects: " + rs.getInt("num_subjects"));
            System.out.println("Total Marks: " + rs.getDouble("total_marks"));
            System.out.println("--------------------------------------");
        }
    }

    public void updateStudent(Connection conn) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Roll No of student to update: ");
        int rollNo = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new Name: ");
        String name = sc.nextLine();
        System.out.print("Enter new Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new Department: ");
        String department = sc.nextLine();
        System.out.print("Enter new Branch: ");
        String branch = sc.nextLine();
        System.out.print("Enter new Number of Subjects: ");
        int numSubjects = sc.nextInt();

        double totalMarks = 0;
        for (int i = 1; i <= numSubjects; i++) {
            System.out.print("Enter marks for subject " + i + ": ");
            totalMarks += sc.nextDouble();
        }

        String sql = "UPDATE studentt SET name=?, age=?, department=?, branch=?, num_subjects=?, total_marks=? WHERE roll_no=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setString(3, department);
        ps.setString(4, branch);
        ps.setInt(5, numSubjects);
        ps.setDouble(6, totalMarks);
        ps.setInt(7, rollNo);

        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void deleteStudent(Connection conn) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Roll No of student to delete: ");
        int rollNo = sc.nextInt();

        String sql = "DELETE FROM studentt WHERE roll_no=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, rollNo);

        int rowsDeleted = ps.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void calculateGPA(Connection conn, int rollNo) throws SQLException {
        String sql = "SELECT total_marks, num_subjects FROM studentt WHERE roll_no=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, rollNo);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            double total = rs.getDouble("total_marks");
            int subjects = rs.getInt("num_subjects");
            if (subjects == 0) {
                System.out.println("Invalid subject count.");
                return;
            }
            double gpa = total / (subjects * 10.0);
            System.out.printf("GPA for Roll No %d is: %.2f%n", rollNo, gpa);
        } else {
            System.out.println("Student not found.");
        }
    }

    public void calculateAverageGPAByBranch(Connection conn) throws SQLException {
        String sql = "SELECT branch, AVG(total_marks / (num_subjects * 10)) AS avg_gpa FROM studentt GROUP BY branch";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\nAverage GPA by Branch:");
        while (rs.next()) {
            String branch = rs.getString("branch");
            double avgGpa = rs.getDouble("avg_gpa");
            System.out.printf("Branch: %s, Avg GPA: %.2f%n", branch, avgGpa);
        }
    }
}
