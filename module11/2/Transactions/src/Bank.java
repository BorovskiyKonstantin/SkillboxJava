import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Bank {
    private volatile Map<String, Account> accounts;
    private final Random random = new Random();

    public Bank() {
        this.accounts = new TreeMap<>();
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(0);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);
        //
        if (fromAccountNum.compareTo(toAccountNum) > 0) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    transferMoney(fromAccount, toAccount, amount);
                }
            }
        } else {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    transferMoney(fromAccount, toAccount, amount);
                }
            }
        }
        //Проверка службы безопасности
        if (amount > 50_000) {
            try {
                if (isFraud(fromAccountNum, toAccountNum, amount)) {
                    //Блокировка аккаунтов
                    fromAccount.setBlocked(true);
                    toAccount.setBlocked(true);
                    System.out.println("\nоперация мошенническая. Аккаунты: " + fromAccountNum + " и " + toAccountNum + " заблокированы");
                    //Разблокировка аккаунта через 1 секунду в новом потоке
                    new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fromAccount.setBlocked(false);
                        toAccount.setBlocked(false);
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }//transfer()

    private void transferMoney(Account fromAccount, Account toAccount, long amount) {
        if (fromAccount.getBalance() < amount) {
            System.out.println("\nНедостаточно средств!");
            return;
        }
        fromAccount.takeMoney(amount);
        toAccount.putMoney(amount);
        System.out.println("\nс account номер " + fromAccount.getAccNumber());
        System.out.println("на account номер " + toAccount.getAccNumber());
        System.out.println("трансфер " + amount);
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        Account account = accounts.get(accountNum);
        return account.getBalance();
    }


    //===========================================================
    public synchronized void createAccount(String accNumber, long money) {
        accounts.put(accNumber, new Account(accNumber, money));
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }
}
