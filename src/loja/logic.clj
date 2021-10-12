(ns loja.logic)
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

(defn resumo-usuario
  [pedidos]
  (map total-usuario (group-by :usuario pedidos)))