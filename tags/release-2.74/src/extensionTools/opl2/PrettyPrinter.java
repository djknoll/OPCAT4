
package extensionTools.opl2;

import java.util.LinkedList;

import exportedAPI.opcatAPI.ISystem;
import exportedAPI.opcatAPI.ISystemStructure;
import extensionTools.opl2.alg.OPLGeneral;
import extensionTools.opl2.generated.AgentSentence;
import extensionTools.opl2.generated.AgentSentenceType;
import extensionTools.opl2.generated.AggregatedObject;
import extensionTools.opl2.generated.ChangingClause;
import extensionTools.opl2.generated.ChangingSentenceType;
import extensionTools.opl2.generated.ConditionClause;
import extensionTools.opl2.generated.ConditionSentenceType;
import extensionTools.opl2.generated.ConsumptionClause;
import extensionTools.opl2.generated.ConsumptionSentenceType;
import extensionTools.opl2.generated.EffectClause;
import extensionTools.opl2.generated.EffectSentenceType;
import extensionTools.opl2.generated.EnablingClause;
import extensionTools.opl2.generated.EnablingSentenceType;
import extensionTools.opl2.generated.ExhibitedObject;
import extensionTools.opl2.generated.GeneralEventSentence;
import extensionTools.opl2.generated.OPLscript;
import extensionTools.opl2.generated.ObjectAggregationSentenceSetType;
import extensionTools.opl2.generated.ObjectAggregationSentenceType;
import extensionTools.opl2.generated.ObjectBiDirectionalRelationSentence;
import extensionTools.opl2.generated.ObjectEnvironmentalPhysicalSentenceType;
import extensionTools.opl2.generated.ObjectExhibitionSentenceSetType;
import extensionTools.opl2.generated.ObjectExhibitionSentenceType;
import extensionTools.opl2.generated.ObjectInZoomingSentenceSetType;
import extensionTools.opl2.generated.ObjectInZoomingSentenceType;
import extensionTools.opl2.generated.ObjectInheritanceSentenceSet;
import extensionTools.opl2.generated.ObjectInstanceSentenceType;
import extensionTools.opl2.generated.ObjectStateSentenceType;
import extensionTools.opl2.generated.ObjectUniDirectionalRelationSentenceType;
import extensionTools.opl2.generated.Operation;
import extensionTools.opl2.generated.ProcessAggregationSentenceSetType;
import extensionTools.opl2.generated.ProcessAggregationSentenceType;
import extensionTools.opl2.generated.ProcessBiDirectionalRelationSentence;
import extensionTools.opl2.generated.ProcessEnvironmentalPhysicalSentenceType;
import extensionTools.opl2.generated.ProcessExhibitionSentenceSetType;
import extensionTools.opl2.generated.ProcessExhibitionSentenceType;
import extensionTools.opl2.generated.ProcessInZoomingSentenceSetType;
import extensionTools.opl2.generated.ProcessInZoomingSentenceType;
import extensionTools.opl2.generated.ProcessInstanceSentenceType;
import extensionTools.opl2.generated.ProcessInvocationSentenceType;
import extensionTools.opl2.generated.ProcessTimeoutSentenceType;
import extensionTools.opl2.generated.ProcessUniDirectionalRelationSentence;
import extensionTools.opl2.generated.ResultClause;
import extensionTools.opl2.generated.ResultSentenceType;
import extensionTools.opl2.generated.Role;
import extensionTools.opl2.generated.StateClause;
import extensionTools.opl2.generated.StateEntranceSentence;
import extensionTools.opl2.generated.StateTimeoutSentence;
import extensionTools.opl2.generated.ThingSentenceSet;
import extensionTools.opl2.generated.ThingSentenceSetType;
import extensionTools.opl2.generated.TypeDeclarationSentenceType;
import gui.opdProject.OpdProject;
import gui.opdProject.OplColorScheme;

//import javax.xml.bind.Marshaller;

public class PrettyPrinter {

  public static final int OR = 0, AND = 1, XOR = 2;

  public static String[] prop = {
      "or", "and", "either"};
  protected ISystem opcatSystem;
  protected OplColorScheme colorScheme;
  protected StringBuffer bf = new StringBuffer();

  public PrettyPrinter(ISystemStructure elems, ISystem opcatSystem_) {
    elements = elems;
    opcatSystem = opcatSystem_;
    OpdProject myProject = (OpdProject) opcatSystem;
    colorScheme = myProject.getOplColorScheme();
    startHTML = "<HTML><HEAD></HEAD><BODY>"; /*+
        colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).
        openingHTMLFontTag();*/
    //endHTML = colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).
      //  closingHTMLFontTag() + "</BODY></HTML>";
    endHTML = "</BODY></HTML>";
      this.setEndLine();
  }

  public StringBuffer getBuffer() {
    return bf;
  }

  public void visit(ProcessUniDirectionalRelationSentence e) {
    bf.append(margin);
    printAttr(OplColorScheme.PROCESS_STYLE, e.getSourceName());
    bf.append(" ");
    if(e.getRelationName().startsWith("relates")){
      printAttr(OplColorScheme.DEFAULT_STYLE, e.getRelationName());
    }else
      printAttr(OplColorScheme.TAG_STYLE, e.getRelationName());
    bf.append(" ");
    printAttr(OplColorScheme.PROCESS_STYLE, e.getDestinationName());
    bf.append(endLine);
  }

  public void visit(ProcessBiDirectionalRelationSentence e, String subjectName) {
    bf.append(margin);

    if (subjectName.equals(e.getDestinationName())) {
      printAttr(OplColorScheme.PROCESS_STYLE, e.getDestinationName());
      printAttr(OplColorScheme.DEFAULT_STYLE," and ");
      printAttr(OplColorScheme.PROCESS_STYLE, e.getSourceName());
      if(e.getRelationName().startsWith("equivalent")){
        printAttr(OplColorScheme.DEFAULT_STYLE, " "+e.getRelationName());
     }else
      printAttr(OplColorScheme.TAG_STYLE, " "+e.getRelationName());
    }
    else {
      printAttr(OplColorScheme.PROCESS_STYLE, e.getSourceName());
      printAttr(OplColorScheme.TAG_STYLE," " + e.getRelationName()+" ");
      printAttr(OplColorScheme.PROCESS_STYLE, e.getDestinationName());
    }
    bf.append(endLine);
  }

