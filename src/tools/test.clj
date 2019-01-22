(ns tools.test
  (:require
   [cognitect.test-runner :as test]))

(defn -main [& args]
  (apply test/-main args))
