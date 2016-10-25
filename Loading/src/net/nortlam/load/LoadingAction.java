package net.nortlam.load;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;
import net.nortlam.crudmaker.ProjectUtil;
import net.nortlam.crudmaker.store.Persistence;
import net.nortlam.visual.EntitySceneTopComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.windows.WindowManager;

public final class LoadingAction implements ActionListener {
    
    private Persistence persistence;
    private JFileChooser fileChooser;
    private ProjectUtil projectUtil;
    
    public LoadingAction() {
        System.out.printf(">>> LoadingAction()\n");
        projectUtil = ProjectUtil.getInstance();
    }
    

    public void actionPerformed(ActionEvent e) {
        if(persistence == null) persistence = new Persistence();
        
        // Is there any files to open ?
        if(!projectUtil.isThereAnyFiles()) {
            NotifyDescriptor message = new NotifyDescriptor(
                    "There are no files to be open", "Open...", 
                    NotifyDescriptor.DEFAULT_OPTION, 
                    NotifyDescriptor.ERROR_MESSAGE, null, null);
            DialogDisplayer.getDefault().notify(message);
            return;
        }
        
        if(fileChooser == null) {
            fileChooser = new JFileChooser(projectUtil.getBaseDir());
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new ProjectUtil.ProjectFilter());
        }
        
        int value = fileChooser.showOpenDialog(
                                    WindowManager.getDefault().getMainWindow());
        if(value == JFileChooser.APPROVE_OPTION) {
            try {
                persistence.load(fileChooser.getSelectedFile());

                // Create a new EntityScene and open
                EntitySceneTopComponent topComponent = new EntitySceneTopComponent();
                topComponent.setNewDocument(persistence.getDocument(), persistence.getProjects());
                topComponent.open();
                topComponent.requestActive();
                topComponent.redraw();
                
            } catch(IOException ex) {
                System.err.printf("### LoadAction.actionPerformed() IO EXCEPTION:%s\n", ex.getMessage());
            } catch(JAXBException ex) {
                System.err.printf("### LoadAction.actionPerformed() JAXB EXCEPTION:%s\n", ex.getMessage());
            }
        }
        
    }

//    // FILE FILTER FILE FILTER FILE FILTER FILE FILTER FILE FILTER FILE FILTER 
//    //  FILE FILTER FILE FILTER FILE FILTER FILE FILTER FILE FILTER FILE FILTER 
//
//    @Override
//    public String getDescription() {
//        return Persistence.DEFAULT_DESCRIPTION;
//    }
//    
//    @Override
//    public boolean accept(File pathname) {
//        return filter.accept(new File(projectUtil.getBaseDir(), pathname));
//        boolean valid = true;
//        for(String ending: Persistence.PROJECTS_ENDING)
//            if(pathname.toString().contains(ending)) {
//                // Found something not allowed
//                valid = false; break;
//            }
//        
//        return valid;
//    }
}