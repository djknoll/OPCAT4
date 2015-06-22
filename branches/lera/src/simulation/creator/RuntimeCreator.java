package simulation.creator;

import exportedAPI.opcatAPIx.*;
import java.util.*;
import simulation.tasks.*;
import simulation.creator.rules.Rule;
import simulation.Logger;
import simulation.ExternalInput;
import simulation.creator.creationInfo.*;

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


public class RuntimeCreator implements IQueueCreator{
  private final static int INITIAL_QUEUES_SIZE = 5000;
  ExternalInputHelper userInputHelper;
  AutoInitiationHelper autoInitiationHelper;
  private IXSystem opmSystem;
  private CreationInfoTable creationTable;
  private TreeMap<Integer, ArrayList<Rule>> rulesHistory;
  private ArrayList<SimulationTask> creatingTasks = new ArrayList<SimulationTask>(INITIAL_QUEUES_SIZE);

  private int nextTS2create;
  private Logger logger = Logger.getInstance();
  private CreationConfig config;

  public RuntimeCreator(IXSystem system, CreationConfig config) {
    opmSystem = system;
    this.config = config;
    rulesHistory = new TreeMap<Integer, ArrayList<Rule>>();
    reset();
  }
  
  public void reset(){
    nextTS2create = 0;
    creationTable = new CreationInfoTable(opmSystem, config);
    userInputHelper = new ExternalInputHelper(creationTable);
    autoInitiationHelper = new AutoInitiationHelper(creationTable);
    rulesHistory.clear();
    //config = ""
    if (config.isAutoInitiated()){
      add2History(autoInitiationHelper.getAutoInitiatedRules());
    }

  }
  

  public List<SimulationTask> getNextTasks(int time) throws CreationException{
    creatingTasks.clear();
    SortedMap<Integer, ArrayList<Rule>> rules = rulesHistory.tailMap(nextTS2create);

    nextTS2create += time;
    logger.print("RuntimeCreator::getNextTasks - lastTs "+nextTS2create+" rulesSize "+rules.size() + " history size "+rulesHistory.size(),2);

    if (rules.isEmpty()){
    	throw new NoPossibleTasksException();
    }
    	
    if (rules.firstKey().intValue() >= nextTS2create){	
      return creatingTasks;
    }

    Iterator<ArrayList<Rule>> ruleIter = rules.values().iterator();
    while (ruleIter.hasNext()){
      ArrayList<Rule> currTSRules = ruleIter.next();
      int currTime = currTSRules.get(0).getTime();
      if ( currTime >= nextTS2create){
        break;
      }

      boolean shouldRefreshIterator = false;
      for (int i = 0; i < currTSRules.size(); i++){
        Rule currRule = currTSRules.get(i);
        if (!currRule.isConditionSatisfied()){ // rule isn't satisfied put debug info
          if (currRule.getDebugInfo() != null){
            creatingTasks.add(currRule.getDebugInfo());
          }
        }else{ // rule satisfied, put created tasks
          currRule.setEvaluated(true);
          List<SimulationTask> newTasks = currRule.getConsequenceTasks();
          creatingTasks.addAll(newTasks);
          List<Rule> generatedRules = currRule.getConsequenceRules();
          if (generatedRules != null && !generatedRules.isEmpty()){
            add2History(generatedRules);
            shouldRefreshIterator = true;
          }
          currRule.updateCreationTable();
        }
      }

      // Need to refresh the iterators because no support in java for addition during iteration
      if (shouldRefreshIterator){
        rules = rulesHistory.tailMap(currTime + 1);
        ruleIter = rules.values().iterator();
      }
    }

    if (creatingTasks.size() == 0 && rulesHistory.tailMap(nextTS2create).size() == 0){
    	throw new NoPossibleTasksException();
    }
    
    return creatingTasks;
  }


  private void add2History(List<Rule> ruleList){
    if (ruleList == null){
      return;
    }

    Iterator<Rule> iter = ruleList.iterator();
    while (iter.hasNext()){
      Rule currRule = iter.next();
      ArrayList<Rule> ruleArray = rulesHistory.get(currRule.getTime());
      if (ruleArray == null){
        ruleArray = new ArrayList<Rule>();
        rulesHistory.put(currRule.getTime(), ruleArray);
      }
      ruleArray.add(currRule);
    }
  }

  public List<SimulationTask> recreateTasks(ExternalInput input, int inputTime, int time2create) throws CreationException{
    invalidateRules(inputTime - 1);
    nextTS2create = inputTime;
    List<Rule> externalRules = userInputHelper.getExternalInputRules(input, inputTime);
    logger.print("RuntimeCreator:recreateTask - got "+externalRules.size()+" user Rules - input time "+inputTime,2);
    add2History(externalRules);
    return getNextTasks(time2create);
  }



  private void invalidateRules(int lastValidTS){
    SortedMap<Integer, ArrayList<Rule>> sMap = rulesHistory.tailMap(lastValidTS);
    Iterator<Integer> keysIterator = sMap.keySet().iterator();
    ArrayList<Integer> keys2Check = new ArrayList<Integer>();
    while (keysIterator.hasNext()){
      keys2Check.add(keysIterator.next());
    }

    logger.print("RuntimeCreator:invalidateRules - has "+keys2Check.size()+" to check", 2);
    for (int i = keys2Check.size() - 1; i >= 0; i--){
      removeInvalidatedRules(keys2Check.get(i), lastValidTS);
    }
  }

  private void removeInvalidatedRules(int key, int lastValidTS){
    ArrayList<Rule> rules2Check = rulesHistory.get(key);
    if (rules2Check == null){
      return;
    }

    ListIterator<Rule> iter = rules2Check.listIterator(rules2Check.size());
    while (iter.hasPrevious()){
      Rule currRule = iter.previous();
      if (currRule.isEvaluated()){
        currRule.undoUpdateCreationTable();
      }
      logger.print("Checking rule "+currRule.toString()+" par time "+currRule.getParentTime(),2);
      if (currRule.getParentTime() > lastValidTS){
        logger.print("Removing invalidated rule "+currRule.toString(),2);
        iter.remove();
      }
    }

    if (rules2Check.isEmpty()){
      rulesHistory.remove(key);
    }
  }

  private void initiateAutomaticaly(){
    // TODO
  }
}
