package ticketcounter.naivecounter;

public class NaiveRevenueCounter {

    private int revenue = 0;
    public void addRevenue(int revenue) {
        this.revenue += revenue;
    }

    public int getRevenue() { return this.revenue; }
}
