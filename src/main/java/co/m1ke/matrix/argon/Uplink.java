package co.m1ke.matrix.argon;

import co.m1ke.matrix.Matrix;
import co.m1ke.matrix.event.events.uplink.ArgonUplinkEvent;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.util.Defaults;
import co.m1ke.matrix.util.JsonUtils;

import org.json.JSONObject;

public class Uplink {

    private String dest;
    private Logger logger;

    public Uplink(String dest) {
        this.dest = dest;
        this.logger = new Logger("Link");
        if (!this.dest.contains(Defaults.ARGON_SERVICE_PATTERN)) {
            logger.warning("Uplink destination is not an official Argon endpoint.");
        }

        this.establish();
    }

    public void establish() {

        // TODO: Store information inside UplinkResponse object for Argon instancing + SecureBoot.

        try {
            JSONObject res = JsonUtils.getFromUrl(this.dest);
            logger.info("Uplink -> Connected via Cloud (" + res.getString("instance") + ") version " + res.getDouble("version") + ".");
            Matrix.getEventManager().emit(new ArgonUplinkEvent());
        } catch (Exception e) {
            logger.severe("Uplink is not ready.");
            /*logger.except(e, "Failed to retrieve cloud uplink");
            Matrix.getEventManager().emit(new ArgonUplinkFailureEvent(e));*/
        }
    }

}
