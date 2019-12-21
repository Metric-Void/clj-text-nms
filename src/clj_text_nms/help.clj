(ns clj-text-nms.help (:gen-class)
  (:require [clj-text-nms.logic :as logic]))

; Help documentations for the game.

(def available-topics ["mine" "explore" "base" "teleport" "craft" "recharge"])

(def helps {
    :mine
"Mining is the most important way you get resources. You can mine on any tile, on any planet. Each planet and tile have different kinds and richness of resources. If you get an advanced mining laser, you can directly mine some advanced resources. Advanced mining laser also increase the total amount of resouces you get for a single round of mining."
    :explore
"Explore the tile means investigating it. You'll find a random interest in the current tile. You may find some items, meet enemies, get location datas, and more!"
    :base
"Build a base at the current tile. You'll be able to teleport to your bases from anywhere in the universe, and your life support will slowly charge when you're at a base tile."
    :teleport
"If you're in the same tile with your starship, you can fly to a random tile on a planet you choose, or revisit the tiles you have in your address book."
    :craft
"Items can be crafted into other items. See docs/guide for a list of items you can make."
    :recharge
"Life support slowly drops by each turn. You need to recharge them once in a while. HPs does not automatically recharge, but you can find first-aid stations by exploring."
})