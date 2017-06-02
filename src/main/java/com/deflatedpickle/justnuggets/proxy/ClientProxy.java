package com.deflatedpickle.justnuggets.proxy;

import com.deflatedpickle.justnuggets.items.ItemNugget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;

public class ClientProxy implements CommonProxy{
    @Override
    public void init() {
    }

    public static void registerItemColour(ItemNugget nugget){
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
        itemColors.registerItemColorHandler(new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                return nugget.colour;
            }
        }, nugget);
    }
}
