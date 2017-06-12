package com.deflatedpickle.justnuggets.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;


public class ModCrafting {
    private static Set<Pair<Item, String>> recipes = new HashSet();

    public static void register(){
        for (Pair<Item, String> pair : recipes){
            // Adds recipe for turning the ingot into nuggets.
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pair.getLeft(), 9), pair.getRight()));
            // Adds recipe for turning 9 of the nugget into an ingot.
            GameRegistry.addShapelessRecipe(OreDictionary.getOres(pair.getRight()).get(0).copy(), pair.getLeft(), pair.getLeft(), pair.getLeft(), pair.getLeft(), pair.getLeft(), pair.getLeft(), pair.getLeft(), pair.getLeft(), pair.getLeft());
        }
    }

    public static void add_recipe(Item nugget, String metal){
        recipes.add(Pair.of(nugget, metal));
    }
}
