package net.fabricmc.tiny.utils;

import net.fabricmc.tiny.optifine.OptiFineCape;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DataStorage {

    private static final ConcurrentMap<String, OptiFineCape> OPTIFINE_CAPES = new ConcurrentHashMap<>();

    public static ConcurrentMap<String, OptiFineCape> getOptifineCapes()
    {
        return OPTIFINE_CAPES;
    }
}
