import java.util.*;

public class Game {
    private int currentPlayer;
    private Player[] players;
    private boolean[] playerInPrison;
    private Board board;
    private Queue<ChanceCard> cards;

    public Game(Player[] players) {
        this.currentPlayer = 0;
        this.players = players;
        this.playerInPrison = new boolean[players.length];

        this.board = new Board(generateGameFields(), players.length, 0);
        this.cards = new LinkedList<>();

        Random rng = new Random();
        List<ChanceCard> chanceCards = generateChanceCards();
        while (chanceCards.size() > 0) {
            int cardIndex = rng.nextInt(chanceCards.size());
            cards.add(chanceCards.remove(cardIndex));
        }

        // TODO add cup?
    }

    private GameField[] generateGameFields() {
        // Generate all the fields in the board
        GameField[] fields = new GameField[24];

        PropertyColor[] propertyPropertyColors = new PropertyColor[] {PropertyColor.BROWN, PropertyColor.LIGHT_BLUE, PropertyColor.MAGENTA, PropertyColor.ORANGE, PropertyColor.RED, PropertyColor.YELLOW};

        // Make the first 3 sides of the board
        int fieldIndex = 0;
        for (int i = 0; i < 3; ++i) {
            // Generate the "rest" field (start, visiting & free parking)
            fields[fieldIndex++] = new GameField();
            int propertyPrice = i+1;
            int colorIndex = i * 2;

            // Generate the first pair of properties (brown, magenta & red)
            fields[fieldIndex++] = new PropertyField(propertyPrice, propertyPropertyColors[colorIndex]);
            fields[fieldIndex++] = new PropertyField(propertyPrice, propertyPropertyColors[colorIndex]);

            // Generate the chance field in the middle of the side
            fields[fieldIndex++] = new ChanceField();

            ++colorIndex;
            // Generate the second pair of properties (light blue, orange & yellow)
            fields[fieldIndex++] = new PropertyField(propertyPrice, propertyPropertyColors[colorIndex]);
            fields[fieldIndex++] = new PropertyField(propertyPrice, propertyPropertyColors[colorIndex]);
        }

        // Generate the last side
        fields[fieldIndex++] = new GoToPrisonField();
        // Generate the green pair
        fields[fieldIndex++] = new PropertyField(4, PropertyColor.GREEN);
        fields[fieldIndex++] = new PropertyField(4, PropertyColor.GREEN);
        // Generate the chance field on the last side
        fields[fieldIndex++] = new ChanceField();
        // Generate the dark blue pair
        fields[fieldIndex++] = new PropertyField(5, PropertyColor.DARK_BLUE);
        fields[fieldIndex++] = new PropertyField(5, PropertyColor.DARK_BLUE);

        return fields;
    }

    private List<ChanceCard> generateChanceCards() {
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

    public Player getCurrentPlayer() {
        return players[currentPlayer];
    }

    public Player getOwnerOfSet(PropertyColor propertyColorOfSet) {
        Player owner1 = null;
        Player owner2 = null;
        for (int i = 0; i < board.NUMBER_OF_FIELDS; i++) {
            GameField field = board.getFieldAt(i);
            if (!(field instanceof PropertyField)) {
                continue;
            }
            PropertyField property = (PropertyField)field;
            if (!property.PropertyColor.equals(propertyColorOfSet)) {
                if (owner1 == null) {
                    owner1 = property.getOwner();
                }
                else {
                    owner2 = property.getOwner();
                }
            }
        }

        if (owner1 == null || owner1.equals(owner2)) {
            return owner1;
        }
        return null;
    }

    public void setPlayerInPrison(int playerIndex) {
        playerInPrison[playerIndex] = true;
    }

    public void releasePlayerFromPrison(int playerIndex) {
        playerInPrison[playerIndex] = false;
    }

    public boolean isPlayerInPrison(int playerIndex) {
        return playerInPrison[playerIndex];
    }

    public void nextTurn() {
        currentPlayer = (currentPlayer + 1) % players.length;
    }

    public ChanceCard drawChanceCard() {
        return cards.remove();
    }

    public void insertChanceCard(ChanceCard card) {
        this.cards.add(card);
    }

    public PlayerMovement movePlayerBy(int amount) {
        PlayerMovement movement = board.movePlayerByAmount(currentPlayer, amount);

        // Pay the player if the passed start
        if (movement.PassedStart) {
            getCurrentPlayer().updateBalance(2);
        }

        return movement;
    }

    public PlayerMovement movePlayerTo(int newField) {
        PlayerMovement movement = board.movePlayerToField(currentPlayer, newField);

        // Pay the player if the passed start
        if (movement.PassedStart) {
            getCurrentPlayer().updateBalance(2);
        }
        return movement;
    }

    public void setPlayerPositionTo(int newPosition) {
        board.movePlayerToField(currentPlayer, newPosition);
    }
}
