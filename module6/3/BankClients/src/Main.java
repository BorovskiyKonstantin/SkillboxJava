import BankClients.Client;
import BankClients.IndividualBusinessman;
import BankClients.JuridicalPerson;
import BankClients.PhysicalPerson;

public class Main {
    public static void main(String[] args) {

        //Это объект абстрактного класса?==========
        Client client = new Client(100.0) {
            @Override
            public void withdrawMoney(double amount) {
                super.withdrawMoney(amount);
            }
        };
        client.putMoney(100.0);
        //Почему такое возможно?==========


        PhysicalPerson physicalPerson = new PhysicalPerson(100);
        physicalPerson.putMoney(100);
        physicalPerson.withdrawMoney(100);
        physicalPerson.getAccountBalance();

        JuridicalPerson juridicalPerson = new JuridicalPerson(100.0);
        juridicalPerson.putMoney(100);
        juridicalPerson.withdrawMoney(100);
        juridicalPerson.getAccountBalance();

        IndividualBusinessman ip = new IndividualBusinessman(100);
        ip.putMoney(100);
        ip.putMoney(1000);
        ip.withdrawMoney(100);
        ip.getAccountBalance();

    }

}
