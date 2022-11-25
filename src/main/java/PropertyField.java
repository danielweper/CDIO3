public class PropertyField extends GameField{
    public final int Value;
    public final PropertyColor PropertyColor;
    private Player owner;
    public PropertyField(int fieldPris, PropertyColor propertyColor){
        this.Value = fieldPris;
        this.PropertyColor = propertyColor;
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
