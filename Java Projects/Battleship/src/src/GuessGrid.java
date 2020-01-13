import java.util.ArrayList;

/**maniupulates grid that records guesses in battleship*/
public class GuessGrid
{
    String[][] guesses;
    ArrayList<Point> hits = new ArrayList<>();

   /**creates a new guess grid*/
    public GuessGrid()
    {
        // This creates our matrix of points
        guesses = new String[Constants.SIZE][Constants.SIZE];

        for (int row = 0; row < Constants.SIZE; row++)
        {
            for (int column = 0; column < Constants.SIZE; column++)
            {
                guesses[row][column] = null;
            }
        }
    }

    /**records new shot as hit or miss on guess grid*/
    public void record(int row, int column, String outcome){
        guesses[row][column] = outcome;

        Point p = new Point(row, column);

        if(outcome.equals("hit"))
        {
            guesses[row][column] = "X";

            if(!pointInList(hits, p)){
                hits.add(p);
            }
        }
        else
        if(outcome.equals("miss"))
        {
            guesses[row][column] = "O";
        }
    }

    /**
     * determines if point has already been fired on
     * @param list list of points already used
     * @param p current point
     * @return true if point has already been used, false if not
     */
    boolean pointInList(ArrayList<Point> list, Point p)
    {
        for(Point point : list)
        {
            if(p.getRow() == point.getRow() && p.getCol() == point.getCol())
            {
                return true;
            }
        }

        return false;
    }

    /**
     * counts number of hits
     * @return hit count
     */
    public int hitCount()
    {
        return hits.size();
    }

    /**
     * prints guess grid
     */
    public void print()
    {
        System.out.println("---------------------------------");

        for (int row = 0; row < Constants.SIZE; row++)
        {
            for (int column = 0; column < Constants.SIZE; column++)
            {
                char displayChar = ' ';

                if(guesses[row][column] != null)
                {
                    displayChar = guesses[row][column].charAt(0);
                }

                System.out.printf("| %s ", displayChar);
            }

            System.out.println("|");
        }

        System.out.println("---------------------------------");
    }

    /**
     * determines if all ships on guess grid have been destroyed
     * @return true if all ships destroyed, false if ship still alive
     */
    public boolean fullDestruction()
    {
        System.out.println("Current hit count == " + hitCount() + " // Required to win == " + Fleet.HITS_TO_WIN);

        return hitCount() == Fleet.HITS_TO_WIN;
    }
}
