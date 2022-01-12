# ItemCapes
### A Minecraft mod which delegates capes as an item to be used as a Curio!

[Curios](https://www.curseforge.com/minecraft/mc-mods/curios) is REQUIRED for this mod to run! Please ensure you get the latest version in order to use it.

This mod was designed with modpacks in mind. But you can use it for your own uses if you wish, it just requires a bit of configuring.
> In order to fulfill the potential of this mod it is strongly recommended you have some sort of JSON knowledge, and know your way around datapacks.

With that out of the way, let's get started.

## How is it done?
The mod takes the `CapeType` found in the items nbt and uses that as a pointer at what to render for a design.
For example, if you have a cape texture named `blue.png`, the item nbt should match the file name. `{"CapeType": "blue"}`
There is a default texture inside of the mod jar so that an item with no NBT values can still render fine. (Its called "red" I'm sure you can find it.)

## Adding cape designs
Once the mod is installed it will generate a folder in the root of your game. `(root)/capes` This is where your cape textures will be stored in your pack.
If you went ahead of me because you think your cool, you probably noticed an `elytras` folder in there to. We'll get to that later. *Don't get ahead of me.*

The cape textures can be dropped into the `capes` folder and the mod will register them to the game.
> Note that currently, reloading the game resources will not refresh this registry. I know, it's inconvenient. I'm looking into it.
> If you made changes to this folder, you will need to restart the game for changes to be applied.

And thats it! Your done! Keep note of the file names you used for your cape textures however, they'll come in handy later.

*For elytra textures, just repeat everything except drop everything in the `elytras` folder found in the `capes` folder.
ENSURE THE FILE NAME IS THE EXACT SAME AS THE CAPE'S FILE NAME OR YOU WILL RUN INTO ISSUES!*

## Cape Crafting Serializer Types
As said, the capes function based on an NBT entry. So it is already flexible in what mod pack developers can do to make them obtainable.
However, this mod adds two new crafting serializer types in case that wasn't enough. They are defined as `crafting_cape_shaped` and `crafting_cape_shapeless`.
They are quite literally just carbon copies of the shaped and shapeless recipe types, with one new entry in the `result` json object.
You can specify a `"cape_type"` when adding these recipes into your datapacks, and the recipe will apply that as an NBT value to the resulting item.

### Examples

__Shaped:__
```json
{
  "type": "itemcapes:crafting_cape_shaped",
  "pattern": [
    "#",
    "X",
    "#"
  ],
  "key": {
    "#": {
      "item": "minecraft:red_dye"
    },
    "X": {
      "item": "itemcapes:cape"
    }
  },
  "result": {
    "item": "itemcapes:cape",
    "count": 1,
    "cape_type": "red"
  }
}
```
Notice the "cape_type" in the result object towards the bottom. This entry defines the name for your cape, and MUST match the file name found in the `capes` folder (minus the .png)
In this example, a cape and 2 red dye in a specific pattern will result in a new cape item with "red" as the cape design. (Defined in the nbt as `{"CapeType":"red"}`)
This is the shaped type, which in a recipe json, the type can be defined as `"type": "itemcapes:crafting_cape_shaped"`

__Shapeless:__
```json
{
  "type": "itemcapes:crafting_cape_shapeless",
  "ingredients": [
    {
      "item": "itemcapes:cape"
    },
    {
      "item": "minecraft:light_blue_dye"
    }
  ],
  "result": {
    "item": "itemcapes:cape",
    "count": 1,
    "cape_type": "blue"
  }
}
```
Similarly, a cape and 1 light blue dye in any pattern will result in a new cape item with "blue" as the cape design. (Defined in the nbt as `{"CapeType":"blue"}`)
This is the shapeless type, which in a recipe json, the type can be defined as `"type": "itemcapes:crafting_cape_shapeless"`

The mod does not include any pre-made recipes in the jar. These are examples you can utilize for your own datapack(s) for your modpack.
Or of course, you can simply ignore and have other means of obtaining these capes.
