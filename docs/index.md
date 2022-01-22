<!--
This document is for the main website. If it changes, the site content changes.
You can add notifications and documents to be showed publicly on the net.
This document is under CC BY-NC-SA 3.0 lisence.
-->
# Armourer's Workshop 1.16.5 Port Project

This is the project of porting [Armourer's Workshop by RiskyKen](https://github.com/RiskyKen/Armourers-Workshop)(*[Fork](https://github.com/JeonDohyeon/Armourers-Workshop)*) into **Minecraft 1.16.5**, the latest huge Minecraft version until 1.17.

Join our Discord server over [here](https://discord.gg/jZfacdAzT3)! You can get **git updates** and **project workflows** easily over here.

## Why 1.16.5?

You can wonder why I decided to port this old code into 1.16.5, not 1.17/1.18 or 1.14~1.16.4.
The answer's simple: **Most of the mods has ported to this wonderful and stable version of Minecraft**.
Also, since 1.17 the code of building mods is completely different, so it's difficult to build.

## Features

The port will have every features out from AW 1.12.2, the latest version for the latest supportive version.

## Branches

![image](https://user-images.githubusercontent.com/34373595/150487191-4f4b534f-0d4d-481b-8c88-2b738b2b7e8b.png)

There'll not be so many branches: There'll be **Experiment**, **Dangerous**, **Preview**, and **Release**.
(`Experiment/Kotlin` and `test` are removed lately)

**Using other coding language is unavaliable at this moment. If you want it, create your own fork. Updates on those will not be merged into here.**

`main` will get updates when the latest successful build is successfully applied to server, with no conflicts.

### Build

When you're to build, you should use one of these:
- [Dangerous(Alpha)](https://github.com/JeonDohyeon/Armourers-Workshop-1.16#Dangerous(Alpha)) for the latest successful build
- [Preview(Beta)](https://github.com/JeonDohyeon/Armourers-Workshop-1.16#Preview(Beta)) for the latest working build
- [main](https://github.com/JeonDohyeon/Armourers-Workshop-1.16#main) for the latest release

When build, firstly clone the repo you're to build.
And at where you're extracted the archive,
- run `chmod +x gradlew` and `./gradlew build`(or just `sudo gradlew build`) on Linux,
- or `.\gradlew.bat build` with admin console on Windows.

## Current Workflow

Currently we're working on updating the code into 1.16.5 Forge.

Since the Forge Mod Loader(a.k.a. `FML`)'s API code has greatly changed, we have to resolve the dependencies from old to new.
> In some parts, we should even **rewrite** the code since the old code is not supported after the migration.

Also, the native **Minecraft**'s code was changed alot too, but they're easier than FML because the internal code of API has not changed that much.

## Contributing

You can contribute this project into several parts: `Code`, `Localizations`, and `Wiki`.

### Contribute into Code

You can contribute about the code with pull request.

**Every pull request will be recorded to Actions > Build, even which you've forked.**

Also, **the pull requests will be recorded to [project document](https://github.com/users/JeonDohyeon/projects/2)**,
as `To-Do`(Issues), `In-Progress`(Pull Requests), `Review In Progress`(Review Required), `Reviewer Approved`(Review Done: Approved), or `Done/Denied`(Merged/Denied).

*Tip: If you created an PR to `main`, the PR will be declined.*

### Contribute into Localizations

Yup, you can still contribute the localizations on this repo. The desc will be written in english(the base!!!), and you can localize it into your language.

**The localization document type is completely different from up to Forge 1.12.x**, so you should check it out once more.

Localizations are also the code changes, so **it will be recorded as pull requests** as if it's code contributions.

### Contribute into Wiki

You can write a wiki of AW, but it'll be approved when the API code is completed to be ported into 1.16.5.

You can write a tip of creating armours, how to publish it, how to use the model in other 3D-supported programs, etc.

## Cross-Copatiblity and File Format

The most important point of porting is the cross-copatiblity with the source. If the file extension changes from `.armour`, the code should still support to import/export as `.armour`.

You can see the total breakdown of the `armour` format [here](https://github.com/JeonDohyeon/Armourers-Workshop-1.16/blob/main/armour%20file%20format.md).
It has the JSON too, but only for the test.

*Tip: If the file extension changes it'll be one of these: `.armourx`, `.json`, `.xml`, or `.txt`. None of these are supported in AW 1.12.2.*

*Tip: For the mod copatiblities, the port **can** have the function to export as `.json` since it's widely used in mods that support 3D renders.*

*Tip: If the block changes, the mod will allow you to still choose the old ones. However, since it's not number-ordered since 1.13 the order of newly added ones will be different.*

## License

![](https://i.creativecommons.org/l/by-nc-sa/3.0/88x31.png)

__[Armourer's Workshop by RiskyKen](https://github.com/RiskyKen/Armourers-Workshop)__, the __Armourer's Workshop 1.16.5 Port Project__, the `.armour` file format, and their logos are licensed under a [Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License](https://creativecommons.org/licenses/by-nc-sa/3.0/).

## Donate to RiskyKen, the Original Creator of Armourer's Workshop

[![ko-fi](https://www.ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/K3K3WVTZ)

## Donate to Dohyeon, the AW Port Project Leader

[![ko-fi](https://www.ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/Dohyeon)
