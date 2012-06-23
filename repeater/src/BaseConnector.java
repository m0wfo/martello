package martello;

import java.io.*;
import java.net.*;

public abstract class BaseConnector {

    protected InetAddress address;
    protected int port;
    private DatagramSocket socket;
    private MulticastSocket multicastSocket;
    private boolean isMulti;

    public BaseConnector(String a, int p, boolean isMulticast) {
        this.isMulti = isMulticast;

        try {
            this.address = InetAddress.getByName(a);
        } catch (UnknownHostException e) {
            System.out.println("Unknown multicast host!");
            System.exit(-1);
        }

        this.port = p;

        try {
            if (isMulti) {
                this.multicastSocket = new MulticastSocket(port);
                multicastSocket.joinGroup(address);
            } else {
                socket = new DatagramSocket(port, address);
                socket.setBroadcast(false);
            }
        } catch (IOException e) {
            System.out.println("Unable to open socket!");
            System.exit(-1);
        }
    }

    protected DatagramSocket sock() {
        if (isMulti) return multicastSocket;
        return socket;
    }

    public void shoutMessage(DatagramPacket msg) {
        try {
            msg.setPort(port);
            sock().send(msg);
        } catch (IOException e) {}
    }

    public Runnable listenForMessages() {
        return new Runnable() {

            byte[] buffer = new byte[Repeater.P_SIZE];

            public void run() {
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, 0, Repeater.P_SIZE);
                    try {
                        sock().receive(packet);
                        handler(packet).run();
                    } catch (IOException e) {}
                }
            }
        };
    }

    protected abstract Runnable handler(final DatagramPacket incoming);

}
