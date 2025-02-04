(ns graph.gen
  (:require
    [clojure.math :as math]))

(defn make-graph
  ([] (make-graph 10))
  ([size] (make-graph size (dec size)))
  ([size sparseness]
   {:pre [(integer? size)
          (<= 0 size)
          (integer? sparseness)
          (<= 0 sparseness)
          (<= (dec size) sparseness)
          (<= sparseness (math/pow size (dec size)))]}
   (let [vertices (->> (range 1 (inc size))
                       (shuffle)
                       (mapv (comp keyword str)))
         edges (volatile! [])
         add-edge (fn [from to]
                    (vswap! edges conj [from to (rand-int 10)]))]
     ;TODO consider adding connected and random edges in parallel
     ;Ensure the graph is connected
     (doseq [[from to] (partition 2 1 vertices)]
       (add-edge from to))
     ;Add additional edges randomly
     (when (pos-int? size)
       (dotimes [_ (- sparseness (count @edges))]
         (let [from (rand-nth vertices)
               to (rand-nth vertices)]
           (add-edge from to))))
     ;Convert edges to graph map
     (let [graph (->> vertices
                      (map #(vector % []))
                      (into (sorted-map)))]
       (reduce (fn [graph [from to weight]]
                 (update graph from conj [to weight]))
               graph
               @edges)))))