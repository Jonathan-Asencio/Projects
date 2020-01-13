import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * creates board recording hits and misses on player's fleet
 * 
 * @author Jonathan Asencio
 * @version 20190712
 */
public class Armada
{
    Point[][] grid;
    char[][] displayGrid = new char[Constants.SIZE][Constants.SIZE];

    Ship[][] shipLocations = new Ship[Constants.SIZE][Constants.SIZE];

    ArrayList<Point> occupiedPoints = new ArrayList<>();
    ArrayList<Point> hits = new ArrayList<>();
    ArrayList<Point> misses = new ArrayList<>();

    ArrayList<Ship> ships = new ArrayList<>();

    public static int HITS_TO_WIN = 17;

    /**
     * creates new fleet grid from file
     * @param filename
     */
    public Armada(String filename)
    {
        // This creates our matrix of points
        grid = new Point[Constants.SIZE][Constants.SIZE];

        for (int row = 0; row < Constants.SIZE; row++) {
            for (int column = 0; column < Constants.SIZE; column++) {
                shipLocations[row][column] = null;
                grid[row][column] = new Point(row,column);
                displayGrid[row][column] = ' ';
            }
        }

        initializeShips(filename);
    }

    /**
     * reads file and places ship according to file
     * @param filename
     */
    public void initializeShips(String filename)
    {
        try
        {
            File fileptr = new File(filename);
            Scanner in = new Scanner(fileptr);
            int[] sizes = { 5, 4, 3, 3, 2 };

            int playerID = in.nextInt();

            for(int iship = 0; iship < Constants.NSHIPS; iship++)
            {
                Ship newship = new Ship(in.next(), sizes[iship]);

                ArrayList<Point> shiploc = new ArrayList<Point>();

                for(int i = 0; i < sizes[iship]; i++)
                {
                    String temploc;
                    int x, y;
                    temploc = in.next();

                    int index = temploc.indexOf(',');

                    x = Integer.parseInt(temploc.substring(0, index));
                    y = Integer.parseInt(temploc.substring(index+1, temploc.length()));

                    Point p = new Point(x, y);
                    shiploc.add(p);

                    displayGrid[x][y] = newship.getNameAsChar();
                }

                newship.setLocation(shiploc);

                ships.add(newship);
            }

            in.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
            System.exit(1);
        }
    }

    /**
     * places ships on board
     * @param ship array of ships
     * @throws Exception
     */
    public void placeShip(Ship ship) throws Exception {
        // See if this ship intersects another we've seen?
        for (Point point : ship.getLocation()) {
            for (Point pointInList : occupiedPoints) {
                if (point.getRow() == pointInList.getRow() && point.getCol() == pointInList.getCol()) {
                    throw new Exception("Overlapping ships found");
                }
            }
        }

        // And then update our index once we've confirmed they don't overlap
        for (Point point : ship.getLocation()) {
            occupiedPoints.add(point);
        }

        ships.add(ship);
    }

    /**
     * determines hit or miss on fleet
     * @param row point on x axis
     * @param column point on y axis
     * @return true if hit, false if miss
     */
    public boolean fire(int row, int column) {
        Point p = new Point(row, column);

        for(Ship ship : ships)
        {
            boolean hitShip = ship.fire(p);

            if(hitShip)
            {
                displayGrid[row][column] = 'H';

                if(!hasPoint(hits, p))
                {
                    hits.add(p);
                }

                return true;
            }
        }

        displayGrid[row][column] = 'm';

        if(!hasPoint(misses, p))
        {
            misses.add(p);
        }

        return false;
    }

    /**
     * determines if player has already hit point x, y
     * @param list list of already hit points
     * @param p point x, y of current shot
     * @return true if point has already been used, false if not
     */
    boolean hasPoint(ArrayList<Point> list, Point p)
    {
        for(Point point : list){
            if(p.getRow() == point.getRow() && p.getCol() == point.getCol())
            {
                return true;
            }
        }

        return false;
    }

    /**
     * determines if all ships in fleet have been sunk
     * @return true if all sunk, false if ships still alive
     */
    public boolean fullDestruction()
    {
        for(Ship ship : ships)
        {
            // If any single ship is unsunk, we are not fully destroyed
            if(!ship.isSunk())
            {
                return false;
            }
        }

        return true;
    }

    /**
     * prints fleet board
     */
    public void print()
    {
        System.out.println("---------------------------------");

        for (int row = 0; row < Constants.SIZE; row++)
        {
            for (int column = 0; column < Constants.SIZE; column++)
            {
                char output = ' ';

                System.out.print("| " + displayGrid[row][column] + " ");
            }

            System.out.println("|");
        }

        System.out.println("---------------------------------");
    }
}