package com.deflatedpickle.justnuggets;

import com.deflatedpickle.justnuggets.init.ModCrafting;
import com.deflatedpickle.justnuggets.items.ItemNugget;
import com.deflatedpickle.justnuggets.proxy.CommonProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS, dependencies = "after:*")
public class JustNuggets {
    @Instance
    public static JustNuggets instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static ItemNugget nugget;

    public static List<Item> nugget_list = new ArrayList();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
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
                        // Register rendering of the nugget
                        ModelLoader.setCustomModelResourceLocation(nugget, 0, new ModelResourceLocation("justnuggets" + ":" + "nugget", "inventory"));
                        // Adds the nugget to the list
                        nugget_list.add(nugget);
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
        // Runs through each nugget
        for (Object nug : nugget_list) {
            // Registers the colour for each nugget
            registerItemColor((Item) nug, (IItemColor) nug);
        }
    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event){
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemColor(Item nugget, IItemColor color){
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
        itemColors.registerItemColorHandler(color::getColorFromItemstack, nugget);
    }

    public static CreativeTabs tabJustNuggets = new CreativeTabs("tab_justnuggets"){
        @Override
        public ItemStack getTabIconItem(){
            return new ItemStack(Items.GOLD_NUGGET);
        }
    };
}
