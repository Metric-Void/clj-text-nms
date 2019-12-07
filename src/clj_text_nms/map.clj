(ns clj-text-nms.map
  (:gen-class))

(defrecord Tile [type planet id shelter base desc res res-ext])
(defrecord Planet [type galaxy id desc temp rad tox ls-drop])
(defrecord Galaxy [planets])

;Short description of elements.
(def name-map {
  :observatory "Observatory"
  :carbon "Carbon"
  :di-hydro "Di-Hydrogen"
  :oxygen "Oxygen"
  :ferrite-dust "Ferrite Dust"
  :paraff "Paraffinium"
  :cond-carbon "Condensed Carbon"
  :mag-ferrite "Magnetized Ferrite"
  :ura "Uranium"
  :ammo "Ammonium"
  :sulf "Sulfur"
  :co2 "CO2"
  :cu "Copper"
  :chro "Chromatic Metal"
  :cl "Chloride"
})

(def loc-map {
  :g-lkx "Limekell X"
  :p-3dba "Mabiangra-II"
  :t-3dba-xfce "Lowell-Kowin Outpost"
  :t-3dba-xbea "Kerbin-August Swamp"
})

(def Mabiangra-II (Planet. :lush :g-lkx :p-3dba "A lush planet" 30.9 0.1 0.1 0))

(def Mabiangra-II-I (Tile. :normal :p-3dba :t-3dba-xfce :observatory nil
                           "An open ground with a Gek Observatory in the center.\nYou can see lots of plants around, but no animals.\nRocks on the ground seems to be rich of ferrite."
                           {:carbon 10 :di-hydro 3 :oxygen 3 :ferrite-dust 10 :parraff 5}
                           {:cond-carbon 5 :mag-ferrite 3 :sic-pow 3}))

(def Mabiangra-II-II (Tile. :normal :p-3dba :t-3dba-xbea nil nil
                            "This place seems to be full of flowers.\nFlowers on the ground are rich in sodium and oxygen."
                            {:carbon 3 :oxygen 8} {:mag-ferrite 3}))

(def loc-obj-map {
  :p-3dba Mabiangra-II
  :t-3dba-xfce Mabiangra-II-I
  :t-3dba-xbea Mabiangra-II-II
})

(defn describe-planet [planet]
  (println (format "Planet: %s\nEnvironment: %s\nDescription: %s\nTemperature(avg.): %fC\nRadioactivity: %f rad.\nToxicity: %f tox."
  ((:id planet) loc-map) ((:type planet) planet-envs) (:desc planet) (:temp planet) (:rad planet) (:tox planet))
))

(defn desc-planet-by-id [planet-id]
  (describe-planet (planet-id loc-obj-map))
)

(defn describe-tile [tile]
  (println (format "Name: %s\nOn Planet: %s\nDescription:%s\n" ((:id tile) loc-map) ((:planet tile) loc-map) (:desc tile)))
)

(defn describe-tile-id [tile-id]
  (describe-tile (tile-id loc-obj-map))
)