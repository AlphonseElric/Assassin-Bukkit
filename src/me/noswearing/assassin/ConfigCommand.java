package me.noswearing.assassin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.isOp() && strings.length == 2) {
            switch (strings[0].toLowerCase()) {
                case "bordermax":
                    try {
                        Config.BORDERMAX = Integer.parseInt(strings[1]);
                        commandSender.sendMessage(ChatColor.AQUA + "Max world border set to " + strings[1]);
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(ChatColor.RED + "Argument 3 must be a number!");
                    }
                    break;
                case "bordermin":
                    try {
                        Config.BORDERMIN = Integer.parseInt(strings[1]);
                        commandSender.sendMessage(ChatColor.AQUA + "Min world border set to " + strings[1]);
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(ChatColor.RED + "Argument 3 must be a number!");
                    }
                    break;
                case "time":
                    try {
                        Config.TIME = Integer.parseInt(strings[1]);
                        commandSender.sendMessage(ChatColor.AQUA + "Time set to " + strings[1]);
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(ChatColor.RED + "Argument 3 must be a number!");
                    }
                    break;
                default:
                    commandSender.sendMessage(ChatColor.RED + "/aconfig [bordermax/bordermin/time] [value]");
                    break;
            }
        }
        return false;
    }
}
