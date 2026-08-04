# Expense Tracker Pro 📊

A sleek, modern desktop Java Swing application designed to track personal expenses, compute total expenditure in real time, and export data directly to CSV files.

---

## 🌟 Key Features

* **Modern & Responsive UI:** Custom rounded buttons, custom borders, and dynamic component styling.
* **Real-Time Total Calculation:** Instant aggregation and display of total expenditure.
* **Input Validation & Safety:** Exception handling for invalid numeric prices and empty text inputs.
* **CSV Data Export:** Clean export of itemized expenses to `.csv` format for easy reporting and accounting.
* **Clean Architecture:** Built using Object-Oriented Principles (OOP) with clear separation between Data Models, State Controllers, File I/O, and Swing View components.

---

## 🏗️ Architecture & Project Structure

The project follows the **Separation of Concerns (SoC)** principle:

```text
src/
└── shop/
    └── shoppingtracker/
        ├── Expense.java                  # Pure immutable domain model
        ├── ExpenseManager.java           # In-memory state controller & business logic
        ├── ExpenseTrackerPro.java        # Swing GUI view & layout container
        └── trackerstyling/
            ├── RoundedBorder.java        # Custom border UI component
            ├── RoundedButton.java        # Custom button UI component
            └── ExportCSV/
                └── ExportToCSV.java      # Dedicated CSV file I/O service

🎨 Design Highlights & Architectural Principles

Single Responsibility Principle (SRP):

   Expense: Encapsulates pure item attributes (name, amount).
   ExpenseManager: Controls in-memory collection state (List<Expense>) and mathematical calculations.
   ExportToCSV: Handles file output operations, directory selection, and string escaping completely decoupled from Swing logic.
   ExpenseTrackerPro: Handles UI assembly, layout hierarchy, and event handling.
   Encapsulation & Safety: Internal lists in ExpenseManager are exposed using Collections.unmodifiableList() to prevent unauthorized mutation from external classes.
   Robust File I/O & CSV Formatting: Proper string escaping wraps entries containing commas or quotes, preventing generated .csv files from corrupting.
   UI Defense: Revalidate and repaint operations run alongside input sanitization (try-catch for NumberFormatException) to prevent UI render artifacts and runtime crashes.

🚀 How to Run the Application
Prerequisites
   Java Development Kit (JDK): Version 11 or higher
   IDE: IntelliJ IDEA, Eclipse, or NetBeans

Option 1: Running inside IntelliJ IDEA (Recommended)
   Open the project folder in IntelliJ IDEA.
   Navigate to src/shop/shoppingtracker/ExpenseTrackerPro.java.
   Right-click ExpenseTrackerPro.java and select Run 'ExpenseTrackerPro.main()' (or click the green Play button next to the main method).

Option 2: Running via Terminal / Command Line
   Clone the Repository:

Bash
   git clone [https://github.com/mumberestanny-eng/Refactored-Calculator.git](https://github.com/mumberestanny-eng/Refactored-Calculator.git)
   cd Refactored-Calculator
Compile the Source Files:

Bash
   javac -d bin src/shop/shoppingtracker/*.java src/shop/shoppingtracker/trackerstyling/*.java src/shop/shoppingtracker/trackerstyling/ExportCSV/*.java
   Launch the GUI:

Bash
    java -cp bin shop.shoppingtracker.ExpenseTrackerPro
    
📸 Usage Workflow

    Enter the Item Name and Item Price in the Input Console.
    Click Add Item to cart to append the expense to your active list.
    Click Total to compute the total expenditure in real time.
    Click Export to CSV to select a file path and save your record!

🛠️ Tech Stack

    Language: Java 11+
    GUI Framework: Java Swing / AWT
    Version Control: Git / GitHub