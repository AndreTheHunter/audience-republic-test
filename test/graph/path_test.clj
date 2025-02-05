(ns graph.path-test
  (:require
    [clojure.test :refer :all]
    [graph.gen :refer [make-graph]]
    [graph.path :refer :all]
    [io.jesi.customs.spy :as spy]))

(deftest shortest-path-test
  (let [graph {:1 [[:2 1] [:3 2]]
               :2 [[:4 4]]
               :3 [[:4 2]]
               :4 []}]

    (testing "simple graph"
      (is (= [:1 :3 :4] (shortest-path graph :1 :4)))
      (is (= [:2 :4] (shortest-path graph :2 :4))))

    (testing "unreachable"
      (is (nil? (shortest-path graph :4 :1)))
      (is (nil? (shortest-path graph :4 :2)))
      (is (nil? (shortest-path graph :2 :3)))
      (is (nil? (shortest-path graph :3 :1)))))

  (testing "circular graph"
    (let [graph {:1 [[:2 1] [:3 2]]
                 :2 [[:4 4] [:2 0]]
                 :3 [[:4 2] [:1 2]]
                 :4 []}]
      (is (= [:1 :3 :4] (shortest-path graph :1 :4)))
      (is (= [:2 :4] (shortest-path graph :2 :4)))))

  (testing "duplicate faster path"
    (let [graph {:1 [[:2 14] [:2 1] [:3 2]]
                 :2 [[:4 10] [:4 4]]
                 :3 [[:4 1]]
                 :4 []}]
      (is (= [:1 :3 :4] (shortest-path graph :1 :4)))))

  (testing "least number of vertices when multiple shortest paths"
    (let [graph {:1 [[:2 1] [:3 1]]
                 :2 [[:3 1]]
                 :3 [[:4 1]]
                 :4 []}]
      (is (= [:1 :3 :4] (shortest-path graph :1 :4)))))

  (testing "generated graph"
    (spy/enabled
      (let [graph (make-graph)
            verts (vec (keys graph))
            from (rand-nth verts)
            to (rand-nth verts)]
        (spy/pprint graph from to)
        (spy/pprint (shortest-path graph from to))
        ;uncomment to see output
        (comment (is false))))))