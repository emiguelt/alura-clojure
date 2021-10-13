(ns hospital.aula6
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]))
(defn chega-em [fila pessoa]
  (conj fila pessoa))

(defn chega-em!
  [hospital pessoa]
  (let [fila (get hospital :espera)]
    (alter fila chega-em pessoa)))                          ;;alter update a ref receiving the function
;;ref-set can be used instead of the alter, but the execution of the function is passed as parameter
;;(ref-set fila (chega-em @fila pessoa))

(defn simula-um-dia []
  (let [hospital {:espera       (ref h.model/empty-queue)
                  :laboratorio1 (ref h.model/empty-queue)
                  :laboratorio2 (ref h.model/empty-queue)
                  :laboratorio3 (ref h.model/empty-queue)
                  }]
    (dosync
      (chega-em! hospital "miguel"))
    (pprint hospital)))

(simula-um-dia)

(defn async-chega-em! [hospital pessoa]
  (future
    (Thread/sleep (rand 5000))
    (dosync
      (println "Tentando codigo sinconizado" pessoa)
      (chega-em! hospital pessoa))))

(defn simula-um-dia-async []
  (let [hospital {:espera       (ref h.model/empty-queue)
                  :laboratorio1 (ref h.model/empty-queue)
                  :laboratorio2 (ref h.model/empty-queue)
                  :laboratorio3 (ref h.model/empty-queue)
                  }]
    (let [futures (mapv #(async-chega-em! hospital %) (range 10))]
      (future
        (dotimes [n 4]
          (Thread/sleep 2000)
          (pprint hospital)
          (pprint futures))))))

(simula-um-dia-async)

