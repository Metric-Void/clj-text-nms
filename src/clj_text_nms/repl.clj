(ns clj-text-nms.repl
    (:gen-class)
    (:require [clj-text-nms.explore :as explore])
    (:require [clj-text-nms.player :as player])
    (:require [clj-text-nms.logic :as logic])
    (:require [clj-text-nms.text :as text])
    (:require [clj-text-nms.help :as help])
    (:require [clj-text-nms.map :as map])
    (:require [clojure.string :as cljstr])
    )

(defn read-with-inst
    ([]
        (do (printf ">>")
            (flush)
            (read)
            )
        )
    ([prompt]
        (do (printf "%s >> " prompt)
            (flush)
            (read)
            )
        )
    )


(defn craft
    [player]
    (loop
        [state player
         msg   (text/msg-craftable player)
         cftb  (logic/craftable player)]
        (do (println msg)
            (let [input1 (read-with-inst)]
                (if (integer? input1)
                    (if (or (< input1 0) (>= input1 (count cftb)))
                        (recur
                            state
                            "Invalid input. Enter a legit number."
                            cftb
                            )
                        (let [new-state
                            (let [recipe (nth cftb input1)]
                                (loop
                                    [input2 (do (println "How many do you want? (You can [c]ancel crafting this item.)")
                                                (read-with-inst))]
                                    (if (integer? input2)
                                        (if (< input2 0)
                                            (do
                                                (println "Invalid amount.")
                                                (recur (read-with-inst))
                                                )
                                            (let [try-mine (text/msg-craft-cost state recipe input2)]
                                                (let [  flag (first try-mine)
                                                        msg2 (second try-mine)]
                                                    
                                                    (do (println msg2)
                                                        (if flag
                                                            (let [input3 (read-with-inst)]
                                                                (if (= (cljstr/lower-case (subs (name input3) 0 1)) "y")
                                                                    (player/craft state recipe input2)
                                                                    (recur
                                                                        (do (println "How many do you want? (You can [c]ancel crafting this item.)")
                                                                            (read-with-inst)
                                                                            )
                                                                        )
                                                                    )
                                                                )
                                                            (recur (read-with-inst))
                                                            )
                                                        )
                                                    )
                                                )
                                            )
                                        (if (= (cljstr/lower-case (subs (name input2) 0 1)) "c")
                                            nil
                                            (do
                                                (println "Invalid input")
                                                (recur (read-with-inst))
                                                )
                                            )
                                        )
                                    )
                                )
                            ]
                            (if (nil? new-state)
                                (recur
                                    state
                                    (text/msg-craftable state)
                                    cftb
                                    )
                                (recur
                                    new-state
                                    (text/msg-craftable new-state)
                                    (logic/craftable new-state)
                                    )
                                )
                            )
                        )
                    (if (and (symbol? input1) (= (cljstr/lower-case (subs (name input1) 0 1)) "f"))
                        state
                        (recur
                            state
                            "Invalid input. Enter an integer or [f]inish crafting."
                            cftb
                            )
                        )
                    )
                )
            )
        )
    )

(defn teleport
    [player]
    (if (< (:fuel (:ship player)) 25)
        (do
            (println "You don't have enough fuel to take off.")
            [player false]
            )
        (let [input (read-with-inst text/prompt-teleport)]
            (if (symbol? input)
                (let [initial (cljstr/lower-case (subs (name input) 0 1))]
                    (if (not (or (= initial "p") (= initial "t")))
                        (do
                            (println "Invalid input.")
                            [player false]
                            )
                        (do
                            (println (text/msg-teleport-dest player initial))
                            (let [vec-locs (vec (:map-locs player))
                                input2  (read-with-inst "[C]ancel or teleport to")]
                                (cond
                                    (symbol? input2)
                                        (do
                                            (if (= (cljstr/lower-case (subs (name input2) 0 1)) "c")
                                                (println "Teleport canceled.")
                                                (println "Invalid input.")
                                                )
                                            [player false]
                                            )
                                    (number? input2)
                                        (if (or (< input2 0)
                                                (> input2 (case initial
                                                            "p" (count map/planet-map)
                                                            "t" (count (:map-locs player))
                                                            )
                                                )
                                                )
                                            (do
                                                (println "Invalid input.")
                                                [player false]
                                                )
                                            (do
                                                (println "Teleport succeeded.")
                                                (case initial
                                                    "p" [(player/teleport-planet player (nth (keys map/planet-map) input2)) true]
                                                    "t" [(player/teleport-tile   player (nth vec-locs    input2)) true]
                                                    )
                                                )
                                            )
                                    :else (do
                                            (println "Invalid input.")
                                            [player false]
                                            )
                                    )
                                )
                            )
                        )
                    )
                (do
                    (println "Invalid input.")
                    [player false]
                    )
                )
            )
        )
    )

