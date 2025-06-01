package me.akostadinov.taskmanager;

import lombok.Getter;
import me.akostadinov.taskmanager.task.config.TaskConfig;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.akostadinov.taskmanager.constant.Messages.PREFIX;
import static org.mineacademy.fo.settings.FileConfig.NO_DEFAULT;

public final class TaskManager extends SimplePlugin {

    @Getter
    public static List<String> taskPeriods = new ArrayList<>();

    public static TaskManager getInstance() {
        return (TaskManager) SimplePlugin.getInstance();
    }

    @Override
    protected void onPluginStart() {
        Common.setLogPrefix(PREFIX);
        Common.setTellPrefix(null);

        taskPeriods.addAll(Arrays.asList("second", "seconds", "minute", "minutes", "hour", "hours"));

        TaskConfig.getInstance().loadConfiguration(NO_DEFAULT, "tasks.yml");
    }

}
