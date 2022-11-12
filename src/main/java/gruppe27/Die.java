package gruppe27;

public abstract class Die {
    protected int face;

    public abstract void roll();

    public int getFace() {
        return face;
    }

    @Override
    public String toString() {
        return "" + getFace();
    }
}
