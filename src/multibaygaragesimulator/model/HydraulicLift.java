package multibaygaragesimulator.model;

import java.util.concurrent.locks.ReentrantLock;

public class HydraulicLift{

    private final ReentrantLock lock = new ReentrantLock();

    public ReentrantLock getLock() {
        return lock;
    }
}
