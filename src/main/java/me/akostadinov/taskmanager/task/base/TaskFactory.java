package me.akostadinov.taskmanager.task.base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.akostadinov.taskmanager.task.DroppedItemsTask;
import me.akostadinov.taskmanager.task.RestartTask;

import static me.akostadinov.taskmanager.constant.Messages.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaskFactory {

    public static BaseTask createTask(String name, String period) {
        switch (name) {
            case "restart":
                return new RestartTask(
                        period,
                        String.format(SERVER_RESTARTED_IN, period),
                        SERVER_RESTARTED_IN,
                        ABRACADABRA
                );
            case "droppeditems":
                return new DroppedItemsTask(
                        period,
                        String.format(DROPPED_ITEMS_REMOVED_IN, period),
                        DROPPED_ITEMS_REMOVED_IN,
                        DROPPED_ITEMS_REMOVED
                );
            default:
                throw new IllegalArgumentException(String.format(TASK_NOT_FOUND, name));
        }
    }

}
