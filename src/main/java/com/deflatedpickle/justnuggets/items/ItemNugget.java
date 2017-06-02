package com.deflatedpickle.justnuggets.items;

import com.deflatedpickle.justnuggets.JustNuggets;
import com.deflatedpickle.justnuggets.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemNugget extends Item {
    // TODO: Use the texture for the golden nugget and greyscale it automatically.
    public String base;
    public String type;
    public int colour;

    public ItemNugget(String base, String type){
        this.base = base;
        this.type = type;

        setUnlocalizedName(base.substring(type.length()).toLowerCase() + "Nugget");
        setRegistryName(Reference.MOD_ID + ":" + base.substring(type.length()) + "_nugget");
        setCreativeTab(JustNuggets.tabJustNuggets);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack){
        return String.format("%s Nugget", this.base.substring(type.length()));
    }
}
