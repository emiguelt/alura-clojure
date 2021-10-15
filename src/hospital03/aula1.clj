(ns hospital03.aula1
  (:use clojure.pprint)
  (:require [schema.core :as s]))

; Schema is used to validates the inputs of a method to guarantee the received type

; it validate a value is of a defined type
(s/validate Long 15)
(try (s/validate Long "Hello")
     (catch Exception e (pprint (.getMessage e))))

; makes schema validation default
(s/set-fn-validation! true)

(s/defn teste-simples [x :- Long]
  (println x))

;the errors are check in execution time
(teste-simples 1)
(try  (teste-simples "Hello")
      (catch Exception e (pprint (.getMessage e))))

; Schema has its own types like s/Str
(s/defn novo-paciente
  [id :- Long, nome :- s/Str]
  {:id id :nome nome})

(pprint (novo-paciente 1 "Joao"))
(pprint (try (novo-paciente "Joao" 1)
             (catch Exception e (.getMessage e))))