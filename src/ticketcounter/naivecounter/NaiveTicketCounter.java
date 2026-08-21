package ticketcounter.naivecounter;


public class NaiveTicketCounter {

        private final NaiveRevenueCounter naiveRevenue;
        private final NaiveStock naiveStock;

        public NaiveTicketCounter(NaiveRevenueCounter revenue, NaiveStock stock) {
            this.naiveRevenue = revenue;
            this.naiveStock = stock;
        }
        public void processTicket(int price){
            if (price > 0){
                naiveStock.sellTicket();
                naiveRevenue.addRevenue(price);
            }
        }
}
