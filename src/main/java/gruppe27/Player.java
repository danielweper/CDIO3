package gruppe27;

public class Player {
    public final String Name;
    public final Account Account;
    public Player(String playerName, int StartingBalance, int PlayerID){
        this.Name = playerName;
        this.Account = new Account(StartingBalance);
    }
}