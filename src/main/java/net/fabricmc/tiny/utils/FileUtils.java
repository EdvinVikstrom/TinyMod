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

    public static boolean dirContainsFile(File folder, String fileName)
    {
        File[] files = folder.listFiles();
        if (files == null)
            return false;
        for (File file : files)
        {
            if (file.getName().equals(fileName))
                return true;
        }
        return false;
    }

}
