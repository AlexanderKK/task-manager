package me.akostadinov.taskmanager.task;

import me.akostadinov.taskmanager.task.base.BaseTask;
import org.bukkit.Bukkit;

public class RestartTask extends BaseTask {

    public RestartTask(String period, String notificationMessage, String messageBeforeAction, String messageAfterAction) {
        super(period, notificationMessage, messageBeforeAction, messageAfterAction);
    }

    @Override
    protected void executeAction() {
        Bukkit.getServer().shutdown();
    }

}
