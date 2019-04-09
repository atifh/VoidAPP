(ns void-app.core
  (:use [clojure.repl]) ;; To access doc
  (:require [environ.core :refer [env]]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [postal.core :refer [send-message]]
            [net.cgrand.enlive-html :as html]
            [void-app.validations :refer :all]
            [void-app.html-parsing :as hparse]))

(def user (env :smtp-user))
(def pass (env :smtp-password))
(def host (env :smtp-host))
(def from-email (env :from-email))

(def conn {:host host :port 465 :ssl true :user user :pass pass})

(defn send-email
  "A top level function to send email using Postal"
  [to-email subject body]
  (if (is-valid-email to-email)
    (try (send-message conn {:from from-email
                             :to to-email
                             :subject subject
                             :body body})
         (catch Exception e (println "Sending email failed with this error" e)))
    (println "Invalid email")))

;; Sample call
;; (send-email "mail@atifhaider.com" "Testing email" "Sending email via Clojure code")

(defn send-email-enqueue
  "Sends asynchronous email using Java threads"
  [to-email subject body]
  (Thread. (fn []
             ;; this will run in a new Thread
             (send-email to-email subject body))))

;; Sample threaded email call
;; (.start (send-email-enqueue "mail@atifhaider.com" "Testing email" "Sending email via Clojure code"))

(defn parse-website
  "Takes a URL and fetches the content of the web page
  and returns page-title, page-meta-description and
  all the images available in that page"
  [url]
  (if (not (is-valid-url url))
    "Invalid url" ;; returns when the url is invalid
    (when-let [response (hparse/get-content-from-url url)]
    (let [dom (html/html-snippet (:body response))]
      {:title (hparse/get-page-title dom)
       :images (hparse/get-all-images dom)}))))

;; Sample call: (parse-website "http://atifhaider.com")

(defn read-csv-file
  "Takes a path of the csv file and returns
  a sequence."
  [file-path]
  (with-open [reader (io/reader file-path)]
    (let [csv-data (csv/read-csv reader)]
      (println csv-data)
      ;; courtesy: https://github.com/clojure/data.csv#example-usage
      (map zipmap
           (->> (first csv-data)
                (map keyword)
                repeat)
           (rest csv-data)))))

;; Sample call: (read-csv-file "test.csv")
