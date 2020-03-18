package Company.StaffOfEmployees;

import Company.Company;

public class TopManager implements Employee {
    private int fixedSalary = 150;
    private int awardPercent = 1;//%

    //Первый вариант
//    public TopManager(int fixedSalary) {
//        this.fixedSalary = fixedSalary;
//    }
    /**1.Пришлось пересоздать этот метод с параметром "companyIncome", т.к. ЗП зависит от дохода компании*/
//    public int getMonthSalary(int companyIncome) {
//        if(companyIncome >= 10000) {
//            int awardAmount = companyIncome * awardPercent / 100;
//            int finalSalary = fixedSalary + awardAmount;
//            return finalSalary;
//        }
//        return fixedSalary;
//    }

    /**2.Остался метод от интерфейса без параметров. В данном случае лучше было бы убрать интерфейс Employee и этод метод.
     * Но что, если в интерфейсе были бы еще несколько методов,которые необходимо реализовать и контроллировать их наличие?
     */

//    @Override
//    public int getMonthSalary() {
//        return 0;
//    }

    /**
     * 3.Думаю тогда лучше реализовать что-то вроде:
     */

    //Второй вариант
    private Company company;

    public TopManager(int fixedSalary, Company company) {
        this.fixedSalary = fixedSalary;
        this.company = company;
    }

    @Override
    public int getMonthSalary() {
        int companyIncome = company.getCompanyIncome();
        if (companyIncome >= 10000) {
            int awardAmount = companyIncome * awardPercent / 100;
            int finalSalary = fixedSalary + awardAmount;
            return finalSalary;
        }
        return fixedSalary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * ВОПРОС:
     * Если используется интерфейс с кучей методов и у одного из этих методов нужно добавить/поменять параметры, то как это грамотно сделать?
     */


}
