package com.github.kay9.itemcapes;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod(ItemCapes.MODID)
public class ItemCapes
{
    public static final String MODID = "itemcapes";
    public static final String CURIOS_MODID = "curios";

    public static final RegistryObject<Item> CAPE_ITEM = RegistryObject.of(ItemCapes.id("cape"), ForgeRegistries.ITEMS);

    public ItemCapes()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addGenericListener(Item.class, (RegistryEvent.Register<Item> e) -> e.getRegistry().register(new CapeItem()));
        bus.addListener((InterModEnqueueEvent e) -> InterModComms.sendTo(CURIOS_MODID, SlotTypeMessage.REGISTER_TYPE, () ->
                new SlotTypeMessage.Builder("cape").build()));

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
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
