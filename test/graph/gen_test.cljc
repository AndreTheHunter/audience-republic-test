(ns graph.gen-test
  (:require
    [clojure.set :as set]
    [clojure.test :refer :all]
    [com.rpl.specter :as sp]
    [graph.gen :refer :all]))

(deftest make-graph-test
  (let [size (rand-int 10)
        graph (make-graph size (+ size (rand-int 10)))
        graph-keys (set (keys graph))]

    (testing "is of expected size"
      (is (= size
             (count graph))))

    (testing "has all vertices"
      (is (= (->> (range 1 (inc size))
                  (map (comp keyword str))
                  (set))
             graph-keys)))

    (testing "all nodes are connected"
      (let [destinations (->> graph
                              (sp/select [sp/MAP-VALS sp/ALL sp/FIRST])
                              (set))
            heads (set/difference graph-keys destinations)]
        (is (= graph-keys
               (set/union destinations heads)))))))