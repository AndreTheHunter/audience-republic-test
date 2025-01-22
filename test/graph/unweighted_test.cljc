(ns graph.unweighted-test
  (:require [clojure.test :refer [deftest is]]
            [graph.unweighted :refer :all]))

(def graph {:1 [:2 :3]
            :2 [:4]
            :3 [:4]
            :4 []})

(deftest seq-graph-dfs-test
  (is (= [:1 :3 :4 :2] (seq-graph-dfs graph :1)))
  (is (= [:2 :4] (seq-graph-dfs graph :2)))
  (is (= [:3 :4] (seq-graph-dfs graph :3)))
  (is (= [:4] (seq-graph-dfs graph :4))))

(deftest seq-graph-bfs-test
  (is (= [:1 :2 :3 :4] (seq-graph-bfs graph :1))))