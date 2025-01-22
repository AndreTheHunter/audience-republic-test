(ns graph.gen
  #?(:clj (:import (clojure.lang PersistentTreeMap))))

(defn make-graph [size sparseness]
  (let [vertices (->> (range 1 (inc size))
                      (mapv (comp keyword str)))
        edges (volatile! #{})
        add-edge (fn [from to]
                   (vswap! edges conj [from to (rand-int 10)]))]
    ;; Ensure the graph is connected
    (doseq [[from to] (partition 2 1 vertices)]
      (add-edge from to))
    ;; Add additional edges randomly
    (while (< (count @edges) sparseness)
      (let [from (rand-nth vertices)
            to (rand-nth (remove #{from} vertices))]
        (add-edge from to)))
    ;; Convert edges to adjacency list
    (reduce (fn [graph [from to weight]]
              (update graph from (fnil conj []) [to weight]))
            (sorted-map)
            @edges)))