  public void visit(ObjectUniDirectionalRelationSentenceType e) {
    bf.append(margin);
    boolean has_s = false;
    has_s = printCardinality(e.getSourceMinimalCardinality(),
                                     e.getSourceMaximalCardinality(),true);
    if(has_s){
      printAttr(OplColorScheme.OBJECT_STYLE, extensionTools.opl2.alg.
                EnglishRules.pluralOf(e.getSourceName()));
    }
    else printAttr(OplColorScheme.OBJECT_STYLE, e.getSourceName());
    if(e.getRelationName().startsWith("relates")){
      printAttr(OplColorScheme.DEFAULT_STYLE, " "+e.getRelationName()+" ");
    }else
      printAttr(OplColorScheme.TAG_STYLE, " "+e.getRelationName()+" ");
    has_s = printCardinality(e.getDestinationMinimalCardinality(),
                                     e.getDestinationMaximalCardinality(),false);
    if (has_s) {
      printAttr(OplColorScheme.OBJECT_STYLE,
                extensionTools.opl2.alg.
                EnglishRules.pluralOf(e.getDestinationName()));
    }
    else
      printAttr(OplColorScheme.OBJECT_STYLE, e.getDestinationName());
    bf.append(endLine);
  }

  public void visit(ObjectBiDirectionalRelationSentence e, String subjectName) {
    boolean has_s = false;
    bf.append(margin);
    if (subjectName.equals(e.getDestinationName())) {
      has_s = printCardinality(e.getDestinationMinimalCardinality(),
                               e.getDestinationMaximalCardinality(),true);
      if (has_s) {
        printAttr(OplColorScheme.OBJECT_STYLE, extensionTools.opl2.alg.
                  EnglishRules.pluralOf(e.getDestinationName()));
      }
      else
        printAttr(OplColorScheme.OBJECT_STYLE, e.getDestinationName());
      printAttr(OplColorScheme.DEFAULT_STYLE," and ");
      has_s = printCardinality(e.getSourceMinimalCardinality(),
                               e.getSourceMaximalCardinality(),false);
      if (has_s) {
        printAttr(OplColorScheme.OBJECT_STYLE,
                  extensionTools.opl2.alg.
                  EnglishRules.pluralOf(e.getSourceName()));
      }
      else
        printAttr(OplColorScheme.OBJECT_STYLE, e.getSourceName());
      printAttr(OplColorScheme.DEFAULT_STYLE," are");
      if(e.getRelationName().startsWith("equivalent")){
        printAttr(OplColorScheme.DEFAULT_STYLE, " "+e.getRelationName());
     }else
      printAttr(OplColorScheme.TAG_STYLE, " "+e.getRelationName());
      bf.append(endLine);
    }
    else {
      has_s = printCardinality(e.getSourceMinimalCardinality(),
                               e.getSourceMaximalCardinality(),true);
      if (has_s) {
        printAttr(OplColorScheme.OBJECT_STYLE,
                  extensionTools.opl2.alg.
                  EnglishRules.pluralOf(e.getSourceName()));
      }
      else
        printAttr(OplColorScheme.OBJECT_STYLE, e.getSourceName());
      printAttr(OplColorScheme.TAG_STYLE," " + e.getRelationName()+" ");
      has_s = printCardinality(e.getDestinationMinimalCardinality(),
                               e.getDestinationMaximalCardinality(),false);
      if (has_s) {
        printAttr(OplColorScheme.OBJECT_STYLE,
                  extensionTools.opl2.alg.
                  EnglishRules.pluralOf(e.getDestinationName()));
      }
      else
        printAttr(OplColorScheme.OBJECT_STYLE, e.getDestinationName());
      bf.append(endLine);
    }
  }

  public void visit(ObjectAggregationSentenceSetType e) {
    visit(e.getObjectAggregationSentence());
    printParagraph(e.getThingSentenceSet(), false);
  }

  public void visit(ObjectInZoomingSentenceSetType e){
    visit(e.getObjectInZoomingSentence());
    printParagraph(e.getThingSentenceSet(),false);
  }

  public void visit(ProcessInZoomingSentenceSetType e){
    visit(e.getProcessInZoomingSentence());
    printParagraph(e.getThingSentenceSet(),false);
  }

  public void visit(ObjectInZoomingSentenceType e){
    int key = OplColorScheme.OBJECT_STYLE;
    if(e.getInZoomedObject().isEmpty()&&e.getInZoomedProcess().isEmpty())
      return;
    bf.append(margin);
    java.util.List lst = e.getInZoomedObject();
    printAttr(key, e.getObjectName());
    printAttr(OplColorScheme.DEFAULT_STYLE," zooms into ");
    int size = lst.size();
    int size1;
    for(int j=0;j<2;j++){
      if(j==1){
        lst = e.getInZoomedProcess();
        size1 = lst.size();
        key = OplColorScheme.PROCESS_STYLE;
        if(size>0 && size1>0){
          printAttr(OplColorScheme.DEFAULT_STYLE,", as well as ");
        }
        size=size1;
      }
      for (int i = 0; i < size; i++) {
        setDelimiter(i, size, AND);
        printAttr(key, (String)lst.get(i));
      }
    }bf.append(endLine);
  }

  public void visit(ProcessInZoomingSentenceType e){
    int key = OplColorScheme.PROCESS_STYLE;
    if(e.getInZoomedObject().isEmpty()&&e.getInZoomedProcess().isEmpty())
      return;
    bf.append(margin);
    java.util.List lst = e.getInZoomedProcess();

    printAttr(key, e.getProcessName());
    printAttr(OplColorScheme.DEFAULT_STYLE," zooms into ");
    int size = lst.size();
    int size1;
    for(int j=0;j<2;j++){
      if(j==1){
        lst = e.getInZoomedObject();
        size1 = lst.size();
        key = OplColorScheme.OBJECT_STYLE;
        if(size>0 && size1>0){
         printAttr(OplColorScheme.DEFAULT_STYLE,", as well as ");
        }
        size=size1;
      }
      for (int i = 0; i < size; i++) {
        setDelimiter(i, size, AND);
        printAttr(key, (String)lst.get(i));
      }
    }
    bf.append(endLine);
  }

  public StateClause getDefaultState(java.util.List states){
    StateClause def =null;
    for(int i=0;i<states.size();i++){
      if( ((StateClause) states.get(i)).isDefault()){
        def = (StateClause)states.get(i);
      }
    }
    return def;
  }

