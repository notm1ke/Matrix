package co.m1ke.matrix.terminal.defaults;

import co.m1ke.matrix.terminal.TerminalCommand;
import co.m1ke.matrix.util.Lang;

public class EchoCommand extends TerminalCommand {

    public EchoCommand() {
        super(new String[] { "echo" }, Lang.usage("Matrix", "echo <msg>"), "Matrix");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            log(getHelp());
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s + " ");
        }

        log(sb.toString().trim());
    }

}
