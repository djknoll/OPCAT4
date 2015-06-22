package versions;

/**
 * Created by raanan.
 * Date: May 6, 2010
 * Time: 12:02:53 PM
 */
public class OPXLibrary extends OPXEntity {

    private static final String ID_GENERATOR_TAG = "libraryreference.libpath";

    public OPXLibrary(OPX_ENTITY_TYPE type) {
        super(type);
    }

    public long getLibID() {
        return (get(ID_GENERATOR_TAG) == null ? -1 : get(ID_GENERATOR_TAG).hashCode());
    }

}
