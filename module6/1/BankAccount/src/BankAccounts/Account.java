package BankAccounts;

public class Account {
    protected double accountBalance;
    public Account(double accountBalance){
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
    }

    public void withdrawMoney(double amount) {
        if(amount <= accountBalance){
        accountBalance = accountBalance - amount;
        System.out.println("Снятие средств: " + amount);
        getAccountBalance();
        }
        else {
            System.out.println("Отказ по причине:");
            System.out.println("Недостаточно средств!");
        }
    }

}