  public void visit(ObjectStateSentenceType e) {
    int key = OplColorScheme.OBJECT_STYLE;
    int key2 = OplColorScheme.STATE_STYLE;
    java.util.List states = e.getStateClause();
    StateClause def =null;
    int size = states.size();
    if (size == 0)
      return;
    StateClause state = (StateClause) states.get(0);
    bf.append(margin);
    printAttr(key, e.getObjectName());
    printAttr(OplColorScheme.DEFAULT_STYLE," can be ");
    def = getDefaultState(states);
    //printAttr(key2, ( (StateClause) states.get(0)).getStateName());
    if (def!=null) {
      printAttr(key2, def.getStateName());
      printAttr(OplColorScheme.DEFAULT_STYLE," by default");
    }
    for (int i = 0; i < size; i++) {
      if((StateClause) states.get(i)==def)
        continue;
      setDelimiter(i, size, OR);
      printAttr(key2, ( (StateClause) states.get(i)).getStateName());
    }
    bf.append(endLine);
    printParagraph(e.getStateClause(), true);
  }

  public void visit(StateClause clause) {
    if (clause.isInitial()) {
      bf.append(margin);
      printAttr(OplColorScheme.STATE_STYLE, clause.getStateName());
      printAttr(OplColorScheme.DEFAULT_STYLE," is initial");
      bf.append(endLine);
    }
    String max = new String();
    String min = new String();
    if (clause.getMaxTimeValue() != null)
      max = OPLGeneral.visit(clause.getMaxTimeValue());
    if (clause.getMinTimeValue() != null)
      min = OPLGeneral.visit(clause.getMinTimeValue());
    if (min.length() > 0) {
      bf.append(margin);
      printAttr(OplColorScheme.STATE_STYLE, clause.getStateName());
      printAttr(OplColorScheme.DEFAULT_STYLE," lasts ");
      printAttr(OplColorScheme.TAG_STYLE, min);
      printAttr(OplColorScheme.DEFAULT_STYLE," to ");
      if (max.length() > 0)
        printAttr(OplColorScheme.TAG_STYLE, max);
      else
        printAttr(OplColorScheme.TAG_STYLE, "infinity");
      bf.append(endLine);
    }
    if (clause.isFinal()) {
      bf.append(margin);
      printAttr(OplColorScheme.STATE_STYLE, clause.getStateName());
      printAttr(OplColorScheme.DEFAULT_STYLE," is final");
      bf.append(endLine);
    }
  }

  public void visit(ProcessInvocationSentenceType proc) {
    int key = OplColorScheme.PROCESS_STYLE;
    java.util.List invokedProcs = proc.getTriggeredProcess();
    bf.append(margin);
    printPath(proc.getPathLabel());
    printAttr(key, proc.getProcessName());
    addActionSentence(key, invokedProcs, " invokes ");
    bf.append(endLine);
  }

  public void visit(ProcessTimeoutSentenceType proc) {
    int key = OplColorScheme.PROCESS_STYLE;
    java.util.List invokedProcs = proc.getTriggeredProcess();
    bf.append(margin);
    printPath(proc.getPathLabel());
    printAttr(key, proc.getProcessName());
    addActionSentence(key, invokedProcs, " triggers ");
    printAttr(OplColorScheme.DEFAULT_STYLE," when it lasts more than ");
    printAttr(OplColorScheme.DEFAULT_STYLE, OPLGeneral.visit(proc.getMaxTimeoutValue()));
    bf.append(endLine);
  }

  public void visit(ProcessAggregationSentenceSetType e) {
    visit(e.getProcessAggregationSentence());
    printParagraph(e.getThingSentenceSet(), false);
  }

  public void visit(ObjectExhibitionSentenceSetType e) {
    visit(e.getObjectExhibitionSentence());
    printParagraph(e.getThingSentenceSet(), false);
  }

  public void visit(ProcessExhibitionSentenceSetType e) {
    visit(e.getProcessExhibitionSentence());
    printParagraph(e.getThingSentenceSet(), false);
  }

  //returns true if entity of the cardinality should come
  //with "s" on the end;
  //for example: many As
  public boolean printCardinality(int min, int max, boolean isFirst) {
    if (min == max && max > 1) {
        printAttr(OplColorScheme.CARDINALITY_STYLE, Integer.toString(min)+" ");
      return true;
    }
    if (min == 1 && max == 1) {
      return false;
    }
    if (min == 0 && max == 1) {
      if(isFirst){
        printAttr(OplColorScheme.CARDINALITY_STYLE, "An optional ");
      }else
        printAttr(OplColorScheme.CARDINALITY_STYLE, "an optional ");
      return false;
    }
    if (max != -1) {
        printAttr(OplColorScheme.CARDINALITY_STYLE,min + " to " + max +" ");
      return true;
    }
    if (min == 0 && max == -1) {
      if(isFirst){
        printAttr(OplColorScheme.CARDINALITY_STYLE, "Optional ");
      }else
        printAttr(OplColorScheme.CARDINALITY_STYLE, "optional ");
      return true;
    }
    if (min == 1 && max == -1) {
      if(isFirst){
        printAttr(OplColorScheme.CARDINALITY_STYLE, "At least one ");
      }else
        printAttr(OplColorScheme.CARDINALITY_STYLE, "at least one ");
      return false;
    }
    if(isFirst){
        printAttr(OplColorScheme.CARDINALITY_STYLE, "Many ");
      }else
        printAttr(OplColorScheme.CARDINALITY_STYLE, "many ");
    return true;
  }

  public void visit(ProcessAggregationSentenceType e) {
    int key = OplColorScheme.PROCESS_STYLE;
    java.util.List exhibits = e.getAggregatedProcess();
    bf.append(margin);
    printAttr(key, e.getProcessName());
    addAggregationSentence(key, exhibits);
    bf.append(endLine);
  }

  public void visit(ObjectAggregationSentenceType e) {
    int key = OplColorScheme.OBJECT_STYLE;
    java.util.List exhibits = e.getAggregatedObject();
    bf.append(margin);
    printAttr(key, e.getObjectName());
    addAggregationSentence(key, exhibits);
    bf.append(endLine);
  }

  public void addExhibitionObjectSentence(int key, java.util.List exhibits,
                                          java.util.List operations) {
    ExhibitedObject obj;
    Operation op;
    java.util.List Objects = new LinkedList();
    java.util.List Processes = new LinkedList();
    boolean isObjects = false;
    printAttr(OplColorScheme.DEFAULT_STYLE," exhibits ");
    for (java.util.ListIterator itr = exhibits.listIterator(); itr.hasNext(); ) {
      obj = (ExhibitedObject) itr.next();
      Objects.add(obj);
    }
    for (java.util.ListIterator itr = operations.listIterator(); itr.hasNext(); ) {
      op = (Operation) itr.next();
      Processes.add(op);
    }
    if (key == OplColorScheme.PROCESS_STYLE) {
      java.util.List tmp = Objects;
      Objects = Processes;
      Processes = Objects;
    }
    else
      isObjects = true;
    addExhibitedThings(isObjects, Objects);
    if (!Objects.isEmpty() && !Processes.isEmpty()) {
      printAttr(OplColorScheme.DEFAULT_STYLE,", as well as ");
    }
    addExhibitedThings( (!isObjects), Processes);
  }

