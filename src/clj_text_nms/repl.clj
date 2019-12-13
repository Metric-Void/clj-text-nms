(ns clj-text-nms.repl
    (:gen-class)
    (:require [clj-text-nms.player :as player])
    (:require [clj-text-nms.logic :as logic])
    (:require [clj-text-nms.text :as text])
    (:require [clojure.string :as cljstr])
    )

(defn craft
    [player]
    (loop
        [state player
         msg   (text/msg-craftable player)
         cftb  (logic/craftable player)]
        (do (println msg)
            (let [input1 (read)]
                (if (integer? input1)
                    (if (or (< input1 0) (>= input1 (count cftb)))
                        (recur
                            state
                            "Invalid input. Enter a legit number.\n"
                            cftb
                            )
                        (let [new-state
                            (let [recipe (nth cftb input1)]
                                (loop
                                    [input2 (read)]
                                    (if (integer? input2)
                                        (if (< input2 0)
                                            (do
                                                (println "Invalid amount.\n")
                                                (recur (read))
                                                )
                                            (let [try-mine (text/msg-craft-cost state recipe input2)]
                                                (let [  flag (first try-mine)
                                                        msg2 (second try-mine)]
                                                    
                                                    (do (println msg2)
                                                        (if flag
                                                            (let [input3 (read)]
                                                                (if (= (cljstr/lower-case (subs (name input3) 0 1)) "y")
                                                                    (player/craft state recipe input2)
                                                                    (recur (read))
                                                                    )
                                                                )
                                                            (recur (read))
                                                            )
                                                        )
                                                    )
                                                )
                                            )
                                        (if (= (cljstr/lower-case (subs (name input2) 0 1)) "c")
                                            nil
                                            (do
                                                (println "Invalid input\n")
                                                (recur (read))
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
                    (if (= (cljstr/lower-case (subs (name input1) 0 1)) "f")
                        state
                        (recur
                            state
                            "Invalid input. Enter a number or [f]inish crafting.\n"
                            cftb
                            )
                        )
                    )
                )
            )
        )
    )

(defn new-game
    []
    (do (println text/op)
        (loop
            [player (player/new-player)]
            
            (do (println text/current-state)
                (println text/options)
                (let [input (read)]
                    (case (cljstr/lower-case (subs (name input) 0 1))
                        "m" (recur (player/mine player))
                        "s" (do
                                (println (text/msg-inventory player))
                                (recur player)
                                )
                        "c" (recur (craft player))
                        "q" "See you again."
                        )
                    )
                )
            )
        )
    )