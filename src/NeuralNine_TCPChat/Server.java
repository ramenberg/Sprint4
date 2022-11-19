package NeuralNine_TCPChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    public static ArrayList<ConnectionHandler> getConnections() {
        return connections;
    }

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
            e.printStackTrace();
            shutDownServer();
        }
    }
    public void shutDownServer() {
        done = true;
        pool.shutdown();
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

    public static void main(String[] args) {
        Server server1 = new Server();
        server1.run();
    }
}

