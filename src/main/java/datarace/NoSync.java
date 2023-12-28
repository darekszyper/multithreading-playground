package datarace;

import java.util.Random;

public class NoSync implements Runnable {
    private static int count = 0;
    private final int threadId;

    public NoSync(int threadId) {
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
        int counter = count;
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        counter++;
        count = counter;
    }

    public static int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        NoSync t1 = new NoSync(1);
        NoSync t2 = new NoSync(2);
        NoSync t3 = new NoSync(3);

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
