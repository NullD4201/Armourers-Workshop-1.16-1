# The Armour File Format
This is the `armour` file format's byte order.

As it mentioned on the [wiki](https://github.com/RiskyKen/Armourers-Workshop/wiki/Armour-File-Format), the string has their own `unsigned short` as their string length.

This document is made to cross-compile the `armour` file and `json` file, since it seems to be similar to NBT(JSON format).

If I type the number and A~F in 2-byte spaced(e.g. `00 05`, `0A C7`, etc.) they're `unsigned short`. the `length` also is.

## First Lines
This first line is fixed, and others are not fixed as the length is different and the file is linear.

0x0~0x3: File Version(example: `13`)

And this follows:
`00 0D, AW-SKIN-START`

And this will be in json like:
```json
{"Version": 13,
```

## Properties
This part begins with this:
`00 0B, PROPS-START`

4-byte: Properties Count

and these follow:
`00 0A, authorName, string(00), length, User ID`

`00 0A, authorUUID, string(00), 00 24, UUID`

`00 0A, customName, string(00), length, Custom Name`

`00 07, flavour, string(00), length, Desc`

`00 09, PROPS-END`

This will be in json like:
```json
"Properties": [
  {"authorName": "(user ID)"},
  {"authorUUID": "(user UUID)"},
  {"customName": "(Armour's Name)"},
  {"flavour": "(Desc)"}
],
```

## Skin Type
This part begins with this:
`00 0A, TYPE-START`

This has a bunch of types. Each type has different amount of parts and their name, and here's the list.

```
armourers:head <= base
armourers:chest <= base, leftArm, rightArm
armourers:legs <= leftLeg, rightLeg, skirt
armourers:feet <= leftFoot, rightFoot
armourers:wings <= leftWing, rightWing
armourers:sword <= base
armourers:shield <= base
armourers:bow <= frame1 ~ frame3
armourers:pickaxe, armourers:axe, armourers:shovel, armourers:hoe <= base
armourers:item <= base
armourers:block <= base, multiblock
armourers:part <= base
armourers:outfit <= head;chest;legs;feet;wings as their child
armourers:unknown <= undefined skin
armourers:skirt, armourers:arrow <= base(outdated)
```

* The right side of arrow is the registered name of the part. If you're to call the left part of the foot, you can call it as `armour.feet.leftFoot` thing.
* This will be used below, as the skin part's registered name.

When the outfit has defined, the child is enlisted in the `Paint` part.

After defining the skin type, this part ends with:
`00 08, TYPE-END`

And in json, it'll look like:
```json
"type": "(type)",
```

## Skin Paint
This part begins with:
`00 0B, PAINT-START, 00, 00 09, PAINT-END`
>If this part had the coloured paint(`00 0B, PAINT-START, 01`) it will set up those with RBGt Pixel Data with 2048 bytes(2KB long).
>First 3 bytes have RBG data, and following 1 byte has their paint type.
>
>The paint type 0 is `normal`, 1 ~ 8 is for `dye_1` ~ `dye_8`.
>104 has `rainbow`, 105 ~ 106 are `pulse`s, 107 is `texture`, 108 ~ 109 are `flicker`s.
>251 is `eye`, 252 is `misc`, 253 is `skin`, 254 is `hair` and the last one(255) is not set.

A single-byte int follows to count the amount of skin part(s).

Empty one will be in JSON like:
```json
"paint": []
```

and the filled one be like:
```json
"paint": [[255, 0, 0, 0]]
```

### Armour
This part begins with:
`00 0A, PART-START`

And the registered name follows. Of course, the text length should be called.

The cube count is called next in 4 bytes.

And then, this part follows as many as the count of cubes:
```
Cube ID, X Position, Y Position, Z Position, Color(6 times, RGBt)
```

* The ID is: 00 as `normal`, 01 as `glowing`, 02 as `glass`, 03 as `glass(glowing)`.

There're markers, so the similar things happen too.

The count is called, and this follow as many as the count of markers if it's not `00 00 00 00`:
```
X Position, Y Position, Z Position, Facing
```

This part ends with:
`00 08, PART-END`

In JSON:
```json
"parts": [{
  "id": "(parts' registered name)",
  "cube": [
    [
      "ID", "X", "Y", "Z", [
        "Face1 R", "Face1 G", "Face1 B", "Face1 Type"
        ], "...", [
        "...", "Face6 Type"
        ]
      ], [
      "..."
      ]
    ], "marker": [
      ["X", "Y", "Z", "Facing"],
    []
  ]
}]
```

## Footer
The file ends with:
`00 0B, AW-SKIN-END`
