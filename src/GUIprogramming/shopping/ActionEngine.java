package GUIprogramming.shopping;

import java.util.*;
import java.util.List;

public class ActionEngine {

     List<Action> history = new ArrayList<>();
     List<Action> undoStack = new ArrayList<>();

     public void createAction(String description){

         if (!description.isEmpty()){
             Action a = new Action(description.trim());
             history.add(a);
         }
     }

     public List<String> getHistory(){
         List<String> actions = new ArrayList<>();
         String action = "";
         int index = 0;

         for (int i = history.size() - 1; i >= 0; i--) {
             Action a = history.get(i);
             action = (index + 1)+". " +a.getDescription();
             actions.add(action);
             index++;
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

     public void undo(){
         if (!history.isEmpty()){
             Action u = history.removeLast();
             undoStack.add(u);
         }
     }
     public void redo(){
         if (!undoStack.isEmpty()){
             Action r = undoStack.removeLast();
             history.add(r);
         }
     }

}
