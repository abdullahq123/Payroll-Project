import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollManager {
    private List<Employee> employeeList;

    public PayrollManager() {
        employeeList = new ArrayList<>();
    }

    public void addEmployee(String name, String position, double hourlyWage) {
        if (name == null || name.isEmpty() || position == null || position.isEmpty() || hourlyWage <= 0) {
            throw new IllegalArgumentException("Invalid employee details.");
        }
        Employee newEmployee = new Employee(name, position, hourlyWage);
        employeeList.add(newEmployee);
        saveEmployeesToFile("");  // Ensure the correct path is used
    }

    public void deleteEmployee(String employeeName) {
        employeeList.removeIf(employee -> employee.getEmployeeName().equalsIgnoreCase(employeeName));
        saveEmployeesToFile("/Users/abdullahq/Desktop/Personal Projects CS/New Lazeez Payroll Project/New Lazeez Payroll Project/src/employeeSheet.csv");
    }

    public void editEmployee(String employeeName, String newEmployeeName, String newEmployeePosition, double newHourlyWage) {
        for (Employee employee : employeeList) {
            if (employee.getEmployeeName().equalsIgnoreCase(employeeName)) {
                employee.setEmployeeName(newEmployeeName);
                employee.setEmployeePosition(newEmployeePosition);
                employee.setHourlyWage(newHourlyWage);
                saveEmployeesToFile("/Users/abdullahq/Desktop/Personal Projects CS/New Lazeez Payroll Project/New Lazeez Payroll Project/src/employeeSheet.csv");
                return;
            }
        }
    }

    public double calculateSalary(String employeeName, int hoursWorked) {
        for (Employee employee : employeeList) {
            if (employee.getEmployeeName().equalsIgnoreCase(employeeName)) {
                return hoursWorked * employee.getHourlyWage();
            }
        }
        return 0;
    }

    public List<Employee> getEmployeeList() {
        return new ArrayList<>(employeeList);
    }

    public void loadEmployeesFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine(); // Skip the header line
            String[] row;
            while ((line = reader.readLine()) != null) {
                row = line.split(",");
                if (row.length == 3) {
                    String name = row[0];
                    String position = row[1];
                    double wage = Double.parseDouble(row[2]);
                    Employee employee = new Employee(name, position, wage);
                    employeeList.add(employee);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    public void saveEmployeesToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Employee Name,Position,Hourly Wage\n");
            for (Employee employee : employeeList) {
                writer.write(employee.getEmployeeName() + "," + employee.getEmployeePosition() + "," + employee.getHourlyWage() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
