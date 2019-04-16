package pw.ollie.psbuildcheck.check;

import pw.ollie.psbuildcheck.PSBCPlugin;

import org.bukkit.scheduler.BukkitRunnable;

public final class CheckDataSaveTask extends BukkitRunnable {
    private final PSBCPlugin plugin;

    public CheckDataSaveTask(PSBCPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getCheckManager().saveData();
    }
}
