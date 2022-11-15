package gruppe27;

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


    public GameField movePlayerByAmount(int playerByIndex, int moveAmount) {
        int currentPlayerIndexOnBoard = this.playerIndexesOnBoard[playerByIndex];
        int newIndexOnBoard = currentPlayerIndexOnBoard + moveAmount;


        newIndexOnBoard = newIndexOnBoard % numberOfFields;

        return fields[newIndexOnBoard];

    }

    public GameField movePlayerToField(int playerByIndex, int field) {
        this.playerIndexesOnBoard[playerByIndex] = field;
        return fields[field];
    }


}

