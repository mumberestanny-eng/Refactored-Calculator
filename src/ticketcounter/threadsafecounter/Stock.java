package ticketcounter.threadsafecounter;

import ticketcounter.interfaces.Sellable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Stock implements Sellable {
    private int stock;

    private final ReentrantLock lock = new ReentrantLock();

    public Stock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean sellTicket() {
        try {

            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        lock.lock();
        try{
            if (stock > 0) {
                stock--;
                return true;
            }
            return false;
        } finally{
                lock.unlock();
            }

    }
    @Override
    public int getStock() {
        lock.lock();
        try{

            return stock;
        } finally{
            lock.unlock();
        }
    }
}
