package BankClients;

public class PhysicalPerson extends Client {
    public PhysicalPerson(double accountBalance) {
        super(accountBalance);
    }

    //"чтобы у физических лиц пополнение и снятие происходило без комиссии"
    @Override
    public final void withdrawMoney(double amount) {
        super.withdrawMoney(amount);
    }

    @Override
    public final void putMoney(double amount) {
        super.putMoney(amount);
    }
}
