(ns clj-text-nms.text
    (:gen-class)
    (:require [clj-text-nms.map :as map])
    (:require [clj-text-nms.logic :as logic]))

(def msg-win "You did it! 太陽の心をてにいれた。")

(def options "    [S]can this territory
    [E]xplore this territory
    [G]o to another tile
    [M]ine in this tile
    [V]iew your inventory
    [C]raft new items
    [B]uild a base on this tile
    [R]echarge life support
    [Q]uit the game")

(def teleport-option "    [T]eleport")

(defn current-state
    [player]
    (format "Your HP: %2d%%     Life Supporting System: %2d%%" (:hp player) (:ls player))
    )

(def dividing-line "-------------------------------------------------------------")

; (def current-state "Current State.")

(defn msg-inventory
    [state]
    (loop
        [nums   (vals (:inventory state))
         items  (keys (:inventory state))
         msg    "-------------------------------------------------------------\nYou Inventory:"]
        
        (if (empty? nums)
            msg
            (recur
                (rest nums)
                (rest items)
                (format "%1s\n%2$18s %3$3d" msg ((first items) map/name-map) (first nums))
                )
            )
        )
    )

(defn msg-craftable
    [state]
    (loop
        [recipes (logic/craftable state)
         count   0
         msg     "-------------------------------------------------------------\nThings you can craft:\n"]

        (if (empty? recipes)
            (str msg "Enter a number or [f]inish crafting.")
            (let [recipe (first recipes)]
                (recur
                    (rest recipes)
                    (inc  count)
                    (format "%1$s%2$2d: %3$18s\n"
                        msg 
                        count
                        ((first (keys (last recipe))) map/name-map)
                        (first (vals (last recipe)))
                        )
                    )
                )
            )
        )
    )

(defn msg-craft-cost
    [state recipe num]
    (loop
        [materials  (keys (first recipe))
         num-need   (vals (first recipe))
         insuff     false
         insu-mat   nil
         msg        "-------------------------------------------------------------\nThis will cost:\n"]

        (if (empty? materials)
            (if insuff
                [false (str msg "You don't have enough " insu-mat ".\nTry another number or [c]ancel crafting this item.")]
                [true (str msg "Do you wish to proceed? [Y/n]")]
                )
            (recur
                (rest materials)
                (rest num-need)
                (if (> (* (first num-need) num) ((first materials) (:inventory state)))
                    true
                    insuff
                    )
                (if (and (not insuff) (> (* (first num-need) num) ((first materials) (:inventory state))))
                    ((first materials) map/name-map)
                    insu-mat
                    )
                (format "%1s%2$18s %3$3d (You have %4$d)\n"
                    msg
                    ((first materials) map/name-map)
                    (* (first num-need) num)
                    ((first materials) (:inventory state))
                    )
                )
            )
        )
    )

(defn msg-mine
    [mined-res]
    
    (loop
        [items (keys mined-res)
         nums  (vals mined-res)
         msg   "-------------------------------------------------------------\nYou got:"]

        (if (empty? items)
            msg
            (recur
                (rest items)
                (rest nums)
                (format "%1$s\n%2$18s %3$3d"
                    msg
                    ((first items) map/name-map)
                    (first nums)
                    )
                )
            )
        )
    )

(defn msg-teleport-dest
    [player initial]
    (case initial
        "p" (loop
                [planet-list (keys map/planet-map)
                 count       0
                 msg         (format "%s\nHere are the planets you can teleport to" dividing-line)]
                (if (empty? planet-list)
                    msg
                    (recur
                        (rest planet-list)
                        (inc count)
                        (format "%1s\n%2$2d: %3$s" msg count ((first planet-list) map/loc-map))
                        )
                    )
                )
        "t" (loop
                [tile-list (:map-locs player)
                count      0
                msg        (format "%s\nHere are the tiles you can teleport to" dividing-line)]
                (if (empty? tile-list)
                    msg
                    (recur
                        (rest tile-list)
                        (inc count)
                        (format "%1s\n%2$2d: %3$s" msg count ((first tile-list) map/loc-map))
                        )
                    )
                )
        "Wrong parameter"
        )
    )

(def prompt-teleport "Do you want to teleport to a [p]lanet or a [t]ile?")

(def msg-move "Try
    [N]orth
    [W]est
    [S]outh
    [E]ast"
    )

(def op (str
    "Welcome to the text adventure game of No Man's Sky!"
    "In this game you will travel on and between planets, mine resources, craft items...."
    "And your final goal is to craft \"Heart of the Sun\". It require exclusive ingredients"
    "that could only be found by exploring regions. Your scanner will tell you if a region"
    "contains exclusive items."
))