package com.github.kay9.itemcapes;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CapeItem extends Item implements ICurioItem
{
    public static final RegistryObject<Item> ITEM = RegistryObject.of(ItemCapes.id("cape"), ForgeRegistries.ITEMS);

    public static final String CAPE_TYPE_NBT = "CapeType"; // Key for ResourceLocation
    public static final ResourceLocation DEFAULT_CAPE = ItemCapes.id("textures/red.png");

    public CapeItem()
    {
        super(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1).tab(ItemGroup.TAB_TOOLS));
        setRegistryName(ITEM.getId());
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack)
    {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_ELYTRA, 1f, 1f);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack)
    {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World level, List<ITextComponent> desc, ITooltipFlag flags)
    {
        String type = "red";
        CompoundNBT tag = stack.getTag();
        if (tag != null)
        {
            String string = tag.getString(CAPE_TYPE_NBT);
            if (!string.isEmpty()) type = string;
        }
        desc.add(new TranslationTextComponent("item.itemcapes.cape.desc").withStyle(TextFormatting.GOLD)
                .append(new StringTextComponent(": ").withStyle(TextFormatting.AQUA))
                .append(new StringTextComponent(type.replace('_', ' ')).withStyle(TextFormatting.YELLOW)));
    }
}
