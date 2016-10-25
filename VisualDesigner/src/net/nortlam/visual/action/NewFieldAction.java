package net.nortlam.visual.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import net.nortlam.crudmaker.design.Entity;
import net.nortlam.crudmaker.design.EntityField;
import net.nortlam.visual.EntityScene;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

public final class NewFieldAction extends AbstractAction 
                         implements LookupListener, ContextAwareAction {

    private Entity entity;
    private PanelField panel;
    private EntityScene scene;

    private Lookup.Result<EntityScene> resultScene;
    private Lookup.Result<Entity> resultEntity;

    public NewFieldAction() {
        this(Utilities.actionsGlobalContext());
        putValue(Action.NAME, "New Field....");
    }

    public NewFieldAction(Lookup lookup) {
        super(NbBundle.getMessage(NewFieldAction.class, "CTL_NewFieldAction"));
        System.out.printf(">>> NewFieldAction()\n");
        resultScene = lookup.lookupResult(EntityScene.class);
        resultScene.addLookupListener(this);
        resultChanged(new LookupEvent(resultScene));

        resultEntity = lookup.lookupResult(Entity.class);
        resultEntity.addLookupListener(this);
        resultChanged(new LookupEvent(resultEntity));

        setEnabled(false);
    }

    public void actionPerformed(ActionEvent event) {
        System.out.printf(">>> NewFieldAction.actionPerformed() Scene is Null ? %b Entity is Null ? %b\n", scene == null, entity == null);
        panel = new PanelField(scene);

        EntityField newField = new EntityField(entity, "", "", false, "");
        panel.setField(newField);

        DialogDescriptor dialogNewEntity = new DialogDescriptor(panel, "New Field",
                true, DialogDescriptor.OK_CANCEL_OPTION, null, null);
        panel.setDialogDescriptor(dialogNewEntity);
        Object result = DialogDisplayer.getDefault().notify(dialogNewEntity);
        if(result != null && result == DialogDescriptor.OK_OPTION) {
            panel.updateField();
            scene.addField(entity, newField); scene.validate();
        }
    }

    @Override
    public void resultChanged(LookupEvent event) {
        System.out.printf(">>> NewFieldAction.resultChanged()\n");
        if(resultScene != null && !resultScene.allInstances().isEmpty())
            scene = resultScene.allInstances().iterator().next();
        else scene = null;

        if(resultEntity != null && !resultEntity.allInstances().isEmpty())
            entity = resultEntity.allInstances().iterator().next();
        else entity = null;

        setEnabled(scene != null && entity != null);
    }

    @Override
    public Action createContextAwareInstance(Lookup lookup) {
        return new NewFieldAction(lookup);
    }
}
