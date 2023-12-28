package daemon;

public class GarbageCollector extends Thread {

    @Override
    public void run() {
        while (true) {
            System.out.println("Collecting garbage...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
