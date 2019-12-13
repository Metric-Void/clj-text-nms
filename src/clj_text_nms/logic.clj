(ns clj-text-nms.logic
    (:gen-class)
    (:require [clj-text-nms.map :as map]))

(def craft-recipes
    [[{:cu 2} {:chro 1}]
     [{:chro 1} {:cu 2}]
     [{:carbon 2} {:cond-carbon 1}]
     [{:cond-carbon 1} {:carbon 2}]
     [{:carbon 1, :oxygen 2} {:co2 1}]
     [{:ferrite-dust 2} {:mag-ferrite 1}]
     [{:mag-ferrite 1} {:ferrite-dust 2}]
    ])

; Add two hashmaps.
; Keys are combined. Conflicting values are added together.
(defn combine-hashmap [map1 map2]
    (merge-with + map1 map2)
)

; Multiply the value of map1.
(defn mult-hashmap [map1 mult]
    (into (hash-map)
        (for [[k v] map1] (hash-map k (* mult v)))
    )
)

; Subtract two hashmaps.
; Returns nil if any key is insufficient.
(defn subtract-hashmap [orig subtr]
    (let [subtr-avail-keys (vec (keys subtr))]
        (loop [leftkeys subtr-avail-keys override-vals (hash-map)]
            (if (empty? leftkeys)
                (into (hash-map) (filter #(not= 0 (second %)) (merge orig override-vals)))
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

(defn mine-tilekey [tile-key player]
    (mine (tile-key map/loc-obj-map) player)
)

; List craftable items
; Returns something like [[{:cu 2} {:chro 1}] [{:chro 1} {:cu 2}]]
(defn craftable [player]
    "Returns a 2-d vector of craftable items."
    (filter #(some? (subtract-hashmap (:inventory player) (first %))) craft-recipes)
)

; Craft the given recipe many times.
; Return new inventory. nil if item not enough.
(defn craft [inventory recipe repeat]
    "Craft the given recipe many times"
    (let [  required-items (first recipe)
            product-items (second recipe)
            mult-required-items (mult-hashmap required-items repeat)
            mult-product-items (mult-hashmap product-items repeat)]
        (combine-hashmap (subtract-hashmap inventory mult-required-items) mult-product-items)
    )
)