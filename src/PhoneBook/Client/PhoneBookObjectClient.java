package PhoneBook.Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class PhoneBookObjectClient extends PhoneBook.Resources.Friend implements Serializable {
    int port = 54789;
    InetAddress ip = InetAddress.getLocalHost();

    public PhoneBookObjectClient() throws IOException {


        try (Socket socketFromServer = new Socket(ip, port);
             ObjectOutputStream out = new ObjectOutputStream(socketFromServer.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socketFromServer.getInputStream())) {

            String fromUser;
            PhoneBook.Resources.Friend fromServer;

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while ((fromServer = (PhoneBook.Resources.Friend) in.readObject()) != null) {
                System.out.print("Server sent: " + fromServer + " ");

                fromUser = userInput.readLine();
                if (fromUser != null) {
                    System.out.println("Client sent: " + fromUser);
                    out.writeObject(fromUser);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) throws IOException {
        new PhoneBookObjectClient();
    }
}
