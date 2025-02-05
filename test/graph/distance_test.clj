(ns graph.distance-test
  (:require
    [clojure.test :refer :all]
    [graph.distance :refer :all]))

(def graph {:1 [[:2 1] [:3 2]]
            :2 [[:4 4]]
            :3 [[:4 2]]
            :4 []})

(deftest eccentricity-test

  (testing "returns the maximum distance from the specified node"
    (is (= 2 (eccentricity graph :1)))
    (is (= 1 (eccentricity graph :2)))
    (is (= 1 (eccentricity graph :3)))
    (is (nil? (eccentricity graph :4)))))

(deftest radius-test

  (testing "returns the minimum eccentricity"
    (is (= 1 (radius graph)))))

(deftest diameter-test

  (testing "returns the maximum eccentricity"
    (is (= 2 (diameter graph)))))