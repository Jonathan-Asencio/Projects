import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Class that creates client to connect with GameServer
 * 
 * @author Jonathan Asencio
 * @version 20190712
 */
public class GameClient
{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * open socket for two player game
     * @param clientSocket
     */
    public GameClient(Socket clientSocket)
    {
        this.clientSocket = clientSocket;

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Could not initialize GameClient");

            e.printStackTrace();

            System.exit(1);
        }
    }

    /**
     * Send a message to the remote side, but don't listen for a response
     * @param msg
     */
    public void sendOnly(String msg) {
        try {
            out.println(msg);
        } catch (Exception e)
        {
            System.out.println("Error sending message to remote from client");
            System.exit(1);
        }
    }

    /**
     * Send a message to the remote side, and immediately listen for a response
     *
     * @param msg
     * @return
     */
    public String sendMessage(String msg) {
        String resp = "";
        try {
            out.println(msg);
            resp = in.readLine();
        } catch (Exception e)
        {
            System.out.println("Error sending message to remote from client");
            System.exit(1);
        }

        return resp;
    }

    /**
     * Read a string from the remote side
     * @return
     */
    public String readMessage() {
        String line = "";

        try
        {
            line = in.readLine();
        }
        catch (Exception e)
        {
            System.out.println("Error reading message from remote end");
            System.exit(1);
        }

        return line;
    }

    /**
     * Clean up open connections and sockets
     */
    public void stop()
    {
        try
        {
            in.close();
            out.close();
            clientSocket.close();
            //serverSocket.close();
        }
        catch(Exception e)
        {
            System.out.println("Error stopping client");
        }
    }

    /**
     * Runs the game client (the remote side) by connecting remotely and then running a configured BattleshipGame
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        //Fleet gameBoard = new Fleet("player1ship.txt");
           //GuessGrid guesses = new GuessGrid();

        Scanner in = new Scanner(System.in);
        String ipAddress = null;

        while(true) {
            System.out.println("Please provide the server's IP address… ");

            String ip = in.next();

            try {
                ipAddress = InetAddress.getByName(ip).getHostName();
                break;
            }
            catch(Exception e) {
                System.out.println("That IP address seems to be incorrect.");
            }
        }

        Socket clientSocket = null;
        try {
            clientSocket = new Socket(ipAddress, 3333);
        } catch (IOException e) {
            e.printStackTrace();

            System.exit(1);
        }

        Logger gameLogger = new Logger("client_log.txt");
        GameClient client = new GameClient(clientSocket);

        //Fleet gameBoard = new Fleet("player1ship.txt");

        BattleshipGame game = new BattleshipGame(client, gameLogger, 0, "player1ship.txt");

        // In a loop, go first and then respond to the server's guess after that, then repeatwhile()
        game.run();

        System.out.println("GAME OVER MAN, GAME OVER");

        client.stop();
    }
}