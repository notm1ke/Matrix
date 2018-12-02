package co.m1ke.matrix.terminal.defaults;

import co.m1ke.matrix.terminal.TerminalCommand;
import co.m1ke.matrix.util.Lang;

public class StopCommand extends TerminalCommand {

    public StopCommand() {
        super(new String[] { "stop" }, Lang.usage("Matrix", "stop"), "Matrix");
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            log(getHelp());
            return;
        }
        log("Matrix service stopped.");
        System.exit(-1);
    }

}
