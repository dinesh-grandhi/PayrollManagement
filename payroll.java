import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class payroll extends JFrame {
    private JLabel nameLabel, passwordLabel;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton loginButton, exitButton, aboutButton;

    private final String EMPLOYEE_FILE = "employees.txt";

    public payroll() {
        setTitle("Payroll Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");
        aboutButton = new JButton("About");

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = GridBagConstraints.CENTER;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);

        add(nameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1; 
        add(nameField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        add(passwordLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1; 
        add(passwordField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraints.gridwidth = 2; 
        add(loginButton, gridBagConstraints);

        gridBagConstraints.gridwidth = 1; 
        gridBagConstraints.anchor = GridBagConstraints.LINE_END; 
        add(exitButton, gridBagConstraints);

        gridBagConstraints.gridx = 1; 
        add(aboutButton, gridBagConstraints);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAbout();
            }
        });

        pack();
        setVisible(true);
    }

    private void login() {
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());

        if (name.equals("name") && password.equals("password")) {
            JOptionPane.showMessageDialog(this, "Login successful!");

            openMainDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
        }
    }

    private void openMainDashboard() {
        JFrame mainDashboardFrame = new JFrame("Payroll Management System - Main Dashboard");
        mainDashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainDashboardFrame.setLayout(new GridBagLayout());

        JLabel mainDashboardLabel = new JLabel("Main Dashboard");
        mainDashboardLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel mainDashboardPanel = new JPanel();
        mainDashboardPanel.setLayout(new GridBagLayout());

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.anchor = GridBagConstraints.CENTER;
        labelConstraints.insets = new Insets(10, 0, 20, 0); // Adjust top padding

        mainDashboardPanel.add(mainDashboardLabel, labelConstraints);

        JButton addEmployeeButton = new JButton("Add New Employee");
        JButton modifyEmployeeButton = new JButton("Modify Employee Record");
        JButton deleteEmployeeButton = new JButton("Delete Employee");
        JButton viewEmployeeButton = new JButton("View Employee List");

        addEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        modifyEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyEmployeeDetails();
            }
        });

        deleteEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        viewEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewEmployees();
            }
        });

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = GridBagConstraints.RELATIVE;
        buttonConstraints.anchor = GridBagConstraints.CENTER;
        buttonConstraints.insets = new Insets(10, 0, 10, 0); // Adjust vertical spacing

        mainDashboardPanel.add(addEmployeeButton, buttonConstraints);
        mainDashboardPanel.add(modifyEmployeeButton, buttonConstraints);
        mainDashboardPanel.add(deleteEmployeeButton, buttonConstraints);
        mainDashboardPanel.add(viewEmployeeButton, buttonConstraints);

        mainDashboardFrame.add(mainDashboardPanel);

        JButton exitDashboardButton = new JButton("Exit");
        exitDashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = GridBagConstraints.RELATIVE;
        buttonConstraints.anchor = GridBagConstraints.LINE_END;
        buttonConstraints.insets = new Insets(10, 0, 10, 0);

        mainDashboardPanel.add(exitDashboardButton, buttonConstraints);

        mainDashboardFrame.add(mainDashboardPanel);

        mainDashboardFrame.pack();
        mainDashboardFrame.setVisible(true);
    }

    private void addEmployee() {
        String employeeName = JOptionPane.showInputDialog(this, "Enter employee name:");
        String employeeId = JOptionPane.showInputDialog(this, "Enter employee ID:");
        String employeePosition = JOptionPane.showInputDialog(this, "Enter employee position:");
        double employeeSalary = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter employee salary:"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE, true))) {
            writer.write(employeeName + "," + employeeId + "," + employeePosition + "," + employeeSalary);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Employee added successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to add employee. Please try again.");
        }
    }

    private void modifyEmployeeDetails() {
        String employeeId = JOptionPane.showInputDialog(this, "Enter employee ID:");
        ArrayList<String> employees = readEmployeeFile();

        boolean found = false;
        for (int i = 0; i < employees.size(); i++) {
            String[] employeeData = employees.get(i).split(",");
            if (employeeData.length >= 2 && employeeData[1].equals(employeeId)) {
                String modifiedName = JOptionPane.showInputDialog(this, "Enter modified name:");
                String modifiedPosition = JOptionPane.showInputDialog(this, "Enter modified position:");
                double modifiedSalary = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter modified salary:"));

                employees.set(i, modifiedName + "," + employeeId + "," + modifiedPosition + "," + modifiedSalary);
                found = true;
                break;
            }
        }

        if (found) {
            if (writeEmployeeFile(employees)) {
                JOptionPane.showMessageDialog(this, "Employee details modified successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to modify employee details. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Employee not found. Please enter a valid employee ID.");
        }
    }

    private void deleteEmployee() {
        String employeeId = JOptionPane.showInputDialog(this, "Enter employee ID:");
        ArrayList<String> employees = readEmployeeFile();

        boolean found = false;
        for (int i = 0; i < employees.size(); i++) {
            String[] employeeData = employees.get(i).split(",");
            if (employeeData.length >= 2 && employeeData[1].equals(employeeId)) {
                employees.remove(i);
                found = true;
                break;
            }
        }

        if (found) {
            if (writeEmployeeFile(employees)) {
                JOptionPane.showMessageDialog(this, "Employee details deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete employee details. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Employee not found. Please enter a valid employee ID.");
        }
    }

    private void viewEmployees() {
        ArrayList<String> employees = readEmployeeFile();

        if (!employees.isEmpty()) {
            StringBuilder employeeDetails = new StringBuilder();
            for (String employee : employees) {
                employeeDetails.append(employee).append("\n");
            }
            JOptionPane.showMessageDialog(this, employeeDetails.toString());
        } else {
            JOptionPane.showMessageDialog(this, "No employees found.");
        }
    }

    private ArrayList<String> readEmployeeFile() {
        ArrayList<String> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                employees.add(line);
            }
        } catch (FileNotFoundException e) {
            return employees;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to read employee details.");
        }

        return employees;
    }

    private boolean writeEmployeeFile(ArrayList<String> employees) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE))) {
            for (String employee : employees) {
                writer.write(employee);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void openAbout() {
        JFrame aboutFrame = new JFrame("About");
        aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aboutFrame.setSize(300, 200);
    
        JLabel aboutLabel = new JLabel("<html>Made by:<br/>Dharan Perumalla(AP21110010201),<br/>Tarun Teja Kudeti(AP21110010205),<br/>Pavan Sastry NVSS(AP21110010209),<br/>Dinesh Grandhi(AP21110010240).</html>");
        aboutLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);
        aboutLabel.setVerticalAlignment(SwingConstants.CENTER);
    
        aboutFrame.getContentPane().add(aboutLabel);
    
        aboutFrame.setLocationRelativeTo(null);
    
        aboutFrame.setVisible(true);
    }
    

    private void exit() {
        int confirm = JOptionPane.showOptionDialog(this, "Are you sure you want to exit?", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new payroll();
            }
        });
    }
}