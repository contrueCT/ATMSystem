package util;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class AccountLockManager {
    private static final ConcurrentHashMap<String, ReentrantLock> accountLocks = new ConcurrentHashMap<>();


    public static ReentrantLock getAccountLock(String account) {
        return accountLocks.computeIfAbsent(account, k -> new ReentrantLock());

    }

    public static void acquireLocks(String... cardNumbers) {
        Arrays.sort(cardNumbers);
        for (String cardNumber : cardNumbers) {
            getAccountLock(cardNumber).lock();
        }
    }

    public static void releaseLocks(String... cardNumbers) {
        Arrays.sort(cardNumbers);
        for (int i = cardNumbers.length - 1; i >= 0; i--) {
            getAccountLock(cardNumbers[i]).unlock();
        }
    }
}
