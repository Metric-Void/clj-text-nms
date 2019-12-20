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
  :cob "Cobalt",
  :io-cob "Ionized Cobalt",
  :ura "Uranium",
  :ammo "Ammonium",
  :sulf "Sulfur",
  :co2 "CO2",
  :cu "Copper",
  :silver "Silver",
  :gold "Gold",
  :platinum "Platinum"
  :chro "Chromatic Metal",
  :cl "Chloride",
  :adv-laser "Advanced mining laser.",
  :met-plate "Metal Plating",
  :dih-gel "Di-Hydrogen Gel",
  :c-tube "Carbon Nanotube",
  :h-seal "Hermetic Seal",
  :nanode-shell "Nanode Enclosure",                         ;Curiousity 1
  :captured-nanode "Captured Nanode",
  :light-of-night "Light of Night",                         ;Curiousity 2
  :englobed-shade "Englobed Shade",
  :noospheric-gel "Noospheric Gel",                         ;Curiousity 3
  :noospheric-orb "Noospheric Orb",
  :emag-casing "Dark Matter Casing",                        ;Curiousity 4
  :dark-matter "Dark Matter",
  :part-collider "Particle Collider",                       ;Curiousity 5
  :dawns-end "Dawn's end",
  :condens-photon "Condensed Photon",                       ;Curiousity 6
  :photic-jade "Photic Jade"
  :async-obj  "Asynchronous Exciter"                        ;Curiousity 7
  :state-phasure "State Phasure"
  :stars-record "Record of the stars"                       ;Curiousity 8
  :novae-reclaiment "Novae Reclaiment"
  :sim-record "Simulation Record"                           ;Curiousity 9
  :modified-quanta "Modified Quanta"
  :time-capsule "Time Capsule"                              ;Curiousity 10
  :heart-of-the-sun "Heart of the Sun"                      ;Final goal
})

(def planet-envs {
    :lush   "Lush - This planet is full of creatures. The atmosphere is dense and gravity is normal."
    :rad    "Radioactive - This planet is radioactive. Creatures on this planet have adapted to this envinronment and probably dangerous.\nRadioactive minerals can be found abundant here. \nLife support will drop as time goes."
    :cold   "Cold - This is a frosty planet with ice everywhere.\nSolid Carbon Dioxide can be easily found here.\nLife support will drop as time goes.\n"
    :hot    "Hot - This planet is burning. Everything looks reddish.\nSulfur can be found abundant here.\nLife support will drop as time goes."
    :tox    "The atmosphere on this planet is toxic. \nAmmonium can be found abundant here. \nLife support will drop as time goes."
    :exotic "This planet have no atmosphere and an apparent lack of life. Life support drops rapidly.\nHowever, metal resources can be found abundant here."
})

(def loc-map {
  :g-lkx "Limekell X",
  :p-3dba "Mabiangra-II",
  :t-3dba-xfce "Lowell-Kowin Outpost",
  :t-3dba-xbea "Kerbin-August Swamp",
  :t-3dba-xcaf "Anton-Webbs Lowland",
  :t-3dba-xdab "Blorg-Rattenbury Grassland",
  :t-3dba-xaaa "Javorian Hills",
  :t-3dba-xeff "Rosamond Valley"
  :t-3dba-xfda "Heath Cliff"
})

;;
;;hill        valley      cliff
;;
;;grassland   lowland
;;
;;outpost     swamp
;;

(def loc-dir-map {
  :t-3dba-xfce {:east :t-3dba-xbea, :south nil, :west nil, :north :t-3dba-xdab},
  :t-3dba-xbea {:east nil, :south nil, :west :t-3dba-xfce, :north :t-3dba-xcaf},
  :t-3dba-xcaf {:east nil, :south :t-3dba-xbea, :west :t-3dba-xdab, :north :t-3dba-xeff},
  :t-3dba-xdab {:east :t-3dba-xcaf, :south :t-3dba-xfce, :west nil, :north :t-3dba-xaaa},
  :t-3dba-xaaa {:east :t-3dba-xeff, :south :t-3dba-xdab, :west nil, :north nil},
  :t-3dba-xeff {:east :t-3dba-xfda, :south :t-3dba-xcaf, :west :t-3dba-xaaa, :north nil},
  :t-3dba-xfda {:east nil, :south nil, :west :t-3dba-xeff, :north nil}
})

