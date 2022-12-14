package PhoneBook.Objects.Server;
import PhoneBook.Objects.Resources.Friend;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class PhoneBookObjectServer implements Serializable {

    int port = 54789;
    String welcomeMessage = "Ange namn på personen du söker: ";

    // init DAO
    DAO dao = new DAO();

    public PhoneBookObjectServer() {

        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socketToClient = serverSocket.accept();
             ObjectOutputStream oos = new ObjectOutputStream(socketToClient.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socketToClient.getInputStream())) {

            Object userInputFromClient;
            Friend friendToClient;
            String stringToClient;

            // Fuling enligt Sigrun då klienten bara tar emot Friend
            oos.writeObject(welcomeMessage); // intro message

            while ((userInputFromClient = ois.readObject()) != null) {
                if (userInputFromClient instanceof String) {
                    friendToClient = dao.getPersonByName(((String) userInputFromClient).trim());
                    System.out.println("Client sent: " + userInputFromClient);

                    if (friendToClient == null) {
                        System.out.println("Sent to client: Not found, " + userInputFromClient);
                        oos.writeObject("Personen du sökte hittades ej. Namn: " + userInputFromClient);
                    } else {
                        System.out.println("Sent to client: " + friendToClient);
                        oos.writeObject(friendToClient);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PhoneBookObjectServer();
    }
}
