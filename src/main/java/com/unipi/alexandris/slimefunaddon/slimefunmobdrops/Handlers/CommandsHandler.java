package com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Handlers;

import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Commands.HelpCmd;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Commands.ReloadCmd;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.Commands.SubCommand;
import com.unipi.alexandris.slimefunaddon.slimefunmobdrops.SlimefunMobDrops;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class CommandsHandler implements TabExecutor {

    private final HashMap<String, SubCommand> commands = new HashMap<>();

    public CommandsHandler(SlimefunMobDrops plugin) {
        commands.put("help", new HelpCmd(this));
        commands.put("reload", new ReloadCmd(plugin));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.AQUA + "Slimefun Mob Drops Addon" + ChatColor.GRAY + " is a plugin addon for slimefun designed by Antitonius" +
                    " for custom Mob Drop Tables in theNRK Minecraft server. To check the help page, type "
                    + ChatColor.YELLOW + "/sfmobdrops help" + ChatColor.GRAY + ".");
            return true;
        }

        SubCommand command = commands.get(args[0].toLowerCase());

        if (command == null) {
            sender.sendMessage(ChatColor.RED + "Unknown command. To check out the help page, type " + ChatColor.GRAY + "/sfmobdrops help" + ChatColor.RED + ".");
            return true;
        }

        if (command.inGameOnly() && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot run this command.");
            return true;
        }

        String[] subCmdArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subCmdArgs, 0, subCmdArgs.length);

        if (!command.onCommand(sender, subCmdArgs)) {
            sender.sendMessage(ChatColor.RED + "Command usage: /sfmobdrops " + ChatColor.GRAY + command.getUsage() + ChatColor.RED + ".");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        //create new array
        final List<String> completions = new ArrayList<>();
        //copy matches of first argument from list (ex: if first arg is 'm' will return just 'minecraft')
        StringUtil.copyPartialMatches(args[0], commands.keySet().stream().toList(), completions);
        //sort the list
        Collections.sort(completions);
        return completions;
    }

    public Collection<SubCommand> getCommands() {
        return commands.values();
    }
}
