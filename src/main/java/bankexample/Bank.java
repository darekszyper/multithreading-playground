package bankexample;

import java.util.Random;
import java.util.logging.Logger;

public class Bank {

    private static final Logger LOGGER = Logger.getLogger(Bank.class.getName());
    private final double initialTotalBalance;
    private final double[] accounts;

    public Bank(int numAccounts) {
        accounts = new double[numAccounts];
        initializeRandomAccounts();
        initialTotalBalance = getTotalBalance();
    }

    private void initializeRandomAccounts() {
        Random random = new Random();
        for (int i = 0; i < accounts.length; i++) {
            double amount = (random.nextInt(50000, 100001)) / 100.0; // From 500$ to 1000$
            accounts[i] = amount;
            LOGGER.info("Account number " + i + " initialized with: " + accounts[i] + "$");
        }
    }

    public synchronized void transfer(int from, int to, double amount) {
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance initially was: %10.2f, and now is: %10.2f%n", initialTotalBalance, getTotalBalance());
    }

    public double getTotalBalance() {
        double total = 0;
        for (double accountBalance : accounts) {
            total += accountBalance;
        }
        return total;
    }

    public int size() {
        return accounts.length;
    }
}
