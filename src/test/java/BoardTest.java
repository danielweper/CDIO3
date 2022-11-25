import junit.framework.TestCase;

public class BoardTest extends TestCase {
    public void testPlayerCantGoTroughTheLastField() {
        GameField[] arr = new GameField[11];
        Board board = new Board(arr, 3,10);
        board.movePlayerByAmount(0, 4);

        int expected = 3;
        int actual = board.getPlayerPosition(0);
        assertEquals(expected, actual);
    }

    public void testPlayerMoveToTheExpectedField() {
        GameField[] arr = new GameField[11];
        Board board = new Board(arr, 2,3);
        board.movePlayerByAmount(1, 3);

        int expected = 6;
        int actual = board.getPlayerPosition(1);
        assertEquals(expected, actual);
    }
}