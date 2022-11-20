package WittCodeChat;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter; // send msg C to C

    private String clientUserName;

    public String getClientUserName() {
        return clientUserName;
    }

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientUserName = bufferedReader.readLine().trim();
            clientHandlers.add(this);
            broadcastMessage("har anslutit till chatten. ");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() { // everything here runs in a separate thread
        String messageFromClient;

        // original
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                if (messageFromClient.equalsIgnoreCase("/quit") ||
                        messageFromClient.equalsIgnoreCase("/exit")) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
                } else {
                    broadcastMessage(messageFromClient);
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (clientHandler != this) { // only send message to other users
                    clientHandler.bufferedWriter.write("<" + clientUserName + "> " + messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    // when client disconnects
    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("har lämnat chatten. "); // to other users
        System.out.println(clientUserName + " har kopplat från. "); // to server
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader,
                                BufferedWriter bufferedWriter) {
        removeClientHandler();
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
}
