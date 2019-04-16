package pw.ollie.psbuildcheck;

import pw.ollie.psbuildcheck.check.CheckDataSaveTask;
import pw.ollie.psbuildcheck.check.CheckManager;
import pw.ollie.psbuildcheck.command.BuildcheckCommand;
import pw.ollie.psbuildcheck.command.CheckbuildsCommand;
import pw.ollie.psbuildcheck.gui.GUIManager;

import org.bukkit.plugin.java.JavaPlugin;

public final class PSBCPlugin extends JavaPlugin {
    private CheckManager checkManager;
    private GUIManager guiManager;

    private CheckDataSaveTask saveTask;

    @Override
    public void onEnable() {
        this.checkManager = new CheckManager(this);
        this.checkManager.loadData();

        this.guiManager = new GUIManager(this);

        this.saveTask = new CheckDataSaveTask(this);
        this.saveTask.runTaskTimerAsynchronously(this, 20L * 60 * 5, 20L * 60 * 5);

        this.getCommand("buildcheck").setExecutor(new BuildcheckCommand(this));
        this.getCommand("checkbuilds").setExecutor(new CheckbuildsCommand(this));
    }

    @Override
    public void onDisable() {
        this.saveTask.cancel();
        this.checkManager.saveData();
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }

    public GUIManager getGUIManager() {
        return this.guiManager;
    }
}
