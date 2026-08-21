package ticketcounter.threadsafecounter;

import java.util.*;

public class SellTicket {

    private final RevenueCounter revenue;
    private final Stock stock;

    public SellTicket(RevenueCounter revenue, Stock stock) {
        this.revenue = revenue;
        this.stock = stock;
    }
    public void processTicket(int price){
        if (price > 0){
           stock.sellTicket();
           revenue.addRevenue(price);
        }
    }
}
