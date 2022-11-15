public class Player {
    public final String Name;
    public final Account Account;
    public final int ID;
    public Player(String playerName, int StartingBalance, int playerId){
        this.Name = playerName;
        this.Account = new Account(StartingBalance);
        this.ID = playerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player p) {
            return this.ID == p.ID;
        }
        return false;
    }
}