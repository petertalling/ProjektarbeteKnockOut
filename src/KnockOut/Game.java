package KnockOut;

import KnockOut.Facade.InputOutputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Game extends JFrame implements ActionListener{

    private final int NUMBER_OF_DICE = 2;
    private int currentTotal;
    private int knockOutNumber;
    private String name;
    private int points = 0;
    Dice dice1 = new Dice();
    Dice dice2 = new Dice();
    InputOutputHandler inputOutputHandler = new InputOutputHandler();

    JPanel basePanel = new JPanel();
    JPanel topHalf = new JPanel();
    JPanel bottomHalf = new JPanel();
    JLabel title = new JLabel("Knock-out!");
    JLabel die1 = new JLabel(dice1.getImage());
    JLabel die2 = new JLabel(dice2.getImage());
    JLabel pointsLabel = new JLabel("Poäng: ");
    JLabel koNumber = new JLabel("Knock-out nummer: " + knockOutNumber);
    JButton throwDice = new JButton("Kasta tärningar");

    public Game(String name, int knockoutNumber) throws IOException {
        this.add(basePanel);
        basePanel.setLayout(new BorderLayout());
        basePanel.add(topHalf, BorderLayout.NORTH);
        basePanel.add(bottomHalf, BorderLayout.SOUTH);
        topHalf.setLayout(new GridLayout(4,1));
        topHalf.add(title);
        topHalf.add(koNumber);
        topHalf.add(pointsLabel);
        topHalf.add(throwDice);
        throwDice.addActionListener(this);
        title.setFont(new Font("Tahoma", Font.PLAIN, 25));
        bottomHalf.setLayout(new GridLayout(1,2));
        bottomHalf.add(die1);
        bottomHalf.add(die2);
        pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        if (knockoutNumber < NUMBER_OF_DICE || knockoutNumber > 6 * NUMBER_OF_DICE) {
            JOptionPane.showMessageDialog(null, "KnockOut-nummer måste vara mellan "
                    + NUMBER_OF_DICE + "-" + NUMBER_OF_DICE * 6 + "!");
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.knockOutNumber = knockoutNumber;
        koNumber.setText("Knock-out nummer: " + knockoutNumber);
    }

    public boolean gameLost() {
        return currentTotal == knockOutNumber;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == throwDice && !gameLost()){
            dice1.throwDice();
            die1.setIcon(dice1.getImage());
            dice2.throwDice();
            die2.setIcon(dice2.getImage());
            currentTotal = dice1.getCurrentNumber() + dice2.getCurrentNumber();
            points++;
            pointsLabel.setText("Poäng: " + points);
            if (gameLost()) {
                title.setText("DU FÖRLORADE!");
                try {
                    inputOutputHandler.writeResultsToFile(points + " poäng, " + name);
                    inputOutputHandler.readResultFromFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
