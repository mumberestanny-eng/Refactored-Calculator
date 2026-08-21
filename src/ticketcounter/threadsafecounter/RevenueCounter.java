package ticketcounter.threadsafecounter;

import java.util.concurrent.atomic.AtomicInteger;

public class RevenueCounter {

    private AtomicInteger revenue = new AtomicInteger(0);

    public void addRevenue(int price){
        revenue.addAndGet(price);
    }

    public int getRevenue() {
        return revenue.get();
    }
}
