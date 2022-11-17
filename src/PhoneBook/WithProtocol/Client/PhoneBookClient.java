package PhoneBook.WithProtocol.Client;

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

    public PhoneBookClient() throws IOException {

        String fromUser;
        String fromServer;

        try (Socket s = new Socket(ip, port);
             PrintWriter out = new PrintWriter(s.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))
             ) {

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while ((fromServer = in.readLine()) != null) {

                System.out.print("Server: " + fromServer);
                if (fromServer.equalsIgnoreCase("n")) {
                    break;
                }

                fromUser = userInput.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }

        } catch (UnknownHostException uh) {
            System.err.println("Couldn't find host " + ip);
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + ip);
            System.exit(1);
        }

    }
    public static void main(String[] args) throws IOException {
        new PhoneBookClient();
    }
}