(defn move
    [player]
    (do
        (printf "%s\n%s\n" text/dividing-line text/msg-move)
        (let [input (read-with-inst)]
            (case (cljstr/lower-case (subs (name input) 0 1))
                "n" (player/tick-planet (player/move player :north))
                "w" (player/tick-planet (player/move player :west))
                "s" (player/tick-planet (player/move player :south))
                "e" (player/tick-planet (player/move player :east))
                (do
                    (println "Invalid direction")
                    player
                    )
                )
            )
        )
    )

(defn help
    []
    (do
        (println text/msg-help)
        (let [input (read-with-inst "Enter the topic you are interest in")]
            (if (symbol? input)
                (let [help-key (keyword input)]
                    (if (nil? (help-key help/helps))
                        (println "We don't have help on that topic.")
                        (printf "%s\n%s\n%s\n" text/dividing-line (help-key help/helps) text/dividing-line)
                        )
                    )
                (println "Invalid input.")
                )
            )
        )
    )

(defn new-game
    []
    (do (println text/op)
        (loop
            [player       (player/new-player)
             newstate     true
             continue     false]

            (let [on-ship-tile (= (:tile player) (:tile (:ship player)))
                  win-state    (not (nil? (:heart-of-the-sun (:inventory player))))]
                
                (if (< (:hp player) 0)
                    (do
                        (println text/msg-dead)
                        (println text/credit)
                        )
                    (do 
                        (if (and win-state (not continue))
                            (do
                                (println text/msg-win)
                                (println text/credit)
                                (println "You may continue to play if you wish.")
                                (recur player true true)
                                )
                            (do (when newstate
                                    (do
                                        (println "=============================================================")
                                        (println (text/current-state player))
                                        (when on-ship-tile
                                            (println "You are on the same tile with your spaceship.")
                                            )
                                        )
                                    )
                                (println "You can")
                                (when on-ship-tile
                                    (println text/ship-tile-option)
                                    )
                                (println text/options)
                                (let [input (read-with-inst)]
                                    (if (symbol? input)
                                        (case (cljstr/lower-case (subs (name input) 0 1))
                                            "s" (do
                                                    (player/scan player)
                                                    (recur (player/tick-planet player) true continue)
                                                    )
                                            "e" (do
                                                    (println text/dividing-line)
                                                    (recur (player/tick-planet (explore/explore player)) true continue)
                                                    )
                                            "g" (recur (move player) true continue)
                                            "m" (recur (player/tick-planet (player/mine player)) true continue)
                                            "v" (do
                                                    (println (text/msg-inventory player))
                                                    (recur player false continue)
                                                    )
                                            "c" (recur (player/tick-planet (craft player)) true continue)
                                            "b" (recur (player/estab-base player) true continue)
                                            "r" (recur (player/recharge-life-support player) true continue)
                                            "q" (println "See you again.")
                                            "t" (if (not on-ship-tile)
                                                    (do (println "You need to be by your spaceship to teleport.")
                                                        (recur player false continue)
                                                        )
                                                    (let [result (teleport player)]
                                                        (recur (first result) (second result) continue)
                                                        )
                                                    )
                                            "a" (if (not on-ship-tile)
                                                    (do (println "You need to be by your spaceship to add fuel.")
                                                        (recur player false continue)
                                                        )
                                                    (recur (player/tick-planet (player/recharge-ship-fuel player)) true continue)
                                                    )
                                            "h" (do
                                                    (help)
                                                    (recur player false continue)
                                                    )
                                            (do (println "Invalid input.")
                                                (recur player false continue)
                                                )
                                            )
                                        (do (println "Invalid input.")
                                            (recur player false continue)
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    )