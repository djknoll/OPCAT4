package exportedAPI.opcatAPIx;
import java.util.Enumeration;
/**
 * IXLinkInstance is an interface for graphical instance of the IXLinkEntry.
 */
public interface IXLinkInstance extends IXInstance
{

    /**
     * Gets IXConnectionEdgeInstance which is source of this IXLinkInstance.
     */

    public IXConnectionEdgeInstance getSourceIXInstance();

    /**
     * Gets IXConnectionEdgeInstance which is destination of this IXLinkInstance.
     */
    public IXConnectionEdgeInstance getDestinationIXInstance();


    /**
     * Animates this link for time milliseconds. In current implementation
     * red point runs on line for time milliseconds. Animation stops automaticaly
     * after time milliseconds.
     */
    public void animate(long time);

    /**
     * Animates this link for remainedTime milliseconds, when starting at
     * (1 - remainedTime/totalTime) point. In current implementation
     * red point runs on line for remainedTime milliseconds. Animation stops automaticaly
     * after reamainedTime milliseconds.
     */

    public void animate(long totalTime, long remainedTime);

    /**
     * Returns time (long milliseconds) remained until enf of animation.
     * @return time (long milliseconds) remained until enf of animation.
     */

    public long getRemainedAnimationTime();

    /**
     * Returns total time (long milliseconds) of animation if this IXLinkInstance
     * is animated. Otherwise returns 0.
     * @return total time (long milliseconds) of animation.
     */

    public long getTotalAnimationTime();

    /**
     * Animates this link for very short period of time. In current implementation
     * line's color becomes red for 350 milliseconds.
     */
    public void animateAsFlash();

    /**
     * Stops the animation immediately.
     */
    public void stopAnimation();

    /**
     * Checks if this IXLinkInstance is animated.
     * @return true if this IXLinkInstance is animated.
     */
    public boolean isAnimated();

    /**
     * Pauses animation. If this IXLinkInstance is not animated
     * this command will be ignored.
     */
    public void pauseAnimation();

    /**
     * Continues paused animation. If this IXLinkInstance is not animated
     * and not paused this command will be ignored.
     */
    public void continueAnimation();

    /**
     * Returns Enumeration of IXLinkInstance - all procedural links having
     * Xor/Or logical relation with this IXLinkInstance at source point.
     * Returns null if no neighbour link by source is found.
     * @param isOr defines needed type of logical relation,
     * true/false means Or/Xor.
     */
    public Enumeration getOrXorSourceNeighbours(boolean isOr);

    /**
     * Returns Enumeration of IXLinkInstance - all procedural links having
     * Xor/Or logical relation with this IXLinkInstance at destination point.
     * Returns null if no neighbour link by destination is found.
     * @param isOr defines needed type of logical relation,
     * true/false means Or/Xor.
     */
    public Enumeration getOrXorDestinationNeighbours(boolean isOr);


}