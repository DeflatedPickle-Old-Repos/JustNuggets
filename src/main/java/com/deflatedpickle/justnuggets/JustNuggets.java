package com.deflatedpickle.justnuggets;

import com.deflatedpickle.justnuggets.init.ModCrafting;
import com.deflatedpickle.justnuggets.items.ItemNugget;
import com.deflatedpickle.justnuggets.proxy.CommonProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
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
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

import static com.deflatedpickle.justnuggets.proxy.ClientProxy.registerItemColour;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS, dependencies = "after:*")
public class JustNuggets {
    @Instance
    public static JustNuggets instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static ItemNugget nugget;

    public static List<ItemNugget> nugget_list = new ArrayList();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
        for (String block : OreDictionary.getOreNames())
            // Search for all blocks in the OreDictionary
            if (block.startsWith("block") && !OreDictionary.getOres(block).isEmpty()){
                String blockName = block.substring("block".length());
                for (String base : OreDictionary.getOreNames())
                    // Check if that block has an ingot or gem
                    if (!OreDictionary.getOres(base).isEmpty()) {
                        if (base.equals("ingot" + blockName)) {
                            registerNugget(base, "ingot");
                        } else if (base.equals("gem" + blockName)) {
                            registerNugget(base, "gem");
                        }
                    }
            }
    }

    @EventHandler
    public void Init(FMLInitializationEvent event){
        proxy.init();
        ModCrafting.register();
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new IResourceManagerReloadListener() {
            @Override
            public void onResourceManagerReload(IResourceManager resourceManager) {
                for (ItemNugget nug : nugget_list) {
                    for (ItemStack ingot : OreDictionary.getOres(String.format("ingot%s", nug.base.substring(5)))) {
                        registerNuggetColour(ingot, nug);
                    }
                    for (ItemStack gem : OreDictionary.getOres(String.format("gem%s", nug.base.substring(3)))) {
                        registerNuggetColour(gem, nug);
                    }
                    registerItemColour(nug);
                }
            }
        });
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

    private void registerNugget(String base, String type){
        // Make a new nugget
        nugget = new ItemNugget(base, type);
        // Register the nugget
        GameRegistry.register(nugget);
        // Register rendering of the nugget
        ModelLoader.setCustomModelResourceLocation(nugget, 0, new ModelResourceLocation("justnuggets" + ":" + "nugget", "inventory"));
        // Adds the nugget to the list
        nugget_list.add(nugget);
        // Add crafting recipe
        ModCrafting.add_recipe(nugget, base);
        // Register to ore dictionary
        OreDictionary.registerOre("nugget" + base, nugget);
    }

    private void registerNuggetColour(ItemStack base, ItemNugget nug){
        IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(base, null, null);
        List<BakedQuad> quads = model.getQuads(null, null, 0);
        for (BakedQuad quad : quads) {
            TextureAtlasSprite sprite = quad.getSprite();
            int[] rgba = sprite.getFrameTextureData(0)[0];
            int colour = rgba[7 + 7 * sprite.getIconWidth()];
            // System.out.println(String.format("Nugget: %s | Ingot: %s | Texture Name: %s | RGBA: %s", nug, base, sprite.getIconName(), colour));
            nug.colour = colour;
        }
    }
}
