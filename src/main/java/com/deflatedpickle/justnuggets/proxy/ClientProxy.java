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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ClientProxy implements CommonProxy{
    @Override
    public void init() {
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new IResourceManagerReloadListener() {
            @Override
            public void onResourceManagerReload(IResourceManager resourceManager) {
                // Iterates through all existing nuggets.
                for (ItemNugget nug : JustNuggets.nugget_list) {
                    // Iterates through ingots that match the nugget.
                    for (ItemStack ingot : OreDictionary.getOres(String.format("ingot%s", nug.base.substring(5)))) {
                        // Registers the nugget's colour.
                        registerNuggetColour(ingot, nug);
                    }
                    // Iterates through gems that match the nugget.
                    for (ItemStack gem : OreDictionary.getOres(String.format("gem%s", nug.base.substring(3)))) {
                        // Registers the nugget's colour.
                        registerNuggetColour(gem, nug);
                    }
                    registerItemColour(nug);
                }
            }
        });
    }

    @Override
    public void registerNuggetModel(ItemNugget nugget){
        // Sets the model of the passed nugget to the location of the model.
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
        // Gets the model of the passed base.
        IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(base, null, null);
        // Creates a list of quads in the model.
        List<BakedQuad> quads = model.getQuads(null, null, 0);
        // Iterates through all quads in the list.
        for (BakedQuad quad : quads) {
            // Creates a sprite from the quad.
            TextureAtlasSprite sprite = quad.getSprite();
            // Gets the RGBA from the sprite.
            int[] rgba = sprite.getFrameTextureData(0)[0];
            // Creates an image.
            BufferedImage image = new BufferedImage(sprite.getIconWidth(), sprite.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            // Sets the RGB of the image to the sprite's colours.
            image.setRGB(0, 0, sprite.getIconWidth(), sprite.getIconHeight(), rgba, 0, sprite.getIconWidth());
            // Creates a scaled image of the image.
            Image scaled = image.getScaledInstance(1, 1, BufferedImage.TYPE_INT_ARGB);

            // Creates a new BufferedImage.
            BufferedImage buff_scaled = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            // Draws the scaled image on to the new BufferedImage.
            buff_scaled.getGraphics().drawImage(scaled, 0, 0, null);

            // Sets the colour variable to the RGB of the BufferedImage.
            int colour = buff_scaled.getRGB(0, 0);

            // Sets the colour of the nugget to the colour variable.
            nug.colour = colour;
        }
    }
}
