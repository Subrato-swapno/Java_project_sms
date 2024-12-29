import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    private int id;
    private String name;
    private int age;
    private String scienceGrade;
    private String banglaGrade;
    private String englishGrade;
    private String mathGrade;
    private double totalGrade;
    public Student(int id, String name, int age, String scienceGrade, String banglaGrade, String englishGrade, String mathGrade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.scienceGrade = scienceGrade;
        this.banglaGrade = banglaGrade;
        this.englishGrade = englishGrade;
        this.mathGrade = mathGrade;
        this.totalGrade = calculateTotalGrade();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getScienceGrade() {
        return scienceGrade;
    }

    public String getBanglaGrade() {
        return banglaGrade;
    }

    public String getEnglishGrade() {
        return englishGrade;
    }

    public String getMathGrade() {
        return mathGrade;
    }

    public double getTotalGrade() {
        return totalGrade;
    }
    private double calculateTotalGrade() {
        int totalPoints = gradeToPoint(scienceGrade) + gradeToPoint(banglaGrade)
                + gradeToPoint(englishGrade) + gradeToPoint(mathGrade);
        return totalPoints / 4.0;
    }
    private int gradeToPoint(String grade) {
        switch (grade.toUpperCase()) {
            case "A": return 4;
            case "B": return 3;
            case "C": return 2;
            case "D": return 1;
            case "F": return 0;
            default: return 0;
        }
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + age + " | " + totalGrade;
    }
}

public class StudentManagementGUI extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private DefaultListModel<String> studentListModel = new DefaultListModel<>();
    private JTable studentTable;
    private Object[][] studentData;
    public StudentManagementGUI() {
        showLoginScreen();
    }
    private void showLoginScreen() {
        JPanel loginPanel = new JPanel(new GridLayout(2, 2));
        JPasswordField passwordField = new JPasswordField();
        loginPanel.add(new JLabel("Enter Password:"));
        loginPanel.add(passwordField);
        int option = JOptionPane.showConfirmDialog(
                this,
                loginPanel,
                "Admin Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());

            if (isValidPassword(password)) {
                loadInitialStudents();
                showMainApp();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid password. Application will close.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }
    private boolean isValidPassword(String password) {
        return password.equals("1234");
    }
    private void loadInitialStudents() {
        students.add(new Student(1, "Seam Ahmed", 20, "A", "B", "A", "C"));
        students.add(new Student(2, "Moshiur Rahman", 22, "B", "C", "B", "A"));
        students.add(new Student(3, "Adnan Hossain", 21, "A", "A", "A", "B"));
        students.add(new Student(4, "Sadi Khan", 23, "C", "B", "C", "A"));
        students.add(new Student(5, "Sayem Uddin", 20, "B", "B", "A", "B"));
        students.add(new Student(6, "Tasnim Akter", 22, "A", "A", "B", "C"));
        students.add(new Student(7, "Dalia Akter", 21, "B", "A", "A", "B"));
        students.add(new Student(8, "Elaf Ahmed", 20, "C", "A", "B", "C"));
        students.add(new Student(9, "Ahona Talider", 22, "A", "B", "C", "B"));
        students.add(new Student(10, "Oishy Jannat Rayka", 21, "B", "C", "B", "A"));
        students.add(new Student(11, "Misu Musu", 23, "A", "B", "A", "B"));
        students.add(new Student(12, "Tajbe Jaman", 20, "A", "C", "B", "B"));
        students.add(new Student(13, "Munneima Parvez Jahin", 22, "B", "A", "B", "A"));
        students.add(new Student(14, "Kamnur Nahar", 21, "A", "B", "A", "C"));
        students.add(new Student(15, "Choity Rahman", 20, "C", "B", "A", "B"));
        students.add(new Student(16, "Sadia Moni", 22, "B", "C", "B", "A"));
        students.add(new Student(17, "Subrota Debnath", 21, "A", "A", "B", "B"));
        students.add(new Student(18, "Tanveer Islam", 20, "B", "A", "C", "B"));
        for (Student student : students) {
            studentListModel.addElement(student.getName() + " (ID: " + student.getId() + ")");
        }
        studentData = new Object[students.size()][4];
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            studentData[i][0] = student.getId();
            studentData[i][1] = student.getName();
            studentData[i][2] = student.getAge();
            studentData[i][3] = student.getTotalGrade();
        }
    }
    private void showMainApp() {
        setTitle("Student Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        String[] columnNames = {"ID", "Name", "Age", "Average Grade"};
        studentTable = new JTable(studentData, columnNames);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        JButton addButton = new JButton("Add Student");
        JButton deleteButton = new JButton("Delete Student");
        JButton viewButton = new JButton("View Details");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        addButton.addActionListener(e -> addStudent());
        deleteButton.addActionListener(e -> deleteStudent(studentTable.getSelectedRow()));
        viewButton.addActionListener(e -> viewStudentDetails(studentTable.getSelectedRow()));
        setVisible(true);
    }
    private void addStudent() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField scienceField = new JTextField();
        JTextField banglaField = new JTextField();
        JTextField englishField = new JTextField();
        JTextField mathField = new JTextField();
        Object[] inputFields = {
                "ID:", idField,
                "Name:", nameField,
                "Age:", ageField,
                "Science Grade:", scienceField,
                "Bangla Grade:", banglaField,
                "English Grade:", englishField,
                "Math Grade:", mathField
        };
        int option = JOptionPane.showConfirmDialog(this, inputFields, "Add New Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String scienceGrade = scienceField.getText();
                String banglaGrade = banglaField.getText();
                String englishGrade = englishField.getText();
                String mathGrade = mathField.getText();
                Student newStudent = new Student(id, name, age, scienceGrade, banglaGrade, englishGrade, mathGrade);
                students.add(newStudent);
                studentListModel.addElement(newStudent.getName() + " (ID: " + newStudent.getId() + ")");
                updateTableData();
                JOptionPane.showMessageDialog(this, "Student added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers for ID and Age.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void updateTableData() {
        studentData = new Object[students.size()][4];
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            studentData[i][0] = student.getId();
            studentData[i][1] = student.getName();
            studentData[i][2] = student.getAge();
            studentData[i][3] = student.getTotalGrade();
        }
        studentTable.setModel(new DefaultTableModel(studentData, new String[]{"ID", "Name", "Age", "Average Grade"}));
    }
    private void deleteStudent(int selectedRow) {
        if (selectedRow >= 0) {
            students.remove(selectedRow);
            updateTableData();
            JOptionPane.showMessageDialog(this, "Student deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
        }
    }
    private void viewStudentDetails(int selectedRow) {
        if (selectedRow >= 0) {
            Student student = students.get(selectedRow);
            String details = "ID: " + student.getId() + "\n" +
                    "Name: " + student.getName() + "\n" +
                    "Age: " + student.getAge() + "\n" +
                    "Science Grade: " + student.getScienceGrade() + "\n" +
                    "Bangla Grade: " + student.getBanglaGrade() + "\n" +
                    "English Grade: " + student.getEnglishGrade() + "\n" +
                    "Math Grade: " + student.getMathGrade() + "\n" +
                    "Average Grade: " + student.getTotalGrade();

            JOptionPane.showMessageDialog(this, details, "Student Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to view details.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementGUI::new);
    }
}
