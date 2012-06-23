package martello;

import java.util.concurrent.*;

public class Repeater {

    public static final int P_SIZE = 512; // Conservative MTU
    public static final int L_PORT = 49100; // Multicast listening port
    public static final int F_PORT = 49101; // Unicast forwarding port
    public static final String ADDRESS = "224.10.10.1"; // Multicast
                                                          // group

    public static Listener listener;
    public static Forwarder forwarder;

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        listener = new Listener();
        forwarder = new Forwarder();

        for (String addr : args) {
            forwarder.addRepeater(addr);
        }

        service.submit(listener.listenForMessages());
        service.submit(forwarder.listenForMessages());

        System.out.println("Listening...");

        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            System.out.println("Closing...");
            System.exit(0);
        }
    }

}
