# Mechanic Workshop Analyser

A modular, functional Java application designed to parse CSV datasets (`employees_100.csv`, parts files), 
model core workshop domain entities, analyze inventory stock, and evaluate employee payroll metrics using 
Java Streams and functional programming paradigms.

---

## Architecture & System Design

The system relies on immutable domain models, custom CSV parsers, dedicated functional analytical modules, and main executable classes:

```text
                           +------------------------+
                           |     CsvFileParser      |
                           +-----------+------------+
                                       |
                       +---------------+---------------+
                       |                               |
                       v                               v
                +-------------+                 +--------------+
                |    Part     |                 |   Employee   |
                +------+------+                 +------+-------+
                       |                               |
                       v                               v
           +-----------------------+       +-----------------------+
           | WorkShopPartAnalyser  |       |WorkShopEmployeeAnalyser|
           +-----------+-----------+       +-----------+-----------+
                       |                               |
                       v                               v
           +-----------------------+       +-----------------------+
           |     PartAnalyser      |       |   EmployeeAnalyser    |
           |     (Main Entry)      |       |     (Main Entry)      |
           +-----------------------+       +-----------------------+

Core Domain Models
Part (mechanic.Part): Models workshop inventory attributes:

        **name (String)
        **category (String)
        **price (double)       
        **stock (int)       
        **supplier (String)

Employee (mechanic.Employee): Models workshop staff attributes:

        **name (String)       
        **work (String)        
        **department (String)        
        **salary (double)

Features
📁 CSV File Parser (mechanic.workshop.partanalyser.csvhandler.CsvFileParser)
Handles file I/O and converts CSV input lines into domain models:

        **NIO File Reading: Uses Files.readAllLines for efficient parsing.       
        **Validation & Cleaning: Automatically ignores CSV headers and drops lines that do not match expected column counts (5 columns for Part, 4 columns for Employee).        
        **Ingestion: Trims whitespace and handles parsing operations gracefully.

🛠️ Workshop Part Analyser (mechanic.workshop.partanalyser.WorkShopPartAnalyser)
Provides detailed analytics and reporting for workshop inventory:

        **Filtering & Ordering: Query parts by minimum price floor or custom stock thresholds.
        **Reorder & Critical Stock Tracking: Identifies critical items (stock $\le 1$) and generates low-stock warnings (stock $< 3$).
        **Group Aggregation: Computes category counts, supplier groupings, and category-level average prices.
        **Financial Statistics: Summarizes full inventory monetary value ($Price \times Stock$) and outputs comprehensive metrics (DoubleSummaryStatistics).

👥 Workshop Employee Analyser (mechanic.workshop.WorkShopEmployeeAnalyser)
Delivers administration and payroll metrics:

        **Staff Directory: Aggregates distinct employees, departments, and specific roles.
        **Salary Analytics: Groups average salaries by department and role; highlights top earners and low-income thresholds.
        **Hierarchical Sorting: Sorts employee records sequentially by Name $\rightarrow$ Salary (Descending) $\rightarrow$ Department $\rightarrow$ Role.
        **Payroll Overview: Generates summary headcount, lowest, highest, and average compensation statistics.

Exact Directory Structure

        src/
        ├── mechanic/
        │   ├── Employee.java                         # Employee Entity Model
        │   ├── Part.java                             # Part Entity Model
        │   └── workshop/
        │       ├── PartAnalyser.java                 # Main Executable: Inventory Analysis CLI
        │       ├── WorkShopEmployeeAnalyser.java    # Employee Analytics Processor
        │       └── partanalyser/
        │           ├── EmployeeAnalyser.java         # Main Executable: Workforce Analysis CLI
        │           ├── WorkShopPartAnalyser.java    # Inventory Analytics Processor
        │           └── csvhandler/
        │               └── CsvFileParser.java      # CSV Ingestion & Entity Transformer
        ├── com/                                      # Application package
        ├── GUIprogramming/                           # UI components
        ├── shop/                                     # Shop module
        │   └── Main.java                             # Application entry point
        ├── employees_100.csv                         # Workforce Dataset
        ├── Expenses.csv                              # Expenses Dataset
        └── .gitignore                                # Git ignore file

Requirements

        ->Java Development Kit (JDK): 17 or higher (utilizes Stream.toList(), 
        ->Files.readAllLines, and enhanced Java Streams API features).

Expected Input CSV Specifications
Parts CSV File (garage_parts_100.csv)
Structure: Name, Category, Price, Stock, Supplier

        Name, Category, Price, Stock, Supplier
        Brake Pad, Brakes, 45.50, 12, Bosch
        Oil Filter, Engine, 12.00, 2, Fram
        Spark Plug, Engine, 8.75, 0, NGK

Employees CSV File (employees_100.csv)
Structure: Name, Work, Department, Salary

        Name, Work, Department, Salary
        John Doe, Mechanic, Maintenance, 45000.00
        Jane Smith, Manager, Logistics, 65000.00

How to Run
1. Compile the Project
Open a terminal in the root directory of the project containing the src/ folder and run:

        javac -d bin (find src -name "*.java")

2. Execute Inventory Analysis (PartAnalyser)
Executes the main entry point processing garage_parts_100.csv:

        java -cp bin mechanic.workshop.PartAnalyser

3. Execute Workforce & Payroll Analysis (EmployeeAnalyser)
Executes the main entry point processing employees_100.csv:

        java -cp bin mechanic.workshop.partanalyser.EmployeeAnalyser

