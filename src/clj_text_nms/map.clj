(ns clj-text-nms.map
  (:gen-class))

(defrecord Tile [type planet id shelter desc res res-ext])
(defrecord Planet [type galaxy id desc temp rad tox ls-drop])
(defrecord Galaxy [planets])

;Short description of elements.
(def name-map {
  :observatory "Observatory",
  :carbon "Carbon",
  :di-hydro "Di-Hydrogen",
  :oxygen "Oxygen",
  :ferrite-dust "Ferrite Dust",
  :paraff "Paraffinium",
  :cond-carbon "Condensed Carbon",
  :mag-ferrite "Magnetized Ferrite",
  :ura "Uranium",
  :ammo "Ammonium",
  :sulf "Sulfur",
  :co2 "CO2",
  :cu "Copper",
  :chro "Chromatic Metal",
  :cl "Chloride",
  :adv-laser "Advanced mining laser."
})

(def planet-envs {
    :lush   "Lush - This planet is full of creatures. The atmosphere is dense and gravity is normal."
    :rad    "Radioactive - This planet is radioactive. Creatures on this planet have adapted to this envinronment and probably dangerous.\n
            Radioactive minerals can be found abundant here. \n
            Life support will drop as time goes."
    :cold   "Cold - This is a frosty planet with ice everywhere.\n
            Solid Carbon Dioxide can be easily found here.\n
            Life support will drop as time goes.\n"
    :hot    "Hot - This planet is burning. Everything looks reddish.\n
            Sulfur can be found abundant here.\n
            Life support will drop as time goes."
    :tox    "The atmosphere on this planet is toxic. \n
            Ammonium can be found abundant here. \n
            Life support will drop as time goes."
    :exotic "This planet have no atmosphere and an apparent lack of life. Life support drops rapidly.\n
             However, metal resources can be found abundant here."
})

(def loc-map {
  :g-lkx "Limekell X",
  :p-3dba "Mabiangra-II",
  :t-3dba-xfce "Lowell-Kowin Outpost",
  :t-3dba-xbea "Kerbin-August Swamp"
})

(def Mabiangra-II (Planet. :lush :g-lkx :p-3dba "A lush planet" 30.9 0.1 0.1 5))

(def Mabiangra-II-I (Tile. :normal :p-3dba :t-3dba-xfce :observatory-one
                           "An open ground with a Gek Observatory in the center.\nYou can see lots of plants around, but no animals.\nRocks on the ground seems to be rich of ferrite."
                           {:carbon 5 :di-hydro 3 :oxygen 3 :ferrite-dust 5 :paraff 2}
                           {:cond-carbon 2 :mag-ferrite 3 :sic-pow 3}))

(def Mabiangra-II-II (Tile. :normal :p-3dba :t-3dba-xbea nil
                            "This place seems to be full of flowers.\nFlowers on the ground are rich in oxygen."
                            {:carbon 3 :oxygen 8} {:mag-ferrite 3}))

(def loc-obj-map {
  :p-3dba Mabiangra-II
  :t-3dba-xfce Mabiangra-II-I
  :t-3dba-xbea Mabiangra-II-II
})

(defn observatory-one [player]
  (println "The terminal in the observatory was not logged out by the last user.")
  (println "You retrieved a location data from it.")
  ; TODO
)

(def explore-functions {
  :observatory-one observatory-one
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