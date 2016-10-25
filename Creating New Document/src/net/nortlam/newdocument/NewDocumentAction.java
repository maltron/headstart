package net.nortlam.newdocument;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import net.nortlam.crudmaker.Project;
import net.nortlam.crudmaker.design.DesignDocument;
import net.nortlam.crudmaker.impl.ProjectJavaEE;
import net.nortlam.crudmaker.impl.ProjectJavaME;
import net.nortlam.crudmaker.impl.ProjectPlain;

import net.nortlam.visual.EntitySceneTopComponent;

import net.nortlam.newdocument.dynamic.DynamicWizardIterator;
import net.nortlam.newdocument.dynamic.PanelNewDocument1;
import net.nortlam.newdocument.dynamic.PanelNewDocument2;
import net.nortlam.newdocument.dynamic.PanelNewDocument3;

import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;

public final class NewDocumentAction implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        WizardDescriptor.Iterator<WizardDescriptor> iterator = new DynamicWizardIterator();
        WizardDescriptor descriptor = new WizardDescriptor(iterator);
        descriptor.setTitleFormat(new MessageFormat("{0}"));
        descriptor.setTitle("Wizard Title");

        Dialog dialog = DialogDisplayer.getDefault().createDialog(descriptor);
        dialog.setVisible(true);
        dialog.toFront();

        boolean cancelled = descriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) createNewEntityTopComponent(descriptor);
    }

    private void createNewEntityTopComponent(WizardDescriptor descriptor) {
        System.out.printf(">>> NewDocument.creatNewEntityTopComponent()\n");
        DesignDocument newDocument = new DesignDocument();
        Set<Project> projects = new HashSet<Project>();

        String projectName = (String)descriptor.getProperty(PanelNewDocument1.PROP_PROJECT_NAME);
        String groupID = (String)descriptor.getProperty(PanelNewDocument1.PROP_GROUP_ID);
        String packageName = (String)descriptor.getProperty(PanelNewDocument1.PROP_PACKAGE_NAME);
        
        String directory = (String)descriptor.getProperty(PanelNewDocument1.PROP_DIRECTORY);
            ProjectPlain plain = new ProjectPlain(newDocument, projectName, groupID, packageName);
            plain.setBaseDir(directory);
        projects.add(plain);

        String javaEE = (String)descriptor.getProperty(PanelNewDocument1.PROP_JAVA_EE);
        String javaME = (String)descriptor.getProperty(PanelNewDocument1.PROP_JAVA_ME);

        if(javaEE != null) { // Creating a Java EE Project
            String projectNameEE = (String)descriptor.getProperty(PanelNewDocument2.PROP_PROJECT_NAME);
            String dataSource = (String)descriptor.getProperty(PanelNewDocument2.PROP_DATA_SOURCE);
            String persistenceUnit = (String)descriptor.getProperty(PanelNewDocument2.PROP_PERSISTENCE_UNIT);
            String sessionPackage = (String)descriptor.getProperty(PanelNewDocument2.PROP_SESSION_PACKAGE_NAME);
            String contextRoot = (String)descriptor.getProperty(PanelNewDocument2.PROP_CONTEXT_ROOT);
            String restPackage = (String)descriptor.getProperty(PanelNewDocument2.PROP_REST_PACKAGE_NAME);
            String restURL = (String)descriptor.getProperty(PanelNewDocument2.PROP_REST_URL);
                ProjectJavaEE projectJavaEE = new ProjectJavaEE(newDocument, projectNameEE, groupID, packageName, 
                        dataSource, persistenceUnit, sessionPackage, contextRoot, restPackage, restURL);
                projects.add(projectJavaEE);

                // Base Dir ?
                if(projectName.equals(projectNameEE))
                    projectJavaEE.setBaseDir(directory.concat(File.separator.concat("javaee")));
                else projectJavaEE.setBaseDir(directory);
        }

        if(javaME != null) { // Creating a Java ME Project
            String projectNameME = (String)descriptor.getProperty(PanelNewDocument3.PROP_PROJECT_NAME);
            String isPersistence = (String)descriptor.getProperty(PanelNewDocument3.PROP_IS_PERSISTENCE);
            String rmsPrefix = (String)descriptor.getProperty(PanelNewDocument3.PROP_RMS_PREFIX);
            String restURL = (String)descriptor.getProperty(PanelNewDocument3.PROP_REST_URL);
            ProjectJavaME projectJavaME = new ProjectJavaME(newDocument, projectNameME, groupID, packageName, 
                    isPersistence != null, rmsPrefix, restURL);
            projects.add(projectJavaME);

            // Base Dir ?
            if(projectName.equals(projectJavaME))
                projectJavaME.setBaseDir((directory.concat(File.separator.concat("javame"))));
            else projectJavaME.setBaseDir(directory);
        }

        // Finally, create a new Top Component
        EntitySceneTopComponent topComponent = new EntitySceneTopComponent();
        // Keep a reference for each TopComponent
        topComponent.setNewDocument(newDocument, projects);
        topComponent.open();
        topComponent.requestActive();
    }
}
