package extensionTools.opcatLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import exportedAPI.opcatAPIx.*;
import extensionTools.opcatLayoutManager.Constraints.*;

/**
 * This class implements UI for layout manager extension tool.
 * This UI is used primarily for debugging purposes.
 */
public class GUIPanel extends JPanel
{
    /**
     * Creates an instance of the class.
     */
    public GUIPanel(IXSystem system)
    {
        try
        {
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        m_system = system;

        LoadModel();
        FillTreeFirstLevel();
    }

    /**
     * Initializes UI controls.
     */
    private void jbInit() throws Exception
    {
        m_btnArrange.setBounds(new Rectangle(648, 77, 66, 23));
        m_btnArrange.setMargin(new Insets(2, 2, 2, 2));
        m_btnArrange.setText("Arrange");
        m_btnArrange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnArrange_actionPerformed(e);
            }
        });
        m_btnStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnStep_actionPerformed(e);
            }
        });
        m_btnStep.setBounds(new Rectangle(648, 105, 66, 23));
        m_btnStep.setToolTipText("");
        m_btnStep.setMargin(new Insets(2, 2, 2, 2));
        m_btnStep.setText("Step");
        this.setLayout(null);
        m_treeConstraints.setBounds(new Rectangle(60, 39, 274, 140));
        m_treeConstraints.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                m_treeConstraints_valueChanged(e);
            }
        });
        m_btnAlignHorStart.setBounds(new Rectangle(359, 21, 85, 23));
        m_btnAlignHorStart.setMargin(new Insets(2, 2, 2, 2));
        m_btnAlignHorStart.setText("Align to Top");
        m_btnAlignHorStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnAlignHorStart_actionPerformed(e);
            }
        });
        m_btnAlignVerStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnAlignVerStart_actionPerformed(e);
            }
        });
        m_btnAlignVerStart.setText("Align to Left");
        m_btnAlignVerStart.setBounds(new Rectangle(452, 21, 85, 23));
        m_btnAlignVerStart.setToolTipText("");
        m_btnAlignVerStart.setActionCommand("Align Vertically");
        m_btnAlignVerStart.setMargin(new Insets(2, 2, 2, 2));
        m_btnSpaceHorizontally.setActionCommand("Space Equally  Horizontally");
        m_btnSpaceHorizontally.setMargin(new Insets(2, 2, 2, 2));
        m_btnSpaceHorizontally.setText("Even Space");
        m_btnSpaceHorizontally.setToolTipText("");
        m_btnSpaceHorizontally.setBounds(new Rectangle(359, 104, 85, 23));
        m_btnSpaceHorizontally.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnSpaceHorizontally_actionPerformed(e);
            }
        });
        m_btnSpaceVertically.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnSpaceVertically_actionPerformed(e);
            }
        });
        m_btnSpaceVertically.setBounds(new Rectangle(452, 104, 85, 23));
        m_btnSpaceVertically.setToolTipText("");
        m_btnSpaceVertically.setText("Even Space");
        m_btnSpaceVertically.setActionCommand("Space Equally  Vertically");
        m_btnSpaceVertically.setMargin(new Insets(2, 2, 2, 2));
        m_btnRemoveConstraint.setActionCommand("Remove Constraint");
        m_btnRemoveConstraint.setMargin(new Insets(2, 2, 2, 2));
        m_btnRemoveConstraint.setText("Remove Constraint");
        m_btnRemoveConstraint.setToolTipText("");
        m_btnRemoveConstraint.setBounds(new Rectangle(359, 160, 178, 23));
        m_btnRemoveConstraint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnRemoveConstraint_actionPerformed(e);
            }
        });
        m_btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnReload_actionPerformed(e);
            }
        });
        m_btnReload.setText("Reload");
        m_btnReload.setBounds(new Rectangle(648, 21, 66, 23));
        m_btnReload.setMargin(new Insets(2, 2, 2, 2));
        m_chkArrangeInTwoPhases.setBounds(new Rectangle(648, 134, 66, 23));
        m_chkArrangeInTwoPhases.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                m_chkArrangeInTwoPhases_itemStateChanged(e);
            }
        });
        m_chkArrangeInTwoPhases.setText("2 Phase");
        m_chkArrangeInTwoPhases.setSelected(true);
        m_chkArrangeInTwoPhases.setToolTipText("");
        m_btnOrderHorizontally.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnOrderHorizontally_actionPerformed(e);
            }
        });
        m_btnOrderHorizontally.setText("Order");
        m_btnOrderHorizontally.setBounds(new Rectangle(359, 131, 85, 23));
        m_btnOrderHorizontally.setActionCommand("Order Horizontally");
        m_btnOrderHorizontally.setMargin(new Insets(2, 2, 2, 2));
        m_lblHorozontal.setFont(new java.awt.Font("Dialog", 1, 12));
        m_lblHorozontal.setText("Horizontal");
        m_lblHorozontal.setBounds(new Rectangle(359, 0, 85, 20));
        m_lblVertical.setBounds(new Rectangle(452, 1, 85, 20));
        m_lblVertical.setFont(new java.awt.Font("Dialog", 1, 12));
        m_lblVertical.setOpaque(true);
        m_lblVertical.setText("Vertical");
        m_btnOrderVertically.setActionCommand("Order Vertically");
        m_btnOrderVertically.setMargin(new Insets(2, 2, 2, 2));
        m_btnOrderVertically.setToolTipText("");
        m_btnOrderVertically.setBounds(new Rectangle(452, 131, 85, 23));
        m_btnOrderVertically.setText("Order");
        m_btnOrderVertically.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnOrderVertically_actionPerformed(e);
            }
        });
        m_btnAlignHorCenter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnAlignHorCenter_actionPerformed(e);
            }
        });
        m_btnAlignHorCenter.setText("Align to Center");
        m_btnAlignHorCenter.setBounds(new Rectangle(359, 49, 85, 23));
        m_btnAlignHorCenter.setMargin(new Insets(2, 2, 2, 2));
        m_btnAlignHorEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnAlignHorEnd_actionPerformed(e);
            }
        });
        m_btnAlignHorEnd.setText("Align to Bottom");
        m_btnAlignHorEnd.setBounds(new Rectangle(359, 77, 85, 23));
        m_btnAlignHorEnd.setMaximumSize(new Dimension(83, 25));
        m_btnAlignHorEnd.setMargin(new Insets(2, 2, 2, 2));
        m_btnAlignVerCenter.setActionCommand("Align Vertically");
        m_btnAlignVerCenter.setMargin(new Insets(2, 2, 2, 2));
        m_btnAlignVerCenter.setToolTipText("");
        m_btnAlignVerCenter.setBounds(new Rectangle(452, 49, 85, 23));
        m_btnAlignVerCenter.setText("Align to Center");
        m_btnAlignVerCenter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnAlignVerCenter_actionPerformed(e);
            }
        });
        m_btnAlignVerEnd.setActionCommand("Align Vertically");
        m_btnAlignVerEnd.setMargin(new Insets(2, 2, 2, 2));
        m_btnAlignVerEnd.setToolTipText("");
        m_btnAlignVerEnd.setBounds(new Rectangle(452, 77, 85, 23));
        m_btnAlignVerEnd.setText("Align to Right");
        m_btnAlignVerEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnAlignVerEnd_actionPerformed(e);
            }
        });

        m_scrollTreeConstraints = new JScrollPane(m_treeConstraints);
        m_scrollTreeConstraints.setBounds(new Rectangle(-1, 0, 352, 226));

        m_chkAlignToFirstSelected.setToolTipText("");
        m_chkAlignToFirstSelected.setSelected(true);
        m_chkAlignToFirstSelected.setText("Align to first selected");
        m_chkAlignToFirstSelected.setBounds(new Rectangle(359, 191, 178, 23));
        m_btnZone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnZone_actionPerformed(e);
            }
        });
    m_btnZone.setText("Zone");
    m_btnZone.setMargin(new Insets(2, 2, 2, 2));
    m_btnZone.setBounds(new Rectangle(544, 49, 85, 23));
    m_btnTree.setBounds(new Rectangle(544, 77, 85, 23));
    m_btnTree.setMargin(new Insets(2, 2, 2, 2));
    m_btnTree.setSelected(false);
    m_btnTree.setText("Tree");
    m_btnTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnTree_actionPerformed(e);
            }
        });
    m_btnSyntactic.setBounds(new Rectangle(544, 21, 85, 23));
    m_btnSyntactic.setMargin(new Insets(2, 2, 2, 2));
    m_btnSyntactic.setText("Syntactic");
    m_btnSyntactic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnSyntactic_actionPerformed(e);
            }
        });
    m_btnAnchor.setBounds(new Rectangle(544, 104, 85, 23));
    m_btnAnchor.setMargin(new Insets(2, 2, 2, 2));
    m_btnAnchor.setText("Anchor");
    m_btnAnchor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnAnchor_actionPerformed(e);
            }
        });
    m_btnReload1.setMargin(new Insets(2, 2, 2, 2));
        m_btnReload1.setBounds(new Rectangle(0, 0, 66, 23));
        m_btnReload1.setText("Reload");
        m_btnReload1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnReload1_actionPerformed(e);
            }
        });
        m_btnInitArrange.setMargin(new Insets(2, 2, 2, 2));
        m_btnInitArrange.setBounds(new Rectangle(648, 49, 66, 23));
        m_btnInitArrange.setText("Init");
        m_btnInitArrange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_btnInitArrange_actionPerformed(e);
            }
        });
        this.add(m_btnOrderHorizontally, null);
        this.add(m_btnAlignVerStart, null);
        this.add(m_btnSpaceVertically, null);
        this.add(m_btnOrderVertically, null);
        this.add(m_btnAlignHorStart, null);
        this.add(m_btnAlignHorCenter, null);
        this.add(m_btnAlignHorEnd, null);
        this.add(m_btnSpaceHorizontally, null);
        this.add(m_btnAlignVerCenter, null);
        this.add(m_btnAlignVerEnd, null);
        this.add(m_btnRemoveConstraint, null);
        this.add(m_scrollTreeConstraints, null);
        this.add(m_chkAlignToFirstSelected, null);
        this.add(m_lblHorozontal, null);
        this.add(m_lblVertical, null);
        this.add(m_btnZone, null);
        this.add(m_btnTree, null);
        this.add(m_btnSyntactic, null);
        this.add(m_btnAnchor, null);
        this.add(m_btnReload, null);
        this.add(m_btnArrange, null);
        this.add(m_btnStep, null);
        this.add(m_chkArrangeInTwoPhases, null);
        this.add(m_btnReload1, null);
        this.add(m_btnInitArrange, null);
    }

    /**
     * Loads the model.
     */
    private void LoadModel()
    {
        m_systemStructure = m_system.getIXSystemStructure();
        m_currentOpd      = m_system.getCurrentIXOpd();
        m_currentOpdId    = m_currentOpd.getOpdId();
        m_layoutSystem    = new ConstraintBasedForceDirectedLayoutSystem();
    }

    /**
     * Fills the first level of the constraints tree.
     */
    private void FillTreeFirstLevel()
    {
        DefaultMutableTreeNode rootNode  = new DefaultMutableTreeNode("Root");
        DefaultTreeModel       treeModel = new DefaultTreeModel(rootNode);

        //
        // Add constraint categories.
        //
        m_tnNodeNodeOverlap = new DefaultMutableTreeNode("Node-node ovelpaping");
        m_tnNodeEdgeOverlap = new DefaultMutableTreeNode("Node-edge ovelpaping");
        m_tnHorAlign        = new DefaultMutableTreeNode("Horizontal alignment");
        m_tnHorEqSpace      = new DefaultMutableTreeNode("Horizontal equal spacing");
        m_tnHorOrder        = new DefaultMutableTreeNode("Horizontal order");
        m_tnVerAlign        = new DefaultMutableTreeNode("Vertical alignment");
        m_tnVerEqSpace      = new DefaultMutableTreeNode("Vertical equal spacing");
        m_tnVerOrder        = new DefaultMutableTreeNode("Vertical order");
        m_tnZone            = new DefaultMutableTreeNode("Zone");
        m_tnTree            = new DefaultMutableTreeNode("Tree");
        m_tnAnchor          = new DefaultMutableTreeNode("Anchor");
        treeModel.insertNodeInto(m_tnNodeNodeOverlap, rootNode, 0);
        treeModel.insertNodeInto(m_tnNodeEdgeOverlap, rootNode, 0);
        treeModel.insertNodeInto(m_tnHorAlign, rootNode, 0);
        treeModel.insertNodeInto(m_tnHorEqSpace, rootNode, 0);
        treeModel.insertNodeInto(m_tnHorOrder, rootNode, 0);
        treeModel.insertNodeInto(m_tnVerAlign, rootNode, 0);
        treeModel.insertNodeInto(m_tnVerEqSpace, rootNode, 0);
        treeModel.insertNodeInto(m_tnVerOrder, rootNode, 0);
        treeModel.insertNodeInto(m_tnZone, rootNode, 0);
        treeModel.insertNodeInto(m_tnTree, rootNode, 0);
        treeModel.insertNodeInto(m_tnAnchor, rootNode, 0);

        m_treeConstraints.setModel(treeModel);
        m_treeConstraints.setRootVisible(true);
        m_treeConstraints.setShowsRootHandles(true);
        m_treeConstraints.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    /**
     * Adds all the nodes in the currend OPD.
     */
    private void AddNodesToModel()
    {
        for ( Enumeration enumInstances = m_systemStructure.getInstancesInOpd(m_currentOpdId);
              enumInstances.hasMoreElements();
              /* inside loop */)
        {
            IXInstance instance = (IXInstance)enumInstances.nextElement();

            if (instance instanceof IXNode)
            {
                m_layoutSystem.addNode((IXNode)instance);
            }
        }
    }

    /**
     * Adds all the edges in the cuttent OPD.
     */
    private void AddEdgesToModel()
    {
        for ( Enumeration enumInstances = m_systemStructure.getInstancesInOpd(m_currentOpdId);
              enumInstances.hasMoreElements();
              /* inside loop */)
        {
            IXInstance instance = (IXInstance)enumInstances.nextElement();

            long sourceId      = 0;
            long destinationId = 0;

            if (instance instanceof IXLine)
            {
                m_layoutSystem.addEdge((IXLine)instance);
            }
        }
    }

    /**
     * Adds an alignment constraint.
     */
    private void AddAlignmentConstraint( int                    direction,
                                         int                    origin,
                                         boolean                useFirstSelectedAsMain,
                                         DefaultMutableTreeNode treeGroup)
    {
        Constraint constraint = m_layoutSystem.createAlignmentConstraint( direction,
                                                                          origin,
                                                                          useFirstSelectedAsMain ? GetFirstSelectedNode() : null,
                                                                          m_currentOpd.getSelectedItems());
        m_layoutSystem.addConstraint(constraint);
        AddConstraintToTree(constraint, treeGroup);
    }

    /**
     * Adds and equal spacing constraint.
     */
    private void AddEqualSpacingConstraint(int direction, DefaultMutableTreeNode treeGroup)
    {
        Constraint constraint = m_layoutSystem.createSpacingConstraint( direction,
                                                                        0,
                                                                        m_currentOpd.getSelectedItems());
        m_layoutSystem.addConstraint(constraint);
        AddConstraintToTree(constraint, treeGroup);
    }

    /**
     * Adds a sequence constraint.
     */
    private void AddSequenceConstraint(int direction, DefaultMutableTreeNode treeGroup)
    {
        IXNode[] nodes = new IXNode[2];

        int index = 0;

        for ( Enumeration enumSelected = m_currentOpd.getSelectedItems();
              enumSelected.hasMoreElements();
              /* inside loop */)
        {
            IXInstance instance = (IXInstance)enumSelected.nextElement();

            if (instance instanceof IXNode)
            {
                if (index > 1)
                {
                    //
                    // Too much elements.
                    //
                    return;
                }

                nodes[index++] = (IXNode)instance;
            }
        }

        if (index < 2)
        {
            //
            // Not enough elements.
            //
            return;
        }

        Constraint constraint = m_layoutSystem.createOrderConstraint(direction, nodes[0], nodes[1]);
        m_layoutSystem.addConstraint(constraint);
        AddConstraintToTree(constraint, treeGroup);
    }

    /**
     * Returns a constraint object, corresponding to the current selection
     * in the constraints tree control.
     *
     * @return the constraint object
     */
    private Object GetSelectedConstraint()
    {
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)m_treeConstraints.getLastSelectedPathComponent();
        return null != treeNode ? treeNode.getUserObject() : null;
    }

    /**
     * Adds a constraint object to the constraints tree control
     */
    private DefaultMutableTreeNode AddConstraintToTree(Constraint constraint, DefaultMutableTreeNode parent)
    {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(constraint);
        DefaultTreeModel treeModel  = (DefaultTreeModel)m_treeConstraints.getModel();
        treeModel.insertNodeInto(node, parent, 0);
        m_treeConstraints.scrollPathToVisible(new TreePath(node.getPath()));
        return node;
    }

    /**
     * Returns the node that has been selected first.
     */
    private IXNode GetFirstSelectedNode()
    {
        IXNode node = null;

        //
        // The node that has been selected first appears as the last element
        // of the m_currentOpd.getSelectedItems() enumeration.
        //
        for ( Enumeration enumSelected = m_currentOpd.getSelectedItems();
              enumSelected.hasMoreElements();
              /* inside loop */)
        {
            Object element = enumSelected.nextElement();
            if (element instanceof IXNode)
            {
                node = (IXNode) element;
            }
        }

        return node;
    }

    /**
     * Handles click on the "Reload" button.
     */
    private void m_btnReload_actionPerformed(ActionEvent e)
    {
        LoadModel();
        FillTreeFirstLevel();
    }

    /**
     * Handles click on the "Init" button.
     */
    void m_btnInitArrange_actionPerformed(ActionEvent e)
    {
        m_layoutSystem.initArrange(m_chkArrangeInTwoPhases.isSelected());
    }

    /**
     * Handles click on the "Arrange" button.
     */
    private void m_btnArrange_actionPerformed(ActionEvent e)
    {
        m_layoutSystem.arrange(200);
    }

    /**
     * Hanles click on the "Step" button.
     */
    private void m_btnStep_actionPerformed(ActionEvent e)
    {
        m_layoutSystem.step();
    }

    /**
     * Handles changes of "2 phases" checkbox.
     */
    private void m_chkArrangeInTwoPhases_itemStateChanged(ItemEvent e)
    {
        if (null != m_layoutSystem)
        {
            m_layoutSystem.initArrange(m_chkArrangeInTwoPhases.isSelected());
        }
    }

    /**
     * Handles click on the "Align to top" button.
     */
    private void m_btnAlignHorStart_actionPerformed(ActionEvent e)
    {
        AddAlignmentConstraint( Constraint.directionHorizontal,
                                MultiNodeConstraint.originStart,
                                m_chkAlignToFirstSelected.isSelected(),
                                m_tnHorAlign);
    }

    /**
     * Handles click on the "Align to center horizontally" button.
     */
    private void m_btnAlignHorCenter_actionPerformed(ActionEvent e)
    {
        AddAlignmentConstraint( Constraint.directionHorizontal,
                                MultiNodeConstraint.originCenter,
                                m_chkAlignToFirstSelected.isSelected(),
                                m_tnHorAlign);
    }

    /**
     * Handles click on the "Align to bottom" button.
     */
    private void m_btnAlignHorEnd_actionPerformed(ActionEvent e)
    {
        AddAlignmentConstraint( Constraint.directionHorizontal,
                                MultiNodeConstraint.originEnd,
                                m_chkAlignToFirstSelected.isSelected(),
                                m_tnHorAlign);
    }

    /**
     * Handles click on the "Space equal horizontally" button.
     */
    private void m_btnSpaceHorizontally_actionPerformed(ActionEvent e)
    {
        AddEqualSpacingConstraint(Constraint.directionHorizontal, m_tnHorEqSpace);
    }

    /**
     * Handles click on the "Order horizontally" button.
     */
    void m_btnOrderHorizontally_actionPerformed(ActionEvent e)
    {
        AddSequenceConstraint(Constraint.directionHorizontal, m_tnHorOrder);
    }

    /**
     * Handles click on the "Align to left" button.
     */
    private void m_btnAlignVerStart_actionPerformed(ActionEvent e)
    {
        AddAlignmentConstraint( Constraint.directionVertical,
                                MultiNodeConstraint.originStart,
                                m_chkAlignToFirstSelected.isSelected(),
                                m_tnVerAlign);
    }

    /**
     * Handles click on the "Align to center vertically" button.
     */
    private void m_btnAlignVerCenter_actionPerformed(ActionEvent e)
    {
        AddAlignmentConstraint( Constraint.directionVertical,
                                MultiNodeConstraint.originCenter,
                                m_chkAlignToFirstSelected.isSelected(),
                                m_tnVerAlign);
    }

    /**
     * Handles click on the "Align to right" button.
     */
    private void m_btnAlignVerEnd_actionPerformed(ActionEvent e)
    {
        AddAlignmentConstraint( Constraint.directionVertical,
                                MultiNodeConstraint.originEnd,
                                m_chkAlignToFirstSelected.isSelected(),
                                m_tnVerAlign);
    }

    /**
     * Handles click on the "Space equal vertically" button.
     */
    private void m_btnSpaceVertically_actionPerformed(ActionEvent e)
    {
        AddEqualSpacingConstraint(Constraint.directionVertical, m_tnVerEqSpace);
    }

    /**
     * Handles click on the "Order vertically" button.
     */
    private void m_btnOrderVertically_actionPerformed(ActionEvent e)
    {
        AddSequenceConstraint(Constraint.directionVertical, m_tnVerOrder);
    }

    /**
     * Handles click on the "Zone" button.
     */
    void m_btnZone_actionPerformed(ActionEvent e)
    {
        IXNode mainNode = GetFirstSelectedNode();
        if (null == mainNode)
        {
            return;
        }

        Constraint constraint = m_layoutSystem.createZoneConstraint(mainNode, m_currentOpd.getSelectedItems());
        m_layoutSystem.addConstraint(constraint);
        AddConstraintToTree(constraint, m_tnZone);
    }

    /**
     * Handles click on the "Tree" button.
     */
    void m_btnTree_actionPerformed(ActionEvent e)
    {
        IXNode mainNode = GetFirstSelectedNode();
        if (null == mainNode)
        {
            return;
        }

        Constraint constraint = m_layoutSystem.createTreeConstraint( mainNode,
                                                                     null, // don't have access to the relation node
                                                                     m_currentOpd.getSelectedItems());
        m_layoutSystem.addConstraint(constraint);
        AddConstraintToTree(constraint, m_tnTree);
    }

    /**
     * Handles click on the "Syntactic" button.
     */
    void m_btnSyntactic_actionPerformed(ActionEvent e)
    {
        //
        // Make sure the model contains all nodes and edges in the current OPD.
        //
        AddNodesToModel();
        AddEdgesToModel();

        //
        // Add syntactic constraints.
        //
        for ( Iterator iterSyntacticConstraints = m_layoutSystem.addSyntacticConstraints();
              iterSyntacticConstraints.hasNext();
              /* inside loop */)
        {
            Constraint constraint = (Constraint)iterSyntacticConstraints.next();

            DefaultMutableTreeNode treeGroup;
            if (constraint instanceof NodeNodeOverlapConstraint)
            {
                treeGroup = m_tnNodeNodeOverlap;
            }
            else if (constraint instanceof NodeEdgeOverlapConstraint)
            {
                treeGroup = m_tnNodeEdgeOverlap;
            }
            else if (constraint instanceof ZoneConstraint)
            {
                treeGroup = m_tnZone;
            }
            else
            {
                continue;
            }

            AddConstraintToTree(constraint, treeGroup);
        }
    }

    /**
     * Handles click on the "Anchor" button.
     */
    void m_btnAnchor_actionPerformed(ActionEvent e)
    {
        IXNode nodeToAnchor = GetFirstSelectedNode();
        if (null == nodeToAnchor)
        {
            return;
        }
        m_layoutSystem.anchorNode(nodeToAnchor);

        DefaultMutableTreeNode treeNode  = new DefaultMutableTreeNode(nodeToAnchor);
        DefaultTreeModel       treeModel = (DefaultTreeModel)m_treeConstraints.getModel();

        treeModel.insertNodeInto(treeNode, m_tnAnchor, 0);
        m_treeConstraints.scrollPathToVisible(new TreePath(treeNode.getPath()));
    }

    /**
     * Handles selecting items in the constraints tree control.
     */
    private void m_treeConstraints_valueChanged(TreeSelectionEvent e)
    {
        if (null != m_currentlySelectedConstraint)
        {
            if (m_currentlySelectedConstraint instanceof Constraint)
            {
                ((Constraint)m_currentlySelectedConstraint).Hide();
            }
            else if (m_currentlySelectedConstraint instanceof IXNode)
            {
            }
            else
            {
                
            }
        }

        m_currentlySelectedConstraint = GetSelectedConstraint();

        if (null != m_currentlySelectedConstraint)
        {
            if (m_currentlySelectedConstraint instanceof Constraint)
            {
                ((Constraint)m_currentlySelectedConstraint).Show();
            }
            else if (m_currentlySelectedConstraint instanceof IXNode)
            {
            }
            else
            {
                
            }
        }
    }

    /**
     * Handles click on the "Remove coonstraint" button.
     */
    private void m_btnRemoveConstraint_actionPerformed(ActionEvent e)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)m_treeConstraints.getLastSelectedPathComponent();
        if (null == node)
        {
           return;
        }

        Object userObject = node.getUserObject();
        if (null == userObject)
        {
            return;
        }

        if (userObject instanceof Constraint)
        {
            m_layoutSystem.removeConstraint((Constraint)userObject);
        }
        else if (userObject instanceof IXNode)
        {
            m_layoutSystem.removeAnchor((IXNode)userObject);
        }
        else
        {
            
        }

        node.removeFromParent();
        m_treeConstraints.updateUI();
    }

    private JButton                m_btnArrange            = new JButton();
    private JButton                m_btnStep               = new JButton();
    private JCheckBox              m_chkArrangeInTwoPhases = new JCheckBox();
    private JTree                  m_treeConstraints       = new JTree();
    private JScrollPane            m_scrollTreeConstraints;
    private JLabel                 m_lblHorozontal         = new JLabel();
    private JLabel                 m_lblVertical           = new JLabel();
    private JButton                m_btnAlignHorStart      = new JButton();
    private JButton                m_btnAlignHorCenter     = new JButton();
    private JButton                m_btnAlignHorEnd        = new JButton();
    private JButton                m_btnSpaceHorizontally  = new JButton();
    private JButton                m_btnOrderHorizontally  = new JButton();
    private JButton                m_btnAlignVerStart      = new JButton();
    private JButton                m_btnAlignVerCenter     = new JButton();
    private JButton                m_btnAlignVerEnd        = new JButton();
    private JButton                m_btnSpaceVertically    = new JButton();
    private JButton                m_btnOrderVertically    = new JButton();
    private JButton                m_btnZone               = new JButton();
    private JButton                m_btnTree               = new JButton();
    private JButton                m_btnSyntactic          = new JButton();
    private JButton                m_btnAnchor             = new JButton();
    private JButton                m_btnRemoveConstraint   = new JButton();
    private JCheckBox              m_chkAlignToFirstSelected = new JCheckBox();
    private JButton                m_btnReload             = new JButton();
    private DefaultMutableTreeNode m_tnNodeNodeOverlap;
    private DefaultMutableTreeNode m_tnNodeEdgeOverlap;
    private DefaultMutableTreeNode m_tnHorAlign;
    private DefaultMutableTreeNode m_tnHorEqSpace;
    private DefaultMutableTreeNode m_tnHorOrder;
    private DefaultMutableTreeNode m_tnVerAlign;
    private DefaultMutableTreeNode m_tnVerEqSpace;
    private DefaultMutableTreeNode m_tnVerOrder;
    private DefaultMutableTreeNode m_tnZone;
    private DefaultMutableTreeNode m_tnTree;
    private DefaultMutableTreeNode m_tnAnchor;
    private Object                 m_currentlySelectedConstraint;

    private IXSystem                                 m_system;
    private IXSystemStructure                        m_systemStructure;
    private IXOpd                                    m_currentOpd;
    private long                                     m_currentOpdId;
    private ConstraintBasedForceDirectedLayoutSystem m_layoutSystem;
    JButton m_btnReload1 = new JButton();
    JButton m_btnInitArrange = new JButton();
    void m_btnReload1_actionPerformed(ActionEvent e) {

    }
}