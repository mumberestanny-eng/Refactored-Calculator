package shop.shoppingtracker;

import shop.shoppingtracker.trackerstyling.ExportCSV.ExportToCSV;
import shop.shoppingtracker.trackerstyling.RoundedBorder;
import shop.shoppingtracker.trackerstyling.RoundedButton;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class ExpenseTrackerPro extends JFrame {

    private final JTextField itemField = new JTextField();
    private final JTextField priceField =  new JTextField();

    private final JPanel bottomContainer = new JPanel();
    private final JPanel totalPanel = new JPanel();

    private final Color borderColor = new Color(19, 47, 85);
    private final Color defaultBackgroundColor = new Color(230, 233, 237, 77);
    private final Color textColor = new Color(30, 41, 59);

    private final ExportToCSV exporter = new ExportToCSV();
    private final ExpenseManager expenseManager = new ExpenseManager();

    private final Font textFont = new Font("Lexend", Font.PLAIN, 19);
    private final Font titleFont = new Font("Lexend", Font.BOLD, 14);

    public ExpenseTrackerPro() {
        setTitle("Expense Tracker with Export");
        setLayout(new BorderLayout());
        int MAX_HEIGHT = 650;
        int MAX_WIDTH = 400;
        setSize(MAX_WIDTH, MAX_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(createTopContainer(), BorderLayout.NORTH);
        add(createBottomContainer(), BorderLayout.CENTER);
        add(styleTotalContainer(), BorderLayout.SOUTH);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel createTopContainer(){

        JPanel topPanel = new JPanel();
        topPanel.setBackground(defaultBackgroundColor);
        topPanel.setLayout(new GridLayout(4, 1, 5, 5));

        TitledBorder topBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(19, 47, 85), 2),
                "Input Console");

        topBorder.setTitleFont(titleFont);
        topBorder.setTitleColor(new Color(17, 38, 66));
        topPanel.setBorder(topBorder);

        topPanel.add(styleItemField());
        topPanel.add(stylePriceField());
        topPanel.add(createAddButton());
        topPanel.add(createSaveButton());
        return topPanel;
    }
    private RoundedButton createAddButton(){
        RoundedButton addButton = new RoundedButton("Add Item to cart" ,15);
        addButton.setFont(textFont);
        Color butBackgroundColor = new Color(11, 48, 131);
        addButton.setBackground(butBackgroundColor);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        addButton.addActionListener((ActionEvent) -> {
            inputHandler();
            printOnPane();
        });
        return addButton;
    }
    private JScrollPane createBottomContainer(){

        TitledBorder bottomBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(19, 47, 85), 2),
                "Expenses");

        bottomBorder.setTitleFont(titleFont);
        bottomBorder.setTitleColor(new Color(17, 38, 66));
        bottomContainer.setBorder(bottomBorder);

        bottomContainer.setLayout(new BoxLayout(bottomContainer, BoxLayout.Y_AXIS));
        return new JScrollPane(bottomContainer);
    }
    private RoundedButton createSaveButton(){

        RoundedButton saveButton = new RoundedButton("Export to CSV", 15);
        saveButton.setFont(textFont);
        Color butBackgroundColor = new Color(27, 141, 158);
        saveButton.setBackground(butBackgroundColor);
        saveButton.addActionListener(e -> {
            if (expenseManager.getExpenses().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an item first!", "Export Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("Expenses.csv"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try {
                    exporter.saveToCSV(expenseManager.getExpenses(), fileToSave);
                    JOptionPane.showMessageDialog(this, "Successfully saved to " + fileToSave.getName() + "!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return saveButton;
    }
    private JTextField styleItemField(){

        itemField.setFont(textFont);
        Color bgColor = new Color(255, 255, 255);
        itemField.setBackground(bgColor);
        itemField.setForeground(textColor);

        TitledBorder itemBorder = BorderFactory.createTitledBorder(
                new RoundedBorder(borderColor, 2, 15), "Item Name");
        itemBorder.setTitleFont(titleFont);
        itemBorder.setTitleColor(borderColor);

        itemField.setBorder(itemBorder);

        return itemField;
    }
    private JTextField stylePriceField(){

        priceField.setFont(textFont);
        Color bgColor = new Color(255, 255, 255);
        priceField.setBackground(bgColor);
        priceField.setForeground(textColor);

        TitledBorder priceBorder = BorderFactory.createTitledBorder(
                new RoundedBorder(borderColor, 2, 15), "Item Price");
        priceBorder.setTitleFont(titleFont);
        priceBorder.setTitleColor(borderColor);

        priceField.setBorder(priceBorder);
        return priceField;
    }
    private JPanel styleTotalContainer(){
        JPanel totalContainer = new JPanel();

        totalContainer.setBackground(new Color(169, 237, 184));
        totalContainer.setLayout(new BorderLayout(4,4));

        TitledBorder totalBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(19, 47, 85), 2),
                "Total Price");

        totalBorder.setTitleFont(titleFont);
        totalBorder.setTitleColor(new Color(17, 38, 66));
        totalContainer.setBorder(totalBorder);

        totalPanel.setBackground(Color.BLACK);
        totalPanel.setForeground(textColor);
        totalPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        totalContainer.add(createTotalButton(), BorderLayout.WEST);
        totalContainer.add(totalPanel, BorderLayout.CENTER);
        return totalContainer;
    }
    private RoundedButton createTotalButton(){
        RoundedButton totalButton = new RoundedButton("Total", 15);
        totalButton.setFont(textFont);
        totalButton.setBackground(new Color(51, 86, 174));
        totalButton.setForeground(Color.white);
        totalButton.setFocusPainted(false);

        totalButton.addActionListener((e) -> {
            printTotal();
        });
        return totalButton;
    }
    private void inputHandler(){
        String itemName = itemField.getText().trim();
        String priceStr = priceField.getText().trim();

        if (!itemName.isEmpty() && !priceStr.isEmpty()) {
            try {
                double amount = Double.parseDouble(priceStr);
                expenseManager.addExpense(new Expense(itemName, amount));
                itemField.setText("");
                priceField.setText("");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric price.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void printOnPane(){

        bottomContainer.removeAll();
        int index = 1;
        for (Expense exp : expenseManager.getExpenses()) {
            JLabel label = new JLabel(index + ". " + exp);
            label.setFont(new Font("Times New Roman", Font.PLAIN, 17));
            label.setForeground(textColor);
            bottomContainer.add(label);
            index++;
        }
        bottomContainer.revalidate();
        bottomContainer.repaint();
    }
    private void printTotal(){
        totalPanel.removeAll();

        if (!expenseManager.getExpenses().isEmpty()){
            JLabel label = new JLabel();
            Font labeFont = new Font("Consolas", Font.PLAIN, 17);
            label.setFont(labeFont);
            label.setForeground(Color.ORANGE);
            label.setText(" Total Computed → $"+ String.format("%.3f", expenseManager.getTotal()));
            totalPanel.add(label);
        }
        totalPanel.revalidate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseTrackerPro::new);
    }
} // End of the class