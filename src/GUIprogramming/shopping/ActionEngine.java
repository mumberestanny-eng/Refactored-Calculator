package GUIprogramming.shopping;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class ActionEngine {

     List<Action> history = new ArrayList<>();
     List<Action> undoStack = new ArrayList<>();

     boolean isActive = false;

     public void createAction(String description){

         if (!description.isEmpty()){

             Action a = new Action(description.trim());
             history.add(a);
         }
     }

     public List<String> getHistory(){
         List<String> actions = new ArrayList<>();
         String action = "";

         for (int i = history.size() - 1; i >= 0; i--) {
             Action a = history.get(i);
             action = (history.indexOf(a) + 1)+". "+a.getDescription();
             actions.add(action);
         }
         return actions;
     }

     public List<String> getUndoStack(){
         List<String> undoActions = new ArrayList<>();
         String action = "";

         for (int i = undoStack.size() - 1; i >= 0; i--) {
             Action a = undoStack.get(i);
             action = "Dropped: "+a.getDescription();
             undoActions.add(action);
         }
         return undoActions;
     }

     public void transferAction(boolean LR, boolean RL){
        if(LR && !RL){
            Action lastAction = history.removeLast();
            undoStack.add(lastAction);
        }
        if (!LR && RL){
            Action firstAction = undoStack.removeLast();
            history.add(firstAction);
        }
     }




     /*performButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = inputArea.getText().trim();
            if (!text.isEmpty()){

                Action action = new Action(text);
                history.add(action);
                undoStack.clear();
                inputArea.setText("");
            }
            display();
        }
    });*/

     /*undoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            isUndo = true;
            newUpdate();
        }
    });
     redoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            isRedo = true;
            newUpdate();

        }
    });


    public void newUpdate() {
        if (isUndo) {
            if (!history.isEmpty()) {
                // Get the very last item from history
                Action lastAction = history.remove(history.size() - 1);
                // Push it to the undo stack
                undoStack.add(lastAction);
            }
            isUndo = false; // Reset flag
        }

        if (isRedo) {
            if (!undoStack.isEmpty()) {
                // Take the last item dropped into the undo stack
                Action resurrectedAction = undoStack.remove(undoStack.size() - 1);
                // Put it back into active history
                history.add(resurrectedAction);
            }
            isRedo = false; // Reset flag
        }

        // Refresh the screen completely with the new list states!
        display();
    }


    public void display() {
        // 1. Wipe both panels clean
        undoPanel.removeAll();
        redoPanel.removeAll();
        // 2. Render the Active History (Left Side)
        // We loop backwards (from newest to oldest) just like your code intended!
        for (int i = history.size() - 1; i >= 0; i--) {
            Action act = history.get(i);
            // CRITICAL: Create a BRAND NEW JLabel for every single action!
            JLabel actionLabel = new JLabel((i + 1) + ". " + act.getDescription());
            actionLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            actionLabel.setForeground(Color.WHITE); // High visibility on black background
            undoPanel.add(actionLabel);
        }
        // 3. Render the Undo Stack (Right Side)
        for (int j = undoStack.size() - 1; j >= 0; j--) {
            Action act = undoStack.get(j);
            JLabel undoLabel = new JLabel("Dropped: " + act.getDescription());
            undoLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            undoLabel.setForeground(Color.GRAY);
            redoPanel.add(undoLabel);
        }
        // 4. Force Swing to repaint the structural boundaries
        undoPanel.revalidate();
        undoPanel.repaint();
        redoPanel.revalidate();
        redoPanel.repaint();*/
}
