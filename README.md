# PlusTiC Reforged

Remember the classic [PlusTiC mod](https://www.curseforge.com/minecraft/mc-mods/plusticminusbad) for 1.12.2? Ever wanted to see an update for modern Minecraft versions? Well, look no further, because PlusTiC Reforged is here!

> [!NOTE]
> PlusTiC Reforged is still in early development stages. As such, bugs and crashes may be present. Please report any bugs to the project's issue tracker so they may be fixed.

## About

PlusTiC Reforged is a compatibility layer between Tinkers' Construct and various other mods, adding support for new material types that the mods introduce, such as Steel, Osmium, Refined Obsidian, Dark Matter and more. PlusTiC Reforged is a version of the original PlusTiC mod that has been rewritten and ported for newer versions of Minecraft.

## Supported Mods

See [Mod Support](./docs/Mod%20Support.md) for a list of mods that PlusTiC Reforged supports.

## New Materials

See [Materials](./docs/Materials.md) for a list of materials that PlusTiC Reforged introduces, as well as what items are accepted to create that material.

## New Tools

- Katana: a fast two-handed weapon that deals increasing damage the more mobs you kill.
- Laser Gun: a ranged weapon that requires durability and energy.

## Frequently Asked Questions (FAQ)

### What happened to the original PlusTiC mod?

PlusTiC was originally developed by Landmaster, a developer with a rich history in developing game mods. Landmaster, along with a number of various contributors on GitHub, had built the majority of the mod into what it is today.

However, the mods that Landmaster developed have all been removed from CurseForge, due to an [introduction of malicious code that would prevent a specific player from being able to log onto any servers](https://github.com/TeamDman/PlusTiC/commit/9147573c6d514ff88825a8cc1ab8438f9c80a14c). While they had [removed the code and released a new version just a few days later](https://github.com/TeamDman/PlusTiC/commit/d0b4d17ce542a186a1660c7fac6083aa3eea37aa#diff-a516bfe6aaa4dd4f38abbd0de3bdd0dbL197-L204), the damage was already done. Since then, their mods have completely vanished from the internet, including the GitHub repositories.

PlusTiC was eventually forked by TeamDman in 2020, released as "xXx_MoreToolMats_xXx (a PlusTiC fork without the evil)", and maintained by them for almost exactly three years. Development on the original mod had unfortunately come to a complete halt.

There was one feature in particular that I was looking forward to seeing at some point: an update for modern versions of Minecraft. Since it was never updated for versions beyond 1.12.2, it was one of the mods that kept me from upgrading my own modpack for playing with friends. As such, I wanted to try my hand at updating the mod for newer versions of Minecraft, so I started developing PlusTiC Reforged.

### Why is this version called "Reforged"?

Contrary to what some may think, the name "Reforged" wasn't inspired by the NeoForge (or original Forge) modloader. The word "reforged" came from the idea of blacksmiths forging new tools/weapons, since Tinkers' Construct is all about smithing.

Originally, I picked the name to distinguish the port of the original PlusTiC mod. But upon further researching what would be required to perform the port and realizing that a complete rewrite would be far better, the name became much more fitting, like a tool that's melted back into molten metal and reforged.

### Why not just use/contribute to Materialis instead?

Similar to PlusTiC, [Materialis](https://www.curseforge.com/minecraft/mc-mods/materialis) is a compatibility layer between Tinkers' Construct and various other mods, adding support for new material types. Personally, I had used PlusTiC in my own modpack and had grown accustomed to it, and had only learned about Materialis while I was developing PlusTiC Reforged. I have not tried it myself, but it seems to have a good userbase as well.

However, I found that Materialis did not support some of the mods that PlusTiC did, though it also introduced support for other mods PlusTiC did not. I had considered contributing to Materialis to add support, but I was not too fond of the way the mod was structured from a development perspective, and PlusTiC (at the time of writing) seems to have a stronger userbase based on download count. I also wanted to learn more about Java and Minecraft modding, so I decided the best path forward was to continue the legacy of PlusTiC and develop PlusTiC Reforged.

One minor detail is that I personally like the name "PlusTiC" better -- it's shorter, easier to remember (since it's so similar to "plastic"), and has a nice ring to it!

### Will you port this mod to Fabric or Quilt?

Tinkers' Construct itself doesn't support Fabric/Quilt, so...definitely not.

If Tinker's Construct _did_ introduce support for Fabric/Quilt, however, I probably still wouldn't implement support. It's not because I dislike those modloaders, but rather that this is 100% a hobby project, and I just don't have the time to learn different modloaders.

### For versions of Minecraft that NeoForge is available, will you port it to the original Forge too?

I don't particularly plan to support the original Forge from Minecraft 1.20.2 and up, which are the versions that NeoForge is available for. While it should "just work" as they share similar codebases (at least for now), my time to work on this is limited.

### Are you going to add code that blocks a specific player's UUID from joining servers?

Of course not, that would be silly and stupid!

### Will you add support for [mod]?

I'm always open to adding support for new mods! If you have a mod that you'd like to see supported and it does not already have its own support, please open an issue on the Issues page on the GitHub repository. Please note that it may take some time to add support for new mods, as I have a limited amount of time to work on this project.

If you are a developer and would like to add support for a mod yourself, please feel free to submit a pull request on the GitHub repository. I would be happy to include your changes in the mod!

### Will you add [feature]?

Please open an issue on the Issues page on the GitHub repository with your suggestion, and I will consider it! Please note that it may take some time to add new features, as I have a limited amount of time to work on this project.

### Will you backport [feature] to [older Minecraft version]?

I'm afraid not, sorry. Due to my limited time to work on this project, I am only able to support the latest version of Minecraft that I am working on, or if I'm working on porting the mod to a newer version, the latest Minecraft version the mod is available for. For example, once the version for 1.16.5 is released, I will no longer support any versions of Minecraft older than 1.16.5.

### May I use this mod in my modpack?

Of course, you don't even need to ask! Just make sure that your modpack complies with the licenses of all the mods you include, and include a link to the mod's CurseForge page in your modpack's description!

### Where can I download the mod?

Downloads for PlusTiC Reforged are available in three locations:
- The [CurseForge page](https://www.curseforge.com/minecraft/mc-mods/plustic-reforged)
- The [Modrinth page](https://modrinth.com/project/plustic-reforged)
- The [GitHub repository's Releases page](https://github.com/queengooborg/PlusTiC-Reforged/releases)

Please be weary of downloads from any other sources, as they are not controlled by me and may contain malicious code.

### What is the license for this mod?

PlusTiC Reforged is licensed under the GNU GPL v3 License. You can view the full license in the [LICENSE file](./LICENSE) in the root of the repository.

The original PlusTiC mod was licensed under the Apache License 2.0. Since the Reforged edition is a complete rewrite of the original mod, it does not need to be licensed under the same license.

## Contributing

### Building from Source

The easiest way to build the mod is to use IntelliJ IDEA or Eclipse. Simply import the project as a Gradle project in your IDE of choice, and then run the `gen[IDE]Runs` task to generate the run configurations. Then, you can run the `runData` task to generate the data files, the `runClient` task to run the mod in a test environment, or the `build` task to build a JAR file.

If you prefer to build the mod from the command line, you can use the Gradle wrapper included with the project:

```
./gradlew
./gradlew prepareRuns
./gradlew runData
./gradlew runClient
./gradlew build
```

### Localizations

If you are interested in localizing this mod for your language, I would love to include the localizations! Simply submit a [pull request to the GitHub repository](https://github.com/queengooborg/plustic-reforged/pulls) adding the new language file with all the localizations, and I will happily add it in!

### Bug Reports

If you find any bugs, please report them on the [Issues page on the GitHub repository](https://github.com/queengooborg/plustic-reforged/issues). Please include as much information as possible, such as the version of the mod you are using, the version of Minecraft you are playing on, any other mods you have installed, and a crash log or other log file as applicable.

## Developers/Credits

PlusTiC Reforged maintained by @queengooborg and [various contributors on GitHub](https://github.com/queengooborg/PlusTiC-Reforged/graphs/contributors)

Original PlusTiC developed by @Landmaster, then maintained by @TeamDman
