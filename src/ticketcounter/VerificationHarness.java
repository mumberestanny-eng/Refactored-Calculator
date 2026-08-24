package ticketcounter;

import ticketcounter.interfaces.*;
import ticketcounter.logging.SimulationLog;
import ticketcounter.naivecounter.*;
import ticketcounter.threadsafecounter.*;

import java.util.concurrent.*;

public class VerificationHarness {

        public record SimResult(int ticketsSold, int actualRevenue, int remainingStock) {}

        public static SimResult runOnce(Sellable stock, Countable revenue, int initialStock, int ticketPrice) throws InterruptedException {
            BlockingQueue<String> queue = new LinkedBlockingQueue<>();
            for (int i = 1; i <= initialStock; i++) {
                queue.put("Customer-" + i);
            }

            Runnable clerkTask = () -> {
                while (true) {
                    String customer = queue.poll();
                    if (customer == null) break;

                    if (stock.sellTicket()) {
                        revenue.addRevenue(ticketPrice);
                        SimulationLog.logSale(customer, stock.getStock(), revenue.getRevenue());
                    }
                }
            };

            Thread c1 = new Thread(clerkTask, "Clerk-1");
            Thread c2 = new Thread(clerkTask, "Clerk-2");
            Thread c3 = new Thread(clerkTask, "Clerk-3");

            c1.start(); c2.start(); c3.start();
            c1.join();  c2.join();  c3.join();

            int ticketsSold = initialStock - stock.getStock();
            return new SimResult(ticketsSold, revenue.getRevenue(), stock.getStock());
        }

        public static void runSimulationSuite() throws InterruptedException {
            int initialStock = 10;
            int ticketPrice = 10;
            int totalRuns = 10;

            int threadSafePassed = 0;
            int naivePassed = 0;

            SimResult lastThreadSafeResult = null;
            SimResult lastNaiveResult = null;

            for (int i = 0; i < totalRuns; i++) {
                // Thread-Safe Test Run
                Stock safeStock = new Stock(initialStock);
                RevenueCounter safeRevenue = new RevenueCounter();
                lastThreadSafeResult = runOnce(safeStock, safeRevenue, initialStock, ticketPrice);

                int expectedSafeRevenue = lastThreadSafeResult.ticketsSold() * ticketPrice;
                if (lastThreadSafeResult.actualRevenue() == expectedSafeRevenue) {
                    threadSafePassed++;
                } else {
                    System.out.println("ThreadSafe Run #" + (i + 1) + " FAILED");
                }

                // Naive Test Run
                NaiveStock naiveStock = new NaiveStock(initialStock);
                NaiveRevenueCounter naiveRevenue = new NaiveRevenueCounter();
                lastNaiveResult = runOnce(naiveStock, naiveRevenue, initialStock, ticketPrice);

                int expectedNaiveRevenue = lastNaiveResult.ticketsSold() * ticketPrice;
                if (lastNaiveResult.actualRevenue() == expectedNaiveRevenue) {
                    naivePassed++;
                } else {
                    System.out.println("Naive Run #" + (i + 1) + " FAILED: Revenue mismatch detected!");
                }
            }

            printSummaryReport(
                    lastNaiveResult.actualRevenue(), lastNaiveResult.remainingStock(), naivePassed,
                    lastThreadSafeResult.actualRevenue(), lastThreadSafeResult.remainingStock(), threadSafePassed
            );
        }

        public static void main(String[] args) throws InterruptedException {
            runSimulationSuite();
        }

        public static void printSummaryReport(
                int naiveRevenue, int naiveStock, int naivePassed,
                int fixedRevenue, int fixedStock, int fixedPassed) {

            System.out.println("\n==================================================");
            System.out.println("        CONCURRENT TICKET COUNTER REPORT          ");
            System.out.println("==================================================");

            System.out.println("\n--- NAIVE VERSION (Unsynchronized) ---");
            System.out.printf("- Final Revenue    : $%d%n", naiveRevenue);
            System.out.printf("- Final Stock      : %d%n", naiveStock);
            System.out.printf("- 10-Run Pass Rate: %d / 10%n", naivePassed);

            System.out.println("\n--- FIXED VERSION (Thread-Safe) ---");
            System.out.printf("- Final Revenue    : $%d%n", fixedRevenue);
            System.out.printf("- Final Stock      : %d%n", fixedStock);
            System.out.printf("- 10-Run Pass Rate: %d / 10%n", fixedPassed);

            System.out.println("==================================================");
        }
}
