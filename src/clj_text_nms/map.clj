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
  :sic-pow "Silicon Powder"
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
  :nanode-shell "Nanode Enclosure",                         ;Curiousity 1 - observ-1
  :captured-nanode "Captured Nanode",
  :light-of-night "Light of Night",                         ;Curiousity 2 - luvi-i-ii
  :englobed-shade "Englobed Shade",
  :noospheric-gel "Noospheric Gel",                         ;Curiousity 3
  :noospheric-orb "Noospheric Orb",
  :emag-casing "Dark Matter Casing",                        ;Curiousity 4 - tal-v-iii
  :dark-matter "Dark Matter",
  :part-collider "Particle Collider",                       ;Curiousity 5 - umis-iv
  :dawns-end "Dawn's end",
  :condens-photon "Condensed Photon",                       ;Curiousity 6 - tal-v-iv
  :photic-jade "Photic Jade"
  :async-obj  "Asynchronous Exciter"                        ;Curiousity 7 - luv-i-iv
  :state-phasure "State Phasure"
  :stars-record "Record of the stars"                       ;Curiousity 8 - nud-v
  :novae-reclaiment "Novae Reclaiment"
  :sim-record "Simulation Record"                           ;Curiousity 9 - korvax-mii-vi
  :modified-quanta "Modified Quanta"
  :time-capsule "Time Capsule"                              ;Curiousity 10 - umis-iii
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
  :p-9a3e  "Luvocious-I"
  :t-9a3e-xfe4 "Amlinf-Hii Desert"
  :t-9a3e-xc4d "Ewwessalic Grassland"
  :t-9a3e-xbd2 "Ebatus Pit"
  :t-9a3e-x67e "Rhyfordias Terminus"
  :p-5fec "Talandra"
  :t-5fec-xaac "Shariat Cliff"
  :t-5fec-xbee "Gorfis Tundra"
  :t-5fec-xdec "Nova Constantinople"
  :t-5fec-xdda "Tolkien Glacier"
  :p-59aa "Nudryorob"
  :t-59aa-e4d2 "Luknati Plain"
  :t-59aa-ca57 "Emkinses Plain"
  :t-59aa-xadf "Ackladesh Plain"
  :t-59aa-xbdf "Ryoinc Plain"
  :t-59aa-x2bc "Ihamija Plateau"
  :p-2ea1 "Umis"
  :t-2ea1-x9bc "Amnaco Swamp"
  :t-2ea1-x4a3 "Yamabe Desert"
  :t-2ea1-x67f "Ilnyoti Plain"
  :t-2ea1-x3db "Podimi Valley"
  :t-2ea1-xbc2 "Srbija Mounts"
  :p-67ec "Pathavon"
  :t-67ec-xae5 "Daranma Valley"
  :t-67ec-xd34 "Lake Song"
  :t-67ec-x3a6 "Menkar Hills"
  :t-67ec-xe7f "Mount Qabji"
  :t-67ec-x7f7 "Naya-Sohra Plateau"
})

;;
;;hill        valley      cliff
;;
;;grassland   lowland
;;
;;outpost     swamp
;;

