package pw.ollie.psbuildcheck.command;

import pw.ollie.psbuildcheck.PSBCPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class CheckbuildsCommand implements CommandExecutor {
    private final PSBCPlugin plugin;

    public CheckbuildsCommand(PSBCPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return true;
    }
}
