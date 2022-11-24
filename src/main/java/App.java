import gui_fields.GUI_Car;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    static FieldGUI num_Fields = new FieldGUI();
    static GUI_Player[] players;
    static Player[] logicPlayers;

    public static void main(String[] args) {
        GUI gui = new GUI(num_Fields.Showfields());
        SixSidedDie d1 = new SixSidedDie();
        SixSidedDie d2 = new SixSidedDie();

        int playerAmount = 2;

        players = makePlayers(playerAmount, gui);
        logicPlayers = new Player[playerAmount];
        for (int i = 0; i < playerAmount; ++i) {
            GUI_Player guiPlayer = players[i];
            logicPlayers[i] = new Player(guiPlayer.getName(), guiPlayer.getBalance(), guiPlayer.getNumber());
        }

        BoardGUI board = new BoardGUI(players, gui);

        //https://github.com/diplomit-dtu/MatadorGUIGuide/blob/3.2.x/src/main/java/Terning.java
        int currentPlayer = 0;
        while (true) {
            Player player = logicPlayers[currentPlayer];

            gui.showMessage("Roll dice");

            // Roll the dice
            d1.roll();
            d2.roll();
            gui.setDice(d1.face, d2.face);
            int sum = d1.face + d2.face;

            // Move the player on the board
            PlayerMovement movement = board.movePlayerByAmount(currentPlayer, sum);
            if (movement.PassedStart) {
                gui.showMessage("You get paid for doing a whole lap");
                playerGetPaid(currentPlayer, 2);
            }

            LandOnAction action = movement.EndField.landedOn(player);
            switch (action) {
                case GO_TO_PRISON -> {
                    gui.showMessage("You have been sent to prison");
                    board.movePlayerToField(currentPlayer, 6);
                }
                case BUY_PROPERTY -> {
                    gui.showMessage("You have to buy this property");
                    PropertyField property = (PropertyField)movement.EndField;
                    property.setOwner(player);
                    playerPayMoney(currentPlayer, property.Value);
                }
                case PAY_RENT -> {
                    gui.showMessage("You have to pay rent");
                    PropertyField property = (PropertyField)movement.EndField;

                    Player owner = property.getOwner();
                    int rent = property.Value;
                    if (board.playerOwnsBothProperties(currentPlayer, property.PropertyColor)) {
                        gui.showMessage("Owner owns both properties, so rent is double");
                        rent *= 2;
                    }
                    playerPayMoney(currentPlayer, rent);
                    playerGetPaid(owner.ID, rent);
                }
                default -> gui.showMessage("Player should now " + action);
            }

            if (player.account.isBankrupt()) {
                break;
            }

            currentPlayer = (currentPlayer + 1) % playerAmount;
        }
        gui.showMessage("Game is over");
    }

    private static void updatePlayerBalance(int playerIndex, int balanceChange) {
        Player playerLogic = logicPlayers[playerIndex];
        GUI_Player playerGUI = players[playerIndex];

        playerLogic.updateBalance(balanceChange);
        playerGUI.setBalance(playerLogic.getBalance());
    }

    private static void playerPayMoney(int playerIndex, int moneyToPay) {
        updatePlayerBalance(playerIndex, moneyToPay * -1);
    }

    private static void playerGetPaid(int playerIndex, int moneyToGet) {
        updatePlayerBalance(playerIndex, moneyToGet);
    }

    public static GUI_Player[] makePlayers(int amount, GUI gui) {
        int startingBalance = (amount == 2) ? 20 : ((amount == 3) ? 18 : 16);
        ArrayList<Color> colors = new ArrayList<>(Arrays.stream(new Color[] {Color.red, Color.blue, Color.black, Color.green, Color.magenta, Color.yellow}).toList());
        ArrayList<String> colorStrings = new ArrayList<>();
        for (Color color : colors) {
            colorStrings.add(color.toString());
        }

        GUI_Player[] players = new GUI_Player[amount];
        for (int i=0; i < amount; i++) {
            String playerName = gui.getUserString("Player " + (i+1) + " what is your name?");
            String[] colorStringArray = new String[colorStrings.size()];
            String colorString = gui.getUserButtonPressed("Choose a color", colorStrings.toArray(colorStringArray));
            Color playerColor = colors.get(colorStrings.indexOf(colorString));

            GUI_Car playerCar = new GUI_Car(playerColor, playerColor, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL);
            players[i] = new GUI_Player(playerName, startingBalance, playerCar);

            colors.remove(playerColor);
            colorStrings.remove(colorString);
            gui.addPlayer(players[i]);
        }
        return players;
    }

    /*public static void main(String[] args)
    {
        System.out.println( "Hello World!" );

        int playerCount = 2;
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
                    System.out.println("Property color: " + property.Color);
                    property.setOwner(currentPlayer);
                    currentPlayer.updateBalance(property.Value * -1);
                }
                case PAY_RENT -> {
                    PropertyField property = (PropertyField)movement.EndField;
                    System.out.println("Property color: " + property.Color);
                    Player owner = property.getOwner();

                    int price = property.Value;
                    if (owner.equals(game.getOwnerOfSet(property.Color))) {
                        System.out.println("Owner owns both properties, so you have to pay double");
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
    }*/
}
