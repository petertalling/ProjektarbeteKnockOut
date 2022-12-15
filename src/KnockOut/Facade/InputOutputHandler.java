package KnockOut.Facade;

import java.io.IOException;

public class InputOutputHandler {

    private Write out = new Write();
    private Read in = new Read();

    public InputOutputHandler() throws IOException {
    }

    public void writeResultsToFile(String result) throws IOException {
        out.writeResultToFile(result);
    }

    public void readResultFromFile() throws IOException {
        in.readResultFromFile();
    }

}
