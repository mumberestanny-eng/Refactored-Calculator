# Desktop Shopping Action Tracker (Java Swing & AWT)

A modular desktop application built with **Java Swing**,
designed to track user operations and text transformations. 
The project has been refactored to enforce **SOLID principles**,
isolating UI components from underlying event evaluation and business logic.

---
## 🏗️ Architecture & Project Structure

The project follows the **Separation of Concerns (SoC)** principle:

```text
src/
└── GUIprogramming/
        |—— shopping/
            ├── Action.java               # Domain model representing individual action items
            ├── ActionEngine.java         # Core state controller & history manager
            ├── SimpleActionTracker.java  # Main Swing application entry point & layout assembly
            ├── LabelFactory.java         # Factory for customized, styled Swing labels
            ├── RoundedBorder.java        # Custom border UI component for action items
            └── RoundedButton.java        # Custom button UI component with modern styling
                
🛠️ Key Architectural & Refactoring Highlights

1. Single Responsibility Principle (SRP)

        **Presentation Layer (SimpleActionTracker): Responsible exclusively for building Swing frames, 
          managing layout managers, and capturing user interface events.
        
        **Business Logic (ActionEngine): Encapsulates core state management and operational evaluation, 
          completely decoupled from Swing components.

        **Domain & Utilities (Action, LabelFactory:):  Handles element label formatting and text presentation without coupling layout positioning logic..

        **Encapsulated Styling: Layout styling, custom borders, and rounded button geometries are modularized inside trackerstyling.
        
2. Custom Swing Component Styling

        **RoundedButton & RoundedBorder: Custom graphical extensions built over Java AWT/Swing to deliver clean, 
          modernized UI styling without relying on heavy external Look-and-Feel libraries.

🚀 How to Run the Application
Prerequisites
        **Java Development Kit (JDK): JDK 21 or higher is strictly required.

        ⚠️ Note: The underlying core engine utilizes List.removeLast(), 
                a Sequenced Collections API feature introduced in Java 21.
                It will fail to compile on JDK 17 or lower.

        **IDE: IntelliJ IDEA, Eclipse, or NetBeans

Running inside IntelliJ IDEA
        1. Ensure your Project SDK is set to JDK 21+ (File > Project Structure > SDK).
        
        2. Open src/GUIprogramming/shopping/SimpleActionTracker.java.
        
        3. Right-click SimpleActionTracker.java and click Run 'SimpleActionTracker.main()'.
        
Running via Terminal

        # Verify JDK version is 21 or higher
        java -version
        
        # Compile and launch
        javac -d bin src/GUIprogramming/shopping/SimpleActionTracker/*.java 
        java -cp bin GUIprogramming.shopping.SimpleActionTracker

---