package ticketcounter.naivecounter;

public class NaiveStock {

    private int stock;

    public NaiveStock(int stock) {
        this.stock = stock;
    }
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
    public int getStock() {
        return stock;
    }
}
