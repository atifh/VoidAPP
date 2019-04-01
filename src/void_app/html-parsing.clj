(ns void-app.html-parsing
  (:use [clojure.repl])
  (:require [clj-http.client :as client]
            [net.cgrand.enlive-html :as html]))


(defn get-page-title
  "Takes the dom of HTML and returns the page title"
  [dom]
  (first (:content (first (html/select dom [:title])))))

(defn get-all-images
  "Takes the dom of HTMl and returns all the image tags"
  [dom]
  (map (fn [elem] (:src (:attrs elem))) (html/select dom [:img])))

(defn get-content-from-url
  "Takes a URL and returns its content"
  [url]
  (try (client/get url)
       (catch Exception e
         (println "Failed to get content from url"))))
