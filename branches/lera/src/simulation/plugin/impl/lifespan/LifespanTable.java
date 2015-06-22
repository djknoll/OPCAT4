package simulation.plugin.impl.lifespan;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import simulation.plugin.*;
import javax.swing.table.*;

import com.sciapp.comparators.StringComparator;
import com.sciapp.filter.CustomPopupFilterHeaderModel;
import com.sciapp.filter.FilterHeaderModel;
import com.sciapp.filter.FilterTableHeader;
import com.sciapp.filter.TableFilter;
import com.sciapp.treetable.DefaultFilterTreeTableModel;
import com.sciapp.treetable.DefaultMutableTreeTableModel;
import com.sciapp.treetable.DefaultSortTreeTableModel;
import com.sciapp.treetable.MutableTreeTableModel;
import com.sciapp.treetable.TreeTable;

import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXProcessEntry;
import exportedAPI.opcatAPIx.IXStateEntry;

public class LifespanTable extends TreeTable  implements IHistoryListener{
  private final static Color OBJECT_COLOR = new Color(0, 109, 0);
  private final static Color PROCESS_COLOR = new Color(0, 0, 120);
	private final static Color STATE_COLOR = new Color(91, 91, 0);
  
  public final static int SCALE_FACTOR = 50;
  private Hashtable<Long, HistoryPainter> paintersTable;
  private RunHistory history;
  private TableCellRenderer historyRenderer;
  private TableCellRenderer entityNameRenderer;
  private TableCellRenderer entityTypeRenderer;
  
  private LifespanTableModel model;
  private int historyCellWidth = 0;

  public LifespanTable(RunHistory history, LifespanTableModel model) {
    super(model);
    this.model = model;
    DefaultFilterTreeTableModel filter = new DefaultFilterTreeTableModel(model);

    DefaultSortTreeTableModel sort = new DefaultSortTreeTableModel(filter); 
    sort.getSortTableModel().setSortableColumn(2, false);
    sort.getSortTableModel().setComparator(0, new HistoryEntryComparator());    
    
    setTreeTableModel(sort);
    sort.getSortTableModel().setHeader(getTableHeader());
    
    CustomPopupFilterHeaderModel filterHeaderModel  = new CustomPopupFilterHeaderModel();
    filterHeaderModel.setTableHeader((FilterTableHeader)getTableHeader());
    filterHeaderModel.attachToTable(this, filter.getFilterTableModel());

//    this.setDefaultRenderer(RunHistoryEntry.class, new HistoryRenderer());
    this.history = history;
    this.setRowHeight(17);
    paintersTable = new Hashtable<Long, HistoryPainter>();
    historyRenderer = new HistoryRenderer();
    entityNameRenderer = new EnityNameRenderer();
    entityTypeRenderer = new TypeRenderer();

    setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    getColumnModel().getColumn(0).setPreferredWidth(230);
    getColumnModel().getColumn(0).setMinWidth(230);
    getColumnModel().getColumn(1).setPreferredWidth(80);
    getColumnModel().getColumn(1).setMinWidth(80);
    getColumnModel().getColumn(1).setMaxWidth(80);
    getColumnModel().getColumn(2).setHeaderRenderer(new TimeLineRenderer());
    
    getTableHeader().setReorderingAllowed(false);
    setShowDummyColumn(false);
    history.addHistoryListener(this);
  }

  public void entryChanged(long id) {}
  public void childAdded(long parentId, long childId){}
  
