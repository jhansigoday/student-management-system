import java.sql.*;
import java.util.Scanner;

public class StudentData {
    Connection conn;

    public StudentData() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "system", "jhansi");
            System.out.println("Database connection established.");
            addGPAColumn();
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    private void addGPAColumn() {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getColumns(null, null, "STUDENT1", "GPA");
            if (!rs.next()) {
                Statement stmt = conn.createStatement();
                stmt.execute("ALTER TABLE student1 ADD gpa NUMBER(4,2)");
                System.out.println("GPA column added to student1 table.");
            }
        } catch (Exception e) {
            System.out.println("Failed to add GPA column: " + e.getMessage());
        }
    }

    public void addStudent(int rollNo, String name, int age, String branch) {
        try {
            // Check for duplicate roll number
            PreparedStatement check = conn.prepareStatement("SELECT roll_no FROM student1 WHERE roll_no = ?");
            check.setInt(1, rollNo);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                System.out.println("Roll No already exists. Please enter a unique Roll No.");
                return;
            }

            // Proceed to insert
            PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO student1 (roll_no, name, age, branch) VALUES (?, ?, ?, ?)");
            pst.setInt(1, rollNo);
            pst.setString(2, name);
            pst.setInt(3, age);
            pst.setString(4, branch);
            pst.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (Exception e) {
            System.out.println("Add failed: " + e.getMessage());
        }
    }

    public void viewAllStudents() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM student1");
            while (rs.next()) {
                System.out.println("Roll No: " + rs.getInt("roll_no") +
                                   ", Name: " + rs.getString("name") +
                                   ", Age: " + rs.getInt("age") +
                                   ", Branch: " + rs.getString("branch") +
                                   ", GPA: " + rs.getFloat("gpa"));
            }
        } catch (Exception e) {
            System.out.println("View failed: " + e.getMessage());
        }
    }

    public void updateStudent(int rollNo, String name, int age) {
        try {
            PreparedStatement pst = conn.prepareStatement(
                "UPDATE student1 SET name=?, age=? WHERE roll_no=?");
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setInt(3, rollNo);
            int rows = pst.executeUpdate();
            if (rows > 0)
                System.out.println("Student updated.");
            else
                System.out.println("Roll No not found.");
        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    public void deleteStudent(int rollNo) {
        try {
            PreparedStatement pst = conn.prepareStatement("DELETE FROM student1 WHERE roll_no=?");
            pst.setInt(1, rollNo);
            int rows = pst.executeUpdate();
            if (rows > 0)
                System.out.println("Student deleted.");
            else
                System.out.println("Roll No not found.");
        } catch (Exception e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }

    public void calculateGPA(int rollNo, int[] marks) {
        try {
            int total = 0;
            for (int mark : marks)
                total += mark;
            float gpa = (float) total / marks.length;

            PreparedStatement pst = conn.prepareStatement("UPDATE student1 SET gpa=? WHERE roll_no=?");
            pst.setFloat(1, gpa);
            pst.setInt(2, rollNo);
            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("GPA calculated and updated: " + gpa);
            } else {
                System.out.println("Student not found.");
            }
        } catch (Exception e) {
            System.out.println("GPA update failed: " + e.getMessage());
        }
    }

    public void averageGPAByBranch() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Branch: ");
            String branch = sc.nextLine();

            PreparedStatement pst = conn.prepareStatement(
                "SELECT AVG(gpa) AS avg_gpa FROM student1 WHERE branch = ?");
            pst.setString(1, branch);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                float avgGpa = rs.getFloat("avg_gpa");
                System.out.printf("Average GPA for %s: %.2f%n", branch, avgGpa);
            } else {
                System.out.println("No students found in the specified branch.");
            }
        } catch (Exception e) {
            System.out.println("Average GPA calculation failed: " + e.getMessage());
        }
    }
}

