package daemon;

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        GarbageCollector garbageCollector = new GarbageCollector();

        // Making this Thread "Daemon", so it will be automatically terminated after Main Thread is finished.
        // If we comment this out, Main Thread will work eternally.
        garbageCollector.setDaemon(true);
        garbageCollector.start();

        System.out.println("Main process running");
        Thread.sleep(500);

        System.out.println("Main process running");
        Thread.sleep(500);

        System.out.println("Main process running");
        Thread.sleep(500);

        System.out.println("Main process running");
        Thread.sleep(500);

        System.out.println("Main process terminates");
    }
}
