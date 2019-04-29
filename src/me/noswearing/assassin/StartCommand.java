package me.noswearing.assassin;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class StartCommand implements CommandExecutor {
    public static HashMap<Player, Player> hitList = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.isOp()) {
            Config.GAME_STARTED = true;
            ArrayList<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
            ArrayList<Player> tempList = new ArrayList<>(Bukkit.getOnlinePlayers());
            if (onlinePlayers.size() > 1) {
                for (Player player : onlinePlayers) {
                    while (true) {
                        int rand = (int) Math.floor(Math.random() * (tempList.size()));
                        if (player != tempList.get(rand)) {
                            hitList.put(player, tempList.get(rand));
                            tempList.remove(rand);
                            break;
                        }
                    }
                }
                for (Player player : hitList.keySet()) {
                    player.setGameMode(GameMode.SURVIVAL);
                    Location location = player.getLocation();
                    location.setX(Math.floor(Math.random() * Config.BORDERMAX - (Config.BORDERMAX / 2.0)));
                    location.setZ(Math.floor(Math.random() * Config.BORDERMAX - (Config.BORDERMAX / 2.0)));
                    location.setY(player.getWorld().getHighestBlockYAt(location));
                    player.teleport(location);
                    ItemStack paper = new ItemStack(Material.PAPER);
                    ItemMeta meta = paper.getItemMeta();
                    meta.setDisplayName(hitList.get(player).getDisplayName());
                    paper.setItemMeta(meta);
                    player.getInventory().clear();
                    player.getInventory().addItem(paper);
                }
                Bukkit.getServer().getWorlds().get(0).getWorldBorder().setCenter(0, 0);
                Bukkit.getServer().getWorlds().get(0).getWorldBorder().setSize(Config.BORDERMAX);
                Bukkit.getServer().getWorlds().get(0).getWorldBorder().setSize(Config.BORDERMIN, Config.TIME);

                Bukkit.getServer().broadcastMessage(ChatColor.RED + "Assassin Game Started!");
            } else {
                commandSender.sendMessage(ChatColor.RED + "Could not start game! Two or more people required!");
            }
        }
        return false;
    }
}
