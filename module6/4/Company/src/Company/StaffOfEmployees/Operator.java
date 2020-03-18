package Company.StaffOfEmployees;

public class Operator implements Employee {
    private int fixedSalary = 50;

    public Operator(int fixedSalary) {
        this.fixedSalary = fixedSalary;
    }

    @Override
    public int getMonthSalary() {
        return fixedSalary;
    }
}
