(ns clj-text-nms.logic
    (:gen-class))

; Add two hashmaps.
; Keys are combined. Conflicting values are added together.
(defn combine-hashmap [map1 map2]
    (merge-with + map1 map2)
)

; Subtract two hashmaps.
; Returns nil if any key is insufficient.
(defn subtract-hashmap [orig subtr]
    (let [subtr-avail-keys (vec (keys subtr))]
        (loop [leftkeys subtr-avail-keys override-vals (hash-map)]
            (if (empty? leftkeys)
                (merge orig override-vals)
                (if (contains? orig (first leftkeys))
                    (if (< ((first leftkeys) orig) ((first leftkeys) subtr))
                        nil
                        (recur (rest leftkeys)
                        (merge override-vals {(first leftkeys) (- ((first leftkeys) orig)
                        ((first leftkeys) subtr))}))
                    )
                    nil
                )
            )
        )
    )
)

; Mine on the current tile.
; Tile: current tile. Player: Player data.
; Returns a hashmap of mined resources.
; Does NOT modify player inventory.
(defn mine [tile player] 
    "Mine on the current planet. Returns a hashmap of mined resources."
    (let [res-tile (if (:adv-laser player) 
                        (combine-hashmap (:res tile) (:res-ext tile))
                        (:res tile))
          res-list (flatten (for [[k v] res-tile] (repeat v k)))
          res-number (if (:adv-laser player) (+ 0.2 (rand 0.6)) (+ 0.2 (rand 0.3)))
          mined-res-list (random-sample res-number res-list)
          ]
          (frequencies mined-res-list)
    )
)