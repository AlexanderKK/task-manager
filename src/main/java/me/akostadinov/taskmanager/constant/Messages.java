package me.akostadinov.taskmanager.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Messages {

    public static final String PREFIX = "&7[&cTaskManager&7]&r";
    public static final String NO_PERMISSION = "&cYou don't have permission to use this command!";
    public static final String TASK_NOT_FOUND = "&cTask &6%s&c was not found.";
    public static final String PERIOD = "Period";
    public static final String SECOND = "second";
    public static final String SECONDS = "seconds";
    public static final String SERVER_RESTARTED_IN = "&7Server will be restarted in &6%s&7.";
    public static final String ABRACADABRA = "&5&lAbracadabra";
    public static final String SHOULD_BE = "&6%s&c should be &6%s&6.";
    public static final String SHOULD_BE_NUMBER = "&6%s&c should be a %s number.";
    public static final String SHOULD_BE_AT_LEAST = "&6%s &cshould be at least &6%d&c.";
    public static final String SHOULD_NOT_EXCEED = "&6%s &cshould not exceed &6%d&c.";
    public static final String SHOULD_BE_IN_THE_RANGE = "&6%s &cshould be in the range &6%d &cto &6%d&c.";
    public static final String DROPPED_ITEMS_REMOVED_IN = "&7Dropped items will be removed in &6%s&7.";
    public static final String DROPPED_ITEMS_REMOVED = "&6All items have been removed from the ground.";

}
