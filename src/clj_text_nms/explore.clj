(ns clj-text-nms.explore
  (:gen-class)
  (:require [clj-text-nms.logic :as logic]
            [clj-text-nms.map :as map]))

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

; A vector of events that can happen on normal tiles.
(def normal-fns
  [(fn [player]
    (println "You found a weird yellow flower on the ground. When you touched it, some king of liquid seeped out.")
    (println "The liquid proved not dangerous or harmful to you.")
    player)
  (fn [player]
    (println "You found a distress beacon, It is covered in dust, and battery seems to have drained.")
    (println "You managed to retrieve a log from the terminal, as follows:")
    (println " - We were running an inter-galaxy trade route while encountered by pirates.")
    (println " - Our ships were not designed to battle, and we took too much cargo to flee.")
    (println " - We lost our thrust before the sentinels arrived.")
    (println "Attached to the journal is a cargo and damage report. Everything in the report seems")
    (println "to be outdated technical components that serve no use today.")
    player)
   (fn [player]
      (println "A tree suddenly fell around you. Wood broke into small pieces that easily fits into")
      (println "your inventory. You got some carbon.")
      (add-item player {:carbon 10}))
   (fn [player]
      (println "Hey what's that! A gigantic bird! You felt happy."))
   ]
  )

(def monster-fns
  [(fn [player]
    (println "A giant tentacle suddenly reaches out of the ground and attacks you.")
    (println "The tentacle was hiding inside a pit. The opening was covered by dirt, so you didn't notice.")
    (println "You are horrified, but you still fight back with your laser gun. The tentacle retreated.")
    (as-> player p
      (update p :hp #(- % (+ (rand-int 5) 5)))
      (update p :ls #(- % 5))))
      ;;TODO rewrite with helper functions

  (fn [player]
    (println "You see a trail of dust getting closer and closer. Something is running towards you.")
    (println "It seems like a gigantic surf calm, but with two strong legs supporting its tall, flat body.")
    (println "The running surf calm scares you. You shoot it with a laser gun, and it starts to attack you.")
    (println "It falls down and groans in despair. Then it dies. You restored some health from its flesh.")
    (as-> player p
      (update p :hp #(+ % (+ 1 (rand-int 4))))))
    ;;TODO rewrite with helper functions
    ;;TODO cap hp at 100
   ]
  )

(def observatory-one
  [(fn [player]
      (println "The terminal in the observatory was not logged out by the last user.")
      (println "You retrieved a location data from it.")
      ; TODO - Add a tile.
    )]
  )

(def env-vec-map {
  :normal normal-fns
  :monster monster-fns
})

(def explore-functions {
    :observatory-one [observatory-one]
                        ; TODO
})

; Let the player explore the current tile.
; Prints something and return new player status.
(defn explore [player]
  (let [curr-tile (:tile player)
        tile-obj (curr-tile map/loc-obj-map)
        explorable-ops (concat ((:type tile-obj) env-vec-map) ((:shelter tile-obj) explore-functions) )]
    ((rand-nth explorable-ops) player))
)