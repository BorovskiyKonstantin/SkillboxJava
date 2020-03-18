import Company.Company;
import Company.StaffOfEmployees.Employee;
import Company.StaffOfEmployees.Operator;
import Company.StaffOfEmployees.SalesManager;
import Company.StaffOfEmployees.TopManager;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Создание компании
        Company company = new Company();
        //Найм операторов с фиксированной зарплатой i
        for (int i = 1; i < 150; i++) {
            company.hireEmployee(new Operator(i));
        }
        //Найм менеджеров по продажам с фиксированной зарплатой i
        for (int i = 151; i < 250; i++) {
            company.hireEmployee(new SalesManager(i, (int) Math.random() * i));
        }
        //Найм топ менеджеров с фиксированной зарплатой i
        for (int i = 251; i < 270; i++) {
            company.hireEmployee(new TopManager(i, company));
        }

        //10 наибольшиш зарплат
        company.hireEmployee(new Operator(1000));
        ArrayList<Employee> topSalaryStaff = company.getTopSalaryStaff(10);
        System.out.println("ТОП 10 зарплат:");
        for (Employee employee : topSalaryStaff) {
            System.out.println(employee.getMonthSalary());
        }

        //10 наименьших зарплат
        System.out.println();
        ArrayList<Employee> lowSalaryStaff = company.getLowestSalaryStaff(10);
        System.out.println("Меньшие 10 зарплат:");
        for (Employee employee : lowSalaryStaff) {
            System.out.println(employee.getMonthSalary());
        }

    }
}
