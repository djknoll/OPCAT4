package simulation.plugin.impl;

import simulation.plugin.*;
import exportedAPI.opcatAPIx.*;
import simulation.reader.IQueueReaderStatus;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import gui.opdProject.*;
import simulation.util.MiscUtils;
import simulation.tasks.*;
import simulation.tasks.logic.DebugInfoEntry;
import simulation.tasks.logic.DebugInfoTask;

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
public class DebugHandler implements IObservingPlugin {
  public final static int DEFAULT_NUM_OF_DISPLAYED_MESSAGES = 3;
  private IXSystem opmSystem;
  private IControlCommandHandler commandHandler;
  private JComponent uiRepresentation;
  JTextPane textPane = new JTextPane();
  private ArrayList<IDebugInfo> debugHistory;
  private int numOfDisplayedMessages;
  private HTMLPrinter prettyPrinter;

  public DebugHandler(IXSystem opmSystem){
    this.opmSystem = opmSystem;
    prettyPrinter = new HTMLPrinter(opmSystem);
    debugHistory = new ArrayList<IDebugInfo>();
    numOfDisplayedMessages = DEFAULT_NUM_OF_DISPLAYED_MESSAGES;
    textPane.setContentType("text/html");
    textPane.setEditable(false);
    uiRepresentation = new JScrollPane(textPane);
  }

  /**
   * getName
   *
   * @return String
   */
  public String getName() {
    return "Debug Info";
  }

  public void destroy(){}

  public boolean isSynchronious(){
  	return false;
  }

  public JComponent getUIRepresentaion(){
    return uiRepresentation;
  }

  public void init(IControlCommandHandler commandHandler){
    this.commandHandler = commandHandler;
  }

  public void readerStatusChanged(IQueueReaderStatus status){}

  public int getNumOfDisplayedMessages(){
    return numOfDisplayedMessages;
  }

  public void setNumOfDisplayedMessages(int numOfDisplayedMessages){
    this.numOfDisplayedMessages = numOfDisplayedMessages;
  }

  private IDebugInfo getDebugInfo4Task(ILogicalTask task){
    if (task.getType() !=  ILogicalTask.TYPE.DEBUG_INFO){
      return null;
    }

    return (IDebugInfo)task;
  }

