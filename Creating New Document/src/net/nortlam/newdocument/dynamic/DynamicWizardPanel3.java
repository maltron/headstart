package net.nortlam.newdocument.dynamic;

import java.awt.Component;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class DynamicWizardPanel3 implements WizardDescriptor.Panel<WizardDescriptor> {

    private DynamicWizardIterator root;
    private PanelNewDocument3 panel;

    private Set<ChangeListener> listeners = new HashSet<ChangeListener>(1);

    public DynamicWizardPanel3(DynamicWizardIterator root) {
        this.root = root;
    }

//    public void clean() {
//        panel.clean();
//    }

    public boolean hasNext() {
        return panel.isTextFieldFilled();
    }

    public Component getComponent() {
        if(panel == null) panel = new PanelNewDocument3();

        return panel;
    }

    protected final void fireChangeEvent() {
        // First, try to notify the root
        root.fireChangeEvent();

        Iterator<ChangeListener> it;
        synchronized(listeners) {
            it = new HashSet<ChangeListener>(listeners).iterator();
        }

        ChangeEvent event = new ChangeEvent(this);
        while(it.hasNext()) it.next().stateChanged(event);
    }

    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx(SampleWizardPanel1.class);
    }

    public boolean isValid() {
        return hasNext();
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

    public void readSettings(WizardDescriptor descriptor) {
        panel.readProperties(descriptor);
    }

    public void storeSettings(WizardDescriptor descriptor) {
        panel.writeProperties(descriptor);
    }
}
