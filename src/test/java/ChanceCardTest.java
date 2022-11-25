import junit.framework.TestCase;

public class ChanceCardTest extends TestCase {
    public void testChanceCardWithChoiceIsChoiceCard() {
        ChanceCard card = new ChanceCard();
        card.addChoice(new ChanceCardAction(ChanceCardEvent.MOVE_TO, 1), new ChanceCardAction(ChanceCardEvent.MOVE_TO, 1));

        assertTrue(card.isChoiceCard());
    }
    public void testChanceCardWithoutChoiceIsNotChoiceCard() {
        ChanceCard card = new ChanceCard();
        card.addAction(new ChanceCardAction(ChanceCardEvent.MOVE_TO, 1));
        card.addAction(new ChanceCardAction(ChanceCardEvent.MOVE_TO, 1));

        assertFalse(card.isChoiceCard());
    }
}