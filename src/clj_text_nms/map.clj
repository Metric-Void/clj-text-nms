(ns clj-text-nms.map
  (:gen-class))

(defrecord Tile [type planet id shelter base desc res res-ext])
(defrecord Planet [type galaxy id desc temp rad tox])
(defrecord Galaxy [planets])

;Short description of elements.
(def name-map {
               :observatory "Observatory"
               :carbon "Carbon"
               :di-hydro "Di-Hydrogen"
               :oxygen "Oxygen"
               :ferrite-dust "Ferrite Dust"
               :paraff "Paraffinium"
               :sodium "Sodium"
               :cond-carbon "Condensed Carbon"
               :mag-ferrite "Magnetized Ferrite"
               :cu "Copper"
               :chro "Chromatic Metal"
               :cl "Chloride"
               })

(def loc-map {
              :g-lkx "Limekell X"
              :p-3dba "Mabiangra-II"
              :t-3dba-xfce "Lowell-Kowin-Outpost"
              })

(def Mabiangra-II (Planet. :lush :g-lkx :p-3dba "A lush planet" 30.9 0 0))

(def planets-map {
                  :p-3dba Mabiangra-II})

(def Mabiangra-II-I (Tile. :normal :p-3dba :t-3dba-xfce :observatory nil
                           "An open ground with a Gek Observatory in the center.\n
                            You can see lots of plants around, but no animals.\n
                            Rocks on the ground seems to be rich of ferrite."
                           {:carbon 10 :di-hydro 3 :oxygen 3 :ferrite-dust 10 :parraff 5 :sodium 2}
                           {:cond-carbon 5 :mag-ferrite 3 :sic-pow 3}))

(def Mabiangra-II-II (Tile. :normal :p-3dba :t-3dba-xbea nil nil
                            "This place seems to be full of flowers.\n
                             Flowers on the ground are rich in sodium and oxygen."
                            {:carbon 3 :oxygen 8 :sodium 8} {:mag-ferrite 3}))
