package PhoneBook.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PhoneBookServer {

    DAO dao = new DAO();
    int port = 54789;
    String welcomeMessage = "Ange namn på personen du söker: ";

    public PhoneBookServer() {

        try (ServerSocket serverSocket = new ServerSocket(port);
                Socket socketToClient = serverSocket.accept();
             PrintWriter out = new PrintWriter(socketToClient.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socketToClient.getInputStream()))) {

            out.println(welcomeMessage); // intro message
            String userInputFromClient;

            while ((userInputFromClient = in.readLine()) != null) {
                //out.println(welcomeMessage);
                Friend outputStringToClient;
                System.out.println("Client sent: " + userInputFromClient);
                outputStringToClient = dao.getPersonByName((userInputFromClient));
                if (outputStringToClient != null) {
                    System.out.println("Sent to client: " + outputStringToClient);
                    out.println(outputStringToClient +" "+welcomeMessage);
                } else {
                    System.out.println("Sent to client: Not found, " + userInputFromClient);
                    out.println("Personen du sökte hittades ej. Namn: " + userInputFromClient);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new PhoneBookServer();
    }
}
