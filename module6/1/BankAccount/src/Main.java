import BankAccounts.Account;
import BankAccounts.CardAccount;
import BankAccounts.DepositoryAccount;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("1. Обычный счет:");
        Account acc = new Account(1000.0);
        System.out.println("//пополняем на 100");
        acc.putMoney(100);
        System.out.println("//снимаем 100");
        acc.withdrawMoney(100);
        System.out.println("=====================================\n");


        System.out.println("2. Депозитный счет:");
        DepositoryAccount depositoryAccount = new DepositoryAccount(000.0);
        System.out.println("//снимаем 100");
        depositoryAccount.withdrawMoney(100.0);
        System.out.println("//Пауза 3 сек для теста времени...");
        Thread.sleep(3*1000);
        System.out.println("//пополняем на 100 для теста даты");
        depositoryAccount.putMoney(100.0);
        System.out.println("//снимаем 100 и смотрим дату");
        depositoryAccount.withdrawMoney(100);
        System.out.println("=====================================\n");

        System.out.println("3. Карточный счет:");
        CardAccount cardAccount = new CardAccount(1000);
        System.out.println("//снимаем 100");
        cardAccount.withdrawMoney(100);
        System.out.println("//пополняем на 100");
        cardAccount.putMoney(100);
        System.out.println("=====================================\n");
    }

}
