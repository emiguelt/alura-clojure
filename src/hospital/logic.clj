(ns hospital.logic
  (:import (clojure.lang ExceptionInfo)))


(defn cabe-na-fila?
  [hospital departamento]
  ; some-> returns nil when a nil is found in the thread
  (some-> hospital
          departamento
          count
          (< 5)))

; this function throws an error that could be difficult to test, best could be return a meaningful response,
; like in chega-em-with-response
; this function must be private, but is left this way due to first exercises
(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "Fila cheia" {:fila departamento :pessoa-nova pessoa}))))

(defn chega-em-with-response
  [hospital departamento pessoa]
  (try (let [novo-hospital (chega-em hospital departamento pessoa)]
         {:hospital novo-hospital :result :success})
       (catch ExceptionInfo e (do {:hospital hospital :result :error-adding-person})))
  )

; added pause to generate concurrency errors
(defn chega-em-pausado
  [hospital departamento pessoa]
  (Thread/sleep (* (rand) 2000))
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "Fila cheia" {:fila departamento :pessoa-nova pessoa}))))

(defn atende
  [hospital departamento]
  (update hospital departamento pop))


(defn transfere
  [hospital de para]
  {
   ; pre/post conditions work as Java asserts
   ; it generates error if assertion fails
   ; but it could be disabled in production
   :pre  [(not (nil? hospital))]
   ; in post condition the % symbol represents the output of the method
   :post [(= (+ (count (de hospital)) (count (para hospital)))
             (+ (count (de %)) (count (para %))))]
   }
  (let [pessoa (peek (get hospital de))]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))