package multibaygaragesimulator.model;

import java.util.concurrent.locks.ReentrantLock;

public class DiagnosticScanner {
    private final ReentrantLock lock = new ReentrantLock();

    public ReentrantLock getLock() {
        return lock;
    }
}
