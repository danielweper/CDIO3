package gruppe27;
import javax.swing.*;

public class GameGUI extends JFrame{
    private JPanel Board;
    private JPanel BoardMid;
    private JPanel Start;
    private JPanel Field1;

    public GameGUI(String title){
        super(title);
        this.setSize(1000,1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(Board);
        this.pack();
    }

    public static void main(String[] args) {
        JFrame game = new JFrame("Try 1");
        game.setContentPane(new GameGUI("GAME").Board);
        game.setVisible(true);
    }

}
