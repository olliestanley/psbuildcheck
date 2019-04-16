package pw.ollie.psbuildcheck.gui;

import pw.ollie.psbuildcheck.PSBCPlugin;
import pw.ollie.psbuildcheck.check.BuildCheck;
import pw.ollie.psbuildcheck.check.CheckManager;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CheckBuildsGUI extends InventoryGUI {
    private static final int SIZE = 54;
    private static final int EXIT_SLOT = SIZE - 1;

    private final PSBCPlugin plugin;

    public CheckBuildsGUI(PSBCPlugin plugin) {
        super(plugin, "Check Builds", SIZE, event -> {
            int slot = event.getSlot();
            if (slot == EXIT_SLOT) {
                event.setClose(true);
                return;
            }

            CheckManager checkManager = plugin.getCheckManager();
            List<BuildCheck> queuedChecks = checkManager.getQueuedChecks();
            if (queuedChecks.size() <= slot) {
                // this is an empty slot, do nothing
                return;
            }

            BuildCheck check = queuedChecks.get(slot);
            checkManager.removeCheck(check);
            ((CheckBuildsGUI) event.getGUI()).update();
            event.setClose(true);
            event.getPlayer().teleport(check.getLocation());
        });

        this.plugin = plugin;

        this.update();
    }

    public void update() {
        CheckManager checkManager = plugin.getCheckManager();
        List<BuildCheck> checks = checkManager.getQueuedChecks();
        int loopEnd = checks.size() > EXIT_SLOT ? EXIT_SLOT : checks.size();

        for (int i = 0; i < loopEnd; i++) {
            BuildCheck check = checks.get(i);
            if (check == null) {
                continue; // shouldn't ever happen but just in case
            }

            if (check.getPlotName() != null) {
                this.setSlot(i, new ItemStack(Material.SIGN), check.getX() + ", " + check.getY() + ", " + check.getZ(),
                        "Plot: " + check.getPlotName(), "World: " + check.getWorldName(), "Submitter: " + check.getSubmitter());
            } else {
                this.setSlot(i, new ItemStack(Material.SIGN), check.getX() + ", " + check.getY() + ", " + check.getZ(),
                        "World: " + check.getWorldName(), "Submitter: " + check.getSubmitter());
            }
        }

        this.setSlot(EXIT_SLOT, new ItemStack(Material.RED_WOOL), "Exit");
    }
}
