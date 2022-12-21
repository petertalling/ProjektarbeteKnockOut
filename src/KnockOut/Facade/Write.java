package KnockOut.Facade;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Write {

    private static Write instance;
    private final PrintWriter printWriter;
    private Write() throws IOException {
        printWriter = new PrintWriter(new BufferedWriter(new FileWriter("Dice results.txt", true)));
    }

    public static Write getInstance() throws IOException {

        synchronized (Write.class){
            if(instance == null){
                instance = new Write();
            }
        }
        return instance;
    }

    public void writeResultToFile(String result) {
        printWriter.println(result);
        System.out.println("Write skrev resultat till fil");
        printWriter.close();
    }
}
