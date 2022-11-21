//taget fra CDIO 2
import java.util.Random;
public class SixSidedDie extends Die {
    private final Random rng;

    public SixSidedDie() {
        rng = new Random();
        roll();
    }
    public void roll() {
        face = rng.nextInt(6) + 1;
    }
}
