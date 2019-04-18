package pw.ollie.psbuildcheck.gui;

import pw.ollie.psbuildcheck.PSBCPlugin;

public final class GUIManager {
    private final PSBCPlugin plugin;

    public GUIManager(PSBCPlugin plugin) {
        this.plugin = plugin;
    }

    public PSBCPlugin getPlugin() {
        return plugin;
    }
}
