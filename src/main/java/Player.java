public class Player {
    // TODO stort variabelnavn
    public final String name;
    public final Account Account;
    // TODO en spiller nummer/id variabel
    public Player(String playerName, int startingBalance){
        this.name = playerName;
        this.Account = new Account(startingBalance);
    }
}