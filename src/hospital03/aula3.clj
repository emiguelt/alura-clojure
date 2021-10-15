(ns hospital03.aula3
  (:use [clojure pprint]
        [hospital utils])
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PostInt (s/pred pos-int? 'inteiro-positivo))

(defn maior-igual-a-zero? [x] (>= x 0))
(def ValorFinanceiro (s/constrained s/Num maior-igual-a-zero?))

(def Paciente
  "Schema de paciente"
  {:id PostInt :nome s/Str})

(s/defn novo-paciente :- Paciente
  [id :- PostInt, nome :- s/Str]
  {:id id :nome nome})

(pprint (novo-paciente 1 "Joao"))
(pprint (safe-try #(novo-paciente -1 "Joao")))

(def Pedido
  {:paciente     Paciente
   :valor        ValorFinanceiro
   :procedimento s/Keyword})

(s/defn novo-pedido :- Pedido
  [paciente :- Paciente
   valor :- ValorFinanceiro
   procedimento :- s/Keyword]
  {:paciente paciente :valor valor :procedimento procedimento})

(pprint (novo-pedido (novo-paciente 1 "Joao") 10.1 :raio-x))
(pprint (novo-pedido {:id 1 :nome "Joao"} 10.1 :raio-x))
(pprint (safe-try #(novo-pedido (novo-paciente 1 "Joao") -10.1 :raio-x)))
(pprint (safe-try #(novo-pedido (novo-paciente 1 "Joao") 10.1 "raio-x")))
(pprint (safe-try #(novo-pedido {:id -1 :nome "Joao"} 10.1  :raio-x)))


; defines a restriction for a sequence
(def Plano [s/Keyword])

(def Paciente
  "Schema de paciente"
  {:id PostInt :nome s/Str :plano Plano})

(pprint (s/validate  Paciente {:id 10 :nome "Joao" :plano [:raio-x :sangue]} ))
(pprint (s/validate  Paciente {:id 10 :nome "Joao" :plano [:raio-x]} ))
(pprint (s/validate  Paciente {:id 10 :nome "Joao" :plano []} ))
; nil is a valid value
(pprint (s/validate  Paciente {:id 10 :nome "Joao" :plano nil} ))
; the mandatory field must exist in the map
(pprint (safe-try #(s/validate Paciente {:id 10 :nome "Joao"})))
