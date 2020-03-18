import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        testTransfer();
//        testCreateAccounts();
//        testBalance();
    }

    public static void testTransfer() {
        int accountsCount = 10;
        int threadsCount = 1000;
        int operationsCount = 100;

        Bank bank = new Bank();
        for (int i = 1; i <= accountsCount; i++) {
            bank.createAccount(Integer.toString(i), i * 10_000);
        }
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < operationsCount; j++) {
                    //Случайный аккаунт отправителя
                    String fromAcc = Integer.toString(randomInRange(1, bank.getAccounts().size()));
                    //Случайный аккаунт получателя
                    String toAcc = Integer.toString(randomInRange(1, bank.getAccounts().size()));
                    //Транзакция на случайную сумму
                    bank.transfer(fromAcc, toAcc, randomInRange(1000, 55_000));
                }
            });
            threadList.add(thread);
        }

        //Запуск и ожидание выполнения всех потоков
        threadList.forEach(Thread::start);
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //Баланс на всех аккаунтах
        bank.getAccounts().forEach((num, acc) -> System.out.println("Счет № " + acc.getAccNumber() + " баланс " + acc.getBalance()));
    }

    public static void testBalance() {
        int accountsCount = 10;
        int threadsCount = 1000;
        int operationsCount = 1000;

        Bank bank = new Bank();
        for (int i = 0; i < accountsCount; i++) {
            bank.createAccount(String.format("%02d", i), 0);
        }

        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < operationsCount; j++) {
                    bank.getAccounts().forEach((a, s) -> {
                        s.putMoney(1);
                    });
                }
            });
            threadList.add(thread);
        }

        threadList.forEach(Thread::start);
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        bank.getAccounts().forEach((a, s) -> System.out.println(bank.getBalance(a)));
    }

    public static void testCreateAccounts() {

        Bank bank = new Bank();
        List<Thread> threadList = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    bank.createAccount(String.format("%03d", j), 0);
                }
            });

            threadList.add(thread);
        }

        threadList.forEach(Thread::start);
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        bank.getAccounts().forEach((num, acc) -> {
            System.out.println(num);
        });
    }

    private static int randomInRange(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
