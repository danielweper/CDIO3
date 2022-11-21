import gui_fields.GUI_Field;
import gui_fields.GUI_Start;
import gui_fields.GUI_Street;
import gui_fields.GUI_Chance;
import gui_fields.GUI_Jail;
import gui_fields.GUI_Refuge;

import java.awt.Color;

public class FieldGUI {
    private GUI_Field[] fields = new GUI_Field[24]; //There are 24 fields


    public GUI_Field[] Showfields() {
        fields[0] = new GUI_Start();
        //Color?
        fields[0].setTitle("");
        fields[0].setSubText("");

        fields[1] = new GUI_Street();
        //Color?
        fields[1].setTitle("");
        fields[1].setSubText("");

        fields[2] = new GUI_Street();
        //Color?
        fields[2].setTitle("");
        fields[2].setSubText("");

        fields[3] = new GUI_Chance();
        fields[3].setTitle("");
        fields[3].setSubText("");

        fields[4] = new GUI_Street();
        //Color?
        fields[4].setTitle("");
        fields[4].setSubText("");

        fields[5] = new GUI_Street();
        //Color?
        fields[5].setTitle("");
        fields[5].setSubText("");

        fields[6] = new GUI_Jail();
        fields[6].setTitle("");
        fields[6].setSubText("");

        fields[7] = new GUI_Street();
        //Color?
        fields[7].setTitle("");
        fields[7].setSubText("");

        fields[8] = new GUI_Street();
        //Color?
        fields[8].setTitle("");
        fields[8].setSubText("");

        fields[9] = new GUI_Chance();
        fields[9].setTitle("");
        fields[9].setSubText("");

        fields[10] = new GUI_Street();
        //Color?
        fields[10].setTitle("");
        fields[10].setSubText("");

        fields[11] = new GUI_Street();
        //Color?
        fields[11].setTitle("");
        fields[11].setSubText("");

        fields[12] = new GUI_Refuge();
        //Color?
        fields[12].setTitle("");
        fields[12].setSubText("");

        fields[13] = new GUI_Street();
        //Color?
        fields[13].setTitle("");
        fields[13].setSubText("");

        fields[14] = new GUI_Street();
        //Color?
        fields[14].setTitle("");
        fields[14].setSubText("");

        fields[15] = new GUI_Chance();
        fields[15].setTitle("");
        fields[15].setSubText("");

        fields[16] = new GUI_Street();
        //Color?
        fields[16].setTitle("");
        fields[16].setSubText("");

        fields[17] = new GUI_Street();
        //Color?
        fields[17].setTitle("");
        fields[17].setSubText("");

        fields[18] = new GUI_Jail();
        fields[18].setTitle("");
        fields[18].setSubText("");

        fields[19] = new GUI_Street();
        //Color?
        fields[19].setTitle("");
        fields[19].setSubText("");

        fields[20] = new GUI_Street();
        //Color?
        fields[20].setTitle("");
        fields[20].setSubText("");

        fields[21] = new GUI_Chance();
        fields[21].setTitle("");
        fields[21].setSubText("");

        fields[22] = new GUI_Street();
        //Color?
        fields[22].setTitle("");
        fields[22].setSubText("");

        fields[23] = new GUI_Street();
        //Color?
        fields[23].setTitle("");
        fields[23].setSubText("");

        return fields;
    }


    public static int FieldSize(){
        return 24;
    }

}
