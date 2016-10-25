package net.nortlam.visual.action;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.nortlam.crudmaker.design.Entity;
import net.nortlam.crudmaker.impl.ProjectJavaEE;
import net.nortlam.visual.EntityScene;
import org.openide.DialogDescriptor;

/**
 * @author Mauricio Leal */
public class PanelEntity extends JPanel {

    private JLabel labelPackageName;
    private JLabel labelName;
    private JTextField textPackageName;
    private JTextField textName;
    private JCheckBox checkIsSerializable;
    private ValidationListener validationListener = new ValidationListener();
    private JCheckBox checkIsXMLEnabled;

    // JavaEE
    private JLabel labelTableName;
    private JTextField textTableName;

    private DialogDescriptor dialogDescriptor;

//    private GeneratorFactory factory;
    private EntityScene scene;
    private boolean hasJavaEE;

    private Entity entity;

    public PanelEntity(EntityScene scene) {
        this.scene = scene;
        
        initComponents();
    }

    public void setDialogDescritor(DialogDescriptor dialogDescriptor) {
        this.dialogDescriptor = dialogDescriptor;
        dialogDescriptor.setValid(false);
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
        
        textPackageName.setText(entity.getPackageName());
        textName.setText(entity.getName());
        checkIsSerializable.setSelected(entity.isSerializable());
        checkIsXMLEnabled.setSelected(entity.isXMLEnabled());

        // Focus
        if(entity.getPackageName().isEmpty()) textPackageName.requestFocus();
        else textName.requestFocus();

        // Java EE
        if(hasJavaEE) {
            ProjectJavaEE projectJavaEE = (ProjectJavaEE)scene.getProject(ProjectJavaEE.class);
            textTableName.setText(projectJavaEE.getTableName(entity));
        }
    }
    
    public void updateEntity() {
        entity.setPackageName(textPackageName.getText());
        entity.setName(textName.getText());
        entity.setSerializable(checkIsSerializable.isSelected());
        entity.setXMLEnabled(checkIsXMLEnabled.isSelected());

        // Java EE
        if(hasJavaEE) {
            ProjectJavaEE projectJavaEE = (ProjectJavaEE)scene.getProject(ProjectJavaEE.class);
            projectJavaEE.add(entity, textTableName.getText());
        }
    }

    /**
     * Create an Main Panel */
    private void initComponents() {
        hasJavaEE = scene.getProject(ProjectJavaEE.class) != null;

        labelPackageName = new JLabel("Package");
        labelName = new JLabel("Name");
        textPackageName = new JTextField(20);
        textPackageName.getDocument().addDocumentListener(validationListener);
        textName = new JTextField(20);
        textName.getDocument().addDocumentListener(new TextNameListener());
        textName.getDocument().addDocumentListener(validationListener);
        checkIsSerializable = new JCheckBox("Serializable");
        checkIsSerializable.setSelected(true);
        checkIsXMLEnabled = new JCheckBox("XML");
        checkIsXMLEnabled.setSelected(true);

        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints gc = new GridBagConstraints();

        // PACKAGE NAME
        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5; gc.insets.bottom = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelPackageName, gc);
        add(labelPackageName);

        gc.gridx++; gc.gridwidth = 3;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 0;
        gc.weightx = 1.0;
        layout.setConstraints(textPackageName, gc);
        add(textPackageName);

        // NAME
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelName, gc);
        add(labelName);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 5;
        gc.weightx = 1.0;
        layout.setConstraints(textName, gc);
        add(textName);

        // Is Serializable ?
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5;
        gc.weightx = 0.0;
        layout.setConstraints(checkIsSerializable, gc);
        add(checkIsSerializable);
        
        // Is XML ?
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 0;
        gc.weightx = 0.0;
        layout.setConstraints(checkIsXMLEnabled, gc);
        add(checkIsXMLEnabled);

        if(hasJavaEE) {
            // Java EE
            gc.gridx = 0; gc.gridy++; gc.gridwidth = 4; gc.gridheight = 1;
            gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.BOTH;
            gc.insets.right = 0;
            gc.weightx = 1.0; gc.weighty = 1.0;
                JPanel panelEE = createPanelEE();
            layout.setConstraints(panelEE, gc);
            add(panelEE);
        }
    }

    private JPanel createPanelEE() {
        labelTableName = new JLabel("Table");
        textTableName = new JTextField();
        textTableName.getDocument().addDocumentListener(validationListener);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Java EE"));

        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);

        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelTableName, gc);
        mainPanel.add(labelTableName);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;

        gc.insets.right = 0;
        gc.weightx = 1.0;
        layout.setConstraints(textTableName, gc);
        mainPanel.add(textTableName);

        return mainPanel;
    }

    private class ValidationListener implements DocumentListener {

        private void validate() {
            if(dialogDescriptor != null)
                dialogDescriptor.setValid(!textPackageName.getText().isEmpty() &&
                        !textName.getText().isEmpty() &&
                        (textTableName != null ? !textTableName.getText().isEmpty() : true));
        }

        @Override
        public void changedUpdate(DocumentEvent e) { validate(); }
        @Override
        public void insertUpdate(DocumentEvent e) { validate();  }
        @Override
        public void removeUpdate(DocumentEvent e) { validate(); }
    }

    private class TextNameListener implements DocumentListener {

        private void checkName() {
            char[] value = textName.getText().toCharArray();
            StringBuffer result = new StringBuffer();
            for(int i=0; i < value.length; i++) {
                if(i == 0) {
                    result.append(value[i]);
                    continue;
                } // Nothing to do on first element

                if(Character.isLetter(value[i]) && Character.isUpperCase(value[i]))
                    result.append("_").append(value[i]);
                else result.append(value[i]);
            }

            if(textTableName != null)
                textTableName.setText(result.toString().toUpperCase());
        }

        public void changedUpdate(DocumentEvent e) {checkName();}
        public void insertUpdate(DocumentEvent e) {checkName();}
        public void removeUpdate(DocumentEvent e) {checkName();}
    }
}
