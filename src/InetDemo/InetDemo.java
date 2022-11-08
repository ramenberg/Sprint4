package InetDemo;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetDemo {

    public static void main(String[] args) throws UnknownHostException {

        InetAddress ip1 = InetAddress.getLocalHost();
//        System.out.println(ip1);
//        System.out.println(ip1.getHostName());
//        System.out.println(ip1.getHostAddress());

        InetAddress ip2 = InetAddress.getByName("192.168.0.14");
        System.out.println(ip2);

        InetAddress ip3 = InetAddress.getLoopbackAddress(); // eg samma som getLocalHost
        System.out.println(ip3);

    }
}
