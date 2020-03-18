public class Account {
    private long money;
    private String accNumber;
    private volatile boolean blocked;

    public Account(String accNumber, long money) {
        this.accNumber = accNumber;
        this.money = money;
        this.blocked = false;
    }

    //============= Getters & setters =============
    public String getAccNumber() {
        return accNumber;
    }

    public synchronized long getBalance() {
        return money;
    }

    public synchronized void putMoney(long money) {
        this.money = this.money + money;
    }

    public synchronized void takeMoney(long money) {
        this.money = this.money - money;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
