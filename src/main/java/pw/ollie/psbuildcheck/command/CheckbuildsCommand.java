package pw.ollie.psbuildcheck.command;

import pw.ollie.psbuildcheck.PSBCPlugin;
import pw.ollie.psbuildcheck.gui.CheckBuildsGUI;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CheckbuildsCommand implements CommandExecutor {
    private final PSBCPlugin plugin;

    public CheckbuildsCommand(PSBCPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can do that.");
            return true;
        }
        if (!sender.hasPermission("psbc.admin")) {
            sender.sendMessage(ChatColor.RED + "You can't do that.");
            return true;
        }
        new CheckBuildsGUI(plugin).open((Player) sender);
        return true;
    }
}
