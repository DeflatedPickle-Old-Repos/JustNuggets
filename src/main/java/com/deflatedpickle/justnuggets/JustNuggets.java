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

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS, dependencies = "after:*")
public class JustNuggets {
    @Instance
    public static JustNuggets instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static ItemNugget nugget;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        for (String block : OreDictionary.getOreNames())
            // Search for all blocks in the OreDictionary
            if (block.startsWith("block") && !OreDictionary.getOres(block).isEmpty()){
                String blockName = block.substring("block".length());
                for (String metal : OreDictionary.getOreNames())
                    // Check if that block has an ingot
                    if (metal.equals("ingot" + blockName) && !OreDictionary.getOres(metal).isEmpty()){
                        // Make a new nugget
                        nugget = new ItemNugget(metal);
                        // Register the nugget
                        GameRegistry.register(nugget);
                        // Add crafting recipe
                        ModCrafting.add_recipe(nugget, metal);
                        // Register to ore dictionary
                        OreDictionary.registerOre("nugget" + metal, nugget);
                    }
            }
    }

    @EventHandler
    public void Init(FMLInitializationEvent event){
        proxy.init();
        ModCrafting.register();
    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event){
    }

    public static CreativeTabs tabJustNuggets = new CreativeTabs("tab_justnuggets"){
        @Override
        public ItemStack getTabIconItem(){
            return new ItemStack(Items.GOLD_NUGGET);
        }
    };
}
