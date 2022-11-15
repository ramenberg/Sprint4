package ServerToClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    int port = 54789;

    String welcome = "Uppkopplingen till servern lyckades!";
    final static String q1 = "Happiness is an uphill battle. Wear the good shoes. - Kurt Vonnegut";
    final static String q2 = "Without ice cream, there would be chaos and darkness. - Don Kardong";
    final static String q3 = "When things go wrong, don't go with them. - Elvis Presley";
    final static String q4 = "Happiness never decreases by being shared. - Buddha";
    final static String q5 = "For every minute you are angry you lose 60 seconds of happiness. - Ralph Waldo Emerson";
    public Server() {

        List<String> quoteList = new ArrayList<>();
        quoteList.add(q1); quoteList.add(q2); quoteList.add(q3);
        quoteList.add(q4); quoteList.add(q5);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket s = serverSocket.accept();
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

            out.println(welcome);

            while (true) {
                String quote;

                for (String q : quoteList){
                    quote = quoteList.get(quoteList.indexOf(q));
                    out.println(quote);
                    System.out.println("Sent: " + quote);
                    Thread.sleep(2000);
                }


            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Server serv = new Server();
    }
}
