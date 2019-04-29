package me.noswearing.assassin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KillHandler implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (Config.GAME_STARTED) {
            Player player = event.getEntity();
            if (event.getEntity().getKiller() != null) {
                Player killer = event.getEntity().getKiller();
                if (StartCommand.hitList.size() == 2) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendTitle(ChatColor.GOLD + killer.getName() + ChatColor.RED + " has won the game!", "", 25, 125, 25);
                        Bukkit.getWorlds().get(0).getWorldBorder().reset();
                        Config.GAME_STARTED = false;
                        return;
                    }
                }
                if (StartCommand.hitList.get(killer) == player) {
                    ItemStack paper = new ItemStack(Material.PAPER), nextPaper = new ItemStack(Material.PAPER);
                    ItemMeta meta = paper.getItemMeta(), nextMeta = nextPaper.getItemMeta();
                    meta.setDisplayName(player.getName());
                    paper.setItemMeta(meta);
                    nextMeta.setDisplayName(StartCommand.hitList.get(player).getName());
                    nextPaper.setItemMeta(nextMeta);
                    killer.getInventory().setItem(killer.getInventory().first(paper), nextPaper);
                    StartCommand.hitList.replace(killer, StartCommand.hitList.get(player));
                    StartCommand.hitList.remove(player);
                    killer.playSound(killer.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 3.0f, 0.5f);
                    killer.sendTitle(ChatColor.GOLD + StartCommand.hitList.get(killer).getName() + ChatColor.RED + " is your new hit!", "", 50, 150, 50);
                } else if (StartCommand.hitList.get(player) == killer) {
                    ItemStack paper = new ItemStack(Material.PAPER), nextPaper = new ItemStack(Material.PAPER);
                    ItemMeta meta = paper.getItemMeta(), nextMeta = nextPaper.getItemMeta();
                    meta.setDisplayName(player.getName());
                    paper.setItemMeta(meta);
                    nextMeta.setDisplayName(killer.getName());
                    nextPaper.setItemMeta(nextMeta);
                    for (Player key : StartCommand.hitList.keySet()) {
                        if (StartCommand.hitList.get(key) == player) {
                            key.getInventory().setItem(key.getInventory().first(paper), nextPaper);
                            StartCommand.hitList.replace(key, killer);
                            StartCommand.hitList.remove(player);
                            key.playSound(key.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 3.0f, 0.5f);
                            key.sendTitle(ChatColor.GOLD + StartCommand.hitList.get(killer).getName() + ChatColor.RED + " is your new hit!", "", 50, 150, 50);
                            break;
                        }
                    }
                } else {
                    killer.setHealth(0.0);
                    killer.kickPlayer("Do not randomly kill!");
                }
            } else {
                player.spigot().respawn();
                Location location = player.getLocation();
                location.setX(Math.floor(Math.random() * Config.BORDERMAX - (Config.BORDERMAX / 2.0)));
                location.setZ(Math.floor(Math.random() * Config.BORDERMAX - (Config.BORDERMAX / 2.0)));
                location.setY(player.getWorld().getHighestBlockYAt(location));
                player.teleport(location);
                ItemStack paper = new ItemStack(Material.PAPER);
                ItemMeta meta = paper.getItemMeta();
                meta.setDisplayName(StartCommand.hitList.get(player).getName());
                paper.setItemMeta(meta);
                player.getInventory().addItem(paper);
            }
        }
    }
}
