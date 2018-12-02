package co.m1ke.matrix.terminal.defaults;

import co.m1ke.matrix.terminal.TerminalCommand;
import co.m1ke.matrix.util.Lang;

public class AboutCommand extends TerminalCommand {

    public AboutCommand() {
        super(new String[] { "about" }, Lang.usage("Matrix", "about"), "Matrix");
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            log(getHelp());
            return;
        }
        log("Matrix is a proprietary plugin platform made by Argon Development.");
    }

}
