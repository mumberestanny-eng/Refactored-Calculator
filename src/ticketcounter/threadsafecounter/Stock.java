package ticketcounter.threadsafecounter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Stock {
    private int stock;

    private final ReentrantLock lock = new ReentrantLock();

    public Stock(int stock) {
        this.stock = stock;
    }


    public boolean sellTicket() {
            lock.lock();
            try{
                if (stock > 0) {
                    try {

                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    stock--;
                    return true;
                }
                return false;
        } finally{
                lock.unlock();
            }

    }

    public int getStock() {
        lock.lock();
        try{

            return stock;
        } finally{
            lock.unlock();
        }
    }
}
