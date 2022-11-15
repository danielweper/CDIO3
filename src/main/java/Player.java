public class Player {
    public final String Name;
    public final Account Account;
    public final int ID;
    public Player(String playerName, int StartingBalance, int playerId){
        this.Name = playerName;
        this.Account = new Account(StartingBalance);
        this.ID = playerId;
    }
}