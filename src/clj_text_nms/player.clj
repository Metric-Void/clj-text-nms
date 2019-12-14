(ns clj-text-nms.player (:gen-class)
    (:require [clj-text-nms.logic :as logic])
    (:require [clj-text-nms.map :as map])
    (:require [clj-text-nms.text :as text]))

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
(defn tick-planet [player]
    "Modify the player's Life Support Status based on the current planet."
    (let [  tile ((:tile player) map/loc-obj-map)
            planet ((:planet tile) map/loc-obj-map)]
        (if-not (contains? (:base-tiles player) (:tile player))
            (let [new-state (update player :ls #(- % (:ls-drop planet)))]
                (if (<= (:ls new-state) 0)
                    (update (assoc player :ls 0) :hp #(- % 10))
                    new-state
                )
            )
            (update player :ls #(+ % 2))
        )
    )
)

; Mine resources on the current planet.
; Returns the new player status.
(defn mine [player]
    (let [mined-res (logic/mine-tilekey (:tile player) player)]
        (do
            (println (text/msg-mine mined-res))
            (add-item player mined-res)
        )
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
                    (update new-player :base-tiles #(conj % (:tile player)))
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

; Teleport to a location on the map
; Both tile and planet will be changed.
; Starship does not follow the player
(defn teleport [player newtile]
    (assoc player :tile newtile :planet (:planet (newtile map/loc-obj-map)))
)

; Interactive command, recharge Life Support.
; Returns new player status.
(defn recharge-life-support [player]
    "Prompts to recharge Life Support"
    (if (contains? (:inventory player) :oxygen)
        (let [
            single-charge 3                                                 ; Defines how much LS each oxygen charges.
            required-amount (int (/ (- 100 (:ls player) single-charge)))    ; Required amount to charge to full
            have-amount (:oxygen (:inventory player))       ; The amount of oxygen the player have.
            use-amount (min required-amount have-amount)    ; The actual amount of Oxygen that can be used.
            ls-change-amount (* single-charge use-amount)   ; The amount of LS charged
            after-charge (+ (:ls player) ls-change-amount)     ; The value after charging
        ]
            (println (format "Use %d oxygen to charge Life Support from %d to %d?\n[1] Yes [2] No" use-amount (:ls player) after-charge))
            (if (= (read) 1)
                (do (println "Success!") (assoc player :ls after-charge))
                (do (println "Cancelled") player)
            )
        )
        (do (println "Recharging life support requires Oxygen, which you don't have any.") player)
    )
)

; Recharge life support without asking
(defn recharge-ls-silent [player]
    "Silently recharge Life Support"
    (if (contains? (:inventory player) :oxygen)
        (let [
            single-charge 3                                                 ; Defines how much LS each oxygen charges.
            required-amount (int (/ (- 100 (:ls player) single-charge)))    ; Required amount to charge to full
            have-amount (:oxygen (:inventory player))       ; The amount of oxygen the player have.
            use-amount (min required-amount have-amount)    ; The actual amount of Oxygen that can be used.
            ls-change-amount (* single-charge use-amount)   ; The amount of LS charged
            after-charge (+ (:ls player) ls-change-amount)     ; The value after charging
        ]
            (assoc player :ls after-charge)
        )
        (do (println "Recharging life support requires Oxygen, which you don't have any.") player)
    )
)

; Set new HP. Ensure HP is between 0 and 100.
; Returns new player status
(defn set-hp [player new-hp]
    "Set the new HP."
    (let [realhp (min 100 (max 0 new-hp))]
        (assoc player :hp realhp))
)

; Set new LS. Ensure LS is between 0 and 100.
; Returns new player status
(defn set-ls [player new-ls]
    "Set the new LS."
    (let [realls (min 100 (max 0 new-ls))]
        (assoc player :hp realls))
)

; Update HP with the given function.
; Returns new player status.
(defn update-hp [player ifn]
    "Update HP with given function."
    (set-hp player (ifn (:hp player)))
)

; Update LS with the given function.
; Returns new player status.
(defn update-ls [player ifn]
    "Update LS with given function."
    (set-ls player (ifn (:ls player)))
)