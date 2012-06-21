import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.concurrent.*;

public class Sender {

    private int port;

    public Sender() {
        this.port = 1337;
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
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
        
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            System.out.println("bye");
            System.exit(0);
        }
    }

}
