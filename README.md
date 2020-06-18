# PlusTiC (Minus Bad) AKA xXx_MoreToolMats_xXx
Minecraft mod for adding new tools to and integrating various mods with Tinkers Construct

CurseForge page: https://www.curseforge.com/minecraft/mc-mods/plusticminusbad
## New tool(s)
- Katana. A fast two-handed weapon that deals increasing damage the more mobs you kill.
- Laser Gun. A ranged weapon that requires durability and energy (Forge, Tesla, RF).
## A note about Natural Pledge
Starting in 5.1.2.0, PlusTiC forces out and *overrides* Natural Pledge TiC materials by default. This can be changed in the config so that PlusTiC does *not* add Botania materials if Natural Pledge is loaded.
## Supported mod
- Biomes o Plenty by Glitchfiend
- Project Red by MrTJP
- Mekanism by aidencbrady
- Botania by Vazkii
- Advanced Rocketry by zmaster587
- ArmorPlus by Moritz30
- Thermal Foundation by CoFH
- Draconic Evolution by brandon3055
- Actually Additions by Ellpeck
- Natura by mDiyo
- Psi by Vazkii
- Avaritia by brandon3055
- MineFactory Reloaded by skyboy026
- Galacticraft by micdoodle8
- Survivalist by gigaherz
- ProjectE by sinkillerj
- Gems+ by RobZ51
- Applied Energistics 2 by AlgorithmX2 (Llamagistics by thiakil should work as well)
- Environmental Tech by ValkyrieofNight
- Thaumcraft by azanor
- Simply Jetpacks by Tomson124
## Using the source and building
After cloning this repository, run the command (with working directory in the folder with the repository)
```
./gradlew setupDecompWorkspace
```
For Eclipse, run
```
./gradlew eclipse
```
and add the jars in ./libs to the build path.
Build with
```
./gradlew clean
./gradlew build
```
## Developers/Credits
Creator/Lead Developer: @Landmaster

Chinese translation: @DYColdWind

New weapon textures: @Tenebris11

8.x tweaks: @Colonel_Gerdauf
