(ns loja.aula3
  (:require [loja.db :as db]))

(println (db/todos-pedidos))

; grouping data by user
(println (group-by :usuario (db/todos-pedidos)))

; custom group by
(defn minha-funcao-agrupamento
  [elemento]
  (:usuario elemento))

(println (group-by minha-funcao-agrupamento (db/todos-pedidos)))

; how manuy users has "pedido"
(println (count (group-by :usuario (db/todos-pedidos))))

; how many "pedido" by user
(println (map count (vals (group-by :usuario (db/todos-pedidos)))))

(->> (db/todos-pedidos)
     (group-by :usuario)
     vals
     (map count)
     println)


; how many "pedido" by user with key
(defn pedido-por-usuario
  [[usuario pedidos]]
  [usuario (count pedidos)])

(defn total-detail
  [detail-entry]
  (* (:quantidade detail-entry 0) (:preco-unitario detail-entry 0)))

; get price by pedido
(defn preco-pedido
  [pedido]
  ;(println "preco pedido" pedido)
  (let [detail (vals (:itens pedido))]
    (reduce + (map total-detail detail))))

(defn total-usuario
  [[usuario pedidos]]
  {:usuario usuario :totales (reduce + (map preco-pedido pedidos)) :pedidos (count pedidos)})

(println (map pedido-por-usuario (group-by :usuario (db/todos-pedidos))))
(println (map total-usuario (group-by :usuario (db/todos-pedidos))))