  public void addExhibitedThings(boolean key_, java.util.List Objects) {
    int listSize = Objects.size();
    ExhibitedObject obj;
    Operation op;
    String objName;
    int key;
    boolean hasS;
    if (key_) {
      key = OplColorScheme.OBJECT_STYLE;
    }
    else {
      key = OplColorScheme.PROCESS_STYLE;
    }
    for (java.util.ListIterator itr = Objects.listIterator(); itr.hasNext(); ) {
      if (key_) {
        obj = (ExhibitedObject) itr.next();
        hasS = printCardinality(obj.getMinimalCardinality(),
                                obj.getMaximalCardinality(), false);
        objName = obj.getAttributeName();
      }
      else {
        op = (Operation) itr.next();
        objName = op.getOperationName();
        hasS = false;
      }
      if (hasS) {
        objName = extensionTools.opl2.alg.EnglishRules.pluralOf(objName);
      }
      printAttr(key, objName);
      if (itr.nextIndex() < (listSize - 1)) {
        printAttr(OplColorScheme.DEFAULT_STYLE,", ");
      }
      else if (itr.nextIndex() == (listSize - 1)) {
        if (listSize > 2)
          printAttr(OplColorScheme.DEFAULT_STYLE,",");
        printAttr(OplColorScheme.DEFAULT_STYLE," and ");
      }
    }
  }

  public void addAggregationSentence(int key, java.util.List exhibits) {
    int listSize = exhibits.size();
    boolean isObj = false;
    boolean hasS = false;
    AggregatedObject tmp;
    String obj;
    if (key == OplColorScheme.OBJECT_STYLE)
      isObj = true;
    printAttr(OplColorScheme.DEFAULT_STYLE," consists of ");
    for (java.util.ListIterator itr = exhibits.listIterator(); itr.hasNext(); ) {
      if (isObj) {
        tmp = (AggregatedObject) itr.next();
        hasS = printCardinality(tmp.getMinimalCardinality(),
                                tmp.getMaximalCardinality(),false);
        obj = tmp.getObjectName();
      }
      else
        obj = (String) itr.next();
      if (hasS) {
        obj = extensionTools.opl2.alg.EnglishRules.pluralOf(obj);
      }
      printAttr(key, obj);
      if (itr.nextIndex() < (listSize - 1)) {
        printAttr(OplColorScheme.DEFAULT_STYLE,", ");
      }
      else if (itr.nextIndex() == (listSize - 1)) {
        if (listSize > 2)
          printAttr(OplColorScheme.DEFAULT_STYLE,",");
        printAttr(OplColorScheme.DEFAULT_STYLE," and ");
      }
    }
  }

  public void addActionSentence(int key, java.util.List invokedProcs,
                                String action) {
    bf.append(action);
    int size = invokedProcs.size();
    if (size == 1) {
      printAttr(key, (String) invokedProcs.get(0));
      return;
    }
    if (size == 2) {
      printAttr(key, (String) invokedProcs.get(0));
      printAttr(OplColorScheme.DEFAULT_STYLE," and ");
      printAttr(key, (String) invokedProcs.get(1));
      return;
    }
    for (int i = 0; i < size; i++) {
      setDelimiter(i, size, AND);
      printAttr(key, (String) invokedProcs.get(i));

    }
  }

  public void visit(ProcessExhibitionSentenceType e) {
    int key = OplColorScheme.PROCESS_STYLE;
    java.util.List exhibits = e.getExhibitedObject();
    bf.append(margin);
    printAttr(key, e.getProcessName());
    printAttr(OplColorScheme.DEFAULT_STYLE," exhibits ");
    java.util.List ops = e.getOperation();
    int size = ops.size();
    int objSize = exhibits.size();
    for (int i = 0; i < size; i++) {
      Operation op = (Operation) ops.get(i);
      setDelimiter(i, size, AND);
      printAttr(OplColorScheme.PROCESS_STYLE, op.getOperationName());
    }
    if ( (size > 0) && (objSize > 0))
      printAttr(OplColorScheme.DEFAULT_STYLE,", as well as ");
    for (int i = 0; i < objSize; i++) {
      ExhibitedObject exhib = (ExhibitedObject) exhibits.get(i);
      setDelimiter(i, objSize, AND);
      printAttr(OplColorScheme.OBJECT_STYLE, exhib.getAttributeName());
    }
    bf.append(endLine);
  }

  public void visit(ObjectExhibitionSentenceType e) {
    int key = OplColorScheme.OBJECT_STYLE;
    java.util.List exhibits = e.getExhibitedObject();
    java.util.List procs = e.getOperation();
    bf.append(margin);
    printAttr(key, e.getObjectName());
    addExhibitionObjectSentence(key, exhibits, procs);
    bf.append(endLine);
  }

  public void visit1(extensionTools.opl2.generated.ObjectInheritanceSentenceSet e) {
    addInhFather(OplColorScheme.OBJECT_STYLE, e.getObjectName(),e.getObjectInheritanceSentence());
  }

  public void visit1(extensionTools.opl2.generated.ProcessInheritanceSentenceSet e) {
  	addInhFather(OplColorScheme.PROCESS_STYLE, e.getObjectName(),e.getProcessInheritanceSentence());
  }

  public void visit(ObjectInstanceSentenceType e) {
   addInstFather(OplColorScheme.OBJECT_STYLE, e.getObjectName(),
                e.getInstanceFatherName());
 }

 public void visit(ProcessInstanceSentenceType e) {
   addInstFather(OplColorScheme.PROCESS_STYLE, e.getProcessName(),
                e.getInstanceFatherName());
 }


  public void visit(ObjectEnvironmentalPhysicalSentenceType sentence) {
    addEnvPhysical(OplColorScheme.OBJECT_STYLE, sentence.getObjectName(),
                   sentence.isEnvironmental(),
                   sentence.isPhysical());
  }

  public void visit(ProcessEnvironmentalPhysicalSentenceType sentence) {
    addEnvPhysical(OplColorScheme.PROCESS_STYLE, sentence.getProcessName(),
                   sentence.isEnvironmental(),
                   sentence.isPhysical());
  }

  protected void addEnvPhysical(int key, String subj,
                                boolean isEnvir, boolean isPhysical) {
    bf.append(margin);
    printAttr(key, subj);
    printAttr(OplColorScheme.DEFAULT_STYLE," is ");
    if (isEnvir && isPhysical) {
      printAttr(OplColorScheme.DEFAULT_STYLE,"environmental and physical");
      bf.append(endLine);
      return;
    }
    if (isEnvir) {
      printAttr(OplColorScheme.DEFAULT_STYLE,"environmental");
      bf.append(endLine);
      return;
    }
    printAttr(OplColorScheme.DEFAULT_STYLE,"physical");
    bf.append(endLine);
  }

