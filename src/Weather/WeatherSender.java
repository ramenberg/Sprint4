package Weather;

import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class WeatherSender {

    public WeatherSender() throws IOException {

        var scan = new Scanner(System.in);

        int port = 45678;
        InetAddress ip = InetAddress.getLocalHost();
        DatagramSocket socket = new DatagramSocket();
        String message = "";
        String temp;

        String city = JOptionPane.showInputDialog(null, "Ange stad: ");
        if (city == null || city.length() == 0) {
            System.exit(0);
        }
        System.out.println(city);

//        System.out.print("Ange stad: ");
//        if (!scan.hasNextLine()) {
//            System.exit(0);
//        } else {
//            city = scan.nextLine();
//        }

        while (scan.hasNext()) {
            temp = scan.next();
            message = city + ", " + temp;
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
            socket.send(packet);
        }
    }

    public static void main(String[] args) throws IOException {
        new WeatherSender();
    }
}
