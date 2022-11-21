import gui_fields.*;
import gui_main.GUI;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class BoardGUI{
        static FieldGUI num_Fields = new FieldGUI();
        private DieCup cup;

        public static void main(String[] args) {
                //to show fields
                GUI gui = new GUI(num_Fields.Showfields());
                //GUI guiDie = new GUI();
                SixSidedDie d1 = new SixSidedDie();
                SixSidedDie d2 = new SixSidedDie();


                //gui.setDice(d1.face,d2.face);

                while (true){
                        String choice = gui.getUserButtonPressed("Pick one",  "With two die");
                        if (choice.equals("With two die") ){
                                gui.setDice(d1.face, d2.face);
                        }
                }

        }
}
