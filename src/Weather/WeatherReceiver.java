package Weather;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class WeatherReceiver {

    public WeatherReceiver() throws IOException {
    int port = 45678;
        try (DatagramSocket socket = new DatagramSocket(port)) {
            byte[] bytes = new byte[256]; // Storlek på meddelande. Om längre bryts till flera paket
            while (true) {
                DatagramPacket dgp = new DatagramPacket(bytes, bytes.length);
                socket.receive(dgp);
                String txt = new String(dgp.getData(), 0, dgp.getLength());
                System.out.println(txt);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new WeatherReceiver();
    }
}
