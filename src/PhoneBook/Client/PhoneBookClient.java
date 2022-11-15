package PhoneBook.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class PhoneBookClient {
    int port = 54789;
    InetAddress ip = InetAddress.getLocalHost();

    public PhoneBookClient() throws UnknownHostException {

        String fromUser;
        String fromServer;

        try (Socket s = new Socket(ip, port);
             PrintWriter out = new PrintWriter(s.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while ((fromServer = in.readLine()) != null) {
                System.out.print("Server sent: " + fromServer);
//                out.println("Test from server");
                fromUser = userInput.readLine();
                if (fromUser != null) {
                    out.println(fromUser);
                } else {
                    System.exit(0);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) throws UnknownHostException {
        new PhoneBookClient();
    }
}
