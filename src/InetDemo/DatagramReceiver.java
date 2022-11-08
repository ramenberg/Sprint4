package InetDemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DatagramReceiver {

    int port = 44_444;
    DatagramSocket ds = new DatagramSocket(port);
    byte[] bytes = new byte[254]; // storlek på meddelande. om längre bryts till flera paket

    public DatagramReceiver() throws IOException {
        while (true) {
            DatagramPacket dgp = new DatagramPacket(bytes, bytes.length);
            ds.receive(dgp);
            System.out.println(dgp.getAddress());
            String s = new String(dgp.getData(), 0, dgp.getLength());
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws IOException {
        new DatagramReceiver();
    }
}
