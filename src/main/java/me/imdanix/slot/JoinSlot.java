package me.imdanix.slot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class JoinSlot extends JavaPlugin implements Listener {
    private int slot;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        updateSlot(getConfig().getInt("default-slot", 1) - 1);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("joinslot.set")) {
            if(args.length < 1) {
                reloadConfig();
                updateSlot(getConfig().getInt("default-slot", 1) - 1);
                sender.sendMessage(ChatColor.GREEN + "Default slot is now on " + (slot + 1));
            } else {
                try {
                    updateSlot(Integer.valueOf(args[0]));
                    getConfig().set("default-slot", (slot + 1));
                    saveConfig();
                    sender.sendMessage(ChatColor.GREEN + "Default slot is now on " + (slot + 1) + "!");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Are you sure that \"" + args[0] + "\" is a number?");
                }
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().setHeldItemSlot(slot);
    }

    private void updateSlot(int newSlot) {
        slot = Math.max(0, Math.min(8, newSlot));
    }
}
