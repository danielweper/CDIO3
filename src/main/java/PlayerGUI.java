import gui_fields.GUI_Car;
import gui_fields.GUI_Player;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class PlayerGUI extends GUI_Player{
    private String Name;
    private static final int MAX_Players = 4;

    public PlayerGUI(String name){
        super(name);
    }
    public PlayerGUI(String name,int balance){
        super(name, balance);
    }
    public PlayerGUI(String name,int balance, GUI_Car car){
        super(name, balance, car);
    }

    //For loop


    //Movement

    public static void movement(String[] args) {
        for (int i = 0; i < MAX_Players; i++){

        }
    }
}
