package com.deflatedpickle.justnuggets;

import com.deflatedpickle.justnuggets.init.ModCrafting;
import com.deflatedpickle.justnuggets.items.ItemNugget;
import com.deflatedpickle.justnuggets.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS, dependencies = "after:*")
public class JustNuggets {
    @Instance
    public static JustNuggets instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    // Creates the nugget variable.
    public static ItemNugget nugget;

    // Creates a list for the nuggets.
    public static List<ItemNugget> nugget_list = new ArrayList();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Iterate through all ores in the ore dictionary.
        for (String ore : OreDictionary.getOreNames()) {
            if (!OreDictionary.getOres(ore).isEmpty()) {
                // Check if the ore is an ingot.
                if (ore.startsWith("ingot")) {
                    // Registers the nugget from an ingot.
                    registerNugget(ore, "ingot");
                    // Checks if the ore is gem.
                } else if (ore.startsWith("gem")) {
                    // Registers the nugget from a gem.
                    registerNugget(ore, "gem");
                }/* else if (ore.startsWith("food")) {
                    // Registers the nugget from a food.
                    registerNugget(ore, "food");
                }*/
            }
        }
    }

    // This comment only exists to trigger JamiesWhiteShirt.

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        proxy.init();
        // Registers the crafting for the nuggets.
        ModCrafting.register();
    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event) {
    }

    public static CreativeTabs tabJustNuggets = new CreativeTabs("tab_justnuggets") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.GOLD_NUGGET);
        }
    };

    public void registerNugget(String base, String type){
        // Make a new nugget.
        nugget = new ItemNugget(base, type);
        // Register the nugget.
        GameRegistry.register(nugget);
        // Register rendering of the nugget.
        proxy.registerNuggetModel(nugget);
        // Adds the nugget to the list.
        nugget_list.add(nugget);
        // Add crafting recipe.
        ModCrafting.add_recipe(nugget, base);
        // Register to ore dictionary.
        OreDictionary.registerOre("nugget" + base.substring(type.length()), nugget);
    }
}
