package gruppe27;

import java.util.Random;

public class Board {


    // Er den der skal bruges, når dice er blevet oprettet.
    int diceFaceValue;

    // Har oprettet til vist det bliver nødvendigt at starte et specifikt sted fra
    int startField = 0;

    int numberOfFields;

    // Tænkte til felterne, men kan godt være at logikken for felterne ikke har nogen function i denne klasse
    int[] listOfFields;
    int presentField;

    // Til test
    int randomDiceFaceValue;

    Board(int numberOfFields) {
        this.numberOfFields = numberOfFields;

        this.listOfFields = new int[numberOfFields];

    }


    // Til test
    Random rng = new Random();


    public int roll() {

        // Brugt random til test. Bruger bare den rigtige dice klasse når den er oprettet

        this.randomDiceFaceValue = rng.nextInt(6) + 1;

        return this.randomDiceFaceValue;
    }

    // Afgør hvor man lander.

    public int getPresentField() {
        this.presentField = this.randomDiceFaceValue + this.presentField;

        // Når man fx. slår 5 og står på felt nr. 9, i et spil med 12 felter.
        // Dette gør at man bliver indenfor felterne
        if (numberOfFields <= this.presentField) {
            this.presentField = this.presentField - numberOfFields;
        }

        return this.presentField;
    }

    public static void main(String[] args) {

        // Til testning for at se om det virker:

        Board k1 = new Board(12);

        for (int i = 0; i <= 5; i++) {
            System.out.println("The present field is " + k1.presentField);
            System.out.println("The face value is " + k1.roll());
            System.out.println("Landed on index " + k1.getPresentField() + "\n");
        }

    }


}