package KnockOut;

import java.util.Random;

public class Dice {
    private int currentNumber;

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void throwDice() {
        Random random = new Random();
        currentNumber = random.nextInt(1, 7);
    }
}
