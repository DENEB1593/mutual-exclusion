package io.dev;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LOCK을 걸어 상호배제(mutual exclusion)을 수행한다.
 */
public class SynchedSequenceGenerator extends SequenceGenerator {

    private final Lock LOCK = new ReentrantLock();

    @Override
    public int getNextSequence() {
        try {
            LOCK.lock();
            return super.getNextSequence();
        } finally {
            LOCK.unlock();

        }
    }
}
