package MultiCast;

import java.io.IOException;
import java.net.*;

public class MulticastReceiver {

    public MulticastReceiver() throws IOException {
    int port = 44444;
    InetAddress ip = InetAddress.getByName("234.234.234.234");
    MulticastSocket ds = new MulticastSocket(port);
    byte[] bytes = new byte[254];

    InetSocketAddress group = new InetSocketAddress(ip, port);
    NetworkInterface netIf = NetworkInterface.getByName("wlan2");

    ds.joinGroup(group, netIf);

        while (true) {
            DatagramPacket dgp = new DatagramPacket(bytes, bytes.length);
            ds.receive(dgp);
            System.out.println(dgp.getAddress());
            String s = new String(dgp.getData(), 0, dgp.getLength());
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws IOException {
        new MulticastReceiver();
    }
}