(def Mabiangra-II (Planet. :lush :g-lkx :p-3dba "A lush planet" 30.9 0.1 0.1 5))

(def Mabiangra-II-I (Tile. :normal :p-3dba :t-3dba-xfce :observatory-one
                           "An open ground with a Gek Observatory in the center.\nYou can see lots of plants around, but no animals.\nRocks on the ground seems to be rich of ferrite."
                           {:carbon 5 :di-hydro 3 :oxygen 3 :ferrite-dust 5 :paraff 2}
                           {:cond-carbon 2 :mag-ferrite 3 :sic-pow 3}))

(def Mabiangra-II-II (Tile. :normal :p-3dba :t-3dba-xbea :none
                            "This place seems to be full of flowers.\nFlowers on the ground are rich in oxygen."
                            {:carbon 3 :oxygen 4} {:mag-ferrite 3}))

(def Mabiangra-II-III (Tile. :monster :p-3dba :p-3dba-xcaf :none
                            "This is a dense forest.\nTrees grow so closely together that it is gloomy even at noon.\nThe fog that covers the depth of the forest forest makes it look creepier."
                            {:carbon 5 :oxygen 3 :paraff 2}
                            {:cond-carbon 5}))

(def Mabiangra-II-IV (Tile. :normal :p-3dba :t-3dba-xdab :none
                            "An open grassland, with no trees or buildings in sight.\nPlants contain carbon and oxygen."
                            {:carbon 2 :di-hydro 1 :oxygen 2 :ferrite-dust 3} {:sic-pow 2}))

(def Mabiangra-II-V (Tile. :normal :p-3dba :t-3dba-xaaa :none
                            "A beautiful hilly landscape. Small traces of shiny stuff can be seen on the mountains."
                            {:di-hydro 3 :ferrite-dust 4 :paraff 3 :cu 1} {:mag-ferrite 3}))

(def Mabiangra-II-VI (Tile. :normal :p-3dba :t-3dba-xeff :korvax-at-mii-vi
                            "A small valley with a Korvax research center close to waters.\nA huge device sits at the middle of the building."
                            {:carbon 1 :oxygen 3 :ferrite-dust 2} {:mag-ferrite 1 :sic-pow 1}))

(def Mabiangra-II-VII (Tile. :normal :p-3dba :t-3dba-xfda :none
                            "A steep rock cliff.\nYou can see nothing here but stones."
                            {:ferrite-dust 2} {:mag-ferrite 3 :sic-pow 3}))

(def loc-obj-map {
  :p-3dba Mabiangra-II
  :t-3dba-xfce Mabiangra-II-I
  :t-3dba-xbea Mabiangra-II-II
  :t-3dba-xcaf Mabiangra-II-III
  :t-3dba-xdab Mabiangra-II-IV
  :t-3dba-xaaa Mabiangra-II-V
  :t-3dba-xeff Mabiangra-II-VI
  :t-3dba-xfda Mabiangra-II-VII
})

(def tiles-with-exclusive #{
  :t-3dba-xfce :t-3dba-xeff
})

(defn describe-planet [planet]
  (println (format "Planet: %s\nEnvironment: %s\nDescription: %s\nTemperature(avg.): %fC\nRadioactivity: %f rad.\nToxicity: %f tox."
  ((:id planet) loc-map) ((:type planet) planet-envs) (:desc planet) (:temp planet) (:rad planet) (:tox planet))
))

(defn desc-planet-by-id [planet-id]
  (describe-planet (planet-id loc-obj-map))
)

(defn describe-tile [tile]
  (println (format "Name: %s\nOn Planet: %s\nDescription:%s\nHave Curiousity:%s"
                   ((:id tile) loc-map) ((:planet tile) loc-map) (:desc tile) (contains? tiles-with-exclusive (:id tile))))
)

(defn describe-tile-id [tile-id]
  (describe-tile (tile-id loc-obj-map))
)