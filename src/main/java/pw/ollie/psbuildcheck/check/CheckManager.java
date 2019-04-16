package pw.ollie.psbuildcheck.check;

import pw.ollie.psbuildcheck.PSBCPlugin;

import java.util.ArrayList;
import java.util.List;

public final class CheckManager {
    private final PSBCPlugin plugin;
    private final List<BuildCheck> queuedChecks;

    public CheckManager(PSBCPlugin plugin) {
        this.plugin = plugin;
        this.queuedChecks = new ArrayList<>();
    }

    public PSBCPlugin getPlugin() {
        return plugin;
    }

    public List<BuildCheck> getQueuedChecks() {
        return new ArrayList<>(queuedChecks);
    }

    public void addCheck(BuildCheck check) {
        queuedChecks.add(check);
    }

    public void removeCheck(BuildCheck check) {
        queuedChecks.remove(check);
    }

    public void loadData() {
        // todo
    }

    public void saveData() {
        // todo
    }
}
