public class Account {
    private int balance;
    public Account(int startBalance){
        this.balance = startBalance;
    }

    public int getBalance() {
        return balance;
    }

    public void getPaid(int effectValue) {
        this.balance = this.balance + (effectValue);
    }

    public void loseMoney(int effectValue) {
        this.balance = this.balance - (effectValue);
    }

    public boolean isBankrupt() {
        return balance < 0;
    }
}