  protected void addInhFather(int key, String subj, java.util.List objs) {
    bf.append(margin);
    printAttr(key, subj);
    int objSize = objs.size();
    if (key == OplColorScheme.PROCESS_STYLE) {
      printAttr(OplColorScheme.DEFAULT_STYLE," is ");
      for (int i = 0; i < objSize; i++) {
       extensionTools.opl2.generated.ProcessInheritanceSentence exhib = (extensionTools.opl2.generated.ProcessInheritanceSentence) objs.get(i);
       setDelimiter(i, objSize, AND);
       printAttr(key, exhib.getInheritanceFatherName());
     }
    }
    else {
      printAttr(OplColorScheme.DEFAULT_STYLE," is");
      for (int i = 0; i < objSize; i++) {
        extensionTools.opl2.generated.ObjectInheritanceSentence exhib = (extensionTools.opl2.generated.ObjectInheritanceSentence) objs.get(i);
        setDelimiter(i, objSize, AND);
        addASubject(key, exhib.getInheritanceFatherName());
      }
    }
    bf.append(endLine);
  }

  protected void addInstFather(int key, String subj, String obj) {
   bf.append(margin);
   printAttr(key, subj);

   if (key == OplColorScheme.PROCESS_STYLE) {
     printAttr(OplColorScheme.DEFAULT_STYLE," is instance of ");
     printAttr(key, obj);
   }
   else {
     printAttr(OplColorScheme.DEFAULT_STYLE," is instance of");
     addASubject(key, obj);
   }
   bf.append(endLine);
 }


  protected void addASubject(int key, String name) {
    char tmp = name.toLowerCase().charAt(0);
    if (tmp == 'a' || tmp == 'o' || tmp == 'e' || tmp == 'y' || tmp == 'u'
        || tmp == 'i')
      printAttr(OplColorScheme.DEFAULT_STYLE," an ");
    else
      printAttr(OplColorScheme.DEFAULT_STYLE," a ");
    printAttr(key, name);
  }


  public void visit(OPLscript opl) {
    java.util.List opls = opl.getThingSentenceSet();
    bf.append(startHTML);
    for (int i = 0; i < opls.size(); i++) {
      visit( (ThingSentenceSetType) opls.get(i));
    }
    int j = bf.lastIndexOf("<BR>");
    if(j!=-1)
      bf.delete(j,j+4);
    bf.append(endHTML);
  }

  public void visit(ChangingSentenceType sentence) {
    java.util.List clauses = sentence.getChangingClause();
    ChangingClause cl;
    int size = clauses.size();
    bf.append(margin);
    printPath(sentence.getPathLabel());
    printAttr(OplColorScheme.PROCESS_STYLE, sentence.getProcessName());
    printAttr(OplColorScheme.DEFAULT_STYLE," changes ");
    for (int i = 0; i < size; i++) {
      setDelimiter(i, size, sentence.getLogicalRelation());
      cl = (ChangingClause) clauses.get(i);
      printAttr(OplColorScheme.OBJECT_STYLE, cl.getObjectName());
      printAttr(OplColorScheme.DEFAULT_STYLE," from ");
      printAttr(OplColorScheme.STATE_STYLE, cl.getSourceStateName());
      printAttr(OplColorScheme.DEFAULT_STYLE," to ");
      printAttr(OplColorScheme.STATE_STYLE, cl.getDestinationStateName());
    }
    bf.append(endLine);
  }

  public void visit(ConditionSentenceType sentence) {
    java.util.List clauses = sentence.getConditionClause();
    ConditionClause cl;
    int size = clauses.size();
    bf.append(margin);
    printPath(sentence.getPathLabel());
    printAttr(OplColorScheme.PROCESS_STYLE, sentence.getProcessName());
    printAttr(OplColorScheme.DEFAULT_STYLE," occurs if ");
    for (int i = 0; i < size; i++) {
      setDelimiter(i, size, sentence.getLogicalRelation());
      cl = (ConditionClause) clauses.get(i);
      printAttr(OplColorScheme.OBJECT_STYLE, cl.getObjectName());
      printAttr(OplColorScheme.DEFAULT_STYLE," is ");
      printAttr(OplColorScheme.STATE_STYLE, cl.getStateName());
    }
    bf.append(endLine);
  }

  public void visit(ConsumptionSentenceType sentence) {
    java.util.List clauses = sentence.getConsumptionClause();
    int size = clauses.size();
    ConsumptionClause cl;
    bf.append(margin);
    printPath(sentence.getPathLabel());
    printAttr(OplColorScheme.PROCESS_STYLE, sentence.getProcessName());
    printAttr(OplColorScheme.DEFAULT_STYLE," consumes ");
    for (int i = 0; i < size; i++) {
      //System.err.println("My relation is: " + sentence.getLogicalRelation());
      setDelimiter(i, size, sentence.getLogicalRelation());
      cl = (ConsumptionClause) clauses.get(i);
      if (cl.getStateName() != null) {
        printAttr(OplColorScheme.STATE_STYLE, cl.getStateName());
        bf.append(" ");
      }
      printAttr(OplColorScheme.OBJECT_STYLE, cl.getObjectName());
    }
    bf.append(endLine);

  }

  public void visit(EffectSentenceType sentence) {
    java.util.List clauses = sentence.getEffectClause();
    int size = clauses.size();
    EffectClause cl;
    bf.append(margin);
    printPath(sentence.getPathLabel());
    printAttr(OplColorScheme.PROCESS_STYLE, sentence.getProcessName());
    printAttr(OplColorScheme.DEFAULT_STYLE," affects ");
    for (int i = 0; i < size; i++) {
     // System.err.println("My relation is1: " + sentence.getLogicalRelation());
      setDelimiter(i, size, sentence.getLogicalRelation());
      cl = (EffectClause) clauses.get(i);
      printAttr(OplColorScheme.OBJECT_STYLE, cl.getObjectName());
    }
    bf.append(endLine);
  }

