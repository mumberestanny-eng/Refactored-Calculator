# Desktop Shopping Action Tracker (Java Swing & AWT)

A modular desktop application built with **Java Swing**, designed to track user operations and text transformations. The project has been refactored to enforce **SOLID principles**, isolating UI components from underlying event evaluation and business logic.

---

## 🏗️ Architecture & Package Structure

The source code is organized within the `GUIprogramming.shopping` package:

```text
src/
└── GUIprogramming/
    └── shopping/
        ├── Action.java              # Data model representing single user operations
        ├── ActionEngine.java        # Core business logic processing actions & transformations
        ├── RoundedBorder.java       # Custom Swing UI component decorator
        ├── RoundedButton.java       # Custom Swing UI button styling
        ├── SimpleActionTracker.java # Main Swing JFrame UI presentation layer
        └── TextConverter.java       # String formatting and text utility module

🛠️ Key Architectural & Refactoring Highlights

1. Single Responsibility Principle (SRP)

Presentation Layer (SimpleActionTracker): Responsible exclusively for building Swing frames, 
managing layout managers, and capturing user interface events.

Business Logic (ActionEngine): Encapsulates core state management and operational evaluation, 
completely decoupled from Swing components.

Domain & Utilities (Action, TextConverter): Handles object modeling and string manipulation independently.

2. Custom Swing Component Styling

RoundedButton & RoundedBorder: Custom graphical extensions built over Java AWT/Swing to deliver clean, 
modernized UI styling without relying on heavy external Look-and-Feel libraries.