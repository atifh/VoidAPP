(defproject void-app "0.0.1"
  :description "VoidAPP is a utility app written in Clojure that contains your daily-need functions."
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.draines/postal "2.0.3"]
                 [clj-http "3.9.1"]
                 [environ "1.1.0"]
                 [enlive "1.1.6"]]
  :target-path "target/%s"
  :repl-options {:init-ns void-app.core
                 :welcome (println "Wuhoo! you've got all the utility functions. \nMay the force be with you!")})
