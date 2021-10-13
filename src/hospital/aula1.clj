; Aula 1
(ns hospital.aula1
  (:use [clojure pprint]
        [hospital.utils])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model])
  )

(defn test-colecao
  [tipo elementos]
  (println tipo (seq elementos))
  (println (seq (conj elementos 333)))
  (try (do (pprint  (pop elementos))
           (println (peek elementos)))
       (catch Exception e (println (.getMessage e)))))

(test-colecao "Vector" [111 222])
(test-colecao "Lista" '(111 222))
(test-colecao "Set" #{111 222})
(test-colecao "Fila" (conj (clojure.lang.PersistentQueue/EMPTY) 111 222))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(println "\n\n\nHospital")

(defn simula-um-dia []
  ; root binding
  (def hospital (h.model/novo-hospital))
  (def hospital (h.logic/chega-em hospital :espera 111))
  (def hospital (h.logic/chega-em hospital :espera 222))
  (def hospital (h.logic/chega-em hospital :espera 333))
  (def hospital (h.logic/chega-em hospital :laboratorio1 444))
  (def hospital (h.logic/chega-em hospital :laboratorio3 555))
  ; funciona, mas é uma variavel global
  (pprint hospital)

  (def hospital (h.logic/atende hospital :laboratorio1))
  (def hospital (h.logic/atende hospital :espera))
  (pprint hospital)

  (def hospital (h.logic/chega-em hospital :espera 666))
  (def hospital (h.logic/chega-em hospital :espera 777))
  (def hospital (h.logic/chega-em hospital :espera 888))
  (try (def hospital (h.logic/chega-em hospital :espera 999))
       (catch Exception e (println (.getMessage e))))
  (pprint hospital)
  )

(simula-um-dia)

(println "Hospital pausado")

(defn chega-pausado [pessoa]
  (def hospital (h.logic/chega-em-pausado hospital :espera pessoa)))

;; ERROR adding concurrently
(defn simula-dia-paralelo []
  (def hospital (h.model/novo-hospital))
  (task #(chega-pausado "111"))
  (task #(chega-pausado "222"))
  (task #(chega-pausado "333"))
  (task #(chega-pausado "444"))
  (task #(chega-pausado "555"))
  (task #(chega-pausado "666"))
  (Thread/sleep 10000)
  (pprint hospital))

(simula-dia-paralelo)

(println "end")