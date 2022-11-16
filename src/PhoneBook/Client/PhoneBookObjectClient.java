package PhoneBook.Client;

import PhoneBook.Resources.Friend;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class PhoneBookObjectClient implements Serializable {
    int port = 54789;
    InetAddress ip = InetAddress.getLocalHost();

    public PhoneBookObjectClient() throws IOException {

        Object fromUser;
        Friend friend;
        Object fromServer;

        try (Socket socketFromServer = new Socket(ip, port);
             ObjectOutputStream out = new ObjectOutputStream(socketFromServer.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socketFromServer.getInputStream())) {

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while ((fromServer = in.readObject()) != null) {
                if (fromServer instanceof String) {
                    System.out.print("Server sent: " + fromServer + " ");
                } else {
                    friend = (Friend)fromServer;
                    System.out.println("Server sent: " + friend);
                }
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
