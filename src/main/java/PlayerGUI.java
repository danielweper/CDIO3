import gui_fields.GUI_Car;
import gui_fields.GUI_Player;

public class PlayerGUI extends GUI_Player{

    public PlayerGUI(String name){
        super(name);
    }
    public PlayerGUI(String name,int balance){
        super(name, balance);
    }
    public PlayerGUI(String name,int balance, GUI_Car car){
        super(name, balance, car);
    }


}
