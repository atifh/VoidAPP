(ns void-app.validations
  (:use [clojure.repl]))

(def email-regex-pattern
  #"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")

(defn is-valid-email
  "Checks whether the given email is valid or not"
  [email]
  (re-matches email-regex-pattern email))
