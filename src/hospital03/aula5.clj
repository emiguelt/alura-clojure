(ns hospital03.aula5
  (:use [clojure pprint]
        [hospital utils])
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PostInt (s/pred pos-int? 'inteiro-positivo))
(def Plano [s/Keyword])

; we can define an optional field
(def Paciente
  "Schema de paciente"
  {:id                          PostInt
   :nome                        s/Str
   :plano                       Plano
   (s/optional-key :nascimento) s/Str})

(def Pacientes
  {PostInt Paciente})

(def Visitas
  {PostInt [s/Str]})

(s/defn adiciona-pacientes :- Pacientes
  [pacientes :- Pacientes paciente :- Paciente]
  (let [id (:id paciente)]
    (assoc pacientes id paciente)))

(s/defn adiciona-visita :- Visitas
  [visitas :- Visitas
   paciente :- PostInt
   novas-visitas :- [s/Str]]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc visitas paciente novas-visitas)))

(s/defn imprime-relatorio-de-paciente
  [visitas :- Visitas
   paciente :- PostInt]
  (println "Visitas do paciente" paciente "sao:" (get visitas paciente)))


(defn test-uso-de-pacientes []
  (let [miguel {:id 1 :nome "Miguel" :plano []}
        maria {:id 2 :nome "Maria" :plano []}
        paulo {:id 3 :nome "Paulo" :plano []}
        pacientes (reduce adiciona-pacientes {} [miguel, maria, paulo])
        visitas (-> {}
                    (adiciona-visita (:id miguel) ["1/1/1"])
                    (adiciona-visita (:id maria) ["1/1/1", "2/2/2"])
                    (adiciona-visita (:id miguel) ["3/1/1"])
                    )]
    (pprint visitas)
    (pprint pacientes)
    (imprime-relatorio-de-paciente visitas (:id miguel))))
(test-uso-de-pacientes)