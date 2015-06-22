package gui.opmEntities;

import gui.projectStructure.OpcatProperties;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class represents Process in OPM. For better understanding of this class
 * you should be familiar with OPM.
 *
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * @version 2.0
 */

public class OpmProcess extends OpmThing {

    public static final String DEFAULT_MAX_TIME_ACTIBVATION = "infinity";
    public static final String DEFAULT_MIN_TIME_ACTIBVATION = new String("0;0;0;0;0;0;0");
    public static final String DEFAULT_PROCESS_BODY = "none";


    // ---------------------------------------------------------------------------
    // The private attributes/members are located here
    private String processBody;
    //private String maxTimeActivation;
    //private String minTimeActivation;

    /**
     * Creates an OpmProcess with specified id and name. Id of created
     * OpmProcess must be unique in OPCAT system. Other data members of
     * OpmProcess get default values.
     *
     * @param processId   OpmProcess id
     * @param processName OpmProcess name
     */
    public OpmProcess(long processId, String processName) {
        super(processId, processName);
        getMyProps().setProperty(OpcatProperties.maxTimeActivation, DEFAULT_MAX_TIME_ACTIBVATION);
        getMyProps().setProperty(OpcatProperties.minTimeActivation, DEFAULT_MIN_TIME_ACTIBVATION);
        this.processBody = DEFAULT_PROCESS_BODY;
    }

    // ---------------------------------------------------------------------------
    // The public methods are located here

    public void copyPropsFrom(OpmEntity origin) {
        super.copyPropsFrom(origin);
        getMyProps().setProperty(OpcatProperties.maxTimeActivation, ((OpmProcess) origin).getMaxTimeActivation());
        getMyProps().setProperty(OpcatProperties.minTimeActivation, ((OpmProcess) origin).getMinTimeActivation());
        this.processBody = ((OpmProcess) origin).getProcessBody();
        // Added by Eran Toch
        // rolesManager = origin.getRolesManager();
    }

    public boolean hasSameProps(OpmProcess pProcess) {
        return (super.hasSameProps(pProcess)
                && getMyProps().getProperty(OpcatProperties.maxTimeActivation).equals(pProcess
                .getMaxTimeActivation())
                && getMyProps().getProperty(OpcatProperties.minTimeActivation).equals(pProcess
                .getMinTimeActivation()) && this.processBody
                .equals(pProcess.getProcessBody()));
    }

    /**
     * Returns process body of this OpmProcess
     */
    public String getProcessBody() {
        return this.processBody;
    }

    /**
     * Sets process body of this OpmProcess
     */
    public void setProcessBody(String processBody) {
        this.processBody = processBody;
    }

    /**
     * Returns NAME representing maximum activation time of this OpmProcess.
     * This NAME contains non-negative integer X 7 (msec, sec, min, hours,
     * days, months, years) with semi-colons separation.
     */

    public String getMaxTimeActivation() {
        return getMyProps().getProperty(OpcatProperties.maxTimeActivation);
    }

    //retuen min activation in mSecs,  if Infinity return 1000 years in msec
    public long getMinActivationTimeInmSec() {

        String time = getMinTimeActivation();
        long ret = 0;

        if (time.equalsIgnoreCase("0;0;0;0;0;0;0")) {
            return 0;
        }

        if (time.equalsIgnoreCase("infinity")) {
            return 1000 * 12 * 30 * 24 * 60 * 60 * 1000;
        }

        StringTokenizer st = new StringTokenizer(time, ";");
        ArrayList<Integer> fields = new ArrayList<Integer>();
        while (st.hasMoreTokens()) {
            fields.add(Integer.valueOf(st.nextToken()));
        }

        ret  = fields.get(0) ;
        ret =  ret +  fields.get(1) * 1000 ;
        ret =  ret +  fields.get(2) * 60 * 1000 ;
        ret =  ret +  fields.get(3) * 60 * 60 * 1000 ;
        ret =  ret +  fields.get(4) * 24 * 60 * 60 * 1000 ;
        ret =  ret +  fields.get(5) * 30 * 24 * 60 * 60 * 1000 ;
        ret =  ret +  fields.get(6) * 12 * 30 * 24 * 60 * 60 * 1000 ;
        return ret;
    }


    private String formatTime(String time) {
        if (time.equalsIgnoreCase("0;0;0;0;0;0;0")) {
            return "0";
        }

        if (time.equalsIgnoreCase("infinity")) {
            return "infinity";
        } else {
            ArrayList<String> fields = new ArrayList<String>();
            ArrayList<String> names = new ArrayList<String>();

            names.add("msec");
            names.add("sec");
            names.add("min");
            names.add("hours");
            names.add("days");
            names.add("months");
            names.add("years");

            StringTokenizer st = new StringTokenizer(time, ";");
            while (st.hasMoreTokens()) {
                fields.add(st.nextToken());
            }
            String ret = "";
            for (int i = fields.size() - 1; i > -1; i--) {
                if (!fields.get(i).equalsIgnoreCase("0")) {
                    ret = ret + fields.get(i) + " " + names.get(i) + ",";
                }
            }
            return ret.substring(0, ret.length() - 1);
        }
    }

    public String getMaxTimeActivationString() {
        return formatTime(getMaxTimeActivation());
    }

    /**
     * Sets maximum activation time of this OpmProcess. This field contains
     * non-negative integer X 7 (msec, sec, min, hours, days, months, years)
     * with semi-colons separation.
     */

    public void setMaxTimeActivation(String time) {
        getMyProps().setProperty(OpcatProperties.maxTimeActivation, time);
    }

    /**
     * Returns NAME representing minimum activation time of this OpmProcess.
     * This NAME contains non-negative integer X 7 (msec, sec, min, hours,
     * days, months, years) with semi-colons separation.
     */

    public String getMinTimeActivation() {
        return getMyProps().getProperty(OpcatProperties.minTimeActivation);
    }

    public String getMinTimeActivationString() {
        return formatTime(getMinTimeActivation());
    }

    /**
     * Sets minimum activation time of this OpmProcess. This field contains
     * non-negative integer X 7 (msec, sec, min, hours, days, months, years)
     * with semi-colons separation.
     */

    public void setMinTimeActivation(String time) {
        getMyProps().setProperty(OpcatProperties.minTimeActivation, time);
    }

}