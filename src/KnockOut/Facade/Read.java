package KnockOut.Facade;

import java.io.*;
import java.nio.file.Files;

public class Read {

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("Dice results.txt")));

    public Read() throws FileNotFoundException {
    }

    public void readResultFromFile() throws IOException {
        try {
            System.out.println(bufferedReader.readLine());
        } catch (Exception ex) {
            System.out.println("Något gick fel!");
        }
    }
}
