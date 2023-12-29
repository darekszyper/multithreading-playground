package bankexample;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Bank {

    private static final Logger LOGGER = Logger.getLogger(Bank.class.getName());
    private final ReentrantLock lockWithFairAccess = new ReentrantLock(true);
    private final ReentrantLock lockWithDefaultAccess = new ReentrantLock();
    private final double initialTotalBalance;
    private final double[] accounts;
    private final Map<Thread, Integer> threadTransferCount = new HashMap<>();

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

    public void transfer(int from, int to, double amount) {
        lockWithFairAccess.lock();

        try {
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance initially was: %10.2f, and now is: %10.2f%n", initialTotalBalance, getTotalBalance());
            threadTransferCount.put(Thread.currentThread(), threadTransferCount.getOrDefault(Thread.currentThread(), 0) + 1);
        } finally {
            lockWithFairAccess.unlock();
        }
    }

    // Default lock which is a non-fair lock will make some threads starve.
/*
    public void transfer(int from, int to, double amount) {
        lockWithDefaultAccess.lock();

        try {
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance initially was: %10.2f, and now is: %10.2f%n", initialTotalBalance, getTotalBalance());
            threadTransferCount.put(Thread.currentThread(), threadTransferCount.getOrDefault(Thread.currentThread(), 0) + 1);
        } finally {
            lockWithDefaultAccess.unlock();
        }
    }
 */

    // Not synchronized transfers will possibly change total balance, because of Data Race.
/*
    public void transfer(int from, int to, double amount) {
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance initially was: %10.2f, and now is: %10.2f%n", initialTotalBalance, getTotalBalance());
        threadTransferCount.put(Thread.currentThread(), threadTransferCount.getOrDefault(Thread.currentThread(), 0) + 1);
    }
 */

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

    public void printTransferCount() {
        List<Map.Entry<Thread, Integer>> list = new ArrayList<>(threadTransferCount.entrySet());
        list.sort(Comparator.comparingLong(o -> o.getKey().getId()));

        for (Map.Entry<Thread, Integer> entry : list) {
            System.out.println(entry.getKey() + " performed " + entry.getValue() + " transfers.");
        }
    }
}
