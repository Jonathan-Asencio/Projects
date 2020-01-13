import java.util.ArrayList;

public class Ship
{
    private int size;
    private String name;
    private ArrayList<Point> location = new ArrayList<>();
    private ArrayList<Point> hit = new ArrayList<>();
    private boolean sunk = false;

    /**
     * creates new ship with specified name and size
     * @param na name
     * @param s size
     */
    public Ship(String na, int s)
    {
        name = na;
        size = s;
    }

    /**
     * gets list of hits
     * @return list of hits
     */
    public ArrayList<Point> getHits()
    {
        return hit;
    }

    /**
     * gets name of ship
     * @return ship name
     */
    public String getName()
    {
        return name;
    }

    /**
     * gets first letter of ship
     * @return initial letter of ship
     */
    public char getNameAsChar()
    {
        return name.charAt(0);
    }

    /**
     * sets name of ship
     * @param n name of ship
     */
    public void setName(String n)
    {
        name = n;
    }

    /**
     * gets size of ship
     * @return size of ship
     */
    public int getSize()
    {
        return location.size();
    }

    /**
     * sets location of ship as array of points
     * @param loc ship location
     */
    public void setLocation(ArrayList<Point> loc)
    {
        for(int i = 0; i < loc.size(); i++)
        {
            location.add(loc.get(i));
        }
    }

    /**
     * gets location of ship
     * @return location
     */
    public ArrayList<Point> getLocation()
    {
        return location;
    }

    /**
     * checks if ships overlap
     * @param point
     * @return true if ships overlap, false if not
     */
    public boolean intersects(Point point)
    {
        for(Point lPoint : location)
        {
            if(lPoint.getRow() == point.getRow() && lPoint.getCol() == point.getCol())
            {
                return true;
            }
        }

        return false;
    }

    /**
     * checks if point is a hit and makes sure point hasn't already been hit by previous missile
     * @param point
     * @return true if hit
     */
    public boolean fire(Point point) {
        // If the ship doesn't intersect the point, it's a miss
        if(!intersects(point))
        {
            return false;
        }

        // Now record the hit
        boolean alreadyHit = false;

        // Did we already mark the hit?
        for(Point hPoint : hit) {
            if(hPoint.getRow() == point.getRow() && hPoint.getCol() == point.getCol()){
                alreadyHit = true;
            }
        }

        // If not, record it
        if(!alreadyHit)
        {
            hit.add(point);
        }

        // Now respond that we had a hit
        return true;
    }

    /**
     * checks if ship is sunk
     * @return
     */
    public boolean isSunk()
    {
        return hit.size() == location.size();
    }
}
