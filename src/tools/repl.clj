(ns tools.repl
  (:require
   [clojure.tools.cli :as cli]
   [clojure.string :as str]
   [nrepl.server :as nrepl]
   [cider.nrepl :as ci]))

(def cli-options
  [["-p" "--port PORT" "Port number to start repl"
    :default 7888
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-f" "--fun FUNCTION" "Function to call after repl start"]])

(defn -main [& args]
  (let [{{p :port f :fun} :options :as opts} (cli/parse-opts args cli-options)]
    (nrepl/start-server :port p :handler ci/cider-nrepl-handler)
    (println "started at port " p)
    (when f
      (let [[*ns _] (str/split f #"\/")]
        (require `[~(symbol *ns)])
        (apply (resolve (symbol f)) [])))))
