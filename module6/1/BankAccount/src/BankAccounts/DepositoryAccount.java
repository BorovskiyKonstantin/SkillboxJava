package BankAccounts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DepositoryAccount extends Account{
    Calendar dateOfLastDeposit;
    Calendar dateOfEndBlocking;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'и время' HH:mm:ss zzz");
    public DepositoryAccount(double accountBalance) {
        super(accountBalance);
        dateOfLastDeposit = Calendar.getInstance();
        dateOfEndBlocking = (Calendar) dateOfLastDeposit.clone();
        dateOfEndBlocking.add(Calendar.MONTH,1);
    }

    public void putMoney(double amount) {
        super.putMoney(amount);
        dateOfLastDeposit = Calendar.getInstance();
        dateOfEndBlocking = (Calendar) dateOfLastDeposit.clone();
        dateOfEndBlocking.add(Calendar.MONTH,1);
        System.out.println("Дата пополнения: " + dateFormat.format(dateOfLastDeposit.getTime()));
        System.out.println("Средства недоступны для снятия до " + dateFormat.format(dateOfEndBlocking.getTime()));
    }

    public void withdrawMoney(double amount) {

        Date date = new Date();
        if(date.before(dateOfEndBlocking.getTime())){
            System.out.println("Отказ по причине:");
            System.out.println("Снятие средств недоступно до " + dateFormat.format(dateOfEndBlocking.getTime()));
        } else {
            super.withdrawMoney(amount);
        }
    }

}
