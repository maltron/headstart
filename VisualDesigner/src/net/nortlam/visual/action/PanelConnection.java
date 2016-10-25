package net.nortlam.visual.action;

import net.nortlam.crudmaker.impl.javaee.column.AssociationTable;
import net.nortlam.crudmaker.impl.javaee.column.ColumnManyToMany;
import net.nortlam.crudmaker.impl.javaee.column.ColumnOneToMany;
import net.nortlam.crudmaker.impl.javaee.column.ColumnManyToOne;
import net.nortlam.crudmaker.impl.javaee.TableColumn;
import net.nortlam.crudmaker.impl.javaee.column.Column;
import net.nortlam.crudmaker.design.EntityField;
import net.nortlam.crudmaker.impl.ProjectJavaEE;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import net.nortlam.crudmaker.design.EntityConnection;
import net.nortlam.crudmaker.impl.javaee.Cascade;
import net.nortlam.crudmaker.impl.javaee.Fetch;
import net.nortlam.crudmaker.impl.javaee.column.Approach;
import net.nortlam.crudmaker.impl.javaee.column.ColumnOneToOne;
import net.nortlam.visual.EntityScene;

import static net.nortlam.visual.action.FormUtil.createForm;

/**
 * @author Mauricio Leal */
public class PanelConnection extends JPanel {

    private JToggleButton toggleOneToOne, toggleManyToOne,
                                          toggleOneToMany, toggleManyToMany;
    private JToggleButton[] buttonSelection;
    
    // Table Association
//    private JLabel labelTableSource, labelPKSource;
//    private JTextField textTableSource, textPKSource;
//    private JLabel labelJoinTable, labelJoinPKSource, labelJoinPKTarget;
//    private JTextField textJoinTable, textJoinPKSource, textJoinPKTarget;
//    private JLabel labelTableTarget, labelPKTarget;
//    private JTextField textTableTarget, textPKTarget;
    
    private JPanel panelApproach;
    private CardLayout layoutApproach;
    public static final String PANEL_ONETOONE = "@One-To-One";
    public static final String PANEL_MANYTOONE = "@Many-To-One";
    public static final String PANEL_ONETOMANY = "@One-To-Many";
    public static final String PANEL_MANYTOMANY = "@Many-To-Many";
    
    private Border borderEtched = BorderFactory.createEtchedBorder();
    private Border borderEmpty = BorderFactory.createEmptyBorder(5, 5, 5, 5);

    private JCheckBox checkBidirectional;
    private JCheckBox checkCascadeAll, 
     checkCascadePersist, checkCascadeMerge, checkCascadeRemove,
     checkCascadeRefresh;
    private JRadioButton radioEager, radioLazy;
    
    // ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE 
    //  ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE 
    private JLabel labelTableSource_OneToOne, labelPKSource_OneToOne;
    private JTextField textTableSource_OneToOne, textPKSource_OneToOne;
    private JLabel labelJoinTable_OneToOne, labelJoinPKSource_OneToOne, labelJoinPKTarget_OneToOne;
    private JTextField textJoinTable_OneToOne, textJoinPKSource_OneToOne, textJoinPKTarget_OneToOne;
    private JLabel labelTableTarget_OneToOne, labelPKTarget_OneToOne;
    private JTextField textTableTarget_OneToOne, textPKTarget_OneToOne;
    private JToggleButton toggleOneToOne_FK, toggleOneToOne_PK, toggleOneToOne_Association;
    private JToggleButton[] toggleOneToOne_Selection;
    
    private JLabel labelOneToOne_FK_Table, labelOneToOne_FK_FK;
    private JTextField textOneToOne_FK_Table;
    private JTextField textOneToOne_FK_FK;
        
    private JLabel labelOneToOne_PK_Table;
    private JLabel labelOneToOne_PK_Up;
    private JLabel labelOneToOne_PK_Down;
    private JTextField textOneToOne_PK_Table;
    private JTextField textOneToOne_PK_Up;
    private JTextField textOneToOne_PK_Down;
    
    private ComponentSelection selectionOneToOne;
    
    // MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE 
    //  MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE 
    private JLabel labelTableSource_ManyToOne, labelPKSource_ManyToOne;
    private JTextField textTableSource_ManyToOne, textPKSource_ManyToOne;
    private JLabel labelJoinTable_ManyToOne, labelJoinPKSource_ManyToOne, labelJoinPKTarget_ManyToOne;
    private JTextField textJoinTable_ManyToOne, textJoinPKSource_ManyToOne, textJoinPKTarget_ManyToOne;
    private JLabel labelTableTarget_ManyToOne, labelPKTarget_ManyToOne;
    private JTextField textTableTarget_ManyToOne, textPKTarget_ManyToOne;
    private JToggleButton toggleColumn_ManyToOne, toggleTable_ManyToOne;
    private JLabel labelTable_ManyToOne;
    private JTextField textTable_ManyToOne;
    private JLabel labelColumn_ManyToOne;
    private JTextField textColumn_ManyToOne;
    private JLabel labelTargetName_ManyToOne;
    private JTextField textTargetName_ManyToOne;
    
    private ComponentSelection selectionManyToOne;
    
    // ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY 
    //   ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY 
    private JLabel labelTableSource_OneToMany, labelPKSource_OneToMany;
    private JTextField textTableSource_OneToMany, textPKSource_OneToMany;
    private JLabel labelJoinTable_OneToMany, labelJoinPKSource_OneToMany, labelJoinPKTarget_OneToMany;
    private JTextField textJoinTable_OneToMany, textJoinPKSource_OneToMany, textJoinPKTarget_OneToMany;
    private JLabel labelTableTarget_OneToMany, labelPKTarget_OneToMany;
    private JTextField textTableTarget_OneToMany, textPKTarget_OneToMany;
    private JToggleButton toggleColumn_OneToMany, toggleTable_OneToMany;
    private JLabel labelTable_OneToMany;
    private JTextField textTable_OneToMany;
    private JLabel labelColumn_OneToMany;
    private JTextField textColumn_OneToMany;
    private JLabel labelTargetName_OneToMany;
    private JTextField textTargetName_OneToMany;
    
    private ComponentSelection selectionOneToMany;
    
    // MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY 
    //  MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY 
    private JLabel labelTableSource_ManyToMany, labelPKSource_ManyToMany;
    private JTextField textTableSource_ManyToMany, textPKSource_ManyToMany;
    private JLabel labelJoinTable_ManyToMany, labelJoinPKSource_ManyToMany, labelJoinPKTarget_ManyToMany;
    private JTextField textJoinTable_ManyToMany, textJoinPKSource_ManyToMany, textJoinPKTarget_ManyToMany;
    private JLabel labelTableTarget_ManyToMany, labelPKTarget_ManyToMany;
    private JTextField textTableTarget_ManyToMany, textPKTarget_ManyToMany;
    private JLabel labelTargetName_ManyToMany;
    private JTextField textTargetName_ManyToMany;
    
    private TitledBorder borderSource, borderTarget;
    
    private EntityScene scene;
    private boolean hasJavaEE;
    private EntityConnection connection;

    private ProjectJavaEE projectJavaEE;
    private String tableSource, tableTarget;
    private EntityField fieldIDSource, fieldIDTarget;
    
    private ToggleButtonSelection toggleButtonSelection;

    public PanelConnection(EntityScene scene) {
        this.scene = scene;

        System.out.printf(">>> PanelConnection()\n");
        initComponents();
    }

