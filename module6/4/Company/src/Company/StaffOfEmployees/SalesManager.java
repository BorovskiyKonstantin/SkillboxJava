package Company.StaffOfEmployees;

public class SalesManager implements Employee {
    private int fixedSalary = 100;
    private int awardPercent = 5;//%
    private int incomeForCompany = 120;

    public SalesManager(int fixedSalary, int incomeForCompany) {
        this.incomeForCompany = incomeForCompany;
    }


    @Override
    public int getMonthSalary() {
        int netIncome = incomeForCompany - fixedSalary; //чистый заработок с учетом расхода на ЗП
        if (netIncome > 0) {
            int awardAmount = (int) (double) (netIncome * awardPercent / 100);
            int finalSalary = fixedSalary + awardAmount;
            return finalSalary;
        }
        return fixedSalary;
    }
}
