package simulation.reader;
import java.util.*;
import exportedAPI.opcatAPIx.*;

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

public class RuntimeInfoTable {
  private Hashtable<Long, RuntimeInfoEntry> infoTable;
  private IXSystem opmSystem;

  public RuntimeInfoTable(IXSystem system) {
    opmSystem = system;
    infoTable = new Hashtable<Long, RuntimeInfoEntry>();
    Enumeration opmEntries = opmSystem.getIXSystemStructure().getElements();
    while (opmEntries.hasMoreElements()){
      IXEntry currEntry = (IXEntry)opmEntries.nextElement();
      infoTable.put(currEntry.getId(), new RuntimeInfoEntry(currEntry, system));
    }
  }

  public RuntimeInfoEntry getInfoEntry(long entityId){
    return infoTable.get(entityId);
  }

  public Iterator<RuntimeInfoEntry> iterator(){
    return infoTable.values().iterator();
  }
}
