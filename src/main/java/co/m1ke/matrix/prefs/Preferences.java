package co.m1ke.matrix.prefs;

import co.m1ke.matrix.Matrix;
import co.m1ke.matrix.closable.Closable;
import co.m1ke.matrix.closable.ClosableOverride;
import co.m1ke.matrix.error.MatrixExceptionImpl;
import co.m1ke.matrix.event.events.CompatibilityEvent;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.prefs.models.CompatibilityMode;
import co.m1ke.matrix.util.JsonUtils;
import co.m1ke.matrix.util.container.Key;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Preferences implements Closable, ClosableOverride {

    private File file;
    private Logger logger;
    private JSONObject body;

    private CompatibilityMode compatibilityMode;

    public Preferences() {
        this.logger = new Logger("Prefs");
        this.file = new File("options.json");

        try {
            if (!this.file.exists()) {
                if (!this.file.createNewFile()) {
                    throw new MatrixExceptionImpl("Error reading preferences file");
                }
                this.body = new JSONObject();
                this.body.put("compatibilityMode", "STANDALONE");

                this.save();
            }

            this.body = JsonUtils.getFromFile(file);
            if (this.body == null) {
                throw new MatrixExceptionImpl("Illegible preferences file");
            }

            this.compatibilityMode = CompatibilityMode.match(body.getString("compatibilityMode"));

            Matrix.getEventManager().emit(new CompatibilityEvent(this.compatibilityMode));
        } catch (Exception e) {
            logger.except(e);
        }
    }

    public void save() {
        PrintWriter writer;
        try {
            writer = new PrintWriter(this.file);

            writer.write("");
            writer.write(this.body.toString(3));
            writer.close();
        } catch (FileNotFoundException e) {
            logger.except(e, "Error saving internal preferences");
        }
    }

    @Override
    public void close() {
        this.save();
    }

    public Key getKey(String path) {
        return Key.of(body.get(path));
    }

    public void setKey(String path, Key value) {
        body.put(path, value.asString());
    }

    public CompatibilityMode getCompatibilityMode() {
        return compatibilityMode;
    }

}
