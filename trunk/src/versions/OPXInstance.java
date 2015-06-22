package versions;

/**
 * Created by raanan.
 * Date: May 24, 2010
 * Time: 6:56:59 PM
 */
public class OPXInstance extends OPXEntity {

    int order = -1;

    public OPXInstance(OPX_ENTITY_TYPE ENTITYTYPE, int order, OPXID uuid) {
        super(ENTITYTYPE, uuid);
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

}
