package waitnotify;

public class Demo {

    public static void main(String[] args) {
        int maxCount = 10;
        Thread t0 = new Thread(new SequencePrinter(0, maxCount));
        Thread t1 = new Thread(new SequencePrinter(1, maxCount));
        Thread t2 = new Thread(new SequencePrinter(2, maxCount));

        t0.start();
        t1.start();
        t2.start();
    }
}
