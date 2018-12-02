package co.m1ke.matrix.terminal;

import co.m1ke.matrix.Matrix;
import co.m1ke.matrix.logging.Logger;
import co.m1ke.matrix.terminal.defaults.AboutCommand;
import co.m1ke.matrix.terminal.defaults.ClearCommand;
import co.m1ke.matrix.terminal.defaults.EchoCommand;
import co.m1ke.matrix.terminal.defaults.HelpCommand;
import co.m1ke.matrix.terminal.defaults.PluginsCommand;
import co.m1ke.matrix.terminal.defaults.StopCommand;
import co.m1ke.matrix.terminal.defaults.UptimeCommand;
import co.m1ke.matrix.terminal.defaults.VersionCommand;
import co.m1ke.matrix.util.Lang;
import co.m1ke.matrix.util.Timings;

import java.util.HashMap;
import java.util.Scanner;

public class Terminal {

    private HashMap<String, TerminalCommand> registeredCommands;

    private Thread terminalThread;
    private long startup;

    private Logger logger;

    public Terminal(TerminalCommand... commands) {
        Timings timings = new Timings("Terminal", "Initialization");

        this.registeredCommands = new HashMap<>();
        this.startup = System.currentTimeMillis();
        this.logger = new Logger("Terminal");

        register(
                new AboutCommand(),
                new ClearCommand(),
                new EchoCommand(),
                new HelpCommand(this),
                new PluginsCommand(Matrix.getPluginManager()),
                new StopCommand(),
                new UptimeCommand(this),
                new VersionCommand()
        );
        register(commands);

        Scanner scan = new Scanner(System.in);
        terminalThread = new Thread(() -> {
            while (true) {
                String cmd = scan.nextLine();
                String[] args = new String[]{};

                if (cmd.contains(" ")) {
                    args = cmd.substring(cmd.indexOf(' ') + 1).split(" ");
                    cmd = cmd.split(" ")[0];
                }

                TerminalCommand command = registeredCommands.get(cmd.toLowerCase());

                if (command != null) {
                    command.setUsedAlias(cmd.toLowerCase());
                    command.execute(args);
                } else {
                    logger.info("Unknown command, check for typos?");
                }
            }
        }, "Terminal Service");
        timings.complete(() -> terminalThread.start(), "Terminal activated in %c%tms" + Lang.RESET + ".");
    }

    public void register(TerminalCommand command) {
        for (String a : command.getNames()) {
            registeredCommands.put(a, command);
        }
    }

    public void register(TerminalCommand... commands) {
        for (TerminalCommand cmd : commands) {
            register(cmd);
        }
    }

    public HashMap<String, TerminalCommand> getCommands() {
        return registeredCommands;
    }

    public long getStartup() {
        return startup;
    }

}
