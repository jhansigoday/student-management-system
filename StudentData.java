import java.sql.*;

public class StudentData {
    Connection conn;

    public StudentData() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "system", "jhansi");
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public void addStudent(int rollNo, String name, int age) {
        try {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO studentt VALUES (?, ?, ?, NULL)");
            pst.setInt(1, rollNo);
            pst.setString(2, name);
            pst.setInt(3, age);
            pst.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (Exception e) {
            System.out.println("Add failed: " + e.getMessage());
        }
    }

    public void viewAllStudents() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM studentt");
            while (rs.next()) {
                System.out.println("Roll No: " + rs.getInt(1) + ", Name: " + rs.getString(2) +
                                   ", Age: " + rs.getInt(3) + ", GPA: " + rs.getFloat(4));
            }
        } catch (Exception e) {
            System.out.println("View failed: " + e.getMessage());
        }
    }

    public void updateStudent(int rollNo, String name, int age) {
        try {
            PreparedStatement pst = conn.prepareStatement(
                "UPDATE studentt SET name=?, age=? WHERE roll_no=?");
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
            PreparedStatement pst = conn.prepareStatement("DELETE FROM studentt WHERE roll_no=?");
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

            PreparedStatement pst = conn.prepareStatement("UPDATE studentt SET gpa=? WHERE roll_no=?");
            pst.setFloat(1, gpa);
            pst.setInt(2, rollNo);
            pst.executeUpdate();
            System.out.println("GPA calculated and updated: " + gpa);
        } catch (Exception e) {
            System.out.println("GPA update failed: " + e.getMessage());
        }
    }
}
