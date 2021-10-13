(ns hospital02.aula5
  (:use [clojure pprint]))

; immutable method that can be tested
(defn tipo-de-autorizador [pedido]
  (let [paciente (:paciente pedido)
        situacao (:situacao paciente)]
    (cond (= :urgente situacao) :sempre-autorizado
          (contains? paciente :plano) :plano-de-saude
          :else :credito-minimo)))

; defines a method to have many implementation, with a selector
(defmulti deve-assinar-pre-autorizacao-do-pedido? tipo-de-autorizador)

; first implementation of the method
(defmethod deve-assinar-pre-autorizacao-do-pedido? :sempre-autorizado [_]
  false)

; second implementation
(defmethod deve-assinar-pre-autorizacao-do-pedido? :credito-minimo [pedido]
  (>= (:valor pedido 0) 50))

; third implementation
(defmethod deve-assinar-pre-autorizacao-do-pedido? :plano-de-saude [pedido]
  (not (some #(= % (:procedimento pedido)) (-> pedido :paciente :plano))))

(let [particular {:id 1 :nome "MIguel" :nascimento "1/1/1" :situacao :urgente}
      plano {:id 1 :nome "MIguel" :nascimento "1/1/1" :situacao :urgente :plano [:raio-x :ultrassom]}]
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular :valor 100 :procedimento :sangue}))
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano :valor 100 :procedimento :sangue})))

(let [particular {:id 1 :nome "MIguel" :nascimento "1/1/1" :situacao :normal}
      plano {:id 1 :nome "MIguel" :nascimento "1/1/1" :situacao :normal :plano [:raio-x :ultrassom]}]
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular :valor 100 :procedimento :sangue}))
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano :valor 100 :procedimento :sangue})))
