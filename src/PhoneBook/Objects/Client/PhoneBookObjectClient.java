package PhoneBook.Objects.Client;

import PhoneBook.Objects.Resources.Friend;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class PhoneBookObjectClient implements Serializable {
    int port = 54789;
    InetAddress ip = InetAddress.getLocalHost();

    public PhoneBookObjectClient() throws IOException {


        try (Socket socketFromServer = new Socket(ip, port);
             ObjectOutputStream oos = new ObjectOutputStream(socketFromServer.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socketFromServer.getInputStream())) {

            String fromUser;
            Friend fromServer;

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while ((fromServer = (Friend) ois.readObject()) != null) {
                System.out.print("Server sent: " + fromServer + " ");

                fromUser = userInput.readLine();
                if (fromUser != null) {
                    System.out.println("Client sent: " + fromUser);
                    oos.writeObject(fromUser);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) throws IOException {
        new PhoneBookObjectClient();
    }
}
