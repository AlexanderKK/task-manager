package me.akostadinov.taskmanager.task.repository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.akostadinov.taskmanager.task.base.BaseTask;
import me.akostadinov.taskmanager.task.base.TaskFactory;
import me.akostadinov.taskmanager.task.config.TaskConfig;
import me.akostadinov.taskmanager.task.service.TaskService;
import me.akostadinov.taskmanager.util.StringUtil;
import org.mineacademy.fo.Common;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mineacademy.fo.settings.YamlStaticConfig.NO_DEFAULT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaskRepository {

    @Getter
    private static final Map<String, TaskConfig> tasksConfigMap = new LinkedHashMap<>();

    public static void saveTask(BaseTask task) {
        TaskConfig taskConfig = new TaskConfig(task);
        String taskConfigKey = StringUtil.removeWhiteSpaces(task.getName());

        tasksConfigMap.put(taskConfigKey, taskConfig);
        taskConfig.save();
    }

    public static void removeTask(String taskName) {
        TaskConfig.getInstance().loadConfiguration(NO_DEFAULT, "tasks.yml");
        String taskConfigKey = StringUtil.removeWhiteSpaces(taskName);

        tasksConfigMap.remove(taskConfigKey);
        TaskConfig.getInstance().save();
    }

    public static void mapToTasksConfigMap(Map<String, TaskConfig.Task> tasksMap) {
        tasksMap.forEach((taskName, task) -> {
            TaskConfig taskConfig = new TaskConfig();
            taskConfig.setTask(task);

            tasksConfigMap.put(taskName, taskConfig);
        });
    }

    public static Map<String, TaskConfig.Task> getTasks() {
        Map<String, TaskConfig.Task> tasksMap = new LinkedHashMap<>();
        tasksConfigMap.forEach((taskName, taskConfig) -> tasksMap.put(taskName, taskConfig.getTask()));

        return tasksMap;
    }

    public static void loadTasks(Map<String, TaskConfig.Task> tasksFromConfigMap) {
        TaskRepository.mapToTasksConfigMap(tasksFromConfigMap);
        Map<String, BaseTask> tasksMap = TaskService.getTasksMap();

        for (Map.Entry<String, TaskConfig> taskEntry : tasksConfigMap.entrySet()) {
            String taskName = taskEntry.getKey().toLowerCase();
            String taskPeriod = taskEntry.getValue().getTask().getPeriod();

            BaseTask task;
            try {
                task = TaskFactory.createTask(taskName, taskPeriod);
            } catch (IllegalStateException ex) {
                Common.log(ex.getMessage());
                return;
            }

            tasksMap.put(taskName, task);
            task.start();
        }
    }

}
