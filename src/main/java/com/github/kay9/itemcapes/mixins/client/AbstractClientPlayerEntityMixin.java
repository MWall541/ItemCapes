package com.github.kay9.itemcapes.mixins.client;

import com.github.kay9.itemcapes.CapeItem;
import com.github.kay9.itemcapes.ItemCapes;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashMap;
import java.util.Map;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin
{
    private static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();

    @Inject(method = "getCloakTextureLocation", at = @At("HEAD"), cancellable = true)
    private void redirectCapeTexture(CallbackInfoReturnable<ResourceLocation> cir)
    {
        findCurio(cir, false);
    }

    @Inject(method = "getElytraTextureLocation", at = @At("HEAD"), cancellable = true)
    private void redirectElytraTexture(CallbackInfoReturnable<ResourceLocation> cir)
    {
        if (ItemCapes.Config.INSTANCE.showOnElytras.get()) findCurio(cir, true);
    }

    private void findCurio(CallbackInfoReturnable<ResourceLocation> cir, boolean elytra)
    {
        CuriosApi.getCuriosHelper()
                .findEquippedCurio(CapeItem.ITEM.get(), (AbstractClientPlayerEntity) (Object) this)
                .map(t ->
                {
                    ItemStack stack = t.getRight();
                    CompoundNBT tag = stack.getTag();
                    if (tag != null)
                    {
                        String type = tag.getString(CapeItem.CAPE_TYPE_NBT);
                        if (elytra) type = "elytra_" + type;
                        return TEXTURE_CACHE.computeIfAbsent(type, s -> ResourceLocation.tryParse(ItemCapes.MODID + ":" + s));
                    }

                    return CapeItem.DEFAULT_CAPE;
                })
                .ifPresent(cir::setReturnValue);
    }
}
