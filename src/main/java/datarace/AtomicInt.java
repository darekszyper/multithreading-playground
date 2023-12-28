package datarace;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicInt implements Runnable {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int threadId;

    public AtomicInt(int threadId) {
        this.threadId = threadId;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            System.out.println("Thread number: *" + threadId + "* is running.");
            addOneToStaticVariable();
        }
    }

    private void addOneToStaticVariable() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        count.addAndGet(1);

    }

    public static int getCount() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicInt t1 = new AtomicInt(1);
        AtomicInt t2 = new AtomicInt(2);
        AtomicInt t3 = new AtomicInt(3);

        Thread tt1 = new Thread(t1);
        Thread tt2 = new Thread(t2);
        Thread tt3 = new Thread(t3);

        tt1.start();
        tt2.start();
        tt3.start();

        tt1.join();
        tt2.join();
        tt3.join();

        System.out.println("should be 30: " + getCount());
    }
}
