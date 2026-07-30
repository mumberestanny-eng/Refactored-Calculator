package com.garage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.*;

/**
 * Desktop Presentation Layer using Java Swing/AWT.
 * Single Responsibility: Building and managing UI components.
 */
public class Calculator extends JFrame {

    private final JLabel displayLabel = new JLabel();
    private final CalculatorEngine engine = new CalculatorEngine();

    private final Color colorOrange = new Color(255, 109, 0);
    private final Color rightSideSymbolColor = new Color(97, 87, 79);
    private final Color compactLightBlack = new Color(135, 106, 106, 143);
    private final Color calculatorForeground = new Color(240, 243, 244, 179);
    private final Color defaultButtonColor = new Color(185, 216, 138);

    private final Font buttonFont = new Font("Lexend", Font.PLAIN, 28);
    private final Font labelFont = new Font("Times New Roman", Font.PLAIN, 80);

    private static final int MAX_CALCULATOR_HEIGHT = 540;
    private static final int MAX_CALCULATOR_WIDTH = 360;

    private final String[] symbols = {
            "AC", "%", "+/-", "/",
            "7", "8", "9", "x",
            "4", "5", "6", "+",
            "1", "2", "3", "-",
            "0", ".", " √", "="
    };

    private final String[] topSymbols = {"AC", "%", "+/-"};
    private final String[] rightSymbols = {"/", "x", "+", "-", "="};

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(1, 3));

        add(createDisplayPanel(), BorderLayout.NORTH);
        add(createSignPanel(symbols), BorderLayout.CENTER);

        setSize(MAX_CALCULATOR_WIDTH, MAX_CALCULATOR_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createDisplayPanel() {
        JPanel displayPanel = new JPanel();
        Color compactBlack = new Color(29, 43, 43);
        displayPanel.setBackground(compactBlack);
        displayPanel.setLayout(new BorderLayout());

        displayLabel.setText("0");
        displayLabel.setFont(labelFont);
        displayLabel.setBackground(compactBlack);
        displayLabel.setForeground(calculatorForeground);
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setOpaque(true);

        displayPanel.add(displayLabel);
        return displayPanel;
    }

    private JPanel createSignPanel(String[] calculatorSymbols) {
        JPanel signPanel = new JPanel();
        signPanel.setLayout(new GridLayout(5, 4, 3, 3));
        signPanel.setBackground(compactLightBlack);

        for (String symbol : calculatorSymbols) {
            // Instead of using the using the JButton, i prefer the class RoundedButton which extends the JButton
            // This one helps me to desing rounded button.
            RoundedButton button = new RoundedButton(symbol, 15);
            button.setFocusable(false);
            button.setFont(buttonFont);
            button.setBackground(defaultButtonColor);

            if (Arrays.asList(topSymbols).contains(symbol)) {
                button.setBackground(colorOrange);
                button.setForeground(Color.BLACK);
            } else if (Arrays.asList(rightSymbols).contains(symbol)) {
                button.setBackground(rightSideSymbolColor);
                button.setForeground(Color.BLACK);
            }

            // Lambda Action Listener delegating directly to engine
            button.addActionListener((ActionEvent e) -> {
                String updatedDisplay = engine.processInput(displayLabel.getText(), button.getText());
                displayLabel.setText(updatedDisplay);
            });

            signPanel.add(button);
        }
        return signPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}