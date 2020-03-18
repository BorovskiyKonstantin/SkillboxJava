package BankAccounts;

public class Account {
    //Сделать id счета
    private double accountBalance;

    public Account(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getAccountBalance() {
        System.out.println("Баланс: " + accountBalance);
        System.out.println();
        return accountBalance;
    }

    public void putMoney(double amount) {

        accountBalance = accountBalance + amount;
        System.out.println("Пополнение баланса! Сумма: " + amount);
        getAccountBalance();
        DepositoryAccount account = new DepositoryAccount(100);
    }

    public void withdrawMoney(double amount) {
        if (amount <= accountBalance) {
            accountBalance = accountBalance - amount;
            System.out.println("Снятие средств: " + amount);
            getAccountBalance();
        } else {
            System.out.println("Отказ по причине:");
            System.out.println("Недостаточно средств!");
        }
    }

}
