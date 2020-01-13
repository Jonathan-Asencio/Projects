

/**
 * Tester for class Tictactoe3D.
 *
 * @author Jonathan
 * @version 20190726
 */
public class TestGame
{
    public static void main(String[] args)
    {
        Tictactoe3D game = new Tictactoe3D();

        do
        {
            game.getMove();
            game.display();
            //game.display();
        }
        while (game.checkWin() == -1);

        if (game.checkWin() == 0)
            System.out.println("Player 1 wins");
        else
            System.out.println("Player 2 wins");

    }
}