(def loc-dir-map {
  :t-3dba-xfce {:east :t-3dba-xbea, :south nil, :west nil, :north :t-3dba-xdab}
  :t-3dba-xbea {:east nil, :south nil, :west :t-3dba-xfce, :north :t-3dba-xcaf}
  :t-3dba-xcaf {:east nil, :south :t-3dba-xbea, :west :t-3dba-xdab, :north :t-3dba-xeff}
  :t-3dba-xdab {:east :t-3dba-xcaf, :south :t-3dba-xfce, :west nil, :north :t-3dba-xaaa}
  :t-3dba-xaaa {:east :t-3dba-xeff, :south :t-3dba-xdab, :west nil, :north nil}
  :t-3dba-xeff {:east :t-3dba-xfda, :south :t-3dba-xcaf, :west :t-3dba-xaaa, :north nil}
  :t-3dba-xfda {:east nil, :south nil, :west :t-3dba-xeff, :north nil}
  :t-9a3e-xc4d {:east :t-9a3e-x67e :north :t-9a3e-xfe4}
  :t-9a3e-x67e {:west :t-9a3e-xc4d :north :t-9a3e-xbd2}
  :t-9a3e-xfe4 {:south :t-9a3e-xc4d :east :t-9a3e-xbd2}
  :t-9a3e-xbd2 {:south :t-9a3e-x67e :west :t-9a3e-xfe4}
  :t-5fec-xaac {:east nil, :south :t-5fec-xdec, :west nil, :north nil}
  :t-5fec-xbee {:east nil, :south nil, :west :t-5fec-xdec, :north nil}
  :t-5fec-xdec {:east :t-5fec-xbee, :south :t-5fec-xdda, :west nil, :north :t-5fec-xaac}
  :t-5fec-xdda {:east nil, :south nil, :west nil, :north :t-5fec-xdec}
  :t-59aa-e4d2 {:west :t-59aa-ca57 :east :t-59aa-xadf :south :t-59aa-x2bc}
  :t-59aa-ca57 {:east :t-59aa-e4d2}
  :t-59aa-xadf {:west :t-59aa-e4d2 :south :t-59aa-xbdf}
  :t-59aa-xbdf {:north :t-59aa-xadf :west :t-59aa-x2bc}
  :t-59aa-x2bc {:north :t-59aa-e4d2 :east :t-59aa-xbdf}
  :t-2ea1-x9bc {:east :t-2ea1-x4a3}
  :t-2ea1-x4a3 {:west :t-2ea1-x9bc :south :t-2ea1-x67f}
  :t-2ea1-x67f {:north :t-2ea1-x4a3 :south :t-2ea1-xbc2}
  :t-2ea1-x3db {:west :t-2ea1-xbc2}
  :t-2ea1-xbc2 {:north :t-2ea1-x67f :east :t-2ea1-x3db}
  :t-67ec-xae5 {:east :t-67ec-x7f7 :south nil :west :t-67ec-x3a6 :north :t-67ec-xd34}
  :t-67ec-xd34 {:east nil :south :t-67ec-xae5 :west nil :north nil}
  :t-67ec-x3a6 {:east :t-67ec-xae5 :south nil :west nil :north nil}
  :t-67ec-xe7f {:east nil :south nil :west nil :north :t-67ec-x7f7}
  :t-67ec-x7f7 {:east nil :south :t-67ec-xe7f :west :t-67ec-xae5 :north nil}
})

(def Mabiangra-II (Planet. :lush :g-lkx :p-3dba "A lush planet. Rich in Magnetized Ferrite and Paraffinum" 30.9 0.1 0.1 2))

(def Luvocious-I (Planet. :hot :g-lkx :p-9a3e "A burning planet. Rich in Sulfur." 65.6 0.1 0.1 4))

(def Nudryorob (Planet. :exotic :g-lkx :p-59aa "A planet with no atmosphere. Rich in all kins of metal." 32.0 0.1 0.1 5))

(def Talandra-V (Planet. :cold :g-lkx :p-5fec "A cold planet. Rich in Carbon Dioxide and ferrite" -40.0 0.1 0.1 4))

(def Umis (Planet. :rad :g-lkx :p-2ea1 "A radioactive planet. Rich in Uranium" 20.0 6.3 0.3 4))

(def Pathavon (Planet. :tox :g-lkx :p-67ec "A toxic and mountainous planet. Rich in ammonia." 19.0 0.1 7.5 4))

(def Mabiangra-II-I (Tile. :normal :p-3dba :t-3dba-xfce :observatory-one
                           "An open ground with a Gek Observatory in the center.\nYou can see lots of plants around, but no animals.\nRocks on the ground seems to be rich of ferrite."
                           {:carbon 5 :di-hydro 3 :oxygen 3 :ferrite-dust 5 :paraff 2}
                           {:cond-carbon 2 :mag-ferrite 3 :sic-pow 3}))

