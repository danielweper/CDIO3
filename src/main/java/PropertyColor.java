public enum PropertyColor {
    BROWN,
    LIGHT_BLUE,
    MAGENTA,
    ORANGE,
    RED,
    YELLOW,
    GREEN,
    DARK_BLUE;

    @Override
    public String toString() {
        String representation = super.toString().replaceAll("_", " ");
        return representation.substring(0, 1).toUpperCase() + representation.substring(1).toLowerCase();
    }
}
