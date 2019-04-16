package pw.ollie.psbuildcheck.gui;

import pw.ollie.psbuildcheck.PSBCPlugin;

public final class GUIManager {
    private final PSBCPlugin plugin;
    private final CheckBuildsGUI checkBuildsGUI;

    public GUIManager(PSBCPlugin plugin) {
        this.plugin = plugin;
        this.checkBuildsGUI = new CheckBuildsGUI(plugin);
    }

    public PSBCPlugin getPlugin() {
        return plugin;
    }

    public CheckBuildsGUI getCheckBuildsGUI() {
        return checkBuildsGUI;
    }
}
