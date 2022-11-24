import gui_fields.*;
import gui_main.GUI;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

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
            setPlayerPosition(i, moveToStart);
        }
    }

    public void setPlayerPosition(int playerIndex, PlayerMovement movement) {
        GUI_Player playerToMove = players[playerIndex];

        GUI_Field oldField = gui.getFields()[movement.StartIndex];
        GUI_Field newField = gui.getFields()[movement.EndIndex];

        oldField.setCar(playerToMove, false);
        newField.setCar(playerToMove, true);

        boardLogic.movePlayerToField(playerIndex, movement.EndIndex);
    }

    public PlayerMovement movePlayerByAmount(int playerIndex, int moveAmount) {
        PlayerMovement movement = boardLogic.movePlayerByAmount(playerIndex, moveAmount);
        setPlayerPosition(playerIndex, movement);
        return movement;
    }

    public PlayerMovement movePlayerToField(int playerIndex, int field) {
        PlayerMovement movement = boardLogic.movePlayerToField(playerIndex, field);
        setPlayerPosition(playerIndex, movement);
        return movement;
    }
}
