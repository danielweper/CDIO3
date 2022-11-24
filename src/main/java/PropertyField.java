public class PropertyField extends GameField{
    public final int Value;
    public final Color Color;
    private Player owner;
    public PropertyField(int fieldPris, Color color){
        this.Value = fieldPris;
        this.Color = color;
        this.owner = null;
    }

    public void setOwner(Player newOwner) {
        this.owner = newOwner;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public LandOnAction landedOn(Player player) {
        if (owner == null) {
            return LandOnAction.BUY_PROPERTY;
        }
        if (owner.equals(player)) {
            return LandOnAction.NOTHING;
        }
        return LandOnAction.PAY_RENT;
    }
}
