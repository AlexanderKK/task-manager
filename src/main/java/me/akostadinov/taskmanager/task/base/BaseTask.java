package me.akostadinov.taskmanager.task.base;

import lombok.Getter;
import me.akostadinov.taskmanager.TaskManager;
import me.akostadinov.taskmanager.util.Messenger;
import me.akostadinov.taskmanager.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.TimeUtil;

import static me.akostadinov.taskmanager.constant.Messages.SECOND;
import static me.akostadinov.taskmanager.constant.Messages.SECONDS;

@Getter
public abstract class BaseTask {

    private static final long TICKS_PER_SECOND = 20;
    private static final long DELAY_SECONDS = 2;

    private final String period;
    private String notificationMessage;
    private String messageBeforeAction;
    private String messageAfterAction;
    private boolean showMessages = true;

    private Integer taskId;
    private long timeOfRegistration;

    public BaseTask(String period) {
        this.period = period;
        this.showMessages = false;
        this.timeOfRegistration = TimeUtil.currentTimeSeconds();
    }

    public BaseTask(String period, String notificationMessage, String messageBeforeAction, String messageAfterAction) {
        this.period = period;
        this.notificationMessage = notificationMessage;
        this.messageBeforeAction = messageBeforeAction;
        this.messageAfterAction = messageAfterAction;
        this.timeOfRegistration = TimeUtil.currentTimeSeconds();
    }

    protected abstract void executeAction();

    public void start() {
        long periodInTicks = TimeUtil.toTicks(period);

        taskId = Bukkit.getScheduler().runTaskTimer(TaskManager.getInstance(), () -> {
            if (showMessages) {
                Bukkit.getScheduler().runTaskLater(TaskManager.getInstance(),
                        () -> Messenger.broadcast(notificationMessage), DELAY_SECONDS * 20);
            }

            executePerSecond();
        }, 0, periodInTicks).getTaskId();
    }

    public String getName() {
        String className = this.getClass().getSimpleName();
        String taskName = className.substring(0, className.lastIndexOf("Task"));

        return StringUtil.separateCamelCase(taskName);
    }

    private void executePerSecond() {

        final long[] secondsLeft = {TimeUtil.toTicks(period) / 20};

        BukkitRunnable notificationTask = new BukkitRunnable() {

            @Override
            public void run() {
                if (!Bukkit.getScheduler().isQueued(taskId)) {
                    cancel();
                    return;
                }

                if (secondsLeft[0] <= 0) {
                    executeAction();
                    timeOfRegistration = TimeUtil.currentTimeSeconds();

                    Messenger.broadcast(messageAfterAction);

                    cancel();
                    return;
                }

                if (!showMessages) {
                    secondsLeft[0]--;
                    return;
                }

                long currentSeconds = secondsLeft[0];
                if (currentSeconds <= 50 && currentSeconds % 10 == 0 || currentSeconds <= 5) {
                    Messenger.broadcast(
                            String.format(messageBeforeAction, currentSeconds + " " + (currentSeconds == 1 ? SECOND : SECONDS))
                    );
                }

                secondsLeft[0]--;
            }

        };

        notificationTask.runTaskTimer(TaskManager.getInstance(), 0, TICKS_PER_SECOND);
    }

}
