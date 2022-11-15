import javax.swing.*;

public class GameGUI extends JFrame{
    private JPanel Board;
    private JPanel BoardMid;
    private JPanel Start;
    private JPanel Field1;

    public GameGUI(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(Board);
        this.pack();
    }

    public static void main(String[] args) {
        JFrame game = new JFrame("Monopoly Junior");
        game.setContentPane(new GameGUI("GAME").Board);
        game.setSize(1920,1080);
        game.setVisible(true);
    }

}
