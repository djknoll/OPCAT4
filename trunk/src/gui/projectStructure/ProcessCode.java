package gui.projectStructure;

import java.util.Iterator;
import java.util.LinkedList;

public class ProcessCode extends LinkedList<String> {

    /**
     *
     */
    public enum OPCAT_CODE_LANG {
        RPG, JAVA, SQL
    }

    ;


    public enum OPCAT_CODE_TYPE {
        PROCESS, ExternalProcedure, InternalProcedure, Code
    }

    ;

    //TABLE, FIELD, , InternalDS, ExternalDS, Variable, Constant, KEY

    private static final long serialVersionUID = -8834857855621082695L;
    private int executionOrder = -1;
    private OPCAT_CODE_LANG lang;

    public ProcessCode(String code, int thingExecutionOrder, OPCAT_CODE_LANG lang, OPCAT_CODE_TYPE type) {
        super();
        this.add(code);
        this.executionOrder = thingExecutionOrder;
        this.lang = lang;
    }

    public String getThingCodeText() {
        return this.getFirst();
    }

    public String getThingAggragetedCodeText() {
        Iterator<String> iter = this.iterator();
        String ret = "";
        while (iter.hasNext()) {
            ret = ret + iter.next();
        }
        return ret;
    }


}
