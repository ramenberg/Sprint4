package SigrunTDCLiveDemo;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    int port = 44444;
    String mess = "Hej hej";
    public Client() throws UnknownHostException {
        // try with resources
        InetAddress ip = InetAddress.getLocalHost();
        try(Socket sock = new Socket(ip, port)) {
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

            while (true) {
                out.println(mess);
                Thread.sleep(3000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws UnknownHostException {
        Client c = new Client();
    }
}
