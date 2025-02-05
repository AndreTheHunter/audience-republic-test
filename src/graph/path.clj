(ns graph.path
  (:require
    [com.rpl.specter :as sp]))

(defn shortest-path [graph from to]
  ;based on https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Pseudocode
  (loop [unvisited (set (keys graph))
         distances (-> (zipmap (keys graph) (repeat ##Inf))
                       (assoc from 0))
         prevs {}]
    (if (empty? unvisited)
      (when-not (infinite? (distances to))
        ; traverse back
        (loop [path '()
               curr to]
          (let [path (conj path curr)]
            (if (or (nil? curr) (= curr from))
              path
              (recur path
                     (prevs curr))))))
      (let [curr (first (sort-by distances unvisited))
            curr-distance (distances curr)
            [distances prevs] (->> (graph curr)
                                   ;remove visited neighbours
                                   (sp/setval [sp/ALL (comp not unvisited first)] sp/NONE)
                                   ;update distances
                                   (reduce (fn [[distances prevs :as v] [nbr distance]]
                                             (let [new-distance (+ curr-distance distance)]
                                               (if (< new-distance (distances nbr))
                                                 [(assoc distances nbr new-distance)
                                                  (assoc prevs nbr curr)]
                                                 v)))
                                           [distances prevs]))
            unvisited (disj unvisited curr)]
        (recur unvisited distances prevs)))))