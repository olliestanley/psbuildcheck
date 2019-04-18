package pw.ollie.psbuildcheck.command;

import pw.ollie.psbuildcheck.PSBCPlugin;
import pw.ollie.psbuildcheck.check.BuildCheck;

import com.github.intellectualsites.plotsquared.plot.PlotSquared;
import com.github.intellectualsites.plotsquared.plot.object.Plot;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public final class BuildcheckCommand implements CommandExecutor {
    private final PSBCPlugin plugin;

    public BuildcheckCommand(PSBCPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can do that.");
            return true;
        }
        if (!sender.hasPermission("psbc.request")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
            return true;
        }
        Player player = (Player) sender;
        Location location = player.getLocation();
        Set<Plot> plots = PlotSquared.get().getBasePlots();
        Plot relevantPlot = null;
        for (Plot plot : plots) {
            if (plot.getArea().contains(location.getBlockX(), location.getBlockZ())) {
                relevantPlot = plot;
                break;
            }
        }
        BuildCheck buildCheck = new BuildCheck(location.getWorld().getName(), location.getBlockX(), location.getBlockY(),
                location.getBlockZ(), relevantPlot == null ? null : relevantPlot.getAlias(), player.getName());
        plugin.getCheckManager().addCheck(buildCheck);
        sender.sendMessage(ChatColor.GRAY + "Successfully added the build to the check queue.");
        return true;
    }
}
