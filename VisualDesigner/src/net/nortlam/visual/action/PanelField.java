package net.nortlam.visual.action;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.nortlam.crudmaker.design.EntityField;
import net.nortlam.crudmaker.impl.ProjectJavaEE;
import net.nortlam.crudmaker.impl.javaee.TableColumn;
import net.nortlam.crudmaker.impl.javaee.column.Column;
import net.nortlam.visual.EntityScene;
import org.openide.DialogDescriptor;

/**
 * @author Mauricio Leal */
public class PanelField extends JPanel {
    
    public static final String DEFAULT_STRING_LENGTH = "50";

    private JLabel labelName, labelType, labelInitialValue, labelLength;
    private JTextField textName, textType, textInitialValue, textLength;
    private JCheckBox checkIsIdentifier, checkIsTransient;

    private ValidationListener validationListener = new ValidationListener();

//    public Column(String name, int length, boolean isNullable,
//            String columnDefinition, boolean isUnique, boolean isInsertable,
//                    boolean isUpdatable) {
    // Java EE
    private JLabel labelColumnName, labelColumnDefinition;
    private JTextField textColumnName, textColumnDefinition;
    private JCheckBox checkIsNullable, checkIsUnique, checkIsInsertable, checkIsUpdatable;

    private EntityScene scene;
    private EntityField field;
    private boolean hasJavaEE;

    private DialogDescriptor dialogDescriptor;

    public PanelField(EntityScene scene) {
        this.scene = scene;

        initComponents();
    }

    public void setField(EntityField field) {
        this.field = field;

        checkIsIdentifier.setSelected(field.isIdentifier());
        textName.setText(field.getName());
        textType.setText(field.getType());
        textInitialValue.setText(field.getInitialValue());
        textLength.setText(String.valueOf(field.getLength()));
        checkIsTransient.setSelected(field.isTransient());

        // Java EE
        if(hasJavaEE) {
            ProjectJavaEE projectJavaEE = (ProjectJavaEE)scene.getProject(ProjectJavaEE.class);
            TableColumn tableColumn = projectJavaEE.getColumn(field);
            if(tableColumn != null && tableColumn instanceof Column) {
                Column column = (Column)tableColumn;
                textColumnName.setText(column.getName());
                textColumnDefinition.setText(column.getColumnDefinition());
                checkIsNullable.setSelected(column.isNullable());
                checkIsUnique.setSelected(column.isUnique());
                checkIsInsertable.setSelected(column.isInsertable());
                checkIsUpdatable.setSelected(column.isUpdatable());

            } else { // There is no information about column
                textColumnName.setText("");
                textColumnDefinition.setText("");
                checkIsNullable.setSelected(true);
                checkIsUnique.setSelected(false);
                checkIsInsertable.setSelected(true);
                checkIsUpdatable.setSelected(true);
            }
        }
    }

    public void updateField() {
        if(field != null) {
            field.setIsIdentifier(checkIsIdentifier.isSelected());
            field.setName(textName.getText());
            field.setType(textType.getText());
            field.setInitialValue(textInitialValue.getText());

            try {
                field.setLength(Integer.parseInt(textLength.getText()));
            } catch(NumberFormatException e) {
                field.setLength(-1);
            }
            
            field.setIsTransient(checkIsTransient.isSelected());

            // Java EE
            if(hasJavaEE) {
                ProjectJavaEE projectJavaEE = (ProjectJavaEE)scene.getProject(ProjectJavaEE.class);
                Column column = new Column(field, textColumnName.getText(),
                        field.getLength(), checkIsNullable.isSelected(),
                        textColumnDefinition.getText(),
                        checkIsUnique.isSelected(),
                        checkIsInsertable.isSelected(),
                        checkIsUpdatable.isSelected());
                projectJavaEE.add(field, column);
            }
        }
    }

    public void setDialogDescriptor(DialogDescriptor dialogDescriptor) {
        this.dialogDescriptor = dialogDescriptor;
        if(dialogDescriptor != null) dialogDescriptor.setValid(false);
    }

