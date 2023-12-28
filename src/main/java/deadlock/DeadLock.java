package deadlock;

public class DeadLock extends Thread {

    private static int autoIncrement = 0;
    private final int id;

    private final static Object resource1 = "Resource1";

    public DeadLock() {
        this.id = ++autoIncrement;
    }

    @Override
    public void run() {
        if (id == 1) {
            // Using synchronize statement with intrinsic locks of specific object and general DeadLock.class
            synchronized (resource1) {
                System.out.println("Thread " + id + ": Locked resource 1 intrinsic lock");

                synchronized (DeadLock.class) {
                    System.out.println("Thread " + id + ": Locked DeadLock.class intrinsic lock");
                }
            }
        } else {
            synchronized (DeadLock.class) {
                System.out.println("Thread " + id + ": Locked DeadLock.class intrinsic lock");

                synchronized (resource1) {
                    System.out.println("Thread " + id + ": Locked resource 1 intrinsic lock");
                }
            }
        }
    }

}
