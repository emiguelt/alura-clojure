(ns hospital02.aula1
  (:use [clojure pprint]))

(defn adiciona-paciente
  [pacientes paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente nao possui id" {:paciente paciente}))))


(defn test-adiciona-paciente []
  (let [pacientes {}
        miguel {:id 1 :name "Miguel" :nascimento "20/1/2000"}
        maria {:id 2 :name "Maria" :nascimento "20/1/2002"}
        joao { :name "Joao" :nascimento "20/1/2001"}
        ]
    (pprint  (adiciona-paciente pacientes miguel))
    (pprint  (adiciona-paciente pacientes maria))
    (try (pprint (adiciona-paciente pacientes joao))
         (catch Exception e (println "Error " (.getMessage e))))
    ))

(test-adiciona-paciente)

; Java type, each field is object type, funciona como um map
(defrecord Paciente [id nome nascimento])

; how to build:
(println (->Paciente 15, "Joao" "1/1/1"))
(pprint (->Paciente 15, "Joao" "1/1/1"))
(pprint (Paciente. 15, "Joao" "1/1/1"))
(pprint (Paciente. "Joao" 15 "1/1/1"))                      ;does not validates the order of the params
(pprint (map->Paciente {:id 15 :nome "Joao" :nascimento "1/1/29"}))
(pprint (class (Paciente. 15, "Joao" "1/1/1")))
(pprint (class (map->Paciente {:id 15 :nome "Joao" :nascimento "1/1/29"})))


(pprint (map->Paciente {:id 15 :nome "Joao" :nascimento "1/1/29" :rg 122})) ;Can aggregate date that is out of the record
(pprint (class (map->Paciente {:id 15 :nome "Joao" :nascimento "1/1/29" :rg 122}))) ;Can aggregate date that is out of the record


(println (= (Paciente. 15, "Joao" "1/1/1") (Paciente. 15, "Joao" "1/1/1")))
(println (= (Paciente. 135, "Joao" "1/1/1") (Paciente. 15, "Joao" "1/1/1")))
