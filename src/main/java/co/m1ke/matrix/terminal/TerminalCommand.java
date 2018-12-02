package co.m1ke.matrix.terminal;

import co.m1ke.matrix.logging.Logger;

public abstract class TerminalCommand {

    private Logger logger;

    private String[] names;
    private String usedAlias;
    private String help;
    private String head;

    public TerminalCommand(String[] names, String help, String head) {
        this.names = names;
        this.help = help;
        this.head = head;

        this.logger = new Logger(this.head);
    }

    public abstract void execute(String[] args);

    public void log(String msg) {
        logger.info(msg);
    }

    public void raw(String msg) {
        logger.raw(msg);
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String getUsedAlias() {
        return usedAlias;
    }

    public void setUsedAlias(String usedAlias) {
        this.usedAlias = usedAlias;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

}
