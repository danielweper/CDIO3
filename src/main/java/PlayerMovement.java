public class PlayerMovement {
    public final int StartIndex;
    public final int EndIndex;
    public final boolean PassedStart;

    public PlayerMovement(int startIndex, int endIndex) {
        this.StartIndex = startIndex;
        this.EndIndex = endIndex;
        this.PassedStart = endIndex < startIndex;
    }
}
