package GUIprogramming.shopping;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class TextConverter {

    private final Font FONT_LABEL = new Font("Lexend", Font.PLAIN, 18);
    private final Color DefaultLabelForeground = new Color(0, 7, 6);


    public JLabel createNewLabel(String text) {
        JLabel outputLabel = new JLabel();
        outputLabel.setText(text);
        outputLabel.setForeground(DefaultLabelForeground);
        outputLabel.setFont(FONT_LABEL);
        return outputLabel;
    }

    public List<JLabel> createActiveLabelCollection(List<String> source){

        List<JLabel> labelCollection = new  ArrayList<>();
        for(String s : source){
            labelCollection.add(createNewLabel(s));
        }
        return labelCollection;
    }

    public List<JLabel> createUndoLabelCollection(List<String> source){
        List<JLabel> labelCollection = new  ArrayList<>();
        for(String s : source){
            labelCollection.add(createNewLabel(s));
        }
        return labelCollection;
    }

}
