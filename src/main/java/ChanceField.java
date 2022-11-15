public class ChanceField extends GameField {
    @Override
    public LandOnAction landedOn(Player player) {
        return LandOnAction.DRAW_CHANCE_CARD;
    }
}
