package net.nortlam.newdocument.dynamic;

import java.awt.Component;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class DynamicWizardPanel1
          implements WizardDescriptor.Panel<WizardDescriptor>,
                        DocumentListener, ChangeListener {

    private DynamicWizardIterator root;
    private PanelNewDocument1 panel;
    private Set<ChangeListener> listeners = new HashSet<ChangeListener>(1);

    public DynamicWizardPanel1(DynamicWizardIterator root) {
        this.root = root;
    }

//    public void clean() {
//        panel.clean();
//    }

    public Component getComponent() {
        if (panel == null) {
            panel = new PanelNewDocument1();
            panel.addDocumentListener(this);
            panel.addChangeListener(this);
        }
        return panel;
    }

    protected final void fireChangeEvent() {
        //System.out.printf(">>> DynamicWizardPanel1.fireChangeEvent()\n");
        // First, try to notify the root
        root.fireChangeEvent();

        Iterator<ChangeListener> it;
        synchronized(listeners) {
            it = new HashSet<ChangeListener>(listeners).iterator();
        }

        ChangeEvent event = new ChangeEvent(this);
        while(it.hasNext()) it.next().stateChanged(event);
    }

    public boolean hasNext() {
        return panel.isTextFieldFilled() && panel.isAnyCheckBoxSelected()
                && panel.isProjectNameValid();
    }

    public boolean isJavaEE() {
        return panel.isJavaEE();
    }

    public boolean isJavaME() {
        return panel.isJavaME();
    }

    // CHANGE LISTENER CHANGE LISTENER CHANGE LISTENER CHANGE LISTENER CHANGE LISTENER
    //  CHANGE LISTENER CHANGE LISTENER CHANGE LISTENER CHANGE LISTENER CHANGE LISTENER 

    @Override
    public void stateChanged(ChangeEvent e) {
        fireChangeEvent();
    }

    // DOCUMENT LISTENER DOCUMENT LISTENER DOCUMENT LISTENER DOCUMENT LISTENER
    //  DOCUMENT LISTENER DOCUMENT LISTENER DOCUMENT LISTENER DOCUMENT LISTENER

    @Override
    public void changedUpdate(DocumentEvent e) {
        fireChangeEvent();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        fireChangeEvent();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        fireChangeEvent();
    }

    // WIZARDDESCRIPTOR PANEL WIZARDDESCRIPTOR PANEL WIZARDDESCRIPTOR PANEL WIZARDDESCRIPTOR PANEL
    //  WIZARDDESCRIPTOR PANEL WIZARDDESCRIPTOR PANEL WIZARDDESCRIPTOR PANEL WIZARDDESCRIPTOR PANEL

    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx(SampleWizardPanel1.class);
    }

    public boolean isValid() {
        //System.out.printf(">>> DynamicWizardPanel1.isValid() %b\n", panel.isTextFieldFilled() && panel.isProjectNameValid());
        return panel.isTextFieldFilled() && panel.isProjectNameValid();
    }

    public final void addChangeListener(ChangeListener event) {
        synchronized(listeners) {
            listeners.add(event);
        }
    }

    public final void removeChangeListener(ChangeListener event) {
        synchronized(listeners) {
            listeners.remove(event);
        }
    }

    public void readSettings(WizardDescriptor settings) {
        // NOT USED
    }

    public void storeSettings(WizardDescriptor settings) {
        panel.storeProperties(settings);
    }
}
