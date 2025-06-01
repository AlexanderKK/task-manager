package me.akostadinov.taskmanager.task;

import me.akostadinov.taskmanager.task.base.BaseTask;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class DroppedItemsTask extends BaseTask {

    public DroppedItemsTask(String period, String notificationMessage, String messageBeforeAction, String messageAfterAction) {
        super(period, notificationMessage, messageBeforeAction, messageAfterAction);
    }

    @Override
    protected void executeAction() {
        List<World> worlds = getServer().getWorlds();

        for (World world : worlds) {
            List<Entity> entities = world.getEntities();

            for (Entity entity : entities) {
                if (entity instanceof Item) {
                    entity.remove();
                }
            }
        }
    }

}
