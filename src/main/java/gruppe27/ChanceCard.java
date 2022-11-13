package gruppe27;

public class ChanceCard {
    private final ChanceCardAction[] actions;
    public final boolean HasChoice;

    private ChanceCard(ChanceCardAction[] actions, boolean hasChoice) {
        this.actions = actions;
        this.HasChoice = hasChoice;
    }

    public static ChanceCard SingleActionCard(ChanceCardAction action) {
        return new ChanceCard(new ChanceCardAction[] {action}, false);
    }

    public static ChanceCard MultiActionCard(ChanceCardAction... actions) {
        return new ChanceCard(actions, false);
    }

    public static ChanceCard MultiChoiceCard(ChanceCardAction... possibilities) {
        return new ChanceCard(possibilities, true);
    }

    public ChanceCardAction[] getActions() {
        return actions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Chance Card: ");
        sb.append('"');
        sb.append(actions[0]);
        sb.append('"');

        String separator = HasChoice ? " or " : " then ";
        for (int i = 1; i < actions.length; i++) {
            sb.append(separator);
            sb.append('"');
            sb.append(actions[i]);
            sb.append('"');
        }

        return sb.toString();
    }
}
