(ns clj-text-nms.repl
    (:gen-class)
    (:require [clj-text-nms.player :as player])
    (:require [clj-text-nms.logic :as logic])
    (:require [clj-text-nms.text :as text])
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
        (do (printf "%s >>" prompt)
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
                    (if (= (cljstr/lower-case (subs (name input1) 0 1)) "f")
                        state
                        (recur
                            state
                            "Invalid input. Enter a number or [f]inish crafting."
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
            [player   (player/new-player)
             newstate true]
            
            (do (when newstate
                    (do
                        (println "=============================================================")
                        (println (text/current-state player))
                        )
                    )
                (println text/options)
                (let [input (read-with-inst)]
                    (if (symbol? input)
                        (case (cljstr/lower-case (subs (name input) 0 1))
                            "m" (recur (player/tick-planet (player/mine player)) true)
                            "s" (do
                                    (println (text/msg-inventory player))
                                    (recur player false)
                                    )
                            "c" (recur (player/tick-planet (craft player)) true)
                            "b" (recur (player/estab-base player) true)
                            "q" (println "See you again.")
                            (do (println "Invalid input.")
                                (recur player false)
                                )
                            )
                        (do (println "Invalid input.")
                            (recur player false)
                            )
                        )
                    )
                )
            )
        )
    )