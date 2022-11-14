package gruppe27;

import java.util.*;

public class Game {
    private Player[] players;
    private Board board;
    private Queue<ChanceCard> cards;
    private DieCup cup;

    public Game(int playerCount) {
        this.players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player("", 20);
        }

        this.board = new Board(24);
        this.cards = new LinkedList<>();

        Random rng = new Random();
        List<ChanceCard> chanceCards = generateChanceCards();
        while (chanceCards.size() > 0) {
            int cardIndex = rng.nextInt(chanceCards.size());
            cards.add(chanceCards.remove(cardIndex));
        }

        // TODO add cup?
    }

    private List<ChanceCard> generateChanceCards() {
        List<ChanceCard> allChanceCards = new ArrayList<>();
        // Commonly used
        ChanceCardAction takeAnotherCard = new ChanceCardAction(ChanceCardEvent.TAKE_ANOTHER_CARD, 1);

        // Make all the give to other players cards
        for (int i = 0; i < 4; i++) {
            allChanceCards.add(ChanceCard.MultiActionCard(new ChanceCardAction(ChanceCardEvent.GIVE_TO_OTHER_PLAYER, i), takeAnotherCard.copy()));
        }

        // Make the 2 move to (without any consequence) cards
        allChanceCards.add(ChanceCard.SingleActionCard(new ChanceCardAction(ChanceCardEvent.MOVE_TO, 0)));
        allChanceCards.add(ChanceCard.SingleActionCard(new ChanceCardAction(ChanceCardEvent.MOVE_TO, 23)));

        // Make the move to and pay card
        allChanceCards.add(ChanceCard.MultiActionCard(new ChanceCardAction(ChanceCardEvent.MOVE_TO, 10), new ChanceCardAction(ChanceCardEvent.GET_PROPERTY_FREE_OR_PAY_OWNER, 0)));

        // Make the pay and get paid cards
        allChanceCards.add(ChanceCard.SingleActionCard(new ChanceCardAction(ChanceCardEvent.PAY_BANK, 2)));
        allChanceCards.add(ChanceCard.SingleActionCard(new ChanceCardAction(ChanceCardEvent.GET_PAID_BY_BANK, 2)));
        allChanceCards.add(ChanceCard.SingleActionCard(new ChanceCardAction(ChanceCardEvent.GET_PAID_BY_PLAYERS, 1)));

        // Make the move up to 5 squares card
        ChanceCardAction[] moveActions = new ChanceCardAction[5];
        for (int i = 0; i < 5; i++) {
            moveActions[i] = new ChanceCardAction(ChanceCardEvent.MOVE_RELATIVE, i+1);
        }
        allChanceCards.add(ChanceCard.MultiChoiceCard(moveActions));

        // Make the move 1 or take another card, card
        allChanceCards.add(ChanceCard.MultiChoiceCard(new ChanceCardAction(ChanceCardEvent.MOVE_RELATIVE, 1), takeAnotherCard.copy()));

        // Make get out of jail free card
        allChanceCards.add(ChanceCard.SingleActionCard(new ChanceCardAction(ChanceCardEvent.GET_OUT_OF_JAIL_FREE, 0)));

        // Make all the move to specific color cards
        // TODO

        // Return the made array
        return allChanceCards;
    }
}
