package Company.StaffOfEmployees;

public interface Employee extends Comparable<Employee> {
    int getMonthSalary();

    @Override
    default int compareTo(Employee employee) {
        if (getMonthSalary() < employee.getMonthSalary()) {
            return 1;
        }
        if (getMonthSalary() > employee.getMonthSalary()) {
            return -1;
        }
        return 0;
    }
}
