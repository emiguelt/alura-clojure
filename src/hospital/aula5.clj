(ns hospital.aula5
  (:use [clojure pprint]
        [hospital.utils])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

(defn chega-em! [pessoa hospital]
  (swap! hospital h.logic/chega-em :espera pessoa))

(defn transfere! [hospital de para]
  (swap! hospital h.logic/transfere de para))

(defn start-thread [hospital pessoa] (task #(chega-em! pessoa hospital)))

(defn simula-dia []
  (let [hospital (atom (h.model/novo-hospital))]
      (start-thread hospital "joao")
      (start-thread hospital "maria")
      (start-thread hospital "daniela")
      (start-thread hospital "miguel")
      (transfere! hospital :espera :laboratorio1)
      (pprint @hospital)))


(simula-dia)

;; use juxt function to call two or more function over the same data and return the result of all in a single structure (array

(println "end")
