package com.github.kay9.itemcapes.recipe;

import com.github.kay9.itemcapes.ItemCapes;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class ShapelessCapeRecipe extends ShapelessRecipe
{
    public ShapelessCapeRecipe(ResourceLocation id, String group, ItemStack result, NonNullList<Ingredient> ingredients)
    {
        super(id, group, result, ingredients);
    }

    private static ShapelessCapeRecipe wrap(ShapelessRecipe recipe, @Nullable JsonObject json)
    {
        ItemStack stack = recipe.getResultItem();
        ShapedCapeRecipe.applyCapeType(stack, json);
        return new ShapelessCapeRecipe(recipe.getId(), recipe.getGroup(), stack, recipe.getIngredients());
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return Serializer.SERIALIZER.get();
    }

    public static class Serializer extends ShapelessRecipe.Serializer
    {
        public static final RegistryObject<IRecipeSerializer<ShapelessCapeRecipe>> SERIALIZER = RegistryObject.of(ItemCapes.id("crafting_cape_shapeless"), ForgeRegistries.RECIPE_SERIALIZERS);

        public Serializer()
        {
            setRegistryName(SERIALIZER.getId());
        }

        @Override
        public ShapelessCapeRecipe fromJson(ResourceLocation id, JsonObject json)
        {
            return wrap(super.fromJson(id, json), json);
        }

        @Override
        @SuppressWarnings("ConstantConditions")
        public ShapelessCapeRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer)
        {
            return wrap(super.fromNetwork(id, buffer), null);
        }
    }
}
