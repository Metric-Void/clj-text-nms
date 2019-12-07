(ns clj-text-nms.player (:gen-class))

; A record for the player.
; HP - Health Points
; LS - Life Support
; galaxy - His current Galaxy
; planet - His current planet
; tile - His current tile
; inventory - His inventory
; adv-laster - Whether the player have got advanced laser.
; ship-tile - The location of the player's Spaceship.
(defrecord Player [hp ls galaxy planet tile inventory adv-laser ship-tile])

(def new-player []
    "Initialize a new player"
    (Player. 100 100 :g-lkx :p-3dba :t-3dba-xfce (hash-map) false :t-3dba-xfce))

; Modifies the player's life support according to the current planet.
; If life support is zero, decrease player's HP by 10.
; Return the new player status.
(def tick-planet [player tile]
    "Modify the player's Life Support Status based on the current planet."
    (let [planet ((:planet tile) loc-obj-map)]
        (if (:shelter :none)
            (let [new-state (update-in player :ls #(- % (:ls-drop planet)))]
                (if (<= (:ls new-state) 0)
                    (update-in (assoc player :ls 0) :hp #(- # 10))
                    new-state
                )
            )
            (update-in player :ls #(+ % 10))
        )
    )
)

; Mine resources on the current planet.
; Returns the new player status.
(defn mine [player tile]
    ; TODO
)


; items - Hashmap of items to add
; Add the items to player's inventory.
; Returns new player status.
(defn add-item [player items]
    ; TODO
)

; items - Items to remove.
; If amount not sufficient, return nil. Otherwise returns new player status.
(defn rmv-item [plater items]
    ; TODO
)