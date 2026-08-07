package GUIprogramming.shopping;

import java.util.*;
import java.util.List;

public class ActionEngine {

     private final List<Action> history = new ArrayList<>();
     private final List<Action> undoStack = new ArrayList<>();

     public void createAction(String description){
         if (!description.isEmpty()){
             Action a = new Action(description.trim());
             history.add(a);
             undoStack.clear();
         }
     }

     public List<String> getHistory(){
         List<String> actions = new ArrayList<>();
         String action = "";
         for (Action activeAction : history) {
             action = activeAction.getDescription();
             actions.add(action);
         }
         return actions;
     }

     public List<String> getUndoStack(){
         List<String> undoActions = new ArrayList<>();
         String action = "";
         for (Action undoAction : undoStack) {
             action =undoAction.getDescription();
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
