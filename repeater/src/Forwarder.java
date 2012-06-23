package martello;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.concurrent.*;

public class Forwarder {

    private int port;

    public Forwarder() {
        this.port = 1337;
    }

    public static void something() {
        
        try {
            MulticastSocket s = new MulticastSocket(1337);

            try {
                InetAddress group = InetAddress.getByName("238.0.0.123");
                s.joinGroup(group);

                String msg = "ello";
                DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),
                                                       group, 1337);
                s.send(hi);
            } catch (UnknownHostException e) {
                System.out.println("uhost");
                System.exit(-1); }
        } catch (IOException e) {
            System.out.println("iox" + e);
            System.exit(-1); }
    }

}
