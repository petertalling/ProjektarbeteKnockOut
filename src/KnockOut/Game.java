package KnockOut;

import KnockOut.Facade.InputOutputHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Game extends Thread implements ActionListener{
    private int currentTotal;
    private int knockOutNumber;
    private String name;
    private int points = 0;

    PinkDice dice1 = new PinkDice();
    YellowDice dice2 = new YellowDice();
    InputOutputHandler inputOutputHandler = new InputOutputHandler();

    JFrame frame = new JFrame();
    JPanel basePanel = new JPanel();
    JPanel topHalf = new JPanel();
    JPanel bottomHalf = new JPanel();

    JLabel title = new JLabel();
    JLabel die1 = new JLabel(dice1.getImage());
    JLabel die2 = new JLabel(dice2.getImage());
    JLabel pointsLabel = new JLabel();
    JLabel koNumber = new JLabel();
    JLabel highScore1 =  new JLabel();
    JLabel highScore2 =  new JLabel();
    JLabel highScore3 =  new JLabel();

    JButton throwDice = new JButton("Kasta tärningar");
    JButton playAgain = new JButton("Spela igen");

    public Game() throws IOException {
        frame.add(basePanel);
        basePanel.setLayout(new BorderLayout());
        basePanel.add(topHalf, BorderLayout.NORTH);
        basePanel.add(bottomHalf, BorderLayout.SOUTH);
        topHalf.setLayout(new GridLayout(4,1));
        bottomHalf.setLayout(new GridLayout(1,2));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        startGame();
    }
    private void startGame() {
        int NUMBER_OF_DICE = 2;
        int respons;
        JOptionPane.showMessageDialog(null, """
                Välkommen till KnockOut!\s
                Reglerna är simpla:\s
                - Välj förbjudet nummer (2-12)\s
                - Kasta två tärningar tills summan av de två blir det förbjudna numret\s
                - Din poäng blir antalet rundor du lyckas ta dig igenom!\s
                
                
                Kontaktuppgifter till skapare: +46 8 123 55 55 info@noreply.se""");

        name = JOptionPane.showInputDialog(null, "Ditt namn?");

        while(true){
            respons = Integer.parseInt(JOptionPane.showInputDialog(null, "Förbjudet nummer?").trim());
            if(respons < NUMBER_OF_DICE || respons > 6 * NUMBER_OF_DICE){
                JOptionPane.showMessageDialog(null, "KnockOut-nummer måste vara mellan " + NUMBER_OF_DICE + "-" + NUMBER_OF_DICE * 6 + "!");
            }else{
                knockOutNumber = respons;
                break;
            }
        }
        setGUI();
    }

    private void setGUI() {
        topHalf.add(title);
        title.setText("KnockOut!");
        topHalf.add(koNumber);
        koNumber.setText("KnockOut-nummer: " + knockOutNumber);
        topHalf.add(pointsLabel);
        pointsLabel.setText("Poäng: ");
        topHalf.add(throwDice);
        throwDice.addActionListener(this);

        title.setFont(new Font("Thoma", Font.PLAIN, 25));
        bottomHalf.add(die1);
        bottomHalf.add(die2);
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }


    public ArrayList<String> scoreBoardList (ArrayList<String> resultList){
        ArrayList<Integer> scores = new ArrayList<>();
        for (String result : resultList) {
            scores.add(Integer.parseInt(result.substring(0, result.indexOf(' '))));
        }
        scores.sort(Collections.reverseOrder());

        ArrayList<String> sortedResults = new ArrayList<>();
        for (Integer score : scores) {
            for (String result : resultList) {
                if (result.contains(score + " ")) {
                    sortedResults.add(result);
                }
            }
        }
        return sortedResults;
    }

    public boolean gameLost() {
        return currentTotal == knockOutNumber;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playAgain){
            topHalf.remove(highScore1);
            topHalf.remove(highScore2);
            topHalf.remove(highScore3);
            playAgain.removeActionListener(this);
            bottomHalf.remove(playAgain);
            points = 0;
            startGame();
        }
        if (e.getSource() == throwDice && !gameLost()) {
            System.out.println("tärningen är kastad..");
            dice1.throwDice();
            die1.setIcon(dice1.getImage());
            dice2.throwDice();
            die2.setIcon(dice2.getImage());
            currentTotal = dice1.getCurrentNumber() + dice2.getCurrentNumber();
            points++;
            pointsLabel.setText("Poäng: " + points);
            frame.revalidate();
            frame.repaint();
            if (gameLost()) {
                title.setText("DU FÖRLORADE!");
                topHalf.remove(throwDice);
                throwDice.removeActionListener(this);
                topHalf.remove(koNumber);
                topHalf.remove(pointsLabel);
                bottomHalf.remove(die1);
                bottomHalf.remove(die2);
                bottomHalf.add(playAgain);
                playAgain.addActionListener(this);
                try {
                    inputOutputHandler.writeResultsToFile(points + " poäng, " + name);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    ArrayList<String> highScores = scoreBoardList(inputOutputHandler.resultListFromFile());
                    highScore1.setText(highScores.get(0));
                    highScore2.setText(highScores.get(1));
                    highScore3.setText(highScores.get(2));
                    highScores.clear();
                    topHalf.add(highScore1);
                    topHalf.add(highScore2);
                    topHalf.add(highScore3);
                    frame.revalidate();
                    frame.repaint();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