    private void initComponents() {
        hasJavaEE  = scene.getProject(ProjectJavaEE.class) != null;

        labelName = new JLabel("Name");
        labelType = new JLabel("Type");
        labelInitialValue = new JLabel("Initial");
        labelLength = new JLabel("Length");
        textName = new JTextField(10);
        textName.getDocument().addDocumentListener(new TextNameListener());
        textName.getDocument().addDocumentListener(validationListener);
        textType = new JTextField(10);
        textType.getDocument().addDocumentListener(new TextTypeListener());
        textType.getDocument().addDocumentListener(validationListener);
        textInitialValue = new JTextField(10);
        textLength = new JTextField(10);
        TextLengthListener lengthListener = new TextLengthListener();
        textLength.addFocusListener(lengthListener);
        textLength.getDocument().addDocumentListener(lengthListener);
        checkIsIdentifier = new JCheckBox("Identifier");
        checkIsIdentifier.addChangeListener(new CheckIsIdentifierListener());
        checkIsTransient = new JCheckBox("Transient");

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5; gc.insets.bottom = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelType, gc);
        add(labelType);

        gc.gridx++;
        layout.setConstraints(labelName, gc);
        add(labelName);

        gc.gridx++;
        layout.setConstraints(labelInitialValue, gc);
        add(labelInitialValue);

        gc.gridx++;
        gc.insets.right = 0;
        layout.setConstraints(labelLength, gc);
        add(labelLength);

        gc.gridx = 0; gc.gridy++;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 5;
        gc.weightx = 1.0; gc.weighty = 0.0;
        layout.setConstraints(textType, gc);
        add(textType);

        gc.gridx++;
        layout.setConstraints(textName, gc);
        add(textName);

        gc.gridx++;
        layout.setConstraints(textInitialValue, gc);
        add(textInitialValue);

        gc.gridx++;
        gc.insets.right = 0;
        layout.setConstraints(textLength, gc);
        add(textLength);

        gc.gridx = 0; gc.gridy++; gc.gridwidth = 4; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 0;
            JPanel panelChecks = new JPanel(new GridLayout(1,2,5,5));
            panelChecks.add(checkIsIdentifier);
            panelChecks.add(checkIsTransient);

        layout.setConstraints(panelChecks, gc);
        add(panelChecks);

