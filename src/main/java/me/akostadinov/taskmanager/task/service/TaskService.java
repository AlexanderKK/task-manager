package me.akostadinov.taskmanager.task.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.akostadinov.taskmanager.task.base.BaseTask;
import me.akostadinov.taskmanager.task.repository.TaskRepository;
import me.akostadinov.taskmanager.util.StringUtil;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaskService {

    @Getter
    private static final Map<String, BaseTask> tasksMap = new HashMap<>();

    public static boolean register(BaseTask task) {
        String taskKey = StringUtil.transformToLowerCaseNoWhiteSpaces(task.getName());
        boolean isReset = false;

        if (tasksMap.containsKey(taskKey)) {
            remove(taskKey);
            isReset = true;
        }

        tasksMap.put(taskKey, task);
        task.start();
        TaskRepository.saveTask(task);

        return isReset;
    }

    public static BaseTask remove(String taskName) {
        BaseTask removedTask = tasksMap.remove(taskName);
        if (removedTask == null) {
            return null;
        }

        Bukkit.getScheduler().cancelTask(removedTask.getTaskId());
        TaskRepository.removeTask(removedTask.getName());

        return removedTask;
    }

    public static BaseTask find(String taskName) {
        return tasksMap.get(taskName);
    }

    public static List<String> listKeys() {
        return tasksMap.keySet().stream().toList();
    }

    public static List<BaseTask> listTasks() {
        return tasksMap.values().stream().toList();
    }

    public static boolean isEmpty() {
        return tasksMap.isEmpty();
    }

}
