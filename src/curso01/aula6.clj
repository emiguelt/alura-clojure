(ns curso01.aula6)

(def pedido {:mochila {:quantidade 10, :preco 100}
             :camiseta {:quantidade 4, :preco 50}})

(defn preco-de-produtos
  [[_ item]]
  (* (:quantidade item) (:preco item)))

(println "Pedido:" pedido)
(println  "Preco por item:" (map preco-de-produtos pedido))
(println "Total:" (reduce + (map preco-de-produtos pedido)))

(defn total-do-pedido
  [pedido]
  (reduce + (map preco-de-produtos pedido))
  )

(println (total-do-pedido pedido))

(defn total-do-pedido
  [pedido]
  (->> pedido
      (map preco-de-produtos)
      (reduce +)
  )  )

(println (total-do-pedido pedido))
