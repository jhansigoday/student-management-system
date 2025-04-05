public class StudentData {
    private int rollNo;
    private String name;
    private int age;
    private String department;
    private String branch;
    private int numSubjects;
    private double totalMarks;

    public StudentData(int rollNo, String name, int age, String department, String branch,
                       int numSubjects, double totalMarks) {
        this.rollNo = rollNo;
        this.name = name;
        this.age = age;
        this.department = department;
        this.branch = branch;
        this.numSubjects = numSubjects;
        this.totalMarks = totalMarks;
    }

    // Getters and setters
    public int getRollNo() { return rollNo; }
    public void setRollNo(int rollNo) { this.rollNo = rollNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public int getNumSubjects() { return numSubjects; }
    public void setNumSubjects(int numSubjects) { this.numSubjects = numSubjects; }

    public double getTotalMarks() { return totalMarks; }
    public void setTotalMarks(double totalMarks) { this.totalMarks = totalMarks; }
}
