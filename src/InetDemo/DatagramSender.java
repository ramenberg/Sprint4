package InetDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class DatagramSender {


    public DatagramSender() throws IOException {
        // sys in/sys out kräver ej try w/ resources
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        InetAddress ip = InetAddress.getLocalHost();
        int toPort = 44_444;
        DatagramSocket ds = new DatagramSocket();
        String txt;

        while ((txt = in.readLine()) != null) {
            byte[] bytes = txt.getBytes();
            DatagramPacket dgp = new DatagramPacket(bytes,bytes.length, ip, toPort);
            ds.send(dgp);
        }

    }

    public static void main(String[] args) throws IOException {
        new DatagramSender();
    }
}
