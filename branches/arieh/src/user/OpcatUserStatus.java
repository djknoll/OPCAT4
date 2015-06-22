package user;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Oct 24, 2009
 * Time: 12:25:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpcatUserStatus {
    public final static int LOGIN_OK = 0;
    public final static int LOGIN_FAIL = 1;
    public final static int NOT_ONLINE = 2;
    public final static int NOT_TRIED = 3;
    public final static int LOGIN_CANCEL = 4;

    int myStatus;

    public int currentStatus() {
        return myStatus;
    }

    public OpcatUserStatus(int status) {
        myStatus = status;
    }
}
