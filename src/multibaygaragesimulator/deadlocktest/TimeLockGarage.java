package multibaygaragesimulator.deadlocktest;

import multibaygaragesimulator.model.*;

import java.util.concurrent.TimeUnit;

public class TimeLockGarage {
  public static void main(String[] args) throws InterruptedException {
       HydraulicLift lift = new HydraulicLift();
       DiagnosticScanner scanner = new DiagnosticScanner();

       // Worker 1 tries Scanner first, then Lift
       Runnable mechanic1Task = () -> performService(
               Thread.currentThread().getName(),
               scanner,
               lift,
               true
       );

       // Worker 2 tries Lift first, then Scanner (Opposite order to test contention)
       Runnable mechanic2Task = () -> performService(
               Thread.currentThread().getName(),
               scanner,
               lift,
               false
       );

       Thread m1 = new Thread(mechanic1Task, "Stanislas");
       Thread m2 = new Thread(mechanic2Task, "Justin");

       m1.start();
       m2.start();

       m1.join();
       m2.join();

       System.out.println("Garage operations finished cleanly.");
   }

   /*
   **Here is method helping us to synchronize both the hydraulic lift
   * the diagnostic scanner, thus if one mechanic holds one another
   * whether wait or back off and look for an available tool.
   *
    */
    private static void performService(String name, DiagnosticScanner scanner, HydraulicLift lift, boolean primaryIsScanner) {
        var lock1 = primaryIsScanner ? scanner.getLock() : lift.getLock(); // Ternary operation
        var lock2 = primaryIsScanner ? lift.getLock() : scanner.getLock();

        String resource1Name = primaryIsScanner ? "DiagnosticScanner" : "HydraulicLift";
        String resource2Name = primaryIsScanner ? "HydraulicLift" : "DiagnosticScanner";

        try {
            // Attempt to acquire primary resource within 500ms
            if (lock1.tryLock(500, TimeUnit.MILLISECONDS)) {
                System.out.println(name + " acquired " + resource1Name);

                try {
                    Thread.sleep(100); // Simulate setup time

                    // Attempt to acquire secondary resource within 500ms
                    if (lock2.tryLock(500, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println(name + " acquired " + resource2Name + " -> Starting work!");
                            Thread.sleep(600); // Simulate work using both resources
                        } finally {
                            lock2.unlock();
                            System.out.println(name + " released " + resource2Name);
                        }
                    } else {
                        System.out.println(name + " couldn't get " + resource2Name + " in time. Backing off...");
                    }
                } finally {
                    lock1.unlock();
                    System.out.println(name + " released " + resource1Name);
                }
            } else {
                System.out.println(name + " couldn't get " + resource1Name + ". Retrying later...");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
