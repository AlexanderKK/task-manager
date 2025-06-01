package me.akostadinov.taskmanager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeFormatter {

    public static String formatSecondsToDayTime(long totalSeconds) {
        long remainder = totalSeconds % 3600;

        long hours = totalSeconds / 3600;
        long minutes = remainder / 60;
        long seconds = remainder % 60;

        StringBuilder builder = new StringBuilder();

        if (hours > 0) {
            builder
                    .append(hours)
                    .append(" hour")
                    .append(hours == 1 ? "" : "s")
                    .append(" ");
        }

        if (minutes > 0) {
            builder
                    .append(minutes)
                    .append(" minute")
                    .append(minutes == 1 ? "" : "s")
                    .append(" ");
        }

        if (seconds > 0 || builder.isEmpty()) {
            builder
                    .append(seconds)
                    .append(" second")
                    .append(seconds == 1 ? "" : "s");
        }

        return builder.toString().trim();
    }

}