   /**
   * processTaskForward
   *
   * @param task ILogicalTask
   * @todo Implement this simulation.plugin.IObservingPlugin method
   */
  public void processTaskForward(ILogicalTask task) {
    IDebugInfo debugInfo = getDebugInfo4Task(task);
    if (debugInfo == null){
      return;
    }

    debugHistory.add(debugInfo);
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        textPane.setText(prettyPrinter.getHTML());
      }
    });
  }


  /**
   * processTaskBackward
   *
   * @param task ILogicalTask
   * @todo Implement this simulation.plugin.IObservingPlugin method
   */
  public void processTaskBackward(ILogicalTask task) {
    IDebugInfo debugInfo = getDebugInfo4Task(task);
    if (debugInfo == null){
      return;
    }

    debugHistory.remove(debugHistory.size() - 1);
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        textPane.setText(prettyPrinter.getHTML());
      }
    });
  }

  public String getStringDescription(){
    String description = "";

    int numOfPrintedMessages = 0;
    for (int i = debugHistory.size() - 1;
         (i >= 0) && (numOfPrintedMessages <= numOfDisplayedMessages); i--){
      description += debugHistory.get(i).toString();
      numOfPrintedMessages++;
    }
    return description;
  }


  class HTMLPrinter{
    protected OplColorScheme colorScheme;
    protected String startHTML;
    protected String endHTML;
    protected String endLine;
    protected String margin;

    public HTMLPrinter(IXSystem system) {
      colorScheme = ((OpdProject)system).getOplColorScheme();
      startHTML = "<HTML><HEAD></HEAD><BODY>";
      endHTML = "</BODY></HTML>";
      margin = "&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp ";
      endLine = colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).
          openingHTMLFontTag()+"."+this.colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).
          closingHTMLFontTag()+"<BR>";
    }

    private int getStyle4Entity(IXEntry entity){
      if (entity instanceof IXObjectEntry){
        return OplColorScheme.OBJECT_STYLE;
      }

      if (entity instanceof IXProcessEntry){
        return OplColorScheme.PROCESS_STYLE;
      }

      if (entity instanceof IXStateEntry){
        return OplColorScheme.STATE_STYLE;
      }

      return OplColorScheme.DEFAULT_STYLE;
    }

    private String getDebugSingleEntryHTML(DebugInfoEntry entry){
      String entityType = MiscUtils.getEntityType(entry.getNonsatisfiedEntry());
      int nonsatisfiedEntryStyle = getStyle4Entity(entry.getNonsatisfiedEntry());

      switch (entry.getReason()){
        case DebugInfoEntry.REASON.USER_INPUT_REQUIRED : {
          return margin +
              colorScheme.getAttribute(nonsatisfiedEntryStyle).openingHTMLFontTag()+
              entityType+" "+entry.getNonsatisfiedEntry().getName()+
              colorScheme.getAttribute(nonsatisfiedEntryStyle).closingHTMLFontTag() +
              colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).openingHTMLFontTag()+
              " requires user input" + colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).closingHTMLFontTag()+
              endLine;
        }
        case DebugInfoEntry.REASON.LINK_NOT_SATISFIED : {
          String causeType = MiscUtils.getEntityType(entry.getCauseOfNonsatisfiedEntry());
          int causeStyle = getStyle4Entity(entry.getCauseOfNonsatisfiedEntry());
          return margin +
              colorScheme.getAttribute(nonsatisfiedEntryStyle).openingHTMLFontTag()+
              entityType+
              colorScheme.getAttribute(nonsatisfiedEntryStyle).closingHTMLFontTag() +
              colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).openingHTMLFontTag()+
              " is not satisfied because "+
              colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).closingHTMLFontTag()+
              colorScheme.getAttribute(causeStyle).openingHTMLFontTag()+
              causeType + " " + entry.getCauseOfNonsatisfiedEntry().getName()+
              colorScheme.getAttribute(causeStyle).closingHTMLFontTag()+
              colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).openingHTMLFontTag()+
              " has no instances"+
              colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).closingHTMLFontTag()+
              endLine;
        }
        case DebugInfoEntry.REASON.SHOULD_BE_MANUALLY_ACTIVATED : {
          String causeType = MiscUtils.getEntityType(entry.getCauseOfNonsatisfiedEntry());
          int causeStyle = getStyle4Entity(entry.getCauseOfNonsatisfiedEntry());
          return margin +
              colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).openingHTMLFontTag()+
              "Above entity should be manually activated because of "+
              colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).closingHTMLFontTag()+
              colorScheme.getAttribute(nonsatisfiedEntryStyle).openingHTMLFontTag()+
              entityType+
              colorScheme.getAttribute(nonsatisfiedEntryStyle).closingHTMLFontTag() +
              endLine;
        }
        	
        default : throw new IllegalStateException("Reason of type "+entry.getCauseOfNonsatisfiedEntry()+" is unknown ");
      }
    }

    private String getHTML4Task(DebugInfoTask debugInfo){
      String entityType = MiscUtils.getEntityType(debugInfo.getEntity());
      int entityStyle = getStyle4Entity(debugInfo.getEntity());

      String description = colorScheme.getAttribute(entityStyle).openingHTMLFontTag()+
              entityType+" "+debugInfo.getEntity()+
              colorScheme.getAttribute(entityStyle).closingHTMLFontTag() +
              colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).openingHTMLFontTag()+
              " failed to run (time = "+debugInfo.getTime()+") for following reasons :"+
              colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).closingHTMLFontTag()+"<BR>";


      Iterator<DebugInfoEntry> iter = debugInfo.getDebugInfo();
      while (iter.hasNext()){
        description += getDebugSingleEntryHTML(iter.next());
      }
      return description;

    }

    public String getHTML(){
      String description = startHTML;

      int numOfPrintedMessages = 0;
      for (int i = debugHistory.size() - 1;
           (i >= 0) && (numOfPrintedMessages <= numOfDisplayedMessages); i--){
        description += getHTML4Task((DebugInfoTask)debugHistory.get(i));
        numOfPrintedMessages++;
      }

      description += endHTML;
      return description;
    }
  }
}
