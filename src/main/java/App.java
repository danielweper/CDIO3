/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args)
    {
        System.out.println( "Hello World!" );

        int playerCount = 4;
        int startingMoney = (new int[] {0, 0, 20, 18, 16})[playerCount];

        Player[] players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player("Player " + (i+1), startingMoney, i);
        }

        DieCup cup = new DieCup(new SixSidedDie(), new SixSidedDie());

        Game game = new Game(players);
        while (true) {
            cup.roll();
            Player currentPlayer = game.getCurrentPlayer();
            System.out.println(String.format("Starting %s's turn (money: %d)", currentPlayer.name, currentPlayer.getBalance()));

            if (game.isPlayerInPrison(currentPlayer.ID)) {
                System.out.println("Paying to get out of prison");
                currentPlayer.updateBalance(-1);
                game.releasePlayerFromPrison(currentPlayer.ID);
                game.setPlayerPositionTo(6);
                System.out.println("New balance: " + currentPlayer.getBalance());
            }

            PlayerMovement movement = game.movePlayerBy(cup.getSum());
            System.out.println("Rolled " + cup.getSum());
            System.out.println("Movement: " + movement);

            LandOnAction gameAction = movement.EndField.landedOn(currentPlayer);
            System.out.println("Action to perform: " + gameAction);
            switch (gameAction) {
                case BUY_PROPERTY -> {
                    PropertyField property = (PropertyField)movement.EndField;
                    property.setOwner(currentPlayer);
                    currentPlayer.updateBalance(property.Value * -1);
                }
                case PAY_RENT -> {
                    PropertyField property = (PropertyField)movement.EndField;
                    Player owner = property.getOwner();

                    int price = property.Value;
                    if (owner.equals(game.getOwnerOfSet(property.Color))) {
                        price *= 2;
                    }
                    currentPlayer.updateBalance(price * -1);
                    property.getOwner().updateBalance(price);
                }
                case DRAW_CHANCE_CARD -> {
                    ChanceCard card = game.drawChanceCard();
                    System.out.println("Drew " + card);
                    game.insertChanceCard(card);
                }
                case GO_TO_PRISON -> game.setPlayerInPrison(currentPlayer.ID);
                case NOTHING -> {}
            }

            System.out.println(String.format("Ending %s's turn (money: %d)", currentPlayer.name, currentPlayer.getBalance()));
            System.out.println();

            if (currentPlayer.getBalance() < 0) {
                break;
            }
            game.nextTurn();
        }
        System.out.println("GAME OVER");
    }
}
