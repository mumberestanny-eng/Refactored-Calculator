package multibaygaragesimulator.deadlocktest;

import multibaygaragesimulator.model.DiagnosticScanner;
import multibaygaragesimulator.model.HydraulicLift;

import java.util.concurrent.ExecutionException;

public class NonTimeLockGarage {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        HydraulicLift lift = new  HydraulicLift();
        DiagnosticScanner scanner = new DiagnosticScanner();

        Runnable mechanic = () -> {
            synchronized (scanner) {
                System.out.println(Thread.currentThread().getName()+" has acquired the scanner");
                try{
                    Thread.sleep(100);
                }
               catch (InterruptedException _){Thread.currentThread().interrupt();}
                synchronized (lift){
                    System.out.println(Thread.currentThread().getName()+" has acquired the lift, starting work!");
                    try{
                        Thread.sleep(500);
                    } catch (InterruptedException _){Thread.currentThread().interrupt();}
                }
            }
        };

        Runnable mechanic2 = () -> {
            synchronized (lift) {
                System.out.println(Thread.currentThread().getName()+" has acquired the lift");
                try{
                    Thread.sleep(500);
                }
                catch (InterruptedException _){Thread.currentThread().interrupt();}
                synchronized (scanner){
                    System.out.println(Thread.currentThread().getName()+" has acquired the scanner, starting work!");
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException _){Thread.currentThread().interrupt();}
                }
            }
        };

        Thread mechanic1Task = new Thread(mechanic, "Stanislas");
        Thread mechanic2Task = new Thread(mechanic2, "Justin");

        mechanic1Task.start();
        mechanic2Task.start();

        mechanic1Task.join();
        mechanic2Task.join();

        System.out.println("Gone finishing");
    }
}
