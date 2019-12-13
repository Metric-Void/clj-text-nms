(ns clj-text-nms.repl
    (:gen-class)
    (:require [clj-text-nms.player :as player])
    (:require [clj-text-nms.text :as text])
    (:require [clojure.string :as cljstr])
    )

(defn craft
    [player]
    (do
        (println (text/msg-craftable player))
        player
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