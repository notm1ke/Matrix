package co.m1ke.matrix.compat;

import co.m1ke.matrix.Matrix;
import co.m1ke.matrix.event.events.CompatibilityEvent;
import co.m1ke.matrix.event.interfaces.Event;
import co.m1ke.matrix.event.listener.Listener;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.prefs.Preferences;
import co.m1ke.matrix.prefs.models.CompatibilityMode;
import co.m1ke.matrix.util.Comparables;
import co.m1ke.matrix.util.container.Key;

public class SpigotCompatibility extends Listener {

    private Logger logger;
    private Preferences preferences;

    public SpigotCompatibility(Preferences preferences) {
        super("Spigot Compatibility", Matrix.getSelfPlugin().getEventManager());

        this.logger = new Logger("Compat");
        this.preferences = preferences;
    }

    @Override
    public void init() {
        registerSelf();
    }

    private void run() {
        if (this.preferences.getCompatibilityMode() != CompatibilityMode.SPIGOT) {
            return;
        }

        Key version = this.preferences.getKey("spigotVersion");

        if (!Comparables.isDouble(version.asString())) {
            if (version.asString().equalsIgnoreCase("FIRST_TIME_SETUP")) {
                logger.warning("Spigot compatibility hook is not ready. Please visit options.json to configuration it.");
                return;
            }
            logger.severe("Illegible compatibility preferences.");
            return;
        }

        new Thread(() -> {

        }, "Spigot Compatibility Thread").run();
    }

    @Event
    public void onCompatibilityEvent(CompatibilityEvent e) {
        if (e.getCompatibilityMode() != CompatibilityMode.SPIGOT) {
            this.unregisterSelf();
            return;
        }
        Matrix.getPreferences().setKey("spigotVersion", Key.of("FIRST_TIME_SETUP"));
        Matrix.getPreferences().save();
    }

    public Preferences getPreferences() {
        return preferences;
    }

}
