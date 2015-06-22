package simulation.plugin.impl.lifespan;

import simulation.plugin.*;
import exportedAPI.opcatAPIx.*;
import simulation.reader.IQueueReaderStatus;
import javax.swing.*;

import com.sciapp.filter.CustomPopupFilterHeaderModel;
import com.sciapp.filter.FilterHeaderModel;
import com.sciapp.filter.FilterTableHeader;
import com.sciapp.table.AdvancedJScrollPane;
import com.sciapp.table.locked.LockedTableModel;
import com.sciapp.treetable.DefaultFilterTreeTableModel;
import com.sciapp.treetable.DefaultSortTreeTableModel;

import java.awt.event.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Yevgeny Yaroker
 * @version 1.0
 */
public class Lifespan implements IObservingPlugin {
  private IXSystem opmSystem;
  private IControlCommandHandler commandHandler;
  private RunHistory history;
  private LifespanTable myTable;
  private AdvancedJScrollPane uiRepresentation;
  private Timer refreshTimer;
  private boolean dataWasChanged = false;

  public Lifespan(IXSystem opmSystem){
    this.opmSystem = opmSystem;
    history = new RunHistory(opmSystem);
    LifespanTableModel model = new LifespanTableModel(history);
    myTable = new LifespanTable(history, model);

    uiRepresentation = new AdvancedJScrollPane(myTable);
    LockedTableModel lockedModel = uiRepresentation.getLockedModel();
    lockedModel.setLockedColumns(2, LockedTableModel.LEFT_DIRECTION);
    
    refreshTimer = new Timer(500,
                             new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if (!dataWasChanged){
          return;
        }

        dataWasChanged = false;
        myTable.refreshTable();
      }
    });

    refreshTimer.start();
  }
  /**
   * getName
   *
   * @return String
   * @todo Implement this simulation.plugin.ISimulationPlugin method
   */
  public String getName() {
    return "Lifespan";
  }
  
  public boolean isSynchronious(){
  	return false;
  }
  

  public void destroy(){
    refreshTimer.stop();
  }

  public JComponent getUIRepresentaion(){
    return uiRepresentation;
  }

  public void init(IControlCommandHandler commandHandler){
    this.commandHandler = commandHandler;
  }

  public void readerStatusChanged(IQueueReaderStatus status){
    history.setSystemTime(status.getTimeline());
    dataWasChanged = true;
  }

  private RunHistoryEvent getEvent4Task(ILogicalTask task){
    if (!(task.getEntity() instanceof IXProcessEntry || task.getEntity() instanceof IXObjectEntry ||
    		task.getEntity() instanceof IXStateEntry)){
      return null;
    }

    return new RunHistoryEvent(task.getEntity(), task.getTime(), task.getType());
  }

   /**
   * processTaskForward
   *
   * @param task ILogicalTask
   * @todo Implement this simulation.plugin.IObservingPlugin method
   */
  public void processTaskForward(ILogicalTask task) {
    RunHistoryEvent event = getEvent4Task(task);
    if (event == null){
      return;
    }

    history.addHistoryEvent(task, event);
    dataWasChanged = true;
  }


  /**
   * processTaskBackward
   *
   * @param task ILogicalTask
   * @todo Implement this simulation.plugin.IObservingPlugin method
   */
  public void processTaskBackward(ILogicalTask task) {
    RunHistoryEvent event = getEvent4Task(task);
    if (event == null){
      return;
    }

    history.removeHistoryEvent(task, event);
    dataWasChanged = true;
  }
}
