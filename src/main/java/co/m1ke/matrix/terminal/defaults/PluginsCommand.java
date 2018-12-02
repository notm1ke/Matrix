package co.m1ke.matrix.terminal.defaults;

import co.m1ke.matrix.plugin.Plugin;
import co.m1ke.matrix.plugin.PluginManager;
import co.m1ke.matrix.terminal.TerminalCommand;
import co.m1ke.matrix.util.Lang;

public class PluginsCommand extends TerminalCommand {

    private PluginManager manager;

    public PluginsCommand(PluginManager manager) {
        super(new String[] { "plugins", "pl" }, Lang.usage("Plugins", "pl"), "Plugins");
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            log(getHelp());
            return;
        }

        if (manager.getPlugins().isEmpty()) {
            log("There are no plugins loaded at this time.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Plugin plugin : manager.getPlugins()) {
            sb.append(plugin.getName() + ", ");
        }

        log("Plugins (" + manager.getPlugins().size() + "): " + Lang.stripLastChar(sb.toString().trim()));

    }

}
