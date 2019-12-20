(ns clj-text-nms.explore
  (:gen-class)
  (:require [clj-text-nms.logic :as logic]
            [clj-text-nms.map :as map]
            [clj-text-nms.player :as player]))

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
    (println "You found a weird yellow flower on the ground. When you touched it, some kind of liquid seeped out.")
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
      (player/update-hp p #(- % (+ (rand-int 5) 5)))
      (player/update-ls p #(- % 5))))

  (fn [player]
    (println "You see a trail of dust getting closer and closer. Something is running towards you.")
    (println "It seems like a gigantic surf clam, but with two strong legs supporting its tall, flat body.")
    (println "The running surf clam scares you. You shoot it with a laser gun, and it starts to attack you.")
    (println "It falls down and groans in despair. Then it dies. You restored some health from its flesh.")
    (as-> player p
      (player/update-hp p #(+ % (+ 1 (rand-int 4))))))
   ]
  )

(def observatory-one
  [(fn [player]
      (println "The terminal in the observatory was not logged out by the last user.")
      (println "You retrieved a location data from it.")
      ; TODO - Add a tile.
      player
    )
   (fn [player]
     (println "You found a curiousity in the observatory!")
     (println "It's a spherical nanode enclusure. It looks red and translucent.")
     (println "The special material on its surface does not conduct heat.")
     (add-item player {:nanode-shell 1})
     )
   ]
  )

(def korvax-at-mii-vi [
  (fn [player]
    (println "The research center is holding lots of blueprints.")
    (println "It seems that they have some extra material for disposal.")
    (println "But you may need them. It's some Ammonia.")
    (add-item player {:ammo 10})
  )
  (fn [player]
    (println "Your wandering around the facility has clearly made some sentinels unhappy.")
    (println "They tried to shoot you, buy you swiftly evaded.")
    player
  )
  (fn [player]
    (println "Your wandering around the facility has clearly made some sentinels unhappy.")
    (println "You got several shots before you evade, but you collected some ferrite dust from their ammo.")
    (as-> player p
      (player/update-hp p #(- % (+ (rand-int 5) 5)))
      (add-item p {:ferrite-dust 20})
    )
  )
])

(def env-vec-map {
  :normal normal-fns
  :monster monster-fns
})

(def explore-functions {
    :observatory-one [observatory-one]
    :none []
    :korvax-at-mii-vi [korvax-at-mii-vi]
    ; TODO
})

; Let the player explore the current tile.
; Prints something and returns new player status.
(defn explore [player]
  (let [curr-tile (:tile player)
        tile-obj (curr-tile map/loc-obj-map)
        explorable-ops (concat ((:type tile-obj) env-vec-map) ((:shelter tile-obj) explore-functions) )]
    ((rand-nth explorable-ops) player))
)