  public void visit(AgentSentenceType sentence) {
    java.util.List procs = sentence.getTriggeredProcessName();
    int size = procs.size();
    bf.append(margin);
    printPath(sentence.getPathLabel());
    printAttr(OplColorScheme.OBJECT_STYLE, sentence.getObjectName());
    printAttr(OplColorScheme.DEFAULT_STYLE," handles ");
    for (int i = 0; i < size; i++) {
      //System.err.println("My relation is: " + sentence.getLogicalRelation());
      setDelimiter(i, size, sentence.getLogicalRelation());
      printAttr(OplColorScheme.PROCESS_STYLE, (String) procs.get(i));
    }
    bf.append(endLine);
  }

//LERA
  public void visit(StateEntranceSentence sentence) {
    java.util.List procs = sentence.getTriggeredProcess();
    int size = procs.size();
    bf.append(margin);
    printPath(sentence.getPathLabel());
    printAttr(OplColorScheme.OBJECT_STYLE, sentence.getObjectName());
    printAttr(OplColorScheme.DEFAULT_STYLE," triggers ");
    for (int i = 0; i < size; i++) {
     // System.err.println("My relation is: " + sentence.getLogicalRelation());
      setDelimiter(i, size, sentence.getLogicalRelation());
      printAttr(OplColorScheme.PROCESS_STYLE, (String) procs.get(i));
    }
    printAttr(OplColorScheme.DEFAULT_STYLE," when it enters ");
    printAttr(OplColorScheme.STATE_STYLE, sentence.getStateName());
    bf.append(endLine);
  }

  public void visit(StateTimeoutSentence sentence) {
    java.util.List procs = sentence.getTriggeredProcess();
    int size = procs.size();
    bf.append(margin);
    printPath(sentence.getPathLabel());
    printAttr(OplColorScheme.OBJECT_STYLE, sentence.getObjectName());
    printAttr(OplColorScheme.DEFAULT_STYLE," triggers ");
    for (int i = 0; i < size; i++) {
     // System.err.println("My relation is: " + sentence.getLogicalRelation());
      setDelimiter(i, size, sentence.getLogicalRelation());
      printAttr(OplColorScheme.PROCESS_STYLE, (String) procs.get(i));
    }
    String date = null;
    if (sentence.getMaxTimeoutValue() != null)
      date = OPLGeneral.visit(sentence.getMaxTimeoutValue());
    if (date != null && date.length()>0) {
      printAttr(OplColorScheme.DEFAULT_STYLE," when ");
      printAttr(OplColorScheme.STATE_STYLE, sentence.getStateName());
      printAttr(OplColorScheme.DEFAULT_STYLE," lasts more than ");
      //System.err.println(" Lasts max: "+);
      printAttr(OplColorScheme.TAG_STYLE,
                date);

    }
//    if (sentence.getMinReactionTime() != null && sentence.)
//      date = OPLGeneral.visit(sentence.getMinReactionTime());
//    if (date != null && date.length()>0) {
//      bf.append(" with reaction time from ");
//      printAttr(OplColorScheme.TAG_STYLE,
//                OPLGeneral.visit(date));
//      printAttr(OplColorScheme.STATE_STYLE, sentence.getStateName());
//      bf.append(" lasts more than ");
//      //System.err.println(" Lasts max: "+);
//      printAttr(OplColorScheme.TAG_STYLE,
//                OPLGeneral.visit(date));
//    }
    bf.append(endLine);
  }

  public void visit(GeneralEventSentence sentence, boolean hasStates) {
    java.util.List procs = sentence.getTriggeredProcessName();
    int size = procs.size();
    bf.append(margin);
    printPath(sentence.getPathLabel());
    printAttr(OplColorScheme.OBJECT_STYLE, sentence.getObjectName());
    printAttr(OplColorScheme.DEFAULT_STYLE," triggers ");
    for (int i = 0; i < size; i++) {
      setDelimiter(i, size, sentence.getLogicalRelation());
      printAttr(OplColorScheme.PROCESS_STYLE, (String) procs.get(i));
    }
    if (hasStates)
      printAttr(OplColorScheme.DEFAULT_STYLE," when its state changes");
    bf.append(endLine);
  }

  public void visit(EnablingSentenceType sentence) {
    java.util.List clauses = sentence.getEnablingClause();
    int size = clauses.size();
    EnablingClause cl;
    bf.append(margin);
    printPath(sentence.getPathLabel());
    printAttr(OplColorScheme.PROCESS_STYLE, sentence.getProcessName());
    printAttr(OplColorScheme.DEFAULT_STYLE," requires ");
    for (int i = 0; i < size; i++) {
      setDelimiter(i, size, sentence.getLogicalRelation());
      cl = (EnablingClause) clauses.get(i);
      if (cl.getStateName() != null) {
        printAttr(OplColorScheme.STATE_STYLE, cl.getStateName());
        bf.append(" ");
      }
      printAttr(OplColorScheme.OBJECT_STYLE, cl.getObjectName());
    }
    bf.append(endLine);
  }

