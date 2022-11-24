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

    public static GameField[] generateStandardFields() {
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
}

