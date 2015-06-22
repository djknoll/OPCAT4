package gui.license;

/**
 * Represent a trail version of Opcat, which is limited by the number of things
 * that can be created. Please note that this license enables to open models
 * with more than <ocde>MAX_NUM_OF_THINGS</code>, but will not allow to add elements
 * with more than this number to the model.
 *
 * @author Eran Toch
 */
public class RestrictedLicense extends GenericLicense {

    /**
     * Sets the number of things that can be created by this Opcat.
     */
    private static final int MAX_NUM_OF_THINGS = 0;
    /* (non-Javadoc)
      * @see gui.license.ILicense#isLimitedLicense()
      */

    public boolean isLimitedLicense() {
        return true;
    }

    /* (non-Javadoc)
      * @see gui.license.ILicense#getMaxNumberOfThings()
      */

    public int getMaxNumberOfThings() {
        return MAX_NUM_OF_THINGS;
    }


    /* (non-Javadoc)
      * @see gui.license.GenericLicense#getLicenseCaption()
      */

    public String getLicenseCaption() {
        if (MAX_NUM_OF_THINGS == 0) {
            return "Viewer ";

        } else {
            return "Limited License - limited to " + MAX_NUM_OF_THINGS + " entities";
        }
    }
    /* (non-Javadoc)
      * @see gui.license.ILicense#getLicenseError()
      */

    public String getLicenseError() {
        if (MAX_NUM_OF_THINGS == 0) {
            return "Viewer Version does not permit to create models ";

        } else {
            return "Your license does not permit to create models with over " + MAX_NUM_OF_THINGS + " entities (objects, processes and states)";
        }
    }

    /* (non-Javadoc)
    * @see gui.license.ILicense#getLicenseDiscription()
    */

    public String getLicenseDescription() {
        if (MAX_NUM_OF_THINGS == 0) {
            return "Viewer ";
        } else {
            return "Limited License - limited to " + MAX_NUM_OF_THINGS + " entities";
        }
    }
}
