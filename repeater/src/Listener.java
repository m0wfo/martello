package martello;

import java.net.*;

public class Listener extends BaseConnector {

    public Listener() {
        super(Repeater.ADDRESS, Repeater.L_PORT, true);
    }

    protected Runnable handler(final DatagramPacket incoming) {
        return new Runnable() {
            public void run() {
                String prefix = new String(incoming.getData(), 0, 2);
                if (!prefix.equals("_.")) {
                    Repeater.forwarder.tell(incoming);
                }
            }
        };
    }

}
