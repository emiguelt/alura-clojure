(ns hospital.logic)

(defn cabe-na-fila?
  [hospital departamento]
  (< (count (get hospital departamento)) 5))

(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "Fila cheia" {:fila departamento :pessoa-nova pessoa}))))

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
  (let [pessoa (peek (get hospital de))]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))