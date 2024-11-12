import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class EmployeeManagementGUI 
{
    static String USER_ID = "kinshuk2903";
    static String PASSWORD = "kinshuk095";

    public static void main(String[] args) 
    {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(500, 500);
        loginFrame.setLayout(null); 

        JLabel userLabel = new JLabel("User ID:");
        JTextField userField = new JTextField();
        userLabel.setBounds(40, 40, 100, 30); 
        userField.setBounds(150, 40, 100, 30); 

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        passwordLabel.setBounds(40, 80, 100, 30); 
        passwordField.setBounds(150, 80, 100, 30);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 120, 100, 30);
        loginButton.addActionListener(e -> 
        {
            String userId = userField.getText();
            String password = new String(passwordField.getPassword());

            if (USER_ID.equals(userId) && PASSWORD.equals(password)) 
            {
                loginFrame.dispose();
                showMainWindow();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        loginFrame.add(userLabel);
        loginFrame.add(userField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }
    public static void showMainWindow() 
    {
        JFrame frame = new JFrame("Employee Management System");
        frame.setSize(700, 500);
        frame.setLayout(new GridLayout(3, 1));

        JLabel display = new JLabel("Welcome to Employee Management System", SwingConstants.CENTER);
        display.setFont(new Font("Times New Roman", Font.BOLD, 30));
        display.setForeground(Color.BLUE);
        frame.add(display);

        JPanel panel = new JPanel();
        frame.add(panel);

        addButton(panel, "Add a new Employee", e -> addEmployee());
        addButton(panel, "View all Employees", e -> viewAllEmployees());
        addButton(panel, "Show details of an Employee", e -> viewEmployee());
        addButton(panel, "Delete Employee details", e -> removeEmployee());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void addButton(JPanel panel, String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBackground(Color.ORANGE);
        button.setForeground(Color.DARK_GRAY);
        button.addActionListener(listener);
        panel.add(button);
    }

    public static void addEmployee() {
        JFrame frame = new JFrame("Add Employee");
        frame.setSize(400, 400);

        JLabel label1 = new JLabel("Enter Employee ID:");
        JTextField empIdField = new JTextField();
        JPanel panel1 = new JPanel(new GridLayout(1, 2));
        panel1.add(label1);
        panel1.add(empIdField);

        JLabel label2 = new JLabel("Enter Employee Name:");
        JTextField empNameField = new JTextField();
        JPanel panel2 = new JPanel(new GridLayout(1, 2));
        panel2.add(label2);
        panel2.add(empNameField);

        JLabel label3 = new JLabel("Enter Department:");
        JTextField depField = new JTextField();
        JPanel panel3 = new JPanel(new GridLayout(1, 2));
        panel3.add(label3);
        panel3.add(depField);

        JLabel label4 = new JLabel("Enter Salary:");
        JTextField salaryField = new JTextField();
        JPanel panel4 = new JPanel(new GridLayout(1, 2));
        panel4.add(label4);
        panel4.add(salaryField);

        JLabel label5 = new JLabel("Enter Hire-date:");
        JTextField hireDateField = new JTextField();
        JPanel panel5 = new JPanel(new GridLayout(1, 2));
        panel5.add(label5);
        panel5.add(hireDateField);

        JLabel label6 = new JLabel("Enter Address of the Employee:");
        JTextField addressField = new JTextField();
        JPanel panel6 = new JPanel(new GridLayout(1, 2));
        panel6.add(label6);
        panel6.add(addressField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String id = empIdField.getText();
            String name = empNameField.getText();
            String dept = depField.getText();
            String salary = salaryField.getText();
            String hireDate = hireDateField.getText();
            String address = addressField.getText();

            try (BufferedReader reader = new BufferedReader(new FileReader("Employee.xls"));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("NewRec.xls"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line + "\n");
                }
                writer.write(String.join("\t", id, name, dept, salary, hireDate, address) + "\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            deleteFile();
            renameFile();
            JOptionPane.showMessageDialog(null, "Details have been updated!");
            frame.dispose();
        });

        frame.setLayout(new GridLayout(7, 1));
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(panel5);
        frame.add(panel6);
        frame.add(submitButton);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void deleteFile() 
    {
        File oldFile = new File("Employee.xls");
        oldFile.delete();
    }

    public static void renameFile() 
    {
        File newFile = new File("NewRec.xls");
        File oldFile = new File("Employee.xls");
        newFile.renameTo(oldFile);
    }

    public static void viewAllEmployees() {
        JFrame frame = new JFrame("View All Employees");
        frame.setSize(500, 400);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\KIIT\\Desktop\\Internship\\Employee.xls"))) {
            textArea.read(reader, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void viewEmployee() {
        JFrame frame = new JFrame("View Employee");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JTextField empIdField = new JTextField();
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        JLabel inputLabel = new JLabel("Enter Employee ID:");
        inputPanel.add(inputLabel);
        inputPanel.add(empIdField);
        frame.add(inputPanel, BorderLayout.NORTH);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");
        frame.add(searchButton, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String empId = empIdField.getText();
            try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\KIIT\\Desktop\\Internship\\Employee.xls"))) {
                String line;
                boolean found = false;
                while ((line = reader.readLine()) != null) {
                    String[] records = line.split("\t");
                    if (records[0].equals(empId)) {
                        resultArea.setText(String.join("\n", "Employee ID: " + records[0],
                                "Employee Name: " + records[1],
                                "Department: " + records[2],
                                "Salary: " + records[3],
                                "Hire-date: " + records[4],
                                "Address: " + records[5]));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    resultArea.setText("Employee not found.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void removeEmployee() {
        JFrame frame = new JFrame("Remove Employee");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JTextField empIdField = new JTextField();
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        JLabel inputLabel = new JLabel("Enter Employee ID:");
        inputPanel.add(inputLabel);
        inputPanel.add(empIdField);
        frame.add(inputPanel, BorderLayout.NORTH);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete");
        frame.add(deleteButton, BorderLayout.SOUTH);

        deleteButton.addActionListener(e -> {
            String empId = empIdField.getText();
            try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\KIIT\\Desktop\\Internship\\Employee.xls"));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("NewRec.xls"))) {
                String line;
                boolean found = false;
                while ((line = reader.readLine()) != null) {
                    if (!line.split("\t")[0].equals(empId)) {
                        writer.write(line + "\n");
                    } else {
                        found = true;
                    }
                }
                if (found) {
                    JOptionPane.showMessageDialog(null, "Employee record deleted.");
                } else {
                    JOptionPane.showMessageDialog(null, "Employee not found.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            deleteFile();
            renameFile();
        });

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}