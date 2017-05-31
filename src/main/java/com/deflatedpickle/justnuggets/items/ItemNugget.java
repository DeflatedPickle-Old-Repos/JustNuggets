package com.deflatedpickle.justnuggets.items;

import com.deflatedpickle.justnuggets.JustNuggets;
import com.deflatedpickle.justnuggets.Reference;
import net.minecraft.item.Item;

public class ItemNugget extends Item{
    public ItemNugget(String base){
        setUnlocalizedName("nugget" + base.substring("ingot".length()));
        setRegistryName(Reference.MOD_ID + ":" + base.substring("ingot".length()) + "_nugget");
        setCreativeTab(JustNuggets.tabJustNuggets);
    }
}
