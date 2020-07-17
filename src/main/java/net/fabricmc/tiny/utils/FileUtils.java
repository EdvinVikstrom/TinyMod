package net.fabricmc.tiny.utils;

import java.io.*;

public class FileUtils {

    public static void write(String filepath, byte[] data)
    {
        try (FileOutputStream fos = new FileOutputStream(filepath))
        {
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] read(String filepath)
    {
        try (FileInputStream fis = new FileInputStream(filepath))
        {
            byte[] data = new byte[fis.available()];
            fis.read(data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean fileExists(String fileName)
    {
        return new File(fileName).exists();
    }

    public static boolean containsFile(File folder, String fileName)
    {
        return new File(folder, fileName).exists();
    }
}
