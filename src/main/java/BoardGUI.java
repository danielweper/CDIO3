import gui_fields.*;
import gui_main.GUI;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class BoardGUI{
        static FieldGUI num_Fields = new FieldGUI();

        public static void main(String[] args) {
                GUI gui = new GUI(num_Fields.Showfields());
                SixSidedDie d1 = new SixSidedDie();
                SixSidedDie d2 = new SixSidedDie();

                Player[] players = makePlayers(4, gui);


                //https://github.com/diplomit-dtu/MatadorGUIGuide/blob/3.2.x/src/main/java/Terning.java
                while (true){
                        String choice = gui.getUserButtonPressed("",  "With two die");
                        if (choice.equals("With two die") ){
                                d1.roll();
                                d2.roll();
                                gui.setDice(d1.face, d2.face);
                        }
                }

        }
        public static Player[] makePlayers(int amount, GUI gui) {
                int startingBalance = (amount == 2) ? 20 : ((amount == 3) ? 18 : 16);
                Player[] players = new Player[amount];
                for (int i=0; i < amount; i++){
                        players[i] = new Player(gui.getUserString("Player " + (i+1) + " what is your name?"), startingBalance, i);
                }
                return players;
        }
}
