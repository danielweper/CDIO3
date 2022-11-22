/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args)
    {
        System.out.println( "Hello World!" );

        int playerCount = 2;
        int startingMoney = (new int[] {0, 0, 20, 18, 16})[playerCount];

        Player[] players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player("Player " + (i+1), startingMoney, i);
        }

        DieCup cup = new DieCup(new SixSidedDie(), new SixSidedDie());

        Game g = new Game(players);
        while (true) {
            cup.roll();
            System.out.println("Rolled " + cup.getSum());
        }
    }
}
