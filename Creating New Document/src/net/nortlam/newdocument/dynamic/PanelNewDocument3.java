package net.nortlam.newdocument.dynamic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import org.openide.WizardDescriptor;

/**
 * @author Mauricio Leal */
public final class PanelNewDocument3 extends JPanel {

    public static final String PROP_PROJECT_NAME = "ProjectNameME";
    public static final String PROP_IS_PERSISTENCE = "IsPersistence";
    public static final String PROP_RMS_PREFIX = "RMS-Prefix";
    public static final String PROP_REST_URL = "RestURLME";

    private JLabel labelProjectName;
    private JTextField textProjectName;
    private JCheckBox checkIsPersistence;
    private JLabel labelRMSPrefix;
    private JTextField textRMSPrefix;
    private JLabel labelRestURL;
    private JTextField textRestURL;

    public PanelNewDocument3() {
        initComponents();
    }

//    public void clean() {
//        textProjectName.setText("");
//        checkIsPersistence.setSelected(false);
//        textRMSPrefix.setText("");
//        textRestURL.setText("");
//    }

    private void initComponents() {
        labelProjectName = new JLabel("Project");
        textProjectName = new JTextField();
        checkIsPersistence = new JCheckBox("Is Persistence ?");
        labelRMSPrefix = new JLabel("RMS Prefix");
        textRMSPrefix = new JTextField();
        labelRestURL = new JLabel("Rest URL");
        textRestURL = new JTextField();

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

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 5;
        gc.weightx = 1.0;
        layout.setConstraints(textProjectName, gc);
        add(textProjectName);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHEAST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 0;
        gc.weightx = 0.0;
        layout.setConstraints(checkIsPersistence, gc);
        add(checkIsPersistence);

        // RMS PREFIX
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelRMSPrefix, gc);
        add(labelRMSPrefix);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 5;
        gc.weightx = 1.0;
        layout.setConstraints(textRMSPrefix, gc);
        add(textRMSPrefix);

        // REST URL
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.NORTHWEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5;
        gc.weightx = 0.0; gc.weighty = 1.0;
        layout.setConstraints(labelRestURL, gc);
        add(labelRestURL);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTH; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 5;
        gc.weightx = 1.0; gc.weighty = 1.0;
        layout.setConstraints(textRestURL, gc);
        add(textRestURL);
    }

    @Override
    public String getName() {
        return "Java Mobile Settings";
    }

    public void addDocumentListener(DocumentListener listener) {
        textProjectName.getDocument().addDocumentListener(listener);
        textRMSPrefix.getDocument().addDocumentListener(listener);
        textRestURL.getDocument().addDocumentListener(listener);
    }

    public void addChangeListener(ChangeListener listener) {
        checkIsPersistence.addChangeListener(listener);
    }

    public boolean isTextFieldFilled() {
        return !textProjectName.getText().isEmpty() &&
                !textRMSPrefix.getText().isEmpty() &&
                 !textRestURL.getText().isEmpty();
    }

    public void readProperties(WizardDescriptor descriptor) {
        String projectName = (String)descriptor.getProperty(PanelNewDocument1.PROP_PROJECT_NAME);
        if(projectName != null) {
            textProjectName.setText(projectName);
            checkIsPersistence.setSelected(true);
            textRMSPrefix.setText("rms-".concat(projectName.toLowerCase()));
        } else {
            textProjectName.setText("");
            checkIsPersistence.setSelected(true);
            textRMSPrefix.setText("");
        }

        String contextRoot = (String)descriptor.getProperty(PanelNewDocument2.PROP_CONTEXT_ROOT);
        String restURL = (String)descriptor.getProperty(PanelNewDocument2.PROP_REST_URL);
        if(contextRoot != null && restURL != null)
            textRestURL.setText(contextRoot.concat(restURL));
        else textRestURL.setText("");
    }

    public void writeProperties(WizardDescriptor descriptor) {
        descriptor.putProperty(PROP_PROJECT_NAME, textProjectName.getText());
        descriptor.putProperty(PROP_IS_PERSISTENCE, checkIsPersistence.isSelected() ? PROP_IS_PERSISTENCE : null);
        descriptor.putProperty(PROP_RMS_PREFIX, textRMSPrefix.getText());
        descriptor.putProperty(PROP_REST_URL, textRestURL.getText());
    }
}
