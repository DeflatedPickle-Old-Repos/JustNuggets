package com.deflatedpickle.justnuggets.items;

import com.deflatedpickle.justnuggets.JustNuggets;
import com.deflatedpickle.justnuggets.Reference;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNugget extends Item implements IItemColor{
    // TODO: Implement IItemColor somewhere else, I've been told it's bad to do it here.
    // TODO: Use the texture for the golden nugget and greyscale it automatically.
    public String base;
    public String type;
    public int colour;

    public ItemNugget(String base, String type){
        this.base = base;
        this.type = type;

        setUnlocalizedName("nugget" + base.substring(type.length()));
        setRegistryName(Reference.MOD_ID + ":" + base.substring(type.length()) + "_nugget");
        setCreativeTab(JustNuggets.tabJustNuggets);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack){
        return String.format("%s Nugget", this.base.substring(type.length()));
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemstack(ItemStack stack, int tint){
        return this.colour;
    }
}
