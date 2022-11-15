public class PropertyField extends GameField{
    int fieldPrice;
    Color color;
    Player owner;
    public PropertyField(int fieldPris,Color color){
        this.fieldPrice =fieldPris;
        this.color=color;
        this.owner=null;
    }
    public int  assessment(){
        return fieldPrice;
    }
    public Color fieldColor(){
        return color;
    }

    @Override
    public LandOnAction landedOn(Player player) {
        if(owner==null)
        return LandOnAction.BUY_PROPERTY;
        if(owner.equals(player))
            return LandOnAction.NOTHING;
        return LandOnAction.PAY_RENT;
    }

}
