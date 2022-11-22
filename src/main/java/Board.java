public class Board {
    private int numberOfFields;
    private GameField[] fields;
    private int[] playerIndexesOnBoard;

    Board(GameField[] fields, int numberOfPlayers, int startField) {
        this.fields = fields;
        this.numberOfFields = fields.length;
        this.playerIndexesOnBoard = new int[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            this.playerIndexesOnBoard[i] = startField;
        }
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
}

