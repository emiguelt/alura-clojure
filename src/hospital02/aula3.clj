(ns hospital02.aula3
  (:use [clojure pprint])
  (:require [hospital02.logic :as h.logic]))

(defn carrega-paciente [id]
  (println "Carregando" id)
  (Thread/sleep 1000)
  {:id id, :carregado-em (h.logic/agora)})

(defn carrega-se-nao-existe
  [cache id carregadora]
  (if (contains? cache id)
    cache
    (let [paciente (carregadora id)]
      (assoc cache id paciente))))

(defprotocol Carregavel
  (carrega! [this id]))

(defrecord Cache
  [cache carregadora]
  Carregavel
  (carrega! [this id]
    (swap! cache carrega-se-nao-existe id carregadora)
    (get @cache id)))

(let [pacientes (->Cache (atom {}), carrega-paciente)]
  (pprint pacientes)
  (carrega! pacientes 15)
  (carrega! pacientes 30)
  (carrega! pacientes 15)
  (pprint pacientes))

