package gruppe27;

public class Player {
    public final String name;
    public final Account Account;
    public Player(String playerName, int startingBalance){
        this.name = playerName;

        this.Account = new Account(startingBalance);
    }
}