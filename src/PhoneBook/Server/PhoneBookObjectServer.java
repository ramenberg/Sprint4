package PhoneBook.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class PhoneBookObjectServer {

    int port = 54789;
    String welcomeMessage = "Ange namn på personen du söker: ";

    // init friends list from db.
    Database db = new Database();
    List<Friend> friendListFromDb = Database.getFriendList();
    public PhoneBookObjectServer() {

        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socketToClient = serverSocket.accept();
             ObjectOutputStream out = new ObjectOutputStream(socketToClient.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socketToClient.getInputStream())) {

            out.writeObject(welcomeMessage); // intro message
            Object userInputFromClient;

            while ((userInputFromClient = in.readObject()) != null) {
                Friend outputObjectToClient;
                if (userInputFromClient instanceof String) {
                    System.out.println("Client sent: " + userInputFromClient);
                } else {
                    outputObjectToClient = getFriend((String) userInputFromClient);
                    if (outputObjectToClient != null) {
                        System.out.println("Sent to client: " + outputObjectToClient);
                        out.writeObject(outputObjectToClient +" "+welcomeMessage);
                    } else {
                        System.out.println("Sent to client: Not found, " + userInputFromClient);
                        out.writeObject("Personen du sökte hittades ej. Namn: " + userInputFromClient);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected Friend getFriend(String name) {
        for (Friend f : friendListFromDb) {
            if ((name.equalsIgnoreCase(f.getFirstName()) ||
                    name.equalsIgnoreCase(f.getLastName()))) {
                System.out.println("Friend Found");
                return f;
            }

        }
        return null;
    }
    public static void main(String[] args) {
        new PhoneBookObjectServer();
    }
}
