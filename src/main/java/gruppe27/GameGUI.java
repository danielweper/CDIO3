package gruppe27;
import javax.swing.*;

public class GameGUI extends JFrame{
    private JPanel Board;
    private JPanel BoardMid;
    private JPanel Start;

    public GameGUI(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(Board);
        this.pack();
    }

    public static void main(String[] args) {
        JFrame game = new GameGUI("Try 1");
        game.setVisible(true);
    }

}
