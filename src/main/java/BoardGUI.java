import gui_fields.*;
import gui_main.GUI;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class BoardGUI{
        static FieldGUI num_Fields = new FieldGUI();

        public static void main(String[] args) {
                //to show fields
                GUI gui = new GUI(num_Fields.Showfields());
                gui.setDice(5,6);
        }
}
