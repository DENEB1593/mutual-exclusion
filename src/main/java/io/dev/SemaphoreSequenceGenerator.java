package io.dev;

import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.Semaphore;

/**
 * Semaphore는 임계영역에 접근할 수 있는 스레드 수를 정할 수 있다.
 * Semaphore의 permit을
 */
public class SemaphoreSequenceGenerator extends SequenceGenerator {

    private final Semaphore MUTEX = new Semaphore(1);

    @Override
    public int getNextSequence() {
        int seq = 0;
        try {
            MUTEX.acquire();
            seq = super.getNextSequence();
            MUTEX.release();
        } catch (InterruptedException e) {
            // 예외 처리 시 추가한다.
        } finally {
            return seq;
        }
    }
}
