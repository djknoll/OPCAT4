package exportedAPI.opcatAPI;

/**
 * IProcessEntry is an interface to logical representation of Process in OPM meaning
 */

public interface IProcessEntry extends IThingEntry {

    /**
     * Returns process body of this IProcessEntry
     *
     * @return process body of this IProcessEntry
     */
    public String getProcessBody();

    /**
     * Returns NAME representing maximum activation time
     * of this Process. This NAME contains non-negative integer X 7
     * (msec, sec, min, hours, days, months, years) with semi-colons
     * separation.
     *
     * @return maximum activation time in string representation
     */
    public String getMaxTimeActivation();

    /**
     * Returns NAME representing minimum activation time
     * of this Process. This NAME contains non-negative integer X 7
     * (msec, sec, min, hours, days, months, years) with semi-colons
     * separation.
     *
     * @return minimum activation time in string representation
     */
    public String getMinTimeActivation();

}