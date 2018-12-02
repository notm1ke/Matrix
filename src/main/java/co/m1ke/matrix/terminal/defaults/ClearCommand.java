package co.m1ke.matrix.terminal.defaults;

import co.m1ke.matrix.terminal.TerminalCommand;
import co.m1ke.matrix.util.Lang;

public class ClearCommand extends TerminalCommand {

    public ClearCommand() {
        super(new String[] { "clear", "clearlog" }, Lang.usage("Matrix", "clear"), "Matrix");
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            log(getHelp());
            return;
        }
        for (int i = 0; i < 200; i++) {
            raw(" ");
        }
        log("Terminal log cleared.");
    }

}
