package com.deflatedpickle.justnuggets.items;

import com.deflatedpickle.justnuggets.JustNuggets;
import com.deflatedpickle.justnuggets.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemNugget extends Item {
    // TODO: Use the texture for the golden nugget and greyscale it automatically.
    // Create a variable for the base.
    public String base;
    // Create a variable for the type.
    public String type;
    // Create a variable for the colour.
    public int colour;

    public ItemNugget(String base, String type){
        // Set the base variable to the base that was passed in.
        this.base = base;
        // Set the type variable to the type that was passed in.
        this.type = type;

        // Sets the unlocalized name for the nugget.
        setUnlocalizedName(base.substring(type.length()).toLowerCase() + "Nugget");
        // Sets the registry name for the nugget.
        setRegistryName(Reference.MOD_ID + ":" + base.substring(type.length()) + "_nugget");
        // Sets the creative tab for the nugget.
        setCreativeTab(JustNuggets.tabJustNuggets);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack){
        // Sets the display name of the nugget.
        return String.format("%s Nugget", this.base.substring(type.length()));
    }
}
