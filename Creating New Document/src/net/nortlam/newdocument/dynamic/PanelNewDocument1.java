package net.nortlam.newdocument.dynamic;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import net.nortlam.crudmaker.ProjectUtil;
import org.openide.WizardDescriptor;

/**
 * @author Mauricio Leal */
public final class PanelNewDocument1 extends JPanel {

    public static final String PROP_PROJECT_NAME = "ProjectName";
    public static final String PROP_DIRECTORY = "Directory";
    public static final String PROP_GROUP_ID = "GroupID";
    public static final String PROP_PACKAGE_NAME = "PackageName";
    public static final String PROP_JAVA_EE = "JavaEE";
    public static final String PROP_JAVA_ME = "JavaME";

    private JLabel labelProjectName;
    private JTextField textProjectName;
    private JLabel labelDirectory;
    private JTextField textDirectory;
    private JButton buttonDirectory;
    private JLabel labelGroupID;
    private JTextField textGroupID;
    private JLabel labelPackageName;
    private JTextField textPackageName;

    private JLabel labelType;
    private JCheckBox checkJavaEE;
    private JCheckBox checkJavaME;
    private JCheckBox checkAndroid;
    private JCheckBox checkiOS;
    private JCheckBox checkBlackBerry;
    private JCheckBox checkQt;
    private JCheckBox checkWP7;
    private JCheckBox checkJXTA;
    private JCheckBox checkNetBeans;
    private JCheckBox[] checkBoxes;
    private JLabel labelErrorMessage;

    private File directorySelected;
    
    private String[] currentNames;

    public PanelNewDocument1() {
        initComponents();
    }

//    public void clean() {
//        textProjectName.setText("");
//        textDirectory.setText("");
//        for(int i=0; i < checkBoxes.length; i++)
//            checkBoxes[i].setSelected(false);
//        
//        currentNames = ProjectUtil.getInstance().listProjectFilenames();
//    }

    private void initComponents() {
        currentNames = ProjectUtil.getInstance().listProjectFilenames();
        
        labelProjectName = new JLabel("Project Name");
        textProjectName = new JTextField();
//        textProjectName.getDocument().addDocumentListener(new FillDirectoryWithProjectName());
        labelDirectory = new JLabel("Directory");
        textDirectory = new JTextField();
//        textDirectory.addFocusListener(new GetDirectoryTyped());
        buttonDirectory = new JButton("Browse");
        buttonDirectory.addActionListener(new BrowseFileChooser());
        
        labelGroupID = new JLabel("Group ID");
        textGroupID = new JTextField();
        labelPackageName = new JLabel("Package");
        textPackageName = new JTextField();

        // PENDING: Perhaps using some icons in here
        labelType = new JLabel("Type");
        checkJavaEE = new JCheckBox("Java EE");
        checkJavaME = new JCheckBox("Java ME");
        checkAndroid = new JCheckBox("Android"); checkAndroid.setEnabled(false);
        checkiOS = new JCheckBox("iOS");         checkiOS.setEnabled(false);
        checkBlackBerry = new JCheckBox("BlackBerry"); checkBlackBerry.setEnabled(false);
        checkQt = new JCheckBox("Qt");           checkQt.setEnabled(false);
        checkWP7 = new JCheckBox("WP7");         checkWP7.setEnabled(false);
        checkJXTA = new JCheckBox("JXTA");       checkJXTA.setEnabled(false);
        checkNetBeans = new JCheckBox("NetBeans");  checkNetBeans.setEnabled(false);
        checkBoxes = new JCheckBox[] {checkJavaEE, checkJavaME,
            checkAndroid, checkiOS, checkBlackBerry, checkQt, checkWP7, checkJXTA,
            checkNetBeans
        };
        labelErrorMessage = new JLabel();
        labelErrorMessage.setForeground(Color.RED);
        
//        for(int i=0; i < checkBoxes.length; i++)
//            checkBoxes[i].addChangeListener(new DetectChanges());

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        GridBagConstraints gc = new GridBagConstraints();

        // PROJECT NAME
        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5; gc.insets.bottom = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelProjectName, gc);
        add(labelProjectName);

