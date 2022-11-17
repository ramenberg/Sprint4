package PhoneBook.WithProtocol.Client;

import PhoneBook.MultiUser.Resources.Initiator;
import PhoneBook.MultiUser.Resources.Response;

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
            Object fromServer;

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while ((fromServer = ois.readObject()) != null) {
                if (fromServer instanceof Initiator) {
                    System.out.println("Connection to server established successfully.");
                    System.out.print("Skriv namnet på personen du söker: ");
                } else if (fromServer instanceof Response) {
                    // if false - skriv "hittades ej"
                    if (!((Response) fromServer).isFound()) {
                        System.out.println("Personen du sökte hittades ej.");
                    } else {
                        System.out.println("Server sent: " + (((Response) fromServer).getFriend()));
                        // if true - skriv ut person
                    }
                    System.out.print("Skriv namnet på personen du söker: ");
                }

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
