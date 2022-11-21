public class Player {
    public final String name;
    public final Account account;
    public final int ID;
    public Player(String playerName, int StartingBalance, int playerId){
        this.name = playerName;
        this.account = new Account(StartingBalance);
        this.ID = playerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player p) {
            return this.ID == p.ID;
        }
        return false;
    }

    public void updateBalance(int a){
        account.getMoney(a);
    }

    public int getBalance(){
        return account.getBalance();
    }
}