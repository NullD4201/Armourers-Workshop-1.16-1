## Armourer's Workshop 1.16.5 Port Project

This is the project of porting [Armourer's Workshop by RiskyKen](https://github.com/RiskyKen/Armourers-Workshop)(*[Fork](https://github.com/JeonDohyeon/Armourers-Workshop)*) into **Minecraft 1.16.5**, the latest huge Minecraft version until 1.17.

Join our Discord server over [here](https://discord.gg/jZfacdAzT3)! You can get **git updates** and **project workflows** easily over here.

## Why 1.16.5?

You can wonder why I decided to port this old code into 1.16.5, not 1.17/1.18 or 1.14~1.16.4. The answer's simple: **Most of the mods has ported to this wonderful and stable version of Minecraft**. Even the CustomNPC mod has ported into here, but I don't know where is it lol

## Features

The port will have every features out from AW 1.12.2, the latest version for the latest supportive version.

## Current Workflow

Currently I'm studying Java 8 for porting this: Maybe the child repos will be created since the multi-export seems to be unavaliable.

Workflow is under the code block:

```markdown
First is the one if I port it using the old code:
1. Firstly it should be ported to the latest update of Forge 1.12.2, to make it easy to port it into later versions.
   It should be ported to some reason, like the code copablity across the later version of Forge.
   May need to rewrite the code entirely.
2. Porting into Forge 1.13: Maybe the direct port will be unavaliable so porting into 1.13.x will be required.
3. Porting into Forge 1.15: 1.14 and 1.15's API changes are similar, so maybe the both will be worked at once.
   1.15 is the later version so maybe direct port into 1.15 will save a bit of time.
4. Porting into Forge 1.16.1: This step is necessary because between 1.16.1 and 1.16.5 has a lot of code gaps.
5. Porting into Forge 1.16.5: This is the last step.
```
```markdown
Second is the one if I just rewrite the requirements:
1. Firstly I should documentise the code to make sure which is required to code.
2. Find the copablity of the functions: If the external-file loading is disabled without the API
   the API branch will be created.
   If the NetGet is disabled I will add you should use versions for older Minecraft to get armour files from Net.
3. Code with Forge 1.16.5 Mode DevKit: I wish it will work properly, similar to 1.12.2 ones.
```

Both will use GitHub's compile system: I'm afraid my PC will correctly compile it.

## Contributing

You can contribute this project into several parts: `Code`, `Localizations`, and `Wiki`.

### Contribute into Code

You can contribute about the code with pull request.

**Every pull request will be recorded to Actions > Build, even which you've forked.**

Also, **the pull requests will be recorded to [project document](https://github.com/users/JeonDohyeon/projects/2)**, as `To-Do`(Issues), `In-Progress`(Pull Requests), `Review In Progress`(Review Required), `Reviewer Approved`(Review Done: Approved), `Done/Denied`(Merged/Denied).

### Contribute into Localizations

Yup, you can still contribute the localizations on this repo. The desc will be written in english(the base!!!), and you can localize it into your language.

**The localization document type is completely different from up to Forge 1.12.x**, so you should check it out once more.

Localizations are also the code changes, so **it will be recorded as pull requests** as if it's code contributions.

### Contribute into Wiki

You can write a wiki of AW, but it'll be approved when the API code is completed to be ported into 1.16.5.

You can write a tip of creating armours, how to publish it, how to use the model in other 3D-supported programs, etc.

## Cross-Copatiblity

The most important point of porting is the cross-copatiblity with the source. If the file extension changes from `.armour`, the code should still support to import/export as `.armour`.

*Tip: If the file extension changes it'll be one of these: `.armourx`, `.json`, `.xml`, or `.txt`. None of these are supported in AW 1.12.2.*

*Tip: For the mod copatiblities, the port **can** have the function to export as `.json` since it's widely used in mods that support 3D renders.*

*Tip: If the block changes, the mod will allow you to still choose the old ones. However, since it's not number-ordered since 1.13 the order of newly added ones will be different.*

## License

![](https://i.creativecommons.org/l/by-nc-sa/3.0/88x31.png)

__[Armourer's Workshop by RiskyKen](https://github.com/RiskyKen/Armourers-Workshop)__ and the __Armourer's Workshop 1.16.5 Port Project__ is licensed under a [Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License](https://creativecommons.org/licenses/by-nc-sa/3.0/).

## Donate to RiskyKen, the Original Creator of Armourer's Workshop

[![ko-fi](https://www.ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/K3K3WVTZ)

## Donate to Dohyeon, the AW Port Project Leader

[![ko-fi](https://www.ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/Dohyeon)
