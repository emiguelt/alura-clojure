(ns hospital02.aula4
  (:use [clojure pprint]))

(defrecord PacienteParticular [id nome nascimento situacao])
(defrecord PacienteComPlanoDeSaude [id nome nascimento situacao plano])

; immutable method that can be tested
(defn tipo-de-autorizador [pedido]
  (let [paciente (:paciente pedido)
        situacao (:situacao paciente)
        urgencia? (= :urgente situacao)]
    (if urgencia?
      :sempre-autorizado
      (class paciente))))

; defines a method to have many implementation, with a selector
(defmulti deve-assinar-pre-autorizacao-do-pedido? tipo-de-autorizador)

; first implementation of the method
(defmethod deve-assinar-pre-autorizacao-do-pedido? :sempre-autorizado [_]
  false)

; second implementation
(defmethod deve-assinar-pre-autorizacao-do-pedido? PacienteParticular [pedido]
  (>= (:valor pedido 0) 50))

; third implementation
(defmethod deve-assinar-pre-autorizacao-do-pedido? PacienteComPlanoDeSaude [pedido]
  (not (some #(= % (:procedimento pedido)) (-> pedido :paciente :plano))))

(let [particular (->PacienteParticular 1 "MIguel" "1/1/1" :urgente)
      plano (->PacienteComPlanoDeSaude 1 "Miguel" "1/1/1" :urgente [:raio-x :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular :valor 100 :procedimento :sangue}))
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano :valor 100 :procedimento :sangue})))

(let [particular (->PacienteParticular 1 "MIguel" "1/1/1" :normal)
      plano (->PacienteComPlanoDeSaude 1 "Miguel" "1/1/1" :normal [:raio-x :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular :valor 100 :procedimento :sangue}))
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano :valor 100 :procedimento :sangue})))
