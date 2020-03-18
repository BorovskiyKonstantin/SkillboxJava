package BankClients;

public abstract class Client {
    private Double accountBalance;

    public Client(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getAccountBalance() {
        System.out.println("Баланс: " + accountBalance);
        System.out.println();
        return accountBalance;
    }

    public void putMoney(double amount) {
        if (amount < 0) {
            System.out.println("Ошибка! Введена сумма меньше 0.0(ноля)!");
        } else {
            accountBalance = accountBalance + amount;
            System.out.println("Пополнение баланса! Сумма: " + amount);
            getAccountBalance();
        }
    }

    public void withdrawMoney(double amount) {
        if (amount <= 0) {
            System.out.println("Ошибка! Введите сумму больше 0(нуля)");
        } else {
            if (amount <= accountBalance) {
                accountBalance = accountBalance - amount;
                System.out.println("Снятие средств: " + amount);
                getAccountBalance();
            } else {
                System.out.println("Отказ по причине: Недостаточно средств!");
            }
        }
    }
}
