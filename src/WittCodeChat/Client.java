package WittCodeChat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;


    public Client(Socket socket, String userName) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.userName = userName;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            String messageToSend;
            // original
//            while (socket.isConnected()) {
//                messageToSend = scanner.nextLine();
//                bufferedWriter.write(userName + ": " + messageToSend);
//                bufferedWriter.newLine();
//                bufferedWriter.flush();
//            }

            // TODO quit-funktionen funkar ej
            // test med ändring av username och quit
            while (socket.isConnected()) {
                try {
                    while (true) {
                        messageToSend = scanner.nextLine();
                        if (messageToSend.startsWith("/quit") ||
                                messageToSend.startsWith("/exit")) {
                            closeEverything(socket, bufferedReader, bufferedWriter);
                            break;
                        } else if (messageToSend.startsWith("/rename")) {
                            changeClientUserName(messageToSend);
                        } else {
                            bufferedWriter.write(userName + ": " + messageToSend);
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        }
                    }
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
                }
            }

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void changeClientUserName(String messageFromClient) throws IOException {
        String[] messageSplit = messageFromClient.split(" ", 2);
        if (messageSplit.length == 2) {
            bufferedWriter.write(userName + " har bytt användarnamn till: " + messageSplit[1]); // to server console
            userName = messageSplit[1];
            System.out.println("Namnbytet lyckades. Nytt användarnamn: " + userName);
        } else {
            System.out.println("Inget användarnamn angavs. ");
        }
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    public void listenForMessage() {
        new Thread(() -> {
            String messageFromGroupChat;

            while (socket.isConnected()) {
                try {
                    messageFromGroupChat = bufferedReader.readLine();
                    System.out.println(messageFromGroupChat);
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader,
                                BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Välj ett användarnamn för chatten: ");
        String userName = scanner.nextLine().trim();
        Socket socket = new Socket("localhost", 12345);
        Client client = new Client(socket, userName);
        client.listenForMessage();
        client.sendMessage();
    }
}
