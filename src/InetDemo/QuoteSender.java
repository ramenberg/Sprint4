package InetDemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class QuoteSender {

    final static String q1 = "Happiness is an uphill battle. Wear the good shoes. - Kurt Vonnegut";
    final static String q2 = "Without ice cream, there would be chaos and darkness. Don Kardong";
    final static String q3 = "When things go wrong, don't go with them. - Elvis Presley";
    final static String q4 = "Happiness never decreases by being shared. - Buddha";
    final static String q5 = "For every minute you are angry you lose 60 seconds of happiness. - Ralph Waldo Emerson";

    public QuoteSender() throws IOException, InterruptedException {

//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        List<String> quoteList = new ArrayList<>();
        quoteList.add(q1); quoteList.add(q2); quoteList.add(q3);
                quoteList.add(q4); quoteList.add(q5);

        InetAddress toIp = InetAddress.getLocalHost();
        int toPort = 55_555;
        DatagramSocket ds = new DatagramSocket();

        int quouteCounter = 0;

        while (true) {
            byte[] data = quoteList.get(quouteCounter).getBytes();
            DatagramPacket dgp = new DatagramPacket(data,data.length, toIp, toPort);
            ds.send(dgp);
//            System.out.println("Sent " + dgp);
            quouteCounter = (quouteCounter + 1) % 5;
            Thread.sleep(3000);
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new QuoteSender();
    }
}
