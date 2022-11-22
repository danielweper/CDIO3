import java.lang.reflect.Field;
import java.util.*;

public class Game {
    private int currentPlayer;
    private Player[] players;
    private Board board;
    private Queue<ChanceCard> cards;
    private DieCup cup;

    public Game(Player[] players) {
        this.currentPlayer = 0;
        this.players = players;

        this.board = new Board(generateGameFields(), this.players.length, 0);
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

        Color[] propertyColors = new Color[] {Color.BROWN, Color.LIGHT_BLUE, Color.MAGENTA, Color.ORANGE, Color.RED, Color.YELLOW};

        // Make the first 3 sides of the board
        int fieldIndex = 0;
        for (int i = 0; i < 3; ++i) {
            // Generate the "rest" field (start, visiting & free parking)
            fields[fieldIndex++] = new GameField();
            int propertyPrice = i+1;
            int colorIndex = i * 2;

            // Generate the first pair of properties (brown, magenta & red)
            fields[fieldIndex++] = new PropertyField(propertyPrice, propertyColors[colorIndex]);
            fields[fieldIndex++] = new PropertyField(propertyPrice, propertyColors[colorIndex]);

            // Generate the chance field in the middle of the side
            fields[fieldIndex++] = new ChanceField();

            ++colorIndex;
            // Generate the second pair of properties (light blue, orange & yellow)
            fields[fieldIndex++] = new PropertyField(propertyPrice, propertyColors[colorIndex]);
            fields[fieldIndex++] = new PropertyField(propertyPrice, propertyColors[colorIndex]);
        }

        // Generate the last side
        fields[fieldIndex++] = new GoToPrisonField();
        // Generate the green pair
        fields[fieldIndex++] = new PropertyField(4, Color.GREEN);
        fields[fieldIndex++] = new PropertyField(4, Color.GREEN);
        // Generate the chance field on the last side
        fields[fieldIndex++] = new ChanceField();
        // Generate the dark blue pair
        fields[fieldIndex++] = new PropertyField(5, Color.DARK_BLUE);
        fields[fieldIndex++] = new PropertyField(5, Color.DARK_BLUE);

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
            card.addAction(new ChanceCardAction(ChanceCardEvent.GIVE_TO_OTHER_PLAYER, i));
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
        Color[] specificColors = new Color[] {Color.ORANGE, Color.LIGHT_BLUE, Color.RED};
        for (int i = 0; i < 3; i++) {
            ChanceCardAction moveToColorAction = new ChanceCardAction(ChanceCardEvent.MOVE_TO_COLOR, specificColors[i].ordinal());
            card = new ChanceCard();
            card.addAction(moveToColorAction);
            card.addAction(getForFreeOrPayOwner.copy());

            allChanceCards.add(card);
        }

        // Make all the move to choice of cards
        Color[][] colorChoices = new Color[][] { {Color.ORANGE, Color.GREEN}, {Color.MAGENTA, Color.DARK_BLUE}, {Color.LIGHT_BLUE, Color.RED}, {Color.BROWN, Color.YELLOW}};
        for (int i = 0; i < 4; i++) {
            Color[] choices = colorChoices[i];
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

    public void nextTurn() {
        currentPlayer = (currentPlayer + 1) % 4;
    }

    public void movePlayerBy(int amount) {
        board.movePlayerByAmount(currentPlayer, amount);
    }

    public void movePlayerTo(int newField) {
        board.movePlayerToField(currentPlayer, newField);
    }
}
