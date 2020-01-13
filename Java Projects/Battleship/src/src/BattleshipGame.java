import java.util.Scanner;

public class BattleshipGame {
    private Fleet myBoard;
    private GuessGrid guessGrid = new GuessGrid();
    private GameLogger gameLogger;
    private Statistics statistics = new Statistics();

    GameClient client;

    private int current_player = 0;
    private int other_player = 1;

    /**
     * creates new game of battleship with server connection and new game log
     * @param client
     * @param gameLogger
     * @param current_player
     * @param filename
     */
    public BattleshipGame(GameClient client, GameLogger gameLogger, int current_player, String filename)
    {
        this.client = client;
        this.gameLogger = gameLogger;
        this.current_player = current_player;

        other_player = current_player == 1 ? 0 : 1;

        this.myBoard = new Fleet(filename);

        startLog();
    }

    /**
     * starts new log for game
     */
    void startLog() {
        gameLogger.println("Beginning log for user " + current_player);

        statistics.writeStatistics(gameLogger);
    }

    /**
     * gets fleet board
     * @return fleet board
     */
    public Fleet getMyBoard() {
        return myBoard;
    }

    /**
     * asks player for point at which to fire
     */
    public void promptForMyGuess()
    {
        Scanner in = new Scanner(System.in);

        System.out.println("Player " + current_player + ":");
        printBoard();

        String move;
        int x, y;

        System.out.println("Enter row: ");
        x = in.nextInt();
        System.out.println("Enter column: ");
        y = in.nextInt();

        move = x + "," + y;
        Point p = new Point(x, y);

        // Send this move to the server, and listen for hit or miss
        String response = client.sendMessage(move);

        switch (response)
        {
            case "hit":
                // mark a hit at (x,y)
                System.out.println("HIT!");
                guessGrid.record(x, y, response);

                gameLogger.println("");
                gameLogger.println("HIT FOR PLAYER " + current_player + " @ " + p);
                statistics.recordHit(current_player);
                statistics.writeStatistics(gameLogger);

                // If it's all over, we're done
                if(guessGrid.fullDestruction())
                {
                    System.out.println("Hooray - I sunk their battleship!!!");
                    client.stop();

                    gameLogger.println("Woot - Player " + current_player + " sunk your battleship!!!");

                    System.exit(0);
                }

                break;

            case "miss":
                // mark a miss at (x,y)
                System.out.println("MISS!");
                guessGrid.record(x, y, response);

                gameLogger.println("");
                gameLogger.println("MISS FOR PLAYER " + current_player + " @ " + p);
                statistics.recordMiss(current_player);
                statistics.writeStatistics(gameLogger);

                break;

            default:
                System.out.println("Unexpected response from server received - " + response);
                client.stop();
                System.exit(1);
        }
    }

    /**
     * determines if guess is hit or miss and places results on player guess gird and opposite player fleet grid
     */
    public void processTheirGuess()
    {
        String theirMove = client.readMessage();

        System.out.println("Received --> " + theirMove);

        int row, column;

        // Split their move into two pieces, and grab x and y out

        String[] pieces = theirMove.split(",");
        row = Integer.valueOf(pieces[0]);
        column = Integer.valueOf(pieces[1]);

        Point p = new Point(row,column);

        boolean isHit = myBoard.fire(row, column);

        // determine if this was a hit or miss
        // record that result into myBoard

        if(isHit)
        {
            gameLogger.println("");
            gameLogger.println("HIT FOR PLAYER " + other_player + " @ " + p);
            statistics.recordHit(other_player);
            statistics.writeStatistics(gameLogger);

            client.sendOnly("hit");

            if(myBoard.fullDestruction()){
                System.out.println("You sunk my battleship!");

                gameLogger.println("Woot - Player " + current_player + " sunk your battleship!!!");

                client.stop();
                System.exit(0);
            }
        }
        else
        {
            gameLogger.println("");
            gameLogger.println("MISS FOR PLAYER " + other_player + " @ " + p);
            statistics.recordMiss(other_player);
            statistics.writeStatistics(gameLogger);

            client.sendOnly("miss");
        }
    }

    /**
     * runs game. Prompts for guess, processes guess, determines winner
     */
    public void run() {
        // if user 0, we go, then them
        // if user 1, they go, then we do

        while(true)
        {
            if (current_player == 0)
            {
                promptForMyGuess();

                if(guessGrid.fullDestruction())
                {
                    System.out.println("They are dead");
                    client.stop();
                    System.exit(0);
                }

                processTheirGuess();

                if(myBoard.fullDestruction())
                {
                    System.out.println("I'm dead");
                    client.stop();
                    System.exit(0);
                }
            }
            else
            {
                processTheirGuess();

                if(myBoard.fullDestruction())
                {
                    System.out.println("I'm dead");
                    client.stop();
                    System.exit(0);
                }

                promptForMyGuess();

                if(guessGrid.fullDestruction())
                {
                    System.out.println("They are dead");
                    client.stop();
                    System.exit(0);
                }
            }
        }
    }

    /**
     * prints boards
     */
    public void printBoard()
    {
        myBoard.print();
        System.out.println("==========================================");
        System.out.println("==========================================");
        guessGrid.print();
    }
}