(ns graph.gen-test
  (:require
    [clojure.math.numeric-tower :as math]
    [clojure.test :refer :all]
    [com.rpl.specter :as sp]
    [graph.gen :refer :all]
    [io.jesi.customs.strict :refer [thrown-with-msg?]])
  (:import
    (java.util.regex Pattern)))

(defn- test-make-graph [size sparseness]
  (testing (str "size: " size " sparseness: " sparseness)
    (let [graph (make-graph size sparseness)
          graph-keys (set (keys graph))]

      (testing "has the expected number of vertices"
        (is (= size (count graph))))

      (testing "has all vertices"
        (let [expected-vertices (->> (range 1 (inc size))
                                     (map (comp keyword str))
                                     (set))]
          (is (= expected-vertices graph-keys))))

      (testing "all nodes are connected"
        ;TODO check all the nodes https://www.geeksforgeeks.org/check-if-a-directed-graph-is-connected-or-not/
        (let [destinations (->> graph
                                (sp/select [sp/MAP-VALS sp/ALL sp/FIRST])
                                (set))
              edgeless (->> graph (sp/select [sp/ALL (comp empty? second) sp/FIRST]))]
          (is (every? destinations edgeless))))

      (when (pos-int? size)
        (testing "has the expected number of edges"
          (let [edge-count (->> graph
                                (sp/select [sp/MAP-VALS sp/ALL])
                                (count))]
            (is (= sparseness edge-count))))))))

(defn- ^Pattern re-literal [s]
  (Pattern/compile (Pattern/quote s)))

(deftest make-graph-test
  (test-make-graph 0 0)
  (test-make-graph 1 1)
  (test-make-graph 2 1)
  (test-make-graph 2 2)
  (test-make-graph 3 9)
  ;TODO generate test data
  (let [size (rand-int 10)
        sparseness (if (zero? size)
                     0
                     (min (rand-int 100)
                          (math/expt size (dec size))))]
    (test-make-graph size sparseness))

  (testing "invalid size"
    (is (thrown-with-msg? AssertionError (re-literal "Assert failed: (integer? size)") (make-graph 1.0 0)))
    (is (thrown-with-msg? AssertionError (re-literal "Assert failed: (<= 0 size)") (make-graph -1 0))))

  (testing "invalid sparseness"
    (is (thrown-with-msg? AssertionError (re-literal "Assert failed: (integer? sparseness)") (make-graph 1 1.0)))
    (is (thrown-with-msg? AssertionError (re-literal "Assert failed: (<= 0 sparseness)") (make-graph 1 -1)))
    (is (thrown-with-msg? AssertionError (re-literal "Assert failed: (<= (dec size) sparseness)") (make-graph 2 0)))
    (is (thrown-with-msg? AssertionError (re-literal "Assert failed: (<= sparseness (math/pow size (dec size)))") (make-graph 2 3)))))