package com.deflatedpickle.justnuggets.items;

import com.deflatedpickle.justnuggets.JustNuggets;
import com.deflatedpickle.justnuggets.Reference;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNugget extends Item implements IItemColor{
    public ItemNugget(String base, String type){
        setUnlocalizedName("nugget" + base.substring(type.length()));
        setRegistryName(Reference.MOD_ID + ":" + base.substring(type.length()) + "_nugget");
        setCreativeTab(JustNuggets.tabJustNuggets);
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemstack(ItemStack stack, int tint){
        return 0xff99ff;
    }
}
