package net.nortlam.visual.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.nortlam.crudmaker.design.Entity;
import net.nortlam.visual.EntityScene;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;

public final class NewEntityAction implements ActionListener {

    private EntityScene scene;
    private PanelEntity panel;

    private static String lastPackageName;

    public NewEntityAction(EntityScene scene) {
        this.scene = scene;
    }

    public void actionPerformed(ActionEvent e) {
        if(panel == null) // Does it has a Java EE
            panel = new PanelEntity(scene);

        Entity newEntity = new Entity((lastPackageName != null ? lastPackageName : ""), "", true); // New Instance
        panel.setEntity(newEntity);

        DialogDescriptor dialogNewEntity = new DialogDescriptor(panel, "New Entity",
                true, DialogDescriptor.OK_CANCEL_OPTION, null, null);
        panel.setDialogDescritor(dialogNewEntity);
        Object result = DialogDisplayer.getDefault().notify(dialogNewEntity);
        
        // Create a new Entity
        if(result != null && result == DialogDescriptor.OK_OPTION) {
            panel.updateEntity();
            lastPackageName = newEntity.getPackageName();
            scene.addEntity(newEntity); scene.layoutScene(); scene.validate();
        }
    }
}
