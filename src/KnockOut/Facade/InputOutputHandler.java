package KnockOut.Facade;

import java.io.IOException;

public class InputOutputHandler {

    private final Write OUT = new Write();
    private final Read IN = new Read();

    public InputOutputHandler() throws IOException {
    }

    public void writeResultsToFile(String result) throws IOException {
        OUT.writeResultToFile(result);
    }

    public void readResultFromFile() throws IOException {
        IN.readResultFromFile();
    }

}