(def Mabiangra-II-II (Tile. :normal :p-3dba :t-3dba-xbea :none
                            "This place seems to be full of flowers.\nFlowers on the ground are rich in oxygen."
                            {:carbon 3 :oxygen 4} {:mag-ferrite 3}))

(def Mabiangra-II-III (Tile. :monster :p-3dba :p-3dba-xcaf :none
                            "This is a dense forest.\nTrees grow so closely together that it is gloomy even at noon.\nThe fog that covers the depth of the forest forest makes it look creepier."
                            {:carbon 5 :oxygen 3 :paraff 2} {:cond-carbon 5}))

(def Mabiangra-II-IV (Tile. :normal :p-3dba :t-3dba-xdab :none
                            "An open grassland close to waters, with no trees or buildings in sight.\nPlants contain carbon and oxygen."
                            {:carbon 2 :di-hydro 1 :oxygen 2 :ferrite-dust 3 :sic-pow 2} {:cl 3}))

(def Mabiangra-II-V (Tile. :normal :p-3dba :t-3dba-xaaa :none
                            "A beautiful hilly landscape. Small traces of shiny stuff can be seen on the mountains."
                            {:di-hydro 3 :ferrite-dust 4 :paraff 3 :cu 1} {:mag-ferrite 3}))

(def Mabiangra-II-VI (Tile. :normal :p-3dba :t-3dba-xeff :korvax-at-mii-vi
                            "A small valley with a Korvax research center close to waters.\nA huge device sits at the middle of the building."
                            {:carbon 1 :oxygen 3 :ferrite-dust 2 :sic-pow 1} {:mag-ferrite 2}))

(def Mabiangra-II-VII (Tile. :normal :p-3dba :t-3dba-xfda :cliff-at-mii-vii
                            "A steep rock cliff.\nYou can see nothing here but stones."
                            {:ferrite-dust 2 :sic-pow 3} {:gold 2 :mag-ferrite 3}))

(def Luvocious-I-I (Tile. :normal :p-9a3e :t-9a3e-xfe4 :none
                           "A desert. The sands are even too hot to stand on."
                           {:sulfur 3 :carbon 1 :oxygen 1 :sic-pow 3 :cu 1} {:cob 2}))

(def Luvocious-I-II (Tile. :normal :p-9a3e :t-9a3e-xc4d :luvocious-i-ii
                           "A flatland with lots of plants. They all have small leaves and a red stem."
                           {:carbon 5 :sulfur 1 :oxygen 3} {:cu 1 :mag-ferrite 2}))

(def Luvocious-I-III (Tile. :normal :p-9a3e :t-9a3e-xbd2 :none
                            "An underground cave. Some weird plants are generating weird gas."
                            {:ammo 1, :sic-pow 2, :carbon 3, :oxygen 2} {:cu 3, :cob 2}))

(def Luvocious-I-IV (Tile. :normal :p-9a3e :t-9a3e-x67e :luvocious-i-iv
                           "A Korvax Transmission Tower stands at the center of a midland."
                           {:sulfur 3 :carbon 2 :oxygen 2 :sic-pow 1} {:cob 2 :mag-ferrite 2}))

(def Nudryorob-I (Tile. :exotic :p-59aa :t-59aa-e4d2 :none
                 "Barren Land with absolutely no sign of life.\nGround looks yellowish."
                 {:ferrite-dust 5 :cu 2 :ura 3} {:gold 3 :silver 2 :cu 3 :platinum 1 :cob 2}))

(def Nudryorob-II (Tile. :exotic :p-59aa :t-59aa-ca57 :none
                  "Barren Land with absolutely no sign of life.\nGround looks silverish."
                  {:ferrite-dust 5 :cob 2} {:gold 2 :silver 4 :cu 1 :platinum 3 :cob 2}))

