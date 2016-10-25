package net.nortlam.visual.action;

/*
 * A set of methos to easy construct a panel */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * @author Mauricio Leal */
public class FormUtil {

    public static JPanel createForm(JLabel label, JComponent component) {
        return createForm(new JLabel[] {label}, new JComponent[] {component});
    }
    
    public static JPanel createForm(JLabel[] labels, JComponent[] components) {
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        
        GridBagConstraints gc = new GridBagConstraints();
        
        for(int i=0; i < labels.length; i++) {
            // JLABEL JLABEL JLABEL JLABEL JLABEL JLABEL JLABEL JLABEL JLABEL JLABEL 
            gc.gridx = 0; gc.gridy = i; gc.gridwidth = 1; gc.gridheight = 1;
            gc.anchor = GridBagConstraints.NORTHWEST; gc.fill = GridBagConstraints.NONE;
            gc.insets.top = 0; gc.insets.right = 5; gc.insets.bottom = 5; gc.insets.left = 5;
            gc.weightx = 0.0; gc.weighty = 0.0;
            
            if(i == 0) { // FIRST ELEMENT (JLABEL)
                gc.insets.top = 5; gc.insets.right = 5; gc.insets.bottom = 5; gc.insets.left = 5;
                gc.weightx = 0.0; gc.weighty = 0.0;
            }
            if(i == labels.length-1) { // LAST ELEMENT (JLABEL)
                gc.insets.top = 0; gc.insets.right = 5; gc.insets.bottom = 0; gc.insets.left = 5;
                gc.weightx = 0.0; gc.weighty = 1.0;
            }
            layout.setConstraints(labels[i], gc);
            panel.add(labels[i]);
            
            // JTEXTFIELD JTEXTFIELD JTEXTFIELD JTEXTFIELD JTEXTFIELD JTEXTFIELD 
            gc.gridx++; gc.gridy = i; gc.gridwidth = 1; gc.gridheight = 1;
            gc.anchor = GridBagConstraints.NORTH; gc.fill = GridBagConstraints.HORIZONTAL;
            gc.insets.top = 0; gc.insets.right = 5; gc.insets.bottom = 5; gc.insets.left = 0;
            
            if(i == 0) { // FIRST ELEMENT
                gc.insets.top = 5; gc.insets.right = 5; gc.insets.bottom = 5; gc.insets.left = 0;
                gc.weightx = 1.0; gc.weightx = 0.0;
            }
            if(i == labels.length-1) {
                gc.insets.top = 0; gc.insets.right = 5; gc.insets.bottom = 0; gc.insets.left = 0;
                gc.weightx = 1.0; gc.weighty = 1.0;
            }
            layout.setConstraints(components[i], gc);
            panel.add(components[i]);
        }
        
        return panel;
    }
    
}
