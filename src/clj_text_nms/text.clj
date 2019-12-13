(ns clj-text-nms.text
    (:gen-class)
    (:require [clj-text-nms.map :as map])
    (:require [clj-text-nms.logic :as logic]))

(def op "Opening Messages.")

(def options "You can
    [M]ine in this tile
    [S]ee your inventory
    [C]raft new items
    [Q]uit the game")

; (defn current-state
;     [player]
;     "Current State."
;     )

(def current-state "Current State.")

(defn msg-inventory
    [state]
    (loop
        [nums   (vals (:inventory state))
         items  (keys (:inventory state))
         msg    "You Inventory:\n"]
        
        (if (empty? nums)
            msg
            (recur
                (rest nums)
                (rest items)
                (format "%1s %2$18s %3$3d\n" msg ((first items) map/name-map) (first nums))
                )
            )
        )
    )

(defn msg-craftable
    [state]
    (loop
        [recipes (logic/craftable state)
         count   0
         msg     "Things you can craft:\n"]

        (if (empty? recipes)
            msg
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
         msg        "This will cost:\n"]

        (if (empty? materials)
            (if insuff
                [false (str msg "You don't have enough " insu-mat ".\n")]
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
         msg   "You got:\n"]

        (if (empty? items)
            msg
            (recur
                (rest items)
                (rest nums)
                (format "%1$s%2$18s %3$3d\n"
                    msg
                    ((first items) map/name-map)
                    (first nums)
                    )
                )
            )
        )
    )