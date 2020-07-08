package net.fabricmc.tiny.utils;

public class NumUtils {

    public static boolean isNumber(String str)
    {
        try {
            Double.parseDouble(str);
        }catch (NumberFormatException ignore)
        {
            return false;
        }
        return true;
    }

    public static boolean isDigit(char c)
    {
        return c >= '0' && c <= '9';
    }
}