  public void visit(ResultSentenceType sentence) {
    java.util.List clauses = sentence.getResultClause();
    int size = clauses.size();
    ResultClause cl;
    bf.append(margin);
    printPath(sentence.getPathLabel());
    printAttr(OplColorScheme.PROCESS_STYLE, sentence.getProcessName());
    printAttr(OplColorScheme.DEFAULT_STYLE," yields ");
    for (int i = 0; i < size; i++) {
      setDelimiter(i, size, sentence.getLogicalRelation());
      cl = (ResultClause) clauses.get(i);
      if (cl.getStateName() != null) {
        printAttr(OplColorScheme.STATE_STYLE, cl.getStateName());
        bf.append(" ");
      }
      printAttr(OplColorScheme.OBJECT_STYLE, cl.getObjectName());
    }
    bf.append(endLine);

  }

//   public java.util.Hashtable getPaths(ThingSentenceSetType e){
//   java.util.Hashtable paths = new java.util.Hashtable();
//   java.util.List oldPath=new java.util.LinkedList();
//   if(e.getStateEntranceSentence().size()>0){
//     oldPath = ((StateEntranceSentence)e.getStateEntranceSentence().get(0)).getPathLabel();
//   }
//   int j=0;
//   for(int i=0;i<e.getStateEntranceSentence().size();i++){
//     StateEntranceSentence tmp = (StateEntranceSentence)e.getStateEntranceSentence().get(i);
//     if(tmp.getPathLabel().equals(oldPath)){
//       j++;
//     }else {
//       paths.put(oldPath,new Integer(j));
//       oldPath = tmp.getPathLabel();
//       j=1;
//     }
//   }
//   return paths;
//   }

//  public void visitStateTriggers(ThingSentenceSetType e){
//    if(e.getStateEntranceSentence().size()==0)
//      return;
//    java.util.List oldPath = ((StateEntranceSentence)e.getStateEntranceSentence().get(0)).getPathLabel();
//    for(int i=0;i<e.getStateEntranceSentence().size();){
//     int start = i;
//     int end = start;
//     int size=0;
//     java.util.List currPath = ((StateEntranceSentence)e.getStateEntranceSentence().get(i)).getPathLabel();
//     while(currPath.equals(oldPath)){
//       end++;
//       if( end <
//           e.getStateEntranceSentence().size())
//       currPath = ((StateEntranceSentence)e.getStateEntranceSentence().get(end)).getPathLabel();
//     }
//     i=end;
//     size = end-start;
//     end--;
//     StateEntranceSentence tmp = (StateEntranceSentence)e.getStateEntranceSentence().get(start);
//     bf.append(margin);
//     printPath(tmp.getPathLabel());
//     printAttr(OplColorScheme.OBJECT_STYLE, tmp.getObjectName());
//     printAttr(OplColorScheme.DEFAULT_STYLE," triggers ");
//     for(int k=start;k<=end;k++){
//       setDelimiter(k-start, size, AND);
//       tmp = (StateEntranceSentence) e.getStateEntranceSentence().get(k);
//       visit(tmp);
//     }
//     bf.append(endLine);
//     oldPath = ((StateEntranceSentence)e.getStateEntranceSentence().get(i)).getPathLabel();
//   }
//  }

//  public void visitStateTriggers(ThingSentenceSetType e){
//   Hashtable paths = getPaths(e);
//   boolean firstTime = true;
//   int size =0;
//   int j=0;
//   for(int i=0;i<e.getStateEntranceSentence().size();i++){
//     StateEntranceSentence tmp = (StateEntranceSentence) e.getStateEntranceSentence().get(i);
//     if(firstTime){
//       size = ((Integer)paths.get(tmp.getPathLabel())).intValue();
//       bf.append("my size is"+size);
//       j=0;
//       firstTime = false;
//       bf.append(margin);
//       printPath(tmp.getPathLabel());
//       printAttr(OplColorScheme.OBJECT_STYLE, tmp.getObjectName());
//       printAttr(OplColorScheme.DEFAULT_STYLE," triggers ");
//       setDelimiter(j, size, AND);
//       visit(tmp);
//       bf.append("my size is"+size);
//       if(size==1)bf.append(endLine);
//     }else{
//       if(j+1==size){
//         setDelimiter(j, size, AND);
//         visit(tmp);
//         bf.append(endLine);
//         firstTime = true;
//         }else {
//           setDelimiter(j, size, AND);
//           visit(tmp);
//         }
//     }
//     j++;
//     paths.put(tmp.getPathLabel(), new Integer(((Integer)paths.get(tmp.getPathLabel())).intValue()-1));
//   }
//
//  }

  public void visitExistential(ThingSentenceSetType e){
	bf.append(margin);
	String exist = e.getExistential();
	if(e.equals("exists")){
		printAttr(OplColorScheme.PROCESS_STYLE, e.getSubjectThingName());
	}else{
		printAttr(OplColorScheme.OBJECT_STYLE, e.getSubjectThingName());
	}
	printAttr(OplColorScheme.DEFAULT_STYLE," " + exist);
	bf.append(endLine);
  }

  public void visit(ThingSentenceSetType e) {
    int i;
    boolean hasStates = false;
	if(e.getExistential()!=null){
		  //visitExistential(e);
		  return;	
	}
    if (e.getTypeDeclarationSentence() != null) {
      visit(e.getTypeDeclarationSentence());
      printRoles(e);
      return;
    }
    if (e.getObjectEnvironmentalPhysicalSentence() != null)
      visit(e.getObjectEnvironmentalPhysicalSentence());
    if (e.getObjectInheritanceSentenceSet() != null) {
      visit1((ObjectInheritanceSentenceSet)e.getObjectInheritanceSentenceSet());
    }
    if (e.getObjectInstanceSentence() != null)
      visit(e.getObjectInstanceSentence());
    if (e.getObjectStateSentence() != null) {
      visit(e.getObjectStateSentence());
      hasStates = true;
    }
    if (e.getObjectExhibitionSentenceSet() != null)
      visit(e.getObjectExhibitionSentenceSet());
    if (e.getObjectAggregationSentenceSet() != null)
      visit(e.getObjectAggregationSentenceSet());
    if (e.getObjectUniDirectionalRelationSentence() != null) {
      for (i = 0; i < e.getObjectUniDirectionalRelationSentence().size(); i++) {
        visit( (ObjectUniDirectionalRelationSentenceType) e.
              getObjectUniDirectionalRelationSentence().get(i));
      }
    }
    if (e.getObjectBiDirectionalRelationSentence() != null) {
      for (i = 0; i < e.getObjectBiDirectionalRelationSentence().size(); i++) {
        visit( (ObjectBiDirectionalRelationSentence) e.
              getObjectBiDirectionalRelationSentence().get(i),
              e.getSubjectThingName());
      }
    }
    for (i = 0; i < e.getAgentSentence().size(); i++)
      visit( (AgentSentence) e.getAgentSentence().get(i));
    for (i = 0; i < e.getGeneralEventSentence().size(); i++) {
      visit( (GeneralEventSentence) e.getGeneralEventSentence().get(i),
            hasStates);
    }
   // visitStateTriggers(e);
    for (i = 0; i < e.getStateEntranceSentence().size(); i++)
      visit( (StateEntranceSentence) e.getStateEntranceSentence().get(i));
    for (i = 0; i < e.getStateTimeoutSentence().size(); i++)
      visit( (StateTimeoutSentence) e.getStateTimeoutSentence().get(i));
    if (e.getObjectInZoomingSentenceSet() != null)
      visit(e.getObjectInZoomingSentenceSet());
    if (e.getProcessEnvironmentalPhysicalSentence() != null)
      visit(e.getProcessEnvironmentalPhysicalSentence());
    if (e.getProcessInheritanceSentenceSet() != null)
      visit1((extensionTools.opl2.generated.ProcessInheritanceSentenceSet)e.getProcessInheritanceSentenceSet());
    if (e.getProcessInstanceSentence() != null)
      visit(e.getProcessInstanceSentence());
    if (e.getProcessExhibitionSentenceSet() != null)
      visit(e.getProcessExhibitionSentenceSet());
    if (e.getProcessAggregationSentenceSet() != null)
      visit(e.getProcessAggregationSentenceSet());
    if (e.getProcessUniDirectionalRelationSentence() != null) {
      for (i = 0; i < e.getProcessUniDirectionalRelationSentence().size(); i++) {
        visit( (ProcessUniDirectionalRelationSentence) e.
              getProcessUniDirectionalRelationSentence().get(i));
      }
    }
    if (e.getProcessBiDirectionalRelationSentence() != null) {
      for (i = 0; i < e.getProcessBiDirectionalRelationSentence().size(); i++) {
        visit( (ProcessBiDirectionalRelationSentence) e.
              getProcessBiDirectionalRelationSentence().get(i),
              e.getSubjectThingName());
      }
    }
    for (i = 0; i < e.getConditionSentence().size(); i++)
      visit( (ConditionSentenceType) e.getConditionSentence().get(i));
    for (i = 0; i < e.getEnablingSentence().size(); i++)
      visit( (EnablingSentenceType) e.getEnablingSentence().get(i));
    for (i = 0; i < e.getEffectSentence().size(); i++)
      visit( (EffectSentenceType) e.getEffectSentence().get(i));
    for (i = 0; i < e.getChangingSentence().size(); i++)
      visit( (ChangingSentenceType) e.getChangingSentence().get(i));
    for (i = 0; i < e.getConsumptionSentence().size(); i++)
      visit( (ConsumptionSentenceType) e.getConsumptionSentence().get(i));
    for (i = 0; i < e.getResultSentence().size(); i++)
      visit( (ResultSentenceType) e.getResultSentence().get(i));
    for (i = 0; i < e.getProcessTimeoutSentence().size(); i++)
      visit( (ProcessTimeoutSentenceType) e.getProcessTimeoutSentence().get(i));
    for (i = 0; i < e.getProcessInvocationSentence().size(); i++)
      visit( (ProcessInvocationSentenceType) e.getProcessInvocationSentence().
            get(i));
    if (e.getProcessInZoomingSentenceSet() != null){
      visit(e.getProcessInZoomingSentenceSet());
    }
    printRoles(e);
  }
  
