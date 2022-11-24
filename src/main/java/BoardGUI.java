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

    public PlayerMovement movePlayerByAmount(int playerIndex, int moveAmount) {
        PlayerMovement movement = boardLogic.movePlayerByAmount(playerIndex, moveAmount);
        updatePlayerGraphicPosition(playerIndex, movement);
        return movement;
    }

    public PlayerMovement movePlayerToField(int playerIndex, int field) {
        PlayerMovement movement = boardLogic.movePlayerToField(playerIndex, field);
        updatePlayerGraphicPosition(playerIndex, movement);
        return movement;
    }

    private void updatePlayerGraphicPosition(int playerIndex, PlayerMovement movement) {
        GUI_Player playerToMove = players[playerIndex];

        GUI_Field oldField = gui.getFields()[movement.StartIndex];
        GUI_Field newField = gui.getFields()[movement.EndIndex];

        oldField.setCar(playerToMove, false);
        newField.setCar(playerToMove, true);
    }
}
