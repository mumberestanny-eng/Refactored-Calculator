# Multi-Bay Garage Simulator

A concurrent Java application demonstrating multi-threaded worker management, asynchronous result aggregation, concurrency benchmarking, intentional deadlock diagnosis, and resolution using timed lock acquisition.

---

## Core Features

* **Thread Pool Management:** Uses `ExecutorService` to simulate concurrent mechanic bays executing service jobs asynchronously.
* **Callable & Future Aggregation:** Models garage service operations as `Callable<ServiceReport>` tasks that return execution summaries via `Future` handles.
* **Performance Benchmarking:** Compares single-threaded sequential execution against a multi-threaded fixed thread pool to calculate real-world parallel speedup (Speedup = sequential / parallel).
* **Deadlock Simulation & Analysis:** Demonstrates circular resource contention (`DiagnosticScanner` vs. `HydraulicLift`) using standard intrinsic locking.
* **Timed Lock Resolution:** Resolves thread contention and prevents permanent deadlocks using `ReentrantLock.tryLock()` with timeout recovery mechanisms.

---

## Project Structure

```text

multibaygaragesimulator/
├── model/
│   ├── DiagnosticScanner.java     # Shared diagnostic tool resource
│   ├── HydraulicLift.java         # Shared lifting equipment resource
│   └── ServiceReport.java         # Data model for service execution metrics
├── executiontimecheck/
│   └── ThreadsExecutionTime.java  # Single-thread vs multi-thread benchmarking suite
└── deadlocktest/
    ├── NonTimeLockGarage.java     # Reproduces classic circular wait deadlock
    └── TimeLockGarage.java        # Resolves deadlock via ReentrantLock tryLock()

Module Overview
1. Domain Models ( model)
    ServiceReport: Encapsulates service job details including job ID, description, duration, and status.
    DiagnosticScanner& HydraulicLift: Represent physical garage equipment requiring mutual exclusion across active mechanic threads.

2. Execution Benchmarking ( executiontimecheck)
    ThreadsExecutionTime: Submits a batch of service jobs to a SingleThreadExecutorand a 4-thread FixedThreadPool.
    Calculates total elapsed time and prints the resulting speedup factor.

3. Concurrency Safety & Deadlocks ( deadlocktest)
    NonTimeLockGarage: Simulates two threads attempting to acquire DiagnosticScannerand HydraulicLiftin opposite orders,
    triggering an unrecoverable deadlock detectable via jstack.
    TimeLockGarage: Replaces standard intrinsic locks with explicit
    ReentrantLock.tryLock(timeout, timeUnit)calls to ensure threads back off gracefully on resource contention.

How to Run
    Requirements
        Java JDK 17 or higher
        Any standard Java IDE (IntelliJ IDEA, Eclipse) or CLI terminal

    Running Benchmarks
        Execute ThreadsExecutionTime.java to view the parallel speedup log:
          java multibaygaragesimulator.executiontimecheck.ThreadsExecutionTime

Reproducing & Diagnosing Deadlock
    1. Run NonTimeLockGarage.java.
    
    2. Inspect the terminal to confirm execution has halted.
    
    3. Open a terminal and extract the active thread dump:
        jps          # Locate PID for NonTimeLockGarage
        jstack <PID> # View deadlock report
        
Running Deadlock Recovery
    Execute TimeLockGarage.javato verify automatic lock backoff and clean execution completion:
      java multibaygaragesimulator.deadlocktest.TimeLockGarage