
/**
 * Moves peg to players input
 *
 * @author Jonathan Asencio
 * @version 20190726
 */
public class Peg
{
    private char move;
    
    public Peg(char x)
    {
        move = x;
    }
    
    public Peg()
    {
        move = ' ';
    }
    
    public void setMove(char c)
    {
        move = c;
    }
    
    public char getMove()
    {
        return move;
    }
}
