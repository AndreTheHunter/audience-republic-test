(ns graph.distance
  (:require
    [graph.path :refer [shortest-path]]
    [taoensso.encore :refer [assoc-some]]))

(defn eccentricity [graph from]
  (let [destinations (-> (keys graph)
                         (set)
                         (disj from))
        distances (->> destinations
                       (reduce (fn [distances to]
                                 (assoc-some distances to (some-> (shortest-path graph from to)
                                                                  (count)
                                                                  (dec))))
                               {}))]
    (when (seq distances)
      (apply max (vals distances)))))

(defn radius [graph]
  (->> (keys graph)
       (map (partial eccentricity graph))
       (remove nil?)
       (apply min)))

(defn diameter [graph]
  (->> (keys graph)
       (map (partial eccentricity graph))
       (remove nil?)
       (apply max)))