    public void setConnection(EntityConnection connection) {
        System.out.printf(">>> PanelConnection.setConnection()\n");
        this.connection = connection;
        projectJavaEE = (ProjectJavaEE)scene.getProject(ProjectJavaEE.class);
        hasJavaEE = projectJavaEE != null;
        
        borderSource.setTitle(connection.getSource().getParent().getName());
        borderTarget.setTitle(connection.getTarget().getName());
        
        textTargetName_ManyToOne.setText(connection.getTargetName());
        textTargetName_OneToMany.setText(connection.getTargetName());
        textTargetName_ManyToMany.setText(connection.getTargetName());
        
        checkBidirectional.setSelected(connection.isBidirectional());
        
        // Java EE ?
        if(hasJavaEE) {
            tableSource = projectJavaEE.getTableName(connection.getSource().getParent());
            tableTarget = projectJavaEE.getTableName(connection.getTarget());
            fieldIDSource = connection.getSource().getParent().getFieldIdentifier();
            fieldIDTarget = connection.getTarget().getFieldIdentifier();

            TableColumn column = projectJavaEE.getColumn(connection.getSource());
            if(column != null) {
                // Fetch
                if(column.getFetch() != null) {
                    radioEager.setSelected(column.getFetch() == Fetch.EAGER);
                    radioLazy.setSelected(column.getFetch() == Fetch.LAZY);
                }
                // Cascade
                checkCascadeAll.setSelected(column.isCascade(Cascade.ALL));
                checkCascadePersist.setSelected(column.isCascade(Cascade.PERSIST));
                checkCascadeMerge.setSelected(column.isCascade(Cascade.MERGE));
                checkCascadeRemove.setSelected(column.isCascade(Cascade.REMOVE));
                checkCascadeRefresh.setSelected(column.isCascade(Cascade.REFRESH));
                
                // Default Cascade ????
                // If none of Cascade has being selected, select just Cascade.ALL
                if(!column.isCascade(Cascade.ALL) && !column.isCascade(Cascade.PERSIST) &&
                        !column.isCascade(Cascade.MERGE) && !column.isCascade(Cascade.REMOVE) &&
                        !column.isCascade(Cascade.REFRESH)) checkCascadeAll.setSelected(true);
                
                // Approach
                if(column instanceof ColumnOneToOne && 
                        column.getApproach() == Approach.FOREIGN_KEY) {
                    enableOneToOneFK();
                } else if(column instanceof ColumnOneToOne &&
                        column.getApproach() == Approach.SHARED_PRIMARY_KEY) {
                    enableOneToOnePK();
                } else if(column instanceof ColumnOneToOne &&
                        column.getApproach() == Approach.ASSOCIATE_TABLE) {
                    enableOneToOneATable();
                } else if(column instanceof ColumnManyToOne &&
                        column.getApproach() == Approach.REFERENCE_COLUMN) {
                    enableManyToOneReference();
                } else if(column instanceof ColumnManyToOne &&
                        column.getApproach() == Approach.ASSOCIATE_TABLE) {
                    enableManyToOneATable();
                } else if(column instanceof ColumnOneToMany &&
                        column.getApproach() == Approach.REFERENCE_COLUMN) {
                    enableOneToManyReference();
                } else if(column instanceof ColumnOneToMany &&
                        column.getApproach() == Approach.ASSOCIATE_TABLE) {
                    enableOneToManyATable();
                } else if(column instanceof ColumnManyToMany) {
                    enableManyToMany();
                    
                // DEFAULT: OneToOne_FK (if nothing has being set)
                } else enableOneToOneFK();
            }
        }
        
        // Update Cascade behaviour
        // If only Cascade.ALL is active, then everything else should be disable
        if(checkCascadeAll.isSelected()) {
            checkCascadePersist.setSelected(true);
            checkCascadeMerge.setSelected(true);
            checkCascadeRemove.setSelected(true);
            checkCascadeRefresh.setSelected(true);
            // ... and disabled
            checkCascadePersist.setEnabled(false);
            checkCascadeMerge.setEnabled(false);
            checkCascadeRemove.setEnabled(false);
            checkCascadeRefresh.setEnabled(false);
        }
        
    }

    public void updateConnection() {
        connection.setBidirectional(checkBidirectional.isSelected());
        
        // Type ?
        if(toggleOneToOne.isSelected()) connection.setType(EntityConnection.ONE_TO_ONE);
        else if(toggleManyToOne.isSelected()) connection.setType(EntityConnection.MANY_TO_ONE);
        else if(toggleOneToMany.isSelected()) connection.setType(EntityConnection.ONE_TO_MANY);
        else if(toggleManyToMany.isSelected()) connection.setType(EntityConnection.MANY_TO_MANY);
        
        if(hasJavaEE) {
            // One-To-One
            if(toggleOneToOne.isSelected()) {
                ColumnOneToOne columnOneToOne = new ColumnOneToOne(connection.getSource());
                if(toggleOneToOne_FK.isSelected()) {
                    columnOneToOne.setApproach(Approach.FOREIGN_KEY);
                    columnOneToOne.setJoinColumn(textOneToOne_FK_FK.getText());
                    
                } else if(toggleOneToOne_PK.isSelected()) {
                    columnOneToOne.setApproach(Approach.SHARED_PRIMARY_KEY);
                    
                } else if(toggleOneToOne_Association.isSelected()) {
                    columnOneToOne.setApproach(Approach.ASSOCIATE_TABLE);
//                updateAssociationTable(textTableSource_OneToOne, textPKSource_OneToOne,
//                        textJoinTable_OneToOne, textJoinPKSource_OneToOne, textJoinPKTarget_OneToOne,
//                        textTableTarget_OneToOne, textPKTarget_OneToOne);
                    AssociationTable aTable = new AssociationTable(
                            textJoinTable_OneToOne.getText(), 
                            // Join Column
                            textJoinPKSource_OneToOne.getText(), textPKSource_OneToOne.getText(), 
                            // Inverse Column
                            textJoinPKTarget_OneToOne.getText(), textPKTarget_OneToOne.getText());
                    columnOneToOne.setAssociationTable(aTable);
                }
                setFetch(columnOneToOne);
                setCascade(columnOneToOne);
                
                projectJavaEE.add(connection.getSource(), columnOneToOne);
            // Many-To-One
            } else if(toggleManyToOne.isSelected()) {
                ColumnManyToOne columnManyToOne = new ColumnManyToOne(connection.getSource());
                
                if(toggleColumn_ManyToOne.isSelected()) {
                    columnManyToOne.setApproach(Approach.REFERENCE_COLUMN);
                    columnManyToOne.setReferenceColumn(textColumn_ManyToOne.getText());
                    
                } else if(toggleTable_ManyToOne.isSelected()) {
                    AssociationTable aTable = new AssociationTable(
                            textJoinTable_ManyToOne.getText(), 
                            // Join Column
                            textJoinPKSource_ManyToOne.getText(), textPKSource_ManyToOne.getText(), 
                            // Inverse Column
                            textJoinPKTarget_ManyToOne.getText(), textPKTarget_ManyToOne.getText());
                    columnManyToOne.setApproach(Approach.ASSOCIATE_TABLE);
                    columnManyToOne.setAssociationTable(aTable);
                }
                setFetch(columnManyToOne);
                setCascade(columnManyToOne);
                
                projectJavaEE.add(connection.getSource(), columnManyToOne);
            // One-To-Many
            } else if(toggleOneToMany.isSelected()) {
                ColumnOneToMany columnOneToMany = new ColumnOneToMany(connection.getSource());
                
                if(toggleColumn_OneToMany.isSelected()) {
                    columnOneToMany.setApproach(Approach.REFERENCE_COLUMN);
                    columnOneToMany.setReferenceColumn(textColumn_OneToMany.getText());
                    
                } else if(toggleTable_OneToMany.isSelected()) {
                    columnOneToMany.setApproach(Approach.ASSOCIATE_TABLE);
                    AssociationTable aTable = new AssociationTable(
                            textJoinTable_OneToMany.getText(), 
                            // Join Column
                            textJoinPKSource_OneToMany.getText(), textPKSource_OneToMany.getText(), 
                            // Inverse Column
                            textJoinPKTarget_OneToMany.getText(), textPKTarget_OneToMany.getText());
                    columnOneToMany.setAssociationTable(aTable);
                }
                setFetch(columnOneToMany);
                setCascade(columnOneToMany);
                
                projectJavaEE.add(connection.getSource(), columnOneToMany);
                
            // Many-To-Many
            } else if(toggleManyToMany.isSelected()) {
                AssociationTable aTable = new AssociationTable(
                        textJoinTable_ManyToMany.getText(), 
                        // Join Column
                        textJoinPKSource_ManyToMany.getText(), textPKSource_ManyToMany.getText(), 
                        // Inverse Column
                        textJoinPKTarget_ManyToMany.getText(), textPKTarget_ManyToMany.getText());
                ColumnManyToMany columnManyToMany = new ColumnManyToMany(connection.getSource(), 
                                                                    Approach.ASSOCIATE_TABLE, aTable);
                setFetch(columnManyToMany);
                setCascade(columnManyToMany);

                projectJavaEE.add(connection.getSource(), columnManyToMany);
            }
        }        
    }
    
