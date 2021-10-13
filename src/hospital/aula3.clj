(ns hospital.aula3
  (:use [clojure pprint]
        [hospital.utils])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

; atom is like a wrapper that manteins a value that can be updated
(defn test-atomao []
  (let [hospital-triana (atom {:espera h.model/empty-queue})]
    (pprint hospital-triana)
    (pprint (deref hospital-triana))                        ;; de-reference get the content of the atom
    (pprint @hospital-triana)                               ;; shortcut for deref

    ; associates a value in the map, but does not update the original
    (pprint (assoc @hospital-triana :laboratorio1 h.model/empty-queue))
    (pprint @hospital-triana)

    ; update the original
    (pprint (swap! hospital-triana assoc :laboratorio1 h.model/empty-queue))
    (pprint @hospital-triana)
    ))

(test-atomao)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(println "Hospital pausado")

(defn chega-pausado! [pessoa hospital]
  (swap! hospital h.logic/chega-em-pausado :espera pessoa))

(defn start-thread [hospital pessoa] (task #(chega-pausado! pessoa hospital)))

(defn simula-dia-paralelo-refactorado-with-partial []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas [111 222 333 444 555 666]
        ]
    (mapv (partial start-thread hospital) pessoas)

    (Thread/sleep 10000)
    (pprint hospital)))

(simula-dia-paralelo-refactorado-with-partial)

(println "end")