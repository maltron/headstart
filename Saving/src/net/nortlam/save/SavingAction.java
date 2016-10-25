package net.nortlam.save;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.nortlam.crudmaker.store.Persistence;
import net.nortlam.visual.EntityScene;
import org.openide.util.Lookup;

/**
 * @author Mauricio Leal */
public class SavingAction implements ActionListener {

    private EntityScene scene;
    private Lookup.Result<EntityScene> resultScene;
    
    public SavingAction(EntityScene scene) {
        this.scene = scene;
        System.out.printf(">>> SavingAction()\n");
    }
    
    // ACTION LISTENER ACTION LISTENER ACTION LISTENER ACTION LISTENER ACTION LISTENER 
    //  ACTION LISTENER ACTION LISTENER ACTION LISTENER ACTION LISTENER ACTION LISTENER 
    // ABSTRACT ACTION ABSTRACT ACTION ABSTRACT ACTION ABSTRACT ACTION ABSTRACT ACTION 
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.printf(">>> SavingAction.actionPerformed()\n");
        scene.beforeSaving();
        Persistence persistence = new Persistence(scene.getDocument(), scene.getProjects());
        persistence.save();
        scene.contentSaved();
    }    
}
