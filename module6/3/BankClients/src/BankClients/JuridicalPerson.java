package BankClients;

public class JuridicalPerson extends Client {
    private double comissionPercent = 1;//%

    public JuridicalPerson(double accountBalance) {
        super(accountBalance);
    }

    //"у юридических лиц — снятие с комиссией 1%"
    @Override
    public void withdrawMoney(double amount) {
        double comissionAmount = amount * (comissionPercent / 100);
        if (amount + comissionAmount <= getAccountBalance()) {
            super.withdrawMoney(amount);
            super.withdrawMoney(comissionAmount);
            System.out.println("Комиссия 1% составила: " + comissionAmount);
            getAccountBalance();
        } else {
            System.out.println("Отказ по причине: Недостаточно средств!");
        }
    }
}
