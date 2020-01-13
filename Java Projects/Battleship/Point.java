
/**manages points on x and y axis*/

public class Point
{
    private int x, y;

    /**
     * creates new point 0,0
     */
    public Point()
    {
        x = 0;
        y = 0;
    }

    /**
     * creates new point at specified point
     * @param r point on x axis
     * @param c point on y axis
     */
    public Point(int r, int c)
    {
        x = r;
        y = c;
    }

    /**
     * sets point
     * @param r point on x axis
     * @param c point on y axis
     */
    public void setLocation(int r, int c)
    {
        x = r;
        y = c;
    }

    /**
     * gets point on x axis
     * @return  x
     */
    public int getRow()
    {
        return x;
    }

    /**
     * gets point on y axis
     * @return y
     */
    public int getCol()
    {
        return y;
    }

    /**
     * returns point on x and y axis as string
     * @return
     */
    @Override
    public String toString()
    {
        return "(" + x + "," + y + ")";
    }
}
