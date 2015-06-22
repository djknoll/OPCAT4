package simulation.util;
import exportedAPI.opcatAPIx.*;
import java.util.ArrayList;
import java.util.Iterator;
import simulation.Logger;
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
public class LinkLogicalConnection {
  public final static class CONNECTION_TYPE{
    public final static int AND = 0;
    public final static int OR = 1;
    public final static int XOR = 2;
  }

  private boolean isAtomic;
  private IXLinkEntry atomicEntry;
  private ArrayList<LinkLogicalConnection> childConnections;
  private int type;
  private boolean isEmpty;
  private String dataPath;

  public LinkLogicalConnection() {
    this(CONNECTION_TYPE.AND);
  }

  public LinkLogicalConnection(int type) {
    isAtomic = true;
    this.type = type;
    isEmpty = true;
  }


  public boolean isAtomic(){
    return isAtomic;
  }

  public void setAtomic(boolean isAtomic){
    this.isAtomic = isAtomic;
  }

  public void setType(int type){
    this.type = type;
  }

  public int getType(){
    return type;
  }

  public boolean isLinkInConnection(IXLinkEntry link){
    if (isAtomic){
      return ((atomicEntry != null) && (atomicEntry.getId() == link.getId()));
    }

    for (int i = 0; i < childConnections.size(); i++){
      if (childConnections.get(i).isLinkInConnection(link)){
        return true;
      }
    }

    return false;
  }

  public List<IXLinkEntry> getAllParticipatingLinks(){
    ArrayList<IXLinkEntry> participators = new ArrayList<IXLinkEntry>();
    if (isAtomic){
      participators.add(atomicEntry);
      return participators;
    }

    for (int i = 0; i < childConnections.size(); i++){
      List<IXLinkEntry> childLinks = childConnections.get(i).getAllParticipatingLinks();
      participators.addAll(childLinks);
    }

    return participators;
  }


  public void addChildConnection(LinkLogicalConnection connection){
    if (childConnections == null){
      childConnections = new ArrayList<LinkLogicalConnection>();
    }
    isAtomic = false;
    childConnections.add(connection);
    isEmpty = false;
  }

  public Iterator<LinkLogicalConnection> getChildConnections(){
      return childConnections.iterator();
  }



  public IXLinkEntry getAtomicEntry(){
    return atomicEntry;
  }

  public void setAtomicEntry(IXLinkEntry entry){
    isAtomic = true;
    atomicEntry = entry;
    isEmpty = false;
  }

  public int getNumOfChilds(){
    if (isAtomic){
      return 0;
    }

    return childConnections.size();
  }

  public List<IXLinkEntry> getLinksByRelationLogic(){
    return getLinksByRelationLogic(null);
  }

  public List<IXLinkEntry> getLinksByRelationLogic(final String dataPath){
    ArrayList<IXLinkEntry> list = new ArrayList<IXLinkEntry>();
    if (isEmpty){
      return list;
    }

    if (isAtomic){
      if ((dataPath == null) || (atomicEntry.getPath() == null) ||
          atomicEntry.getPath().trim().equals("") ||
          atomicEntry.getPath().trim().equals(dataPath.trim())){
        list.add(atomicEntry);
      }
      return list;
    }

    Collections.shuffle(childConnections); // to give the equal probabilty for or xor connections memebers
    Iterator<LinkLogicalConnection> iter = childConnections.iterator();
    int numOfAddedElems = 0;

    while (iter.hasNext() ){
      LinkLogicalConnection currConnection = iter.next();
      switch (type){
        case CONNECTION_TYPE.AND : {
          numOfAddedElems += add2ListUniqely(list, currConnection.getLinksByRelationLogic(dataPath));
          break;
        }
        case CONNECTION_TYPE.OR : {
          if ((numOfAddedElems == 0) || (Math.random() > 0.5)){
            numOfAddedElems += add2ListUniqely(list, currConnection.getLinksByRelationLogic(dataPath));
          }
          break;
        }
        case CONNECTION_TYPE.XOR : {
          if (numOfAddedElems == 0){
            numOfAddedElems += add2ListUniqely(list, currConnection.getLinksByRelationLogic(dataPath));
          }
          break;
        }
      }
    }

    return list;
  }

  private int add2ListUniqely(List<IXLinkEntry> list, List<IXLinkEntry> newElements){
    int numOfAddedElements = 0;
    Iterator<IXLinkEntry> iter = newElements.iterator();
    while(iter.hasNext()){
      IXLinkEntry currLink = iter.next();
      if (!LinkUtils.isMemberOfList(currLink, list)){
        list.add(currLink);
        numOfAddedElements++;
      }
    }
    return numOfAddedElements;
  }


  public ILinksCheckResult isConditionSatisfied(ILinkCondition condition){
    CheckResult result = new CheckResult(false, null);
    if (isEmpty){
      result.isSatisfied = true;
      return result;
    }

    if (isAtomic){
      result.dataPath = atomicEntry.getPath();
      result.isSatisfied = condition.isConditionSatisfied(atomicEntry);
      if (result.isSatisfied){
        result.satisfyingLinks.add(atomicEntry);
      }
      return result;
    }

    Collections.shuffle(childConnections); // to give the equal probabilty for or xor connections memebers
    boolean isOrElementsWereAdded = false;
    for (int i = 0; i < childConnections.size(); i++){
      ILinksCheckResult lastResult = childConnections.get(i).isConditionSatisfied(condition);
      switch (type){
        case CONNECTION_TYPE.AND :{
          if (lastResult.isCondititionSatisfied() == false){
            return lastResult;
          }else{
            if (lastResult.getDataPath() != null &&
                !lastResult.getDataPath().trim().equals("")){
              result.dataPath = lastResult.getDataPath();
            }
            add2ListUniqely(result.satisfyingLinks, lastResult.getSatistyingLinks());
          }
          break;
        }
        case CONNECTION_TYPE.OR :{
          if (!isOrElementsWereAdded || Math.random() > 0.5){
            isOrElementsWereAdded = true;
            add2ListUniqely(result.satisfyingLinks, lastResult.getSatistyingLinks());
          }
          break;
        }
        case CONNECTION_TYPE.XOR :{
          if (lastResult.isCondititionSatisfied() == true){
            return lastResult;
          }
          break;
        }
      }

    }

    if (type == CONNECTION_TYPE.AND){
      result.isSatisfied = true;
    }
    else if (type == CONNECTION_TYPE.OR){
      result.isSatisfied = isOrElementsWereAdded;
    }
    else if (type == CONNECTION_TYPE.XOR){
      result.isSatisfied = false;
    }

    return result;
  }

  class CheckResult implements ILinksCheckResult {
    boolean isSatisfied = false;
    String dataPath = null;
    ArrayList<IXLinkEntry> satisfyingLinks;

    public CheckResult(boolean isSatisfied, String dataPath){
      this.isSatisfied = isSatisfied;
      this.dataPath = dataPath;
      satisfyingLinks = new ArrayList<IXLinkEntry>();
    }

    public List<IXLinkEntry> getSatistyingLinks(){
      return satisfyingLinks;
    }

    public boolean isCondititionSatisfied(){
      return isSatisfied;
    }
    public String getDataPath(){
      return dataPath;
    }
  }
}

