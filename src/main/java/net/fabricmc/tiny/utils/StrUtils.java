package net.fabricmc.tiny.utils;

public class StrUtils {

    public static int sortStringAlphabetically(String str1, String str2, boolean a_z, boolean caseSensitive)
    {
        if (!caseSensitive)
        {
            str1 = str1.toLowerCase();
            str2 = str2.toLowerCase();
        }
        for (int i = 0; i < str1.length() && i < str2.length(); i++)
        {
            if (str1.charAt(i) < str2.charAt(i))
                return a_z ? -1 : 1;
            else if (str1.charAt(i) > str2.charAt(i))
                return a_z ? 1 : -1;
        }
        return 0;
    }

    public static String trimEnd(String str)
    {
        int end = str.length();
        for (int i = end - 1; i >= 0; i--)
        {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\0')
                end = i;
        }
        return str.substring(0, end);
    }

}
