# Desktop Java Calculator (SOLID Refactoring)

A modular desktop application built with **Java Swing and AWT**, refactored to align with **SOLID design principles** and **Clean Code best practices**.

---

## 📋 Overview
This project was refactored from a tightly-coupled monolithic Swing component into a layered architecture. The interface presentation is isolated from state management and arithmetic calculation.

---

## 🛠️ Refactoring & Architectural Improvements

### 1. Single Responsibility Principle (SRP)
* **Before:** The original `Calculator` class managed UI layout, button creation, user interaction, arithmetic state, string manipulation, and mathematical evaluation.
* **After:** Divided into distinct components:
    * `Calculator.java`: Dedicated exclusively to Swing frame rendering, layout creation, and visual styling.
    * `CalculatorEngine.java`: Pure Java business logic class handling operational state, string parsing, and arithmetic execution.

### 2. Clean Code & Safety Upgrades
* **String Comparison Safety:** Replaced raw `==` string checks with string content methods (`.equals()`), preventing equality bugs in execution flow.
* **Lambda Listeners:** Replaced standard `implements ActionListener` overhead with concise, inline Lambda expressions for event registration.
* **Edge Case Handling:** Added divide-by-zero checks (`Error` state) and decimal duplicate prevention logic.
* **Encapsulation:** Enforced `private final` immutability across UI constants and fields.

---

## 🚀 How to Run

### Prerequisites
* **JDK 17** or higher installed.

### Execution
1. Clone the repository:
   ```bash
   git clone [https://github.com/YOUR_USERNAME/Java-Calculator.git](https://github.com/YOUR_USERNAME/Java-Calculator.git)