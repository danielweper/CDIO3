package gruppe27;

public class PropertyField extends GameField{
    int fieldPris;
    public PropertyField(int fieldPris){
        this.fieldPris=fieldPris;

    }

    public int  vurdering(){
        return (fieldPris);
    }

}
