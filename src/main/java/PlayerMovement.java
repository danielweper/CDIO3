public class PlayerMovement {
    public final int StartIndex;
    public final int EndIndex;
    public final boolean PassedStart;
    public final GameField EndField;

    public PlayerMovement(int startIndex, int endIndex, GameField endField) {
        this.StartIndex = startIndex;
        this.EndIndex = endIndex;
        this.PassedStart = endIndex < startIndex;
        this.EndField = endField;
    }

    @Override
    public String toString() {
        return String.format("Moving from %d to %d (%s)", StartIndex, EndIndex, EndField.toString());
    }
}
