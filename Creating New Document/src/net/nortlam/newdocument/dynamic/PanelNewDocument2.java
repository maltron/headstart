package net.nortlam.newdocument.dynamic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import org.openide.WizardDescriptor;

/**
 * @author Mauricio Leal */
public final class PanelNewDocument2 extends JPanel {

    public static final String PROP_PROJECT_NAME = "ProjectNameEE";
    public static final String PROP_DATA_SOURCE = "DataSource";
    public static final String PROP_PERSISTENCE_UNIT = "PersistenceUnit";
    public static final String PROP_SESSION_PACKAGE_NAME = "SessionPackageName";
    public static final String PROP_CONTEXT_ROOT = "ContextRoot";
    public static final String PROP_REST_PACKAGE_NAME = "RestPackageName";
    public static final String PROP_REST_URL = "RestURL";

    private JLabel labelProjectName;
    private JTextField textProjectName;
    private JLabel labelDataSource;
    private JTextField textDataSource;
    private JLabel labelPersistenceUnit;
    private JTextField textPersistenceUnit;
    private JLabel labelSessionPackageName;
    private JTextField textSessionPackageName;
    private JLabel labelContextRoot;
    private JTextField textContextRoot;
    private JLabel labelRestPackageName;
    private JTextField textRestPackageName;
    private JLabel labelRestURL;
    private JTextField textRestURL;

    public PanelNewDocument2() {
        initComponents();
    }

//    public void clean() {
//        textProjectName.setText("");
//        textDataSource.setText("");
//        textPersistenceUnit.setText("");
//        textSessionPackageName.setText("");
//        textContextRoot.setText("");
//        textRestPackageName.setText("");
//        textRestURL.setText("");
//    }

    private void initComponents() {
        labelProjectName = new JLabel("Project");
        textProjectName = new JTextField();
        labelDataSource = new JLabel("Data Source");
        textDataSource = new JTextField();
        labelPersistenceUnit = new JLabel("Persistence Unit");
        textPersistenceUnit = new JTextField();
        labelSessionPackageName = new JLabel("EJB Package");
        textSessionPackageName = new JTextField();
        labelContextRoot = new JLabel("Context Root");
        textContextRoot = new JTextField();
        labelRestPackageName = new JLabel("Rest Package");
        textRestPackageName = new JTextField();
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
        gc.insets.right = 0;
        gc.weightx = 1.0;
        layout.setConstraints(textProjectName, gc);
        add(textProjectName);

        // DATA SOURCE
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelDataSource, gc);
        add(labelDataSource);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 0;
        gc.weightx = 1.0;
        layout.setConstraints(textDataSource, gc);
        add(textDataSource);

        // PERSISTENCE UNIT
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelPersistenceUnit, gc);
        add(labelPersistenceUnit);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 0;
        gc.weightx = 1.0;
        layout.setConstraints(textPersistenceUnit, gc);
        add(textPersistenceUnit);

        // EJB PACKAGE
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelSessionPackageName, gc);
        add(labelSessionPackageName);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 0;
        gc.weightx = 1.0;
        layout.setConstraints(textSessionPackageName, gc);
        add(textSessionPackageName);

        // CONTEXT ROOT
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelContextRoot, gc);
        add(labelContextRoot);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 0;
        gc.weightx = 1.0;
        layout.setConstraints(textContextRoot, gc);
        add(textContextRoot);

        // REST PACKAGE
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.WEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5;
        gc.weightx = 0.0; gc.weighty = 0.0;
        layout.setConstraints(labelRestPackageName, gc);
        add(labelRestPackageName);

        gc.gridx++;
        gc.anchor = GridBagConstraints.CENTER; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 0;
        gc.weightx = 1.0;
        layout.setConstraints(textRestPackageName, gc);
        add(textRestPackageName);

        // REST URL
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 1; gc.gridheight = 1;
        gc.anchor = GridBagConstraints.NORTHWEST; gc.fill = GridBagConstraints.NONE;
        gc.insets.right = 5;
        gc.weightx = 0.0; gc.weighty = 1.0;
        layout.setConstraints(labelRestURL, gc);
        add(labelRestURL);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTH; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.right = 0;
        gc.weightx = 1.0;
        layout.setConstraints(textRestURL, gc);
        add(textRestURL);
    }

    @Override
    public String getName() {
        return "Java Enterprise Settings";
    }

    public void addDocumentListener(DocumentListener listener) {
        textProjectName.getDocument().addDocumentListener(listener);
        textDataSource.getDocument().addDocumentListener(listener);
        textPersistenceUnit.getDocument().addDocumentListener(listener);
        textSessionPackageName.getDocument().addDocumentListener(listener);
        textContextRoot.getDocument().addDocumentListener(listener);
        textRestPackageName.getDocument().addDocumentListener(listener);
        textRestURL.getDocument().addDocumentListener(listener);
    }

    public boolean isTextFieldFilled() {
        return !textProjectName.getText().isEmpty() &&
                !textDataSource.getText().isEmpty() &&
                 !textPersistenceUnit.getText().isEmpty() &&
                  !textSessionPackageName.getText().isEmpty() &&
                   !textContextRoot.getText().isEmpty() &&
                    !textRestPackageName.getText().isEmpty() &&
                     !textRestURL.getText().isEmpty();
    }

    public void readProperties(WizardDescriptor descriptor) {
        String projectName = (String)descriptor.getProperty(PanelNewDocument1.PROP_PROJECT_NAME);
        textProjectName.setText(projectName);
        textDataSource.setText("jdbc/".concat(projectName.toLowerCase()));
        textPersistenceUnit.setText(projectName.concat("PU"));
        textSessionPackageName.setText("net.nortlam.session");
        textContextRoot.setText("/".concat(projectName.toLowerCase()));
        textRestPackageName.setText("net.nortlam.".concat(projectName.toLowerCase().concat(".resources")));
        textRestURL.setText("/rest");
    }

    public void writeProperties(WizardDescriptor descriptor) {
        descriptor.putProperty(PROP_PROJECT_NAME, textProjectName.getText());
        descriptor.putProperty(PROP_DATA_SOURCE, textDataSource.getText());
        descriptor.putProperty(PROP_PERSISTENCE_UNIT, textPersistenceUnit.getText());
        descriptor.putProperty(PROP_SESSION_PACKAGE_NAME, textSessionPackageName.getText());
        descriptor.putProperty(PROP_CONTEXT_ROOT, textContextRoot.getText());
        descriptor.putProperty(PROP_REST_PACKAGE_NAME, textRestPackageName.getText());
        descriptor.putProperty(PROP_REST_URL, textRestURL.getText());
    }
}
