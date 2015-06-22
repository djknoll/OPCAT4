package simulation.util;
import exportedAPI.opcatAPIx.*;
import java.util.*;

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
public class LevelsRelation {
  public final static int LEVEL_TOLERANCE = 20;
  private IXProcessEntry parentProcess;
  private ArrayList<Level> levels = new ArrayList<Level>();
  private Hashtable<Long, IndexEntry> processIndex = new Hashtable<Long, IndexEntry>();

  public LevelsRelation(IXProcessEntry parentProcess){
    this.parentProcess = parentProcess;
    IXOpd parentOpd = parentProcess.getZoomedInIXOpd();
    if (parentOpd == null){
      return;
    }

    TreeMap<Integer, Level> tempLevels = new TreeMap<Integer,Level>();
    IXThingInstance parentInstance = parentOpd.getMainIXInstance();
    Enumeration children = parentInstance.getChildThings();
    while (children.hasMoreElements()){
      IXThingInstance currChild = (IXThingInstance)children.nextElement();
      if (!(currChild instanceof IXProcessInstance)){
        continue;
      }

      Level currLevel = getAdjacentLevel(currChild.getY(), tempLevels);
      if (currLevel == null){
        currLevel = new Level(currChild.getY());
        tempLevels.put(currChild.getY(), currLevel);
      }

      currLevel.addProcess((IXProcessEntry)currChild.getIXEntry());
    }

    int levelNum = 0;
    for (Iterator<Level> it = tempLevels.values().iterator(); it.hasNext();){
      Level currLevel = (Level)it.next();
      levels.add(currLevel);
      for (int i = 0; i < currLevel.processes.size(); i++){
        processIndex.put(currLevel.processes.get(i).getId(), new IndexEntry(levelNum));
      }
      levelNum++;
    }
  }

  private Level getAdjacentLevel(int yCoordinate, TreeMap<Integer, Level> levels){
   Iterator<Integer> keyIterator = levels.keySet().iterator();
   while (keyIterator.hasNext()){
     int currKey = keyIterator.next();
     if (Math.abs(currKey - yCoordinate) < LEVEL_TOLERANCE){
       return levels.get(currKey);
     }
   }

   return null;
  }

  public int getNumOfLevels(){
    return levels.size();
  }

  public void setProcessFinished(long processId){
    processIndex.get(processId).isFinished = true;
  }

  public boolean isLevelFinished(int levelNum){
    if (levelNum >= levels.size()){
      throw new IllegalArgumentException("LevelsRelation - levelNum cannot be bigger than num of levels");
    }

    List<IXProcessEntry> level = getProcessesInLevel(levelNum);
    Iterator<IXProcessEntry> iter = level.iterator();
    while (iter.hasNext()){
      if (!processIndex.get(iter.next().getId()).isFinished){
        return false;
      }
    }

    return true;
  }

  public boolean isProcessesLevelFinished(long processId){
    return isLevelFinished(processIndex.get(processId).levelNum);
  }

  public int getProcessLevel(long processId){
    return processIndex.get(processId).levelNum;
  }

  public void resetFinished2All(){
    Iterator<IndexEntry> iter = processIndex.values().iterator();
    while (iter.hasNext()){
      iter.next().isFinished = false;
    }

  }

  public List<IXProcessEntry> getProcessesInLevel(int levelNum){
    return levels.get(levelNum).processes;
  }

  private class Level{
    ArrayList<IXProcessEntry> processes = new ArrayList<IXProcessEntry>();
    private int yCoordinate;

    public Level(int yCoordinate){
      this.yCoordinate = yCoordinate;
    }

    public int getYCoordinate(){
      return yCoordinate;
    }

    public void addProcess(IXProcessEntry process){
      processes.add(process);
    }

  }

  private class IndexEntry{
    boolean isFinished;
    final int levelNum;

    public IndexEntry(int levelNum){
      this.levelNum = levelNum;
      isFinished = true;
    }
  }

}