(def Nudryorob-III (Tile. :exotic :p-59aa :t-59aa-xadf :none
                  "Barren Land with absoultely no sign of life.\nMost of the rocks have turned to dust."
                  {:ferrite-dust 5 :di-hydro 2} {:cu 2 :gold 1 :silver 1 :cob 1 :platinum 2 :mag-ferrite 2}))

(def Nudryorob-IV (Tile. :exotic :p-59aa :t-59aa-xbdf :none
                  "Barren Land with absoultely no sign of life.\nGround have traces of high temperature"
                  {:carbon 3 :ferrite-dust 5} {:chro 2 :cu 1 :gold 1 :silver 1 :mag-ferrite 1 :io-cob 3}))

(def Nudryorob-V (Tile. :exotic :p-59aa :t-59aa-x2bc :nudryorob-v
                        "Barrend Land on a high mountain, with a great view of the stars."
                        {:cu 2 :gold 3 :silver 3 :cob 2 :ferrite-dust 2} {:platinum 3 :io-cob 2}))

(def Talandra-V-I (Tile. :normal :p-5fec :t-5fec-xaac :none
                            "A rock cliff covered by ice."
                            {:di-hydro 2 :co2 3 :ferrite-dust 1} {:mag-ferrite 2 :sic-pow    1}))

(def Talandra-V-II (Tile. :normal :p-5fec :t-5fec-xbee :none
                            "A tundra landscape. Only moss and lichen grows here"
                            {:carbon 1 :co2 2 :ferrite-dust 1} {:sic-pow 1}))

(def Talandra-V-III (Tile. :normal :p-5fec :t-5fec-xdec :fortress
                            "An open ground with a huge man-made structure in the center.\nIt seems to be a shelter, but was deserted a long time ago.\nYou don't know who built it or what it was for."
                            {:co2 1 :ferrite-dust 3} {}))

(def Talandra-V-IV (Tile. :normal :p-5fec :t-5fec-xdda :none
                            "A glacier made of pure ice."
                            {:co2 5} {:sic-pow 2}))

(def Umis-I (Tile. :normal :p-2ea1 :t-2ea1-x9bc :none
                   "Maybe a grassland, but you hestitate to call these plants \"grass\"."
                   {:carbon 3 :ura 2 :ferrite-dust 2 :oxygen 2 :di-hydro 3} {:mag-ferrite 2}))

(def Umis-II (Tile. :normal :p-2ea1 :t-2ea1-x4a3 :none
                   "A plain desert, with dount-shaped ores in the middle."
                   {:ferrite-dust 5 :ura 5 :carbon 3 :oxygen 2} {:mag-ferrite 5}))

(def Umis-III (Tile. :normal :p-2ea1 :t-2ea1-x67f :umis-iii
                     "A trading outpost. Would anyone ever arrive here, given the radiation?"
                     {:ferrite-dust 2 :ura 2 :carbon 2 :oxygen 2 :sic-pow 2} {:mag-ferrite 1}))

(def Umis-IV (Tile. :normal :p-2ea1 :t-2ea1-x3db :umis-iv
                    "A research facility. Looks like this station is still staffed."
                    {:ura 2 :oxygen 2 :carbon 1 :sic-pow 1 :di-hydro 2} {:mag-ferrite 1}))

(def Umis-V (Tile. :normal :p-2ea1 :t-2ea1-xbc2 :none
                   "A rocky terrain, surrounded by mountains. There're lots of shiny minerals on the cliff."
                   {:ferrite-dust 5 :ura 5 :carbon 2 :sic-pow 5 :di-hydro 2}
                   {:silver 2 :gold 2 :mag-ferrite 2}))

(def Pathavon-I (Tile. :normal :p-67ec :t-67ec-xae5 :none
                   "A valley surrounded by mountains. A river flows through it."
                   {:carbon 1 :ammo 3 :di-hydro 2} {:sic-pow 2}))

(def Pathavon-II (Tile. :normal :p-67ec :t-67ec-xd34 :none
                   "A huge lake covered in mist."
                   {:carbon 1 :ammo 5 :di-hydro 3} {:sic-pow 1 :cond-carbon 3}))

