package BankClients;

public class IndividualBusinessman extends Client {
    public IndividualBusinessman(double accountBalance) {
        super(accountBalance);
    }

    //"а у ИП — пополнение с комиссией 1%, если сумма меньше 1000 рублей, и 0,5%, если сумма больше либо равна 1000 рублей"
    @Override
    public void putMoney(double amount) {
        if (amount < 1000) {
            double percent = 1;//%
            putMoneyCalculation(amount, percent);
        } else {
            double percent = 0.5;//%
            putMoneyCalculation(amount, percent);
        }
    }

    public void putMoneyCalculation(double amount, double percent) {
        double comissionAmount = amount * (percent / 100);
        super.putMoney(amount);
        super.withdrawMoney(comissionAmount);
        System.out.println("Комиссия " + percent + "% составила: " + comissionAmount);
        getAccountBalance();
    }
}
