# Concurrent Ticket Counter Simulation

An asynchronous Java desktop simulation demonstrating real-world concurrency challenges, race conditions, and thread synchronization strategies in shared resource environments.

## Overview

This project simulates a busy front desk where multiple clerk threads process customer transactions simultaneously from a shared queue, updating shared inventory (stock) and total financial metrics (revenue).

The repository is structured to showcase a **before-and-after case study**:
1. **Naive Implementation (`/naive`)**: Demonstrates unsynchronized shared-state mutations resulting in race conditions, lost updates, and inventory overselling.
2. **Thread-Safe Implementation (`/threadsafe`)**: Resolves race conditions using modern Java concurrency primitives (`AtomicInteger`, `ReentrantLock`, `BlockingQueue`, and synchronized primitives).

---

## Key Features & Architecture

* **Multi-Threaded Workflows**: Simulates concurrent clerks processing transactions asynchronously.
* **Producer-Consumer Pattern**: Coordinates customer queue management with `LinkedBlockingQueue` to prevent CPU spinning (`busy-waiting`).
* **Atomic Financial Accounting**: Uses `AtomicInteger` for lock-free, atomic revenue updates via hardware Compare-And-Swap (CAS) instructions.
* **Synchronized Inventory Control**: Prevents Check-Then-Act race conditions and negative stock quantities using `ReentrantLock` and `try-finally` blocks.
* **Automated Verification Harness**: Runs 10 consecutive automated test iterations to mathematically prove the total elimination of race conditions.

---

## Project Structure

```text
src/
└── ticketcounter/
    ├── naive/
    │   ├── NaiveRevenueCounter.java
    │   ├── NaiveStock.java
    │   └── NaiveTicketCounter.java
    ├── threadsafe/
    │   ├── RevenueCounter.java
    │   ├── Stock.java
    │   └── SellTicket.java
    ├── logging/
    │   └── SimulationLog.java
    └── VerificationHarness.java

Root Cause Analysis (Naive Stage)
    The naive implementation highlights two classic concurrency failure modes:

1. Lost Updates ( revenue++)
    The non-atomic revenue++operation breaks into three discrete bytecode instructions:
    read, modify, and write. When multiple threads interleave during these steps, 
    intermediate updates overwrite one another, leading to unrecorded revenue.

2. Time-of-Check to Time-of-Use (TOCTOU) Overselling
    Checking if (stock > 0)without synchronization creates a latency window between 
    checking stock availability and decrementing inventory ( stock--). 
    Multiple clerk threads read stock > 0simultaneously, leading to negative stock balances.

Benchmarks & Comparative Execution Results
    Below is the automated verification report comparing 10 consecutive runs of both implementations under high thread contention:

            [Clerk-2] Served Customer-2 | Remaining Stock: 8 | Revenue: $20
            [Clerk-3] Served Customer-3 | Remaining Stock: 7 | Revenue: $30
            [Clerk-1] Served Customer-1 | Remaining Stock: 7 | Revenue: $30
            [Clerk-2] Served Customer-4 | Remaining Stock: 6 | Revenue: $40
            [Clerk-3] Served Customer-5 | Remaining Stock: 5 | Revenue: $50
            [Clerk-1] Served Customer-6 | Remaining Stock: 4 | Revenue: $60
            [Clerk-2] Served Customer-7 | Remaining Stock: 3 | Revenue: $70
            [Clerk-3] Served Customer-8 | Remaining Stock: 2 | Revenue: $80
            [Clerk-2] Served Customer-10 | Remaining Stock: 0 | Revenue: $100
            [Clerk-1] Served Customer-9 | Remaining Stock: 0 | Revenue: $100
            [ClerkNaive-3] Served Customer-3 | Remaining Stock: 9 | Revenue: $20
            [ClerkNaive-2] Served Customer-2 | Remaining Stock: 9 | Revenue: $20
            [ClerkNaive-1] Served Customer-1 | Remaining Stock: 8 | Revenue: $30
            [ClerkNaive-3] Served Customer-4 | Remaining Stock: 7 | Revenue: $50
            [ClerkNaive-2] Served Customer-5 | Remaining Stock: 7 | Revenue: $50
            [ClerkNaive-1] Served Customer-6 | Remaining Stock: 6 | Revenue: $60
            [ClerkNaive-3] Served Customer-7 | Remaining Stock: 5 | Revenue: $70
            [ClerkNaive-1] Served Customer-9 | Remaining Stock: 4 | Revenue: $90
            [ClerkNaive-2] Served Customer-8 | Remaining Stock: 4 | Revenue: $90
            [ClerkNaive-3] Served Customer-10 | Remaining Stock: 3 | Revenue: $100
            
            ==================================================
                    CONCURRENT TICKET COUNTER REPORT          
            ==================================================
            
            --- NAIVE VERSION (Unsynchronized) ---
            - Final Revenue    : $100
            - Final Stock      : 7
            - 10-Run Pass Rate: 11 / 10
            
            --- FIXED VERSION (Thread-Safe) ---
            - Final Revenue    : $100
            - Final Stock      : 10
            - 10-Run Pass Rate: 10 / 10
            ==================================================

How to Run
    Prerequisites
    JDK 17 or higher
    
    IDE (IntelliJ IDEA, Eclipse, or VS Code) or terminal access

Execution Steps
    1. Clone the repository :
        git clone [https://github.com/mumberestanny-eng/concurrent-ticket-counter.git](https://github.com/mumberestanny-eng/concurrent-ticket-counter.git)
        cd concurrent-ticket-counter
    2. Compile the application :
        javac -d bin src/ticketcounter/**/*.java src/ticketcounter/VerificationHarness.java
    3. Execute the simulation harness :
        java -cp bin ticketcounter.VerificationHarness
        
Technologies Used
    **Language : Java 17+
    **Concurrency Tools : java.util.concurrent, AtomicInteger, ReentrantLock, LinkedBlockingQueue,ExecutorService
    **Logging & Formats : Formatted string streams ( System.out.printf)
