package GUIprogramming.shopping;

public class Action {
    private final String description;

    public Action(String description) {
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
    @Override
    public String toString() {
        return getDescription();
    }
}
