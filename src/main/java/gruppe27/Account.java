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

    // TODO split setter i 2 (getPaid og loseMoney eller lign.)
    // TODO lav hjælper metoden isBankrupt
}
