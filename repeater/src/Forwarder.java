package martello;

import java.io.*;
import java.net.*;
import java.util.*;

public class Forwarder extends BaseConnector {

    private List<DatagramSocket> repeaters;

    public Forwarder() {
        super("0.0.0.0", Repeater.F_PORT, false);
        repeaters = new ArrayList<DatagramSocket>();
    }

    protected Runnable handler(final DatagramPacket incoming) {
        return new Runnable() {
            public void run() {
                Repeater.listener.shoutMessage(incoming);
            }
        };
    }

    public void addRepeater(String address) {
        try {
            InetAddress addr = InetAddress.getByName(address);
            try {
                DatagramSocket repeater = new DatagramSocket();
                repeater.connect(addr, port);
                if (repeaters.add(repeater)) System.out.println("Added repeater " + address);
            } catch (SocketException e) {}
        } catch (UnknownHostException e) {}
        
    }

    public void tell(DatagramPacket msg) {
        msg.setPort(port);
        Iterator<DatagramSocket> r = repeaters.iterator();
        while (r.hasNext()) {
            DatagramSocket sock = r.next();
            try {
                sock.send(msg);
            } catch (IOException e) {}
        }
    }

}
