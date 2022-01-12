package com.github.kay9.itemcapes;

import com.github.kay9.itemcapes.recipe.ShapedCapeRecipe;
import com.github.kay9.itemcapes.recipe.ShapelessCapeRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Mod(ItemCapes.MODID)
public class ItemCapes
{
    public static final String MODID = "itemcapes";
    public static final String CURIOS_MODID = "curios";

    public static final Logger LOG = LogManager.getLogger(MODID);

    public ItemCapes()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addGenericListener(Item.class, (RegistryEvent.Register<Item> e) -> e.getRegistry().register(new CapeItem()));
        bus.addGenericListener(IRecipeSerializer.class, (RegistryEvent.Register<IRecipeSerializer<?>> e) -> e.getRegistry().register(new ShapelessCapeRecipe.Serializer()));
        bus.addGenericListener(IRecipeSerializer.class, (RegistryEvent.Register<IRecipeSerializer<?>> e) -> e.getRegistry().register(new ShapedCapeRecipe.Serializer()));
        bus.addListener((InterModEnqueueEvent e) -> InterModComms.sendTo(CURIOS_MODID, SlotTypeMessage.REGISTER_TYPE, () ->
                new SlotTypeMessage.Builder("cape").build()));
        bus.addListener(ItemCapes::registerCapeTextures);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

    public static void registerCapeTextures(FMLClientSetupEvent evt)
    {
        try
        {
            Path dir = FMLPaths.GAMEDIR.get();
            int i = registerTexturesIn(dir = dir.resolve("capes"));
            i += registerTexturesIn(dir.resolve("elytras"));
            LOG.info("Successfully loaded {} cape textures.", i);
        }
        catch (Throwable e)
        {
            LOG.error("Failed to load cape textures for ItemCapes. Report immediately to author with logs.", e);
        }
    }

    @SuppressWarnings({"ConstantConditions", "UnstableApiUsage"})
    private static int registerTexturesIn(Path dir) throws IOException
    {
        FileFilter fileFilter = new WildcardFileFilter("*.png");
        Files.createDirectories(dir);
        File[] found = dir.toFile().listFiles(fileFilter);
        for (File file : found)
        {
            ResourceLocation name = id(com.google.common.io.Files.getNameWithoutExtension(file.getName()));
            DynamicTexture texture = new DynamicTexture(NativeImage.read(new FileInputStream(file)));

            Minecraft.getInstance().getTextureManager().register(name, texture);
        }
        return found.length; // Just return the length, errors out without this anyway.
    }

    public static ResourceLocation id(String path)
    {
        return new ResourceLocation(MODID, path);
    }

    public static class Config
    {
        public static final Config INSTANCE;
        public static final ForgeConfigSpec SPEC;

        public final ForgeConfigSpec.BooleanValue showOnElytras;

        static
        {
            Pair<Config, ForgeConfigSpec> configure = new ForgeConfigSpec.Builder().configure(Config::new);
            INSTANCE = configure.getLeft();
            SPEC = configure.getRight();
        }

        public Config(ForgeConfigSpec.Builder builder)
        {
            showOnElytras = builder.comment("Whether or not to allow the cape design to also reflect on elytras.")
                    .define("showOnElytras", true);
        }
    }
}
