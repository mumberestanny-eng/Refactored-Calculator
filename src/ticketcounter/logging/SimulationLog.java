package ticketcounter.logging;

public class SimulationLog {

    public static void logSale(String customer, int remainingStock, int revenue){
        String threadName = Thread.currentThread().getName();

        System.out.printf("[%s] Served %s | Remaining Stock: %d | Revenue: $%d%n", threadName, customer, remainingStock, revenue);
    }
}
