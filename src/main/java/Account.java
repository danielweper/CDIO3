public class Account {
    private int balance;
    public Account(int startBalance){
        this.balance = startBalance;
    }

    public int getBalance() {
        return balance;
    }

    public void getMoney(int effectValue) {
        this.balance = this.balance + (effectValue);
    }


    public boolean isBankrupt() {
        return balance < 0;
    }
}
