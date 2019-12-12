(ns clj-text-nms.text
    (:gen-class)
    (:require [clojure.string :as str])
    )

(defn str-inventory
    [state]
    (loop
        [nums   (vals (:inventory state))
         items  (keys (:inventory state))
         msg    "You have"]
        
        (if (empty? nums)
            (str msg ".")
            (recur
                (rest nums)
                (rest items)
                (if (> (first nums) 1)
                    (str msg " " (name (first keys)) "(s)")
                    (str msg " " (name (first keys)))
                    )
                )
            )
        )
    )