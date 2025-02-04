(ns graph.path-test
  (:require
    [clojure.test :refer :all]
    [graph.path :refer :all]
    [io.jesi.customs.spy :as spy]))

(deftest ^:focus shortest-path-test
  (let [graph {:1 [[:2 1] [:3 2]]
               :2 [[:4 4]]
               :3 [[:4 2]]
               :4 []}]

    (testing "simple graph"
      (spy/enabled
        (is (= [:1 :3 :4] (shortest-path graph :1 :4))))
      (comment (is= [:2 :4] (shortest-path graph :2 :4))))

    (testing "unreachable"
      (is (= nil (shortest-path graph :4 :1)))
      (is (= nil (shortest-path graph :3 :1)))))

  ;FIXME test
  (comment (testing "circular graph"
             (let [graph {:1 [[:2 1] [:3 2]]
                          :2 [[:4 4] [:2 0]]
                          :3 [[:4 2] [:1 2]]
                          :4 []}]
               (spy/enabled
                 (is (= [:1 :3 :4] (shortest-path graph :1 :4)))))
             (comment (is (= [:2 :4] (shortest-path graph :2 :4))))))

  ;FIXME test
  (comment (testing "duplicate faster path"
             (let [graph {:1 [[:2 14] [:2 1] [:3 2]]
                          :2 [[:4 10] [:4 4]]
                          :3 [[:4 1]]
                          :4 []}]
               (spy/enabled
                 (is (= [:1 :3 :4] (shortest-path graph :1 :4)))))))

  ;FIXME test
  (comment (testing "least number of vertices when shortest paths")))