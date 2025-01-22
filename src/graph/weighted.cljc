(ns graph.weighted
  #?(:clj (:import (clojure.lang PersistentQueue))))

(defn- rec-seq [graph explored frontier]
  (lazy-seq
    (if (empty? frontier)
      nil
      (let [v (peek frontier)
            neighbors (map first (get graph v))]
        (cons v (rec-seq
                  graph
                  (into explored neighbors)
                  (into (pop frontier) (remove explored neighbors))))))))

(defn- seq-graph [empty-frontier graph start]
  (rec-seq graph #{start} (conj empty-frontier start)))

(def seq-graph-dfs (partial seq-graph []))
(def seq-graph-bfs (partial seq-graph #?(:clj PersistentQueue/EMPTY)))