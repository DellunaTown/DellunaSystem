package me.lewin.dellunasystem.commads;

import me.lewin.dellunasystem.gui.PartyMainGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if (!(sender instanceof Player)) return true;

        ((Player) sender).openInventory(PartyMainGui.get());

        return true;
    }
}
