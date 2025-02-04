(ns core
  (:require
    [graph.unweighted]
    [graph.weighted]))

;FIXME repl should start here so that examples can be run

(def seq-graph-dfs graph.unweighted/seq-graph-dfs)
(def seq-graph-bfs graph.unweighted/seq-graph-bfs)
(def seq-weight-graph-dfs graph.weighted/seq-graph-dfs)
(def seq-weight-graph-bfs graph.weighted/seq-graph-bfs)