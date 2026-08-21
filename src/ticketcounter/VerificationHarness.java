package ticketcounter;

import ticketcounter.logging.SimulationLog;
import ticketcounter.naivecounter.NaiveRevenueCounter;
import ticketcounter.naivecounter.NaiveStock;
import ticketcounter.threadsafecounter.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class VerificationHarness {

    public static void runSingleSimulation() throws InterruptedException {
        int initialStock = 10;
        int ticketPrice = 10;

        Stock stock = new Stock(initialStock);
        RevenueCounter revenue = new RevenueCounter();
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // 1. Seed customer queue
        for (int i = 1; i <= initialStock; i++) {
            queue.put("Customer-" + i);
        }

        // 2. Create clerk threads to process customers
        Runnable clerkTask = () -> {
            while (true) {
                String customer = queue.poll(); // Grab customer if available
                if (customer == null) break;   // Queue is empty, finish work

                if (stock.sellTicket()) {
                    revenue.addRevenue(ticketPrice);
                    SimulationLog.logSale(customer, stock.getStock(), revenue.getRevenue());
                }
            }
        };

        Thread clerk1 = new Thread(clerkTask, "Clerk-1");
        Thread clerk2 = new Thread(clerkTask, "Clerk-2");
        Thread clerk3 = new Thread(clerkTask, "Clerk-3");

        clerk1.start();
        clerk2.start();
        clerk3.start();
        clerk1.join();
        clerk2.join();
        clerk3.join();

        /*
        Here is the verification of the mathematical behind stock, revenue,  and sell ticket.
         */
        int ticketsSold = initialStock - stock.getStock();
        int expectedRevenue = ticketsSold * ticketPrice;
        int actualRevenue = revenue.getRevenue();

        NaiveStock naiveStock = new NaiveStock(initialStock);
        NaiveRevenueCounter naiveRevenueCounter = new NaiveRevenueCounter();
        BlockingQueue<String> naiveQueue = new LinkedBlockingQueue<>();

        // 1. Seed customer queue
        for (int i = 1; i <= initialStock; i++) {
            naiveQueue.put("Customer-" + i);
        }

        // 2. Create clerk threads to process customers
        Runnable clerkNaiveTask = () -> {
            while (true) {
                String customer = naiveQueue.poll(); // Grab customer if available
                if (customer == null) break;   // Queue is empty, finish work

                if (naiveStock.sellTicket()) {
                    naiveRevenueCounter.addRevenue(ticketPrice);
                    SimulationLog.logSale(customer, naiveStock.getStock(), naiveRevenueCounter.getRevenue());
                }
            }
        };

        Thread clerkNaive1 = new Thread(clerkNaiveTask, "ClerkNaive-1");
        Thread clerkNaive2 = new Thread(clerkNaiveTask, "ClerkNaive-2");
        Thread clerkNaive3 = new Thread(clerkNaiveTask, "ClerkNaive-3");

        clerkNaive1.start();
        clerkNaive2.start();
        clerkNaive3.start();
        clerkNaive1.join();
        clerkNaive2.join();
        clerkNaive3.join();

        /*
        Here is the verification of the mathematical behind stock, revenue,  and sell ticket.
         */
        int naiveTicketsSold = initialStock - naiveStock.getStock();
        int naiveExpectedRevenue = ticketsSold * ticketPrice;
        int naiveActualRevenue = naiveRevenueCounter.getRevenue();


        int totalRuns = 10;
        int threadSafePassedRuns = 0;
        int naivePassedRuns = 0;


        for (int i = 1; i <= totalRuns; i++) {
            boolean success = (actualRevenue == expectedRevenue);

            if (success) {
                threadSafePassedRuns++;
            } else {
                System.out.println("\nRun #" + i + " FAILED: Revenue mismatch detected!\n");
            }

        }
        for (int i = 0; i <= totalRuns; i++) {
            boolean naiveSuccess = (naiveActualRevenue == naiveExpectedRevenue);

            if (naiveSuccess) {
                naivePassedRuns++;
            } else {
                System.out.println("Run #" + i + " FAILED: Revenue mismatch detected!");
            }
        }
        printSummaryReport(naiveExpectedRevenue, naiveTicketsSold, naivePassedRuns, expectedRevenue, ticketsSold, threadSafePassedRuns);
    }

    public static void main(String[] args) throws InterruptedException {
        runSingleSimulation();
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