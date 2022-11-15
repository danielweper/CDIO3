package gruppe27;

import javax.swing.*;

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
    public void getPaid(int effectValue) {this.balance = this.balance + (effectValue);}
    public void loseMoney(int effectValue) {this.balance = this.balance - (effectValue);}

    public boolean isBankrupt() {
        if (balance < 0){
            return true;
    }
        else {return false;}
    }
}
