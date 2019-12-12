# File Structure.

## core.clj

This file is the entry point to the program. It have no other uses other than calling the repl in repl.clj.

## repl.clj

repl.clj Handles interaction with the player. The player can have the following operations:

- Mine: Available on every tile. Mines some resources from the current tile.
- Explore: Available on every tile. Explore the current tile. You may find something interesting!
- Move: Available on every tile. Move to adjacent tiles.
- Craft: Available on every tile. Craft items.
- Recharge Life Support: Available on every tile. Recharge life support with oxygen.
- Check inventory: Available on every tile. List inventory. Can throw away items.
- Establish base: Available if the player does not have a base on the current tile. Establish a base.
- Fly: Available if the player is together with his starship. Move to a random tile on another planet.

All the above functions are present or exported to player.clj.

## player.clj

The logic for player. Includes life support, HP, mining, crafting, moving, etc.

## map.clj

Map data and related helper functions.

## help.clj

In-game help.