  public void timeChanged(final int time) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        historyCellWidth = Math.max(500, time / SCALE_FACTOR);
        getColumnModel().getColumn(2).setPreferredWidth(historyCellWidth);
        getColumnModel().getColumn(2).setMinWidth(historyCellWidth);
      }
    });
  }

  public TableCellRenderer getCellRenderer(int row, int column) {
	  if ( (column == 0)) {
		  return entityNameRenderer;
	  }
	  	  
	  if ( (column == 1)) {
		  return entityTypeRenderer;
	  }

	  if ( (column == 2)) {
		  return historyRenderer;
	  }
	  // else...
	  return super.getCellRenderer(row, column);
  }

  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public void refreshTable(){
	  updateUI();
    //model.refreshModel();
  }

  class EnityNameRenderer implements TableCellRenderer {

		JLabel label = new JLabel();

		public EnityNameRenderer() {
			label.setFont(new Font("Helvetica", Font.PLAIN, 12));
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			RunHistoryEntry paintedEntry = (RunHistoryEntry) value;
			label.setText(paintedEntry.getOpmEntity().getName());
			if (paintedEntry.getOpmEntity() instanceof IXObjectEntry) {
				label.setForeground(OBJECT_COLOR);
			}

			if (paintedEntry.getOpmEntity() instanceof IXProcessEntry) {
				label.setForeground(PROCESS_COLOR);
			}
			
			if (paintedEntry.getOpmEntity() instanceof IXStateEntry) {
				label.setForeground(STATE_COLOR);
			}
			

			return label;
		}
  }

  class TypeRenderer implements TableCellRenderer {

		JLabel label = new JLabel();

		public TypeRenderer() {
			label.setFont(new Font("Helvetica", Font.PLAIN, 12));
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			String paintedString = (String) value;

			if (LifespanTableModel.OBJECT_TYPE.equals(paintedString)) {
				label.setForeground(OBJECT_COLOR);
				label.setText("  "+LifespanTableModel.OBJECT_TYPE);
			}

			if (LifespanTableModel.PROCESS_TYPE.equals(paintedString)) {
				label.setForeground(PROCESS_COLOR);
				label.setText("  "+LifespanTableModel.PROCESS_TYPE);
			}
			
			if (LifespanTableModel.STATE_TYPE.equals(paintedString)) {
				label.setForeground(STATE_COLOR);
				label.setText("  "+LifespanTableModel.STATE_TYPE);
			}
			

			return label;
		}
  }
  
  class TimeLineRenderer implements TableCellRenderer {
	  TimeLinePainter painter; 
	  public TimeLineRenderer() {
		  painter = new TimeLinePainter();
	  }

	  public Component getTableCellRendererComponent(JTable table, Object value,
			  boolean isSelected, boolean hasFocus,
			  int row, int column) {
		  return painter;
	  }
  }

  class TimeLinePainter extends JComponent {
	    private final Font NUMBERS_FONT = new Font("Normal", Font.PLAIN, 10);

	    public TimeLinePainter(){
	      super();
	    }
	    
	    protected void paintComponent(Graphics g) {
	      super.paintComponent(g);
//	      g.setColor(new Color(191, 191, 233));
//	      g.fillRect(0, 0, getWidth(), getHeight());
	      g.setColor(Color.BLACK);
	      g.setFont(NUMBERS_FONT);
	      FontMetrics fm = g.getFontMetrics();	      
	      int halfHeight = getHeight() / 2;
	      g.drawLine(0, getHeight() - 1, historyCellWidth, getHeight() - 1);
	      int numOfSteps = (historyCellWidth * SCALE_FACTOR) / 1000;
	      for (int i = 0; i < numOfSteps; i++){
	    	  int x = (i * 1000)/SCALE_FACTOR;
	    	  g.drawLine(x, getHeight(), x, halfHeight);
	    	  String drawnString = Integer.toString(i);
	    	  g.drawString(drawnString, x + 1 - fm.stringWidth(drawnString) / 2, halfHeight - 1);
	    	  int x1 = x + (500/SCALE_FACTOR);
			  int height = getHeight() - (int)(halfHeight / 1.4);
	    	  g.drawLine(x1, getHeight(), x1,  height);
	      }
	    }
  }

  
  class HistoryRenderer implements TableCellRenderer {
    public HistoryRenderer() {
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus,
        int row, int column) {
    	RunHistoryEntry paintedEntry = (RunHistoryEntry) value;
    	HistoryPainter painter = paintersTable.get(paintedEntry.getOpmEntity().getId());
    	if (painter == null) {
    		painter = new HistoryPainter( paintedEntry, history);
    		paintersTable.put(paintedEntry.getOpmEntity().getId(), painter);
    	}

    	return painter;
    }
  }

  class HistoryPainter extends JComponent {
    public final static int PREFERRED_HEIGHT = 10;
    RunHistoryEntry entry;
    RunHistory history;

    public HistoryPainter(RunHistoryEntry entry, RunHistory history) {
      super();
      this.entry = entry;
      this.history = history;
    }
    
    public long getId(){
    	return entry.getOpmEntity().getId();
    }

    public Dimension getPreferredSize() {
      return new Dimension(PREFERRED_HEIGHT, historyCellWidth);
    }

    public Dimension getMaximumSize() {
      return getPreferredSize();
    }

    public Dimension getMinimumSize() {
      return getPreferredSize();
    }

    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      int offset = Math.max(0, (getHeight() - PREFERRED_HEIGHT) / 2);
      if (entry.getOpmEntity() instanceof IXObjectEntry){
        g.setColor(OBJECT_COLOR.brighter());
      }

      if (entry.getOpmEntity() instanceof IXProcessEntry){
        g.setColor(PROCESS_COLOR.brighter());
      }
      
      if (entry.getOpmEntity() instanceof IXStateEntry){
          g.setColor(STATE_COLOR.brighter());
      }
      
      
      Iterator<RunHistoryEvent> iter = entry.getEvents();
      while (iter.hasNext()) {
        int startActivationTime = -1;
        int endActivationTime = -1;
        RunHistoryEvent currEntry = iter.next();
        if (currEntry.getEventType() != ILogicalTask.TYPE.ACTIVATION &&
        		currEntry.getEventType() != ILogicalTask.TYPE.CREATION) {
          continue;
        }

        startActivationTime = currEntry.getTime();
        while (iter.hasNext()) {
          currEntry = iter.next();
          if (currEntry.getEventType() == ILogicalTask.TYPE.TERMINATION ||
        		  currEntry.getEventType() == ILogicalTask.TYPE.DELETION) {
            endActivationTime = currEntry.getTime();
            break;
          }
        }

        if (endActivationTime == -1) {
          endActivationTime = history.getSystemTime();
        }

        g.fillRect(startActivationTime / SCALE_FACTOR, offset,
                   (endActivationTime - startActivationTime) / SCALE_FACTOR,
                   PREFERRED_HEIGHT);

      }
    }
  }
  
  class HistoryEntryComparator implements Comparator {
		StringComparator strCompartor = new StringComparator();

		public int compare(Object o1, Object o2) {
			if (!(o1 instanceof RunHistoryEntry)
					|| !(o2 instanceof RunHistoryEntry)) {
				return 0;
			}

			return strCompartor.compare(((RunHistoryEntry) o1).getOpmEntity().getName(),
							((RunHistoryEntry) o2).getOpmEntity().getName());
		}
  }

}
