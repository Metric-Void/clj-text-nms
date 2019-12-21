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
       (println "A temporary camp revealed footsteps of the past.")
       (println "On a flatland lies some abandoned cargo. Surrounding them are several lights")
       (println "A beacon is here sending location ")
       player
     )
     (fn [player]
        (println "A tree suddenly fell around you. Wood broke into small pieces that easily fits into")
        (println "your inventory. You got some carbon.")
        (add-item player {:carbon 10}))
     (fn [player]
        (println "Hey what's that! A gigantic bird! You felt happy.")
        player
     )
])

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
   ])

(def exotic-fns [
  (fn [player]
    (println "It's just rocks and dusts. There's nothing to see here")
    player
  )
  (fn [player]
    (println "You found some eggs on the ground. Is it the only life on here?")
    (println "As you tried to examine it, Creatures appeared under the eggs.")
    (println "They're called the biological horrors. They can survive anywhere.")
    (println "They screamed and plunged at you. You fled as fast as you could.")
    (player/update-hp player #(- % 10))
  )
])

(def observatory-one
  [(fn [player]
      (println "The terminal in the observatory was not logged out by the last user.")
      (println "You retrieved a location data from it. Where would it lead to?")
      (update player :map-locs #(conj % :t-59aa-x2bc))
    )
   (fn [player]
     (println "You found a curiousity in the observatory!")
     (println "It's a spherical nanode enclusure. It looks red and translucent.")
     (println "The special material on its surface does not conduct heat.")
     (add-item player {:nanode-shell 1})
     )
   ]
  )

(def fortress
  [(fn [player]
    (if (nil? ((:flags player) :supplies)) (do 
      (println "The place turns out to be the last fortress of an alien civilization.")
      (println "Here the survivals of this species spent their final days.")
      (println "You do not know exactly what difficulties they encountered.")
      (println "There are some remaining supplies. The food is still edible!")
      (println "It tastes like dirt, but you restored some HP.")
      (-> player
        (add-item {:silver 36, :gold 23, :platinum 17, :io-cob 32, :met-plate 8, :dih-gel 5, :c-tube 3, :h-seal 2})
        (player/update-hp #(+ % 10))
        (update :flags #(conj % :supplies))))
      nil))

   (fn [player]
    (println "You find a way into the deepest part of the fortress and see a laboratory.")
    (println "There is a dark dodecahedron box on the table. It seems unbreakable.")
    (println "Luckily, it is open. You look inside and find an intricate circuit.")
    (println "It seems to be able to generate a force field around the box.")
    (add-item player {:emag-casing 1}))
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
    (println "There's a first-aid kit in the research center.")
    (println "You healed yourself using it.")
    (player/update-hp player #(+ % 10))
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
  (fn [player]
    (println "You found a curiousity in a secure room in the research center!")
    (println "The Korvaxes are running a simulation of the universe here.")
    (println "You extracted a record from the control interface.")
    (add-item player {:sim-record 1})
  )
])

(def cliff-at-mii-vii [
  (fn [player]
    (println "You tried to get a better view of the terrain by climbing up a mountain.")
    (println "But you slipped off during climbing. Your exosuit got a slight damage.")
    (player/update-hp player #(- % 5))
  )
 (fn [player]
   (println "You found something precious on the cliff!")
   (println "The mountain contained a gold deposit. You mined some of them.")
   (add-item player {:gold 30})
 )
])

(def luvocious-i-ii [
  (fn [player]
    (println "A plant suddenly shot a spike, targeting another planet.")
    (println "This seems to be their way of mating.")
    player
  )
  (fn [player]
    (println "Night has arrived, and you discovered this marvellous creature.")
    (println "It gathers light from surroundings and creates a curiousity inside its body.")
    (println "This process may took thousands of ages, but luckily one of them has completed the cycle.")
    (println "You've found the curiousity 'Light of Night'!")
    (add-item player {:light-of-night 1})
  )
])

(def luvocious-i-iv [
  (fn [player]
    (println "You headed directly to the Korvax Transmission tower.")
    (println "But as you get closer, you felt weird pulses that you never experienced before.")
    (println "The signal is definitely not just electromagnetic wave.")
    (println "The signal is bringing you fear.")
    player
  )
  (fn [player]
    (println "Feeling creepy, you still headed to the tower.")
    (println "As you examine the antenna and transmission modulator, you found that it's not")
    (println "a normal transmission tower. This tower transmit signal through all realities")
    (println "across multiple universes. The signal is then relayed to other stations in this")
    (println "universe. This is a central router.")
    (println "You found a broken replacement part and repaired it. It is a curiosity called")
    (println "An Asynchronous Exciter. It can send a particle to another universe.")
    (add-item player {:async-obj 1})
  )
])

(def talandra-v-iv [
  (fn [player]
    (println "This iceburg have something glowing inside.")
    (println "A friendly animal passing by brought you life support")
    (println "She looks like a deer, but with much thicker fur.")
    (player/update-ls player #(+ % 50))
  )
  (fn [player]
    (println "You tried to dig to the center of the iceburg.")
    (println "The light became brighter and brigher. You can't see what's inside")
    (println "The temperature is also getting unbelievably cold. Your exosuit is struggling.")
    (println "When you reached the center, you've drained almost all your life support.")
    (println "The light seems to come from a curiousity: Condensed Photon.")
    (println "Nobody knows why photon can be condensed, but here it is in an iceburg.")
    (println "You picked it up very carefully.")
    (as-> player p
      (player/update-ls p #(- % 50))
      (add-item p {:condens-photon 1})
    )
  )
])
(def nudryorob-v [
  (fn [player]
    (println "You found a metal plating penetrating out of the ground.")
    (println "It is clearly an artificial object.")
    (println "Your instinct is telling you to go further.")
    player
  )
  (fn [player]
    (println "As you dug down, you saw a telescope and a dome.")
    (println "This place used to be an observatory. They carry a record of all stellar objects.")
    (println "You retrieved an exclusive item, Record of the stars.")
    (add-item player {:stars-record 1})
  )
])

(def umis-iii [
  (fn [player]
    (println "You chatted with the Gek owner of the trading outpost.")
    (println "This outpost had a long history, and it has been passed by several generations.")
    (println "They're maintaining some historical, important trade routes.")
    (println "He gave you a curiosity, a time capsule as a present.")
    (add-item player {:time-capsule 1})
  )
])

(def umis-iv [
  (fn [player]
    (println "This is a Vy-Keen production facility, a weird sight")
    (println "since most other civilizations think they only know about wars.")
    (println "This facility is making antimatter for starship hyperdrives")
    (println "But they just found a better method than colliding particles")
    (println "So they're getting rid of old particle colliders.")
    (println "You got the curiosity: Particle Collider!")
    (add-item player {:part-collider 1})
  )
])

(def noos-gel [
  (fn [player]
    (println "On this toxic planet you found a yellow, jelly pond.")
    (println "You tried to shot your mining laser upon it, but it started to move")
    (println "It is slowly surrounding you from all directions. You ran away in fear.")
    (println "You cost extra Life Support when running away due to high body activity.")
    (player/update-ls player #(- % 10))
  )
 (fn [player]
   (println "On this toxic planet you found a yellow, jelly pond.")
   (println "When you get closer for examining it, the liquid started to move")
   (println "It is talking to you via telepathy, and it can sense your mind.")
   (println "It retracted after confirming that you're friendly.")
   (println "You fed the pond with some carbon and ammonium. It seemed happy.")
   (println "You took a sample from the pond using the container you have.")
   (println "You got the curiousity: Noospheric Gel!")
   (add-item player {:noospheric-gel 1})
 )
])

(def env-vec-map {
  :normal normal-fns
  :monster monster-fns
  :exotic exotic-fns
})

(def explore-functions {
    :observatory-one observatory-one
    :none []
    :korvax-at-mii-vi korvax-at-mii-vi
    :cliff-at-mii-vii cliff-at-mii-vii
    :luvocious-i-ii luvocious-i-ii
    :luvocious-i-iv luvocious-i-iv
    :nudryorob-v nudryorob-v
    :talandra-v-iv talandra-v-iv
    :fortress fortress
    :umis-iii umis-iii
    :umis-iv umis-iv
})

; Let the player explore the current tile.
; Prints something and returns new player status.
(defn explore [player]
  (let [curr-tile (:tile player)
        tile-obj (curr-tile map/loc-obj-map)
        explorable-ops (concat ((:type tile-obj) env-vec-map) ((:shelter tile-obj) explore-functions) )]
    (loop [current-trial ((rand-nth explorable-ops) player)]
      (if-not (nil? current-trial)
        current-trial
        (recur ((rand-nth explorable-ops) player))
      )
    )
  )
)