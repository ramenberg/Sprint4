package ServerToClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    int port = 54789;
    InetAddress ip = InetAddress.getLocalHost();

    public Client() throws UnknownHostException {

        try(Socket s = new Socket(ip, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String txt;

            while ((txt = in.readLine()) != null) {
                System.out.println(txt);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        Client c = new Client();
    }
}
