package waitnotify;

public class SequencePrinter implements Runnable {
    private static final Object lock = new Object();
    private static int currentCount = 1;
    private final int threadId;
    private final int maxCount;

    public SequencePrinter(int threadId, int maxCount) {
        this.threadId = threadId;
        this.maxCount = maxCount;
    }

    @Override
    public void run() {
        while (true) {
            // Using synchronize statement with intrinsic lock of lock object
            synchronized (lock) {
                while (currentCount % 3 != threadId) {
                    System.out.println("T" + (threadId) + " waits because: " + currentCount + " % 3 = " + currentCount % 3);
                    try {
                        // If currentCount % 3 != threadId this threads will wait until thread with specific id will call lock.notifyAll()
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (currentCount > maxCount) break;
                System.out.println("T" + (threadId) + " " + currentCount++);
                lock.notifyAll();
            }
        }
    }
}
