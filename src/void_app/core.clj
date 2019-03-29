(ns void-app.core
  (:use [clojure.repl]) ;; To access doc
  (:require [environ.core :refer [env]]
            [postal.core :refer [send-message]]
            [void-app.validations :refer [is-valid-email]]))

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

(defn send-email-enqueue
  [to-email subject body]
  "Sends asynchronous email using Java threads"
  (Thread. (fn []
             ;; this will run in a new Thread
             (send-email to-email subject body))))

;; Sample call: (send-email "mail@atifhaider.com" "Testing email" "Sending email via Clojure code")
;; Sample threaded email call: (.start (send-email-enqueue "mail@atifhaider.com" "Testing email" "Sending email via Clojure code"))
