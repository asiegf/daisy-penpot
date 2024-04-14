(ns penpot.generator
  (:require [org.httpkit.client :as http]
            [cognitect.transit :as transit]
            [clojure.data.json :as json]
            [clojure.java.io :as io :refer :all]
            [clojure.string :as s]
            [clojure.data :as d]
            [clojure.set :as set])
  (:import [java.io ByteArrayInputStream ByteArrayOutputStream]))

(def config
  {:version "0.0.1"})

(defmacro defversion
  []
  `(def ^{:export true} ~'version ~(:version config))
  )



(defmacro defbutton
  []
  `(def
     ^{:export true}
     ~'Button
     (fn [] (~'r/reactify-component ~'(fn [& args] [:button "Daisy button"])))))



;; (read-string (slurp ".env.edn"))


(defn get-penpot
  [{:keys [penpot-access-token file-id]}]
  (let [byte-array
        @(http/get "https://design.penpot.app/api/rpc/command/get-file"
                   {:as          :byte-array
                    :headers     {"Authorization" (str "Token " penpot-access-token)}
                    :form-params {:id file-id}}
                   :body)
        in     (ByteArrayInputStream. byte-array)
        reader (transit/reader in :json)]
    (transit/read reader)))

(def get-penpot* (memoize get-penpot))


(defn parse-color-tokens
  [colors]
  (reduce (fn [m {:keys [name color]}]
            (assoc m name color))
          {}
          (vals colors)))



(defmacro deftokens
  []
  (let [color-tokens
        (parse-color-tokens (-> (get-penpot (read-string (slurp ".env.edn")))
                                :data
                                :colors))
        vars
        `(~'stylefy/tag
          ":root"
          ~(reduce-kv (fn [m k v] (assoc m (keyword (str "--ds-" k)) v))
                      {}
                      color-tokens))

        classes
        (->> color-tokens
             (reduce-kv (fn [m k v]
                          (assoc m
                                 (str "ds-color-" k) {:color v}
                                 (str "ds-bg-" k)    {:background-color v}))
                        {})
             (map (fn [[k v]] `(~'stylefy/class ~k ~v))))]
    `(defn ^{:export true}
       ~'tokens
       []
       ~(cons 'do (cons '(js/console.log "[daisy] applying tokens")
                        (cons vars classes)))
       )))


(comment
  (macroexpand-1 '(defversion))
  ;; => (def version "0.0.1")

  (macroexpand-1 '(deftokens))
  (clojure.core/defn tokens [] (do (js/console.log "[daisy] applying tokens") (stylefy/tag ":root" {"--ds-blue" "#575f95", "--ds-green" "#64765a"}) (stylefy/class ".ds-color-blue" {:color "#575f95"}) (stylefy/class ".ds-bg-blue" {:background-color "#575f95"}) (stylefy/class ".ds-color-green" {:color "#64765a"}) (stylefy/class ".ds-bg-green" {:background-color "#64765a"})))

  (macroexpand-1 '(defbutton))
  ;; => (def Button (clojure.core/fn [] (r/reactify-component (fn [& args] [:button "Daisy button"]))))
  )
