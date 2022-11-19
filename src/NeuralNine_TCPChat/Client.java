package NeuralNine_TCPChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{

    protected Socket clientSocket;
    protected BufferedReader in;
    protected PrintWriter out;
    protected boolean done;

    public Client() throws UnknownHostException {
        done = false;
    }

    @Override
    public void run() {
        int port = 19874;
        try (Socket clientSocket = new Socket(InetAddress.getLocalHost(), port);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){

            InputHandler inHandler = new InputHandler();
            Thread t = new Thread(inHandler);
            t.start();

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                System.out.println(inMessage);
            }
        } catch (IOException e) {
            shutDown();
        }
    }

    public void shutDown() {
        done = true;
        try {
            in.close();
            out.close();
            if (!clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class InputHandler implements Runnable {

        @Override
        public void run() {
            try {
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                   String message = inReader.readLine();
                   if (message.equalsIgnoreCase("/quit")) {
                       inReader.close();
                       shutDown();
                   } else {
                       out.println(message);
                   }
                }
            } catch (Exception e) {
                e.printStackTrace();
                shutDown();
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        Client client = new Client();
        client.run();
    }
}
