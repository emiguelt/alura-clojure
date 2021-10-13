(ns hospital.aula4
  (:use [clojure pprint]
        [hospital.utils])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))
(defn chega-pausado! [pessoa hospital]
  (swap! hospital h.logic/chega-em-pausado :espera pessoa))

(defn start-thread [hospital pessoa] (task #(chega-pausado! pessoa hospital)))

; doseq to iter over data
(defn simula-dia-paralelo-refactorado-with-doseq []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas [111 222 333 444 555 666]]
    (doseq [pessoa pessoas]
      (start-thread hospital pessoa))

    (Thread/sleep 10000)
    (pprint hospital)))

(simula-dia-paralelo-refactorado-with-doseq)

; dotimes to iterate n times
(defn simula-dia-paralelo-refactorado-with-dotimes []
  (let [hospital (atom (h.model/novo-hospital))]
    (dotimes [pessoa 6]
      (start-thread hospital pessoa))

    (Thread/sleep 10000)
    (pprint hospital)))

(simula-dia-paralelo-refactorado-with-dotimes)

(println "end")
