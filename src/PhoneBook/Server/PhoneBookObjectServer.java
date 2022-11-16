package PhoneBook.Server;

import PhoneBook.Resources.Friend;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class PhoneBookObjectServer extends Friend {

    int port = 54789;
    String welcomeMessage = "Ange namn på personen du söker: ";

    // init friends list from db.
    Database db = new Database();
    List<PhoneBook.Server.Friend> friendListFromDb = Database.getFriendList();
    public PhoneBookObjectServer() {
        super();

        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socketToClient = serverSocket.accept();
             ObjectOutputStream out = new ObjectOutputStream(socketToClient.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socketToClient.getInputStream())) {

            String userInputFromClient;
//            Friend friendToClient;

            // Fuling enligt Sigrun då klienten bara tar emot Friend
            out.writeObject(new PhoneBook.Server.Friend(null, welcomeMessage)); // intro message

            while ((userInputFromClient = (String)in.readObject()) != null) {

                PhoneBook.Server.Friend outputStringToClient;
                System.out.println("Client sent: " + userInputFromClient);
                outputStringToClient = getFriend(userInputFromClient);
                if (outputStringToClient != null) {
                    System.out.println("Sent to client: " + outputStringToClient);
                    out.writeObject(outputStringToClient +" "+welcomeMessage);
                } else {
                    System.out.println("Sent to client: Not found, " + userInputFromClient);
                    out.writeObject(new PhoneBook.Server.Friend(null, "Personen du sökte hittades ej. Namn: " +
                            userInputFromClient));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected PhoneBook.Server.Friend getFriend(String name) {
        for (PhoneBook.Server.Friend f : friendListFromDb) {
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
