package BankAccounts;

public class CardAccount extends Account {
    private double comissionPercent = 1;//%

    public CardAccount(double accountBalance) {
        super(accountBalance);
    }

    @Override
    public void putMoney(double amount) {
        super.putMoney(amount);
    }

    @Override
    public void withdrawMoney(double amount) {
        double comissionAmount = amount * (comissionPercent / 100);
        if (amount + comissionAmount <= getAccountBalance()) {
            super.withdrawMoney(amount);
            super.withdrawMoney(comissionAmount);
            System.out.println("Комиссия 1% составила: " + comissionAmount);
            getAccountBalance();
        } else {
            System.out.println("Отказ по причине:");
            System.out.println("Недостаточно средств!");
        }
    }

    @Override
    public double getAccountBalance() {
        return super.getAccountBalance();
    }
}
