package martello;

import java.util.concurrent.*;

public class Repeater {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            System.out.println("bye");
            System.exit(0);
        }
    }

}