        gc.gridx++; gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 5; gc.insets.bottom = 5;
        gc.weightx = 1.0; gc.weighty = 0.0;
        layout.setConstraints(textProjectName, gc);
        add(textProjectName);

        // DIRECTORY
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5; gc.insets.bottom = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelDirectory, gc);
        add(labelDirectory);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTH; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 5; gc.insets.bottom = 5;
        gc.weightx = 1.0; gc.weighty = 0.0;
        layout.setConstraints(textDirectory, gc);
        add(textDirectory);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTH; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 0; gc.insets.bottom = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(buttonDirectory, gc);
        add(buttonDirectory);
        
        // Group ID
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5; gc.insets.bottom = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelGroupID, gc);
        add(labelGroupID);
        
        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTH; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 5;
        gc.weightx = 1.0; gc.weighty = 0.0;
        layout.setConstraints(textGroupID, gc);
        add(textGroupID);
        
        // Package Name
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5; gc.insets.bottom = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelPackageName, gc);
        add(labelPackageName);
        
        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTH; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 5;
        gc.weightx = 1.0; gc.weighty = 0.0;
        layout.setConstraints(textPackageName, gc);
        add(textPackageName);

        // TYPES
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 3; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.NORTH; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 0; gc.insets.bottom = 0;
        gc.weightx = 1.0; gc.weighty = 1.0;
            JPanel panelTypes = new JPanel(new GridLayout(0, 5));
            panelTypes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Types"));
            panelTypes.add(checkJavaEE);
            panelTypes.add(checkJavaME);
            panelTypes.add(checkAndroid);
            panelTypes.add(checkiOS);
            panelTypes.add(checkBlackBerry);
            panelTypes.add(checkQt);
            panelTypes.add(checkWP7);
            panelTypes.add(checkJXTA);
            panelTypes.add(checkNetBeans);
        layout.setConstraints(panelTypes, gc);
        add(panelTypes);
        
        // Error Message
        gc.gridx = 0; gc.gridy++;
        gc.anchor = GridBagConstraints.SOUTH; gc.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(labelErrorMessage, gc);
        add(labelErrorMessage);
    }

    public void addDocumentListener(DocumentListener listener) {
        textProjectName.getDocument().addDocumentListener(listener);
        textGroupID.getDocument().addDocumentListener(listener);
        textPackageName.getDocument().addDocumentListener(listener);
        textDirectory.getDocument().addDocumentListener(listener);
    }

    public void addChangeListener(ChangeListener listener) {
        for(int i=0; i < checkBoxes.length; i++)
            checkBoxes[i].addChangeListener(listener);
    }

//    private class DetectChanges implements ChangeListener {
//
//        @Override
//        public void stateChanged(ChangeEvent e) {
//            System.out.printf(">>> Firing Property Change:%s\n", PROP_TYPE);
//            pcs.firePropertyChange(PROP_TYPE, null, null);
//        }
//    }

    private class BrowseFileChooser implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String old = textDirectory.getText();

            JFileChooser jc = new JFileChooser();
            jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = jc.showOpenDialog(PanelNewDocument1.this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                directorySelected = jc.getSelectedFile();
                setDirectory(directorySelected);
            }
        }
    }

    private void setDirectory(File directory) {
        String projectName = textProjectName.getText();
        if(projectName != null && !projectName.isEmpty())
            textDirectory.setText(new File(directory, projectName).toString());
        else textDirectory.setText(directory.toString());
    }

