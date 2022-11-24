import gui_fields.GUI_Field;
import gui_fields.GUI_Start;
import gui_fields.GUI_Street;
import gui_fields.GUI_Chance;
import gui_fields.GUI_Jail;
import gui_fields.GUI_Refuge;

import java.awt.Color;

public class FieldGUI {
    private GUI_Field[] fields = new GUI_Field[24]; //There are 24 fields
    private static final Color brown = new Color(0x993300);

    public GUI_Field[] Showfields() {
        fields[0] = new GUI_Start("Start","","",Color.ORANGE, Color.WHITE);
        fields[0].setSubText("Start her");

        fields[1] = new GUI_Street();
        fields[1].setTitle("Burger place");
        fields[1].setSubText("1");
        fields[1].setBackGroundColor(brown);

        fields[2] = new GUI_Street();
        fields[2].setTitle("Pizzeria");
        fields[2].setSubText("1");
        fields[2].setBackGroundColor(brown);

        fields[3] = new GUI_Chance();
        fields[3].setTitle("Chance");
        fields[3].setSubText("");
        fields[3].setForeGroundColor(Color.ORANGE); //foregrund
        fields[3].setBackGroundColor(Color.WHITE); //Background

        fields[4] = new GUI_Street();
        fields[4].setTitle("Candy store");
        fields[4].setSubText("1");
        fields[4].setBackGroundColor(Color.CYAN);

        fields[5] = new GUI_Street();
        fields[5].setTitle("Ice cream stand");
        fields[5].setSubText("1");
        fields[5].setBackGroundColor(Color.CYAN);

        fields[6] = new GUI_Jail();
        fields[6].setTitle("Jail");
        fields[6].setSubText("Visiting");

        fields[7] = new GUI_Street();
        fields[7].setTitle("Museum");
        fields[7].setSubText("2");
        fields[7].setBackGroundColor(Color.MAGENTA);

        fields[8] = new GUI_Street();
        fields[8].setTitle("Library");
        fields[8].setSubText("2");
        fields[8].setBackGroundColor(Color.MAGENTA);

        fields[9] = new GUI_Chance();
        fields[9].setTitle("Chance");
        fields[9].setSubText("");
        fields[9].setForeGroundColor(Color.ORANGE); //foregrund
        fields[9].setBackGroundColor(Color.WHITE); //Background

        fields[10] = new GUI_Street();
        fields[10].setTitle("Skate park");
        fields[10].setSubText("2");
        fields[10].setBackGroundColor(Color.ORANGE);

        fields[11] = new GUI_Street();
        fields[11].setTitle("Swimming pool");
        fields[11].setSubText("2");
        fields[11].setBackGroundColor(Color.ORANGE);

        fields[12] = new GUI_Refuge();
        fields[12].setTitle("Free parking");
        fields[12].setSubText("Free parking");
        fields[12].setBackGroundColor(Color.WHITE);

        fields[13] = new GUI_Street();
        fields[13].setTitle("Arcade");
        fields[13].setSubText("3");
        fields[13].setBackGroundColor(Color.RED);

        fields[14] = new GUI_Street();
        fields[14].setTitle("Cinema");
        fields[14].setSubText("3");
        fields[14].setBackGroundColor(Color.RED);

        fields[15] = new GUI_Chance();
        fields[15].setTitle("Chance");
        fields[15].setSubText("");
        fields[15].setForeGroundColor(Color.ORANGE); //foregrund
        fields[15].setBackGroundColor(Color.WHITE); //Background

        fields[16] = new GUI_Street();
        fields[16].setTitle("Toy store");
        fields[16].setSubText("3");
        fields[16].setBackGroundColor(Color.YELLOW);

        fields[17] = new GUI_Street();
        fields[17].setTitle("Pet shop");
        fields[17].setSubText("3");
        fields[17].setBackGroundColor(Color.YELLOW);

        fields[18] = new GUI_Jail();
        fields[18].setTitle("Go to jail");
        fields[18].setSubText("Go to jail");
        fields[18].setBackGroundColor(Color.GRAY);

        fields[19] = new GUI_Street();
        fields[19].setTitle("Bowling ally");
        fields[19].setSubText("4");
        fields[19].setBackGroundColor(Color.GREEN);

        fields[20] = new GUI_Street();
        fields[20].setTitle("Zoo");
        fields[20].setSubText("4");
        fields[20].setBackGroundColor(Color.GREEN);

        fields[21] = new GUI_Chance();
        fields[21].setTitle("Chance");
        fields[21].setSubText("");
        fields[21].setForeGroundColor(Color.ORANGE); //foregrund
        fields[21].setBackGroundColor(Color.WHITE); //Background

        fields[22] = new GUI_Street();
        fields[22].setTitle("Water park");
        fields[22].setSubText("5");
        fields[22].setBackGroundColor(Color.BLUE);

        fields[23] = new GUI_Street();
        fields[23].setTitle("The beach");
        fields[23].setSubText("5");
        fields[23].setBackGroundColor(Color.BLUE);

        return fields;
    }
}
