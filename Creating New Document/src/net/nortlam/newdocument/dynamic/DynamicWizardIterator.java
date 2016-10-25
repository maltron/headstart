package net.nortlam.newdocument.dynamic;

import java.awt.Component;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;

public final class DynamicWizardIterator implements WizardDescriptor.Iterator<WizardDescriptor> {

    private int index;

    private static final int BASIC_SETTINGS = 0;
    private static final int JAVA_EE = 1;
    private static final int JAVA_ME = 2;
    private DynamicWizardPanel1 wizard1;
    private DynamicWizardPanel2 wizard2;
    private DynamicWizardPanel3 wizard3;

    private Set<ChangeListener> listeners = new HashSet<ChangeListener>(1);

    public DynamicWizardIterator() {
        setPropertyOnPanels(); index = BASIC_SETTINGS;
    }

    private void setPropertyOnPanels() {
        WizardDescriptor.Panel[] panels = new WizardDescriptor.Panel[] {
            wizard1 = new DynamicWizardPanel1(this),
            wizard2 = new DynamicWizardPanel2(this),
            wizard3 = new DynamicWizardPanel3(this)
        };

        String[] steps = new String[panels.length];
        for (int i = 0; i < panels.length; i++) {
            Component c = panels[i].getComponent();
            // Default step name to component name of panel.
            steps[i] = c.getName();
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                // Sets step number of a component
                // TODO if using org.openide.dialogs >= 7.8, can use WizardDescriptor.PROP_*:
                jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(i));
                // Sets steps names for a panel
                jc.putClientProperty("WizardPanel_contentData", steps);
                // Turn on subtitle creation on each step
                jc.putClientProperty("WizardPanel_autoWizardStyle", Boolean.TRUE);
                // Show steps on the left side with the image on the background
                jc.putClientProperty("WizardPanel_contentDisplayed", Boolean.TRUE);
                // Turn on numbering of all steps
                jc.putClientProperty("WizardPanel_contentNumbered", Boolean.TRUE);
            }
        }
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
        //return getPanels()[index];
        WizardDescriptor.Panel<WizardDescriptor> panel = null;
        switch(index) {
            case BASIC_SETTINGS: panel = wizard1; break;
            case JAVA_EE: panel = wizard2; break;
            case JAVA_ME: panel = wizard3; break;
        }

        return panel;
    }

    @Override
    public String name() {
        return index + 1 + ". from " + 3;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;
        switch(index) {
            case BASIC_SETTINGS: result = wizard1.hasNext(); break;
            case JAVA_EE: result = wizard2.hasNext() && wizard1.isJavaME(); break;
            case JAVA_ME: result = false; break;
        }

        return result;
    }

    @Override
    public boolean hasPrevious() {
        if(index == 0) return false;

        return true;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) throw new NoSuchElementException();

        switch(index) {
            case BASIC_SETTINGS:
                if(wizard1.isJavaEE()) index++; else index += 2; break;
            case JAVA_EE: index++; break;
            case JAVA_ME: index++; break;
        }
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }

        switch(index) {
            case BASIC_SETTINGS: index--; break;
            case JAVA_EE: index--; break;
            case JAVA_ME:
                if(wizard1.isJavaEE()) index--;
                else index -= 2;
        }
    }

    // If nothing unusual changes in the middle of the wizard, simply:
    @Override
    public final void addChangeListener(ChangeListener event) {
        synchronized(listeners) {
            listeners.add(event);
        }
    }

    @Override
    public final void removeChangeListener(ChangeListener event) {
        synchronized(listeners) {
            listeners.remove(event);
        }
    }

    public final void fireChangeEvent() {
        Iterator<ChangeListener> iterator;
        synchronized(listeners) {
            iterator = new HashSet<ChangeListener>(listeners).iterator();
        }

        ChangeEvent event = new ChangeEvent(this);
        while(iterator.hasNext()) iterator.next().stateChanged(event);
    }
}
