(ns tools.config
  (:require
   [clojure.string :as str]))

(defn to-edn-name [k]
  (-> k
      str/lower-case
      (str/replace "_" "-")
      keyword))

(defn *get [cfg]
  (let [envs
        (->> (System/getenv)
             (map (fn [[k v]]
                    [(to-edn-name k) v]))
             (into {}))]
    (->> cfg
         (map (fn [[opt-name {:keys [default required parse-fn]}]]
                (let [v (get envs opt-name default)]
                  (if (and required (nil? v))
                    (throw (Exception. (str "Required env var " opt-name " is not supplied")))
                    [opt-name (cond-> v parse-fn parse-fn)]))))
         (into {})
         (merge envs)
         doall)))
