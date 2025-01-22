(ns graph.gen-test
  (:require
    [clojure.test :refer [deftest is]]
    [graph.gen :refer :all]))

(deftest make-graph-test
  (let [size 5]
    (is (= size (count (make-graph size size)))))
  (is (= {:1 [:2 nil]
          :2 []}
         (make-graph 2 2))))