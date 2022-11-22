import gui_fields.*;
import gui_main.GUI;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class BoardGUI{
        static FieldGUI num_Fields = new FieldGUI();
        static GUI_Player[] players;
        static int[] playerPositions;

        public static void main(String[] args) {
                GUI gui = new GUI(num_Fields.Showfields());
                SixSidedDie d1 = new SixSidedDie();
                SixSidedDie d2 = new SixSidedDie();

                int playerAmount = 2;

                players = makePlayers(playerAmount, gui);
                playerPositions = new int[playerAmount];

                GUI_Field startField = gui.getFields()[0];
                for (int i = 0; i < playerAmount; i++) {
                        startField.setCar(players[i], true);
                }

                //https://github.com/diplomit-dtu/MatadorGUIGuide/blob/3.2.x/src/main/java/Terning.java
                int currentPlayer = 0;
                while (true){
                        String choice = gui.getUserButtonPressed("",  "With two die");
                        if (choice.equals("With two die") ){
                                d1.roll();
                                d2.roll();
                                gui.setDice(d1.face, d2.face);
                        }
                        int sum = d1.face + d2.face;

                        movePlayer(currentPlayer, sum, gui);
                        currentPlayer = (currentPlayer + 1) % playerAmount;
                }

        }
        public static GUI_Player[] makePlayers(int amount, GUI gui) {
                int startingBalance = (amount == 2) ? 20 : ((amount == 3) ? 18 : 16);
                ArrayList<Color> colors = new ArrayList<Color>(Arrays.stream(new Color[] {Color.red, Color.blue, Color.black, Color.green, Color.magenta, Color.yellow}).toList());
                ArrayList<String> colorStrings = new ArrayList<>();
                for (Color color : colors) {
                        colorStrings.add(color.toString());
                }

                GUI_Player[] players = new GUI_Player[amount];
                for (int i=0; i < amount; i++) {
                        String playerName = gui.getUserString("Player " + (i+1) + " what is your name?");
                        String[] colorStringArray = new String[colorStrings.size()];
                        String colorString = gui.getUserButtonPressed("Choose a color", colorStrings.toArray(colorStringArray));
                        Color playerColor = colors.get(colorStrings.indexOf(colorString));

                        GUI_Car playerCar = new GUI_Car(playerColor, playerColor, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL);
                        players[i] = new GUI_Player(playerName, startingBalance, playerCar);

                        colors.remove(playerColor);
                        colorStrings.remove(colorString);
                        gui.addPlayer(players[i]);
                }
                return players;
        }
        public static void movePlayer(int playerIndex, int newPos, GUI gui){
                int oldPos = playerPositions[playerIndex];
                GUI_Player playerToMove = players[playerIndex];
                GUI_Field oldField = gui.getFields()[oldPos];
                oldField.setCar(playerToMove, false);
                GUI_Field targetField = gui.getFields()[newPos];
                targetField.setCar(playerToMove, true);

                playerPositions[playerIndex] = newPos;
        }
}
