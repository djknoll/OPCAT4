package simulation;

import exportedAPI.OpdLayout;
import exportedAPI.opcatAPIx.*;

import java.io.*;
import simulation.reader.*;
import simulation.creator.*;
import simulation.gui.SimulationToolBar;
import simulation.gui.StatusLabel;
import simulation.gui.WaitWindow;

import javax.swing.JLabel;
import java.util.*;

import javax.swing.*;
import simulation.plugin.ISimulationPlugin;

import simulation.plugin.*;
import simulation.gui.*;
import simulation.plugin.impl.*;
import simulation.plugin.impl.lifespan.*;
import javax.swing.event.*;

/**
 * <p>Title: Simulation Module</p>
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
public class SimulationRunner{
  public final static class CREATOR_TYPE{
    public final static int RUNTIME = 0;
    public final static int TEST = 1;
    public final static int FILE = 2;
  }

  IXSystem opmSystem;
  QueueReader reader;
  IQueueCreator creator;
  ConfigUI confUI;
  SimulationConfig config = SimulationConfig.getInstance();
  SimulationToolBar toolbar;
  Logger logger;
  JLabel statusLabel;
  JTabbedPane extensionToolsPane;
  int creatorType =-1;
  Lifespan lifespanPlugin = null;
  DebugHandler debugPlugin = null;
  AdvancedOpdMover opdMover = null;
  List<ISimulationPlugin> plugins = null;
  private ArrayList<ISimulationStatusListener> listeners;
  private SimulationStatus myStatus;
  private VersionController versionCtrl;
  private IXFileControl opmFileControl;
  private boolean isPlaying;
  private WaitWindow startMessageWindow;
  
  public SimulationRunner(IXSystem system, IXFileControl opmFileControl, JTabbedPane extensionToolsPane,
		  Action zoomInAction, Action zoomOutAction, Action closeAction) {
    logger = Logger.getInstance();
    opmSystem = system;
    isPlaying = false;
    listeners = new ArrayList<ISimulationStatusListener>();
    myStatus = new SimulationStatus();
    this.opmFileControl = opmFileControl;
    versionCtrl = VersionControllerOn.getInstance(opmSystem, opmFileControl);
    reader = new QueueReader(system);
    confUI = new ConfigUI(this, system.getMainFrame());
    setCreatorType(CREATOR_TYPE.RUNTIME, null, true);
    this.statusLabel = new StatusLabel(this);
    this.extensionToolsPane = extensionToolsPane;
    updateStatus(reader.getStatus());
    reader.addStatusListener(new StatusListener());
    startMessageWindow = new WaitWindow(system.getMainFrame(), "Preparing simulation...");
    toolbar = new SimulationToolBar(this, zoomInAction, zoomOutAction, closeAction);
  }

  public int getCreatorType(){
	  return creatorType;
  }
  
  public String getPlayedFileName(){
	  if (creatorType != CREATOR_TYPE.FILE){
		  return "No file is playing";
	  }else{
		 return ((FileCreator) creator).getFileName();
	  }
  }
  
  public void setLayoutType(int layoutType){
	  if (opdMover != null && isPlaying){
		  opdMover.setLayoutType(layoutType);
	  }
  }
  
  public void setCreatorType(int type, File tasksFile, boolean enforceCreation){
    if (creatorType == type && !enforceCreation){
      return;
    }

    //stop();
    reader.stop();
    switch (type){
      case CREATOR_TYPE.FILE : {
        creator = new FileCreator(tasksFile, opmSystem);
        break;
      }
      case CREATOR_TYPE.TEST : {
        creator = new TestCreator(opmSystem);
        break;
      }
      case CREATOR_TYPE.RUNTIME : {
        creator = new RuntimeCreator(opmSystem, config.getCreationConfig());
        break;
      }
      default:{
        throw new IllegalArgumentException("SimulationRunner:setCreatorType - unknown creator type "+type);
      }

    }

    creatorType = type;
    reader.setQueueCreator(creator);
  }
  
	private void notifyListeners(ISimulationStatus status) {
		synchronized (listeners) {
			for (int i = 0; i < listeners.size(); i++) {
				listeners.get(i).statusChanged(status);
			}
		}
	}

	public void addStatusListener(ISimulationStatusListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void removeStatusListener(ISimulationStatusListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}
	public boolean isPlaying(){
		return isPlaying;
	}
	
	public JToolBar getToolBar(){
		return toolbar;
	}
	
	public JLabel getStatusLabel(){
		return statusLabel;
	}
  

  public void doUserActivation(){
    ExternalInput input = new ExternalInput();
    input.activatedEnitities = getSelectionInCurrentOPD();
    reader.activate(input);

  }

  public void doUserDeactivation(){
    ExternalInput input = new ExternalInput();
    input.terminatedEntities = getSelectionInCurrentOPD();
    reader.activate(input);
  }

  private List<IXInstance> getSelectionInCurrentOPD(){
    ArrayList<IXInstance> selection = new ArrayList<IXInstance>();
    IXOpd opd = opmSystem.getCurrentIXOpd();
    Enumeration items = opd.getSelectedItems();
    while (items.hasMoreElements()){
      selection.add((IXInstance)items.nextElement());
    }
    return selection;
  }

  public void setContinousPlay(boolean isContinous){
    reader.setContinousPlay(isContinous);
  }

  public boolean isContinouosPlay(){
    return reader.getStatus().isContinuous();
  }

  public void setQuickRunMode(boolean isQuickRun){
	  reader.setQuickRunMode(isQuickRun);
  }

  public boolean isQuickRunMode(){
	  return reader.getStatus().isQuickRunMode();
  }
 
  public void setRunOnCopy(boolean isRunOnCopy){
	  if (isRunOnCopy == isRunOnCopy()){
		  return;
	  }
	  
	  if(!isRunOnCopy){
		  versionCtrl = VersionController.getInstance(opmSystem);
		  return;
	  }

	  versionCtrl = VersionControllerOn.getInstance(opmSystem, opmFileControl);
  }

  public boolean isRunOnCopy() {
	  if(versionCtrl instanceof VersionControllerOn){
		  return true;
	  }
	  
	  return false;
  }
  
  public void saveAsXML(File targetFile){
    reader.saveAsXML(targetFile, opmSystem);
  }

  public void setPlayingSpeed(double speed){
    reader.setPlayingSpeed(speed);
  }


  // Interface for Opcat

  public synchronized void stop(){
	  if (!isPlaying) {
		  return;
	  }
		
	  isPlaying = false;
	  
    logger.print("SimulationRunner:stop - new simulation stopped", 1);
    reader.stop();
    if (lifespanPlugin != null){
      extensionToolsPane.remove(lifespanPlugin.getUIRepresentaion());
      lifespanPlugin = null;
    }

    if (debugPlugin != null){
      extensionToolsPane.remove(debugPlugin.getUIRepresentaion());
      debugPlugin = null;
    }

    destroyPlugins();

    Enumeration entries = opmSystem.getIXSystemStructure().getElements();
    while (entries.hasMoreElements()){
      IXEntry currEntry = (IXEntry)entries.nextElement();
      if (currEntry instanceof IXThingEntry){
      	((IXThingEntry)currEntry).setNumberOfAnimatedInstances(0);
      }
      
      Enumeration instances = currEntry.getInstances();
      while (instances.hasMoreElements()){
        IXInstance currInstance = (IXInstance) instances.nextElement();
        if (currInstance instanceof IXConnectionEdgeInstance){
          ((IXConnectionEdgeInstance)currInstance).stopTesting(0);
        }else{
          if (currInstance instanceof IXLinkInstance){
            ((IXLinkInstance)currInstance).stopTesting();
          }
        }
      }
    }
    
  }
  public synchronized void stopWithRelease(){
	  stop();
	  versionCtrl.releaseSystem();
  }

  public synchronized void start() {
		if (isPlaying) {
			return;
		}
		
		isPlaying = true;
		logger.print("SimulationRunner:start - new simulation started", 1);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				startMessageWindow.setVisible(true);
			}
		});
		
		Thread runner = new Thread(){
			public void run(){
			    opmSystem = versionCtrl.getSystem();
			    reader = new QueueReader(opmSystem);
			    reader.setPlayingSpeed(myStatus.getPlayingSpeed());
			    reader.setContinousPlay(myStatus.isContinuous);
			    reader.setQuickRunMode(myStatus.isQuickRunMode);
			    reader.addStatusListener(new StatusListener());
			    setCreatorType(CREATOR_TYPE.RUNTIME, null, true);
				creator.reset();
				reader.setQueueCreator(creator);

				initPlugins();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (config.showLifespan){
							extensionToolsPane.add("Lifespan", lifespanPlugin.getUIRepresentaion());
						}
						extensionToolsPane.add("Debug Info", debugPlugin.getUIRepresentaion());
						startMessageWindow.setVisible(false);
					}
				});

				reader.start(plugins);
				if (config.getCreationConfig().isAutoInitiated()) {
					boolean isContinous = reader.getStatus().isContinuous();
					reader.setContinousPlay(false);
					reader.playForward();
					reader.setContinousPlay(isContinous);
				}
				
			}
		};
		
		runner.start();
	}

  private void initPlugins(){
	plugins = new ArrayList<ISimulationPlugin>();

	plugins.add(new BreakpointsHandler(opmSystem));
	opdMover = new AdvancedOpdMover(opmSystem);
	plugins.add(opdMover);

	if (lifespanPlugin != null) {
		extensionToolsPane.remove(lifespanPlugin.getUIRepresentaion());
		lifespanPlugin = null;
	}
	
	if (config.showLifespan){
		lifespanPlugin = new Lifespan(opmSystem);
		plugins.add(lifespanPlugin);
	}

	if (debugPlugin != null) {
		extensionToolsPane.remove(debugPlugin.getUIRepresentaion());
	}
	debugPlugin = new DebugHandler(opmSystem);
	plugins.add(debugPlugin);

    Iterator<ISimulationPlugin> iter = plugins.iterator();
    while (iter.hasNext()){
      ISimulationPlugin currPlugin = iter.next();
      if (currPlugin instanceof IObservingPlugin){
        ((IObservingPlugin)currPlugin).init(reader);
        continue;
      }
      if (currPlugin instanceof IChangingPlugin){
        ((IChangingPlugin)currPlugin).init(reader, reader);
        continue;
      }
    }
  }

  private void destroyPlugins(){
    if (plugins == null){
      return;
    }

    Iterator<ISimulationPlugin> iter = plugins.iterator();
    while (iter.hasNext()){
      ISimulationPlugin currPlugin = iter.next();
      currPlugin.destroy();
    }
  }
  
  
  public boolean isPaused(){
    return reader.getStatus().isPaused();
  }

  public void pause(){
    logger.print("SimulationRunner:pause - simulation was paused", 1);
    reader.pause();
  }


  public void resume(){
    logger.print("SimulationRunner:resume - simulation was resumed", 1);
    reader.resume();
  }

  public void playForward(int numOfSteps){
    logger.print("SimulationRunner:playForward - simulation played forward for "+numOfSteps +
                 " steps ", 1);
    reader.playForward();
  }

  public void playBackward(int numOfSteps){
    logger.print("SimulationRunner:playBackward - simulation played backward for "+numOfSteps +
                 " steps ", 1);
    reader.playBackward();
  }

  public void showSettings(){
    confUI.setVisible(true);
  }
  
  public ISimulationStatus getStatus(){
	  return myStatus;
  }

  private void updateStatus(final IQueueReaderStatus status){
      myStatus.isStarted = status.isStarted();
      myStatus.isPaused = status.isPaused();
      myStatus.isContinuous = status.isContinuous();
      myStatus.isQuickRunMode = status.isQuickRunMode();
      myStatus.timeline = status.getTimeline();
      myStatus.isPlayingForward = status.isPlayingForward();
      myStatus.playingSpeed = status.getPlayingSpeed();
      notifyListeners(myStatus);
  }
  
  class StatusListener implements IQueueReaderStatusListener {
    public void statusChanged(IQueueReaderStatus status) {
      updateStatus(status);
    }
  }
  
  class SimulationStatus implements ISimulationStatus{
    boolean isStarted;
    boolean isPaused;
    boolean isContinuous;
    boolean isQuickRunMode;
    int timeline;
    boolean isPlayingForward;
    double playingSpeed;

    public SimulationStatus() {
      isStarted = false;
      isPaused = false;
      isContinuous = false;
      timeline = 0;
      isPlayingForward = true;
      playingSpeed = 1.0;
    }

    public boolean isStarted(){
      return this.isStarted;
    }

    public boolean isPaused(){
      return this.isPaused;
    }

    public boolean isContinuous(){
      return this.isContinuous;
    }
    
    public boolean isQuickRunMode(){
    	return this.isQuickRunMode;
    }

    public int getTimeline(){
      return timeline;
    }

    public boolean isPlayingForward(){
      return isPlayingForward;
    }

    public double getPlayingSpeed(){
      return playingSpeed;
    }
  }
  
}
