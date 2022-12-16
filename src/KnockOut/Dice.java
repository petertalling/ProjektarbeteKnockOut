package KnockOut;

import javax.swing.*;
import java.util.Random;

public class Dice extends JFrame {
    private int currentNumber = 1;

    private ImageIcon image1 = new ImageIcon("src/KnockOut/bilder/dice1.png");
    private ImageIcon image2 = new ImageIcon("src/KnockOut/bilder/dice2.png");
    private ImageIcon image3 = new ImageIcon("src/KnockOut/bilder/dice3.png");
    private ImageIcon image4 = new ImageIcon("src/KnockOut/bilder/dice4.png");
    private ImageIcon image5 = new ImageIcon("src/KnockOut/bilder/dice5.png");
    private ImageIcon image6 = new ImageIcon("src/KnockOut/bilder/dice6.png");
    private ImageIcon[] dieFaces = {image1, image2, image3, image4, image4, image5, image6};

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void throwDice() {
        Random random = new Random();
        currentNumber = random.nextInt(1, 7);
    }
    public ImageIcon getImage() {
        return dieFaces[currentNumber - 1];
    }
}
