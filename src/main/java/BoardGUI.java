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
}
