(ns loja.db)

(def pedido1 {:usuario 1
              :itens   {:mochila  {:id :mochila, :quantidade 2, :preco-unitario 10}
                        :camiseta {:id :camiseta, :quantidade 3, :preco-unitario 5}
                        :tenis {:id:%s :tenis, :quantidade 1}}})
(def pedido2 {:usuario 15
              :itens   {:mochila  {:id :mochila, :quantidade 1, :preco-unitario 10}
                        :camiseta {:id :camiseta, :quantidade 10, :preco-unitario 5}
                        :tenis {:id :tenis, :quantidade 1}}})
(def pedido3 {:usuario 15
              :itens   {:mochila  {:id :mochila, :quantidade 20, :preco-unitario 10}
                        :camiseta {:id :camiseta, :quantidade 3, :preco-unitario 5}
                        :tenis {:id :tenis, :quantidade 1}}})
(def pedido4 {:usuario 10
              :itens   {:mochila  {:id :mochila, :quantidade 6, :preco-unitario 10}
                        :camiseta {:id :camiseta, :quantidade 2, :preco-unitario 5}
                        :tenis {:id :tenis, :quantidade 1}}})
(def pedido5 {:usuario 1
              :itens   {:mochila  {:id :mochila, :quantidade 2, :preco-unitario 10}
                        :camiseta {:id :camiseta, :quantidade 9, :preco-unitario 5}
                        :tenis {:id :tenis, :quantidade 1}}})
(def pedido6 {:usuario 1
              :itens   {:mochila  {:id :mochila, :quantidade 4, :preco-unitario 10}
                        :camiseta {:id :camiseta, :quantidade 7, :preco-unitario 5}
                        :tenis {:id :tenis, :quantidade 1}}})

(defn todos-pedidos [] [pedido1 pedido2 pedido3 pedido4 pedido5 pedido6])
