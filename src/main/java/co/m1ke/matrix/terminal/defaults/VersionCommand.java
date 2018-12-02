package co.m1ke.matrix.terminal.defaults;

import co.m1ke.matrix.terminal.TerminalCommand;
import co.m1ke.matrix.util.Defaults;
import co.m1ke.matrix.util.Lang;

public class VersionCommand extends TerminalCommand {

    public VersionCommand() {
        super(new String[] { "ver", "version" }, Lang.usage("Service", "ver"), "Service");
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            log(getHelp());
            return;
        }
        log("This service is running Matrix version " + Defaults.VERSION + " by Argon Development.");
    }

}
