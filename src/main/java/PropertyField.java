public class PropertyField extends GameField{
    int fieldPrice;
    Color color;
    public PropertyField(int fieldPris,Color color){
        this.fieldPrice =fieldPris;
        this.color=color;
    }
    public int  assessment(){
        return fieldPrice;
    }
    public Color type(){
        return color;
    }

}
