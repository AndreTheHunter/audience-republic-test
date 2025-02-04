(defproject andrethehunter/audience-republic-test "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [com.rpl/specter "1.1.4"]
                 [io.jesi/backpack "7.6.0"]
                 [org.flatland/ordered "1.15.12"]]
  :repl-options {:init-ns test.repl}
  :profiles {:dev {:dependencies [[org.slf4j/slf4j-simple "1.7.30"]
                                  [io.jesi/customs "1.3.3"]
                                  [lambdaisland/kaocha "1.91.1392"]
                                  [org.clojure/math.numeric-tower "0.0.5"]]}}
  :aliases {"kaocha" ["with-profile" "+dev" "run" "-m" "kaocha.runner"]})