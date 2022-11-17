package PhoneBook.WithProtocol.Server;

import PhoneBook.WithProtocol.Resources.*;

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

            String userInputFromClient;
            String outputStringToClient;

            PhoneBookProtocol protocol = new PhoneBookProtocol();
            outputStringToClient = protocol.inputFromUser(null);
            out.println(outputStringToClient);

            while ((userInputFromClient = in.readLine()) != null) {
                outputStringToClient = protocol.inputFromUser(userInputFromClient);
                out.println(outputStringToClient);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new PhoneBookServer();
    }
}
