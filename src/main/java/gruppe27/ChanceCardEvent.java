package gruppe27;

public enum ChanceCardEvent {
    GIVE_TO_OTHER_PLAYER,
    TAKE_ANOTHER_CARD,
    MOVE_TO,
    MOVE_RELATIVE,
    MOVE_TO_COLOR,
    PAY_BANK,
    GET_PAID_BY_BANK,
    GET_PAID_BY_PLAYERS,
    GET_OUT_OF_JAIL_FREE,
    GET_PROPERTY_FREE_OR_PAY_OWNER;

    @Override
    public String toString() {
        String[] words = super.toString().split("_");
        StringBuilder sb = new StringBuilder(words[0].substring(0, 1).toUpperCase());
        sb.append(words[0].substring(1).toLowerCase());

        for (int i = 1; i < words.length; i++) {
            sb.append(' ');
            sb.append(words[i].substring(0, 1).toUpperCase());
            sb.append(words[i].substring(1).toLowerCase());
        }

        return sb.toString();
    }
}
