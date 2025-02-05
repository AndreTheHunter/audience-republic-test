(ns test.repl
  (:require
    [graph.core :refer :all]
    [kaocha.repl :as k]))

(defn run-tests []
  (k/run :unit))