    private void setFetch(TableColumn column) {
        column.setFetch(radioEager.isSelected() ? Fetch.EAGER : Fetch.LAZY);
    }
    
    private void setCascade(TableColumn column) {
        column.resetCascade();
        
        if(checkCascadeAll.isSelected()) {
            column.addCascade(Cascade.ALL);
            return;
        }
        
        if(checkCascadePersist.isSelected()) column.addCascade(Cascade.PERSIST);
        if(checkCascadeMerge.isSelected()) column.addCascade(Cascade.MERGE);
        if(checkCascadeRemove.isSelected()) column.addCascade(Cascade.REMOVE);
        if(checkCascadeRefresh.isSelected()) column.addCascade(Cascade.REFRESH);
    }

    private void setToggleImage(JToggleButton button, String image, String text) {
        button.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(image))));
        button.setText(text);
        button.setHorizontalTextPosition(JToggleButton.CENTER);
        button.setVerticalTextPosition(JToggleButton.BOTTOM);
    }
    
    private void initComponents() {
        System.out.printf(">>> PanelConnection.initComponents()\n");
        
        toggleOneToOne = new JToggleButton(); //modifyToggle(toggleOneToOne);
        setToggleImage(toggleOneToOne, "/net/nortlam/visual/action/image-one2one-tiny.gif", PANEL_ONETOONE);
        toggleManyToOne = new JToggleButton(); //modifyToggle(toggleManyToOne);
        setToggleImage(toggleManyToOne, "/net/nortlam/visual/action/image-many2one-tiny.gif", PANEL_MANYTOONE);
        toggleOneToMany = new JToggleButton(); //modifyToggle(toggleOneToMany);
        setToggleImage(toggleOneToMany, "/net/nortlam/visual/action/image-one2many-tiny.gif", PANEL_ONETOMANY);
        toggleManyToMany = new JToggleButton(); //modifyToggle(toggleManyToMany);
        setToggleImage(toggleManyToMany, "/net/nortlam/visual/action/image-many2many-tiny.gif", PANEL_MANYTOMANY);
        buttonSelection = new JToggleButton[] {
            toggleOneToOne, toggleManyToOne, toggleOneToMany, toggleManyToMany
        };
        
        //ToggleButtonSelection selection = new ToggleButtonSelection();
        SwitchApproach switchApproach = new SwitchApproach();
        ComponentSelection majorComponentSelection = new ComponentSelection(buttonSelection, null);
//        ToggleButtonSelection selection = 
//                                new ToggleButtonSelection(buttonSelection, null);
        toggleOneToOne.addActionListener(switchApproach);
        toggleOneToOne.addActionListener(majorComponentSelection);
        toggleManyToOne.addActionListener(switchApproach);
        toggleManyToOne.addActionListener(majorComponentSelection);
        toggleOneToMany.addActionListener(switchApproach);
        toggleOneToMany.addActionListener(majorComponentSelection);
        toggleManyToMany.addActionListener(switchApproach);
        toggleManyToMany.addActionListener(majorComponentSelection);
        
        //toggleOneToOne.setSelected(true); // Default Selection
        
        checkBidirectional = new JCheckBox("Bidirectional");
        
        checkCascadeAll = new JCheckBox("ALL");
        checkCascadeAll.addActionListener(new CascadeAll());
        checkCascadePersist = new JCheckBox("PERSIST");
        checkCascadeMerge = new JCheckBox("MERGE");
        checkCascadeRemove = new JCheckBox("REMOVE");
        checkCascadeRefresh = new JCheckBox("REFRESH");
        JPanel panelCascade = new JPanel(new GridLayout(1, 5));
        panelCascade.setBorder(BorderFactory.createTitledBorder(borderEtched, "Cascade"));
        panelCascade.add(checkCascadeAll);
        panelCascade.add(checkCascadePersist);
        panelCascade.add(checkCascadeMerge);
        panelCascade.add(checkCascadeRemove);
        panelCascade.add(checkCascadeRefresh);

        radioEager = new JRadioButton("Eager");
        radioLazy = new JRadioButton("Lazy");
        ButtonGroup group = new ButtonGroup();
        group.add(radioEager);
        group.add(radioLazy);
        radioEager.setSelected(true);
        
        borderSource = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "");
        borderTarget = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "");
        
        toggleButtonSelection = new ToggleButtonSelection();

        JPanel panelStrategy = new JPanel(new GridLayout(1,2));
        panelStrategy.setBorder(BorderFactory.createTitledBorder(borderEtched, "Fetch"));
        panelStrategy.add(radioEager);
        panelStrategy.add(radioLazy);

