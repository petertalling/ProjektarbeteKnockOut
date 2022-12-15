package KnockOut;

import KnockOut.Facade.InputOutputHandler;

import javax.swing.*;
import java.io.IOException;

public class Game {
    private final int NUMBER_OF_DICE = 2;
    private int currentTotal;
    private int forbiddenNumber;
    private String name;
    private int points = 0;
    Dice dice1 = new Dice();

    Dice dice2 = new Dice();
    InputOutputHandler inputOutputHandler = new InputOutputHandler();

    public Game(String name, int forbiddenNumber) throws IOException {
        if (forbiddenNumber < NUMBER_OF_DICE || forbiddenNumber > 6 * NUMBER_OF_DICE) {
            JOptionPane.showMessageDialog(null, "Förbjudet nummer måste vara mellan 2-12!");
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.forbiddenNumber = forbiddenNumber;

        while (!gameLost()) {
            int response = JOptionPane.showConfirmDialog(null, "Vill du kasta tärningar?");
            if (response == JOptionPane.OK_OPTION) {
                dice1.throwDice();
                dice2.throwDice();
                currentTotal = dice1.getCurrentNumber() + dice2.getCurrentNumber();
            }
            points++;
        }
        JOptionPane.showMessageDialog(null, "Du fick " + points + " poäng");
        inputOutputHandler.writeResultsToFile(points + " poäng, " + name);
        inputOutputHandler.readResultFromFile();
    }

    public boolean gameLost() {
        return currentTotal == forbiddenNumber;
    }


}
