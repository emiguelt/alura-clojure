(ns hospital03.aula4
  (:use [clojure pprint]
        [hospital utils])
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PostInt (s/pred pos-int? 'inteiro-positivo))
(def Plano [s/Keyword])

; we can define an optional field
(def Paciente
  "Schema de paciente"
  {:id PostInt
   :nome s/Str
   :plano Plano
   (s/optional-key :nascimento) s/Str})

(pprint (s/validate  Paciente {:id 10 :nome "Joao" :plano [:raio-x :sangue]} ))
(pprint (s/validate  Paciente {:id 10 :nome "Joao" :plano [:raio-x] :nascimento "1/1/1"} ))
(pprint (safe-try #(s/validate Paciente {:id 10 :nome "Joao" :plano [:raio-x] :nascimento 1})))

;;;;;;;;;;;;;;;;;;
; Maps with dynamic keys (no fixed keywords)

(def Pacientes
  {PostInt Paciente})

(pprint (s/validate Pacientes {1 {:id 1 :nome "Joao" :plano []}}))
(pprint (safe-try #(s/validate Pacientes {-1 {:id 1 :nome "Joao" :plano []}})))
(pprint (safe-try #(s/validate Pacientes {:1 {:id 1 :nome "Joao" :plano []}})))

