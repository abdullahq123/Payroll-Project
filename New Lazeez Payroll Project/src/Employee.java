public class Employee {
    private String employeeName;
    private String employeePosition;
    private double hourlyWage;

    public Employee(String employeeName, String employeePosition, double hourlyWage) {
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.hourlyWage = hourlyWage;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    @Override
    public String toString() {
        return "Employee Name: " + employeeName + "\nEmployee Position: " + employeePosition + "\nHourly Wage: $" + hourlyWage;
    }
}
