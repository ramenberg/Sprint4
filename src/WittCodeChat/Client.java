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

    // for each user
    public void inputHandler() {
        try {
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            String messageToSend;
            // original
            while (socket.isConnected()) {
                try {
                    messageToSend = scanner.nextLine();
                    bufferedWriter.write(messageToSend); // to CH
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

//    public void sendMessage(String message) {
//        try {
//            bufferedWriter.write(userName + ": " + message);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//        } catch (IOException e) {
//            closeEverything(socket, bufferedReader, bufferedWriter);
//            e.printStackTrace();
//        }
//    }

//    public void changeClientUserName(String messageFromClient) throws IOException {
//        String[] messageSplit = messageFromClient.split(" ", 2);
//        if (messageSplit.length == 2) {
//            bufferedWriter.write(userName + " har bytt användarnamn till: " + messageSplit[1]); // to server console
//            userName = messageSplit[1];
//            ClientHandler.setClientUserName(userName);
//            System.out.println("Namnbytet lyckades. Nytt användarnamn: " + userName);
//        } else {
//            System.out.println("Inget användarnamn angavs. ");
//        }
//        bufferedWriter.newLine();
//        bufferedWriter.flush();
//    }
    public void listenForMessage() {
        new Thread(() -> {
            String messageFromGroupChat;

            while (socket.isConnected()) {
                try {
                    messageFromGroupChat = bufferedReader.readLine();
                    System.out.println(messageFromGroupChat);
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
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
        client.inputHandler();
    }
}
