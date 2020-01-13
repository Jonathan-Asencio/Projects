import java.io.*;

/**
 * class to keep log of game
 * 
 * @author Jonathan Asencio
 * @version 20190712
 */
public class Logger
{
    private String path;
    private PrintStream printStream;

    /**
     * creates new log of game
     * @param path
     * @throws FileNotFoundException
     */
    public Logger(String path) throws FileNotFoundException
    {
        this.path = path;

        printStream = new PrintStream(
            new FileOutputStream(path)
        );
    }

    /**
     *adds line with new line to game log
     * @param entry
     */
    public void println(String entry) {
        printStream.println(entry);
        printStream.flush();
    }

    /**
     * adds line to game log
     * @param entry
     */
    public void print(String entry) {
        printStream.print(entry);
        printStream.flush();
    }
}
