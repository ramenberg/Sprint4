package NeuraNine_TCPChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{

    public int port = 19871;
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;

    public Server() {
        connections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(port)){
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
            }
        } catch (IOException e) {
            shutDown();
        }
    }
    public void shutDown() {
        done = true;
        try {
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler ch : connections)
                ch.shutDownClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ConnectionHandler implements Runnable { // client connection

        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.print("Please enter a nickname: ");
                nickname = in.readLine().trim(); // TODO if-statment for valid nickname
                System.out.println(nickname + " connected. ");
                broadcast(nickname + " has joined the chat. ");

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/nick")) {
                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2) {
                            broadcast(nickname + " changed nickname to " + messageSplit[1]);
                            System.out.println(nickname + " changed nickname to " + messageSplit[1]);
                            nickname = messageSplit[1];
                            out.println("Successfully changed nickname to " + nickname);
                        } else {
                            out.println("No nickname provided. ");
                        }
                    } else if (message.startsWith("/exit")) {
                        broadcast(nickname + " has left the chat. ");
                        shutDownClient();
                    } else {
                        broadcast(nickname + ":" + message);
                    }
                }
            } catch (IOException e) {
                shutDown();
            }
        }

        public void broadcast(String message) {
            for (ConnectionHandler ch : connections) {
                if (ch != null) {
                    ch.sendMessage(message);
                }
            }
        }

        public void shutDownClient() {
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
        public void sendMessage(String message) {
            out.println(message);
        }
    }
}
