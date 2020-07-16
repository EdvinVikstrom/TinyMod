package net.fabricmc.tiny.utils.common;

public class CaseUtils {

    public static String toSnakeCase(String str, char separator)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (Character.isUpperCase(c))
                builder.append(separator);
            builder.append(Character.toLowerCase(c));
        }
        return builder.toString();
    }

    public static String toCamelCase(String str, char separator)
    {
        StringBuilder builder = new StringBuilder();
        boolean upper = false;
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (c == separator)
            {
                upper = true;
                continue;
            }
            if (upper)
            {
                builder.append(Character.toUpperCase(c));
                upper = false;
            }else
                builder.append(Character.toLowerCase(c));
        }
        return builder.toString();
    }
}
