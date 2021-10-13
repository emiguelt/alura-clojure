(ns hospital02.aula2
  (:use [clojure pprint]))

(defrecord PacienteParticular [id nome nascimento])
(defrecord PacienteComPlanoDeSaude [id nome nascimento plano])

(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

; implmenets Cobravel for records
(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (>= valor 50)))

(extend-type PacienteComPlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (not (some #(= % procedimento) plano)))))

(let [particular (PacienteParticular. 1 "MIguel" "1/1/1")
      plano (PacienteComPlanoDeSaude. 1 "Miguel" "1/1/1" [:raio-x :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 100))
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 10))
  (pprint (deve-assinar-pre-autorizacao? plano :raio-x 100))
  (pprint (deve-assinar-pre-autorizacao? plano :sangue 100)))

;; extend-type can be used for any existing class in JVM, like numbers, strings, dates, etc
;; its like extension in kotlin