  public void printRoles(ThingSentenceSetType e){
	if(e.getRole()==null || e.getRole().isEmpty())
		return;
	bf.append(margin);
	printAttr(OplColorScheme.OBJECT_STYLE, e.getSubjectThingName());
    if(e.getRole().size()>1)
			printAttr(OplColorScheme.DEFAULT_STYLE," plays roles");
	else printAttr(OplColorScheme.DEFAULT_STYLE," plays the role");
	for(int i = 0; i < e.getRole().size(); i++){
		  Role rl = (Role)e.getRole().get(i);
		  setDelimiter(i, e.getRole().size(), AND);
		  printAttr(OplColorScheme.DEFAULT_STYLE," of "+rl.getRoleName());
		  if(rl.getLibrary()!=null && !rl.getLibrary().equals("")){
			printAttr(OplColorScheme.DEFAULT_STYLE," of "+rl.getLibrary());
		  }
		}
	bf.append(endLine);
  }

  public void visit(TypeDeclarationSentenceType e) {
    bf.append(margin);
    printAttr(OplColorScheme.OBJECT_STYLE, e.getObjectName());
    printAttr(OplColorScheme.DEFAULT_STYLE," is of type ");
    char firstChar = e.getObjectType().charAt(0);
    if ( ('0' <= firstChar) && (firstChar <= '9')) {
      printAttr(OplColorScheme.DEFAULT_STYLE,"character string of legth " + e.getObjectType());
    }
    else
      printAttr(OplColorScheme.DEFAULT_STYLE,e.getObjectType());
    bf.append(endLine);
    if (e.getObjectScope().equals("public")) {
      return;
    }
    else {
      bf.append(margin);
      printAttr(OplColorScheme.OBJECT_STYLE, e.getObjectName());
      printAttr(OplColorScheme.DEFAULT_STYLE," is " + e.getObjectScope());
      bf.append(endLine);
    }
    if (e.getInitialValue() != null) {
      if (e.getInitialValue().equals("")) {
        return;
      }
    }
    else
      return;
    bf.append(margin);
    printAttr(OplColorScheme.OBJECT_STYLE, e.getObjectName());
    printAttr(OplColorScheme.DEFAULT_STYLE," is "+e.getInitialValue()+" by default");
    bf.append(endLine);

  }

  public void printParagraph(java.util.List things, boolean isPerState) {
    if (things.isEmpty())
      return;
    margin.append("&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp ");
    if (!isPerState) {
      for (java.util.ListIterator itr = things.listIterator(); itr.hasNext(); ) {
        visit( (ThingSentenceSet) itr.next());
      }
    }
    else {
      StateClause cl;
      StateClause def = getDefaultState(things);
      if(def!=null)
        visit(def);
      for (java.util.ListIterator itr = things.listIterator(); itr.hasNext(); ) {
        cl = (StateClause) itr.next();
        if(cl!=def)
          visit(cl);
      }
    }
    margin.delete(margin.length() - 36, margin.length());
  }

  public void printAttr(int attributeName, String value) {
    /*
         if (colors.containsKey(attributeName)){
      bf.append(value);
      return;
         }*/
    bf.append(colorScheme.getAttribute(attributeName).openingHTMLFontTag());
    bf.append(value);
    bf.append(colorScheme.getAttribute(attributeName).closingHTMLFontTag());
  }

  public void printPath(java.util.List pathList) {
    if (pathList.isEmpty())
      return;
    printAttr(OplColorScheme.DEFAULT_STYLE,"Following path");
    for (int i = 0; i < pathList.size(); i++) {
      printAttr(OplColorScheme.DEFAULT_STYLE," ");
      printAttr(OplColorScheme.TAG_STYLE,(String) pathList.get(i));
    }
    printAttr(OplColorScheme.DEFAULT_STYLE,", ");
  }

  protected void setDelimiter(int index, int size, int op) {
    if (size == 1)
      return;
    if (index == size - 1) {
      if (size > 2)
        printAttr(OplColorScheme.DEFAULT_STYLE,",");
      if(op!=XOR)
        printAttr(OplColorScheme.DEFAULT_STYLE," " + prop[op] + " ");
      else
        printAttr(OplColorScheme.DEFAULT_STYLE," " + prop[OR] + " ");
    }
    else if(index == size - 2 && op==XOR)
                printAttr(OplColorScheme.DEFAULT_STYLE," " + prop[op] + " ");
        else
          if (index > 0)
                printAttr(OplColorScheme.DEFAULT_STYLE,", ");
  }

  protected void setDelimiter(int index, int size, String op) {
    if (op.equals("and"))
      setDelimiter(index, size, AND);
    else
      if(op.equals("or"))
         setDelimiter(index, size, XOR);
      else
        setDelimiter(index, size, OR);
  }

  protected void setEndLine() {
    endLine = colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).
        openingHTMLFontTag()+"."+colorScheme.getAttribute(OplColorScheme.DEFAULT_STYLE).
      closingHTMLFontTag()+"<BR>";

  }

  protected ISystemStructure elements;
  protected String startHTML;
  protected String endHTML;

  protected String endLine;
  protected StringBuffer margin = new StringBuffer();
}