import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {
    private JTextField tfUserName;
    private JPasswordField tfPassword;
    private JLabel welcomeLabel;
    private PayrollManager payrollManager;

    public MainFrame() {
        this.payrollManager = new PayrollManager();
        String filePath = "/Users/abdullahq/Desktop/Personal Projects CS/New Lazeez Payroll Project/New Lazeez Payroll Project/src/employeeSheet.csv"; // Update with the actual path
        payrollManager.loadEmployeesFromFile(filePath);

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Lazeez Payroll System");
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));

        welcomeLabel = new JLabel("Welcome", SwingConstants.CENTER);

        tfUserName = new JTextField();
        tfPassword = new JPasswordField();

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(tfUserName);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(tfPassword);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticate();
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            tfUserName.setText("");
            tfPassword.setText("");
            welcomeLabel.setText("Welcome");
        });

        loginPanel.add(loginButton);
        loginPanel.add(clearButton);

        mainPanel.add(loginPanel, BorderLayout.NORTH);
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void authenticate() {
        String username = tfUserName.getText();
        String password = new String(tfPassword.getPassword());
    
        User user = UserDatabase.authenticate(username, password);
        if (user != null) {
            welcomeLabel.setText("Welcome, " + user.getUsername());
            showMainMenu(user);
        } else {
            welcomeLabel.setText("Authentication failed. Try again.");
        }
    }
    private void showMainMenu(User currentUser) {
        getContentPane().removeAll();
        revalidate();
        repaint();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] menuOptions = {
            "Add new employee", "Delete employee", "Edit employee info",
            "Calculate employee salary", "Display employee list", "Exit"
        };

        if (currentUser.getRole() == Role.MANAGER || currentUser.getRole() == Role.ADMIN) {
            menuOptions = new String[] {"Add new employee", "Delete employee", "Edit employee info", "Calculate employee salary", "Display employee list", "Exit"};
        } else if (currentUser.getRole() == Role.EMPLOYEE) {
            menuOptions = new String[] {"Calculate employee salary", "Exit"};
        }

        JList<String> menuList = new JList<>(menuOptions);
        menuList.setFont(new Font("Segoe Print", Font.BOLD, 18));
        menuList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleMenuSelection(menuList.getSelectedValue(), currentUser);
            }
        });

        JTextArea outputArea = new JTextArea();
        outputArea.setFont(new Font("Segoe Print", Font.PLAIN, 16));
        outputArea.setEditable(false);

        mainPanel.add(new JScrollPane(menuList), BorderLayout.WEST);
        mainPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        revalidate();
        repaint();
    }

    private void handleMenuSelection(String selection, User currentUser) {
        switch (selection) {
            case "Add new employee":
                addEmployee();
                break;
            case "Delete employee":
                deleteEmployee();
                break;
            case "Edit employee info":
                editEmployee();
                break;
            case "Calculate employee salary":
                calculateSalary();
                break;
            case "Display employee list":
                displayEmployeeList(currentUser);
                break;
            case "Exit":
                exit();
                break;
        }
    }

    private void addEmployee() {
        String name = JOptionPane.showInputDialog(this, "Enter employee name:");
        String position = JOptionPane.showInputDialog(this, "Enter employee position:");
        double wage = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter employee hourly wage:"));
        payrollManager.addEmployee(name, position, wage);
    }

    private void deleteEmployee() {
        String name = JOptionPane.showInputDialog(this, "Enter employee name to delete:");
        payrollManager.deleteEmployee(name);
    }

    private void editEmployee() {
        String name = JOptionPane.showInputDialog(this, "Enter employee name to edit:");
        String newName = JOptionPane.showInputDialog(this, "Enter new name:");
        String newPosition = JOptionPane.showInputDialog(this, "Enter new position:");
        double newWage = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter new hourly wage:"));
        payrollManager.editEmployee(name, newName, newPosition, newWage);
    }

    private void calculateSalary() {
        String name = JOptionPane.showInputDialog(this, "Enter employee name to calculate salary:");
        int hoursWorked = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter hours worked:"));
        double salary = payrollManager.calculateSalary(name, hoursWorked);
        JOptionPane.showMessageDialog(this, "Salary for " + name + ": $" + salary);
    }

    private void displayEmployeeList(User currentUser) {
        if (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.MANAGER) {
            List<Employee> employees = payrollManager.getEmployeeList();
            StringBuilder builder = new StringBuilder();
            for (Employee employee : employees) {
                builder.append(employee).append("\n\n");
            }
            JTextArea outputArea = new JTextArea(builder.toString());
            outputArea.setFont(new Font("Segoe Print", Font.PLAIN, 16));
            outputArea.setEditable(false);
    
            JOptionPane.showMessageDialog(this, new JScrollPane(outputArea), "Employee List", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "You do not have permission to view the employee list.", "Access Denied", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exit() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            payrollManager.saveEmployeesToFile("/Users/abdullahq/Desktop/Personal Projects CS/New Lazeez Payroll Project/New Lazeez Payroll Project/src/employeeSheet.csv");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
