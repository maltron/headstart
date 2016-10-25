package net.nortlam.visual.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import net.nortlam.crudmaker.design.Entity;
import net.nortlam.crudmaker.design.EntityField;
import net.nortlam.crudmaker.impl.ProjectJavaEE;
import net.nortlam.crudmaker.impl.javaee.column.Column;
import net.nortlam.visual.EntityScene;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 * @author Mauricio Leal */
public class NewFieldIdentifierAction extends AbstractAction 
                                implements LookupListener, ContextAwareAction {
    
    private Entity entity;
    private EntityScene scene;

    private Lookup.Result<EntityScene> resultScene;
    private Lookup.Result<Entity> resultEntity;
    
    public NewFieldIdentifierAction() {
        this(Utilities.actionsGlobalContext());
        putValue(Action.NAME, "New Field Identifier");
    }

    public NewFieldIdentifierAction(Lookup lookup) {
        super(NbBundle.getMessage(NewFieldAction.class, "CTL_NewFieldIdentifierAction"));
        System.out.printf(">>> NewFieldIdentifierAction()\n");
        resultScene = lookup.lookupResult(EntityScene.class);
        resultScene.addLookupListener(this);
        resultChanged(new LookupEvent(resultScene));

        resultEntity = lookup.lookupResult(Entity.class);
        resultEntity.addLookupListener(this);
        resultChanged(new LookupEvent(resultEntity));

        setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        EntityField fieldIdentifier = new EntityField(entity, "ID", "long", true, "");
        // Java EE Specifics
        ProjectJavaEE projectJavaEE = (ProjectJavaEE)scene.getProject(ProjectJavaEE.class);
        if(projectJavaEE != null)
            projectJavaEE.add(fieldIdentifier, new Column(fieldIdentifier, "ID", fieldIdentifier.getLength(), false, "BIGINT", false, false, false));

        scene.addField(entity, fieldIdentifier); scene.validate();
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
        return new NewFieldIdentifierAction(lookup);
    }
    
}
