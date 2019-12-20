(ns clj-text-nms.logic
    (:gen-class)
    (:require [clj-text-nms.map :as map]
              [clojure.string :as cljstr]))

(declare str-craft)

(def craft-recipes
    [[{:cu 2} {:chro 1}]
     [{:chro 1} {:cu 2}]
     [{:carbon 2} {:cond-carbon 1}]
     [{:cond-carbon 1} {:carbon 2}]
     [{:carbon 1, :oxygen 2} {:co2 1}]
     [{:ferrite-dust 2} {:mag-ferrite 1}]
     [{:mag-ferrite 1} {:ferrite-dust 2}]
     [{:carbon 50} {:c-tube 1}]
     [{:ferrite-dust 30} {:met-plat 1}]
     [{:di-hydro 40} {:dih-gel 1}]
     [{:met-plat 5, :c-tube 2, :dih-gel 1} {:adv-laser 1}]
     [{:nanode-shell 1, :chro 100} {:captured-nanode 1}]
     [{:light-of-night 1, :captured-nanode 1, :ferrite-dust 100} {:englobed-shade 1}]
     [{:noospheric-gel 1, :englobed-shade 1, :io-cob 50} {:noospheric-orb 1}]
     [{:emag-casing 1, :noospheric-orb 1, :mag-ferrite 5} {:dark-matter 1}]
     [{:part-collider 1, :dark-matter 1, :chro 25} {:dawns-end 1}]
     [{:condens-photon 1, :dawns-end 1 :copper 100} {:photic-jade 1}]
     [{:async-obj 1, :photic-jade 1, :silver 50} {:state-phasure 1}]
     [{:stars-record 1, :gold 100, :state-phasure 1} {:novae-reclaiment 1}]
     [{:sim-record 1, :platinum 50, :novae-reclaiment 1} {:modified-quanta 1}]
     [{:time-capsule 1, :uranium 100, :modified-quanta 1} {:heart-of-the-sun 1}]
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
          mined-res-list (random-sample res-number res-list)]
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

; Print all recipes.
(defn all-crafts []
    "Print all items that can be crafted."
    (doseq [recipe craft-recipes]
        (str-craft recipe)
    )
)

; Sprcial crafting recipes that trigger a function.
(def special-crafts {
    :adv-laser (fn [player]
                   (println "You've got advanced mining laser!")
                   (assoc player :adv-laser true)
               )
})

; Check if the products of the recipe is special.
; Execute corresponding functions for special crafts.
; Return new player status.
(defn check-special-craft [player recipe]
    "Check special crafts."
    (let [prods (keys (last recipe))]
        (loop [remkeys prods p player]
            (if (empty? remkeys)
                p
                (if (contains? remkeys (first prods))
                    (recur (rest remkeys) (((first prods) special-crafts) p))
                    (recur (rest remkeys) p)
                )
            )
        )
))

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

; Return a string representation of inventory, separated with ","
; Takes a hashmap-like inventory, returns a string.
; e.g. {:carbon 10, :cond-carbon 10} => "10 Carbon, 10 Condensed Carbon"
(defn str-inventory [inventory]
    "String representation of inventory"
    (cljstr/join ", " (for [[k v] inventory]
        (format "%d %s" v (k map/name-map))))
)

; Visualize a single crafting recipe. Returns a string.
; e.g. [{:carbon 2} {:cond-carbon 1}] => "2 Carbon => 1 Condensed Carbon"
(defn str-craft [recipe]
    "Describes a crafting recipe."
    (str (str-inventory (first recipe)) " => " (str-inventory (last recipe)))
)