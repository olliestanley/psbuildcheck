package pw.ollie.psbuildcheck.command;

import pw.ollie.psbuildcheck.PSBCPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class BuildcheckCommand implements CommandExecutor {
    private final PSBCPlugin plugin;

    public BuildcheckCommand(PSBCPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // todo
        return true;
    }
}
