import java.util.Scanner;

/**
 * Creates playable Tictactoe board.
 *
 * @author Jonathan Asencio
 * @version 20190726
 */
public class Tictactoe3D
{
    private Peg [][][] board = new Peg[Constants.SIZE][Constants.SIZE][Constants.SIZE];
    private int current_player = 0;
    private int last_row, last_col, last_plane;

    public Tictactoe3D()
    {
        current_player = 0;
        for (int i = 0; i < Constants.SIZE; i++)
            for(int j = 0; j < Constants.SIZE; j++)
                for (int k = 0; k < Constants.SIZE; k++)
                {
                    board[i][j][k] = new Peg();
                    board[i][j][k].setMove(' ');
                }
    }
    /**
     * Checks to see if player won
     * 
     * @return winner win message
     * @return checkWinOutPlane call to method
     */
    public int checkWin()
    {
        int winner;

        winner = checkWinPlane();

        if (winner != -1)
            return winner;

        return checkWinOutPlane();
    }
    
    /**
     * Checks board for winning move
     * 
     * 
     * 
     */
    public int checkWinOutPlane()
    {
        int sum = 0;
        char last_move = board[last_row][last_col][last_plane].getMove();
        
        for (int plane = 0; plane < Constants.SIZE; plane++)
        {
            if (last_move == board[last_row][last_col][plane].getMove())
                sum++;
        }
        
        if (sum == 4)
        {
            if (last_move == 'O')
                return 0;
            else
                return 1;
        }
        
        // check diag along edge
        sum = 0;
        for (int plane = 0; plane < Constants.SIZE; plane++)
        {
            if (last_move == board[plane][last_col][plane].getMove())
            {
                sum++;
            }
        }
        if (sum == 4)
        {
            if (last_move == 'O')
                return 0;
            else
                return 1;
        }
        
        // check diag along edge in reverse
        sum = 0;
        for (int plane = 0; plane < Constants.SIZE; plane++)
        {
            if (last_move == board[Constants.SIZE - 1 - plane][last_col][plane].getMove())
            {
                sum++;
            }
        }
        if (sum == 4)
        {
            if (last_move == 'O')
                return 0;
            else
                return 1;
        }
        
        // check diagonal along horizontal
        // check diag along edge
        sum = 0;
        for (int plane = 0; plane < Constants.SIZE; plane++)
        {
            if (last_move == board[last_row][plane][plane].getMove())
            {
                sum++;
            }
        }
        if (sum == 4)
        {
            if (last_move == 'O')
                return 0;
            else
                return 1;
        }
        
        // reverse check diagonal along horizontal
        // check diag along edge
        sum = 0;
        for (int plane = 0; plane < Constants.SIZE; plane++)
        {
            if (last_move == board[last_row][Constants.SIZE - 1 - plane][plane].getMove())
            {
                sum++;
            }
        }
        if (sum == 4)
        {
            if (last_move == 'O')
                return 0;
            else
                return 1;
        }
        
        return checkDiagonal();
        
        //return -1;
    }
    
    
    public int checkDiagonal()
    {
        int sum = 0;
        char last_move = board[last_row][last_col][last_plane].getMove();
        
        for (int plane = 0; plane < Constants.SIZE; plane++)
        {
            if (last_move == board[plane][plane][plane].getMove())
                sum++;
        }
        if (sum == 4)
        {
            if (last_move == 'O')
                return 0;
            else
                return 1;
        }
        
        sum = 0;
        for (int plane = 0; plane < Constants.SIZE; plane++)
        {
            if (last_move == board[Constants.SIZE - 1 - plane][plane][plane].getMove())
                sum++;
        }
        if (sum == 4)
        {
            if (last_move == 'O')
                return 0;
            else
                return 1;
        }
        
        sum = 0;
        for (int plane = 0; plane < Constants.SIZE; plane++)
        {
            if (last_move == board[plane][Constants.SIZE - 1 - plane][plane].getMove())
                sum++;
        }
        if (sum == 4)
        {
            if (last_move == 'O')
                return 0;
            else
                return 1;
        }
        
        sum = 0;
        for (int plane = 0; plane < Constants.SIZE; plane++)
        {
            if (last_move == board[Constants.SIZE - 1 - plane][Constants.SIZE - 1 - plane][plane].getMove())
                sum++;
        }
        if (sum == 4)
        {
            if (last_move == 'O')
                return 0;
            else
                return 1;
        }
        
        return -1;
    }
    

    public int checkWinPlane()
    {
        char last_move = board[last_row][last_col][last_plane].getMove();
        int sumV = 0, sumH = 0, sumDiag1 = 0, sumDiag2 = 0;

        // check vertical & horizontal win
        for (int i = 0; i < Constants.SIZE; i++)
        {
            if (last_move == board[i][last_col][last_plane].getMove())
            {
                sumV++;
            }

            if (last_move == board[last_row][i][last_plane].getMove())
            {
                sumH++;
            }

            if (last_move == board[i][i][last_plane].getMove())
            {
                sumDiag1++;
            }

            if (last_move == board[i][Constants.SIZE - i - 1][last_plane].getMove())
            {
                sumDiag2++;
            }
        }

        if (sumV == 4 || sumH == 4 || sumDiag1 == 4 || sumDiag2 == 4)
        {
            if (last_move == 'O')
                return 0;
            else
                return 1;
        }

        // check horizontal
        return -1;
    }

    public void getMove()
    {
        Scanner in = new Scanner(System.in);

        System.out.println("Player " + current_player + " make your move");
        System.out.print("Enter row: ");
        last_row = in.nextInt();
        System.out.printf("\n");
        System.out.print("Enter columh: ");
        last_col = in.nextInt();
        System.out.printf("\n");
        System.out.print("Enter the plane: ");
        last_plane = in.nextInt();
        System.out.printf("\n");

        if (current_player == 0)
            board[last_row][last_col][last_plane].setMove('O');
        else
            board[last_row][last_col][last_plane].setMove('X');

        current_player = Math.abs(1 - current_player);

    }

    public void display()
    {
        for (int plane = 0; plane < Constants.SIZE; plane++)
        {
            System.out.println("---------");
            for (int row = 0; row < Constants.SIZE; row++)
            {
                for (int col = 0; col < Constants.SIZE; col++)
                {
                    System.out.print("|" + board[row][col][plane].getMove());
                }
                System.out.println("|");
                System.out.println("---------");
            }

            System.out.printf("\n");
        }
    }

}
