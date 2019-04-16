package pw.ollie.psbuildcheck;

import pw.ollie.psbuildcheck.check.CheckManager;
import pw.ollie.psbuildcheck.command.BuildcheckCommand;
import pw.ollie.psbuildcheck.command.CheckbuildsCommand;

import org.bukkit.plugin.java.JavaPlugin;

public final class PSBCPlugin extends JavaPlugin {
    private CheckManager checkManager;

    @Override
    public void onEnable() {
        this.checkManager = new CheckManager(this);

        this.getCommand("buildcheck").setExecutor(new BuildcheckCommand(this));
        this.getCommand("checkbuilds").setExecutor(new CheckbuildsCommand(this));
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }
}
