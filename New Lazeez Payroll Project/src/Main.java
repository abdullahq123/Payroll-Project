public Main() {
    this.payrollManager = new PayrollManager();
    String filePath = "/Users/abdullahq/Desktop/Personal Projects CS/New Lazeez Payroll Project/New Lazeez Payroll Project/src/employeeSheet.csv";
    payrollManager.loadEmployeesFromFile(filePath);

    initializeUI();
}
