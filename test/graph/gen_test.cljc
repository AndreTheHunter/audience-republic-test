(ns graph.gen-test
  (:require
    [com.rpl.specter :as sp]
    [graph.gen :refer :all]
    [io.jesi.customs.spy :as spy]
    [io.jesi.customs.strict :refer [deftest is= testing]]))


(deftest make-graph-test
  (spy/enabled
    (let [size (inc (rand-int 10))
          graph (make-graph size (+ size (rand-int 10)))
          graph-keys (set (keys graph))]

      (testing "is of expected size"
        (is= size (count graph)))

      (testing "has all vertices"
        (is= (->> (range 1 (inc size))
                  (map (comp keyword str))
                  (set))
             graph-keys))

      (testing "all nodes are connected"
        ;FIXME not sure this test is correct
        (is= graph-keys
             (->> graph
                  (sp/select [sp/MAP-VALS sp/ALL sp/FIRST])
                  (set)))))))