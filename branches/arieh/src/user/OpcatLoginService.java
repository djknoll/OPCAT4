package user;

import modelControl.OpcatMCManager;
import org.jdesktop.swingx.auth.LoginService;
import util.OpcatLogger;

public class OpcatLoginService extends LoginService {

    @Override
    public boolean authenticate(String name, char[] password, String server)
            throws Exception {
        try {
            OpcatMCManager.authenticate(name, new String(password));
        } catch (Exception ex) {
            // OpcatLogger.logError(ex);
            OpcatLogger.error("While logging into : " + OpcatMCManager.server,
                    false);
            OpcatLogger.error(ex, false);
            return false;
        }
        return true;
    }

}