//        JPanel mainPanel = new JPanel(new BorderLayout(5,5));
        setLayout(new BorderLayout(5,5));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            
            // Approach in each selection
            panelApproach = new JPanel();
            panelApproach.setLayout(layoutApproach = new CardLayout());
            panelApproach.add(panelOneToOne(), PANEL_ONETOONE);
            panelApproach.add(panelManyToOne(), PANEL_MANYTOONE);
            panelApproach.add(panelOneToMany(), PANEL_ONETOMANY);
            panelApproach.add(panelManyToMany(), PANEL_MANYTOMANY);
            
        add(panelApproach, BorderLayout.CENTER);

            JPanel panelButtons = createTogglePanel();
            panelButtons.setBorder(BorderFactory.createLoweredBevelBorder());
        add(panelButtons, BorderLayout.WEST);
        
            JPanel panelSouth = new JPanel();
            GridBagLayout layoutSouth = new GridBagLayout();
            
            GridBagConstraints gcSouth = new GridBagConstraints();
            
            gcSouth.gridx = 0; gcSouth.gridy = 0; gcSouth.gridwidth = 1; gcSouth.gridheight = 1;
            gcSouth.anchor = GridBagConstraints.WEST; gcSouth.fill = GridBagConstraints.NONE;
            gcSouth.weightx = 1.0; gcSouth.weighty = 1.0;
            layoutSouth.setConstraints(checkBidirectional, gcSouth);
            panelSouth.add(checkBidirectional);
            
            gcSouth.gridx++;
            gcSouth.anchor = GridBagConstraints.SOUTH; gcSouth.fill = GridBagConstraints.HORIZONTAL;
            gcSouth.weightx = 1.0;
            layoutSouth.setConstraints(panelStrategy, gcSouth);
            panelSouth.add(panelStrategy);
            
            gcSouth.gridx++;
            gcSouth.anchor = GridBagConstraints.SOUTHEAST;
            layoutSouth.setConstraints(panelCascade, gcSouth);
            panelSouth.add(panelCascade);
        add(panelSouth, BorderLayout.SOUTH);
    }
    
    private JPanel createTogglePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
            JPanel panelButtons = new JPanel(new GridLayout(4, 1, 0, 5));
            panelButtons.add(toggleOneToOne);
            panelButtons.add(toggleManyToOne);
            panelButtons.add(toggleOneToMany);
            panelButtons.add(toggleManyToMany);
        panel.add(panelButtons, BorderLayout.NORTH);
        
        return panel;
    }
    
    private JPanel panelOneToOne() {
        labelTableSource_OneToOne = new JLabel("Table");
        labelPKSource_OneToOne = new JLabel("PK");
        textTableSource_OneToOne = new JTextField();
        textPKSource_OneToOne = new JTextField();
        labelJoinTable_OneToOne = new JLabel("Join Table");
        labelJoinPKSource_OneToOne = new JLabel("PK");
        labelJoinPKTarget_OneToOne = new JLabel("PK");
        
        textJoinTable_OneToOne = new JTextField(); textJoinTable_OneToOne.setColumns(12);
        textJoinPKSource_OneToOne = new JTextField(); textJoinPKSource_OneToOne.setColumns(12);
        textJoinPKTarget_OneToOne = new JTextField(); textJoinPKTarget_OneToOne.setColumns(12);
        
        labelTableTarget_OneToOne = new JLabel("Table");
        labelPKTarget_OneToOne = new JLabel("PK");
        textTableTarget_OneToOne = new JTextField();
        textPKTarget_OneToOne = new JTextField();
        
//        JoinTableSuggestion joinTableSuggestion = 
//                new JoinTableSuggestion(textTableSource_OneToOne, 
//                                textTableTarget_OneToOne, textJoinTable_OneToOne);
//        textTableSource_OneToOne.getDocument().addDocumentListener(joinTableSuggestion);
//        textTableTarget_OneToOne.getDocument().addDocumentListener(joinTableSuggestion);
        
//        SetPK pkSource = new SetPK(textPKSource_OneToOne, textJoinPKSource_OneToOne);
//        textPKSource_OneToOne.getDocument().addDocumentListener(pkSource);
//        
//        SetPK pkTarget = new SetPK(textPKTarget_OneToOne, textJoinPKTarget_OneToOne);
//        textPKTarget_OneToOne.getDocument().addDocumentListener(pkTarget);

        toggleOneToOne_FK = new JToggleButton("Foreign Key");
        toggleOneToOne_PK = new JToggleButton("Shared Primary Key");
        toggleOneToOne_Association = new JToggleButton("Association Table");
        toggleOneToOne_Selection = new JToggleButton[] {
            toggleOneToOne_FK, toggleOneToOne_PK, toggleOneToOne_Association
        };
        
        labelOneToOne_FK_Table = new JLabel("Table");
        labelOneToOne_FK_FK = new JLabel("FK");
        textOneToOne_FK_Table = new JTextField(); textOneToOne_FK_Table.setEditable(false);
        textOneToOne_FK_FK = new JTextField();  textOneToOne_FK_FK.setEditable(true);
        
        labelOneToOne_PK_Table = new JLabel("Table");
        labelOneToOne_PK_Up = new JLabel("PK");
        labelOneToOne_PK_Down = new JLabel("PK");
        textOneToOne_PK_Table = new JTextField(); textOneToOne_PK_Table.setEditable(false);
        textOneToOne_PK_Up = new JTextField(); textOneToOne_PK_Up.setEditable(false);
        textOneToOne_PK_Down = new JTextField(); textOneToOne_PK_Down.setEditable(false);
        
        selectionOneToOne = new ComponentSelection(
//        ToggleButtonSelection selection = new ToggleButtonSelection(
                toggleOneToOne_Selection, new JComponent[][] {
                    {labelOneToOne_FK_Table, labelOneToOne_FK_FK, textOneToOne_FK_Table, textOneToOne_FK_FK },
                    {labelOneToOne_PK_Table, labelOneToOne_PK_Up, labelOneToOne_PK_Down, textOneToOne_PK_Table, textOneToOne_PK_Up, textOneToOne_PK_Down },
                    {labelTableSource_OneToOne, labelPKSource_OneToOne, textTableSource_OneToOne, textPKSource_OneToOne,
                     labelJoinTable_OneToOne, labelJoinPKSource_OneToOne, labelJoinPKTarget_OneToOne, textJoinTable_OneToOne, textJoinPKSource_OneToOne, textJoinPKTarget_OneToOne, 
                     labelTableTarget_OneToOne, labelPKTarget_OneToOne, textTableTarget_OneToOne, textPKTarget_OneToOne}
                });
        disable(new JComponent[] 
                    //{labelOneToOne_FK_Table, labelOneToOne_FK_FK, textOneToOne_FK_Table, textOneToOne_FK_FK, 
                    {labelOneToOne_PK_Table, labelOneToOne_PK_Up, labelOneToOne_PK_Down, textOneToOne_PK_Table, textOneToOne_PK_Up, textOneToOne_PK_Down, 
                    labelTableSource_OneToOne, labelPKSource_OneToOne, textTableSource_OneToOne, textPKSource_OneToOne,
                     labelJoinTable_OneToOne, labelJoinPKSource_OneToOne, labelJoinPKTarget_OneToOne, textJoinTable_OneToOne, textJoinPKSource_OneToOne, textJoinPKTarget_OneToOne, 
                     labelTableTarget_OneToOne, labelPKTarget_OneToOne, textTableTarget_OneToOne, textPKTarget_OneToOne});

        toggleOneToOne_FK.addActionListener(toggleButtonSelection);
        toggleOneToOne_PK.addActionListener(toggleButtonSelection);
        toggleOneToOne_Association.addActionListener(toggleButtonSelection);
        toggleOneToOne_FK.setSelected(true);
        
        JPanel panel = new JPanel(new GridLayout(1, 3));
        
            // By Foreign Key
            JPanel panelFK = new JPanel(new BorderLayout(5,5));
            panelFK.setBorder(borderEtched);
                JPanel panelToggleFK = new JPanel(new BorderLayout());
                panelToggleFK.setBorder(borderEmpty);
                panelToggleFK.add(toggleOneToOne_FK, BorderLayout.NORTH);
            panelFK.add(panelToggleFK, BorderLayout.NORTH);
            panelFK.add(createForm(new JLabel[] {labelOneToOne_FK_Table, labelOneToOne_FK_FK},
                    new JTextField[] {textOneToOne_FK_Table, textOneToOne_FK_FK}),
                    BorderLayout.CENTER);
        panel.add(panelFK);
        // By Sharing Primary Key
            JPanel panelPK = new JPanel(new BorderLayout(5,5));
                JPanel panelTogglePK = new JPanel(new BorderLayout());
                panelTogglePK.setBorder(borderEmpty);
                panelTogglePK.add(toggleOneToOne_PK, BorderLayout.NORTH);
            panelPK.add(panelTogglePK, BorderLayout.NORTH);
            panelPK.setBorder(borderEtched);
            panelPK.add(createForm(new JLabel[] {labelOneToOne_PK_Table, labelOneToOne_PK_Up, labelOneToOne_PK_Down},
                    new JTextField[] {textOneToOne_PK_Table, textOneToOne_PK_Up, textOneToOne_PK_Down}),
                    BorderLayout.CENTER);
        panel.add(panelPK);
        // By Association Table
            JPanel panelSharedTable = new JPanel(new BorderLayout());
            panelSharedTable.setBorder(borderEtched);
                JPanel panelToggleTable = new JPanel(new BorderLayout());
                panelToggleTable.setBorder(borderEmpty);
                panelToggleTable.add(toggleOneToOne_Association, BorderLayout.NORTH);
            panelSharedTable.add(panelToggleTable, BorderLayout.NORTH);
                JPanel panelTable = new JPanel(new GridLayout(3, 1));
                panelTable.add(createTablePanel(borderSource, true, labelTableSource_OneToOne, textTableSource_OneToOne, labelPKSource_OneToOne, textPKSource_OneToOne));
                panelTable.add(createConnectionTablePanel(labelJoinTable_OneToOne, textJoinTable_OneToOne, labelJoinPKSource_OneToOne, textJoinPKSource_OneToOne, labelJoinPKTarget_OneToOne, textJoinPKTarget_OneToOne));
                panelTable.add(createTablePanel(borderTarget, false, labelTableTarget_OneToOne, textTableTarget_OneToOne, labelPKTarget_OneToOne, textPKTarget_OneToOne));
            panelSharedTable.add(panelTable, BorderLayout.CENTER);
        panel.add(panelSharedTable);
        
        return panel;
    }
    
    private JPanel panelManyToOne() {
        labelTableSource_ManyToOne = new JLabel("Table");
        labelPKSource_ManyToOne = new JLabel("PK");
        textTableSource_ManyToOne = new JTextField();
        textPKSource_ManyToOne = new JTextField();
        labelJoinTable_ManyToOne = new JLabel("Join Table");
        labelJoinPKSource_ManyToOne = new JLabel("PK");
        labelJoinPKTarget_ManyToOne = new JLabel("PK");
        textJoinTable_ManyToOne = new JTextField();
        textJoinPKSource_ManyToOne = new JTextField();
        textJoinPKTarget_ManyToOne = new JTextField();
        labelTableTarget_ManyToOne = new JLabel("Table");
        labelPKTarget_ManyToOne = new JLabel("PK");
        textTableTarget_ManyToOne = new JTextField();
        textPKTarget_ManyToOne = new JTextField();
        
        labelTargetName_ManyToOne = new JLabel("Target Name");
        textTargetName_ManyToOne = new JTextField();
        
//        JoinTableSuggestion joinTableSuggestion = 
//                new JoinTableSuggestion(textTableSource_ManyToOne, 
//                                textTableTarget_ManyToOne, textJoinTable_ManyToOne);
//        textTableSource_ManyToOne.getDocument().addDocumentListener(joinTableSuggestion);
//        textTableTarget_ManyToOne.getDocument().addDocumentListener(joinTableSuggestion);
        
//        SetPK pkSource = new SetPK(textPKSource_ManyToOne, textJoinPKSource_ManyToOne);
//        textPKSource_ManyToOne.getDocument().addDocumentListener(pkSource);
//        
//        SetPK pkTarget = new SetPK(textPKTarget_ManyToOne, textJoinPKTarget_ManyToOne);
//        textPKTarget_ManyToOne.getDocument().addDocumentListener(pkTarget);

        labelTable_ManyToOne = new JLabel("Table");
        textTable_ManyToOne = new JTextField(); textTable_ManyToOne.setEditable(false);
        labelColumn_ManyToOne = new JLabel("Column");
        textColumn_ManyToOne = new JTextField();
        
        toggleColumn_ManyToOne = new JToggleButton("Reference Column");
        toggleTable_ManyToOne = new JToggleButton("Association Table");
        
        selectionManyToOne = new ComponentSelection(
//        ToggleButtonSelection selection = new ToggleButtonSelection(
                new JToggleButton[] {toggleColumn_ManyToOne, toggleTable_ManyToOne},
                new JComponent[][] {
                    {labelTable_ManyToOne, labelColumn_ManyToOne, textTable_ManyToOne, textColumn_ManyToOne} ,
                    {labelTableSource_ManyToOne, labelPKSource_ManyToOne, textTableSource_ManyToOne, textPKSource_ManyToOne,
                     labelJoinTable_ManyToOne, labelJoinPKSource_ManyToOne, labelJoinPKTarget_ManyToOne, textJoinTable_ManyToOne, textJoinPKSource_ManyToOne, textJoinPKTarget_ManyToOne, 
                     labelTableTarget_ManyToOne, labelPKTarget_ManyToOne, textTableTarget_ManyToOne, textPKTarget_ManyToOne}
                });
        
        toggleColumn_ManyToOne.addActionListener(toggleButtonSelection);
        toggleTable_ManyToOne.addActionListener(toggleButtonSelection);
        
        disable(new JComponent[] {
            labelTableSource_ManyToOne, labelPKSource_ManyToOne, textTableSource_ManyToOne, textPKSource_ManyToOne,
             labelJoinTable_ManyToOne, labelJoinPKSource_ManyToOne, labelJoinPKTarget_ManyToOne, textJoinTable_ManyToOne, textJoinPKSource_ManyToOne, textJoinPKTarget_ManyToOne, 
             labelTableTarget_ManyToOne, labelPKTarget_ManyToOne, textTableTarget_ManyToOne, textPKTarget_ManyToOne
        });
        toggleColumn_ManyToOne.setSelected(true);

        JPanel mainPanel = new JPanel(new BorderLayout(5,5));
        mainPanel.add(createForm(labelTargetName_ManyToOne, textTargetName_ManyToOne),
                BorderLayout.NORTH);
        
            JPanel panel = new JPanel(new GridLayout(1, 2));

                JPanel panelColumn = new JPanel(new BorderLayout(5,5));
                panelColumn.setBorder(borderEmpty);
                panelColumn.setBorder(borderEtched);
                    JPanel panelColumnButton = new JPanel(new BorderLayout());
                    panelColumnButton.setBorder(borderEmpty);
                    panelColumnButton.add(toggleColumn_ManyToOne, BorderLayout.NORTH);
                panelColumn.add(panelColumnButton, BorderLayout.NORTH);
                panelColumn.add(
                        createForm(new JLabel[] {labelTable_ManyToOne, labelColumn_ManyToOne}, 
                                new JTextField[] {textTable_ManyToOne, textColumn_ManyToOne}), 
                                                                BorderLayout.CENTER);
            panel.add(panelColumn);

            // By Association Table
                JPanel panelSharedTable = new JPanel(new BorderLayout(5,5));
                panelSharedTable.setBorder(borderEtched);
                    JPanel panelToggleTable = new JPanel(new BorderLayout());
                    panelToggleTable.setBorder(borderEmpty);
                    panelToggleTable.add(toggleTable_ManyToOne, BorderLayout.NORTH);
                panelSharedTable.add(panelToggleTable, BorderLayout.NORTH);
                    JPanel panelTable = new JPanel(new GridLayout(3, 1));
                    panelTable.add(createTablePanel(borderSource, true, labelTableSource_ManyToOne, textTableSource_ManyToOne, labelPKSource_ManyToOne, textPKSource_ManyToOne));
                    panelTable.add(createConnectionTablePanel(labelJoinTable_ManyToOne, textJoinTable_ManyToOne, labelJoinPKSource_ManyToOne, textJoinPKSource_ManyToOne, labelJoinPKTarget_ManyToOne, textJoinPKTarget_ManyToOne));
                    panelTable.add(createTablePanel(borderTarget, false, labelTableTarget_ManyToOne, textTableTarget_ManyToOne, labelPKTarget_ManyToOne, textPKTarget_ManyToOne));
                panelSharedTable.add(panelTable, BorderLayout.CENTER);
            panel.add(panelSharedTable);
        mainPanel.add(panel, BorderLayout.CENTER);
        
        return mainPanel;
    }
    

    private JPanel panelOneToMany() {
        labelTableSource_OneToMany = new JLabel("Table");
        labelPKSource_OneToMany = new JLabel("PK");
        textTableSource_OneToMany = new JTextField();
        textPKSource_OneToMany = new JTextField();
        labelJoinTable_OneToMany = new JLabel("Join Table");
        labelJoinPKSource_OneToMany = new JLabel("PK");
        labelJoinPKTarget_OneToMany = new JLabel("PK");
        textJoinTable_OneToMany = new JTextField();
        textJoinPKSource_OneToMany = new JTextField();
        textJoinPKTarget_OneToMany = new JTextField();
        labelTableTarget_OneToMany = new JLabel("Table");
        labelPKTarget_OneToMany = new JLabel("PK");
        textTableTarget_OneToMany = new JTextField();
        textPKTarget_OneToMany = new JTextField();

        labelTargetName_OneToMany = new JLabel("Target Name");
        textTargetName_OneToMany = new JTextField();
        
//        JoinTableSuggestion joinTableSuggestion = 
//                new JoinTableSuggestion(textTableSource_OneToMany, 
//                                textTableTarget_OneToMany, textJoinTable_OneToMany);
//        textTableSource_OneToMany.getDocument().addDocumentListener(joinTableSuggestion);
//        textTableTarget_OneToMany.getDocument().addDocumentListener(joinTableSuggestion);
        
//        SetPK pkSource = new SetPK(textPKSource_OneToMany, textJoinPKSource_OneToMany);
//        textPKSource_OneToMany.getDocument().addDocumentListener(pkSource);
//        
//        SetPK pkTarget = new SetPK(textPKTarget_OneToMany, textJoinPKTarget_OneToMany);
//        textPKTarget_OneToMany.getDocument().addDocumentListener(pkTarget);

        toggleColumn_OneToMany = new JToggleButton("Reference Column");
        toggleTable_OneToMany = new JToggleButton("Association Table");
        
        labelTable_OneToMany =new JLabel("Table");
        textTable_OneToMany = new JTextField();
        labelColumn_OneToMany = new JLabel("Column");
        textColumn_OneToMany = new JTextField();

        selectionOneToMany = new ComponentSelection(
//        ToggleButtonSelection selection = new ToggleButtonSelection(
                new JToggleButton[] {toggleColumn_OneToMany, toggleTable_OneToMany},
                new JComponent[][] {
                    {labelTable_OneToMany, labelColumn_OneToMany, textTable_OneToMany, textColumn_OneToMany} ,
                    {labelTableSource_OneToMany, labelPKSource_OneToMany, textTableSource_OneToMany, textPKSource_OneToMany,
                     labelJoinTable_OneToMany, labelJoinPKSource_OneToMany, labelJoinPKTarget_OneToMany, textJoinTable_OneToMany, textJoinPKSource_OneToMany, textJoinPKTarget_OneToMany, 
                     labelTableTarget_OneToMany, labelPKTarget_OneToMany, textTableTarget_OneToMany, textPKTarget_OneToMany}
                });
        
        toggleColumn_OneToMany.addActionListener(toggleButtonSelection);
        toggleTable_OneToMany.addActionListener(toggleButtonSelection);

        disable(new JComponent[] {
            labelTableSource_OneToMany, labelPKSource_OneToMany, textTableSource_OneToMany, textPKSource_OneToMany,
             labelJoinTable_OneToMany, labelJoinPKSource_OneToMany, labelJoinPKTarget_OneToMany, textJoinTable_OneToMany, textJoinPKSource_OneToMany, textJoinPKTarget_OneToMany, 
             labelTableTarget_OneToMany, labelPKTarget_OneToMany, textTableTarget_OneToMany, textPKTarget_OneToMany
        });
        toggleColumn_OneToMany.setSelected(true);
        
        JPanel mainPanel = new JPanel(new BorderLayout(5,5));
        mainPanel.add(createForm(labelTargetName_OneToMany, textTargetName_OneToMany),
                BorderLayout.NORTH);
        
            JPanel panel = new JPanel(new GridLayout(1, 2));

                JPanel panelColumn = new JPanel(new BorderLayout(5,5));
                panelColumn.setBorder(borderEmpty);
                panelColumn.setBorder(borderEtched);
                    JPanel panelColumnButton = new JPanel(new BorderLayout());
                    panelColumnButton.setBorder(borderEmpty);
                    panelColumnButton.add(toggleColumn_OneToMany, BorderLayout.NORTH);
                panelColumn.add(panelColumnButton, BorderLayout.NORTH);
                panelColumn.add(
                        createForm(new JLabel[] {labelTable_OneToMany, labelColumn_OneToMany}, 
                                new JTextField[] {textTable_OneToMany, textColumn_OneToMany}), 
                                                                BorderLayout.CENTER);
            panel.add(panelColumn);

            // By Association Table
                JPanel panelSharedTable = new JPanel(new BorderLayout(5,5));
                panelSharedTable.setBorder(borderEtched);
                    JPanel panelToggleTable = new JPanel(new BorderLayout());
                    panelToggleTable.setBorder(borderEmpty);
                    panelToggleTable.add(toggleTable_OneToMany, BorderLayout.NORTH);
                panelSharedTable.add(panelToggleTable, BorderLayout.NORTH);
                    JPanel panelTable = new JPanel(new GridLayout(3, 1));
                    panelTable.add(createTablePanel(borderSource, true, labelTableSource_OneToMany, textTableSource_OneToMany, labelPKSource_OneToMany, textPKSource_OneToMany));
                    panelTable.add(createConnectionTablePanel(labelJoinTable_OneToMany, textJoinTable_OneToMany, labelJoinPKSource_OneToMany, textJoinPKSource_OneToMany, labelJoinPKTarget_OneToMany, textJoinPKTarget_OneToMany));
                    panelTable.add(createTablePanel(borderTarget, false, labelTableTarget_OneToMany, textTableTarget_OneToMany, labelPKTarget_OneToMany, textPKTarget_OneToMany));
                panelSharedTable.add(panelTable, BorderLayout.CENTER);
            panel.add(panelSharedTable);
        mainPanel.add(panel, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private JPanel panelManyToMany() {
        labelTableSource_ManyToMany = new JLabel("Table");
        labelPKSource_ManyToMany = new JLabel("PK");
        textTableSource_ManyToMany = new JTextField();
        textPKSource_ManyToMany = new JTextField();
        labelJoinTable_ManyToMany = new JLabel("Join Table");
        labelJoinPKSource_ManyToMany = new JLabel("PK");
        labelJoinPKTarget_ManyToMany = new JLabel("PK");
        textJoinTable_ManyToMany = new JTextField();
        textJoinPKSource_ManyToMany = new JTextField();
        textJoinPKTarget_ManyToMany = new JTextField();
        labelTableTarget_ManyToMany = new JLabel("Table");
        labelPKTarget_ManyToMany = new JLabel("PK");
        textTableTarget_ManyToMany = new JTextField();
        textPKTarget_ManyToMany = new JTextField();

        labelTargetName_ManyToMany = new JLabel("Target Name");
        textTargetName_ManyToMany = new JTextField();
        
//        JoinTableSuggestion joinTableSuggestion = 
//                new JoinTableSuggestion(textTableSource_ManyToMany, 
//                                textTableTarget_ManyToMany, textJoinTable_ManyToMany);
//        textTableSource_ManyToMany.getDocument().addDocumentListener(joinTableSuggestion);
//        textTableTarget_ManyToMany.getDocument().addDocumentListener(joinTableSuggestion);
        
//        SetPK pkSource = new SetPK(textPKSource_ManyToMany, textJoinPKSource_ManyToMany);
//        textPKSource_ManyToMany.getDocument().addDocumentListener(pkSource);
        
//        SetPK pkTarget = new SetPK(textPKTarget_ManyToMany, textJoinPKTarget_ManyToMany);
//        textPKTarget_ManyToMany.getDocument().addDocumentListener(pkTarget);

        JToggleButton toggleTable = new JToggleButton("Association Table");
        toggleTable.setSelected(true);
        toggleTable.setEnabled(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout(5,5));
        mainPanel.add(createForm(labelTargetName_ManyToMany, textTargetName_ManyToMany),
                BorderLayout.NORTH);
        
            // By Association Table
            JPanel panelSharedTable = new JPanel(new BorderLayout(5,5));
            panelSharedTable.setBorder(borderEtched);
                JPanel panelToggleTable = new JPanel(new BorderLayout());
                panelToggleTable.setBorder(borderEmpty);
                panelToggleTable.add(toggleTable, BorderLayout.NORTH);
            panelSharedTable.add(panelToggleTable, BorderLayout.NORTH);
                JPanel panelTable = new JPanel(new GridLayout(3, 1));
                panelTable.add(createTablePanel(borderSource, true, labelTableSource_ManyToMany, textTableSource_ManyToMany, labelPKSource_ManyToMany, textPKSource_ManyToMany));
                panelTable.add(createConnectionTablePanel(labelJoinTable_ManyToMany, textJoinTable_ManyToMany, labelJoinPKSource_ManyToMany, textJoinPKSource_ManyToMany, labelJoinPKTarget_ManyToMany, textJoinPKTarget_ManyToMany));
                panelTable.add(createTablePanel(borderTarget, false, labelTableTarget_ManyToMany, textTableTarget_ManyToMany, labelPKTarget_ManyToMany, textPKTarget_ManyToMany));
            panelSharedTable.add(panelTable, BorderLayout.CENTER);
            
        mainPanel.add(panelSharedTable, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private JPanel createTablePanel(TitledBorder border, boolean top, 
            JLabel labelTableName, JTextField textTableName, 
            JLabel labelPK, JTextField textPK) {
        JPanel panel = createForm(new JLabel[] {labelTableName, labelPK}, 
                                  new JTextField[] {textTableName, textPK});
        if(top) {
            border.setTitleJustification(TitledBorder.LEFT);
            border.setTitlePosition(TitledBorder.TOP);
        }else{    
            border.setTitleJustification(TitledBorder.RIGHT);
            border.setTitlePosition(TitledBorder.BOTTOM);
        }
        panel.setBorder(border);
//        panel.setBorder(BorderFactory.createTitledBorder(
//                BorderFactory.createEtchedBorder(), title, 
//                    top ? TitledBorder.LEFT : TitledBorder.RIGHT, 
//                    top ? TitledBorder.TOP : TitledBorder.BOTTOM));
        
        return panel;
    }
    
    private JPanel createConnectionTablePanel(JLabel labelConnect, JTextField textConnect,
            JLabel labelPKUp, JTextField textPKUp, 
            JLabel labelPKDown, JTextField textPKDown) {
        
        JPanel panel = createForm(new JLabel[] {labelConnect, labelPKUp, labelPKDown},
                new JTextField[] {textConnect, textPKUp, textPKDown});
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        return panel;
    }
    
    private void reset() {
        resetTextField(textTableSource_OneToOne, textPKSource_OneToOne, 
            textOneToOne_PK_Table, textOneToOne_PK_Up, textOneToOne_PK_Down,
            textJoinTable_OneToOne, textJoinPKSource_OneToOne, textJoinPKTarget_OneToOne,
            textTableTarget_OneToOne, textPKTarget_OneToOne,
            textOneToOne_FK_Table, textOneToOne_FK_FK,
            textOneToOne_PK_Table, textOneToOne_PK_Up, textOneToOne_PK_Down);
        resetTextField(textTableSource_ManyToOne, textPKSource_ManyToOne,
            textJoinTable_ManyToOne, textJoinPKSource_ManyToOne, textJoinPKTarget_ManyToOne,
            textTableTarget_ManyToOne, textPKTarget_ManyToOne,
            textTable_ManyToOne, textColumn_ManyToOne, textTargetName_ManyToOne);
        resetTextField(textTableSource_OneToMany, textPKSource_OneToMany,
            textJoinTable_OneToMany, textJoinPKSource_OneToMany, textJoinPKTarget_OneToMany,
            textTableTarget_OneToMany, textPKTarget_OneToMany, 
            textTable_OneToMany, textColumn_OneToMany, textTargetName_OneToMany);
        resetTextField(textTableSource_ManyToMany, textPKSource_ManyToMany,
            textJoinTable_ManyToMany, textJoinPKSource_ManyToMany, textJoinPKTarget_ManyToMany,
            textTableTarget_ManyToMany, textPKTarget_ManyToMany, textTargetName_ManyToMany);
    }
    
    private void resetTextField(JTextField ... field) {
        for(int i=0; i < field.length; i++) field[i].setText("");
    }
    
    private void updateAssociationTable(JTextField textSourceTable, JTextField textSourcePK,
            JTextField textJoinTable, JTextField textJoinPKSource, JTextField textJoinPKTarget,
            JTextField textTargetTable, JTextField textTargetPK) {
        updateAssociationTable(textSourceTable, textSourcePK, 
                textJoinTable, textJoinPKSource, textJoinPKTarget, 
                textTargetTable, textTargetPK, null);
    }

    private void updateAssociationTable(JTextField textSourceTable, JTextField textSourcePK,
            JTextField textJoinTable, JTextField textJoinPKSource, JTextField textJoinPKTarget,
            JTextField textTargetTable, JTextField textTargetPK, AssociationTable associationTable) {

        textSourceTable.setEditable(false);
        textSourcePK.setEditable(false);
        textTargetTable.setEditable(false);
        textTargetPK.setEditable(false);
        
        textSourceTable.setText(tableSource);
        textTargetTable.setText(tableTarget);
        
        if(associationTable != null) {
            // Fill information based on previous request
            textSourcePK.setText(associationTable.getJoinColumnReferenceColumn());
            
            textJoinTable.setText(associationTable.getJoinTableName());
            textJoinPKSource.setText(associationTable.getJoinColumn());
            textJoinPKTarget.setText(associationTable.getInverseColumn());
            
            textTargetPK.setText(associationTable.getInverseColumnReferenceColumn());
        } else {
            // Suggestion
            String source = null, target = null;

            if(fieldIDSource != null) {
                Column column = (Column)projectJavaEE.getColumn(fieldIDSource);
                source = column.getName();
                textSourcePK.setText(source);
            }
            if(fieldIDTarget != null) {
                Column column = (Column)projectJavaEE.getColumn(fieldIDTarget);
                target = column.getName();
                textTargetPK.setText(target);
            }

            textJoinTable.setText(tableSource.concat("_HAS_").concat(tableTarget));
            if(source != null) textJoinPKSource.setText(tableSource.concat("_".concat(source)));
            if(target != null) textJoinPKTarget.setText(tableTarget.concat("_".concat(target)));
        }

        textJoinTable.selectAll();
        textJoinTable.requestFocus();
    }
    
    private void enableToggle(JToggleButton button, JToggleButton ... disable) {
        button.setSelected(true);
        for(int i=0; i < disable.length; i++) disable[i].setSelected(false);
    }
    
    private class ComponentSelection implements ActionListener {
        
        private JToggleButton[] selection;
        private JComponent[][] components;
        
        public ComponentSelection(JToggleButton[] selection, JComponent[][] components) {
            this.selection = selection; this.components = components;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            select((JToggleButton)e.getSource());
        }
        
        public void select(JToggleButton button) {
            if(selection != null) {
                int count = 0;
                for(JToggleButton scan: selection) {
                    boolean state = (scan == button);
                    scan.setSelected(state);
                    if(components != null) enable(state, components[count++]);
                }
            }
        }

        private void enable(boolean state, JComponent[] components) {
            for(JComponent component: components) component.setEnabled(state);
        }
    }
    
    private class ToggleButtonSelection implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.printf(">>> ToggleButtonSelection.actionPerformed()\n");
            
            reset(); 
            if(e.getSource() == toggleOneToOne_FK) enableOneToOneFK();
            else if(e.getSource() == toggleOneToOne_PK) enableOneToOnePK();
            else if(e.getSource() == toggleOneToOne_Association)  enableOneToOneATable();
            else if(e.getSource() == toggleColumn_ManyToOne) enableManyToOneReference();
            else if(e.getSource() == toggleTable_ManyToOne) enableManyToOneATable();
            else if(e.getSource() == toggleColumn_OneToMany) enableOneToManyReference();
            else if(e.getSource() == toggleTable_OneToMany) enableOneToManyATable();
        }
        
    }
    
    private void disable(JComponent[] components) {
        for(JComponent component: components) component.setEnabled(false);
    }
    
    private class CascadeAll implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            enable(checkCascadeAll.isSelected(), new JCheckBox[] {
                checkCascadePersist, checkCascadeMerge, checkCascadeRemove, checkCascadeRefresh
            });
        }
        
        private void enable(boolean state, JCheckBox[] boxes) {
            for(JCheckBox box: boxes)
                if(state) {box.setEnabled(false); box.setSelected(true);}
                else {box.setEnabled(true); box.setSelected(false);}
        }
        
    }
    
    private class SwitchApproach implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // Toggle Buttons
            if(e.getSource() == toggleOneToOne) {
                System.out.printf(">>> SwitchApproach.actionPerformed() ToggleOneToOne\n");
                if(toggleOneToOne_FK.isSelected()) enableOneToOneFK();
                else if(toggleOneToOne_PK.isSelected()) enableOneToOnePK();
                else if(toggleOneToOne_Association.isSelected()) enableOneToOneATable();
                // Default Selection
                else enableOneToOneFK();
                
            } else if(e.getSource() == toggleManyToOne) {
                System.out.printf(">>> SwitchApproach.actionPerformed() ToggleManyToOne\n");
                if(toggleColumn_ManyToOne.isSelected()) enableManyToOneReference();
                else if(toggleTable_ManyToOne.isSelected()) enableManyToOneATable();
                // Default Selection
                else enableManyToOneReference();

            } else if(e.getSource() == toggleOneToMany) {
                System.out.printf(">>> SwitchApproach.actionPerformed() ToggleOneToMany\n");
                if(toggleColumn_OneToMany.isSelected()) enableOneToManyReference();
                else if(toggleTable_OneToMany.isSelected()) enableOneToManyATable();
                // Default Selection
                else enableOneToManyATable();
                
            } else if(e.getSource() == toggleManyToMany) {
                System.out.printf(">>> SwitchApproach.actionPerformed() ToggleManyToMany\n");
                enableManyToMany();
            }
        }
    }
    
    // ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE 
    //  ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE ONE-TO-ONE 
    
    private void enableOneToOneFK() {
        enableToggle(toggleOneToOne, toggleManyToOne, toggleOneToMany, toggleManyToMany);
        layoutApproach.show(panelApproach, PANEL_ONETOONE);
        
        selectionOneToOne.select(toggleOneToOne_FK);
        if(hasJavaEE) {
            textOneToOne_FK_Table.setText(tableSource);
            textOneToOne_FK_Table.setEditable(false);
            
            TableColumn tableColumn = projectJavaEE.getColumn(connection.getSource());
            if(tableColumn != null && tableColumn instanceof ColumnOneToOne) {
                // It has a previous value stored
                textOneToOne_FK_FK.setText(((ColumnOneToOne)tableColumn).getJoinColumn());
            } else {
                // if not, it suggest something that makes sense
                if(fieldIDTarget != null) {
                    Column column = (Column)projectJavaEE.getColumn(fieldIDTarget);
                    textOneToOne_FK_FK.setText(tableTarget.concat("_".concat(column.getName())));
                }
            }
            textOneToOne_FK_FK.selectAll();
            textOneToOne_FK_FK.requestFocus();
        }
    }
    
    private void enableOneToOnePK() {
        enableToggle(toggleOneToOne, toggleManyToOne, toggleOneToMany, toggleManyToMany);
        layoutApproach.show(panelApproach, PANEL_ONETOONE);
        selectionOneToOne.select(toggleOneToOne_PK);
        
        if(hasJavaEE) {
            textOneToOne_PK_Table.setText(tableSource);
            
            // Source Column PK
            if(fieldIDSource != null) {
                Column columnIdentifier = (Column)projectJavaEE.getColumn(fieldIDSource);
                textOneToOne_PK_Up.setText(columnIdentifier.getName());
            }
            
            // Target Column PK
            if(fieldIDTarget != null) {
                Column columnIdentifier = (Column)projectJavaEE.getColumn(fieldIDTarget);
                textOneToOne_PK_Down.setText(columnIdentifier.getName());
            }
        }
    }
    
    private void enableOneToOneATable() {
        enableToggle(toggleOneToOne, toggleManyToOne, toggleOneToMany, toggleManyToMany);
        layoutApproach.show(panelApproach, PANEL_ONETOONE);
        selectionOneToOne.select(toggleOneToOne_Association);
        if(hasJavaEE) {
            TableColumn tableColumn = projectJavaEE.getColumn(connection.getSource());
            if(tableColumn != null && tableColumn instanceof ColumnOneToOne) 
                updateAssociationTable(textTableSource_OneToOne, textPKSource_OneToOne,
                        textJoinTable_OneToOne, textJoinPKSource_OneToOne, textJoinPKTarget_OneToOne,
                        textTableTarget_OneToOne, textPKTarget_OneToOne, tableColumn.getAssociationTable());
            else 
                updateAssociationTable(textTableSource_OneToOne, textPKSource_OneToOne,
                        textJoinTable_OneToOne, textJoinPKSource_OneToOne, textJoinPKTarget_OneToOne,
                        textTableTarget_OneToOne, textPKTarget_OneToOne);
        }
    }
    
    // MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE 
    //  MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE MANY-TO-ONE 
    
    private void enableManyToOneReference() {
        enableToggle(toggleManyToOne, toggleOneToOne, toggleOneToMany, toggleManyToMany);
        layoutApproach.show(panelApproach, PANEL_MANYTOONE);
        selectionManyToOne.select(toggleColumn_ManyToOne);
        
        textTargetName_ManyToOne.setText(connection.getTargetName());
        
        if(hasJavaEE) {
            textTable_ManyToOne.setText(tableSource);
            TableColumn tableColumn = projectJavaEE.getColumn(connection.getSource());
            if(tableColumn != null && tableColumn instanceof ColumnManyToOne) {
                textColumn_ManyToOne.setText(((ColumnManyToOne)tableColumn).getReferenceColumn());
            } else {
                // There is nothing. Plase, suggest
                if(fieldIDTarget != null) {
                    Column column = (Column)projectJavaEE.getColumn(fieldIDTarget);
                    textColumn_ManyToOne.setText(tableTarget.concat("_".concat(column.getName())));
                }
            }
        }
        
        textColumn_ManyToOne.selectAll();
        textColumn_ManyToOne.requestFocus();
    }
    
    private void enableManyToOneATable() {
        enableToggle(toggleManyToOne, toggleOneToOne, toggleOneToMany, toggleManyToMany);
        layoutApproach.show(panelApproach, PANEL_MANYTOONE);
        selectionManyToOne.select(toggleTable_ManyToOne);
        textTargetName_ManyToOne.setText(connection.getTargetName());
        
        if(hasJavaEE)
            updateAssociationTable(textTableSource_ManyToOne, textPKSource_ManyToOne,
                    textJoinTable_ManyToOne, textJoinPKSource_ManyToOne, textJoinPKTarget_ManyToOne,
                    textTableTarget_ManyToOne, textPKTarget_ManyToOne);
    }
    
    // ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY 
    //  ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY ONE-TO-MANY 
    
    private void enableOneToManyReference() {
        enableToggle(toggleOneToMany, toggleManyToOne, toggleOneToOne, toggleManyToMany);
        layoutApproach.show(panelApproach, PANEL_ONETOMANY);
        selectionOneToMany.select(toggleColumn_OneToMany);
        
        textTable_OneToMany.setEditable(false);
        textTargetName_OneToMany.setText(connection.getTargetName());
        
        if(hasJavaEE) {
            textTable_OneToMany.setText(tableSource);
            TableColumn tableColumn = projectJavaEE.getColumn(connection.getSource());
            if(tableColumn != null && tableColumn instanceof ColumnManyToOne) {
                textColumn_OneToMany.setText(((ColumnManyToOne)tableColumn).getReferenceColumn());
            } else {
                if(fieldIDTarget != null) {
                    Column column = (Column)projectJavaEE.getColumn(fieldIDTarget);
                    textColumn_OneToMany.setText(tableTarget.concat("_".concat(column.getName())));
                    
                }
            }
        }
        
        textColumn_OneToMany.selectAll();
        textColumn_OneToMany.requestFocus();
    }
    
    private void enableOneToManyATable() {
        enableToggle(toggleOneToMany, toggleManyToOne, toggleOneToOne, toggleManyToMany);
        layoutApproach.show(panelApproach, PANEL_ONETOMANY);
        selectionOneToMany.select(toggleTable_OneToMany);
        
        textTargetName_OneToMany.setText(connection.getTargetName());
        if(hasJavaEE)
            updateAssociationTable(textTableSource_OneToMany, textPKSource_OneToMany,
                    textJoinTable_OneToMany, textJoinPKSource_OneToMany, textJoinPKTarget_OneToMany,
                    textTableTarget_OneToMany, textPKTarget_OneToMany);
    }
    
    // MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY 
    //  MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY MANY-TO-MANY 
    
    private void enableManyToMany() {
        System.out.printf(">>> PanelConnection.enableManyToMany()\n");
        enableToggle(toggleManyToMany, toggleOneToOne, toggleManyToOne, toggleOneToMany);
        layoutApproach.show(panelApproach, PANEL_MANYTOMANY);
        textTargetName_ManyToMany.setText(connection.getTargetName());
        
        if(hasJavaEE) {
            System.out.printf(">>> PanelConnection.enableManyToMany() it has JavaEE\n");
            updateAssociationTable(textTableSource_ManyToMany, textPKSource_ManyToMany,
                    textJoinTable_ManyToMany, textJoinPKSource_ManyToMany, textJoinPKTarget_ManyToMany,
                    textTableTarget_ManyToMany, textPKTarget_ManyToMany);
        }
    }
}
