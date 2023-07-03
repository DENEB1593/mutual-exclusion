package io.dev;

/**
 * 별도의 LOCK을 걸지않고 진행한다.
 */
public class SequenceGenerator {
    private int currentValue = 0;

    public int getNextSequence() {
        currentValue = currentValue + 1;
        return currentValue;
    }
}
