package me.akostadinov.taskmanager.task.config;

import lombok.*;
import me.akostadinov.taskmanager.task.base.BaseTask;
import me.akostadinov.taskmanager.task.repository.TaskRepository;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.Map;

@NoArgsConstructor
public final class TaskConfig extends YamlConfig {

    @Getter
    private static TaskConfig instance = new TaskConfig();
    private static boolean isLoaded = false;

    @Getter
    @Setter
    private Task task;

    public TaskConfig(BaseTask task) {
        this.task = new Task(task.getName(), task.getPeriod());

        this.setHeader("Running Tasks");
        this.loadConfiguration(NO_DEFAULT, "tasks.yml");
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Task implements ConfigSerializable {

        private String name;
        private String period;

        @Override
        public SerializedMap serialize() {
            SerializedMap taskMap = new SerializedMap();

            taskMap.put("Name", name);
            taskMap.put("Period", period);

            return taskMap;
        }

        public static Task deserialize(SerializedMap taskMap) {
            Task task = new Task();

            task.name = taskMap.getString("Name");
            task.period = taskMap.getString("Period");

            return task;
        }

    }

    @Override
    protected void onLoad() {
        if (isLoaded) {
            return;
        }

        Map<String, Task> tasksFromConfigMap = getMap("Tasks", String.class, Task.class);
        TaskRepository.loadTasks(tasksFromConfigMap);

        isLoaded = true;
    }

    @Override
    protected void onSave() {
        this.set("Tasks", TaskRepository.getTasks());
    }

}
