package me.akostadinov.taskmanager.command.taskgroup;

import me.akostadinov.taskmanager.task.base.BaseTask;
import me.akostadinov.taskmanager.task.service.TaskService;
import me.akostadinov.taskmanager.util.Messenger;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

import java.util.List;

import static me.akostadinov.taskmanager.constant.Messages.NO_PERMISSION;

public class TaskRemoveCommand extends SimpleSubCommand {

    protected TaskRemoveCommand(SimpleCommandGroup parent) {
        super(parent, "remove");

        setDescription("Removes a task");
        setUsage("<task>");
        setPermissionMessage(NO_PERMISSION);
    }

    @Override
    protected void onCommand() {
        if (args.length != 1) {
            return;
        }

        String taskName = args[0];

        BaseTask taskRemoved = TaskService.remove(taskName);
        if (taskRemoved == null) {
            return;
        }

        Messenger.send(sender, String.format("&aTask &6&o%s&a has been removed.", taskRemoved.getName()));
    }

    @Override
    protected List<String> tabComplete() {
        return switch (args.length) {
            case 1 -> completeLastWord(TaskService.listKeys());

            default -> NO_COMPLETE;
        };
    }

}
