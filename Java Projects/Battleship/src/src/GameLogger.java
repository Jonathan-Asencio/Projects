import java.io.*;

/**
 class to keep log of game
 */
public class GameLogger
{
    private String path;
    private PrintStream printStream;

    /**
     * creates new log of game
     * @param path
     * @throws FileNotFoundException
     */
    public GameLogger(String path) throws FileNotFoundException
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
