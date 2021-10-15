(ns hospital03.aula2
  (:use [clojure pprint]
        [hospital utils])
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

; defines a Schema, does not need ":-"
(def Paciente
  "Schema de paciente"
  {:id s/Num :nome s/Str})

(pprint (s/explain Paciente))
(pprint (s/validate Paciente {:id 1 :nome "Joao"}))
(pprint (safe-try #(s/validate Paciente {:id 1 :name "Joao"})))
(pprint (safe-try #(s/validate Paciente {:nome "Joao"})))
(pprint (safe-try #(s/validate Paciente {:id 1 :nome "Joao" :other 1})))

; Can give Type to the function output
(s/defn novo-paciente :- Paciente
  [id :- s/Num,  nome :- s/Str]
  {:id id :nome nome})

(println (novo-paciente 1 "Joao"))

(s/defn novo-paciente-wrong-output :- Paciente
  [id :- s/Num,  nome :- s/Str]
  {:id id :nome nome :plano []})

(println (safe-try #(novo-paciente-wrong-output 1 "Joao")))

; custom validators
(defn estritamente-positivo? [x]
  (> x 0))

(def EstritamentePositivo (s/pred estritamente-positivo? 'description-positive))

(pprint (s/validate EstritamentePositivo 10))
(pprint (safe-try #(s/validate EstritamentePositivo 0)))
(pprint (safe-try #(s/validate EstritamentePositivo -10)))

; multiple validation in same field
(def Paciente
  "Schema de paciente"
  {:id (s/constrained s/Num pos?) :nome s/Str})

(pprint (s/validate Paciente {:id 1 :nome "Joao"}))
(pprint (safe-try #(s/validate Paciente {:id -1 :nome "Joao"})))
