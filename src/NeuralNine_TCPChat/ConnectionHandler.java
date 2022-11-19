package NeuralNine_TCPChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable { // client connection

    private final Socket client;
    private BufferedReader in;
    private PrintWriter out;
    protected String nickname;

    public ConnectionHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out.println("Please enter a nickname: ");

            nickname = in.readLine().trim(); // TODO if-statement for valid nickname?
            System.out.println(nickname + " connected. "); // to server console
            broadcast(nickname + " has joined the chat. "); // to chat

            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/rename")) {
                    String[] messageSplit = message.split(" ", 2);
                    if (messageSplit.length == 2) {
                        broadcast(nickname + " changed nickname to " + messageSplit[1]);
                        System.out.println(nickname + " changed nickname to " + messageSplit[1]); // to server console
                        nickname = messageSplit[1];
                        out.println("Successfully changed nickname to " + nickname);
                    } else {
                        out.println("No nickname provided. ");
                    }
                } else if (message.startsWith("/quit")) {
                    broadcast(nickname + " left the chat. "); // skrivs ut i chatten
                    System.out.println(nickname + " disconnected. Från ConnectionHandler.");
//                    shutDownClient();
                } else if (message.equalsIgnoreCase("/connections")) {
                    for (ConnectionHandler c : Server.connections) {
                        System.out.println(c.toString());
                    }
                } else {
                    broadcast(nickname + ": " + message);
                }
            }
        } catch (IOException e) {
            shutDownClient();
        }
    }

    public void sendMessage(String message) {

        out.println(message);
    }

    public void broadcast(String message) {
        for (ConnectionHandler ch : Server.connections) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }
    public void shutDownClient() {
        System.out.println(nickname + " disconnected. Från ConnectionHandler.");
        try {
            in.close();
            out.close();
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}