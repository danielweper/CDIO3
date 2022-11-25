import java.util.ArrayList;

public class ChanceCard {
    private ArrayList<ChanceCardAction[]> actions;
    private boolean hasChoice;

    public ChanceCard() {
        this.actions = new ArrayList<>();
        hasChoice = false;
    }

    public void addAction(ChanceCardAction action) {
        this.actions.add(new ChanceCardAction[]{action});
    }

    public void addChoice(ChanceCardAction... actions) {
        this.actions.add(actions);
        if (actions.length > 1) {
            hasChoice = true;
        }
    }

    public ArrayList<ChanceCardAction[]> getActions() {
        return actions;
    }

    public boolean isChoiceCard() {
        return hasChoice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Chance Card: ");

        sb.append(formatActionArray(actions.get(0)));

        for (int i = 1; i < actions.size(); i++) {
            ChanceCardAction[] cardActions = actions.get(i);
            if (cardActions.length > 1) {
                sb.append('(');
                sb.append(formatActionArray(cardActions));
                sb.append(')');
            }
            else {
                sb.append(" then ");
                sb.append(formatActionArray(cardActions));
            }
        }

        return sb.toString();
    }

    private String formatActionArray(ChanceCardAction[] actions) {
        StringBuilder sb = new StringBuilder();

        if (actions.length > 1) {
            sb.append('(');
            sb.append('"');
            sb.append(actions[0]);
            sb.append('"');
            for (int i = 1; i < actions.length; i++) {
                sb.append(" or ");
                sb.append('"');
                sb.append(actions[i]);
                sb.append('"');
            }
            sb.append(')');
        }
        else {
            sb.append('"');
            sb.append(actions[0].toString());
            sb.append('"');
        }

        return sb.toString();
    }
}
