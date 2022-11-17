package PhoneBook.MultiUser.Server;

import PhoneBook.MultiUser.Resources.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

            oos.writeObject(new Initiator());

            while ((userInputFromClient = ois.readObject()) != null) {
                if (userInputFromClient instanceof String) {
                    friendToClient = dao.getPersonByName(((String) userInputFromClient).trim());
                    System.out.println("Client sent: " + userInputFromClient);

                    if (friendToClient == null) {
                        System.out.println("Sent to client: Not found, " + userInputFromClient);
                        oos.writeObject(new Response(false));
                    } else {
                        System.out.println("Sent to client: " + friendToClient);
                        oos.writeObject(new Response(true, friendToClient));
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
