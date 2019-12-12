(ns clj-text-nms.core
  (:gen-class)
  (:require [clj-text-nms.repl :as repl])
  (:require [clj-text-nms.player :as player])
  (:require [clj-text-nms.help :as help])
  (:require [clj-text-nms.logic :as logic])
  (:require [clj-text-nms.map :as map])
  (:require [clj-text-nms.text :as text]))

(defn -main
  "A Text version of NMS written in Clojure. Let's begin!"
  [& args]
  (println "Hello, World!"))
