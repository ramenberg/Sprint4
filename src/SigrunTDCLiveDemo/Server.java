package SigrunTDCLiveDemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    int port = 44444;

    public Server() {
        String temp;

        try(ServerSocket serverS = new ServerSocket(port)) {
            Socket sock = serverS.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));


            while ((temp = in.readLine()) != null) {
                System.out.println(temp);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {

        Server s = new Server();

    }

}
