package KnockOut;

import KnockOut.Facade.InputOutputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Game extends JFrame implements ActionListener {
    JPanel basePanel = new JPanel();
    JPanel topHalf = new JPanel();
    JPanel bottomHalf = new JPanel();
    JLabel title = new JLabel("Knock-out!");
    JLabel die1 = new JLabel();
    JLabel die2 = new JLabel();
    JButton throwDice = new JButton("Kasta tärningar");
    ImageIcon image1 = new ImageIcon("bilder/dice1.png");
    ImageIcon image2 = new ImageIcon("bilder/dice2.png");
    ImageIcon image3 = new ImageIcon("bilder/dice3.png");
    ImageIcon image4 = new ImageIcon("bilder/dice4.png");
    ImageIcon image5 = new ImageIcon("bilder/dice5.png");
    ImageIcon image6 = new ImageIcon("bilder/dice6.png");


    private final int NUMBER_OF_DICE = 2;
    private int currentTotal;
    private int forbiddenNumber;
    private String name;
    private int points = 0;
    Dice dice1 = new Dice();

    Dice dice2 = new Dice();
    InputOutputHandler inputOutputHandler = new InputOutputHandler();

    public Game(String name, int forbiddenNumber) throws IOException {
        this.add(basePanel);
        basePanel.setLayout(new BorderLayout());
        basePanel.add(topHalf, BorderLayout.NORTH);
        basePanel.add(bottomHalf, BorderLayout.SOUTH);
        topHalf.setLayout(new GridLayout(2,1));
        topHalf.add(title);
        topHalf.add(throwDice);
        throwDice.addActionListener(this);
        title.setFont(new Font("Tahoma", Font.PLAIN, 25));
        bottomHalf.setLayout(new GridLayout(1,2));
        bottomHalf.add(die1);
        bottomHalf.add(die2);
        die1.setIcon(image1);
        die2.setIcon(image2);
        pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

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
