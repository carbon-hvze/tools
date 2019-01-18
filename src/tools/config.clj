(ns tools.config
  (:require
   [clojure.string :as str]))

(defn to-env [k]
  (-> k
      name
      str/upper-case
      (str/replace  "-" "_")))

(defn *get [cfg]
  (->> cfg
       (map (fn [[opt-name {:keys [default required parse-fn]}]]
              (let [v (or (System/getenv (to-env opt-name))
                          default)]
                (if (and required (nil? v))
                  (throw (Exception. (str "Required env var " opt-name " is not supplied")))
                  [opt-name (cond-> v
                              parse-fn parse-fn)]))))
       (into {})))
