# What mods does PlusTiC Reforged support?

PlusTiC Reforged is designed to add support to a variety of mods. The following is a list of mods that are currently supported, as well as mods that were previously supported and when support was dropped.

## Materials

- [Project Red](https://www.curseforge.com/minecraft/mc-mods/project-red-core)
- [Botania](https://www.curseforge.com/minecraft/mc-mods/botania) -- partially implemented
- [ArmorPlus](https://www.curseforge.com/minecraft/mc-mods/armorplus) -- pending
- [Psi](https://www.curseforge.com/minecraft/mc-mods/psi)
- [Industrial Foregoing](https://www.curseforge.com/minecraft/mc-mods/industrial-foregoing) -- pending
- [ProjectE](https://www.curseforge.com/minecraft/mc-mods/projecte)
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
- [The Twilight Forest](https://www.curseforge.com/minecraft/mc-mods/the-twilight-forest) -- pending
- [Draconic Evolution](https://www.curseforge.com/minecraft/mc-mods/draconic-evolution) (unavailable in MC 1.19) -- pending
- [GemsPlusPlus](https://www.curseforge.com/minecraft/mc-mods/gemsplusplus) (discontinued since MC 1.16)
- [Silent's Gems](https://www.curseforge.com/minecraft/mc-mods/silents-gems)
- [Jade](https://www.curseforge.com/minecraft/mc-mods/jade) (previously [HWYLA](https://www.curseforge.com/minecraft/mc-mods/hwyla)) -- pending
- [Just Enough Items](https://www.curseforge.com/minecraft/mc-mods/jei) -- pending

### Previously Supported

#### In Original PlusTiC

- [Advanced Rocketry](https://www.curseforge.com/minecraft/mc-mods/advanced-rocketry) (discontinued since MC 1.12)
- [Natural Pledge](https://www.curseforge.com/minecraft/mc-mods/natural-pledge)* (discontinued since MC 1.12)
- [Natura](https://www.curseforge.com/minecraft/mc-mods/natura) (discontinued since MC 1.12)
- [Galacticraft Legacy](https://www.curseforge.com/minecraft/mc-mods/galacticraft-legacy) (discontinued since MC 1.12)
- [Environmental Tech](https://www.curseforge.com/minecraft/mc-mods/environmental-tech) (discontinued since MC 1.12)
- [Thaumcraft](https://www.curseforge.com/minecraft/mc-mods/thaumcraft) (discontinued since MC 1.12)
- [Simply Jetpacks 2](https://www.curseforge.com/minecraft/mc-mods/simply-jetpacks-2) (discontinued since MC 1.12)
- [Future MC](https://www.curseforge.com/minecraft/mc-mods/future-mc) (added in v9.1.0, mod is a compatibility layer to add MC 1.15+ features to MC 1.12, thus is not needed in newer versions)
- [Actually Additions](https://www.curseforge.com/minecraft/mc-mods/actually-additions) (unavailable between MC 1.16 and 1.19, available for MC 1.20 -- not planning to add support unless requested)
- [Mekanism](https://www.curseforge.com/minecraft/mc-mods/mekanism) (has built-in support since MC 1.16)
- [Thermal Foundation](https://www.curseforge.com/minecraft/mc-mods/thermal-foundation) (has built-in support since MC 1.16)
- [Advent of Ascension (Nevermine)](https://www.curseforge.com/minecraft/mc-mods/advent-of-ascension-nevermine) (has built-in support since AoA v3.3)
- [Biomes o' Plenty](https://www.curseforge.com/minecraft/mc-mods/biomes-o-plenty) (items used in PlusTiC materials were removed in MC 1.16)
- [Astral Sorcery](https://www.curseforge.com/minecraft/mc-mods/astral-sorcery) (discontinued since MC 1.16)
- [Survivalist](https://www.curseforge.com/minecraft/mc-mods/survivalist) (discontinued since MC 1.16)
- [Avaritia](https://www.curseforge.com/minecraft/mc-mods/avaritia-1-10) (unavailable in MC 1.16, discontinued since MC 1.18)
- [CraftTweaker](https://www.curseforge.com/minecraft/mc-mods/crafttweaker) (did not feel like maintaining support)
- [Construct's Armory](https://www.curseforge.com/minecraft/mc-mods/constructs-armory) (discontinued since MC 1.12, functionality merged into Tinkers' Construct)
- [ModTweaker](https://www.curseforge.com/minecraft/mc-mods/modtweaker) (discontinued since MC 1.12)
- [Tesla](https://www.curseforge.com/minecraft/mc-mods/tesla) (discontinued since MC 1.12)
- [Tesla Core Lib](https://www.curseforge.com/minecraft/mc-mods/tesla-core-lib) (discontinued since MC 1.12)

\* About Natural Pledge: Starting in v5.1.2.0, PlusTiC forced out and overrode Natural Pledge TiC materials by default. This could be changed in the config so that PlusTiC did not add Botania materials if Natural Pledge was loaded.

## New Mod Support

If you would like to request support for a mod, please open an [issue on the GitHub repository](https://github.com/queengooborg/plustic-reforged/issues)!

### Mods With Plans to Support

The following is a list of mods that are planned to be supported by PlusTiC Reforged:

_So far, there have been no requests to add support for new mods (PlusTiC Reforged is still in early development after all). This section will eventually contain the list of requested mods to support._

### Mods That Will Not Be Supported

The following is a list of mods that will not be supported by PlusTiC Reforged, whether the mod already has Tinkers' Construct or for other reasons:

- [Future Versions](https://www.curseforge.com/minecraft/mc-mods/future-versions) (similar mod to Future MC -- tried to implement support, but ran into issues and decided not to continue)

### Mod Supported, But Not Listed?

Since PlusTiC Reforged uses item tags to specify the ingredients needed for new materials, any mod that adds the specified material _should_ be supported automatically. For example, if a mod adds a Ruby gem, it should be usable in PlusTiC Reforged as long as the mod adds its gem to the `forge:gems/ruby` tag. This means that PlusTiC Reforged should be able to support a wide range of mods without needing to add explicit support for them.

If a mod adds an item that should be supported but is not, you may want to file an issue with the developers of that mod to request that they add the item to the appropriate tag.