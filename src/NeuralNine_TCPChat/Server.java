package NeuralNine_TCPChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    protected static ArrayList<ConnectionHandler> connections;
    protected ServerSocket server;
    protected boolean done;
    protected ExecutorService pool;

    public Server() {
        connections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        int port = 19874;
        try {
            server = new ServerSocket(port);
            pool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (IOException e) {
            shutDown();
            e.printStackTrace();
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
            e.printStackTrace(); // ignore?
        }
    }

    public class ConnectionHandler implements Runnable { // client connection

        private final Socket client;
        private BufferedReader in;
        private PrintWriter out;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.print("Please enter a nickname: ");
                String nickname = in.readLine().trim(); // TODO if-statement for valid nickname?
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
                    } else if (message.startsWith("/quit")) {
                        broadcast(nickname + " left the chat. ");
                        shutDownClient();
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

    public static void main(String[] args) {
        Server server1 = new Server();
        server1.run();
    }
}

