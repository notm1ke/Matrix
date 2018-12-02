package co.m1ke.matrix.argon.boot;

import co.m1ke.matrix.logging.Logger;

public class SecureBoot {

    private Logger logger;

    public SecureBoot() {
        this.logger = new Logger("Boot");
        logger.warning("SecureBoot is not ready.");
    }

}
