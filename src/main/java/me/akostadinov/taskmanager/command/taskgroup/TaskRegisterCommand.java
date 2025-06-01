package me.akostadinov.taskmanager.command.taskgroup;

import me.akostadinov.taskmanager.task.base.BaseTask;
import me.akostadinov.taskmanager.task.base.TaskFactory;
import me.akostadinov.taskmanager.task.service.TaskService;
import me.akostadinov.taskmanager.util.Messenger;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

import java.util.Arrays;
import java.util.List;

import static me.akostadinov.taskmanager.TaskManager.taskPeriods;
import static me.akostadinov.taskmanager.constant.Messages.*;

public class TaskRegisterCommand extends SimpleSubCommand {

    private static final long MIN_SECONDS = 60;
    private static final long MAX_SECONDS = 59;
    private static final long MIN_MINUTES = 1;
    private static final long MAX_MINUTES = 59;
    private static boolean isTimePeriodIncorrect;

    protected TaskRegisterCommand(SimpleCommandGroup parent) {
        super(parent, "create");

        setDescription("Creates a task");
        setUsage("<task> <time> <period>");
        setPermissionMessage(NO_PERMISSION);
    }

    @Override
    protected void onCommand() {
        if (args.length < 3 || args.length > 7 || args.length % 2 == 0) {
            Messenger.send(sender, "&6Please enter a period &7&oe.g. 30 minutes&r&6.");
            return;
        }

        isTimePeriodIncorrect = false;
        String taskTimePeriod = generateTimePeriod(1);

        if (taskTimePeriod == null || taskTimePeriod.trim().isEmpty() || isTimePeriodIncorrect) {
            return;
        }

        String taskName = args[0];
        taskTimePeriod = taskTimePeriod.trim();

        BaseTask taskCreated;
        try {
            taskCreated = TaskFactory.createTask(taskName, taskTimePeriod);
        } catch (IllegalArgumentException ex) {
            Messenger.send(sender, "&c" + ex.getMessage());
            return;
        }

        boolean isTaskReset = TaskService.register(taskCreated);
        if (isTaskReset) {
            Messenger.send(sender,
                    String.format("&aTask &6&o%s&a has been reset to &6%s.", taskCreated.getName(), taskCreated.getPeriod()));
        } else {
            Messenger.send(sender, String.format("&aTask &6&o%s&a has been created.", taskCreated.getName()));
        }
    }

    private String generateTimePeriod(int index) {
        if (index == 7 || index >= args.length) {
            return "";
        }

        String taskTimeText = args[index];
        String taskPeriod = args[index + 1].toLowerCase();

        if (!taskPeriods.contains(taskPeriod)) {
            Messenger.send(sender, String.format(SHOULD_BE, PERIOD, "&cseconds&6, &cminutes&6 or &chours"));
            return null;
        }

        String taskPeriodPluralCapitalized = taskPeriod.endsWith("s") ? taskPeriod : taskPeriod + "s";
        taskPeriodPluralCapitalized = Character.toUpperCase(taskPeriodPluralCapitalized.charAt(0))
                + taskPeriodPluralCapitalized.substring(1).toLowerCase();

        int taskTime;
        try {
            taskTime = Integer.parseInt(taskTimeText);
        } catch (Throwable ex) {
            Common.log(ex.getMessage());
            Messenger.send(sender, String.format(SHOULD_BE_NUMBER, taskPeriodPluralCapitalized, "whole"));

            return null;
        }

        if (taskTime <= 0) {
            Messenger.send(sender, String.format(SHOULD_BE_NUMBER, taskPeriodPluralCapitalized, "positive"));
            return null;
        }

        if (taskPeriod.contains("second")) {
            if (taskTime < MIN_SECONDS && index == 1) {
                Messenger.send(sender, String.format(SHOULD_BE_AT_LEAST, taskPeriodPluralCapitalized, MIN_SECONDS));
                return null;
            }

            if (taskTime > MAX_SECONDS && index != 1) {
                Messenger.send(sender, String.format(SHOULD_NOT_EXCEED, taskPeriodPluralCapitalized, MAX_SECONDS));
                return null;
            }
        }

        if (taskPeriod.contains("minute") && taskTime > MAX_MINUTES && index != 1) {
            Messenger.send(sender, String.format(SHOULD_BE_IN_THE_RANGE, taskPeriodPluralCapitalized, MIN_MINUTES, MAX_MINUTES));
            return null;
        }

        String lastTimePeriod = generateTimePeriod(index + 2);
        if (lastTimePeriod == null) {
            isTimePeriodIncorrect = true;
        }

        if (taskTime == 1 && taskPeriod.endsWith("s")) {
            taskPeriod = taskPeriod.substring(0, taskPeriod.length() - 1);
        } else if (taskTime > 1 && !taskPeriod.endsWith("s")) {
            taskPeriod += "s";
        }

        return taskTimeText + " " + taskPeriod + " " + lastTimePeriod;
    }

    @Override
    protected List<String> tabComplete() {
        return switch (args.length) {
            case 1 -> completeLastWord("droppeditems", "restart");
            case 2 -> completeLastWord(Arrays.asList("1 minute", "30 minutes", "5 hours"));

            default -> NO_COMPLETE;
        };
    }

}
