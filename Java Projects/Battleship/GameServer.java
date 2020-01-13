import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Class that creates Server to host game.
 * 
 * @author Jonathan Asencio
 * @version 20190712
 */
public class GameServer
{
    /**
     * Starts up a server, waits for the first incoming client connection, and then starts a BattleshipGame to play the game
     * @param args
     * @throws IOException
     */
    public static void main(String [] args) throws IOException {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(3333);
            System.out.println("Your IP Address is: " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Socket listening on port " + serverSocket.getLocalPort());

            clientSocket = serverSocket.accept();
            System.out.println("Client connected!!!");
        } catch (IOException e) {
            System.out.println("Could not initialize a ServerSocket or listen for a client to connect to me");
            e.printStackTrace();

            System.exit(1);
        }

        Logger gameLogger = new Logger("server_log.txt");

        GameClient server = new GameClient(clientSocket);

        BattleshipGame game = new BattleshipGame(server, gameLogger, 1, "player2ship.txt");

        game.run();

        System.out.println("It's over");

        server.stop();
        serverSocket.close();
    }
}