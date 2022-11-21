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

    //Add Player
    public static void AddPlayer() {
        for (int i=0; i< MAX_Players; i++){
            //Name and number
        }
    }
    //Movement
    public static void movement() {
        for (int i = 0; i < MAX_Players; i++){

        }
    }
}
