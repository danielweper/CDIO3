public class Board {
    private int numberOfFields;
    private GameField[] fields;
    private int[] playerIndexesOnBoard;
    public final int NUMBER_OF_FIELDS;

    Board(GameField[] fields, int numberOfPlayers, int startField) {
        this.fields = fields;
        this.numberOfFields = fields.length;
        this.playerIndexesOnBoard = new int[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            this.playerIndexesOnBoard[i] = startField;
        }

        this.NUMBER_OF_FIELDS = fields.length;
    }

    public static GameField[] generateStandardFields() {
        GameField[] GameField = new GameField[0];
        return GameField;
    }


    public PlayerMovement movePlayerByAmount(int playerByIndex, int moveAmount) {
        int currentPlayerIndex = this.playerIndexesOnBoard[playerByIndex];
        int newIndex = (currentPlayerIndex + moveAmount) % numberOfFields;

        return movePlayerToField(playerByIndex, newIndex);
    }

    public PlayerMovement movePlayerToField(int playerByIndex, int field) {
        int start = playerIndexesOnBoard[playerByIndex];
        this.playerIndexesOnBoard[playerByIndex] = field;
        return new PlayerMovement(start, field, fields[field]);
    }

    public GameField getFieldAt(int index) {
        return fields[index];
    }

    public int getPlayerPosition(int playerByIndex) {
        return 0;
    }
}

