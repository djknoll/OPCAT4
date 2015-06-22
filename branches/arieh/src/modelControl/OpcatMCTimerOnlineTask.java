package modelControl;

import java.net.InetAddress;
import java.util.TimerTask;

public class OpcatMCTimerOnlineTask extends TimerTask {

    @Override
    public void run() {
        try {
            InetAddress address = InetAddress.getByName(OpcatMCManager.server);
            if (address != null) {
                OpcatMCManager.setOnline(true);
            } else {
                OpcatMCManager.setOnline(false);
            }
        } catch (Exception ex) {
            OpcatMCManager.setOnline(false);
        }
    }
}
