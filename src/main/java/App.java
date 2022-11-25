import gui_fields.GUI_Car;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

import java.awt.Color;
import java.util.*;

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
    static Queue<ChanceCard> chanceCards;

    public static void main(String[] args) {
        GUI gui = new GUI(num_Fields.Showfields());
        SixSidedDie d1 = new SixSidedDie();
        chanceCards = new LinkedList<>();
        List<ChanceCard> allCards = generateChanceCards();
        Random rng = new Random();
        while (allCards.size() > 0) {
            int nextCardIndex = rng.nextInt(allCards.size());
            chanceCards.add(allCards.remove(nextCardIndex));
        }

        String playerCountString = gui.getUserButtonPressed("How many players are playing?", "2", "3", "4");
        int playerAmount = Integer.parseInt(playerCountString);

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
                gui.showMessage(String.format("%s, to get out of jail, you have to pay 1 in bail", playerName));

                playerPayMoney(currentPlayer, 1);
                if (player.account.isBankrupt()) {
                    gui.showMessage(String.format("%s unfortunately you cannot pay bail, and sit in prison for the rest of your life", playerName));
                    break;
                }
                playersInPrison[currentPlayer] = false;
            }

            gui.showMessage(String.format("%s roll the dice", playerName));

            // Roll the dice
            d1.roll();
            gui.setDie(d1.face);

            // Move the player on the board
            PlayerMovement movement = board.movePlayerByAmount(currentPlayer, d1.face);
            if (movement.PassedStart) {
                gui.showMessage(String.format("You made it all the way around the board %s! You get paid 2", playerName));
                playerGetPaid(currentPlayer, 2);
            }

            LandOnAction action = movement.EndField.landedOn(player);
            switch (action) {
                case GO_TO_PRISON -> {
                    gui.showMessage(String.format("Unfortunately %s was caught speeding, and is sent to jail", playerName));
                    board.movePlayerToField(currentPlayer, 6);
                    playersInPrison[currentPlayer] = true;
                }
                case BUY_PROPERTY -> {
                    PropertyField property = (PropertyField)movement.EndField;

                    gui.showMessage(String.format("%s, you have been offered to buy this property. Do you accept?", playerName));

                    playerPayMoney(currentPlayer, property.Value);
                    if (player.getBalance() < property.Value) {
                        gui.showMessage(String.format("%s you don't have enough money to buy this property, and is shamed by your friends", playerName));
                    }
                    else {
                        property.setOwner(player);
                    }
                }
                case PAY_RENT -> {
                    PropertyField property = (PropertyField)movement.EndField;
                    payRent(currentPlayer, property, board, gui);
                }
                case DRAW_CHANCE_CARD -> {
                    ChanceCard drawnCard;
                    while (true) {
                        drawnCard = chanceCards.remove();
                        chanceCards.add(drawnCard);
                        if (drawnCard.isChoiceCard()) {
                            continue;
                        }

                        boolean breakOutOfLoop = true;
                        PlayerMovement chanceMovement = null;
                        ArrayList<ChanceCardAction[]> actionsList = drawnCard.getActions();
                        for (ChanceCardAction[] actions : actionsList) {
                            ChanceCardAction chanceAction;
                            if (actions.length == 1) {
                                chanceAction = actions[0];
                                if (chanceAction.Event.equals(ChanceCardEvent.GIVE_CARD_TO_OTHER_PLAYER) || chanceAction.Event.equals(ChanceCardEvent.GET_OUT_OF_JAIL_FREE)) {
                                    breakOutOfLoop = false;
                                }

                                switch (chanceAction.Event) {
                                    case MOVE_RELATIVE -> {
                                        gui.displayChanceCard(String.format("Move %d squares forward", chanceAction.Value));
                                        gui.showMessage("");
                                        chanceMovement = board.movePlayerByAmount(currentPlayer, chanceAction.Value);
                                    }
                                    case MOVE_TO -> {
                                        String fieldName;
                                        switch (chanceAction.Value) {
                                            case 0 -> fieldName = "Start";
                                            case 23 -> fieldName = "The beach";
                                            default -> fieldName = String.format("the %dth field", chanceAction.Value);
                                        }
                                        gui.displayChanceCard(String.format("%s you move to %s", playerName, fieldName));
                                        gui.showMessage("");
                                        chanceMovement = board.movePlayerToField(currentPlayer, chanceAction.Value);
                                    }
                                    case PAY_BANK -> {
                                        gui.displayChanceCard(String.format("%s now has to pay %d for all the candy they bought", playerName, chanceAction.Value));
                                        playerPayMoney(currentPlayer, chanceAction.Value);
                                    }
                                    case GET_PAID_BY_BANK -> {
                                        gui.displayChanceCard(String.format("%s gets their allowance of %d paid", playerName, chanceAction.Value));
                                        playerGetPaid(currentPlayer, chanceAction.Value);
                                    }
                                    case GET_PAID_BY_PLAYERS -> {
                                        gui.displayChanceCard(String.format("It's %s's birthday! Every player gives a gift of %d money", playerName, chanceAction.Value));
                                        for (int i = 0; i < playerAmount; ++i) {
                                            if (i == currentPlayer) {
                                                continue;
                                            }
                                            playerPayMoney(i, 1);
                                        }
                                        playerGetPaid(currentPlayer, playerAmount);
                                    }
                                    case MOVE_TO_COLOR -> {
                                        PropertyColor color = PropertyColor.values()[chanceAction.Value];
                                        gui.displayChanceCard(String.format("%s, you have to move to a %s property", playerName, color.toString().toLowerCase()));
                                        int[] indexes = board.getIndexesOfPropertyColor(color);
                                        int chosenPosition;

                                        boolean choseFirst = gui.getUserLeftButtonPressed(String.format("Which of the %s fields do you want to go to?", color.toString().toLowerCase()), "The first", "The second");
                                        if (choseFirst) {
                                            chosenPosition = indexes[0];
                                        }
                                        else {
                                            chosenPosition = indexes[1];
                                        }

                                        chanceMovement = board.movePlayerToField(currentPlayer, chosenPosition);
                                    }
                                    case GET_PROPERTY_FREE_OR_PAY_OWNER -> {
                                        PropertyField chanceProperty = (PropertyField)chanceMovement.EndField;
                                        LandOnAction chanceLandAction = chanceProperty.landedOn(player);
                                        switch (chanceLandAction) {
                                            case BUY_PROPERTY -> {
                                                gui.displayChanceCard(String.format("%s, you get this property for free!", playerName));
                                                chanceProperty.setOwner(player);
                                            }
                                            case PAY_RENT -> {
                                                gui.displayChanceCard(String.format("Unfortunately the property was already owned, and %s you have to pay rent to the owner", playerName));
                                                payRent(currentPlayer, chanceProperty, board, gui);
                                            }
                                        }
                                    }
                                    default -> breakOutOfLoop = false;
                                }
                            }
                            else {
                                breakOutOfLoop = false;
                                break;
                            }
                        }

                        if (breakOutOfLoop) {
                            if (chanceMovement != null && chanceMovement.PassedStart) {
                                gui.showMessage(String.format("You made it all the way around the board %s! You get paid 2", playerName));
                                playerGetPaid(currentPlayer, 2);
                            }
                            break;
                        }
                    }
                }
            }

            if (player.account.isBankrupt()) {
                break;
            }

            currentPlayer = (currentPlayer + 1) % playerAmount;
        }
        gui.showMessage("Game is over");
    }

    private static void payRent(int payingPlayerId, PropertyField property, BoardGUI board, GUI gui) {
        Player payingPlayer = logicPlayers[payingPlayerId];
        Player owner = property.getOwner();

        gui.showMessage(String.format("%s you were staying at %s's property, and now have to pay rent", payingPlayer.name, owner.name));

        int rent = property.Value;
        if (board.playerOwnsBothProperties(payingPlayerId, property.PropertyColor)) {
            gui.showMessage(String.format("%s owns all properties around, and is now charging double for rent!", owner.name));
            rent *= 2;
        }

        playerPayMoney(payingPlayerId, rent);
        if (payingPlayer.getBalance() < rent) {
            gui.showMessage(String.format("%s you don't have enough money to pay for your stay. You are forced to work off your debt to %s", payingPlayer.name, owner.name));
        }
        else {
            playerGetPaid(owner.ID, rent);
        }
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

    private static GUI_Player[] makePlayers(int amount, GUI gui) {
        int startingBalance = (amount == 2) ? 20 : ((amount == 3) ? 18 : 16);
        ArrayList<Color> colors = new ArrayList<>(Arrays.stream(new Color[] {Color.red, Color.blue, Color.black, Color.green, Color.magenta, Color.yellow}).toList());
        ArrayList<String> colorStrings = new ArrayList<>(Arrays.stream(new String[] {"Red", "Blue", "Black", "Green", "Magenta", "Yellow"}).toList());

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

    private static List<ChanceCard> generateChanceCards() {
        List<ChanceCard> allChanceCards = new ArrayList<>();
        // Commonly used actions
        ChanceCardAction takeAnotherCard = new ChanceCardAction(ChanceCardEvent.TAKE_ANOTHER_CARD, 1);
        ChanceCardAction getForFreeOrPayOwner = new ChanceCardAction(ChanceCardEvent.GET_PROPERTY_FREE_OR_PAY_OWNER, 0);

        // Reused variable
        ChanceCard card;

        // Make all the give to other players cards
        for (int i = 0; i < 4; i++) {
            card = new ChanceCard();
            card.addAction(new ChanceCardAction(ChanceCardEvent.GIVE_CARD_TO_OTHER_PLAYER, i));
            card.addAction(takeAnotherCard.copy());

            allChanceCards.add(card);
        }

        // Make the move to start card
        card = new ChanceCard();
        card.addAction(new ChanceCardAction(ChanceCardEvent.MOVE_TO, 0));
        allChanceCards.add(card);

        // Make the move to last field card
        card = new ChanceCard();
        card.addAction(new ChanceCardAction(ChanceCardEvent.MOVE_TO, 23));
        allChanceCards.add(card);

        // Make the move to and pay card
        card = new ChanceCard();
        card.addChoice(new ChanceCardAction(ChanceCardEvent.MOVE_TO, 10), getForFreeOrPayOwner.copy());
        allChanceCards.add(card);

        // Make the pay the bank card
        card = new ChanceCard();
        card.addAction(new ChanceCardAction(ChanceCardEvent.PAY_BANK, 2));
        allChanceCards.add(card);

        // Make the get money from the bank card
        card = new ChanceCard();
        card.addAction(new ChanceCardAction(ChanceCardEvent.GET_PAID_BY_BANK, 2));
        allChanceCards.add(card);

        // Make the get money from the other players card
        card = new ChanceCard();
        card.addAction(new ChanceCardAction(ChanceCardEvent.GET_PAID_BY_PLAYERS, 1));
        allChanceCards.add(card);

        // Make the move up to 5 squares card
        ChanceCardAction[] moveActions = new ChanceCardAction[5];
        for (int i = 0; i < 5; i++) {
            moveActions[i] = new ChanceCardAction(ChanceCardEvent.MOVE_RELATIVE, i+1);
        }
        card = new ChanceCard();
        card.addChoice(moveActions);
        allChanceCards.add(card);

        // Make the move 1 or take another card, card
        card = new ChanceCard();
        card.addChoice(new ChanceCardAction(ChanceCardEvent.MOVE_RELATIVE, 1), takeAnotherCard.copy());
        allChanceCards.add(card);

        // Make get out of jail free card
        card = new ChanceCard();
        card.addAction(new ChanceCardAction(ChanceCardEvent.GET_OUT_OF_JAIL_FREE, 0));
        allChanceCards.add(card);

        // Make all the move to specific color cards
        PropertyColor[] specificPropertyColors = new PropertyColor[] {PropertyColor.ORANGE, PropertyColor.LIGHT_BLUE, PropertyColor.RED};
        for (int i = 0; i < 3; i++) {
            ChanceCardAction moveToColorAction = new ChanceCardAction(ChanceCardEvent.MOVE_TO_COLOR, specificPropertyColors[i].ordinal());
            card = new ChanceCard();
            card.addAction(moveToColorAction);
            card.addAction(getForFreeOrPayOwner.copy());

            allChanceCards.add(card);
        }

        // Make all the move to choice of cards
        PropertyColor[][] propertyColorChoices = new PropertyColor[][] { {PropertyColor.ORANGE, PropertyColor.GREEN}, {PropertyColor.MAGENTA, PropertyColor.DARK_BLUE}, {PropertyColor.LIGHT_BLUE, PropertyColor.RED}, {PropertyColor.BROWN, PropertyColor.YELLOW}};
        for (int i = 0; i < 4; i++) {
            PropertyColor[] choices = propertyColorChoices[i];
            ChanceCardAction moveToFirstColorAction = new ChanceCardAction(ChanceCardEvent.MOVE_TO_COLOR, choices[0].ordinal());
            ChanceCardAction moveToSecondColorAction = new ChanceCardAction(ChanceCardEvent.MOVE_TO_COLOR, choices[1].ordinal());
            card = new ChanceCard();
            card.addChoice(moveToFirstColorAction, moveToSecondColorAction);
            card.addAction(getForFreeOrPayOwner.copy());

            allChanceCards.add(card);
        }

        // Return the made array
        return allChanceCards;
    }
}
