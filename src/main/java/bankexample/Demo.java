package bankexample;

import java.util.Random;

public class Demo {

    public static void main(String[] args) {
        Bank bank = new Bank(10);

        Random random = new Random();

//        Runnable r = () -> {
//            try {
//                while (!Thread.currentThread().isInterrupted()) {
//                    int fromAccount = random.nextInt(bank.size());
//                    int toAccount = random.nextInt(bank.size());
//                    double amount = (random.nextInt(5000, 10001)) / 100.0; // From 50$ to 100$
//                    bank.transfer(fromAccount, toAccount, amount);
//                    Thread.sleep(random.nextInt(5, 50));
//                }
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        };

        Runnable r = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                int fromAccount = random.nextInt(bank.size());
                int toAccount = random.nextInt(bank.size());
                double amount = random.nextInt(5000, 10001) / 100.0; // From 50$ to 100$
                bank.transfer(fromAccount, toAccount, amount);
            }
        };

        Thread[] threads = new Thread[100];

        // Create and start threads
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(r);
            threads[i].start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (Thread thread : threads) {
            thread.interrupt();
        }

        // wait for threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        bank.printTransferCount();
    }
}
