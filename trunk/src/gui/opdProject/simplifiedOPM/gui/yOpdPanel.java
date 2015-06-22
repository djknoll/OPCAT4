package gui.opdProject.simplifiedOPM.gui;

import com.jidesoft.docking.DockableFrame;
import com.jidesoft.swing.SearchableUtils;
import com.jidesoft.swing.TreeSearchable;
import y.view.Overview;
import y.view.hierarchy.DefaultNodeChangePropagator;
import y.view.hierarchy.HierarchyJTree;
import y.view.hierarchy.HierarchyTreeModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by raanan.
 * Date: 13/06/11
 * Time: 15:39
 */
public class yOpdPanel extends JPanel implements PropertyChangeListener {


    private JSplitPane horizSplit;
    private JSplitPane varticalSplit;
    private JSplitPane propertiesSplit;

    private JPanel graphPanel;
    private JPanel overViewPanel;
    private JPanel graphStructurViewPanel;
    private JPanel propertiesPanel;

    private JLabel additionData;
    private JLabel mulData;

    OpdSimplifiedView view;

    public yOpdPanel() {
        super();
        initUI();
    }

    public yOpdPanel(OpdSimplifiedView view) {
        super();
        initUI();
        setGraph(view);
        this.view = view;
        this.view.addPropertyChangeListener(this);
        //this.view.addPropertyChangeListener("Multiply", this);


    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("Addition".equals(evt.getPropertyName())) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    propertiesPanel.removeAll();
                    JTextPane textPane = new JTextPane();
                    textPane.setContentType("text/html");
                    textPane.setEditable(false);
                    textPane.setOpaque(false);
                    textPane.setBackground(null);
                    textPane.setText(view.getAddText());
                    propertiesPanel.add(textPane);
                    propertiesPanel.repaint();
                    updateUI();
                    repaint();
                }
            });
        }
    }

    private void initUI() {

        setLayout(new BorderLayout());

        horizSplit = new JSplitPane();
        propertiesSplit = new JSplitPane();

        propertiesPanel = new JPanel(new BorderLayout());
        propertiesPanel.setBorder(new TitledBorder("Properties"));
        //propertiesPanel.set;
        graphPanel = new JPanel(new BorderLayout());
        overViewPanel = new JPanel(new BorderLayout());
        graphStructurViewPanel = new JPanel(new BorderLayout());
        propertiesPanel = new JPanel(new BorderLayout());


        varticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                overViewPanel, graphStructurViewPanel);
        varticalSplit.setOneTouchExpandable(true);


        JScrollPane propertieScroll = new JScrollPane(propertiesPanel);
        propertiesSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, varticalSplit, propertieScroll);

        horizSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                graphPanel, propertiesSplit);

        horizSplit.setOneTouchExpandable(true);


        add(horizSplit, BorderLayout.CENTER);

        additionData = new JLabel();
        mulData = new JLabel();
        propertiesPanel.add(additionData);
        propertiesPanel.add(mulData);


    }

    @Override
    public void setPreferredSize(Dimension d) {
        super.setSize(d);

        int space = getHeight() / 5; //150;

        graphPanel.setSize(new Dimension(getWidth() - space, getHeight()));
        graphPanel.repaint();

        varticalSplit.setDividerLocation(space);
        propertiesSplit.setDividerLocation(((Double) (3.5 * space)).intValue());
        horizSplit.setDividerLocation(getWidth() - 2 * space);

        if (view != null) {
            view.doGraphLayout();
            view.repaint();
        }

    }

    private void setGraph(OpdSimplifiedView view) {


        //tree overall view
        HierarchyTreeModel htm = new HierarchyTreeModel(view.getHm());
        htm.setChildComparator(HierarchyTreeModel.createLabelTextComparator());

        HierarchyJTree ht = new HierarchyJTree(view.getHm(), htm);
        ht.setRootVisible(true);
        ht.setShowsRootHandles(true);

        //((HierarchyTreeCellRenderer) ht.getCellRenderer()).setRootValue("OPM Elements");

        TreeSearchable searchTree = SearchableUtils.installSearchable(ht);
        searchTree.setRecursive(true);

        view.getGraph2D().addGraph2DListener(new DefaultNodeChangePropagator());

        JScrollPane scroll = new JScrollPane(ht);
        graphStructurViewPanel.removeAll();
        graphStructurViewPanel.add(BorderLayout.CENTER, scroll);

        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
        ht.registerKeyboardAction(view.getAllPathAction(), stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        //overview
        Overview overview = new Overview(view);
        overview.putClientProperty("Overview.AnimateScrollTo", Boolean.TRUE);
        overview.putClientProperty("Overview.PaintStyle", "Funky");
        overview.putClientProperty("Overview.AllowZooming", Boolean.TRUE);
        overview.putClientProperty("Overview.AllowKeyboardNavigation", Boolean.TRUE);
        overview.putClientProperty("Overview.Inverse", Boolean.TRUE);

        overViewPanel.removeAll();
        overViewPanel.add(BorderLayout.CENTER, overview);

        graphPanel.removeAll();
        graphPanel.add(BorderLayout.CENTER, view);

    }

}
