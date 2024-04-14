(ns penpot.main
  (:require [reagent.core :as r]
            ["react-dom/client" :as rdom]
            [stylefy.core :as stylefy :refer [use-style]]
            [stylefy.reagent :as stylefy-reagent])
  (:require-macros [penpot.generator :as generator])
  )

(stylefy/init {:dom (stylefy-reagent/init)})
(generator/defversion)
(generator/deftokens)
(generator/defbutton)

(defn page
  []
  [:div (tokens)]
  [:main
   [:h1 "Daisy dev page"]

   [:section
    [:h2 "version"]
    [:p version]
    ]

   [:section
    [:h2 "tokens"]
    [:ul
     [:li
      [:p.ds-color-blue  "should be blue (class)"]
      [:p (use-style {:color "var(--ds-blue)"})  "should be blue (var)"]]
     [:li
      [:p.ds-color-green "should be green (class)"]
      [:p (use-style {:color "var(--ds-green)"})  "should be green (var)"]]]]

   [:section
    [:h2 "components"]
    [:ul
     [:li [Button]]]]

   ])

(defonce root (rdom/createRoot (.getElementById js/document "app")))

(defn render!
  [& args]
  (.render root (r/as-element [page])))

#_(render!)
