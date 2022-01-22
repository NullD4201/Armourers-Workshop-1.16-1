# Cross-Compatibility and File Format

The most important point of porting is the cross-compatibility with the source. If the file extension changes from `.armour`, the code should still support to import/export as `.armour`.

You can see the total breakdown of the `armour` format [here](https://github.com/JeonDohyeon/Armourers-Workshop-1.16/blob/main/armour%20file%20format.md).
It has the JSON too, but only for the test.

*Tip: If the file extension changes it'll be one of these: `.armourx`, `.json`, `.xml`, or `.txt`. None of these are supported in AW 1.12.2.*
<br>
*Tip: For the mod compatibles, the port **can** have the function to export as `.json` since it's widely used in mods that support 3D renders.*
<br>
*Tip: If the block changes, the mod will allow you to still choose the old ones. However, since it's not number-ordered since 1.13 the order of newly added ones will be different.*

[Back to main](https://jeondohyeon.github.io/Armourers-Workshop-1.16)
