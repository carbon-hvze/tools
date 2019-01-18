(ns tools.build
  (:require
   [mach.pack.alpha.capsule :as pack]))

(defn -main [& args]
  (apply pack/-main args))
