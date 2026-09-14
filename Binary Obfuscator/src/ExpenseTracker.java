import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseTracker extends JFrame {
    private JTextField itemField;
    private JTextField priceField;
    private JPanel listPanel;

    private int itemCount = 0;

    public ExpenseTracker() {
        // 1. Setup the Frame
        setTitle("Simple Expense Tracker");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // 2. Top Panel (Input Area)
        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        itemField = new JTextField();
        itemField.setBorder(BorderFactory.createTitledBorder("Item Name"));

        priceField = new JTextField();
        priceField.setBorder(BorderFactory.createTitledBorder("Price"));

        JButton addButton = new JButton("Add Expense");

        inputPanel.add(itemField);
        inputPanel.add(priceField);
        inputPanel.add(addButton);

        // 3. Bottom Panel (Display Area)
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // Wrap the list in a ScrollPane so it can handle many items
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Expenses"));

        // 4. Button Logic
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });

        // Add main panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addExpense() {
        String name = itemField.getText();
        String price = priceField.getText();

        if (!name.isEmpty() && !price.isEmpty()) {
            itemCount++;
            // Create a small panel or label for the new entry
            JLabel entry = new JLabel(itemCount +" : "+name + " : $" + price);
            entry.setFont(new Font("Arial", Font.PLAIN, 12));
            entry.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            listPanel.add(entry);

            // Refresh the UI to show the new item
            listPanel.revalidate();
            listPanel.repaint();

            // Clear inputs
            itemField.setText("");
            priceField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExpenseTracker());
    }
}

