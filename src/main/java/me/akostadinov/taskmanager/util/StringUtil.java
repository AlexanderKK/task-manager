package me.akostadinov.taskmanager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtil {

    public static String separateCamelCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(text.charAt(0));

        for (int i = 1; i < text.length(); i++) {
            char currentToken = text.charAt(i);

            if (Character.isUpperCase(currentToken)) {
                builder.append(' ');
            }

            builder.append(currentToken);
        }

        return builder.toString();
    }

    public static String transformToLowerCaseNoWhiteSpaces(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return removeWhiteSpaces(text).toLowerCase();
    }

    public static String removeWhiteSpaces(String text) {
        return text.replaceAll("\\s+", "");
    }

}
