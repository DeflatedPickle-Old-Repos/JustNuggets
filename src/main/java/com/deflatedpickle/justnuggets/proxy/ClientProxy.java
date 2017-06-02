package com.deflatedpickle.justnuggets.proxy;

import com.deflatedpickle.justnuggets.items.ItemNugget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.oredict.OreDictionary;
import com.deflatedpickle.justnuggets.JustNuggets;

import java.util.List;

public class ClientProxy implements CommonProxy{
    @Override
    public void init() {
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new IResourceManagerReloadListener() {
            @Override
            public void onResourceManagerReload(IResourceManager resourceManager) {
                for (ItemNugget nug : JustNuggets.nugget_list) {
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

    @Override
    public void registerNuggetModel(ItemNugget nugget){
        ModelLoader.setCustomModelResourceLocation(nugget, 0, new ModelResourceLocation("justnuggets" + ":" + "nugget", "inventory"));
    }

    public static void registerItemColour(ItemNugget nugget){
        final int colour = nugget.colour;
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
        itemColors.registerItemColorHandler(new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                return colour;
            }
        }, nugget);
    }

    public void registerNuggetColour(ItemStack base, ItemNugget nug){
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
