package gui.opdGraphics.dialogs;

import exportedAPI.OpdKey;
import gui.Opcat2;
import gui.checkModule.CheckModule;
import gui.checkModule.CheckResult;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdGraphics.opdBaseComponents.OpdState;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmState;
import gui.projectStructure.Entry;
import gui.projectStructure.MainStructure;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.StateEntry;
import gui.projectStructure.StateInstance;
import gui.undo.UndoableAddState;
import gui.undo.UndoableDeleteState;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.UndoableEditEvent;


class StatePanel extends JPanel{
	private JList statesList;
	DefaultListModel listModel;
	private JButton addButton, removeButton, propertiesButton;
	private OpdObject parentOpdObject;
	private OpmObject parentOpmObject;
	private JScrollPane scrollPane;
	private JComboBox viewBox;
	private JCheckBox autoarrange;
	private OpdProject myProject;

	public StatePanel(OpdObject o, OpdProject project){
		super();
		parentOpdObject = o;
		parentOpmObject = (OpmObject)o.getEntity();
				myProject = project;
		listModel = new DefaultListModel();
		statesList = new JList(listModel);
		statesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		statesList.setCellRenderer(new CheckListCellRenderer());
		CheckListener lst = new CheckListener(statesList, project, this);
		statesList.addMouseListener(lst);
		statesList.addKeyListener(lst);
		//statesList.setSize(200, 100);


		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(15, 8, 8, 8));

		scrollPane = new JScrollPane(statesList);
		add(scrollPane);

		add(Box.createVerticalStrut(15));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 4, 3, 3));
		buttonPanel.add(Box.createGlue());
		addButton = new JButton("Add");
		addButton.addActionListener(new AddButtonListener());
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new RemoveButtonListener());
		propertiesButton = new JButton("Props");
		propertiesButton.addActionListener(new PropertiesButtonListener());
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(propertiesButton);
		add(buttonPanel);

		add(Box.createVerticalStrut(15));
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.add(new JLabel("   View:  "));
		p.add(Box.createHorizontalStrut(10));
		String[] views = { "None", "Implicit", "Explicit" };
		viewBox = new JComboBox(views);
		viewBox.setSelectedIndex(o.getStatesDrawingStyle());
		p.add(viewBox);
		p.add(Box.createHorizontalStrut(30));
		autoarrange = new JCheckBox("  Autoarrange");
		if (o.isStatesAutoarrange())
		{
			autoarrange.setSelected(true);
		}
		p.add(autoarrange);
		p.add(Box.createHorizontalStrut(40));
		add(p);
		add(Box.createVerticalStrut(75));

		boolean toSelect; // flag to select or not an item in list

			  _createListModel();