        // Java EE
        if(hasJavaEE) {
            gc.gridx = 0; gc.gridy++; gc.gridwidth = 4; gc.gridheight = 1;
            gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.BOTH;
            gc.insets.right = 0;
                JPanel panelJavaEE = createPanelEE();
            layout.setConstraints(panelJavaEE, gc);
            add(panelJavaEE);
        }
    }

    private JPanel createPanelEE() {
        // JAVA EE
        labelColumnName = new JLabel("Name");
        labelColumnDefinition = new JLabel("Definition");
        textColumnName = new JTextField();
        textColumnName.getDocument().addDocumentListener(validationListener);
        textColumnDefinition = new JTextField();
        textColumnDefinition.getDocument().addDocumentListener(validationListener);
        checkIsNullable = new JCheckBox("Null");
        checkIsNullable.setSelected(true);
        checkIsUnique = new JCheckBox("Unique");
        checkIsInsertable = new JCheckBox("Insertable");
        checkIsInsertable.setSelected(true);
        checkIsUpdatable = new JCheckBox("Updatable");
        checkIsUpdatable.setSelected(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Java EE"));
        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);

        GridBagConstraints gc = new GridBagConstraints();

        // Name && Is Null ?
        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5; gc.insets.bottom = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelColumnName, gc);
        mainPanel.add(labelColumnName);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        layout.setConstraints(textColumnName, gc);
        mainPanel.add(textColumnName);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.weightx = 0.0;
        layout.setConstraints(checkIsNullable, gc);
        mainPanel.add(checkIsNullable);

        // Definition & Is
        gc.gridx = 0; gc.gridy++;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5; gc.insets.bottom = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelColumnDefinition, gc);
        mainPanel.add(labelColumnDefinition);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        layout.setConstraints(textColumnDefinition, gc);
        mainPanel.add(textColumnDefinition);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.weightx = 0.0;
        layout.setConstraints(checkIsUnique, gc);
        mainPanel.add(checkIsUnique);

        // Insertable & Updatable
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 3; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1.0;
            JPanel panelInsertUpdate = new JPanel(new GridLayout(1,2,5,5));
            panelInsertUpdate.add(checkIsInsertable);
            panelInsertUpdate.add(checkIsUpdatable);
        layout.setConstraints(panelInsertUpdate, gc);
        mainPanel.add(panelInsertUpdate);

        return mainPanel;
    }

    private class ValidationListener implements DocumentListener {

        private void validate() {
            boolean result = !textName.getText().isEmpty() &&
                    !textType.getText().isEmpty() &&
                    !textInitialValue.getText().isEmpty() &&
                    // Java EE
                    (textColumnName != null ? !textColumnName.getText().isEmpty() : true) &&
                    (textColumnDefinition != null ? !textColumnDefinition.getText().isEmpty() : true);
            if(dialogDescriptor != null) dialogDescriptor.setValid(result);
        }

        @Override
        public void changedUpdate(DocumentEvent e) { validate(); }
        @Override
        public void insertUpdate(DocumentEvent e) { validate(); }
        @Override
        public void removeUpdate(DocumentEvent e) { validate(); }
    }

    private class CheckIsIdentifierListener implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            if(!hasJavaEE) return;

            boolean state = checkIsIdentifier.isSelected();
            if(state) {
                checkIsNullable.setSelected(!state);
                checkIsUnique.setSelected(!state);
                checkIsInsertable.setSelected(!state);
                checkIsUpdatable.setSelected(!state);
            } else {
                checkIsNullable.setSelected(true);
                checkIsInsertable.setSelected(true);
                checkIsUpdatable.setSelected(true);
            }

            checkIsNullable.setEnabled(!state);
            checkIsInsertable.setEnabled(!state);
            checkIsUpdatable.setEnabled(!state);
        }
    }

    private class TextTypeListener implements DocumentListener {

        private void checkType() {
            String value = textType.getText();
            // int or Integer
            if(value.equals("int") || value.equals("Integer")) {
                textInitialValue.setText("-1");
                textLength.setText("");
                if(hasJavaEE) textColumnDefinition.setText("INT");
            // String
            } else if (value.equalsIgnoreCase("String")) {
                textInitialValue.setText("null");
                textLength.setText(DEFAULT_STRING_LENGTH);
                if(hasJavaEE) textColumnDefinition.setText("VARCHAR(".concat(DEFAULT_STRING_LENGTH.concat(")")));
                
            // Long
            } else if(value.equalsIgnoreCase("long")) {
                textInitialValue.setText("-1");
                textLength.setText("");
                if(hasJavaEE) textColumnDefinition.setText("BIGINT");
            }
        }

        public void changedUpdate(DocumentEvent e) {checkType();}
        public void insertUpdate(DocumentEvent e) {checkType();}
        public void removeUpdate(DocumentEvent e) {checkType();}
    }

    private class TextLengthListener implements DocumentListener, FocusListener {

        private String initial;

        private void checkLength() {
            if(!hasJavaEE) return;
            
            String value = textLength.getText();
            String valueDef = textColumnDefinition.getText();
            if(!value.isEmpty() && !value.contains("-")
                                        && initial != null && !initial.isEmpty()) {
                try {
                    int valueInt = Integer.parseInt(value);
                        textColumnDefinition.setText(initial.concat("(".
                                concat(String.valueOf(valueInt).concat(")"))));

                } catch(NumberFormatException e) {
                    textColumnDefinition.setText(valueDef);
                }
            } else textColumnDefinition.setText(initial);
        }

        public void focusGained(FocusEvent e) {
            if(!hasJavaEE) return;
            
            initial = textColumnDefinition.getText();
            if(initial.contains("("))
                initial = initial.substring(0, initial.indexOf("("));
        }

        public void focusLost(FocusEvent e) {checkLength();}

        public void changedUpdate(DocumentEvent e) {checkLength();}
        public void insertUpdate(DocumentEvent e) {checkLength();}
        public void removeUpdate(DocumentEvent e) {checkLength();}
    }

    private class TextNameListener implements DocumentListener {

        private void checkName() {
            char[] value = textName.getText().toCharArray();
            // is it ID ?
            if(new String(value).equals("ID")) {
                if(hasJavaEE) textColumnName.setText("ID");
                checkIsIdentifier.setSelected(true);
                return;
            }

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

            if(hasJavaEE) textColumnName.setText(result.toString().toUpperCase());
        }

        public void changedUpdate(DocumentEvent e) {checkName();}
        public void insertUpdate(DocumentEvent e) {checkName();}
        public void removeUpdate(DocumentEvent e) {checkName();}
    }
}
