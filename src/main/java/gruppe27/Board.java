package gruppe27;

import java.util.Arrays;

public class Board {

    int startField;
    int[] numberOfFields;
    int presentField;


    Board(int startField, int[] numberOfFields) {
        this.startField = startField;
        this.numberOfFields = numberOfFields;
        for (int i = 0 ; i < numberOfFields.length ;i++){
            numberOfFields[i] = i;
        }




    }

/*
   Roll() {

    }
*/

    public static void main(String[] args) {

        Board k1 = new Board(0,new int[12]);




        System.out.println(Arrays.toString(k1.numberOfFields));

    }


}