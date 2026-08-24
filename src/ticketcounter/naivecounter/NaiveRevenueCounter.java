package ticketcounter.naivecounter;

import ticketcounter.interfaces.Countable;
import ticketcounter.interfaces.Sellable;

public class NaiveRevenueCounter implements Countable {

    private int revenue = 0;
    @Override
    public void addRevenue(int revenue) {
        this.revenue += revenue;
    }
    @Override
    public int getRevenue() { return this.revenue; }
}
