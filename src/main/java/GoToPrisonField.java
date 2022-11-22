public class GoToPrisonField extends GameField {
    @Override
    public LandOnAction landedOn(Player player) {
        return LandOnAction.GO_TO_PRISON;
    }
}
