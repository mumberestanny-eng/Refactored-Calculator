package GUIprogramming.shopping;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SimpleActionTracker extends JFrame {

        private JTextArea inputArea;
        private JPanel undoPanel;
        private JPanel redoPanel;

        private final Color DEFAULT_BG_COLOR = new Color(82, 92, 61, 138);
        private final Color TEXTFIELD_COLOR = new Color(30, 31, 34);
        public final Color BORDER_COLOR = new Color(70, 73, 75);

        private final ActionEngine actionController = new  ActionEngine();
        private final LabelFactory labelFactory = new LabelFactory();

        private final Font FONT_BUTTON = new Font("Lexend", Font.PLAIN, 15);
        private final Font FONT_LABEL = new Font("Arial", Font.PLAIN, 15);

        private final int MAX_GUI_HEIGHT = 500;
        private final int MAX_GUI_WIDTH = 700;

        public SimpleActionTracker() {

            setSize(MAX_GUI_WIDTH, MAX_GUI_HEIGHT);
            setTitle("Simple Action Tracker v1");
            setLayout(new BorderLayout(5, 5));
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().setBackground(DEFAULT_BG_COLOR);

            add(createTopPanel(), BorderLayout.NORTH);
            add(createMidPanel(), BorderLayout.CENTER);
            add(createBottomPanel(), BorderLayout.SOUTH);
            setVisible(true);
            setResizable(false);

        }

        public static void main(String[] args) {
             SwingUtilities.invokeLater(SimpleActionTracker::new);
        }

        private JPanel createTopPanel(){
            JPanel topPanel = new JPanel();

            topPanel.setLayout(new BorderLayout(2, 2));
            topPanel.setBackground(DEFAULT_BG_COLOR);

            topPanel.setBorder(BorderFactory.createTitledBorder(
                    new RoundedBorder(BORDER_COLOR, 2,15), "Action Command"
            ));

            topPanel.add(createInputArea(), BorderLayout.WEST);
            topPanel.add(createPerformButton(),  BorderLayout.EAST);

            return topPanel;
        }

        private RoundedButton createPerformButton(){

            RoundedButton performButton = new RoundedButton("Perform Action", 15);
            performButton.setBackground(TEXTFIELD_COLOR);
            performButton.setForeground(Color.WHITE);
            performButton.setFont(FONT_BUTTON);

            performButton.addActionListener((ActionEvent e) -> {
                textHandler();
                printOnPane();
            });
            return performButton;
        }

        private JTextArea createInputArea(){
            inputArea = new JTextArea(1, 38);
            inputArea.setBackground(new Color(30, 14, 14));

            Font TextFieldFont = new Font("Times New Roman", Font.PLAIN, 18);
            inputArea.setFont(TextFieldFont);
            inputArea.setForeground(new Color(252, 252, 249, 255));

            return inputArea;
        }

        private JPanel createMidPanel(){

            JPanel midPanel = new JPanel();
            midPanel.setLayout(new GridLayout(1, 2));

            midPanel.setBackground(DEFAULT_BG_COLOR);
            midPanel.setBorder(BorderFactory.createTitledBorder(
                    new RoundedBorder(DEFAULT_BG_COLOR, 2,15), "Inline Action Command"));

            midPanel.add(createUndoScrollPane());
            midPanel.add(createRedoScrollPane());

            return midPanel;
        }

        private JScrollPane createRedoScrollPane(){

            redoPanel = new JPanel();
            redoPanel.setLayout(new BoxLayout(redoPanel, BoxLayout.Y_AXIS));
            redoPanel.setBackground(new Color(60,63,65));

            JScrollPane redoScroll = new JScrollPane(redoPanel);

            redoScroll.setBorder(BorderFactory.createTitledBorder(
                    new RoundedBorder(BORDER_COLOR, 2, 15), "Undo Stack (undoStack)"));
            return redoScroll;
        }

        private JScrollPane createUndoScrollPane(){

            undoPanel = new JPanel();
            undoPanel.setLayout(new BoxLayout(undoPanel, BoxLayout.Y_AXIS));
            undoPanel.setBackground(Color.gray);

            JScrollPane undoScroll = new JScrollPane(undoPanel);

            undoScroll.setBorder(BorderFactory.createTitledBorder(
                    new RoundedBorder(BORDER_COLOR, 2, 15), "Active History (history)"));
            return undoScroll;
        }

        private JPanel createBottomPanel(){

            JPanel bottomPanel = new JPanel();

            JLabel bottomLabel = new JLabel("Current Status: Awaiting action..", JLabel.CENTER);
            bottomLabel.setFont(FONT_LABEL);

            bottomPanel.setBackground(DEFAULT_BG_COLOR);
            bottomPanel.setLayout(new BorderLayout(5,5));

            bottomPanel.setBorder(BorderFactory.createTitledBorder(
                    new RoundedBorder(BORDER_COLOR, 2,15), "Control Panel"));

            bottomPanel.add(createBottomButtonContainer(), BorderLayout.NORTH);
            bottomPanel.add(bottomLabel, BorderLayout.SOUTH);

            return bottomPanel;
        }

        private JPanel createBottomButtonContainer(){

            JPanel BottomButtonContainer = new JPanel();

            BottomButtonContainer.setBackground(DEFAULT_BG_COLOR);
            BottomButtonContainer.setLayout(new GridLayout(1,2,5,5));

            BottomButtonContainer.add(designUndoButton());
            BottomButtonContainer.add(designRedoButton());

            return BottomButtonContainer;
        }

        private RoundedButton designUndoButton(){

            RoundedButton undoButton = new RoundedButton("Undo", 20);
            undoButton.setBackground(TEXTFIELD_COLOR);
            undoButton.setForeground(Color.WHITE);
            undoButton.setFont(FONT_BUTTON);

            undoButton.addActionListener((ActionEvent e) -> {
                actionController.undo();
                printOnPane();
            });

            return undoButton;

        }

        private RoundedButton designRedoButton(){

            RoundedButton  redoButton = new RoundedButton("Redo", 20);
            redoButton.setBackground(TEXTFIELD_COLOR);
            redoButton.setForeground(Color.WHITE);
            redoButton.setFont(new Font("Arial", Font.PLAIN, 15));
            redoButton.addActionListener((ActionEvent e) -> {
                actionController.redo();
                printOnPane();
            });

            return redoButton;
        }

        public void textHandler(){
            String currentText = inputArea.getText().trim();
            if (!currentText.isEmpty()){
                actionController.createAction(currentText);
            }
            inputArea.setText("");
        }

        public void printOnPane(){
            undoPanel.removeAll();
            redoPanel.removeAll();

            for (JLabel lb : labelFactory.createActiveLabelCollection(actionController.getHistory())){
                undoPanel.add(lb);
            }

            for (JLabel lb : labelFactory.createDroppedLabelCollection(actionController.getUndoStack())){
                redoPanel.add(lb);
            }
            undoPanel.revalidate();
            undoPanel.repaint();
            redoPanel.revalidate();
            redoPanel.repaint();
        }

}