//          Component[] comps = parentOpdObject.getComponents();
//
//          for(int i = 0; i < comps.length; i++)
//          {
//            if (comps[i] instanceof OpdState)
//            {
//              OpdState tmpState = (OpdState)comps[i];
//              listModel.addElement(new StateListCell(tmpState, tmpState.isVisible()));
//            }
//          }

              //reuseComment
              Entry sEntry = o.getEntry();
              if ( sEntry.isLocked())
                lockForEdit();
                //endReuseComment
            }
            //reuseComment
            public void  lockForEdit()
            {
              addButton.setEnabled(false);
              removeButton.setEnabled(false);
              propertiesButton.setEnabled(false);
              viewBox.setEnabled(false);
              autoarrange.setEnabled(false);
            }

            //endReuseComment


		private void _createListModel()
		{
		  listModel.clear();
		  Component[] comps = parentOpdObject.getComponents();

		  for(int i = 0; i < comps.length; i++)
		  {
			if (comps[i] instanceof OpdState)
			{
			  OpdState tmpState = (OpdState)comps[i];
			  listModel.addElement(new StateListCell(tmpState, tmpState.isVisible()));
			}
		  }

		}

	public boolean isAutoarrange()
	{
		return autoarrange.isSelected();
	}

	public int getStatesView()
	{
		return viewBox.getSelectedIndex();
	}


	// Inner classes -- listeners
	class AddButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{

			OpmObject sampleObj = new OpmObject(0, "");
			ObjectEntry sampleObjEntry = new ObjectEntry(sampleObj, myProject);
			OpdObject sampleObjOpd = new OpdObject(sampleObjEntry, myProject, 0, 0);

			OpmState sampleOpmState = new OpmState(0, "");
			StateEntry sampleStateEntry = new StateEntry(sampleOpmState,sampleObj, myProject);
			StateInstance sampleSI = new StateInstance(sampleStateEntry, new OpdKey(0,0),
													sampleObjOpd, myProject);

			StatePropertiesDialog sd = new StatePropertiesDialog(sampleSI, myProject, false,
												 BaseGraphicComponent.SHOW_OK | BaseGraphicComponent.SHOW_CANCEL);

			if(sd.showDialog())
			{
				StateEntry myEntry = myProject.addState(parentOpdObject);

				OpmState nState = (OpmState)myEntry.getLogicalEntity();
				nState.copyPropsFrom(sampleOpmState);


				ObjectEntry parentEntry = (ObjectEntry)myProject.getComponentsStructure().getEntry(parentOpdObject.getEntity().getId());

				for (Enumeration en = parentEntry.getInstances() ; en.hasMoreElements();)
				{
					ObjectInstance parentInstance = (ObjectInstance)en.nextElement();
					OpdObject pObj = (OpdObject)parentInstance.getThing();

					if (pObj.isStatesAutoarrange())
					{
						pObj.fitToContent();
					}

				}

				Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
				Opcat2.getUndoManager().undoableEditHappened(new UndoableEditEvent(myProject, new UndoableAddState(myProject, myEntry)));
				Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
				Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

				_createListModel();
				OpdState newState = null;
				StateInstance stateInst;
				for(Enumeration en = myEntry.getInstances(); en.hasMoreElements();)
				{
					stateInst = (StateInstance)en.nextElement();
					if(stateInst.getKey().getOpdId() == parentOpdObject.getOpdId())
					{
						newState = stateInst.getState();
					}
				}
				if(newState == null)
				{
					JOptionPane.showMessageDialog(StatePanel.this, "Internal Error in\nStatePanel.AddButtonListener.actionPerformed\nPlease contact developers", "Opcat2 - Error", JOptionPane.ERROR_MESSAGE );
					return;
				}
			}
		}
	}

	class RemoveButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int index = (StatePanel.this).statesList.getSelectedIndex();
			if(index == -1)
			{
				return;
			}

			OpdState opdState = (((StateListCell)(StatePanel.this).listModel.get(index)).getOpdState());
			MainStructure ms = myProject.getComponentsStructure();
			StateEntry se = (StateEntry)ms.getEntry(opdState.getEntity().getId());
			StateInstance si = (StateInstance)(se.getInstance(new OpdKey(opdState.getOpdId(), opdState.getEntityInOpdId())));

			CheckResult cr = CheckModule.checkDeletion(si, myProject);
			 if (cr.getResult() == CheckResult.WRONG)
			{
				JOptionPane.showMessageDialog(Opcat2.getFrame(), cr.getMessage(), "Opcat2 - Error" ,JOptionPane.ERROR_MESSAGE);
				return;
			}

			UndoableDeleteState ud = new UndoableDeleteState(myProject, se);

			myProject.deleteState(si);

			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
			Opcat2.getUndoManager().undoableEditHappened(new UndoableEditEvent(myProject, ud));
			Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
			Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
			if (isAutoarrange())
			{
				parentOpdObject.fitToContent();
			}

			(StatePanel.this).listModel.remove(index);
		}
	}

	class PropertiesButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int index = (StatePanel.this).statesList.getSelectedIndex();
			if (index != -1)
			{
				OpdState opdState = (((StateListCell)(StatePanel.this).listModel.get(index)).getOpdState());
				opdState.callPropertiesDialog(BaseGraphicComponent.SHOW_ALL_TABS, BaseGraphicComponent.SHOW_ALL_BUTTONS);
				statesList.repaint();
			}
		}
	}


	public DefaultListModel getListModel()
	{
		return this.listModel;
	}
}




class CheckListCellRenderer extends JCheckBox implements ListCellRenderer
{
	protected Border m_noFocusBorder = new EmptyBorder(1, 3, 1, 1);
	public CheckListCellRenderer() {
		super();
		setOpaque(true);
		setBorder(m_noFocusBorder);
	}
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		setText(value.toString());
		setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
		setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
		StateListCell s = (StateListCell)value;
		setSelected(s.isSelected());
		setFont(list.getFont()); setBorder((cellHasFocus) ? UIManager.getBorder("List.focusCellHighlightBorder") : m_noFocusBorder);
		return this;
	}
}

class CheckListener implements MouseListener, KeyListener
{
	protected JList m_list;
	protected DefaultListModel lm;
	protected OpdProject myProject;
	protected StatePanel parentWin;

	public CheckListener(JList parent, OpdProject prj, StatePanel pWin)
	{
		m_list = parent;
		myProject = prj;
		parentWin = pWin;
	}

	public void mouseClicked(MouseEvent e)
	{
		if (e.getX() < 20)
			doCheck();
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyChar() == ' ')
			doCheck();
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	protected void doCheck()
	{
		int index = m_list.getSelectedIndex();
		if (index < 0)
		{
			return;
		}
		StateListCell choosedState = (StateListCell)m_list.getModel().getElementAt(index);
		if(choosedState.isSelected())
		{
			OpdState opdState = (((StateListCell)(parentWin).listModel.get(index)).getOpdState());
			MainStructure ms = myProject.getComponentsStructure();
			StateEntry se = (StateEntry)ms.getEntry(opdState.getEntity().getId());
			StateInstance si = (StateInstance)(se.getInstance(new OpdKey(opdState.getOpdId(), opdState.getEntityInOpdId())));
			if(si.isRelated())
			{
				JOptionPane.showMessageDialog(parentWin, "You can't hide this state instance\nbecause it related to another thing.", "Opcat2 - Error" ,JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		choosedState.invertSelected();
		m_list.repaint();
	}



}