package KnockOut.Facade;

import java.io.*;
import java.util.ArrayList;

public class Read {
    private final ArrayList<String> resultList = new ArrayList<>();
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("Dice results.txt")));

    public Read() throws FileNotFoundException {
    }

    public ArrayList<String> resultListFromFile() throws IOException {
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                resultList.add(nextLine);
            }
        System.out.println("Read läste in listan");
        return resultList;
    }
}
