(ns clj-text-nms.player (:gen-class)
    (:require [clj-text-nms.logic :as logic])
    (:require [clj-text-nms.map :as map]))

(declare add-item)
(declare rmv-item)

; A record for lethe player.
; HP - Health Points
; LS - Life Support
; galaxy - His current Galaxy
; planet - His current planet
; tile - His current tile
; inventory - His inventory
; adv-laster - Whether the player have got advanced laser.
; ship-tile - The location of the player's Spaceship.
(defrecord Player [hp ls galaxy planet tile inventory adv-laser ship-tile map-locs base-tiles])

(defn new-player []
    "Initialize a new player"
    (Player. 100 100 :g-lkx :p-3dba :t-3dba-xfce (hash-map) false :t-3dba-xfce #{} #{}))

; Modifies the player's life support according to the current planet.
; If life support is zero, decrease player's HP by 10.
; Return the new player status.
(defn tick-planet [player tile]
    "Modify the player's Life Support Status based on the current planet."
    (let [planet ((:planet tile) map/loc-obj-map)]
        (if-not (contains? (:base-tiles player) (:tile player))
            (let [new-state (update-in player :ls #(- % (:ls-drop planet)))]
                (if (<= (:ls new-state) 0)
                    (update-in (assoc :ls player 0) :hp #(- % 10))
                    new-state
                )
            )
            (update-in player :ls #(+ % 2))
        )
    )
)

; Mine resources on the current planet.
; Returns the new player status.
(defn mine [player]
    (let [mined-res (logic/mine-tilekey (:tile player) player)]
        (add-item player mined-res)
    )
)


; items - Hashmap of items to add
; Add the items to player's inventory.
; Returns new player status.
(defn add-item [player items]
    (assoc player :inventory (logic/combine-hashmap (:inventory player) items))
)

; items - Items to remove.
; If amount not sufficient, returns nil and inventory is not modified.
(defn rmv-item [player items]
    (let [new-inv (logic/subtract-hashmap (:inventory player) items)]
        (if (nil? new-inv) nil (assoc :inventory player new-inv))
    )
)

; Establish base on the current tile
; Return new player status if successful, nil if not.
(defn estab-base [player]
    "Establish a base on the current tile."
    (if (contains? (:base-tiles player) (:tile player))
        (println "You already established a base on this tile!")
        (let [new-player (rmv-item player {:chro 25})]
            (if (nil? new-player)
                (println "Establishing a base requires 25 chromatic metal. You do not have enough.")
                (do
                    (println "Success // You've established a base on this tile.")
                    (update-in new-player :base-tiles #(conj % (:tile player)))
                )
            )
        )
    )
)

; Craft items
; Returns the new player profile.
(defn craft [player recipe repeat]
    "Craft a given recipe many times."
    (let [new-inv (logic/craft (:inventory player) recipe repeat)]
        (if (nil? new-inv)
            (println "You do not have enough items!")
            (do
                (println "Crafting success!")
                (assoc player :inventory new-inv)
            )
        )
    )
)
