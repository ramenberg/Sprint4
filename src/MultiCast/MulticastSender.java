package MultiCast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSender {


    public MulticastSender() throws IOException {
        // sys in/sys out kräver ej try w/ resources
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        InetAddress ip = InetAddress.getByName("234.234.234.234");
        int toPort = 44444;
        MulticastSocket ds = new MulticastSocket(toPort);
        String txt;

        while ((txt = in.readLine()) != null) {
            byte[] bytes = txt.getBytes();
            DatagramPacket dgp = new DatagramPacket(bytes,bytes.length, ip, toPort);
            ds.send(dgp);
        }

    }

    public static void main(String[] args) throws IOException {
        new MulticastSender();
    }
}
