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
    protected BufferedReader in, inReader;
    protected PrintWriter out;
    protected boolean done;

    public Client() throws UnknownHostException {
        done = false;
    }

    @Override
    public void run() {
        int port = 19874;
        try {
            clientSocket = new Socket(InetAddress.getLocalHost(), port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            InputHandler inHandler = new InputHandler();
            Thread t = new Thread(inHandler);
            t.start();
            System.out.println("Successfully connected to server. ");

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                System.out.println(inMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
            shutDownClient();
        }
    }


    public class InputHandler implements Runnable {

        @Override
        public void run() {
            try {
                inReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                   String message = inReader.readLine();
                   if (message.equalsIgnoreCase("/quit")) {
                       out.println(message);
                       inReader.close();
                       shutDownClient();
                   } else {
                       out.println(message);
                   }
                }
            } catch (Exception e) {
                e.printStackTrace();
                shutDownClient(); // TODO tas den bort får man inte texten två ggr men prog avslutas ej.
            }
        }
    }
    public void shutDownClient() {
        System.out.println("Från Client.java");
        done = true;
        try {
            in.close();
            out.close();
            if (!clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        Client client = new Client();
        client.run();
    }
}
