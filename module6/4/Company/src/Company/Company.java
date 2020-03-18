package Company;

import Company.StaffOfEmployees.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

public class Company {
    private int companyIncome = 1000000;

    private ArrayList<Employee> listOfEmployees = new ArrayList<>();

    //Реализовано через Collections.sort
    public ArrayList<Employee> getTopSalaryStaff(int count) {
        Collections.sort(listOfEmployees);
        ArrayList<Employee> topSalaryStaff = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            topSalaryStaff.add(i, listOfEmployees.get(i));
        }
        return topSalaryStaff;
    }

    //Реализовано через TreeSer и анонимный Comparator
    public ArrayList<Employee> getLowestSalaryStaff(int count) {
        ArrayList<Employee> lowSalaryStaff = new ArrayList<>();
        TreeSet<Employee> treeSet = new TreeSet<>(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                if (o1.getMonthSalary() < o2.getMonthSalary()) {
                    return 1;
                }
                if (o1.getMonthSalary() > o2.getMonthSalary()) {
                    return -1;
                }
                return 0;
            }
        });
        treeSet.addAll(listOfEmployees);
        for (int i = 0; i < count; i++) {
            lowSalaryStaff.add(i, treeSet.last());
            treeSet.remove(treeSet.last());
        }
        return lowSalaryStaff;
    }

    public void hireEmployee(Employee employee) {
        listOfEmployees.add(employee);
    }


    public int getCompanyIncome() {
        return companyIncome;
    }

    public void setCompanyIncome(int companyIncome) {
        this.companyIncome = companyIncome;
    }
}
