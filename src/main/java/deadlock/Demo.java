package deadlock;

public class Demo {
    public static void main(String[] args) {

        // Thread 1 tries to lock resource1 intrinsic lock then DeadLock.class intrinsic lock
        DeadLock t1 = new DeadLock();

        // Thread 2 tries to lock DeadLock.class intrinsic lock then resource1 intrinsic lock
        DeadLock t2 = new DeadLock();

        // Thread 1 will not release resource1 intrinsic lock before it can acquire  DeadLock.class intrinsic lock
        t1.start();

        // Thread 2 will not release DeadLock.class intrinsic lock before it can acquire resource1 intrinsic lock
        t2.start();

        // Threads will wait for each other's locks eternally
    }
}


