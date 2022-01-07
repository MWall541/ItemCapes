package com.github.kay9.itemcapes.mixins.client;

import com.github.kay9.itemcapes.CapeItem;
import com.github.kay9.itemcapes.ItemCapes;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin
{
    @Inject(method = "getCloakTextureLocation", at = @At("HEAD"), cancellable = true)
    private void redirectCapeTexture(CallbackInfoReturnable<ResourceLocation> cir)
    {
        findCurio(cir::setReturnValue, (AbstractClientPlayerEntity) (Object) this);
    }

    @Inject(method = "getElytraTextureLocation", at = @At("HEAD"), cancellable = true)
    private void redirectElytraTexture(CallbackInfoReturnable<ResourceLocation> cir)
    {
        if (ItemCapes.Config.INSTANCE.showOnElytras.get())
            findCurio(cir::setReturnValue, (AbstractClientPlayerEntity) (Object) this);
    }

    private static void findCurio(Consumer<ResourceLocation> ifPresent, AbstractClientPlayerEntity entity)
    {
        CuriosApi.getCuriosHelper()
                .findEquippedCurio(ItemCapes.CAPE_ITEM.get(), entity)
                .map(t ->
                {
                    ItemStack stack = t.getRight();
                    CompoundNBT tag = stack.getTag();
                    if (tag != null)
                    {
                        String string = tag.getString(CapeItem.CAPE_TYPE_NBT);
                        if (!string.isEmpty()) return new ResourceLocation(string);
                    }

                    return CapeItem.DEFAULT_CAPE;
                })
                .ifPresent(ifPresent);
    }
}
