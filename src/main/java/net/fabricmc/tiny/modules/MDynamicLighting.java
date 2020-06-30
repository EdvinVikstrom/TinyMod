package net.fabricmc.tiny.modules;

import net.fabricmc.tiny.Config;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MDynamicLighting {

    private static final ConcurrentMap<Item, Integer> itemLighting = new ConcurrentHashMap<>();
    static {
        itemLighting.put(Items.TORCH, 15);
    }

    public static int getLightLevel(AbstractClientPlayerEntity player)
    {
        if (Config.getBoolean("dynamicLighting").get())
        {
            ItemStack mainHand = player.getMainHandStack();
            ItemStack offHand = player.getOffHandStack();
            int mainHandLight = getLightLevel(mainHand.getItem());
            int offHandLight = getLightLevel(offHand.getItem());
            return Math.max(mainHandLight, offHandLight);
        }
        return 0;
    }

    private static int getLightLevel(Item item)
    {
        if (itemLighting.containsKey(item))
            return itemLighting.get(item);
        return 0;
    }

}
