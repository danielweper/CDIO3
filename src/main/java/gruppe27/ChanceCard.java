package gruppe27;

public class ChanceCard {
    public final ChanceCardAction[] Actions;
    public final boolean HasChoice;

    private ChanceCard(ChanceCardAction[] actions, boolean hasChoice) {
        this.Actions = actions;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Chance Card: ");
        sb.append('"');
        sb.append(Actions[0]);
        sb.append('"');

        String separator = HasChoice ? " or " : " then ";
        for (int i = 1; i < Actions.length; i++) {
            sb.append(separator);
            sb.append('"');
            sb.append(Actions[i]);
            sb.append('"');
        }

        return sb.toString();
    }
}
