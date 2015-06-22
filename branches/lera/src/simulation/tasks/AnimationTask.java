package simulation.tasks;

import simulation.Logger;
import exportedAPI.opcatAPIx.*;
import exportedAPI.OpdKey;

import org.jdom.Element;
import org.w3c.dom.NamedNodeMap;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import simulation.reader.*;
import simulation.tasks.SimulationTask.XML_TAGS;


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
public abstract class AnimationTask extends SimulationTask{

  public final static class XML_TAGS{
    public final static String TASK_JAVA_CLASS = "javaClass";
    public final static String START_TIME = "startTime";
    public final static String DURATION = "duration";
    public final static String ENTITY_ID = "entityId";
    public final static String IS_MANUALLY_ACTIVATED = "isManuallyActivated";
  }

  private final static int ATOMIC_DURATION = 20;
  private Timer animationTimer;
  private boolean isDuringExecution;
  private boolean isForwardExecuted;
  private int timeLine;
  private final int animationDuration;
  protected IXEntry opmEntity;

  public AnimationTask(int startTime, int duration, IXEntry entity) {
    super(startTime, duration);
    this.opmEntity = entity; 
    if (duration < ATOMIC_DURATION){
      animationDuration = 0;
    }else{
      animationDuration = duration;
    }
    isDuringExecution = false;
    isForwardExecuted = false;
    timeLine = 0;
  }
  
  public IXEntry getEntity(){
    return opmEntity;
  }
  

  public AnimationTask(NamedNodeMap attributes, IXSystem opmSystem, HashMap<String, SimulationTask> previouselyCreatedTasks) {
    super(attributes, opmSystem, previouselyCreatedTasks);
    long entityId = Long.parseLong(attributes.getNamedItem(XML_TAGS.ENTITY_ID).getNodeValue());
    opmEntity = opmSystem.getIXSystemStructure().getIXEntry(entityId);
    if (getDuration() < ATOMIC_DURATION){
      animationDuration = 0;
    }else{
      animationDuration = getDuration();
    }

    isDuringExecution = false;
    isForwardExecuted = false;
    timeLine = 0;
  }
  
  public void fillXML(Element task){
  	super.fillXML(task);
  	task.setAttribute(XML_TAGS.ENTITY_ID, Long.toString(opmEntity.getId()));
  }
  

  public synchronized void executeForward(final int stepDuration, double executionSpeed, RuntimeInfoTable table){
    logger.print("AnimationTask:executeForward - "+toString()+" 'stepDuration = "+stepDuration ,4);

    if (!isDuringExecution){
      isDuringExecution = true;
      isForwardExecuted = true;
      timeLine = 0;
    }
    final boolean isDirectionChanged;
    if (!isForwardExecuted){
      isDirectionChanged = true;
    }else{
      isDirectionChanged = false;
    }

    isForwardExecuted = true;

    doAnimation(timeLine, Math.min(animationDuration, (timeLine + stepDuration)), isDirectionChanged, executionSpeed);

    timeLine = timeLine + stepDuration;

    logger.print("AnimationTask:executeForward - timeLine "+timeLine+" duration "+getDuration(), 5);
    if (timeLine >= animationDuration){
      finishForwardAnimation();	
      logger.print("AnimationTask:executeForward - "+toString()+" process finished" ,2);
      timeLine = animationDuration;
      isDuringExecution = false;
    }
  }

  /**
   * doAnimation
   *
   */
  private void doAnimation(int startTime, int endTime,
                           boolean isDirectionChanged, double executionSpeed){
    if (animationTimer != null){
      animationTimer.stop();
    }

    int stepDuration = (endTime - startTime);
    if (startTime <= 0){
//      animateBackward(0); // workaround for the animation problems
      animateForward((int)Math.round(animationDuration / executionSpeed));
    }else{
      if (isDirectionChanged){
//        animateBackward(0); // workaround for the animation problems
        int animationTime = animationDuration - timeLine + stepDuration;
        animateForward((int)Math.round(animationTime / executionSpeed));
      }else{
        continueAnimation();
      }
    }

    if (endTime < animationDuration){
      animationTimer = new Timer( stepDuration, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          pauseAnimation();
        }
      });
      animationTimer.setRepeats(false);
      animationTimer.setCoalesce(false);
      animationTimer.start();
    }
  }

  public synchronized void executeBackward(final int stepDuration, double executionSpeed, RuntimeInfoTable table){
    logger.print("AnimationTask:executeBackward - "+toString()+" 'stepDuration = "+stepDuration ,4);
    if (!isDuringExecution){
      isDuringExecution = true;
      isForwardExecuted = false;
      timeLine = animationDuration;
    }
    final boolean isDirectionChanged;
    if (isForwardExecuted){
      isDirectionChanged = true;
    }else{
      isDirectionChanged = false;
    }

    isForwardExecuted = false;

    undoAnimation(timeLine, Math.max(0, (timeLine - stepDuration)),
                  isDirectionChanged, executionSpeed);
    timeLine = timeLine - stepDuration;
    if (timeLine <= 0){
      finishBackwardAnimation();
      logger.print("AnimationTask:executeBackward - "+toString()+" process finished" ,2);
      timeLine = 0;
      isDuringExecution = false;
    }
  }

  /**
   * undoAnimation
   *
   */
  private void undoAnimation(int startTime, int endTime,
                             boolean isDirectionChanged, double executionSpeed){
    logger.print("SimulationTask:undoAnimation startTime "+startTime+" endtime "+endTime+
                 " directcha "+isDirectionChanged+" timeline "+timeLine+"  "+toString(),1);
    if (animationTimer != null){
      animationTimer.stop();
    }

    int stepDuration = (startTime - endTime);
    if (startTime >= animationDuration){
//      animateForward(0); // workaround for the animation problems
      animateBackward((int)Math.round(animationDuration / executionSpeed));  //nonblocking
    }else{
      if (isDirectionChanged){
        if (timeLine + stepDuration > animationDuration){
          logger.print("----- Violation "+toString()+" timli "+timeLine+"  step dur "+stepDuration,1);
        }
//        animateForward(0); // workaround for the animation problems
        int animationTime = timeLine + stepDuration;
        animateBackward((int)Math.round(animationTime / executionSpeed));
      }else{
        continueAnimation();
      }
    }

    if (endTime > 0 && animationDuration > 0){
      animationTimer = new Timer(stepDuration, new ActionListener(){
        public void actionPerformed(ActionEvent e){
          pauseAnimation();
        }
      });
      animationTimer.setRepeats(false);
      animationTimer.setCoalesce(false);
      animationTimer.start();
    }
  }


  protected abstract void animateForward(int duration);
  protected abstract void animateBackward(int duration);
  protected abstract void finishForwardAnimation();
  protected abstract void finishBackwardAnimation();
  protected abstract void pauseAnimation();
  protected abstract void continueAnimation();
}
