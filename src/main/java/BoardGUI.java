import gui_fields.*;
import gui_main.GUI;

public class BoardGUI {
    private GUI gui;
    private Board boardLogic;
    private GUI_Player[] players;

    public BoardGUI(GUI_Player[] players, GUI gui) {
        this.gui = gui;
        this.players = players;
        boardLogic = new Board(Board.generateStandardFields(), players.length, 0);

        PlayerMovement moveToStart = new PlayerMovement(0, 0, null);
        for (int i = 0; i < players.length; ++i) {
            updatePlayerGraphicPosition(i, moveToStart);
        }
    }

    public PlayerMovement movePlayerByAmount(int playerId, int moveAmount) {
        PlayerMovement movement = boardLogic.movePlayerByAmount(playerId, moveAmount);
        updatePlayerGraphicPosition(playerId, movement);
        return movement;
    }

    public PlayerMovement movePlayerToField(int playerId, int field) {
        PlayerMovement movement = boardLogic.movePlayerToField(playerId, field);
        updatePlayerGraphicPosition(playerId, movement);
        return movement;
    }

    public boolean playerOwnsBothProperties(int playerId, PropertyColor propertyColor) {
        for (int i = 0; i < boardLogic.NUMBER_OF_FIELDS; ++i) {
            GameField field = boardLogic.getFieldAt(i);
            if (!(field instanceof PropertyField)) {
                continue;
            }
            PropertyField property = (PropertyField)field;
            if (!property.PropertyColor.equals(propertyColor)) {
                continue;
            }
            if (property.getOwner().ID != playerId) {
                return false;
            }
        }
        return true;
    }

    private void updatePlayerGraphicPosition(int playerId, PlayerMovement movement) {
        GUI_Player playerToMove = players[playerId];

        GUI_Field oldField = gui.getFields()[movement.StartIndex];
        GUI_Field newField = gui.getFields()[movement.EndIndex];

        oldField.setCar(playerToMove, false);
        newField.setCar(playerToMove, true);
    }
}
