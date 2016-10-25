package net.nortlam.visual;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import net.nortlam.crudmaker.Project;
import net.nortlam.crudmaker.ProjectUtil;
import net.nortlam.crudmaker.design.DesignDocument;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.graph.GraphPinScene;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.vmd.VMDColorScheme;
import org.netbeans.api.visual.vmd.VMDConnectionWidget;
import org.netbeans.api.visual.vmd.VMDNodeWidget;
import org.netbeans.api.visual.vmd.VMDPinWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

import net.nortlam.crudmaker.design.Entity;
import net.nortlam.crudmaker.design.EntityConnection;
import net.nortlam.crudmaker.design.EntityField;
import net.nortlam.crudmaker.impl.ProjectJavaEE;
import net.nortlam.crudmaker.impl.ProjectJavaME;
import net.nortlam.crudmaker.impl.ProjectPlain;
import net.nortlam.visual.action.NewFieldAction;
import net.nortlam.visual.action.PanelConnection;
import net.nortlam.visual.action.PanelEntity;
import net.nortlam.visual.action.PanelField;
import org.netbeans.api.visual.action.EditProvider;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.graph.layout.GridGraphLayout;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.vmd.VMDFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.lookup.InstanceContent;

/**
 * @author Mauricio Leal */
//public class EntityScene extends GraphPinScene<String, String, String>
//                    implements ConnectProvider {
public class EntityScene extends GraphPinScene<Entity, EntityConnection, EntityField>
                    implements ConnectProvider, SelectProvider, EditProvider, PopupMenuProvider {
    private LayerWidget layerMain;
    private LayerWidget layerInter;
    private LayerWidget layerConnection;
    private LayerWidget layerBackground;

    private WidgetAction actionMove;
    private WidgetAction actionConnect;
    private WidgetAction actionSelect;
    private WidgetAction actionEdit;
    private WidgetAction actionPopUp;

    private Action actionOptionEdit;
    private Action actionOptionDelete;
    private Action actionOptionNewField;
    private JPopupMenu popupMenuEntity, popupMenuField, popupMenuConnection;

    private VMDColorScheme colorScheme = VMDFactory.getNetBeans60Scheme();

    // Not sure if we need this :(
    private List<Widget> listWidgets = new ArrayList<Widget>();

    private EntityConnection connection;

    private Object objectSelected;

    private SceneLayout sceneLayout;
    private Router router;

    private PanelEntity panelEntity;
    private PanelField panelField;
    private PanelConnection panelConnection;

//    private GeneratorFactory factory;
    private EntitySceneTopComponent topComponent;
    private InstanceContent content;
    
    // EACH EntityScene MUST content essentially 2 information:
    // DesignDocument (about Entities relationships) and 
    // Set<Project> which denotes number of Projects for this Project
    private DesignDocument document;
    private Set<Project> projects;
    private boolean isRedrawing = false; // Indicates that the Document must be redraw
    private ProjectUtil projectUtil;

    public EntityScene(EntitySceneTopComponent topComponent, InstanceContent content) {
        this.topComponent = topComponent;
        this.content = content;

        addChild(layerMain = new LayerWidget(this));
        addChild(layerInter = new LayerWidget(this));
        addChild(layerConnection = new LayerWidget(this));
        addChild(layerBackground = new LayerWidget(this));

        router = RouterFactory.createOrthogonalSearchRouter(
                new OrthogonalCollisionsCollector(layerMain, layerConnection));
        // Actions
        actionMove = ActionFactory.createMoveAction();
        actionConnect = ActionFactory.createConnectAction(layerInter, this);
        actionSelect = ActionFactory.createSelectAction(this, true);
        actionEdit = ActionFactory.createEditAction(this);
        actionPopUp = ActionFactory.createPopupMenuAction(this);

        InputMap inputMap = new InputMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, true), "deleteAction");

        actionOptionNewField = new NewFieldAction();
        actionOptionEdit = new EditAction();
        actionOptionDelete = new DeleteAction();
        popupMenuEntity = new JPopupMenu();
            popupMenuEntity.add(actionOptionNewField);
            popupMenuEntity.add(actionOptionEdit);
            popupMenuEntity.addSeparator();
            popupMenuEntity.add(actionOptionDelete);
        
        popupMenuField = new JPopupMenu();
            popupMenuField.add(actionOptionEdit);
            popupMenuField.addSeparator();
            popupMenuField.add(actionOptionDelete);

        popupMenuConnection = new JPopupMenu();
            popupMenuConnection.add(actionOptionEdit);
            popupMenuConnection.addSeparator();
            popupMenuConnection.add(actionOptionDelete);

        ActionMap actionMap = new ActionMap();
        actionMap.put("deleteAction", actionOptionDelete);

        getActions().addAction(ActionFactory.createZoomAction());
        getActions().addAction(ActionFactory.createWheelPanAction());
        getActions().addAction(ActionFactory.createActionMapAction(inputMap, actionMap));
