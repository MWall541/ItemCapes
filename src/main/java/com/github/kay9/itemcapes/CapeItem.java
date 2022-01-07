package com.github.kay9.itemcapes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;

public class CapeItem extends Item implements ICurioItem
{
    public static final String CAPE_TYPE_NBT = "CapeType"; // Key for ResourceLocation
    public static final ResourceLocation DEFAULT_CAPE = ItemCapes.id("red");

    public CapeItem()
    {
        super(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1).tab(ItemGroup.TAB_TOOLS));
        setRegistryName(ItemCapes.CAPE_ITEM.getId());
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
}