//    /**
//     * Based on what the user types, it will fill the directory name */
//    private class FillDirectoryWithProjectName implements DocumentListener {
//
//        private void fillDirectory() {
//            String projectName = textProjectName.getText();
//
//            if(projectName != null && !projectName.isEmpty() && directorySelected != null)
//                textDirectory.setText(new File(directorySelected, projectName).toString());
//            else if(projectName != null && projectName.isEmpty() && directorySelected != null)
//                textDirectory.setText(directorySelected.toString());
//
//            System.out.printf(">>> Firing Property Change:%s\n", PROP_PROJECT_NAME);
//            pcs.firePropertyChange(PROP_PROJECT_NAME, null, projectName);
//        }
//
//        public void changedUpdate(DocumentEvent e) {
//            fillDirectory();
//        }
//
//        public void insertUpdate(DocumentEvent e) {
//            fillDirectory();
//        }
//
//        public void removeUpdate(DocumentEvent e) {
//            fillDirectory();
//        }
//    }
//
//    private class GetDirectoryTyped implements FocusListener {
//
//        public void focusGained(FocusEvent e) {
//        }
//
//        public void focusLost(FocusEvent e) {
//            String directory = textDirectory.getText();
//            if(directory != null && !directory.isEmpty()) {
//                directorySelected = new File(directory);
//
//                System.out.printf(">>> Firing Property Change:%s\n", PROP_DIRECTORY);
//                pcs.firePropertyChange(PROP_DIRECTORY, null, directory);
//            }
//        }
//    }

    @Override
    public String getName() {
        return "Basic Settings";
    }
    
    public boolean isProjectNameValid() {
        //System.out.printf(">>> PanelNewDocument.isProjectNameValid() CurrentNames is NULL ? %b\n", currentNames == null);
        boolean valid = true;
        if(currentNames != null)
            for(String currentName: currentNames)
                if(textProjectName != null && 
                                textProjectName.getText().equals(currentName)) {
                    valid = false; break;
                }
        
        if(valid) labelErrorMessage.setText("");
        else labelErrorMessage.setText("This Project already exists");
        
        //System.out.printf(">>> PanelNewDocument.isProjectNameValid() %b\n", valid);
        return valid;
    }

    public boolean isTextFieldFilled() {
        return (textProjectName != null && !textProjectName.getText().isEmpty())
                && (textGroupID != null && !textGroupID.getText().isEmpty())
                && (textPackageName != null && !textPackageName.getText().isEmpty())
                && (textDirectory != null && !textDirectory.getText().isEmpty());
    }

    public boolean isAnyCheckBoxSelected() {
        if(checkBoxes == null) return false;

        boolean result = false;
        for(int i=0; i < checkBoxes.length; i++)
            // If any of the CheckBox is selected, break
            if(result = (checkBoxes[i] != null && checkBoxes[i].isSelected())) break;

        return result;
    }

    public boolean isJavaEE() {
        return checkJavaEE.isSelected();
    }

    public boolean isJavaME() {
        return checkJavaME.isSelected();
    }

    public void storeProperties(WizardDescriptor descriptor) {
        descriptor.putProperty(PROP_PROJECT_NAME, textProjectName.getText());
        descriptor.putProperty(PROP_DIRECTORY, textDirectory.getText());
        descriptor.putProperty(PROP_JAVA_EE, checkJavaEE.isSelected() ? PROP_JAVA_EE : null);
        descriptor.putProperty(PROP_JAVA_ME, checkJavaME.isSelected() ? PROP_JAVA_ME : null);
        descriptor.putProperty(PROP_GROUP_ID, textGroupID.getText());
        descriptor.putProperty(PROP_PACKAGE_NAME, textPackageName.getText());

        if(!checkJavaEE.isSelected()) {
            descriptor.putProperty(PanelNewDocument2.PROP_PROJECT_NAME, null);
            descriptor.putProperty(PanelNewDocument2.PROP_DATA_SOURCE, null);
            descriptor.putProperty(PanelNewDocument2.PROP_PERSISTENCE_UNIT, null);
            descriptor.putProperty(PanelNewDocument2.PROP_SESSION_PACKAGE_NAME, null);
            descriptor.putProperty(PanelNewDocument2.PROP_CONTEXT_ROOT, null);
            descriptor.putProperty(PanelNewDocument2.PROP_REST_PACKAGE_NAME, null);
            descriptor.putProperty(PanelNewDocument2.PROP_REST_URL, null);
        }

        if(!checkJavaME.isSelected()) {
            descriptor.putProperty(PanelNewDocument3.PROP_PROJECT_NAME, null);
            descriptor.putProperty(PanelNewDocument3.PROP_IS_PERSISTENCE, null);
            descriptor.putProperty(PanelNewDocument3.PROP_RMS_PREFIX, null);
            descriptor.putProperty(PanelNewDocument3.PROP_REST_URL, null);
        }
    }
}
