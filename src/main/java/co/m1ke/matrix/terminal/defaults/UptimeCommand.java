package co.m1ke.matrix.terminal.defaults;

import co.m1ke.matrix.terminal.Terminal;
import co.m1ke.matrix.terminal.TerminalCommand;
import co.m1ke.matrix.util.Lang;
import co.m1ke.matrix.util.TimeUtil;

public class UptimeCommand extends TerminalCommand {

    private Terminal terminal;

    public UptimeCommand(Terminal terminal) {
        super(new String[] { "uptime" }, Lang.usage("Service", "uptime"), "Service");
        this.terminal = terminal;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            log(getHead());
            return;
        }
        long uptime = (System.currentTimeMillis() - terminal.getStartup());
        log("Matrix has been running for " + Lang.GREEN + TimeUtil.getShortenedTimeValue(uptime) + Lang.RESET + ".");
    }

}
