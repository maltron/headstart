package net.nortlam.visual;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import net.nortlam.crudmaker.Project;
import net.nortlam.crudmaker.ProjectUtil;
import net.nortlam.crudmaker.design.DesignDocument;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

@ConvertAsProperties(dtd = "-//net.nortlam.visual//EntityScene//EN", autostore = false)
public final class EntitySceneTopComponent extends TopComponent {
    
    private static final String NEW_CONTENT_INDICATOR = " *";
    private String title;

    private static EntitySceneTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "EntitySceneTopComponent";

    private InstanceContent ic = new InstanceContent();
//    private GeneratorFactory generatorFactory;
    private EntityScene scene;

//    private static Map<DesignDocument, EntitySceneTopComponent> 
//            mapTopComponent = new HashMap<DesignDocument, EntitySceneTopComponent>();

    public EntitySceneTopComponent() {
        initComponents();
        //setName(NbBundle.getMessage(EntitySceneTopComponent.class, "CTL_EntitySceneTopComponent"));
        //setToolTipText(NbBundle.getMessage(EntitySceneTopComponent.class, "HINT_POJODesignTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

        associateLookup(new AbstractLookup(ic));
    }

    public void setNewDocument(DesignDocument document, Set<Project> projects) {
//    public void setNewDocument(String projectName, GeneratorFactory generatorFactory) {
//        this.generatorFactory = generatorFactory;
        this.title = ProjectUtil.getInstance().getName(projects); 
        contentChanged();
        
//        scene.setFactory(generatorFactory);
        scene.setContent(document, projects);
        ic.add(scene); // Available throught Lookup
    }
    
    /**
     * After loading all components, redraw all of them */
    public void redraw() {
        scene.redraw();
    }
    
    public void contentChanged() {
        setName(title.concat(NEW_CONTENT_INDICATOR));
    }
    
    public void contentSaved() {
        setName(title);
    }

    private void initComponents() {
        scene = new EntityScene(this, ic);
        JComponent component = scene.createView();
        JScrollPane scrollPane = new JScrollPane(component,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized EntitySceneTopComponent getDefault() {
        if (instance == null) {
            instance = new EntitySceneTopComponent();
        }
        return instance;
    }
    
//    public static synchronized void addInstance(DesignDocument document, EntitySceneTopComponent topComponent) {
//        mapTopComponent.put(document, topComponent);
//    }
//    
//    public static synchronized EntitySceneTopComponent findInstance(DesignDocument document) {
//        return mapTopComponent.get(document);
//    }

//    public static synchronized POJODesignTopComponent findInstance(DesignDocument document) {
//        POJODesignTopComponent topComponent = mapTopComponent.get(document);
//        if(topComponent == null) { // It doesn't exist yet
//            //topComponent = new POJODesignTopComponent();
//            mapTopComponent.put(document, this);
//        }
//
//        return topComponent;
//    }

    /**
     * Obtain the EntitySceneTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized EntitySceneTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(EntitySceneTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof EntitySceneTopComponent) {
            return (EntitySceneTopComponent) win;
        }
        Logger.getLogger(EntitySceneTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        //return TopComponent.PERSISTENCE_ALWAYS;
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }
}
