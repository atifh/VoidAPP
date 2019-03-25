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
  [to-email, subject, body]
  (if (is-valid-email to-email)
    (let [resp (send-message conn {:from from-email
                                   :to to-email
                                   :subject subject
                                   :body body})]
      (println resp))
    (println "Invalid email")))


;; Sample call: (send-email "mail@atifhaider.com" "Testing email" "Sending email via Clojure code")