(def Pathavon-III (Tile. :normal :p-67ec :t-67ec-x3a6 :none
                   "A hilly landscape."
                   {:carbon 3 :ammo 2 :di-hydro 1} {:sic-pow 2 :cond-carbon 1}))

(def Pathavon-IV (Tile. :normal :p-67ec :t-67ec-xe7f :none
                   "A high mountain with its peaks covered in snow."
                   {:ammo 2 :di-hydro 2} {:sic-pow 2}))

(def Pathavon-V (Tile. :normal :p-67ec :t-67ec-x7f7 :none
                   "A plateau with heavy seasonal rainfall."
                   {:carbon 1 :ammo 4 :di-hydro 2} {:sic-pow 3 :cond-carbon 3}))

(def loc-obj-map {
  :p-3dba Mabiangra-II
  :t-3dba-xfce Mabiangra-II-I
  :t-3dba-xbea Mabiangra-II-II
  :t-3dba-xcaf Mabiangra-II-III
  :t-3dba-xdab Mabiangra-II-IV
  :t-3dba-xaaa Mabiangra-II-V
  :t-3dba-xeff Mabiangra-II-VI
  :t-3dba-xfda Mabiangra-II-VII
  :p-9a3e Luvocious-I
  :t-9a3e-xfe4 Luvocious-I-I
  :t-9a3e-xc4d Luvocious-I-II
  :t-9a3e-xbd2 Luvocious-I-III
  :t-9a3e-x67e Luvocious-I-IV
  :p-59aa Nudryorob
  :t-59aa-e4d2 Nudryorob-I
  :t-59aa-ca57 Nudryorob-II
  :t-59aa-xadf Nudryorob-III
  :t-59aa-xbdf Nudryorob-IV
  :t-59aa-x2bc Nudryorob-V
  :p-5fec Talandra-V
  :t-5fec-xaac Talandra-V-I
  :t-5fec-xbee Talandra-V-II
  :t-5fec-xdec Talandra-V-III
  :t-5fec-xdda Talandra-V-IV
  :p-2ea1 Umis
  :t-2ea1-x9bc Umis-I
  :t-2ea1-x4a3 Umis-II
  :t-2ea1-x67f Umis-III
  :t-2ea1-x3db Umis-IV
  :t-2ea1-xbc2 Umis-V
  :p-67ec Pathavon
  :t-67ec-xae5 Pathavon-I
  :t-67ec-xd34 Pathavon-II
  :t-67ec-x3a6 Pathavon-III
  :t-67ec-xe7f Pathavon-IV
  :t-67ec-x7f7 Pathavon-V
})

(def planet-map {
    :p-3dba [:t-3dba-xfce :t-3dba-xbea :t-3dba-xcaf :t-3dba-xdab :t-3dba-xaaa :t-3dba-xeff :t-3dba-xfda]
    :p-9a3e [:t-9a3e-xfe4 :t-9a3e-xc4d :t-9a3e-xbd2 :t-9a3e-x67e]
    :p-59aa [:t-59aa-xadf :t-59aa-e4d2 :t-59aa-ca57 :t-59aa-x2bc :t-59aa-xbdf]
    :p-5fec [:t-5fec-xaac :t-5fec-xbee :t-5fec-xdec :t-5fec-xdda]
    :p-2ea1 [:t-2ea1-x3db :t-2ea1-x4a3 :t-2ea1-x9bc :t-2ea1-x67f :t-2ea1-xbc2]
    :p-67ec [:t-67ec-xae5 :t-67ec-xd34 :t-67ec-x3a6 :t-67ec-xe7f :t-67ec-x7f7]
})

(def tiles-with-exclusive #{
  :t-3dba-xfce :t-3dba-xeff :t-9a3e-xc4d :t-9a3e-x67e
  :t-59aa-x2bc :p-5fec-xdda :p-5fec-xdec :t-2ea1-x67f :t-2ea1-x3db
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