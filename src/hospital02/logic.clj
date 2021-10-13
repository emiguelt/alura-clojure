(ns hospital02.logic
  (:use [clojure pprint])
  (:require [hospital02.model :as h.model]))

(defn agora []
  (h.model/to-ms (java.util.Date.)))