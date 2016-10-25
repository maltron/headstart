package net.nortlam.visual.action;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.nortlam.crudmaker.GeneratorFactory;
import net.nortlam.visual.EntityScene;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public final class GenerateAction implements ActionListener {

    private final EntityScene scene;

    public GenerateAction(EntityScene scene) {
        this.scene = scene;
    }

    public void actionPerformed(ActionEvent ev) {
        NotifyDescriptor message = new NotifyDescriptor("Generate ?", "Confirmation", 
                NotifyDescriptor.OK_CANCEL_OPTION, NotifyDescriptor.QUESTION_MESSAGE,
                null, null);
        Object result = DialogDisplayer.getDefault().notify(message);
        if(result != null && result == NotifyDescriptor.OK_OPTION) {
//            GeneratorFactory factory = scene.getFactory();
            GeneratorFactory factory = new GeneratorFactory(scene.getDocument(), scene.getProjects());
            factory.init();
            factory.generate();
        }
    }
}
