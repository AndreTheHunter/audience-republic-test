(ns graph.gen-test
  (:require
    [clojure.set :as set]
    [clojure.test :refer :all]
    [com.rpl.specter :as sp]
    [graph.gen :refer :all]
    [io.jesi.customs.spy :as spy]))

(deftest make-graph-test
  (spy/enabled
    (let [size (rand-int 10)
          sparseness (max 0 (+ (dec size) (rand-int 10)))
          _ (spy/prn size sparseness)
          graph (spy/ppeek (make-graph size sparseness))
          graph-keys (set (keys graph))]

      (testing "is of expected size"
        (is (= size (count graph))))

      (testing "has all vertices"
        (let [expected-vertices (->> (range 1 (inc size))
                                     (map (comp keyword str))
                                     (set))]
          (is (= expected-vertices graph-keys))))

      (testing "all nodes are connected"
        (let [destinations (->> graph
                                (sp/select [sp/MAP-VALS sp/ALL sp/FIRST])
                                (set))
              heads (set/difference graph-keys destinations)]
          (is (= graph-keys (set/union destinations heads)))))

      (testing "has the expected number of edges"
        (let [expected (if (pos-int? size) sparseness 0)
              edge-count (->> graph
                              (sp/select [sp/MAP-VALS sp/ALL])
                              (count))]
          (is (= expected edge-count)))))))