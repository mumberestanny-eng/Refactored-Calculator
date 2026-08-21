package multibaygaragesimulator.executiontimecheck;

import multibaygaragesimulator.model.ServiceReport;

import java.util.concurrent.*;


import java.util.ArrayList;
import java.util.List;

public class ThreadsExecutionTime {

    private static List<Callable<ServiceReport>> createTasks(List<ServiceReport> serviceReports) {
        List<Callable<ServiceReport>> callables = new ArrayList<>();
        for (ServiceReport report : serviceReports) {
            callables.add(() -> {
                Thread.sleep((long) report.getDurationMs());
                return report;
            });
        }
        return callables;
    }

    public static void runSimulationWithSingleThread(List<ServiceReport> serviceReports) throws InterruptedException {
        if (serviceReports.isEmpty()) throw new IllegalArgumentException("Service report list is empty");

        // 1. Sequential Execution (1 Thread)
        List<Callable<ServiceReport>> sequentialTasks = createTasks(serviceReports);
        ExecutorService singleExecutor = Executors.newSingleThreadExecutor();

        long startTime = System.currentTimeMillis();
        List<Future<ServiceReport>> futures = singleExecutor.invokeAll(sequentialTasks);
        singleExecutor.shutdown();
        long sequentialTiming = System.currentTimeMillis() - startTime;

        // 2. Parallel Execution (4 Threads)
        List<Callable<ServiceReport>> parallelTasks = createTasks(serviceReports);
        ExecutorService poolExecutor = Executors.newFixedThreadPool(4);

        long startTime2 = System.currentTimeMillis();
        poolExecutor.invokeAll(parallelTasks);
        poolExecutor.shutdown();
        long parallelTiming = System.currentTimeMillis() - startTime2;


        System.out.println("=======================================");
        System.out.println("EXECUTION TIME TEST OF " + serviceReports.size() + " SERVICE JOBS");
        System.out.println("Execution time with 1 thread : " + sequentialTiming + " ms");
        System.out.println("Execution time with 4 threads: " + parallelTiming + " ms\n");

        double speedUpFactor = (double) sequentialTiming / parallelTiming;
        System.out.printf("Speedup factor gain         : %.2fx%n%n", speedUpFactor);

        System.out.println("Completed Service Reports:");
        futures.forEach(future -> {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error processing job: " + e.getMessage());
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Simulation start......\n");

        List<ServiceReport> serviceReports = List.of(
                new ServiceReport(1, "Draining gearbox oil", 300, true),
                new ServiceReport(2, "Scanning OBD data", 400, true),
                new ServiceReport(3, "Overhauling automatic gearbox", 700, true),
                new ServiceReport(4, "Replacing clutch pack", 500, true),
                new ServiceReport(5, "Flushing transmission fluid", 300, true),
                new ServiceReport(6, "Calibrating solenoids", 400, true),
                new ServiceReport(7, "Torque converter inspection", 600, true),
                new ServiceReport(8, "Brake pad replacement", 200, true)
        );

        runSimulationWithSingleThread(serviceReports);

        System.out.println("\nSimulation end......");
    }
}