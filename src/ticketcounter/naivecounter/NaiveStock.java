package ticketcounter.naivecounter;

import ticketcounter.interfaces.Sellable;

public class NaiveStock implements Sellable {

    private int stock;

    public NaiveStock(int stock) {
        this.stock = stock;
    }
    @Override
    public boolean sellTicket(){
        if (stock > 0){
            try {
                Thread.sleep(1); // simulate a stock that is actually decreasing.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            stock--;
            return true;
        }
        return false;
    }
    @Override
    public int getStock() {
        return stock;
    }
}
