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
    static boolean[] playersInPrison;

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
        playersInPrison = new boolean[playerAmount];

        BoardGUI board = new BoardGUI(players, gui);

        //https://github.com/diplomit-dtu/MatadorGUIGuide/blob/3.2.x/src/main/java/Terning.java
        int currentPlayer = 0;
        while (true) {
            Player player = logicPlayers[currentPlayer];
            String playerName = player.name;
            if (playersInPrison[currentPlayer]) {
                gui.showMessage(String.format("%s, to get out of jail, you have to pay 1 million in bail", playerName));

                if (player.getBalance() < 1) {
                    gui.showMessage(String.format("%s unfortunately you cannot pay bail, and sit in prison for the rest of your life", playerName));
                    break;
                }
                playerPayMoney(currentPlayer, 1);
            }

            gui.showMessage(String.format("%s roll the dice", playerName));

            // Roll the dice
            d1.roll();
            d2.roll();
            gui.setDice(d1.face, d2.face);
            int sum = d1.face + d2.face;

            // Move the player on the board
            PlayerMovement movement = board.movePlayerByAmount(currentPlayer, sum);
            if (movement.PassedStart) {
                gui.showMessage(String.format("You made it all the way around the board %s! You get paid 2 million", playerName));
                playerGetPaid(currentPlayer, 2);
            }

            LandOnAction action = movement.EndField.landedOn(player);
            switch (action) {
                case GO_TO_PRISON -> {
                    gui.showMessage(String.format("Unfortunately %s was caught speeding, and is sent to jail", playerName));
                    board.movePlayerToField(currentPlayer, 6);
                }
                case BUY_PROPERTY -> {
                    PropertyField property = (PropertyField)movement.EndField;

                    gui.showMessage(String.format("%s, you have been offered to buy this property. Do you accept?", playerName));

                    if (player.getBalance() < property.Value) {
                        gui.showMessage(String.format("%s you don't have enough money to buy this property, and is shamed by your friends", playerName));
                        player.updateBalance(property.Value * -1);
                    }
                    else {
                        playerPayMoney(currentPlayer, property.Value);
                        property.setOwner(player);
                    }
                }
                case PAY_RENT -> {
                    PropertyField property = (PropertyField)movement.EndField;
                    Player owner = property.getOwner();

                    gui.showMessage(String.format("%s you were staying at %s's property, and now have to pay rent", playerName, owner.name));

                    int rent = property.Value;
                    if (board.playerOwnsBothProperties(currentPlayer, property.PropertyColor)) {
                        gui.showMessage(String.format("%s owns all properties around, and is now charging double for rent!", owner.name));
                        rent *= 2;
                    }

                    if (player.getBalance() < rent) {
                        gui.showMessage(String.format("%s you don't have enough money to pay for your stay. You are forced to work off your debt to %s", playerName, owner.name));
                        player.updateBalance(rent * -1);
                    }
                    else {
                        playerPayMoney(currentPlayer, rent);
                        playerGetPaid(owner.ID, rent);
                    }
                }
                case DRAW_CHANCE_CARD -> {
                    gui.displayChanceCard("You get a chance card!");
                }
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
}
