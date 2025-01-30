(ns test.repl
  (:require
    [kaocha.repl :as k]))

(defn run-tests []
  (k/run :unit))