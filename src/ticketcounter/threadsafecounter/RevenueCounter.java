package ticketcounter.threadsafecounter;

import ticketcounter.interfaces.Countable;

import java.util.concurrent.atomic.AtomicInteger;

public class RevenueCounter implements Countable {

    private final AtomicInteger revenue = new AtomicInteger(0);
    @Override
    public void addRevenue(int price){
        revenue.addAndGet(price);
    }
    @Override
    public int getRevenue() {
        return revenue.get();
    }
}
