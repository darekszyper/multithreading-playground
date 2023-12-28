package bankexample;

import java.util.Random;

public class Demo {

    public static void main(String[] args) {
        Bank bank = new Bank(10);

        Random random = new Random();

        Runnable r = () -> {
            try {
                while (true) {
                    int fromAccount = random.nextInt(bank.size());
                    int toAccount = random.nextInt(bank.size());
                    double amount = (random.nextInt(5000, 10001)) / 100.0; // From 50$ to 100$
                    bank.transfer(fromAccount, toAccount, amount);
                    Thread.sleep(random.nextInt(5, 50));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Create and start threads
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(r);
            thread.start();
        }
    }
}
