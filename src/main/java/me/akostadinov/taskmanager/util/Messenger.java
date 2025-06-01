package me.akostadinov.taskmanager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.remain.Remain;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Messenger {

    public static void send(CommandSender sender, String... messages) {
        if (sender instanceof Player) {
            Common.tellNoPrefix(sender, messages);
        } else if (sender instanceof ConsoleCommandSender) {
            Common.log(messages);
        } else {
            sender.sendMessage(colorize(messages));
        }
    }

    public static void broadcast(String... messages) {
        Common.log(messages);

        for (Player player : Remain.getOnlinePlayers()) {
            Common.tellNoPrefix(player, messages);
        }
    }

    public static String[] colorize(String... messages) {
        List<String> colorizedMessages = new ArrayList<>();

        for (String message : messages) {
            colorizedMessages.add(ChatColor.translateAlternateColorCodes('&', message));
        }

        return colorizedMessages.toArray(String[]::new);
    }

}