//        getActions().addAction(actionSelect);
//        getActions().addAction(ActionFactory.createEditAction(this));

        sceneLayout = LayoutFactory.createSceneGraphLayout (this,
                     new GridGraphLayout<Entity, EntityConnection> ().setChecker (true));
        
        projectUtil = ProjectUtil.getInstance();
    }
    
    // TOP COMPONENT TOP COMPONENT TOP COMPONENT TOP COMPONENT TOP COMPONENT TOP COMPONENT 
    //  TOP COMPONENT TOP COMPONENT TOP COMPONENT TOP COMPONENT TOP COMPONENT TOP COMPONENT 
    
    public void contentChanged() {
        topComponent.contentChanged();
    }
    
    public void contentSaved() {
        topComponent.contentSaved();
    }
    
    public void beforeSaving() {
        // Do operations before saving the model
        
        // Get the coordinates of each Entity
        for(Entity entity: document.getEntities()) {
            Widget widget = findWidget(entity);
            if(widget != null) entity.setLocation(widget.getPreferredLocation());
        }
    }
    
    // MODEL MODEL MODEL MODEL MODEL MODEL MODEL MODEL MODEL MODEL MODEL MODEL MODEL 
    //  MODEL MODEL MODEL MODEL MODEL MODEL MODEL MODEL MODEL MODEL MODEL MODEL MODEL 
    
    public void setContent(DesignDocument document, Set<Project> projects) {
        this.document = document; this.projects = projects;
    }
    
    public DesignDocument getDocument() {
        return document;
    }
    
    public Set<Project> getProjects() {
        return projects;
    }
    
    public Project getProject(Class className) {
        return projectUtil.getProject(projects, className);
    }
    
    public boolean hasPlain() {
        return projectUtil.getProject(projects, ProjectPlain.class) != null;
    }
    
    public boolean hasJavaEE() {
        return projectUtil.getProject(projects, ProjectJavaEE.class) != null;
    }
    
    public boolean hasJavaME() {
        return projectUtil.getProject(projects, ProjectJavaME.class) != null;
    }

    // GRAPH SCENE GRAPH SCENE GRAPH SCENE GRAPH SCENE GRAPH SCENE GRAPH SCENE GRAPH
    //  GRAPH SCENE GRAPH SCENE GRAPH SCENE GRAPH SCENE GRAPH SCENE GRAPH SCENE GRAPH SCENE 

    public boolean isEntity(Object object) {
        return isNode(object);
    }

    public boolean isField(Object object) {
        return isPin(object);
    }

    public boolean isConnection(Object object) {
        return isEdge(object);
    }

    public boolean existEntityWithName(String name) {
        if(name == null) return false;

        boolean result = false;
        for(Entity entity: getNodes())
            if(result = entity.getName().equals(name)) break;

        return result;
    }

    public boolean existFieldWithNameInEntity(Entity entity, String name) {
        if(entity == null || name == null) return false;

        boolean result = false;
        for(EntityField field: entity.getFields())
            if(result = field.getName().equals(name)) break;

        return result;
    }

    public boolean exist(EntityConnection connection) {
        boolean result = false;

        for(EntityConnection c: getEdges())
            if(result = c.equals(connection)) break;

        return result;
    }

    public boolean exist(Entity entity) {
        boolean result = false;

        for(Entity e: getNodes())
            if(result = e.equals(entity)) break;

        return result;
    }

    public boolean exist(Entity entity, EntityField field) {
        boolean result = false;

        for(EntityField scan: getNodePins(entity))
            if(result = scan.equals(field)) break;

        return result;
    }

    public VMDNodeWidget addEntity(Entity entity) { // Entity == Node
        VMDNodeWidget widgetNode = (VMDNodeWidget)addNode(entity);

        // Adds also a EntityField Default (for Connection purposes)
        EntityField fieldDefault = new EntityField(entity);
        fieldDefault.setDefault(true);
        entity.setFieldDefault(fieldDefault);
        addField(entity, fieldDefault);

        return widgetNode;
    }

    public VMDPinWidget addField(Entity entity, EntityField field) { // Field == Pin
//        boolean valid = false;
//        // It must not be the same name
//        for(EntityField scan: getNodePins(entity))
//            if(valid = scan.equals(field)) break;

        return (VMDPinWidget)addPin(entity, field);
    }

    public VMDConnectionWidget addConnection(EntityConnection connection) { // Connection == Edge
        VMDConnectionWidget connectionWidget = (VMDConnectionWidget)addEdge(connection);
        
        // Type of Connection
        LabelWidget labelType = null, labelSource = null, labelTarget = null;
        if(connection.type() == EntityConnection.ONE_TO_ONE) {
            labelType = new LabelWidget(this, PanelConnection.PANEL_ONETOONE);
            labelSource = new LabelWidget(this, "1");
            labelTarget = new LabelWidget(this, "1");
        } else if(connection.type() == EntityConnection.MANY_TO_ONE) {
            labelType = new LabelWidget(this, PanelConnection.PANEL_MANYTOONE);
            labelSource = new LabelWidget(this, "*");
            labelTarget = new LabelWidget(this, "1");
        } else if(connection.type() == EntityConnection.ONE_TO_MANY) {
            labelType = new LabelWidget(this, PanelConnection.PANEL_ONETOMANY);
            labelSource = new LabelWidget(this, "1");
            labelTarget = new LabelWidget(this, "*");
        } else if(connection.type() == EntityConnection.MANY_TO_MANY) {
            labelType = new LabelWidget(this, PanelConnection.PANEL_MANYTOMANY);
            labelSource = new LabelWidget(this, "*");
            labelTarget = new LabelWidget(this, "*");
        }
        labelType.setOpaque(true); labelSource.setOpaque(true); labelTarget.setOpaque(true);
        connectionWidget.addChild(labelType);
        connectionWidget.setConstraint(labelType, 
                        LayoutFactory.ConnectionWidgetLayoutAlignment.CENTER, 0.5f);
        connectionWidget.addChild(labelSource);
        connectionWidget.setConstraint(labelSource,
                        LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_SOURCE, 10);
        connectionWidget.addChild(labelTarget);
        connectionWidget.setConstraint(labelTarget,
                        LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_TARGET, -10);
        
        return connectionWidget;
    }

    public Entity getEntityFromField(Object object) {
        if(!isField(object)) return null;

        return getPinNode((EntityField)object);
    }

    /**
     * Invokes layout of the scene.
     */
    public void layoutScene() {
        sceneLayout.invokeLayout ();
    }
    
    // CONNECT PROVIDER CONNECT PROVIDER CONNECT PROVIDER CONNECT PROVIDER CONNECT PROVIDER
    //  CONNECT PROVIDER CONNECT PROVIDER CONNECT PROVIDER CONNECT PROVIDER CONNECT PROVIDER

    @Override
    public boolean isSourceWidget(Widget widgetSource) {
        EntityField field = (EntityField)findObject(widgetSource);
        if(field == null) return false;

        connection = new EntityConnection(); 
        connection.setSource(field);
        return true;
    }

    @Override
    public ConnectorState isTargetWidget(Widget widgetSource, Widget widgetTarget) {
        Object source = findObject(widgetSource); Object target = findObject(widgetTarget);

        // Get all widgets back to their original state
        // except the one selected
        for(Widget widget: listWidgets) {
            if(widget == widgetSource) continue;

            widget.setState(ObjectState.createNormal());
        }

        // Both source and target cannot be null
        if(source == null || target == null) return ConnectorState.REJECT_AND_STOP;

        // One may not self-connect
        if(source == target) return ConnectorState.REJECT_AND_STOP;

        // Source MUST BE a Field
        if(!isField(source)) return ConnectorState.REJECT_AND_STOP;

        // Target MUST BE a Entity
        if(!isEntity(target)) return ConnectorState.REJECT_AND_STOP;

        // Avoid Field to be connect to its own Entity
        if(isField(source) && getEntityFromField(source).equals(target))
                                                return ConnectorState.REJECT_AND_STOP;
//        // From a PIN's target, it cannot
//        if(isPin(target) && getPinNode((String)target).equals((String)getPinNode((String)source)))
//                                            return ConnectorState.REJECT_AND_STOP;
        // A PIN cannot connect to other PIN
        if(isField(source) && isField(target)) return ConnectorState.REJECT_AND_STOP;

        connection.setTarget((Entity)target);
        // Is this connection already exist ?
        if(exist(connection)) return ConnectorState.REJECT_AND_STOP;

        widgetTarget.setState(widgetTarget.getState().deriveWidgetHovered(true));

        return ConnectorState.ACCEPT;
    }

    @Override
    public void createConnection(Widget widgetSource, Widget widgetTarget) {
        if(panelConnection == null) panelConnection = new PanelConnection(this);

        panelConnection.setConnection(connection);
        DialogDescriptor dialogConnection = new DialogDescriptor(panelConnection, "Connection",
                true, DialogDescriptor.OK_CANCEL_OPTION, null, null);
        Object result = DialogDisplayer.getDefault().notify(dialogConnection);
        if(result != null && result == DialogDescriptor.OK_OPTION) {
            panelConnection.updateConnection();
            
            addConnection(connection);
            setEdgeSource(connection, connection.getSource());
    //        setEdgeTarget(edgeID, isNode(connectionTarget) ?
    //                connectionTarget+VMDGraphScene.PIN_ID_DEFAULT_SUFFIX : connectionTarget);
            setEdgeTarget(connection, connection.getTarget().getFieldDefault());
        }
    }

    @Override
    public boolean hasCustomTargetWidgetResolver(Scene scene) {
        return false;
    }

    @Override
    public Widget resolveTargetWidget(Scene scene, Point sceneLocation) {
        return null;
    }
    
    // GRAPH PIN SCENE GRAPH PIN SCENE GRAPH PIN SCENE GRAPH PIN SCENE GRAPH PIN SCENE 
    //   GRAPH PIN SCENE GRAPH PIN SCENE GRAPH PIN SCENE GRAPH PIN SCENE GRAPH PIN SCENE
    /**
     * Get the elements of all objects and redraw each one */
    public void redraw() {
        isRedrawing = true; // It will avoid Concurrency Issues
        
        // Entities and their Fields
        for(Entity entity: document.getEntities()) {
            addEntity(entity);
            for(EntityField field: entity.getFields())
                addField(entity, field);
        }
        
        // Connections
        if(document.getConnections() != null) {
            System.out.printf(">>> EntityScene.redraw() %d Connections\n", document.getConnections().size());
            for(EntityConnection connection: document.getConnections()) {
                addConnection(connection);
                setEdgeSource(connection, connection.getSource());
                setEdgeTarget(connection, connection.getTarget().getFieldDefault());
            }
        }
        
        isRedrawing = false;
    }

    @Override
    protected Widget attachNodeWidget(Entity entity) {
        System.out.printf(">>> EntityScene.attachNodeWidget()\n");
        if(!isRedrawing) {
            document.add(entity); contentChanged();
        } // ADDED Entity

        VMDNodeWidget widgetNode = new VMDNodeWidget(this, colorScheme);
        widgetNode.setNodeName(entity.getName());
        if(entity.getLocation() != null) 
            widgetNode.setPreferredLocation(entity.getLocation());;
        
        // Actions

        widgetNode.getHeader().getActions().addAction(createObjectHoverAction());
        widgetNode.getActions().addAction(actionSelect);
        widgetNode.getActions().addAction(actionEdit);
        widgetNode.getActions().addAction(createSelectAction());
        widgetNode.getActions().addAction(actionMove);
        widgetNode.getActions().addAction(actionPopUp);

        layerMain.addChild(widgetNode); listWidgets.add(widgetNode);
        return widgetNode;
    }

    @Override
    protected Widget attachPinWidget(Entity entity, EntityField field) {
        System.out.printf(">>> EntityScene.attachPinWidget()\n");
        if(field.isDefault()) return null;

        if(!isRedrawing) {
            // ADDED EntityField
            entity.add(field); contentChanged();
        } 
        
        VMDPinWidget widgetPin = new VMDPinWidget(this, colorScheme);
        widgetPin.setPinName(field.getName());
        //if(pin.isIdentifier()) widgetPin.setFont(widgetPin.getFont().deriveFont(Font.BOLD));
        ((VMDNodeWidget)findWidget(entity)).attachPinWidget(widgetPin);
        
        // Actions
        widgetPin.getActions().addAction(createObjectHoverAction());
        widgetPin.getActions().addAction(actionSelect);
        widgetPin.getActions().addAction(actionEdit);
        widgetPin.getActions().addAction(createSelectAction());
        widgetPin.getActions().addAction(actionConnect);
        widgetPin.getActions().addAction(actionPopUp);

        listWidgets.add(widgetPin);
        return widgetPin;
    }

    @Override
    protected Widget attachEdgeWidget(EntityConnection connection) {
        System.out.printf(">>> EntityScene.attachEdgeWidget()\n");
        if(!isRedrawing) {
            // ADDED EntityConnection
            document.add(connection); contentChanged();
        } 
        
        VMDConnectionWidget widgetConnection = new VMDConnectionWidget(this, colorScheme);
        widgetConnection.setRouter(router);

//        LabelWidget widgetLabel = new LabelWidget(this, edgeID);
//        connection.setConstraint(widgetLabel, ConnectionWidgetLayoutAlignment.CENTER, 0.5f);

        // Actions
        widgetConnection.getActions().addAction(createObjectHoverAction());
        widgetConnection.getActions().addAction(actionSelect);
        widgetConnection.getActions().addAction(actionEdit);
        widgetConnection.getActions().addAction(createSelectAction());
        widgetConnection.getActions().addAction(actionPopUp);

        layerConnection.addChild(widgetConnection);
        return widgetConnection;
    }

    @Override
    protected void attachEdgeSourceAnchor(EntityConnection edge, EntityField oldSourcePin, EntityField sourcePin) {
        ((ConnectionWidget)findWidget(edge)).setSourceAnchor(getPinAnchor(sourcePin));
    }

    @Override
    protected void attachEdgeTargetAnchor(EntityConnection edge, EntityField oldTargetPin, EntityField targetPin) {
        ((ConnectionWidget)findWidget(edge)).setTargetAnchor(getPinAnchor(targetPin));
    }

    private Anchor getPinAnchor(EntityField pin) {
        if (pin == null) return null;

        VMDNodeWidget nodeWidget = (VMDNodeWidget)findWidget(getPinNode(pin));
        Widget pinMainWidget = findWidget(pin);
        Anchor anchor;
        if (pinMainWidget != null) {
            anchor = AnchorFactory.createDirectionalAnchor(pinMainWidget, AnchorFactory.DirectionalAnchorKind.HORIZONTAL, 8);
            anchor = nodeWidget.createAnchorPin(anchor);
        } else anchor = nodeWidget.getNodeAnchor();

        return anchor;
    }
    
    // EDIT PROVIDER EDIT PROVIDER EDIT PROVIDER EDIT PROVIDER EDIT PROVIDER EDIT PROVIDER 
    //  EDIT PROVIDER EDIT PROVIDER EDIT PROVIDER EDIT PROVIDER EDIT PROVIDER EDIT PROVIDER

    @Override
    public void edit(Widget widget) {
        editWidget(widget);
    }

    private void editWidget(Widget widget) {
        System.out.printf(">>> EntityScene.editWidget()\n");
        Object object = findObject(widget);

        // Entity
        if(isEntity(object)) {
            Entity entity = (Entity)object;

            if(panelEntity == null) panelEntity = new PanelEntity(this);
            DialogDescriptor dialogEntity = new DialogDescriptor(panelEntity, "Entity",
                    true, DialogDescriptor.OK_CANCEL_OPTION, null, null);
            panelEntity.setDialogDescritor(dialogEntity);
            panelEntity.setEntity(entity);

            Object result = DialogDisplayer.getDefault().notify(dialogEntity);
            if(result != null && result == DialogDescriptor.OK_OPTION) {
                panelEntity.updateEntity();

                VMDNodeWidget nodeWidget = (VMDNodeWidget)widget;
                nodeWidget.setNodeName(entity.getName());
                validate(); contentChanged();
            }

        // Entity Field
        } else if(isField(object)) {
            EntityField field = (EntityField)object;

            if(panelField == null) panelField = new PanelField(this);
            DialogDescriptor dialogField = new DialogDescriptor(panelField, "Field",
                    true, DialogDescriptor.OK_CANCEL_OPTION, null, null);
            panelField.setDialogDescriptor(dialogField);
            panelField.setField(field);

            Object result = DialogDisplayer.getDefault().notify(dialogField);
            if(result != null && result == DialogDescriptor.OK_OPTION) {
                panelField.updateField();

                VMDPinWidget pinWidget = (VMDPinWidget)widget;
                pinWidget.setPinName(field.getName());
                validate(); contentChanged();
            }
            
        // Entity Connection
        } else if(isConnection(object)) {
            System.out.printf(">>> EntityScene.editWidget() CONNECTION IS SELECTED\n");
            EntityConnection connection = (EntityConnection)object;
            
            if(panelConnection == null) panelConnection = new PanelConnection(this);
            panelConnection.setConnection(connection);
            DialogDescriptor dialogConnection = new DialogDescriptor(panelConnection, "Connection",
                    true, DialogDescriptor.OK_CANCEL_OPTION, null, null);
            Object result = DialogDisplayer.getDefault().notify(dialogConnection);
            if(result != null && result == DialogDescriptor.OK_OPTION) {
                panelConnection.updateConnection();

//                addConnection(connection);
//                setEdgeSource(connection, connection.getSource());
//        //        setEdgeTarget(edgeID, isNode(connectionTarget) ?
//        //                connectionTarget+VMDGraphScene.PIN_ID_DEFAULT_SUFFIX : connectionTarget);
//                setEdgeTarget(connection, connection.getTarget().getFieldDefault());
            }
        }
    }

    private class EditAction extends AbstractAction {

        public EditAction() {
            putValue(Action.NAME, "Edit...");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(objectSelected == null) return;

            editWidget(findWidget(objectSelected));
        }

    }

    // SELECT PROVIDER SELECT PROVIDER SELECT PROVIDER SELECT PROVIDER SELECT PROVIDER
    //  SELECT PROVIDER SELECT PROVIDER SELECT PROVIDER SELECT PROVIDER SELECT PROVIDER

    @Override
    public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
        return false;
    }

    @Override
    public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
        return true;
    }

    @Override
    public void select(Widget widget, Point localLocation, boolean invertSelection) {
        System.out.printf(">>> MyScene3.select()\n");

        List objectsForLookup = new ArrayList();
        objectsForLookup.add(this);

        objectSelected = findObject(widget);
        if(isConnection(objectSelected))
            objectsForLookup.add((EntityConnection)objectSelected);
        else if(isEntity(objectSelected))
            objectsForLookup.add((Entity)objectSelected);
        else if(isField(objectSelected))
            objectsForLookup.add((EntityField)objectSelected);
        else objectSelected = null;
        
        content.set(objectsForLookup, null);
    }

    private class DeleteAction extends AbstractAction {

        public DeleteAction() {
            putValue(Action.NAME, "Delete");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            removeObjectSelected();
        }
    }

    private void removeObjectSelected() {
        // TODO: This method must be smart removing objects from Scene and from DesignDocument
        //       at the same time, so both will have the EXACTLY same content

        if(isConnection(objectSelected)) {
            System.out.printf(">>> removeObjectSelected() EntityConnection has been selected\n");
            EntityConnection connection = (EntityConnection)objectSelected;
            removeEdge(connection); document.remove(connection); contentChanged();

        } else if(isEntity(objectSelected)) {
            System.out.printf(">>> removeObjectSelected() Entity has been selected\n");
            Entity entity = (Entity)objectSelected;
            EntityConnection connection = null;
            for(EntityConnection scan: getEdges())
                if(scan.getTarget().equals(entity)) {
                    connection = scan; break;
                }

            if(connection != null) removeEdge(connection);
            listWidgets.remove(findWidget(objectSelected));
            removeNode(entity); document.remove(connection); contentChanged();

        } else if(isField(objectSelected)) {
            System.out.printf(">>> removeObjectSelected() EntityField has been selected\n");
            EntityField field = (EntityField)objectSelected;
            EntityConnection connection = null;
            for(EntityConnection scan: getEdges())
                if(scan.getSource().equals(field)) {
                    connection = scan; break;
                }

            if(connection != null) removeEdge(connection);
            listWidgets.remove(findWidget(objectSelected));
            removePin(field); contentChanged();

        } else {
            System.out.printf(">>> removeObjectSelected() NOTHING HAS BEING SELECTED\n");
        }
    }

    // POPUP MENU PROVIDER POPUP MENU PROVIDER POPUP MENU PROVIDER POPUP MENU PROVIDER
    //  POPUP MENU PROVIDER POPUP MENU PROVIDER POPUP MENU PROVIDER POPUP MENU PROVIDER

    @Override
    public JPopupMenu getPopupMenu(Widget widget, Point point) {
        System.out.printf(">>> EntityScene.getPopupMenu()\n");
        Object object = findObject(widget);
        if(isEntity(object)) return popupMenuEntity;
        else if(isConnection(object)) return popupMenuConnection;

        return popupMenuField;
    }

}
