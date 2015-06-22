package simulation.creator;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import exportedAPI.opcatAPIx.*;
import simulation.creator.rules.*;
import simulation.*;
import exportedAPI.*;
import simulation.creator.creationInfo.*;

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
public class ExternalInputHelper {
  private CreationInfoTable creationTable;

  public ExternalInputHelper(CreationInfoTable table) {
    creationTable = table;
  }

  public List<Rule> getExternalInputRules(ExternalInput input, int inputTime){
    ArrayList<Rule> newRulesList = new ArrayList<Rule>();

    if (input.activatedEnitities != null){
      Iterator<IXInstance> iter = input.activatedEnitities.iterator();
      while (iter.hasNext()){
        newRulesList.addAll(getRules4Instance(iter.next(), inputTime, true));
      }
    }

    if (input.terminatedEntities != null){
      Iterator<IXInstance> iter = input.terminatedEntities.iterator();
      while (iter.hasNext()){
        newRulesList.addAll(getRules4Instance(iter.next(), inputTime, false));
      }
    }


    for (int i = 0; i < newRulesList.size(); i++){
      newRulesList.get(i).setExternallyActivated(true);
    }

    return newRulesList;
  }

  private List<Rule> getRules4Instance(IXInstance instance, int inputTime, boolean isActivated){
    ArrayList<Rule> emptyList = new ArrayList<Rule>(1);
    if (instance instanceof IXProcessInstance){
      return getRules4Process((IXProcessInstance)instance, inputTime, isActivated);
    }
    if (instance instanceof IXObjectInstance){
      return getRules4Object((IXObjectInstance)instance, inputTime, isActivated);
    }

    if (instance instanceof IXStateInstance){
      return getRules4State((IXStateInstance)instance, inputTime, isActivated);
    }

    if (instance instanceof IXLinkInstance){
      return getRules4Link((IXLinkInstance)instance, inputTime, isActivated);
    }


    return emptyList;
  }

  private List<Rule> getRules4Link(IXLinkInstance instance, int inputTime, boolean isActivated){
    ArrayList<Rule> list = new ArrayList<Rule>();
    IXLinkEntry link = (IXLinkEntry)instance.getIXEntry();
    if (isActivated && (link.getLinkType() == OpcatConstants.AGENT_LINK)){
      list.add(new AgentLinkInvocationRule(inputTime, null, link,
                                           creationTable));
    }
    return list;
  }


  private List<Rule> getRules4Process(IXProcessInstance instance, int inputTime, boolean isActivated){
    ArrayList<Rule> list = new ArrayList<Rule>();
    if (isActivated){
      list.add(new ProcessActivationRule(inputTime, null, (IXProcessEntry)instance.getIXEntry(), creationTable));
    }else{
      //list.add(new ProcessTerminationRule(inputTime, null, (IXProcessEntry)instance.getIXEntry(), creationTable));
    }
    return list;
  }

  private List<Rule> getRules4Object(IXObjectInstance instance, int inputTime, boolean isActivated){
    ArrayList<Rule> list = new ArrayList<Rule>();
    if (isActivated){
      list.add(new ObjectActivationRule(inputTime, null, (IXObjectEntry)instance.getIXEntry(), creationTable));
    }else{
//      list.add(new ObjectTerminationRule(inputTime, null, (IXObjectEntry)instance.getIXEntry(), creationTable));
    }
    return list;
  }

  private List<Rule> getRules4State(IXStateInstance instance, int inputTime, boolean isActivated){
    ArrayList<Rule> list = new ArrayList<Rule>();
//    if (isActivated){
//      list.add(new StateActivationRule(inputTime, null, (IXStateEntry)instance.getIXEntry(), creationTable));
//    }else{
//      list.add(new StateTerminationRule(inputTime, null, (IXStateEntry)instance.getIXEntry(), creationTable));
//    }
    return list;
  }


}
