package me.akostadinov.taskmanager.command;

import me.akostadinov.taskmanager.task.base.BaseTask;
import me.akostadinov.taskmanager.task.service.TaskService;
import me.akostadinov.taskmanager.util.TimeFormatter;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.TimeUtil;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

import static me.akostadinov.taskmanager.constant.Messages.NO_PERMISSION;

@AutoRegister
public final class TaskClickCommand extends SimpleCommand {

    public TaskClickCommand() {
        super("taskclick");

        setPermissionMessage(NO_PERMISSION);
    }

    @Override
    public void onCommand() {
        checkConsole();

        if (args.length != 1) {
            return;
        }

        BaseTask task = TaskService.find(args[0]);
        if (task == null) {
            return;
        }

        long periodInSeconds = TimeUtil.toTicks(task.getPeriod()) / 20;
        long timePassedInSeconds = TimeUtil.currentTimeSeconds() - task.getTimeOfRegistration();

        long secondsLeft = periodInSeconds - timePassedInSeconds;
        if (secondsLeft <= 0) {
            secondsLeft = 0;
        }

        String timeLeft = TimeFormatter.formatSecondsToDayTime(secondsLeft);

        String message = String.format("&6%s&7 completes in &6%s&7.", task.getName(), timeLeft);
        Common.tellNoPrefix(getPlayer(), message);
    }

}
