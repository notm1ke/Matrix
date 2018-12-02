package co.m1ke.matrix.terminal.defaults;

import co.m1ke.matrix.terminal.Terminal;
import co.m1ke.matrix.terminal.TerminalCommand;
import co.m1ke.matrix.util.Lang;

public class HelpCommand extends TerminalCommand {

    private Terminal terminal;

    public HelpCommand(Terminal terminal) {
        super(new String[] { "help", "?" }, Lang.usage("Help", "help"), "Help");
        this.terminal = terminal;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            log(getHelp());
            return;
        }
        log("There are " + terminal.getCommands().size() + " commands loaded.");
        terminal.getCommands().forEach((s, terminalCommand) -> log("  `" + s + "`"));
    }

}
