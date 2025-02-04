(ns graph.path
  (:require
    [com.rpl.specter :as sp]
    [io.jesi.customs.spy :as spy]))

(defn- update-distances [graph distances unvisited curr]
  ;4. For the current node, consider all of its unvisited neighbors and update their
  ; distances through the current node; compare the newly calculated distance to the
  ; one currently assigned to the neighbor and assign the smaller one to it.
  (let [curr-cost (distances curr)]
    (spy/pprint curr curr-cost distances unvisited)
    (->> (graph curr)
         ;remove visited neighbours
         ;FIXME complete
         (sp/setval [sp/ALL])
         ;flatten parallel edges
         (reduce
           (fn [m [k cost]]
             (update m k (fnil min cost)))
           {})
         (reduce
           (fn [m [k cost]]
             (update m k min (+ curr-cost cost)))
           distances))))

(defn shortest-path [graph from to]
  (spy/pprint graph)
  ;1. Create a set of all unvisited nodes: the unvisited set.
  (loop [unvisited (set (keys graph))
         ;2. Assign to every node a distance from start value: for the starting node,
         ; it is zero, and for all other nodes, it is infinity, since initially no
         ; path is known to these nodes. During execution, the distance of a node N
         ; is the length of the shortest path discovered so far between the starting
         ; node and N
         distances (-> (zipmap (keys graph) (repeat ##Inf))
                       (assoc from 0))]
    ;3. From the unvisited set, select the current node to be the one with the smallest
    ; (finite) distance; initially, this is the starting node (distance zero).
    ; If the unvisited set is empty, or contains only nodes with infinite distance
    ; (which are unreachable), then the algorithm terminates by skipping to step 6.
    (spy/pprint unvisited distances)
    (if (empty? unvisited)
      (if (infinite? (distances to))
        nil
        ;6. Once the loop exits (steps 3–5), every visited node contains its shortest
        ; distance from the starting node.
        ;FIXME complete
        (let [distances (update-distances graph distances unvisited to)]
          (->> distances
               (sp/setval [sp/ALL (comp (partial < (distances to)) second)] sp/NONE)
               spy/ppeek
               (sort-by second)
               (mapv first))))
      (let [curr (first (sort-by distances unvisited))]
        (spy/prn curr)
        ;5. After considering all of the current node's unvisited neighbors,
        ; the current node is removed from the unvisited set. Thus a visited node
        ; is never rechecked, which is correct because the distance recorded on the
        ; current node is minimal (as ensured in step 3), and thus final. Repeat from step 3.
        (let [unvisited (disj unvisited curr)]
          (recur unvisited
                 (update-distances graph distances unvisited curr)))))))