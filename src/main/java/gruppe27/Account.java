package gruppe27;

public class Account {

    private int balance;

    public Account(int startBalance){
        this.balance = startBalance;
    }

    // Getter
    public int getBalance() {
        return balance;
    }

    //Setter
    public void updateBalance(int effectValue) {
        this.balance = this.balance + (effectValue);
    }


}
