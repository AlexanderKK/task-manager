package me.akostadinov.taskmanager.command.taskgroup;

import me.akostadinov.taskmanager.task.service.TaskService;
import me.akostadinov.taskmanager.util.Messenger;
import me.akostadinov.taskmanager.util.StringUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.model.SimpleComponent;

import java.util.List;

import static me.akostadinov.taskmanager.constant.Messages.NO_PERMISSION;

public class TaskListCommand extends SimpleSubCommand {

    protected TaskListCommand(SimpleCommandGroup parent) {
        super(parent, "list");

        setDescription("Lists all running tasks");
        setPermissionMessage(NO_PERMISSION);
    }

    @Override
    protected void onCommand() {
        CommandSender sender = getSender();

        if (TaskService.isEmpty()) {
            Messenger.send(sender, "&6There are no running tasks.");
            return;
        }

        List<SimpleComponent> components = TaskService.listTasks()
                .stream()
                .map(task -> {
                    String taskName = task.getName();
                    String taskKey = StringUtil.transformToLowerCaseNoWhiteSpaces(taskName);

                    SimpleComponent component = SimpleComponent.of("&a\uD83C\uDF0D &e&o" + taskName);
                    component.onClick(ClickEvent.Action.RUN_COMMAND, "/taskclick " + taskKey);
                    component.onHover("&7Period: &6&o" + task.getPeriod(), "&7Check time left");

                    return component;
                })
                .toList();

        Messenger.send(sender, "&8" + Common.chatLineSmooth(), "&6# Running tasks #", " ");

        for (SimpleComponent component : components) {
            component.send(sender);
        }

        Messenger.send(sender, " ");
    }

}
