package atmSystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author confff
 */
public class ConcurrentMain {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(MainSystem::userSession);
        executor.execute(MainSystem::userSession);

        executor.shutdown();
    }
}
