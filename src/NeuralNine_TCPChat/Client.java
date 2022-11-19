package NeuralNine_TCPChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;
    InetAddress ip = InetAddress.getLocalHost();

    public Client() throws UnknownHostException {
    }

    @Override
    public void run() {
        int port = 19874;
        try (Socket client = new Socket(ip, port)) {
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inHandler = new InputHandler();
            Thread t = new Thread(inHandler);
            t.start();

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                System.out.println(inMessage);
            }
        } catch (IOException e) {
            // TODO
        }
    }

    public void shutDown() {
        done = true;
        try {
            in.close();
            out.close();
            if (!client.isClosed()) {
                client.close();
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
                   if (message.equals("/quit")) {
                       inReader.close();
                       shutDown();
                   } else {
                       out.println(message);
                   }
                }
            } catch (Exception e) {
                shutDown();
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        Client client = new Client();
    }
}
